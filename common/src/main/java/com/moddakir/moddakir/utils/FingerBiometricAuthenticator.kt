package com.moddakir.moddakir.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import co.infinum.goldfinger.Goldfinger
import co.infinum.goldfinger.Goldfinger.PromptParams
import com.example.moddakirapps.BuildConfig
import com.example.moddakirapps.R
import com.moddakir.moddakir.adapter.AccountsAdapter
import com.moddakir.moddakir.helper.SavedFingerAccountsPreferences
import com.moddakir.moddakir.model.SavedFingerAccount
import com.moddakir.moddakir.ui.bases.listeners.RecyclerItemListener
import com.moddakir.moddakir.ui.widget.ButtonCalibriBold
import timber.log.Timber

abstract class FingerBiometricAuthenticator : RecyclerItemListener<SavedFingerAccount> {
    private var goldfinger: Goldfinger? = null
    val dialogBuilder = AlertDialog.Builder(getContext())
    val alertDialog1 = dialogBuilder.create()
private var  savedFingerAccountsPreferences: SavedFingerAccountsPreferences =
    SavedFingerAccountsPreferences()
    fun initFingerAuth() {
        goldfinger = Goldfinger.Builder(getContext()).logEnabled(BuildConfig.DEBUG).build()
    }

    fun canAuthenticate(): Boolean {
        return goldfinger!!.hasFingerprintHardware() && goldfinger!!.hasEnrolledFingerprint()
    }

    fun getPromInfo(): PromptParams {
        return PromptParams.Builder(getHostActivity())
            .title(getContext().resources.getString(R.string.BiometricPromptTitle))
            .subtitle(getContext().resources.getString(R.string.BiometricPromptSubtitle))
            .negativeButtonText(getContext().resources.getString(R.string.cancel))
            .build()
    }

    fun authenticate() {
        goldfinger!!.authenticate(getPromInfo(), object : Goldfinger.Callback {
            override fun onError(e: Exception) {
                Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.auth_error),
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResult(result: Goldfinger.Result) {
                if (result.type() == Goldfinger.Type.SUCCESS) onBiometricSuccess()
            }
        })
    }


    private fun onBiometricSuccess() {
        try {
//            Pair<Set<String>, Set<String>> userLog = SavedFingerAccountsPreferences.getUserLogin(getContext());
            showUsersDialog()
        } catch (e: Exception) {
            Timber.e("onBiometricSuccess $e")
        }
    }

    private fun showUsersDialog() {
        val inflater = getHostActivity().layoutInflater
        val giftDialogView: View = inflater.inflate(R.layout.storedusers, null)
        val AccountsRecyclerView = giftDialogView.findViewById<RecyclerView>(R.id.accounts)
        val layoutManager = LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        AccountsRecyclerView.layoutManager = layoutManager
        val btn_continue: ButtonCalibriBold = giftDialogView.findViewById(R.id.btn_next)
        val dialogBuilder = AlertDialog.Builder(getContext())
        dialogBuilder.setView(giftDialogView)
        val alertDialog1 = dialogBuilder.create()
        alertDialog1.window!!.setBackgroundDrawableResource(R.color.transparent)
        btn_continue.setOnClickListener { v ->
            alertDialog1.dismiss()
        }

        val accountsAdapter = AccountsAdapter (this)
        AccountsRecyclerView.adapter = accountsAdapter
        alertDialog1.show()
    }


    protected fun showDialogForApproveRegisterAccount(email: String?, password: String?) {
        val pDialog: SweetAlertDialog = SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
        pDialog.setTitleText(getContext().resources.getString(R.string.stoerAccount))
        pDialog.setContentText(getContext().resources.getString(R.string.storeAccountMessage))
        pDialog.setCancelable(false)
        pDialog.setConfirmText(getContext().resources.getString(R.string.save))
        pDialog.setCancelText(getContext().resources.getString(R.string.cancel))
        pDialog.setCancelClickListener { sweetAlertDialog ->
            pDialog.dismissWithAnimation()
            moveToNext()
        }
        pDialog.setConfirmClickListener { sweetAlertDialog ->
            try {

                savedFingerAccountsPreferences.saveAccount(SavedFingerAccount(email!!, password!!))
            } catch (ex: Exception) {
                Timber.e("ExceptionInRegister %s", ex.toString())
            } finally {
                pDialog.dismissWithAnimation()
                moveToNext()
            }
        }
        pDialog.show()
    }

    abstract fun getContext(): Context

    abstract fun moveToNext()

    abstract fun getHostActivity(): FragmentActivity

    abstract fun setAccountData(email: String?, password: String?)
    override fun onItemSelected(account: SavedFingerAccount) {
        setAccountData(account.userName, account.password)
        alertDialog1.dismiss()

    }

}