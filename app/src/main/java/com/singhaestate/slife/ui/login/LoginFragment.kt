package com.singhaestate.slife.ui.login


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import com.singhaestate.slife.R
import com.singhaestate.slife.manager.local.SharePrefManager
import com.singhaestate.slife.manager.local.UserModel
import com.singhaestate.slife.manager.remote.service.ApiService
import com.singhaestate.slife.manager.remote.service.ServiceManager
import com.singhaestate.slife.manager.remote.service.model.LoginFacebookRequest
import com.singhaestate.slife.manager.remote.service.model.LoginRequest
import com.singhaestate.slife.ui.base.BaseFragment
import com.singhaestate.slife.ui.mainmenu.MainMenuActivity
import com.singhaestate.slife.ui.register.RegisterActivity
import com.singhaestate.slife.util.DialogHelper
import com.singhaestate.slife.util.ToastDialog
import com.singhaestate.slife.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.ResponseBody
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.newTask
import org.json.JSONObject
import java.util.*


class LoginFragment : BaseFragment() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        graphMeFacebook(loginResult = loginResult)
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initInstances() {
        rootLayout.requestFocus()

        btnLogin.setOnClickListener {
            startActivity(Intent(this@LoginFragment.activity, MainMenuActivity::class.java).newTask().clearTop().clearTask())

//            val userName = txtUsername.text.toString().trim()
//            var password = txtPassword.text.toString().trim()
//
//            if (userName.isEmpty()) {
//                ToastDialog.showLongToast("กรุณาใส่ชื่อผู้ใช้งาน", this@LoginFragment.activity as Activity)
//                return@setOnClickListener
//            }
//            if (password.isEmpty()) {
//                ToastDialog.showLongToast("กรุณาใส่รหัสผ่าน", this@LoginFragment.activity as Activity)
//                return@setOnClickListener
//            }
//
//            val uniqueID = if (SharePrefManager.getStringFromPref("deviceId").isNotEmpty()) {
//                SharePrefManager.getStringFromPref("deviceId")
//            } else {
//                UUID.randomUUID().toString()
//            }
//
//            val login = LoginRequest(clientId = ServiceManager.CLIENT_ID,
//                    clientSecret = ServiceManager.CLIENT_SECRET,
//                    scope = ServiceManager.USER_SCOPE,
//                    username = userName,
//                    password = password,
//                    app_version = Utils.getAppVersion(this@LoginFragment.activity!!),
//                    device_token = uniqueID,
//                    facebook_id = null,
//                    language = Utils.getLocale(context = this.context!!),
//                    os_type = "ANDROID",
//                    noti_token = "",
//                    os_version = Build.VERSION.RELEASE,
//                    user_agent = "Android"
//            )
//
//            val apiService: ApiService = ServiceManager.service
//            DialogHelper.showProgressDialog(context = this.activity!!)
//            apiService.login(login)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(handleSuccess, handleError, handleComplete)
//
        }

        btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginFragment.activity, RegisterActivity::class.java))
        }
    }

    private fun graphMeFacebook(loginResult: LoginResult) {

        GraphRequest.newMeRequest(loginResult.accessToken) { `object`, _ ->
            val fbId = `object`["id"] as String
            loginFacebook(facebookId = fbId, accessToken = loginResult.accessToken.token)
        }
    }

    private fun loginFacebook(facebookId: String, accessToken: String) {
        val login = LoginFacebookRequest(clientId = ServiceManager.CLIENT_ID,
                clientSecret = ServiceManager.CLIENT_SECRET,
                scope = ServiceManager.USER_SCOPE,
                facebook_id = facebookId,
                accessToken = accessToken
        )

        val apiService: ApiService = ServiceManager.service
        DialogHelper.showProgressDialog(context = this.activity!!)
        apiService.loginFacebook(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleSuccess, handleError, handleComplete)
    }

    private val handleComplete = Action {
        DialogHelper.hideProgressDialog()
    }

    private val handleSuccess = Consumer<ResponseBody> {
        val json = JSONObject(it.string())
        val status = json.getString("code")

        if (status == "success") {
            // {"code":"success","message":"Success","meta":{},"data":{"id":136,"total_points":0,"notification_count":0,"notification_opened_count":0,"status":"ACTIVE","first_name":"Theera","last_name":"Thanawadee","local_first_name":null,"local_last_name":null,"identification":"","mobile_phone":"0812345677","birthdate":"2000-01-01","email":"theera@pirsquare.net","notification_enabled_email":1,"notification_enabled":1,"consumer_tier_id":null,"scrm_id":6349,"prospect_id":null,"verify":1,"full_name":"Theera Thanawadee","full_address":null,"profile_picture_url":null,"profile_thumbnail_url":null,"points_expiring":0,"facebook_id":null,"points":0,"user_name":"theera","expiring":[{"date":"2018-07-31","points":0},{"date":"2018-08-31","points":0},{"date":"2018-09-30","points":0},{"date":"2018-10-31","points":0}],"primary_address":null,"profile_picture":null,"profile_thumbnail":null,"token":{"refresh_token":"fHOL5zLMLHWWGhcZ92ySPkrkSU0FSbDI","token_type":"bearer","access_token":"ntQPYrf1Zcrm35yJ2BxUvoI9ZVIBWcnC","expires_in":7200}}}
            val data = json.getString("data")
            val user = Gson().fromJson(data, UserModel::class.java)
            SharePrefManager.setUserToPref(user)
            startActivity(Intent(this@LoginFragment.activity, MainMenuActivity::class.java).newTask().clearTop().clearTask())
        } else {

            val items = json.getString("code")
            ToastDialog.showLongToast(items, activity = this.activity!!)
        }
    }

    private val handleError = Consumer<Throwable> {
        DialogHelper.hideProgressDialog()
        Utils.showErrorDialog(Utils.isInternetAvailable, activity = this.activity!!)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment().apply {
            arguments = Bundle().apply { }
        }
    }

}
