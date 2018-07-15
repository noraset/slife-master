package com.singhaestate.slife.ui.register


import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singhaestate.slife.R
import com.singhaestate.slife.manager.local.SharePrefManager
import com.singhaestate.slife.manager.remote.service.ApiService
import com.singhaestate.slife.manager.remote.service.ServiceManager
import com.singhaestate.slife.manager.remote.service.model.RegisterRequest
import com.singhaestate.slife.ui.base.BaseFragment
import com.singhaestate.slife.util.DialogHelper
import com.singhaestate.slife.util.ToastDialog
import com.singhaestate.slife.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*


class RegisterFragment : BaseFragment() {

    private val mCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"), Locale.US)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_register, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstances()
    }

    private fun initInstances() {
        rootLayout.requestFocus()

        btnNext.setOnClickListener {
            val email = txtEmail.text.trim().toString()
            val username = txtUsername.text.trim().toString()
            val password = txtPassword.text.trim().toString()
            val mobile = txtMobile.text.trim().toString()
            val name = txtName.text.trim().toString()
            val lastName = txtLastname.text.trim().toString()
            val dob = txtDob.text.trim().toString()

            if (email.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่อีเมล์", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่ชื่อผู้ใช้งาน", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่รหัสผ่าน", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (name.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่ชื่อ", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่นามสกุล", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (mobile.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่เบอร์โทรศัพท์", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }
            if (dob.isEmpty()) {
                ToastDialog.showLongToast("กรุณาใส่วันเดือนปีเกิด", this@RegisterFragment.activity as Activity)
                return@setOnClickListener
            }

            val uniqueID = if (SharePrefManager.getStringFromPref("deviceId").isNotEmpty()) {
                SharePrefManager.getStringFromPref("deviceId")
            } else {
                UUID.randomUUID().toString()
            }

            val model = RegisterRequest(clientId = ServiceManager.CLIENT_ID,
                    clientSecret = ServiceManager.CLIENT_SECRET,
                    scope = ServiceManager.USER_SCOPE,
                    username = username,
                    password = password,
                    first_name = name,
                    last_name = lastName,
                    birthdate = dob,
                    email = email,
                    notification_enabled = true,
                    notification_enabled_email = true,
                    language = Utils.getLocale(this.context!!),
                    app_version = Utils.getAppVersion(this.context!!),
                    os_version = android.os.Build.VERSION.RELEASE,
                    user_agent = "android",
                    noti_token = "",
                    os_type = "ANDROID",
                    device_token = uniqueID,
                    identification = "",
                    facebook_id = null,
                    mobile_phone = mobile
            )

            val apiService: ApiService = ServiceManager.service
            DialogHelper.showProgressDialog(context = this.activity!!)
            apiService.register(model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(handleSuccess, handleError, handleComplete)

        }

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            mCalendar.set(Calendar.YEAR, year)
            mCalendar.set(Calendar.MONTH, monthOfYear)
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDobLabel()
        }

        txtDob.setOnClickListener {
            Utils.hideKeyboard(this@RegisterFragment.context)
            DatePickerDialog(context, dateListener, mCalendar[Calendar.YEAR], mCalendar[Calendar.MONTH], mCalendar[Calendar.DAY_OF_MONTH]).show()
        }
    }

    private fun updateDobLabel() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        txtDob.setText(sdf.format(mCalendar.time))
    }

    private val handleComplete = Action {
        DialogHelper.hideProgressDialog()
    }

    private val handleSuccess = Consumer<ResponseBody> {
        val json = JSONObject(it.string())
        val code = json.getString("code")

        if (code == "BadRequest") {
            val message = json.getString("message")
            ToastDialog.showLongToast(message, this@RegisterFragment.activity as Activity)
        } else {
        }
    }

    private val handleError = Consumer<Throwable> {
        DialogHelper.hideProgressDialog()

        val error = it as HttpException
        val errorBody = error.response().errorBody()?.string()
        val json = JSONObject(errorBody)
        val code = json.getString("code")
        if (code == "BadRequest") {
            val message = json.getString("message")
            ToastDialog.showLongToast(message, this@RegisterFragment.activity as Activity)
        } else {
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                RegisterFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
