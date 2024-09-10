package com.moddakir.moddakir.utils

class EnumsStudentLogger {
    enum class LoggerNames {
        intro {
            override fun toString(): String {
                return "Student_IntroScreen"
            }
        },
        login {
            override fun toString(): String {
                return "Student_LoginScreen"
            }
        },
        emailLogin {
            override fun toString(): String {
                return "Student_LoginByEmail"
            }
        },
        facebookLogin {
            override fun toString(): String {
                return "Student_LoginWithFacebook"
            }
        },
        twitterLogin {
            override fun toString(): String {
                return "Student_LoginWithTwitter"
            }
        },
        googleLogin {
            override fun toString(): String {
                return "Student_LoginWithGoogle"
            }
        },
        forgotPassword {
            override fun toString(): String {
                return "Student_ForgotPassword"
            }
        },
        forgotPasswordByEmail {
            override fun toString(): String {
                return "Student_ForgotPasswordByEmail"
            }
        },
        forgotPasswordByPhone {
            override fun toString(): String {
                return "Student_ForgotPasswordByPhone"
            }
        },
        facebookRegister {
            override fun toString(): String {
                return "Student_RegisterWithFacebook"
            }
        },
        twitterRegister {
            override fun toString(): String {
                return "Student_RegisterWithTwitter"
            }
        },
        googleRegister {
            override fun toString(): String {
                return "Student_RegisterWithGoogle"
            }
        },
        displayCompleteSocialDataScreen {
            override fun toString(): String {
                return "Student_DisplayCompleteSocialDataScreen"
            }
        },
        completeSocialData {
            override fun toString(): String {
                return "Student_completeSocialData"
            }
        },
        SignUpSocial {
            override fun toString(): String {
                return "Student_SignUpSocial"
            }
        },
        chooseRegisterMethod {
            override fun toString(): String {
                return "Student_ChooseRegistrationMethod"
            }
        },
        emailRegister {
            override fun toString(): String {
                return "Student_RegisterByEmail"
            }
        },
        registerFirstStep {
            override fun toString(): String {
                return "Student_RegisterByEmailStepOne"
            }
        },
        registerSecondStep {
            override fun toString(): String {
                return "Student_RegisterByEmailStepTwo"
            }
        },
        registerThirdStep {
            override fun toString(): String {
                return "Student_RegisterByEmailStepThree"
            }
        },
        terms {
            override fun toString(): String {
                return "Student_TermsAndConditions"
            }
        },
        successRegistration {
            override fun toString(): String {
                return "Student_SuccessfulRegistration"
            }
        },
        reservation {
            override fun toString(): String {
                return "successful_reservation"
            }
        },
        home {
            override fun toString(): String {
                return "Student_HomeScreen"
            }
        },
        teacherList {
            override fun toString(): String {
                return "Student_TeacherListScreen"
            }
        },
        teacherListManinButton {
            override fun toString(): String {
                return "Student_TeacherListScreen_MainButton"
            }
        },
        teacherListTabBarButton {
            override fun toString(): String {
                return "Student_TeacherListScreen_TabBarButton"
            }
        },
        callRandomTeacher {
            override fun toString(): String {
                return "Student_CallRandomTeacher"
            }
        },
        buyNewPackage {
            override fun toString(): String {
                return "Student_buyNewPackage"
            }
        },
        buyNewPackageList {
            override fun toString(): String {
                return "Student_ByNewPackageScreenList"
            }
        },
        balanceReportScreen {
            override fun toString(): String {
                return "Student_BalanceReportScreen"
            }
        },
        searchForTeacher {
            override fun toString(): String {
                return "Student_SearchForTeacher"
            }
        },
        callTeacherByAudio {
            override fun toString(): String {
                return "Student_CallTeacherByAudio"
            }
        },
        callTeacherByVideo {
            override fun toString(): String {
                return "Student_CallTeacherByVideo"
            }
        },
        teacherProfile {
            override fun toString(): String {
                return "Student_StudentShowTeacherProfile"
            }
        },
        filterTeacherList {
            override fun toString(): String {
                return "Student_FilterTeacherList"
            }
        },
        addTeacherToFavorites {
            override fun toString(): String {
                return "Student_AddTeacherToFavorites"
            }
        },
        removeTeacherFromFavorites {
            override fun toString(): String {
                return "Student_RemoveTeacherFromFavorites"
            }
        },
        showTeacherSchedule {
            override fun toString(): String {
                return "Student_StudentShowTeacherSchedule"
            }
        },
        showTeacherExperience {
            override fun toString(): String {
                return "Student_StudentShowTeacherExperience"
            }
        },
        showTeacherReviews {
            override fun toString(): String {
                return "Student_StudentShowTeacherReviews"
            }
        },
        connectingToTeacherByAudio {
            override fun toString(): String {
                return "Student_ConnectingToTeacherByAudio"
            }
        },
        connectingToTeacherByVideo {
            override fun toString(): String {
                return "Student_ConnectingToTeacherByVideo"
            }
        },
        joiningToTeacherByAudio {
            override fun toString(): String {
                return "Student_JoiningToTeacherByAudio"
            }
        },
        joiningToTeacherByVideo {
            override fun toString(): String {
                return "Student_JoiningToTeacherByVideo"
            }
        },
        failedToConnectWithTeacherByAudio {
            override fun toString(): String {
                return "Student_FailedToConnectWithTeacherByAudio"
            }
        },
        failedToConnectWithTeacherByVideo {
            override fun toString(): String {
                return "Student_FailedToConnectWithTeacherByVideo"
            }
        },
        waitingForTeacherReponseByAudio {
            override fun toString(): String {
                return "Student_WaitingForTeacherReponseByAudio"
            }
        },
        waitingForTeacherReponseByVideo {
            override fun toString(): String {
                return "Student_WaitingForTeacherReponseByVideo"
            }
        },
        finishedAudioCall {
            override fun toString(): String {
                return "Student_StudentFinishedAudioCall"
            }
        },
        finishedVideoCall {
            override fun toString(): String {
                return "Student_StudentFinishedVideoCall"
            }
        },
        rateTeacher {
            override fun toString(): String {
                return "Student_RateTeacher"
            }
        },
        callTeacherByAudio_teacherList {
            override fun toString(): String {
                return "Student_TeacherList_CallTeacherByAudio"
            }
        },
        callTeacherByVideo_teacherList {
            override fun toString(): String {
                return "Student_TeacherList_CallTeacherByVideo"
            }
        },
        callTeacherByAudio_favoriteTeachers {
            override fun toString(): String {
                return "Student_FavoriteTeachersCallTeacherByAudio"
            }
        },
        callTeacherByVideo_favoriteTeachers {
            override fun toString(): String {
                return "Student_FavoriteTeachersCallTeacherByVideo"
            }
        },
        callTeacherByAudio_subscribedTeachers {
            override fun toString(): String {
                return "Student_SubscribedTeachersCallTeacherByAudio"
            }
        },
        callTeacherByVideo_subscribedTeachers {
            override fun toString(): String {
                return "Student_SubscribedTeachersCallTeacherByVideo"
            }
        },
        callTeacherByAudio_teacherList_profile {
            override fun toString(): String {
                return "Student_TeacherListShowProfileCallTeacherByAudio"
            }
        },
        callTeacherByVideo_teacherList_profile {
            override fun toString(): String {
                return "Student_TeacherListShowProfileCallTeacherByVideo"
            }
        },
        callTeacherByAudio_favoriteTeachers_profile {
            override fun toString(): String {
                return "Student_FavoriteTeachersShowProfileCallTeacherByAudio"
            }
        },
        callTeacherByVideo_favoriteTeachers_profile {
            override fun toString(): String {
                return "Student_FavoriteTeachersShowProfileCallTeacherByVideo"
            }
        },
        callTeacherByAudio_subscribedTeachers_profile {
            override fun toString(): String {
                return "Student_SubscribedTeachersShowProfileCallTeacherByAudio"
            }
        },
        callTeacherByVideo_subscribedTeachers_profile {
            override fun toString(): String {
                return "Student_SubscribedTeachersShowProfileCallTeacherByVideo"
            }
        },
        notifications {
            override fun toString(): String {
                return "Student_StudentNotificationsScreen"
            }
        },
        removeNotification {
            override fun toString(): String {
                return "Student_StudentRemoveNotification"
            }
        },
        sessions {
            override fun toString(): String {
                return "Student_SessionsScreen"
            }
        },
        playAudioRecord {
            override fun toString(): String {
                return "Student_StudentPlayAudioRecord"
            }
        },
        playVideoRecord {
            override fun toString(): String {
                return "Student_StudentPlayVideoRecord"
            }
        },
        sessionDetails {
            override fun toString(): String {
                return "Student_StudentSessionDetailsScreen"
            }
        },
        showReportScreen {
            override fun toString(): String {
                return "Student_StudentShowReportScreen"
            }
        },
        reportTeacher {
            override fun toString(): String {
                return "Student_ReportTeacher"
            }
        },
        removeSession {
            override fun toString(): String {
                return "Student_StudentRemoveSession"
            }
        },
        repurchasePackage {
            override fun toString(): String {
                return "Student_RepurchasePackage"
            }
        },
        usePromoCode {
            override fun toString(): String {
                return "Student_UsePromoCode"
            }
        },
        paymentInfo {
            override fun toString(): String {
                return "Student_PaymentInfoScreen"
            }
        },
        paymentScreen {
            override fun toString(): String {
                return "Student_PaymentScreen"
            }
        },
        paymentSuccess {
            override fun toString(): String {
                return "Student_PaymentSuccess"
            }
        },
        paymentFailed {
            override fun toString(): String {
                return "Student_PaymentFailed"
            }
        },
        shareApp {
            override fun toString(): String {
                return "Student_StudentShareApp"
            }
        },
        aboutApp {
            override fun toString(): String {
                return "Student_StudentAboutApp"
            }
        },
        contactUs {
            override fun toString(): String {
                return "Student_StudentContactUs"
            }
        },
        logout {
            override fun toString(): String {
                return "Student_StudentLogout"
            }
        },
        changeLanguage {
            override fun toString(): String {
                return "Student_StudentChangeLanguage"
            }
        },
        help {
            override fun toString(): String {
                return "Student_HelpScreen"
            }
        },
        myProfile {
            override fun toString(): String {
                return "Student_StudentMyProfile"
            }
        },
        editMyProfile {
            override fun toString(): String {
                return "Student_StudentEditMyProfile"
            }
        },
        packageHistory {
            override fun toString(): String {
                return "Student_PackageHistoryScreen"
            }
        },
        oldPackageDetails {
            override fun toString(): String {
                return "Student_OldPackageDetailsScreen"
            }
        },
        manageDependents {
            override fun toString(): String {
                return "Student_ManageDependentsScreen"
            }
        },
        addDependent {
            override fun toString(): String {
                return "Student_AddDependent"
            }
        },
        addBalanceToDependent {
            override fun toString(): String {
                return "Student_AddBalanceToDependent"
            }
        },
        removeDependent {
            override fun toString(): String {
                return "Student_RemoveDependent"
            }
        },
        getBalanceFromDependent {
            override fun toString(): String {
                return "Student_GetBalanceFromDependent"
            }
        },
        dependentProfile {
            override fun toString(): String {
                return "Student_DependentProfileScreen"
            }
        },
        editDependent {
            override fun toString(): String {
                return "Student_EditDependentScreen"
            }
        },
        balanceTransfer {
            override fun toString(): String {
                return "Student_BalanceTransferScreen"
            }
        },
        transferBalanceToFriedns {
            override fun toString(): String {
                return "Student_TransferBalanceToFriedns"
            }
        },
        transferBalanceToScholarship {
            override fun toString(): String {
                return "Student_TransferBalanceToScholarship"
            }
        },
        planList {
            override fun toString(): String {
                return "Student_PlanListScreen"
            }
        },
        deletePlan {
            override fun toString(): String {
                return "Student_DeletePlan"
            }
        },
        deletePath {
            override fun toString(): String {
                return "Student_DeletePath"
            }
        },
        pathDetails {
            override fun toString(): String {
                return "Student_PathDetailsScreen"
            }
        },
        addPlan1 {
            override fun toString(): String {
                return "Student_AddPlanStepOne"
            }
        },
        addPlan2 {
            override fun toString(): String {
                return "Student_AddPlanStepTwo"
            }
        },
        planAdded {
            override fun toString(): String {
                return "Student_PlanAddedSuccessfully"
            }
        },
        planUpdated {
            override fun toString(): String {
                return "Student_PlanUpdatedSuccessfully"
            }
        },
        calling {
            override fun toString(): String {
                return "Student_Calling"
            }
        },
        student_cancel_session {
            override fun toString(): String {
                return "student_CancelSession"
            }
        },
        session_availability_check {
            override fun toString(): String {
                return "Student_SessionAvailabilityCheck"
            }
        },
        schedule_session {
            override fun toString(): String {
                return "Student_ScheduleSession"
            }
        },
        Add_Dependent {
            override fun toString(): String {
                return "Student_AddDependent"
            }
        },
        App_Launch {
            override fun toString(): String {
                return "AppStarted"
            }
        }
    }
}