package com.moddakir.moddakir.useCase

import com.moddakir.moddakir.utils.ValidationUtils
import com.moddakir.moddakir.ui.bases.authentication.ForgetPasswordActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginActivity
import com.moddakir.moddakir.ui.bases.authentication.LoginWithMobileActivity
import com.moddakir.moddakir.ui.bases.authentication.ResetPasswordActivity
import com.moddakir.moddakir.ui.bases.authentication.VerificationMobileActivity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class AuthUseCaseTest {
    private lateinit var  validationUtils: ValidationUtils
    @Before
    fun setUp(){
         validationUtils=ValidationUtils()
    }
    @Test
    fun login(): Unit = runBlocking{
        val username=LoginActivity.username
        val password=LoginActivity.password
        val lang=LoginActivity.lang
        if(username.isEmpty()||password.isEmpty()||lang.isEmpty()){
            assertTrue(false)
        }
       if(!validationUtils.validusername(username)||!validationUtils.isValidPassword(password)){
           assertTrue(false)
       }
        else{
            assertTrue(true)
        }
    }
    @Test
    fun loginTeacher() {
        val username=LoginActivity.username
        val password=LoginActivity.password
        val lang=LoginActivity.lang
        if(username.isEmpty()||password.isEmpty()||lang.isEmpty()){
            assertTrue(false)
        }
        if(!validationUtils.validusername(username)||!validationUtils.isValidPassword(password)){
            assertTrue(false)
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun forgetPassword() {
        val email=ForgetPasswordActivity.email
        val phone=ForgetPasswordActivity.phone
        val lang=ForgetPasswordActivity.lang
        val token=ForgetPasswordActivity.tokenRecaptcha
        if(email.isEmpty()&&phone.isEmpty()&&lang.isEmpty()){
            assertTrue(false)
        }
        else if(email.isNotEmpty()&&!validationUtils.isEmailValid(email)){
            assertTrue(false)
        }
        else if(phone.isNotEmpty()&&!validationUtils.isValidMobile(phone)){
            assertTrue(false)
        }
        else if(phone.isNotEmpty()&&token.isNullOrEmpty()){
            assertTrue(false)
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun sendPhoneVerifyNum() {
        val phone=LoginWithMobileActivity.phone
        val token=LoginWithMobileActivity.tokenRecaptcha
        if(phone.isEmpty()||token.isNullOrEmpty()){
            assertTrue(false)
        }
        else if(phone.isNotEmpty()&&!validationUtils.isValidMobile(phone)){
            assertTrue(false)
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun loginWithMobile() {
        val phone=LoginWithMobileActivity.phone
        val token=LoginWithMobileActivity.tokenRecaptcha
        if(phone.isEmpty()||token.isNullOrEmpty()){
            assertTrue(false)
        }
        else if(phone.isNotEmpty()&&!validationUtils.isValidMobile(phone)){
            assertTrue(false)
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun validateOTP() {
        val code= VerificationMobileActivity.code
        val providerType=VerificationMobileActivity.providerType
        val providerUserId=VerificationMobileActivity.providerUserId
        val isSocialMediaAccountPending=VerificationMobileActivity.isSocialMediaAccountPending
        if(code.isEmpty()){
            assertTrue(false)
        }
        else if(isSocialMediaAccountPending!=null){
            if(isSocialMediaAccountPending&&providerType.isNullOrEmpty()||isSocialMediaAccountPending&&providerUserId.isNullOrEmpty()){
                assertTrue(false)
            }
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun validatePhoneNumber() {
        val code= VerificationMobileActivity.code
        val providerType=VerificationMobileActivity.providerType
        val providerUserId=VerificationMobileActivity.providerUserId
        val isSocialMediaAccountPending=VerificationMobileActivity.isSocialMediaAccountPending
        if(code.isEmpty()){
            assertTrue(false)
        }
        else if(isSocialMediaAccountPending!=null){
            if(isSocialMediaAccountPending&&providerType.isNullOrEmpty()||isSocialMediaAccountPending&&providerUserId.isNullOrEmpty()){
                assertTrue(false)
            }
        }
        else{
            assertTrue(true)
        }
    }

    @Test
    fun resetPassword() {
        val password=ResetPasswordActivity.password
        if(password.isEmpty()){
            assertTrue(false)
        }
        if(!validationUtils.isValidPassword(password)){
            assertTrue(false)
        }
        else{
            assertTrue(true)
        }
    }
}