package com.rigil.scenarios.common

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.rigil.mogcorelib.common.GameMenuBaseActivity
import com.rigil.mogcorelib.common.SharedPreferencesManager
import com.rigil.mogcorelib.utils.fromJson
import com.rigil.sganalytics.sdk.Tracker
import com.rigil.sganalytics.sdk.extra.SGAnalyticsApplication
import com.rigil.sganalytics.sdk.extra.SGAnalyticsTrackHelper
import com.rigil.scenarios.BuildConfig
import com.rigil.scenarios.R
import com.rigil.scenarios.evaluation.EvaluationFragment
import com.rigil.scenarios.game.GameActivity
import com.rigil.scenarios.nearby.ConnectionInterface
import com.rigil.scenarios.nearby.NearbyConnectionManager
import com.rigil.scenarios.review.ReviewFragment
import kotlinx.android.synthetic.main.activity_game_screen.*
import kotlinx.android.synthetic.main.info_pop_tutorial_option.view.*
import kotlinx.android.synthetic.main.row_simple_list.view.*
import java.util.*


class GameScreenActivity : GameMenuBaseActivity() {

    private var nearbyConnectionManager: NearbyConnectionManager? = null
    private lateinit var endpointsList: ArrayList<String>
    private var userDevice: Boolean = false
    private var popupWindow = PopupWindow()

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)
        setTitleInGame() // For showing selected Practice set in Top bar
        iv_play_button.setOnClickListener { navigateToPlayScreen(false) }
        setUpInfoButton()

        // option buttons at bottom
        setUpOptionsButtonAction() // These are working in Big Devices only and will be hidden in phones
        // tab bar at bottom
        setupBottomTabBarButtonAction()// These are working in Small Devices only and will be hidden in Tablets
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus)
            hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun setUpInfoButton() {
        // Info button
        popupWindow = PopupWindow(layoutInflater.inflate(R.layout.info_pop_tutorial_option, null)).apply {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            contentView.tv_pop_content_source_version.text = ""  //BuildConfig.BASE_URL
            contentView.tv_info_version.text = BuildConfig.VERSION_NAME
            isOutsideTouchable = true
        }
        iv_main_info.setOnClickListener {
            popupWindow.showAtLocation(it, Gravity.BOTTOM or Gravity.LEFT, dpToPx(resources.getInteger(R.integer.info_pop_up_left_margin)), dpToPx(resources.getInteger(R.integer.info_pop_up_bottom_margin)))
        }
    }

    private fun setUpOptionsButtonAction() {
        ivbtn_gamemenu_profile.setOnClickListener { loadProfile() }
        btn_gamemenu_leaderboard.setOnClickListener { loadLeaderboard() }
        btn_gamemenu_reference.setOnClickListener { loadReference() }
        btn_gamemenu_review.setOnClickListener { navigateToReview() }
        btn_gamemenu_evaluate.setOnClickListener { navigateToEvaluation() }
    }

    private fun setupBottomTabBarButtonAction() {
        val bottomNavigationView = bottom_navigation as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.iv_info -> {
                    popupWindow.showAtLocation(bottomNavigationView, Gravity.BOTTOM or Gravity.LEFT, dpToPx(resources.getInteger(R.integer.info_pop_left_margin)), dpToPx(resources.getInteger(R.integer.info_pop_bottom_margin)))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btn_game_review -> {
                    navigateToReview()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btn_game_reference -> {
                    loadReference()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btn_game_leaderboard -> {
                    loadLeaderboard()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.btn_game_evaluate -> {
                    navigateToEvaluation()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = this.resources
                .displayMetrics
                .density
        return Math.round(dp.toFloat() * density)
    }

    // shows alert with available endpoints to be connected with peer-to-peer end-devices
    private fun showAlertWithEndpoints() {
        endpointsList = ArrayList()
        var askToAcceptAlert = AlertDialog.Builder(this, R.style.AlertWithBlackText).create()
        val adb = AlertDialog.Builder(this, R.style.AlertWithBlackText)
        adb.setTitle("Choose Device")
        adb.setNegativeButton("Cancel") { _, _ -> }
        val view = LayoutInflater.from(this).inflate(R.layout.row_simple_list, null)
        adb.setView(view)
        val list = view.lv_simple
        val progressBar = view.progress_bar_alert_devices
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, endpointsList)
        list.adapter = adapter
        val adbCreate = adb.create()
        nearbyConnectionManager = NearbyConnectionManager.getInstance(this, object : ConnectionInterface.Connection {
            override fun connectToDevice(endpointId: String) {
                progressBar.visibility = View.GONE
                adbCreate.dismiss()
                if (userDevice) {
                    nearbyConnectionManager?.sendDataSource(endpointId, getAllDataRecords()!!)
                    val intent = Intent(this@GameScreenActivity, GameActivity::class.java)
                    intent.putExtra("data", getAllDataRecords())
                    intent.putExtra("versusMode", true)
                    intent.putExtra("endpointId", endpointId)
                    startActivity(intent)
                }
            }

            override fun onReceiveDataSource(endpointId: String, data: Array<*>) {
                adbCreate.dismiss()
                if (!userDevice) {
                    val intent = Intent(this@GameScreenActivity, GameActivity::class.java)
                    intent.putExtra("data", data)
                    intent.putExtra("versusMode", true)
                    intent.putExtra("endpointId", endpointId)
                    startActivity(intent)
                }
            }

            override fun askToAcceptTheConnection(endpointId: String) {
                if (!userDevice) {
                    askToAcceptAlert = showAlertToAcceptTheConnection(endpointId)
                    askToAcceptAlert.show()
                } else {
                    nearbyConnectionManager?.acceptTheConnection(endpointId)
                }
            }

            override fun rejectConnection(endpointId: String) {
                if (askToAcceptAlert.isShowing) {
                    askToAcceptAlert.dismiss()
                    adbCreate.dismiss()
                    showAlertWithEndpoints()
                }
                showLongMessage("User $endpointId rejected the connection, try again!")
            }

            override fun connectionError(endpointId: String) {
                showLongMessage("Some Error occurred while connecting to $endpointId")
            }

            override fun requestedTheConnection(endpointId: String) {
                showLongMessage("Requested the connection")
            }

            override fun failedToRequestConnection(endpointId: String) {
                showLongMessage("Failed on request connection")
            }
        },
                object : ConnectionInterface.Device {
                    override fun onDeviceFound(endpointId: String) {
                        showLog("new device found $endpointId")
                        endpointsList.add(endpointId)
                        adapter.notifyDataSetChanged()
                        list.invalidate()
                        progressBar.visibility = View.GONE
                    }

                    override fun onDeviceLost(endpointId: String) {
                        showLog("device lost $endpointId")
                        endpointsList.remove(endpointId)
                        adapter.notifyDataSetChanged()
                        list.invalidate()
                    }
                })
        nearbyConnectionManager?.onDisconnect()
        requestRuntimePermission()

        list.setOnItemClickListener { _, _, position, _ ->
            progressBar.visibility = View.VISIBLE
            nearbyConnectionManager?.connectToEndpoint(endpointsList[position])
            userDevice = true
        }
        adbCreate.show()
    }

    private fun showAlertToAcceptTheConnection(endpointId: String): AlertDialog {
        val adb = AlertDialog.Builder(this, R.style.AlertWithBlackText)
        adb.setTitle("Connection Request")
        adb.setMessage("Allow in order to connect the second user")
        adb.setPositiveButton("OK") { _, _ ->
            nearbyConnectionManager?.acceptTheConnection(endpointId)
        }
        adb.setNegativeButton("Cancel") { _, _ ->
            nearbyConnectionManager?.rejectConnection(endpointId)
            nearbyConnectionManager?.onDisconnect()
        }
        adb.setCancelable(false)
        return adb.create()
    }

    // retrieves all the game-data from mogcore
    fun getAllDataRecords(): Array<*>? {
        val data = getDataSourceData()
        return Gson().fromJson<Array<ScenariosQuizDataSourceModel.Record>>(data.records)
    }

    private fun navigateToPlayScreen(versusMode: Boolean) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("data", getAllDataRecords())
        intent.putExtra("versusMode", versusMode)
        startActivity(intent)
    }

    private fun navigateToReview() {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom,
                        com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom)
                .add(getFragmentViewId(), ReviewFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun navigateToEvaluation() {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom,
                        com.rigil.mogcorelib.R.anim.slide_in_bottom, com.rigil.mogcorelib.R.anim.slide_out_bottom)
                .add(getFragmentViewId(), EvaluationFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun sendAnalytics(view: String) {
        SGAnalyticsTrackHelper.track().screen("Home/".plus(view)).title(view).with(getTracker())
    }

    private fun getTracker(): Tracker {
        return (application as SGAnalyticsApplication).tracker
    }

    override fun getFragmentViewId(): Int {
        return R.id.frame_gamemenuactivity
    }

    override fun getProgressbar(): ProgressBar {
        return this.findViewById(R.id.progressbar_game_main)
    }

    override fun getAppPackage(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun getAppValidationGroupId(): String {
        return BuildConfig.GROUP_ID
    }

    override fun getAppValidationUrlId(): String {
        return BuildConfig.APP_VALIDATION_TABLE_ID
    }

    override fun isLoadCustomButtons(): Boolean {
        return false
    }

    override fun getBaseUrl(): String {
        return ""// BuildConfig.BASE_URL
    }

    override fun getBaseAuthKey(): String {
        return "" //BuildConfig.AUTH_VALUE
    }


    override fun getProjectName(): String {
        return resources.getString(R.string.app_name)
    }

    override fun isLockableProgressBar(): Boolean {
        return true
    }

    override fun isProgressBarWithProgressUpdates(): Boolean {
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (nearbyConnectionManager != null) {
            nearbyConnectionManager?.onDisconnect()
        }
    }

    override fun setTitleInGame() {
        //  Setting Title of Menu Screen
        val psm = SharedPreferencesManager(this)
        val dataSourceId = psm.dataSourceId()
        val dataSourceName = psm.dataSourceName(dataSourceId)
        tv_gamemenu_title_practice_set.text = dataSourceName
    }

    private fun doesUserHasLocationPermission(): Boolean {
        val result: Int = checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestRuntimePermission() {
        if (!doesUserHasLocationPermission()) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    GameScreenActivity.Companion.REQUEST_ACCESS_COARSE_LOCATION)
        } else {
            nearbyConnectionManager?.onConnect()
        }
    }

    // requesting permission for location, we need this in google nearby api
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PackageManager.PERMISSION_DENIED -> (androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("Need this permission for bluetooth connectivity")
                    .setNeutralButton("Ok") { _, _ ->
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                                    GameScreenActivity.Companion.REQUEST_ACCESS_COARSE_LOCATION)
                        }
                    }
                    .show()
                    .findViewById<TextView>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
            PackageManager.PERMISSION_GRANTED -> {
                nearbyConnectionManager?.onConnect()
            }
        }
    }

    override fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    private fun hideNavBar() {}



    companion object {
        private const val REQUEST_ACCESS_COARSE_LOCATION = 23
    }

}