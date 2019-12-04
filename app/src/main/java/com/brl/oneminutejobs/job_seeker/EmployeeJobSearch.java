package com.brl.oneminutejobs.job_seeker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.brl.oneminutejobs.adapters.FetchJobsAdapter;
import com.brl.oneminutejobs.adapters.FetchJobsForEmployeeAdapter;
import com.brl.oneminutejobs.company.Company_Fetch_All_Jobs;
import com.brl.oneminutejobs.others.ConstantsHolder;
import com.ybs.countrypicker.CountryPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class EmployeeJobSearch extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "EmployeeJobSearch";
    private Dialog dialog;



    ArrayList<Integer> server_job_id = new ArrayList<Integer>();
    ArrayList<String> server_job_title = new ArrayList<String>();
    ArrayList<String> server_job_description = new ArrayList<String>();
    ArrayList<String> server_job_qualification = new ArrayList<String>();
    ArrayList<Integer> server_job_jobTypeId = new ArrayList<Integer>();
    ArrayList<Integer> server_job_noOfOpening = new ArrayList<Integer>();
    ArrayList<Integer> server_job_timing = new ArrayList<Integer>();
    ArrayList<Float> server_job_salary = new ArrayList<Float>();
    ArrayList<String> server_job_location = new ArrayList<String>();
    ArrayList<String> server_job_deadline = new ArrayList<String>();
    ArrayList<Integer> server_job_category = new ArrayList<Integer>();
    ArrayList<Integer> server_job_priority = new ArrayList<Integer>();
    ArrayList<String> server_job_applied = new ArrayList<String>();
    ArrayList<String> server_job_companyName = new ArrayList<String>();
    ArrayList<String> server_job_pinned = new ArrayList<String>();


    //------------------------
    ArrayList<Integer> server_job_id_temp = new ArrayList<Integer>();
    ArrayList<String> server_job_title_temp = new ArrayList<String>();
    ArrayList<String> server_job_description_temp = new ArrayList<String>();
    ArrayList<String> server_job_qualification_temp = new ArrayList<String>();
    ArrayList<Integer> server_job_jobTypeId_temp = new ArrayList<Integer>();
    ArrayList<Integer> server_job_noOfOpening_temp = new ArrayList<Integer>();
    ArrayList<Integer> server_job_timing_temp = new ArrayList<Integer>();
    ArrayList<Float> server_job_salary_temp = new ArrayList<Float>();
    ArrayList<String> server_job_location_temp = new ArrayList<String>();
    ArrayList<String> server_job_deadline_temp = new ArrayList<String>();
    ArrayList<Integer> server_job_category_temp = new ArrayList<Integer>();
    ArrayList<Integer> server_job_priority_temp = new ArrayList<Integer>();
    ArrayList<String> server_job_applied_temp = new ArrayList<String>();
    ArrayList<String> server_job_companyName_temp = new ArrayList<String>();
    ArrayList<String> server_job_pinned_temp = new ArrayList<String>();




    private ListView searchedJobList;

    private LinearLayout listViewPanel;
    private LinearLayout detailsPanel;

    //-----------------------

    private TextView titleShow;
    private TextView descriptionShow;
    private TextView qualificationShow;
    private TextView jobTypeShow;
    private TextView vacancyShow;
    private TextView jobTimeShow;
    private TextView jobSalaryShow;
    private TextView locationShow;
    private TextView deadlineShow;
    private TextView categoryShow;

    private ScrollView detailedScroll;

    private Button applyButton;

    //------------------------

    private int backButtonValue = 0;
    private int temp_job_id = 0;
    private int temp_priority = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_employee_job_search);

        searchedJobList = (ListView)findViewById(R.id.job_search_list);
        listViewPanel = (LinearLayout)findViewById(R.id.listViewPanel);
        detailsPanel = (LinearLayout)findViewById(R.id.detailsPanel);

        titleShow = (TextView)findViewById(R.id.titleshow);
        descriptionShow = (TextView)findViewById(R.id.descriptionshow);
        qualificationShow = (TextView)findViewById(R.id.qualificationShow);
        jobTypeShow = (TextView)findViewById(R.id.jobTypeShow);
        vacancyShow = (TextView)findViewById(R.id.vacancyShow);
        jobTimeShow = (TextView)findViewById(R.id.jobTimeShow);
        jobSalaryShow = (TextView)findViewById(R.id.jobSalaryShow);
        locationShow = (TextView)findViewById(R.id.jobLocationShow);
        deadlineShow = (TextView)findViewById(R.id.deadlineShow);
        categoryShow = (TextView)findViewById(R.id.catagoryShow);
        applyButton = (Button)findViewById(R.id.applyButton);
        detailedScroll = (ScrollView)findViewById(R.id.detailedScroll);


        showPanels(1);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);


        String userIdLocal = prefs.getString("userid", "");

       fetch_all_jobs(Integer.parseInt(userIdLocal));

       applyButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               applyButton.startAnimation(buttonClick);

               app_for_job(Integer.parseInt(userIdLocal),temp_job_id);//turzo
           }
       });
    }

    //--

    private void showLoadingBarAlert(){


        dialog = new Dialog(EmployeeJobSearch.this);

        dialog.setContentView(R.layout.loading);

        dialog.setTitle("Please wait!");

        dialog.setCancelable(false);



        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        dialog.show();

    }



    private void hideLoadingBar(){



        dialog.dismiss();

    }

    // this method will store the info of user to  database
    private void fetch_all_jobs(int userID) {

        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("id", userID);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ ConstantsHolder.findEmployeeAllPost, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);
                        Log.d(TAG,ConstantsHolder.rawServer+ ConstantsHolder.findEmployeeAllPost);

                        //Log.d(TAG,respo);


                        //parseFetchData(response);
                        // Log.d(TAG,respo);
                        hideLoadingBar();

                        int status = response.optInt("status");

                        if(status == 200){

                            String dataList = response.optJSONArray("employeeJobPostModels").toString();

                            if(dataList.equalsIgnoreCase("[]")){

                                Toasty.warning(EmployeeJobSearch.this, "No job data available!", Toast.LENGTH_LONG, true).show();
                            }else{

                                //parseFetchData(response.optJSONObject("jobpostList"));
                                // Log.d("Custom 1",dataList);

                                JSONObject jo = new JSONObject();
                                //JSONArray ja = new JSONArray();
// populate the array
                                try {

                                    jo.put("data",response.optJSONArray("employeeJobPostModels"));
                                    parseFetchData(jo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(EmployeeJobSearch.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,error.toString());
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

    private void parseFetchData(JSONObject jobj) {

        // Log.e("Custom 2",jobj.toString());
       // Log.d("Custom 2","-------------- Entered ---------------------");
        JSONArray newArray = jobj.optJSONArray("data");
        int size = newArray.length();
        Log.e("Custom 2",String.valueOf(size));


        if(size > 0){



            for(int i=0; i<size; i++){

                JSONObject newObj = newArray.optJSONObject(i);




                if(newObj.optInt("priority") > 0){

                    server_job_id.add(newObj.optInt("id"));
                    server_job_title.add(newObj.optString("title"));
                    server_job_description.add(newObj.optString("description"));
                    server_job_qualification.add(newObj.optString("educationQualification"));
                    server_job_jobTypeId.add(newObj.optInt("jobTypeId"));
                    server_job_noOfOpening.add(newObj.optInt("noOfOpening"));
                    server_job_timing.add(newObj.optInt("timing"));
                    server_job_salary.add((float)newObj.optInt("salary"));
                    server_job_location.add(newObj.optString("location"));
                    server_job_deadline.add(newObj.optString("deadLine"));
                    server_job_category.add(newObj.optInt("category"));
                    server_job_priority.add(newObj.optInt("priority"));
                    server_job_applied.add(newObj.optString("appliad"));
                    server_job_companyName.add(newObj.optString("companyName"));
                    server_job_pinned.add(newObj.optString("pined"));

                    ////server_job_priority.add(newObj.optInt("priority"));

                }else{

                    server_job_id_temp.add(newObj.optInt("id"));
                    server_job_title_temp.add(newObj.optString("title"));
                    server_job_description_temp.add(newObj.optString("description"));
                    server_job_qualification_temp.add(newObj.optString("educationQualification"));
                    server_job_jobTypeId_temp.add(newObj.optInt("jobTypeId"));
                    server_job_noOfOpening_temp.add(newObj.optInt("noOfOpening"));
                    server_job_timing_temp.add(newObj.optInt("timing"));
                    server_job_salary_temp.add((float)newObj.optInt("salary"));
                    server_job_location_temp.add(newObj.optString("location"));
                    server_job_deadline_temp.add(newObj.optString("deadLine"));
                    server_job_category_temp.add(newObj.optInt("category"));
                    server_job_priority_temp.add(newObj.optInt("priority"));
                    server_job_applied_temp.add(newObj.optString("appliad"));
                    server_job_companyName_temp.add(newObj.optString("companyName"));
                    server_job_pinned_temp.add(newObj.optString("pined"));


                    //server_job_priority_temp.add(newObj.optInt("priority"));


                }





            }

            //--

            //-- Concate
            for(int j=0; j<server_job_id_temp.size();j++){


                server_job_id.add(server_job_id_temp.get(j));
                server_job_title.add(server_job_title_temp.get(j));
                server_job_description.add(server_job_description_temp.get(j));
                server_job_qualification.add(server_job_qualification_temp.get(j));
                server_job_jobTypeId.add(server_job_jobTypeId_temp.get(j));
                server_job_noOfOpening.add(server_job_noOfOpening_temp.get(j));
                server_job_timing.add(server_job_timing_temp.get(j));
                server_job_salary.add(server_job_salary_temp.get(j));
                server_job_location.add(server_job_location_temp.get(j));
                server_job_deadline.add(server_job_deadline_temp.get(j));
                server_job_category.add(server_job_category_temp.get(j));
                server_job_priority.add(server_job_priority_temp.get(j));
                server_job_applied.add(server_job_applied_temp.get(j));
                server_job_companyName.add(server_job_companyName_temp.get(j));
                server_job_pinned.add(server_job_pinned_temp.get(j));

            }



            //---------------
        }

        //Log.d(TAG,server_job_title.toString());
        if(server_job_id.size() > 0 ){

            searchedJobList.setAdapter(new FetchJobsForEmployeeAdapter(this,server_job_id.size(),server_job_id,server_job_title,server_job_deadline,server_job_priority,server_job_companyName,server_job_pinned));
           // readyAllListView();

        }else{

            Toasty.warning(EmployeeJobSearch.this, "No job post data available!", Toast.LENGTH_LONG, true).show();

        }






    }

    public void showDetailedWindow(int postID,int position,int post_priority){

        backButtonValue = 1;
        showPanels(2);

        String canBeApplied = server_job_applied.get(position);
        //Toasty.info(EmployeeJobSearch.this,canBeApplied,Toasty.LENGTH_LONG,false).show();

        if(canBeApplied.equalsIgnoreCase("false")){

            applyButton.setText("Apply now");
            applyButton.setEnabled(true);
            applyButton.setBackgroundColor(getResources().getColor(R.color.color2));

        }else{

            applyButton.setText("Already applied");
            applyButton.setEnabled(false);
            applyButton.setBackgroundColor(getResources().getColor(R.color.delete_message_text));

        }

        //--

        titleShow.setText(server_job_title.get(position));
        descriptionShow.setText(furnishedString(server_job_description.get(position)));
        qualificationShow.setText(furnishedString(server_job_qualification.get(position)));

        if(server_job_jobTypeId.get(position) == 1){

            jobTypeShow.setText(String.valueOf("Office"));

        }else{

            jobTypeShow.setText(String.valueOf("Remote"));
        }


        vacancyShow.setText(String.valueOf(server_job_noOfOpening.get(position)));

        if(server_job_timing.get(position) == 1){

            jobTimeShow.setText("Full-Time");

        }else{

            jobTimeShow.setText("Part-Time");
        }



        jobSalaryShow.setText(String.valueOf(server_job_salary.get(position)));

        locationShow.setText(server_job_location.get(position));
        deadlineShow.setText(server_job_deadline.get(position));


        String[] stringArray = getResources().getStringArray(R.array.catagory_array);

        categoryShow.setText(stringArray[(server_job_category.get(position-1))]);

        /*

        if(server_job_category.get(position) == 1){

            categoryShow.setText("IT/Telecommunication");
        }else{

            categoryShow.setText("Industrial");
        }
        */


        //detailsPanel.setVisibility(View.VISIBLE);
        detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(detailsPanel, "scaleX", 0.0f,1.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(detailsPanel, "scaleY", 0.0f,1.0f);

        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();
        detailedScroll.fullScroll(ScrollView.FOCUS_UP);

        temp_job_id = postID;




        //--------------


    }

    public String furnishedString(String original){

        String furnished = "";
        String tempText = "";

        for(int i=0; i<original.length(); i++){

            if(original.charAt(i) == '\r' || original.charAt(i) == '\n'){

            }else{
                tempText = tempText + original.charAt(i);

            }
        }

        for(int i=0; i<tempText.length(); i++){

            if(i==0){

                furnished = furnished + "* ";
                furnished =  furnished + tempText.charAt(i);

            }else{

                if(i < tempText.length()-2){


                    if(tempText.charAt(i) == '.'){

                        furnished =  furnished +tempText.charAt(i)+"\n\n"+"* ";
                    }else{
                        furnished =  furnished + tempText.charAt(i);
                    }
                }else{

                    furnished =  furnished + tempText.charAt(i);
                }


            }
        }


        return  furnished;
    }

    public void showPanels(int i){

        listViewPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));

        if(i == 1){

            listViewPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


        }else if(i == 2){

            detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    // this method will store the info of user to  database
    private void app_for_job(int userID,int jobPostId) {

        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("userId", userID);
            parameters.put("postId", jobPostId);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ ConstantsHolder.applyJobPostPost, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        //parseFetchData(response);
                        // Log.d(TAG,respo);
                        hideLoadingBar();

                        int status = response.optInt("status");

                        if(status == 200){

                            Toasty.success(EmployeeJobSearch.this, "Applied Successfully!", Toast.LENGTH_LONG, true).show();

                            Intent openAgain = new Intent(EmployeeJobSearch.this,EmployeeJobSearch.class);
                            startActivity(openAgain);
                            finish();


                        }else{

                            Toasty.error(EmployeeJobSearch.this, "Server not responding, please turn on your internet connection and try again.", Toast.LENGTH_LONG, true).show();

                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(EmployeeJobSearch.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,error.toString());
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

    public void makePinnedPost(int postID,int position,int post_priority,String isPinned){

        temp_job_id = postID;
       // if(isPinned.equalsIgnoreCase("true")){

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);


        String userIdLocal = prefs.getString("userid", "");

            add_pinned_post(Integer.parseInt(userIdLocal),temp_job_id,isPinned);
      //  }



    }

    // this method will store the info of user to  database
    private void add_pinned_post(int userID,int jobPostId,String pinnedOrNot) {

        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();

        if(pinnedOrNot.equalsIgnoreCase("true")){

            try {


                parameters.put("userId", userID);
                parameters.put("postId", jobPostId);
                parameters.put("status", false);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{


            try {


                parameters.put("userId", userID);
                parameters.put("postId", jobPostId);
                parameters.put("status", true);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ ConstantsHolder.saveJob, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        //parseFetchData(response);
                        // Log.d(TAG,respo);
                        hideLoadingBar();

                        int status = response.optInt("status");

                        if(status == 200){

                            if (pinnedOrNot.equalsIgnoreCase("true")) {

                                Toasty.success(EmployeeJobSearch.this, "Job removed form pinned list successfully!", Toast.LENGTH_LONG, true).show();

                                Intent openAgain = new Intent(EmployeeJobSearch.this,EmployeeJobSearch.class);
                                startActivity(openAgain);
                                finish();
                            } else {



                                Toasty.success(EmployeeJobSearch.this, "Job pinned successfully!", Toast.LENGTH_LONG, true).show();

                                Intent openAgain = new Intent(EmployeeJobSearch.this,EmployeeJobSearch.class);
                                startActivity(openAgain);
                                finish();

                            }


                        }else{

                            Toasty.error(EmployeeJobSearch.this, "Server not responding, please turn on your internet connection and try again.", Toast.LENGTH_LONG, true).show();

                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(EmployeeJobSearch.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,error.toString());
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



    @Override
    public void onBackPressed() {

        if(backButtonValue == 1){

            showPanels(1);
            backButtonValue = 0;

        }else{

            Intent openSearchJob = new Intent(EmployeeJobSearch.this,Job_Seeker_Modified_Dashboard.class);
            startActivity(openSearchJob);
            finish();


        }
    }
}