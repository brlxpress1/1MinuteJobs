package com.brl.oneminutejobs.job_seeker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.adapters.FetchJobSeekerAllJobsAdapter;
import com.brl.oneminutejobs.adapters.FetchJobsAdapter;
import com.brl.oneminutejobs.company.Company_Fetch_All_Jobs;
import com.brl.oneminutejobs.others.ConstantsHolder;
import com.brl.oneminutejobs.others.DateConversion;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class JobSeekerAppliedJobs extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "JobSeekerAppliedJobs";

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

    //----------------------------

    ArrayList<Integer> server_job_id2 = new ArrayList<Integer>();
    ArrayList<String> server_job_title2 = new ArrayList<String>();
    ArrayList<String> server_job_description2 = new ArrayList<String>();
    ArrayList<String> server_job_qualification2 = new ArrayList<String>();
    ArrayList<Integer> server_job_jobTypeId2 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_noOfOpening2 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_timing2 = new ArrayList<Integer>();
    ArrayList<Float> server_job_salary2 = new ArrayList<Float>();
    ArrayList<String> server_job_location2 = new ArrayList<String>();
    ArrayList<String> server_job_deadline2 = new ArrayList<String>();
    ArrayList<Integer> server_job_category2 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_priority2 = new ArrayList<Integer>();
    ArrayList<String> server_job_applied2 = new ArrayList<String>();
    ArrayList<String> server_job_companyName2 = new ArrayList<String>();
    ArrayList<String> server_job_pinned2 = new ArrayList<String>();

    //------------------------------

    private ListView appliedJobs;
    private ListView pinnedJobs;

    private Button appliedJobsView;
    private Button pinnedJobsView;

    //----------------------

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

    private LinearLayout detailsPanel;
    private LinearLayout buttonPanel;
    private LinearLayout listViewPanel;

    private int backButtonValue = 0;
    private int whichList = 1;

    ArrayList<String> categoryFromServerNormalArray2 = new ArrayList<String>();
    ArrayList<String> categoryFromServerNormalArray = new ArrayList<String>();
    private int originalCategory = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_job_seeker_applied_jobs);

        appliedJobs = (ListView)findViewById(R.id.appliedJobs);
        pinnedJobs = (ListView)findViewById(R.id.pinnedJobs);

        appliedJobsView = (Button)findViewById(R.id.all_list_view);
        pinnedJobsView = (Button)findViewById(R.id.pinned_list_view);

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

        detailedScroll = (ScrollView)findViewById(R.id.detailedScroll);

        detailsPanel = (LinearLayout)findViewById(R.id.detailsPanel);
        buttonPanel = (LinearLayout)findViewById(R.id.buttonPanel);
        listViewPanel = (LinearLayout)findViewById(R.id.listViewPanel);

        showLayout(1);


        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);


        String userIdLocal = prefs.getString("userid", "");

        fetch_all_jobs(Integer.parseInt(userIdLocal));

        appliedJobsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appliedJobsView.startAnimation(buttonClick);

                whichList = 1;


                appliedJobs.setVisibility(View.VISIBLE);
                pinnedJobs.setVisibility(View.GONE);
                showLayout(1);

                appliedJobs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                pinnedJobs.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(appliedJobs, "scaleX", 0.0f,1.0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(appliedJobs, "scaleY", 0.0f,1.0f);

                scaleDownX.setDuration(1000);
                scaleDownY.setDuration(1000);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                scaleDown.start();


            }
        });

        pinnedJobsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pinnedJobsView.startAnimation(buttonClick);


                whichList = 2;



                appliedJobs.setVisibility(View.GONE);
                pinnedJobs.setVisibility(View.VISIBLE);

                showLayout(1);


                pinnedJobs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                appliedJobs.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(pinnedJobs, "scaleX", 0.0f,1.0f);
                ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(pinnedJobs, "scaleY", 0.0f,1.0f);

                scaleDownX.setDuration(1000);
                scaleDownY.setDuration(1000);

                AnimatorSet scaleDown = new AnimatorSet();
                scaleDown.play(scaleDownX).with(scaleDownY);

                scaleDown.start();

            }
        });


    }


    private void showLoadingBarAlert(){


        dialog = new Dialog(JobSeekerAppliedJobs.this);

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
                (Request.Method.POST, ConstantsHolder.rawServer+ ConstantsHolder.findAllMyJobs, parameters, new com.android.volley.Response.Listener<JSONObject>() {

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





                                JSONObject jo = new JSONObject();
                                //JSONArray ja = new JSONArray();

                                try {

                                    jo.put("data",response.optJSONArray("employeeAppliedJobs"));
                                    jo.put("data2",response.optJSONArray("employeePinJobs"));

                                    parseFetchData(jo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }




                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(JobSeekerAppliedJobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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


       // Log.e(TAG,jobj.toString());

        // Log.e("Custom 2",jobj.toString());
        JSONArray newArray = jobj.optJSONArray("data");
        JSONArray newArray2 = jobj.optJSONArray("data2");
        int size = newArray.length();
        int size2 = newArray2.length();
        //Log.e("Custom 2",String.valueOf(size));
        //-- Total applied jobs
        if(size > 0){



            for(int i=0; i<size; i++){

                JSONObject newObj = newArray.optJSONObject(i);





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





            }

            //--




            //---------------
        }
        //---------------------------------

        if(size2 > 0){



            for(int i=0; i<size2; i++){

                JSONObject newObj = newArray2.optJSONObject(i);





                server_job_id2.add(newObj.optInt("id"));
                server_job_title2.add(newObj.optString("title"));
                server_job_description2.add(newObj.optString("description"));
                server_job_qualification2.add(newObj.optString("educationQualification"));
                server_job_jobTypeId2.add(newObj.optInt("jobTypeId"));
                server_job_noOfOpening2.add(newObj.optInt("noOfOpening"));
                server_job_timing2.add(newObj.optInt("timing"));
                server_job_salary2.add((float)newObj.optInt("salary"));
                server_job_location2.add(newObj.optString("location"));
                server_job_deadline2.add(newObj.optString("deadLine"));
                server_job_category2.add(newObj.optInt("category"));
                server_job_priority2.add(newObj.optInt("priority"));
                server_job_applied2.add(newObj.optString("appliad"));
                server_job_companyName2.add(newObj.optString("companyName"));
                server_job_pinned2.add(newObj.optString("pined"));





            }

            //--




            //---------------
        }
        //---------------------------------

        //Log.d(TAG,server_job_title.toString());
        if(server_job_id.size() > 0 ){

            appliedJobs.setAdapter(new FetchJobSeekerAllJobsAdapter(this,server_job_id.size(),server_job_id,server_job_title,server_job_deadline,server_job_priority,server_job_companyName,server_job_pinned));


        }else{

            Toasty.warning(JobSeekerAppliedJobs.this, "You didn't applied any job yet!", Toast.LENGTH_LONG, true).show();

        }

        if(server_job_id2.size() > 0 ){

            pinnedJobs.setAdapter(new FetchJobSeekerAllJobsAdapter(this,server_job_id2.size(),server_job_id2,server_job_title2,server_job_deadline2,server_job_priority2,server_job_companyName2,server_job_pinned2));


        }else{

            Toasty.warning(JobSeekerAppliedJobs.this, "You didn't pinned any job!", Toast.LENGTH_LONG, true).show();

        }





    }


    public void showDetailedWindow(int postID,int position,int post_priority){

        backButtonValue = 1;
        showLayout(2);


       if(whichList == 1){


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
           deadlineShow.setText(DateConversion.organizeDate(server_job_deadline.get(position)));

           Log.e(TAG,server_job_category.toString());
           Log.e(TAG,String.valueOf(server_job_category.get(position)));
           getCateforyName(server_job_category.get(position));


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

           //-----------------



       }else {


           //--

           titleShow.setText(server_job_title2.get(position));
           descriptionShow.setText(furnishedString(server_job_description2.get(position)));
           qualificationShow.setText(furnishedString(server_job_qualification2.get(position)));

           if(server_job_jobTypeId2.get(position) == 1){

               jobTypeShow.setText(String.valueOf("Office"));

           }else{

               jobTypeShow.setText(String.valueOf("Remote"));
           }


           vacancyShow.setText(String.valueOf(server_job_noOfOpening2.get(position)));

           if(server_job_timing2.get(position) == 1){

               jobTimeShow.setText("Full-Time");

           }else{

               jobTimeShow.setText("Part-Time");
           }



           jobSalaryShow.setText(String.valueOf(server_job_salary2.get(position)));

           locationShow.setText(server_job_location2.get(position));
           deadlineShow.setText(server_job_deadline2.get(position));

           Log.e(TAG,server_job_category.toString());
           Log.e(TAG,String.valueOf(server_job_category.get(position)));
           getCateforyName(server_job_category.get(position));

           /*

           if(server_job_category2.get(position) == 1){

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

           //-----------------

       }







    }

    public String furnishedString(String original){



        String furnished = "";
        String tempText = "";

        String tempS = original.trim();

        for(int i=0; i<tempS.length(); i++){

            if(tempS.charAt(i) == '\r' || tempS.charAt(i) == '\n'){

                tempText = tempText + "\n";

            }else{
                tempText = tempText + tempS.charAt(i);

            }
        }

        /*
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
*/

        return  tempText;
    }

    private void showLayout(int i){

        buttonPanel.setVisibility(View.GONE);
        listViewPanel.setVisibility(View.GONE);
        detailsPanel.setVisibility(View.GONE);

        if(i == 1){

            buttonPanel.setVisibility(View.VISIBLE);
            listViewPanel.setVisibility(View.VISIBLE);


        }else if(i == 2){

            detailsPanel.setVisibility(View.VISIBLE);
        }
    }

    //-- Get category all
    // this method will store the info of user to  database
    private void getCategoryAll2() {


        //showLoadingBarAlert();

        categoryFromServerNormalArray.clear();





        //--

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                ConstantsHolder.rawServer+ ConstantsHolder.findCategoryAll,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        Log.d(TAG,response.toString());



                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject student = response.optJSONObject(i);

                                // Get the current student (json object) data
                                String name = student.getString("name");

                                //Log.e(TAG,name);

                                //categoryShow.setText(name);
                                categoryFromServerNormalArray2.add(name);


                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }



                        //---------------

                        // hideLoadingBar();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Toasty.error(JobSeekerAppliedJobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,error.toString());
                        // hideLoadingBar();

                    }
                }
        );



        //--------------------
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(jsonArrayRequest);


    }

    // this method will store the info of user to  database
    private void getCateforyName(int categoryID) {

        //showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("id", categoryID);




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ ConstantsHolder.findCategory, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        //parseFetchData(response);
                        // Log.d(TAG,respo);


                        if(respo.equalsIgnoreCase("")){

                        }else{

                            String categoryName = response.optString("name");
                            categoryShow.setText(categoryName);
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(JobSeekerAppliedJobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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


            showLayout(1);
            backButtonValue = 0;
        }else{

            Intent openSearchJob = new Intent(JobSeekerAppliedJobs.this,Job_Seeker_Modified_Dashboard.class);
            startActivity(openSearchJob);
            finish();

        }
    }
}
