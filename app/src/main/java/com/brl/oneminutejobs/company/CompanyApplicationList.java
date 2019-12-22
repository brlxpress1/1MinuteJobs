package com.brl.oneminutejobs.company;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.others.ConstantsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class CompanyApplicationList extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "CompanyApplicationList";

    String[] listItem;

    private ArrayList<Integer> applicant_list_show_id = new ArrayList<Integer>();
    private   ArrayList<String> applicant_list_show_name = new ArrayList<String>();
    //CompanyFetchAllJobs company_fetch_all_jobs;

    private ListView applicantList;
    private Dialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_company_applicant_list);

        applicantList = (ListView)findViewById(R.id.applicant_list);



        applicant_list_show_id = CompanyFetchAllJobs.applicant_list_show_id;
        applicant_list_show_name = CompanyFetchAllJobs.applicant_list_show_name;


        String[] mStringArray = new String[applicant_list_show_name.size()];
        mStringArray = applicant_list_show_name.toArray(mStringArray);
        Log.e(TAG,applicant_list_show_name.toString());

        //listItem = company_fetch_all_jobs.applicant_list_show_name.toArray(company_fetch_all_jobs.applicant_list_show_name.size();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mStringArray);
        applicantList.setAdapter(adapter);

        applicantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                //String value=adapter.getItem(position);
               //Toast.makeText(getApplicationContext(),String.valueOf(applicant_list_show_id.get(position)),Toast.LENGTH_SHORT).show();
                fetch_user_info(applicant_list_show_id.get(position),0);


            }
        });

        // fetch_user_info(102,0);
    }

    // this method will store the info of user to  database
    private void fetch_user_info(int userID,int userType) {

        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("userId", userID);
            parameters.put("userType", userType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.fetchUserData, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        parseFetchData(response);
                       // hideLoadingBar();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(CompanyApplicationList.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        hideLoadingBar();

                    }
                }){

            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                //headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.getCache().clear();
        rq.add(jsonObjectRequest);

        //-----------------

    }

    private void parseFetchData(JSONObject jobj){


        SharedPreferences.Editor editor = getSharedPreferences("UserDetailsData", MODE_PRIVATE).edit();
            /*
            // linearLayout.startAnimation(buttonClick);
            SharedPreferences.Editor editor = getSharedPreferences("UserDetailsData", MODE_PRIVATE).edit();
            editor.putString("userid", String.valueOf(userID));
            editor.putString("photourl", photoUrl);
            editor.putString("username", userName);
            editor.putString("email", email);
            editor.putString("experience", experience);
            editor.putString("expectedsalary", expectedSalary);
            editor.putString("cvurl", jobSeekerCVUrl.get(position));
            editor.putString("skillset", jobSeekerSkillSet.get(position));
            editor.apply();

            Intent openSecondVerifier = new Intent(CompanySearchBoard.this,EmployeeDetails.class);
            startActivity(openSecondVerifier);
            //finish();
            */






        if(jobj != null){

            boolean userExists =jobj.optBoolean("userExist");
            if(userExists){

                // Getting the Job Seeker info
                JSONObject jobSeekerModel = jobj.optJSONObject("jobSeekerModel");


/*
                editor.putString("userid", String.valueOf(userID));
                editor.putString("photourl", photoUrl);
                editor.putString("username", userName);
                editor.putString("email", email);
                editor.putString("experience", experience);
                editor.putString("expectedsalary", expectedSalary);
                editor.putString("cvurl", jobSeekerCVUrl.get(position));
                editor.putString("skillset", jobSeekerSkillSet.get(position));
                editor.apply();
                */




                //-----

                String fullName = jobSeekerModel.optString("fullName");
                editor.putString("username", String.valueOf(fullName));

                String photoUrl = jobSeekerModel.optString("photoUrl");
                editor.putString("photourl", photoUrl);

                String email = jobSeekerModel.optString("email");
                editor.putString("email", email);

                String experience = jobSeekerModel.optString("experience");
                editor.putString("experience", experience);

                String expectedSalary = jobSeekerModel.optString("expectedSalary");
                editor.putString("expectedSalary", expectedSalary);

                String cvUrl = jobSeekerModel.optString("cvUrl");
                editor.putString("cvurl", cvUrl);

                editor.putString("skillset", "donot");

                editor.apply();
                Log.d(TAG,fullName+"------------------------  ");

                Intent openSecondVerifier = new Intent(CompanyApplicationList.this, EmployeeDetails.class);
                startActivity(openSecondVerifier);

                //parseSkillSetFromJsonArray(listData.getJSONArray("skillsList"));





                //String skillset = jobSeekerModel.optString("skillset");
                //editor.putString("skillset", jobSeekerSkillSet.get(position));





                //-------------------------------


                hideLoadingBar();




            }else {
                // Go to Login
            }
        }else {

            // Go to Login
        }




    }



    private void showLoadingBarAlert(){


        dialog = new Dialog(CompanyApplicationList.this);

        dialog.setContentView(R.layout.custom_profile_dashboard_loading1);

        dialog.setTitle("Please wait!");

        dialog.setCancelable(false);



        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.END);



        dialog.show();

    }



    private void hideLoadingBar(){



        dialog.dismiss();

    }



    //----------------------

    @Override
    public void onBackPressed() {

        finish();
    }
}
