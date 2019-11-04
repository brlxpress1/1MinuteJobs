package com.brl.oneminutejobs.others;


import com.brl.oneminutejobs.models.DateResponse;
import com.brl.oneminutejobs.models.LoginInformationResponse;
import com.brl.oneminutejobs.models.PhoneNumberCheck;
import com.brl.oneminutejobs.models.SignUpResponse;
import com.brl.oneminutejobs.models.UploadFileResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface API_Retrofit {


    // User exist or not check
    @Headers("Content-Type: application/json")
    @POST("UserCheck")
    Call<PhoneNumberCheck> userExistCheck(
            @Body JSONObject rawJson);


    // Registration api for both job seeker & company
    @Headers("Content-Type: application/json")
    @POST("UserSignUp")
    Call<SignUpResponse> signUpJobSeeker(
            @Body JSONObject rawJson);

    // Registration api for both job seeker & company
    @Headers("Content-Type: application/json")
    @POST("FindUserDetails")
    Call<LoginInformationResponse> fetchUserData(
            @Body JSONObject rawJson);


    //
    @Headers("Content-Type: application/json")
    @POST("UserDetails/JobSeeker/BirthDayUpdate")
    Call<DateResponse> updateBirthDate(
            @Body JSONObject rawJson);


}


