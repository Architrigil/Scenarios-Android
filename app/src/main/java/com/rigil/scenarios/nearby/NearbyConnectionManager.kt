package com.rigil.scenarios.nearby

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.rigil.scenarios.BuildConfig
import java.util.*
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.AdvertisingOptions


object NearbyConnectionManager : ConnectionInterface, ConnectionInterface.ConnectionData {
    private const val SERVICE_ID = BuildConfig.APPLICATION_ID
    private lateinit var connectionsClient: ConnectionsClient
    lateinit var connection: ConnectionInterface.Connection
    lateinit var device: ConnectionInterface.Device
    var gameScoreInterface: ConnectionInterface.FragmentScore? = null
    private var instance: NearbyConnectionManager? = null
    private var lock = Object()
    private var onlyEndpoint: String = ""
    private var endpointsList: ArrayList<String>? = null
    // Status of device connection
    enum class State {
        UNKNOWN,
        SEARCHING,
        CONNECTED
    }

    private var state = NearbyConnectionManager.State.UNKNOWN
        set(state) {
            if (NearbyConnectionManager.state == state) {
                NearbyConnectionManager.showLog("State set to $state but already in that state")
                return
            }
            NearbyConnectionManager.showLog("State set to $state")
            field = state
            NearbyConnectionManager.onStateChanged(state)
        }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            NearbyConnectionManager.connection.askToAcceptTheConnection(endpointId)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    NearbyConnectionManager.showLog("now connected")
                    NearbyConnectionManager.connection.connectToDevice(endpointId)
                    NearbyConnectionManager.onlyEndpoint = endpointId
                }
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    NearbyConnectionManager.connection.rejectConnection(endpointId)
                }
                ConnectionsStatusCodes.STATUS_ERROR -> {
                    NearbyConnectionManager.connection.connectionError(endpointId)
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            NearbyConnectionManager.showLog("Disconnected")
            NearbyConnectionManager.device.onDeviceLost(endpointId)
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            NearbyConnectionManager.showLog("got from endpoint $endpointId")
            try {
                val data = SerializationHelper.deserialize(payload.asBytes())
                when (data) {
                    is Int -> {
                        NearbyConnectionManager.showLog("int is ->" + data.toString())
                        NearbyConnectionManager.receiveScore(data)
                    }
                    is Array<*> -> {
                        NearbyConnectionManager.showLog("array is -> $data")
                        NearbyConnectionManager.connection.onReceiveDataSource(endpointId, data)
                    }
                    else -> NearbyConnectionManager.showLog("other type of data -> $data")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {

        }
    }

    private val userNickname: String
        get() {
            var name = ""
            val random = Random()
            for (i in 0..4) {
                name += random.nextInt(10)
            }
            return name
        }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            NearbyConnectionManager.showLog("Endpoint is $endpointId")
            NearbyConnectionManager.endpointsList?.add(endpointId)
            NearbyConnectionManager.device.onDeviceFound(endpointId)
        }

        override fun onEndpointLost(endpointId: String) {
            // A previously discovered endpoint has gone away
            NearbyConnectionManager.endpointsList?.remove(endpointId)
            NearbyConnectionManager.device.onDeviceLost(endpointId)
        }
    }

    init {
        NearbyConnectionManager.endpointsList = ArrayList()
    }

    // returns connectivity instance for searching the endpoints
    fun getInstance(context: Context, connection: ConnectionInterface.Connection, device: ConnectionInterface.Device): NearbyConnectionManager {
        synchronized(NearbyConnectionManager.lock) {
            if (NearbyConnectionManager.instance == null) {
                NearbyConnectionManager.instance = NearbyConnectionManager
            }
            NearbyConnectionManager.connectionsClient = Nearby.getConnectionsClient(context)
            NearbyConnectionManager.connection = connection
            NearbyConnectionManager.device = device
            return NearbyConnectionManager.instance as NearbyConnectionManager
        }
    }

    // need this for managing scores; it returns connectivity instance with score; implemented in GameFragment
    fun getInstanceSecond(context: Context, gameScoreInterface: ConnectionInterface.FragmentScore, device: ConnectionInterface.Device): NearbyConnectionManager {
        synchronized(NearbyConnectionManager.lock) {
            if (NearbyConnectionManager.instance == null) {
                NearbyConnectionManager.instance = NearbyConnectionManager
            }
            NearbyConnectionManager.connectionsClient = Nearby.getConnectionsClient(context)
            NearbyConnectionManager.gameScoreInterface = gameScoreInterface
            NearbyConnectionManager.device = device
            return NearbyConnectionManager.instance as NearbyConnectionManager
        }
    }

    // requests to connect to endpoint
    fun connectToEndpoint(endpointId: String) {
        NearbyConnectionManager.connectionsClient.requestConnection(
                NearbyConnectionManager.userNickname,
                endpointId,
                NearbyConnectionManager.connectionLifecycleCallback)
                .addOnSuccessListener {
                    // We successfully requested a connection. Now both sides
                    // must accept before the connection is established.
                    NearbyConnectionManager.showLog("Requested a connection")
                    NearbyConnectionManager.connection.requestedTheConnection(endpointId)

                }
                .addOnFailureListener {
                    // Nearby Connections failed to request the connection.
                    NearbyConnectionManager.showLog("failure on request connection")
                    NearbyConnectionManager.connection.failedToRequestConnection(endpointId)
                }
    }

    // starts showing availability to be connected
    private fun startAdvertising() {
        NearbyConnectionManager.connectionsClient.startAdvertising(
                NearbyConnectionManager.userNickname,
                NearbyConnectionManager.SERVICE_ID,
                NearbyConnectionManager.connectionLifecycleCallback,
                AdvertisingOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build())
                .addOnSuccessListener {
                    // We're advertising!
                    NearbyConnectionManager.showLog("now advertising")
                }
                .addOnFailureListener {
                    // We were unable to start advertising.
                    NearbyConnectionManager.showLog("Error on advertising")
                }
    }

    // starts looking for devices
    private fun startDiscovery() {
        NearbyConnectionManager.connectionsClient.startDiscovery(
                NearbyConnectionManager.SERVICE_ID,
                NearbyConnectionManager.endpointDiscoveryCallback,
                DiscoveryOptions.Builder().setStrategy(Strategy.P2P_POINT_TO_POINT).build())
                .addOnSuccessListener {
                    // We're discovering!
                    NearbyConnectionManager.showLog("Discovering")
                }
                .addOnFailureListener { e ->
                    // We were unable to start discovering.
                    NearbyConnectionManager.showLog("Error on Discovering ${e.printStackTrace()}")
                }
    }

    private fun sendArray(endpointId: String, data: Array<*>) {
        try {
            NearbyConnectionManager.connectionsClient.sendPayload(
                    endpointId, Payload.fromBytes(SerializationHelper.serialize(data)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendScore(endpointId: String, score: Int) {
        try {
            NearbyConnectionManager.connectionsClient.sendPayload(
                    endpointId, Payload.fromBytes(SerializationHelper.serialize(score)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showLog(message: String) {
        Log.d("ConnectionManager", message)
    }

    private fun disconnectFromAllEndpoints() {
        NearbyConnectionManager.endpointsList?.indices?.forEach { i ->
            NearbyConnectionManager.endpointsList?.get(i)?.let { NearbyConnectionManager.connectionsClient.disconnectFromEndpoint(it) }
        }
    }

    private fun stopAllEndpoints() {
        NearbyConnectionManager.connectionsClient.stopAllEndpoints()
        NearbyConnectionManager.endpointsList = ArrayList()
    }

    private fun stopDiscovery() {
        NearbyConnectionManager.connectionsClient.stopDiscovery()
    }

    private fun stopAdvertising() {
        NearbyConnectionManager.connectionsClient.stopAdvertising()
    }

    private fun onStateChanged(newState: NearbyConnectionManager.State) {
        when (newState) {
            NearbyConnectionManager.State.SEARCHING -> {
                NearbyConnectionManager.disconnectFromAllEndpoints()
                NearbyConnectionManager.startDiscovery()
                NearbyConnectionManager.startAdvertising()
            }
            NearbyConnectionManager.State.CONNECTED -> {
                NearbyConnectionManager.stopDiscovery()
                NearbyConnectionManager.stopAdvertising()
            }
            NearbyConnectionManager.State.UNKNOWN -> NearbyConnectionManager.stopAllEndpoints()
        }
    }

    override fun onDisconnect() {
        NearbyConnectionManager.state = NearbyConnectionManager.State.UNKNOWN
        NearbyConnectionManager.showLog("Disconnected")
    }

    override fun onConnect() {
        NearbyConnectionManager.state = NearbyConnectionManager.State.SEARCHING
    }

    override fun sendDataSource(endpointId: String, data: Array<*>) {
        NearbyConnectionManager.sendArray(endpointId, data)
    }

    fun receiveScore(score: Int) {
        NearbyConnectionManager.gameScoreInterface?.receiveScore(score)
    }

    fun acceptTheConnection(endpointId: String) {
        NearbyConnectionManager.connectionsClient.acceptConnection(endpointId, NearbyConnectionManager.payloadCallback)
    }

    fun rejectConnection(endpointId: String) {
        NearbyConnectionManager.connectionsClient.rejectConnection(endpointId)
    }
}
