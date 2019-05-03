package com.rigil.scenarios.nearby

interface ConnectionInterface {
    fun onDisconnect()
    fun onConnect()

    interface Connection {
        fun connectToDevice(endpointId: String)
        fun onReceiveDataSource(endpointId: String, data: Array<*>)
        fun askToAcceptTheConnection(endpointId: String)
        fun rejectConnection(endpointId: String)
        fun connectionError(endpointId: String)
        fun requestedTheConnection(endpointId: String)
        fun failedToRequestConnection(endpointId: String)
    }

    interface Device {
        fun onDeviceFound(endpointId: String)
        fun onDeviceLost(endpointId: String)
    }

    interface ConnectionData {
        fun sendDataSource(endpointId: String, data: Array<*>)
    }

    interface FragmentScore {
        fun receiveScore(score: Int)
    }
}



