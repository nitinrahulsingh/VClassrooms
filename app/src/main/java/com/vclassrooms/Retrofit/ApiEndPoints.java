package com.vclassrooms.Retrofit;



import com.vclassrooms.Entity.AddEbookDetailsResponse;
import com.vclassrooms.Entity.AddGalleryResponse;
import com.vclassrooms.Entity.AddOnlineLecResponse;
import com.vclassrooms.Entity.AnnouncementDetailResponse;
import com.vclassrooms.Entity.AnnouncementEnum;
import com.vclassrooms.Entity.ApplyLeaveResponse;
import com.vclassrooms.Entity.AssignmentResponse;
import com.vclassrooms.Entity.ChangePasswordRequest;
import com.vclassrooms.Entity.ClasTimeTableResponse;
import com.vclassrooms.Entity.CommonSuccessResponse;
import com.vclassrooms.Entity.EbookDetailsResponse;
import com.vclassrooms.Entity.ExamTimeTableResponse;
import com.vclassrooms.Entity.GalleryDetailResponse;
import com.vclassrooms.Entity.HolidayDetailResponse;
import com.vclassrooms.Entity.LeaveDeatailsResponse;
import com.vclassrooms.Entity.LeaveRequest;
import com.vclassrooms.Entity.LeaveTypeResponse;
import com.vclassrooms.Entity.LoginResponse;
import com.vclassrooms.Entity.MarkAttendanceEnum;
import com.vclassrooms.Entity.OnlineExamEnum;
import com.vclassrooms.Entity.OnlineExaminationResponse;
import com.vclassrooms.Entity.OnlineLectureDetailsResponse;
import com.vclassrooms.Entity.ParentDirectoryResponse;
import com.vclassrooms.Entity.ProfileDetailInsertResponse;
import com.vclassrooms.Entity.ProfileResponse;
import com.vclassrooms.Entity.SchoolResponse;
import com.vclassrooms.Entity.StandardDivisionResponse;
import com.vclassrooms.Entity.StandardListResponse;
import com.vclassrooms.Entity.StudentAttendanceDetailResponse;
import com.vclassrooms.Entity.StudentDirectoryResponse;
import com.vclassrooms.Entity.StudentListResponse;
import com.vclassrooms.Entity.SubjectDetailsResponse;
import com.vclassrooms.Entity.TeacherDirectoryResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiEndPoints {

    //Login Details
    @GET("Login")
    Call<LoginResponse> getLoginDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("vchUser_name") String vchUser_name,
            @Query("vchPassword") String vchPassword,
            @Query("intSchool_id") String intSchool_id,
            @Query("FCMToken") String FCMToken
    );

    //School Details
    @GET("Login")
    Call<SchoolResponse> getSchoolDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command
    );

    //Profile Details
    @GET("Profile")
    Call<ProfileResponse> getProfileDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );

    //Profile Details Insert
    @Multipart
    @POST("Profile")
    Call<ProfileDetailInsertResponse> onProfileChanges(
            @Part MultipartBody.Part file,
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("vchProfile") String vchProfile,
            @Query("First_Name") String First_Name,
            @Query("Mobile_No") String Mobile_No,
            @Query("Email_Id") String Email_Id,
            @Query("Address") String Address
    );

    //Student Directory Details
    @GET("Directory")
    Call<StudentDirectoryResponse> getStudentDirDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );

    //Teacher Directory detail
    @GET("Directory")
    Call<TeacherDirectoryResponse> getTeacherDirDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );

    //Student Directory Details
    @GET("Directory")
    Call<ParentDirectoryResponse> getParentDirDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("Mobile_No") String Mobile_No
    );

    //Student Directory Details
    @GET("Directory")
    Call<StudentDirectoryResponse> getSParentDirDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("Mobile_No") String Mobile_No
    );

    // Standard Details
    @GET("Standard")
    Call<StandardListResponse> getStandardList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );

    // Standard Division Details
    @GET("Division")
    Call<StandardDivisionResponse> getStandardDivisionList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id
    );

    // Student Standard wise Details
    @GET("StudentList")
    Call<StudentListResponse> getStudentStandardWiseList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intRole_id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intSchool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intDivision_id") String intDivision_id,
            @Query("intStandard_id") String intStandard_id
    );

    // Student Standard wise Details
    @GET("Attendance")
    Call<StudentAttendanceDetailResponse> getStudentAttendanceDatewise(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("dtDate") String dtDate,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intDivision_Id") String intDivision_id,
            @Query("intStandard_Id") String intStandard_id,
            @Query("intUser_id") String intUser_id
    );

    //ChangePassword
    @POST("ChangePassword")
    Call<CommonSuccessResponse> updatePassword(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Body ChangePasswordRequest changePasswordRequest
    );

    @Multipart
    @POST("Media")
    Call<CommonSuccessResponse> onUploadImages(
            @Header("Authorization") String Authorization,
            @Part List<MultipartBody.Part> files,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intUser_id") String intUser_id,
            @Query("MediaType_Id") String MediaType_Id,
            @Query("intMedia_Id") String intMedia_Id
    );

    @POST("Gallery")
    Call<AddGalleryResponse> onAddGalleryDetail(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("strTitle") String strTitle,
            @Query("strDiscription") String strDiscription,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intUser_id") String intUser_id
    );

    // Assignment Details
    @GET("Assignment")
    Call<AssignmentResponse> getAssignmentList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id,
            @Query("intDivision_Id") String intDivision_Id
    );

    // ExamTimetable Details
    @GET("TimeTable")
    Call<ExamTimeTableResponse> getExamTimeTableList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id,
            @Query("intDivision_Id") String intDivision_Id,
            @Query("intSem_Id") String intSem_Id
    );

    // ClassTimeTable Details
    @GET("TimeTable")
    Call<ClasTimeTableResponse> getClassTimeTableList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id,
            @Query("intDivision_Id") String intDivision_Id,
            @Query("intSem_Id") String intSem_Id
    );

    // Holiday Details
    @GET("HolidayEvent")
    Call<HolidayDetailResponse> getHolidayList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intRole_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );

    //Leave Details
    @GET("Leave")
    Call<LeaveDeatailsResponse> getLeaveDetailList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intRole_Id") String intRole_Id,
            @Query("intEmp_Id") String intEmp_Id,
            @Query("intAcademic_Id") String intAcademic_Id,
            @Query("intSchool_Id") String intSchool_Id
    );

    //Leave Details Insert
    @POST("Leave")
    Call<ApplyLeaveResponse> InsertLeaveDetail(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Body LeaveRequest leaveRequest
    );

    //Leave Details
    @GET("Leave")
    Call<LeaveTypeResponse> getLeaveTypeList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intSchool_Id") String intSchool_Id
    );

    //Mark Attendance
    @POST("Attendance")
    Call<CommonSuccessResponse> MarkAttendanceDetail(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Body List<MarkAttendanceEnum> markAttendanceEnum
    );

    //Leave Details
    @GET("Gallery")
    Call<GalleryDetailResponse> getGalleryDetails(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("Gallery_Id") String Gallery_Id
    );

    //Ebook Details
    @GET("EBook")
    Call<EbookDetailsResponse> getEbookDetail(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_id") String intStandard_id,
            @Query("DivisionId") String DivisionId,
            @Query("Role_id") String Role_id,
            @Query("intUser_id") String intUser_id
    );

    @Multipart
    @POST("EBook")
    Call<AddEbookDetailsResponse> onUploadEbook(
            @Part MultipartBody.Part file,
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("strTitle") String strTitle,
            @Query("strDiscription") String strDiscription,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intUser_id") String intUser_id,
            @Query("vchFile") String vchFile,
            @Query("Role_Id") String Role_Id,
            @Query("Standard_Id") String Standard_Id,
            @Query("FileSize") String FileSize,
            @Query("Subject_Id") String SubjectId,
            @Query("Division_Id") String DivisionId
    );
    // Subject Details
    @GET("Subject")
    Call<SubjectDetailsResponse> getSubjectList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id
    );
    // OnlineLecture Details
    @GET("OnlineLecture")
    Call<OnlineLectureDetailsResponse> getOnlineLecList(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id,
            @Query("intDivision_Id") String intDivision_Id
    );

    @Multipart
    @POST("OnlineLecture")
    Call<AddOnlineLecResponse> onUploadLec(
            @Part MultipartBody.Part file,
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Academic_Id") String Academic_Id,
            @Query("Division_Id") String Division_Id,
            @Query("FCMToken") String FCMToken,
            @Query("FilePath") String FilePath,
            @Query("InsertedBy") String InsertedBy,
            @Query("Lesson_Details") String Lesson_Details,
            @Query("Lesson_Name") String Lesson_Name,
            @Query("Role_Id") String Role_Id,
            @Query("School_Id") String School_Id,
            @Query("Standard_Id") String Standard_Id,
            @Query("Subject_Id") String Subject_Id,
            @Query("User_Id") String User_Id,
            @Query("vchProfile") String vchProfile
    );


    @Multipart
    @POST("Assignment")
    Call<CommonSuccessResponse> onUploadAssignmentDetails(
            @Header("Authorization") String Authorization,
            @Part List<MultipartBody.Part> files,
            @Query("command") String command,
            @Query("Standard_Id") String Standard_Id,
            @Query("Division_Id") String Division_Id,
            @Query("Subject_Id") String Subject_Id,
            @Query("User_ID") String User_ID,
            @Query("Assignment_Name") String Assignment_Name,
            @Query("Assignment_Details") String Assignment_Details,
            @Query("Academic_Id") String Academic_Id,
            @Query("School_Id") String School_Id,
            @Query("InsertedBy") String InsertedBy
    );


    // OnlineExamination Details
    @GET("OnlineExamination")
    Call<OnlineExaminationResponse> getOnlineExamDeatil(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("Role_Id") String Role_Id,
            @Query("intUser_id") String intUser_id,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intStandard_Id") String intStandard_Id,
            @Query("intDivision_Id") String intDivision_Id
    );

    //OnlineExamination Details Insert
    @POST("OnlineExamination")
    Call<CommonSuccessResponse> InsertOnlineExamination(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Body OnlineExamEnum onlineExamEnum
    );
    //Announcement Details Insert
    @POST("Announcement")
    Call<CommonSuccessResponse> InsertAnnouncementDetail(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Body AnnouncementEnum announcementEnum
    );

    // Assignment Details
    @GET("Announcement")
    Call<AnnouncementDetailResponse> getAnnouncement(
            @Header("Authorization") String Authorization,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id
    );
}