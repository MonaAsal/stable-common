package com.moddakir.moddakir.ui.bases.authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.moddakirapps.R
import com.example.moddakirapps.databinding.ActivityLoginStudentBinding
import com.example.moddakirapps.databinding.ActivityRegisterMobileBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.CallbackManager.Factory.create
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.android.recaptcha.RecaptchaAction
import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.hbb20.CountryCodePicker
import com.moddakir.moddakir.App
import com.moddakir.moddakir.helper.LocaleHelper
import com.moddakir.moddakir.network.model.RecaptchaImpl.Companion.recaptchaTasksClient
import com.moddakir.moddakir.network.Session.saveSocialMediaType
import com.moddakir.moddakir.network.model.base.BaseActivity
import com.moddakir.moddakir.ui.widget.ButtonUniqueLight
import com.moddakir.moddakir.ui.widget.TextViewUniqueLight
import com.moddakir.moddakir.utils.ValidationUtils
import com.moddakir.moddakir.viewModel.AutViewModel
import java.util.Objects

abstract class SocialMedialActivity : BaseActivity() {
    private var RC_SIGN_IN: Int = 12
    private var TWITTER_SIGN_IN_CODE: Int = 140
    var providerUserId: String? = null
    var userphone: String = ""
    var providerType: String? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var callbackManager: CallbackManager? = null
    private var emailFromSocial: String = ""
    var type: String? = null
    private var faceBookActivityResult: ActivityResultLauncher<Intent>? = null
    private var googleActivityResult: ActivityResultLauncher<Intent>? = null
    private val authViewModel: AutViewModel by viewModels()
    private lateinit var binding: ActivityRegisterMobileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_social_medial)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestProfile()
            .requestEmail()
            .requestIdToken(resources.getString(R.string.serverclientid))
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        callbackManager = create()
        App.bindingStudent= ActivityLoginStudentBinding.inflate(layoutInflater)

        App.bindingStudent.btnFacebook.setOnClickListener{ { v: View? ->
            type = "RegisterSocial"
            if (getIntent().getBooleanExtra("isFromAccounts", false)) {
                logout()
            }
            LoginManager.getInstance()
                .logInWithReadPermissions(
                    this@SocialMedialActivity,
                    mutableListOf("public_profile", "email")
                )
            LoginManager.getInstance()
                .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    @SuppressLint("LogNotTimber")
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("Success", "Login")
                        handleFacebookAccessToken(loginResult.accessToken)
                    }

                    override fun onCancel() {
                        Toast.makeText(
                            this@SocialMedialActivity,
                            "Login Cancel",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(exception: FacebookException) {
                        Toast.makeText(
                            this@SocialMedialActivity,
                            exception.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                )
        }}
        App.bindingStudent.btnGoogle.setOnClickListener {
            if (getIntent().getBooleanExtra("isFromAccounts", false)) {
                logout()
            }
            type = "RegisterSocial"
            val signInIntent = mGoogleSignInClient!!.signInIntent
            googleActivityResult!!.launch(signInIntent)
        }
        App.bindingStudent.twitter.setOnClickListener {
            loginTwitter()
        }
    }

    @SuppressLint("LogNotTimber")
    private fun loginTwitter() {
        FirebaseAuth.getInstance().signOut()
        type = "RegisterSocial"
        CookieManager.getInstance().removeAllCookies { aBoolean: Boolean ->
            Log.d("TWITTER_COOKIE", "Cookie removed: $aBoolean")
        }
        if (getIntent().getBooleanExtra("isFromAccounts", false)) {
            FirebaseAuth.getInstance().signOut()
            logout()
        }
        twitterLoginHandler()
    }

    private fun twitterLoginHandler() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val pendingResultTask = firebaseAuth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult> { authResult ->
                        authViewModel.signInWithSocial(
                            Objects.requireNonNull<AdditionalUserInfo?>(authResult.additionalUserInfo).profile!!["email"].toString(),
                            authResult.additionalUserInfo!!.username!!,
                            Objects.requireNonNull<FirebaseUser?>(authResult.user).uid.toString(),
                            "",
                            "",
                            "Twitter",
                            (Objects.requireNonNull<AuthCredential?>(authResult.credential) as OAuthCredential).accessToken!!,
                            LocaleHelper.getLocale(this).toString(),
                            applicationContext
                        )
                        emailFromSocial = Objects.requireNonNull(
                            authResult.additionalUserInfo!!.profile!!["email"]
                        ).toString()
                    })
                .addOnFailureListener { e -> // Handle failure.
                    Toast.makeText(this@SocialMedialActivity, e.message, Toast.LENGTH_LONG)
                        .show()
                    e.message
                }
        } else {
            val provider = OAuthProvider.newBuilder("twitter.com")
            provider.addCustomParameter("lang", "en")
            firebaseAuth.startActivityForSignInWithProvider( this, provider.build())
                .addOnSuccessListener { authResult ->
                    authViewModel.signInWithSocial(
                        Objects.requireNonNull<AdditionalUserInfo?>(authResult.additionalUserInfo).profile!!["email"].toString(),
                        authResult.additionalUserInfo!!.username!!,
                        Objects.requireNonNull<FirebaseUser?>(authResult.user).uid.toString(),
                        "",
                        "",
                        "Twitter",
                        (Objects.requireNonNull<AuthCredential?>(authResult.credential) as OAuthCredential).accessToken!!,
                        LocaleHelper.getLocale(this).toString(),
                        applicationContext
                    )
                    emailFromSocial = Objects.requireNonNull(
                        authResult.additionalUserInfo!!.profile!!["email"]
                    ).toString()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this@SocialMedialActivity,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                    e.message
                }
        }
    }

    private fun logout() {
        authViewModel.requestLogout()
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val mAuth = FirebaseAuth.getInstance()
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    FirebaseAuth.getInstance()
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    authViewModel.signInWithSocial(
                        user!!.email!!, user.displayName!!,
                        user.uid,
                        "",
                        "",
                        "Facebook",
                        token.token,
                        LocaleHelper.getLocale(this).toString(), applicationContext
                    )
                    emailFromSocial = user!!.email!!
                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

    @SuppressLint("LogNotTimber")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("ON_ACT_RESULT", "$requestCode|$resultCode")
        if (requestCode != TWITTER_SIGN_IN_CODE) {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
        googleActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.data != null) {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(
                        result.data
                    )
                handleSignInResult(task)
            }
        }

        faceBookActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.data != null) {
                callbackManager!!.onActivityResult(
                    RC_SIGN_IN,
                    result.resultCode,
                    result.data
                )
            }
        }
    }

    @SuppressLint("LogNotTimber")
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                var avatar = ""
                var token: String? = ""
                if (account.idToken != null) token = account.idToken
                if (account.photoUrl != null) avatar = account.photoUrl.toString()
                emailFromSocial = account.email!!
                authViewModel.signInWithSocial(
                    account.email!!,
                    account.displayName!!, account.id!!,
                    "", avatar, "Google", token!!, LocaleHelper.getLocale(this).toString(),
                    this
                )
            }
        } catch (e: ApiException) {
            Log.e("GoogleSignInAccount", "signInResult:failed code=" + e.statusCode)
        } catch (e: Exception) {
            Log.e("GoogleSignInAccount", "signInResult:failed code=$e")
        }
    }

    private fun checkValidation(
        emailLayout: TextInputLayout,
        countryCodePicker: CountryCodePicker,
        phoneTil: TextInputLayout
    ): Boolean {
        val validationUtils= ValidationUtils()
        val isValidEmail: Boolean =
            validationUtils.isEmailValid(emailLayout.editText!!.text.toString())
        val isValidPhone = countryCodePicker.isValidFullNumber

        if (!isValidPhone) {
            phoneTil.error = resources.getString(R.string.mobile_number_required)
            phoneTil.isErrorEnabled = true
        } else phoneTil.isErrorEnabled = false


        if (!isValidEmail) {
            emailLayout.error = resources.getString(R.string.email_invalid)
            emailLayout.isErrorEnabled = true
        } else {
            emailLayout.isErrorEnabled = false
        }

        return isValidEmail && isValidPhone
    }
    private fun showCompleteSocialDialog() {

        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.complete_social_dialog, null)
        val signUp: ButtonUniqueLight = dialogView.findViewById(R.id.btn_sign_up)
        val cancelBut: ButtonUniqueLight = dialogView.findViewById(R.id.cancel_but)
        val genderErrorTv: TextViewUniqueLight = dialogView.findViewById(R.id.type_error_tv)
        val typeRg = dialogView.findViewById<RadioGroup>(R.id.type_rg)
        val emailLayout = dialogView.findViewById<TextInputLayout>(R.id.email_til)
        if (emailFromSocial != null) {
            if (!emailFromSocial.isEmpty()) {
                emailLayout.editText!!.setText(emailFromSocial)
                emailLayout.visibility = View.GONE
            } else {
                emailLayout.visibility = View.VISIBLE
            }
        } else {
            emailLayout.visibility = View.VISIBLE
        }

        val phoneTil = dialogView.findViewById<TextInputLayout>(R.id.phone_tile)
        val countryCodePicker = dialogView.findViewById<CountryCodePicker>(R.id.countryCodePicker)

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        val completeSignUpDialog = dialogBuilder.create()
        completeSignUpDialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        cancelBut.setOnClickListener {  completeSignUpDialog.dismiss() }
        signUp.setOnClickListener {
            countryCodePicker.registerCarrierNumberEditText(phoneTil.editText)
            if (typeRg.checkedRadioButtonId == -1) {
                genderErrorTv.setText(R.string.type_required)
            } else {
                val type: String = authViewModel.getGenderType(typeRg.checkedRadioButtonId)!!
                if (checkValidation(emailLayout, countryCodePicker, phoneTil)) {
                    val email =
                        Objects.requireNonNull(emailLayout.editText)!!.text.toString().trim { it <= ' ' }
                    userphone = countryCodePicker.fullNumberWithPlus
                    saveSocialMediaType( type)
                    if (recaptchaTasksClient != null) {
                        recaptchaTasksClient!!.executeTask(RecaptchaAction.SIGNUP)
                            .addOnSuccessListener(OnSuccessListener<String?> { token ->
                                authViewModel.loginWithMobile(
                                    userphone,
                                    token,
                                    true
                                )
                            }).addOnFailureListener(
                                this@SocialMedialActivity
                            ) { e -> }
                    }
                }
               // if (authViewModel.socialMap.containsKey("")) completeSignUpDialog.dismiss()
            }
        }
        completeSignUpDialog.show()
    }


}