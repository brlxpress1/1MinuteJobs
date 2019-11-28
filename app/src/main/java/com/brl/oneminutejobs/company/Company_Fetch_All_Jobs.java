package com.brl.oneminutejobs.company;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.brl.oneminutejobs.adapters.SearchResultExample;
import com.brl.oneminutejobs.job_seeker.Job_Seeker_Dashboard;
import com.brl.oneminutejobs.others.ConstantsHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.database.DatabaseReference;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class Company_Fetch_All_Jobs extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "Company_Fetch_All_Jobs";
    private Dialog dialog;

    private ListView all_job_list;
    private ListView current_job_list;
    private ListView expired_job_list;

    private Button all_job_button;
    private Button current_job_button;
    private Button expired_job_button;


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

    //------------------------

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

    //-----------------------------------
    ArrayList<Integer> server_job_id3 = new ArrayList<Integer>();
    ArrayList<String> server_job_title3 = new ArrayList<String>();
    ArrayList<String> server_job_description3 = new ArrayList<String>();
    ArrayList<String> server_job_qualification3 = new ArrayList<String>();
    ArrayList<Integer> server_job_jobTypeId3 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_noOfOpening3 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_timing3 = new ArrayList<Integer>();
    ArrayList<Float> server_job_salary3 = new ArrayList<Float>();
    ArrayList<String> server_job_location3 = new ArrayList<String>();
    ArrayList<String> server_job_deadline3 = new ArrayList<String>();
    ArrayList<Integer> server_job_category3 = new ArrayList<Integer>();
    ArrayList<Integer> server_job_priority3 = new ArrayList<Integer>();

    //-----------------------------------
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

    private Button closeButton;

    //--------------------

    private EditText job_title;
    private  EditText job_description;
    private EditText job_qualification;

    private Spinner job_type;
    private ImageView job_type_opener;

    private EditText job_vacancies;

    private Spinner job_time;
    private ImageView job_time_opener;

    private EditText job_salary;

    private EditText job_location;
    private ImageView job_location_opener;

    private EditText job_deadline;
    private ImageView job_deadline_opener;

    private Spinner job_catagory;
    private ImageView job_catagory_opener;

    private Button job_post_button;

    CountryPicker picker;


   //-------------------------
    private int temp_id = 0;
    private int temp_priority = 0;
    private int temp_position = 0;

    private LinearLayout popup_button_panel;
    private LinearLayout postEditPanel;
    private LinearLayout detailsPanel;
    private LinearLayout listViewPanel;
    //-----------------------------

    private Button favouriteButton;
    private Button editPostButton;
    private Button deletePostButton;
    private Button closePopupButton;

    private int backButtonValue = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_company_all_job_posts);

        all_job_list = (ListView)findViewById(R.id.all_job_list);
        current_job_list = (ListView)findViewById(R.id.current_job_list);
        expired_job_list = (ListView)findViewById(R.id.expired_job_list);

        all_job_button = (Button)findViewById(R.id.all_job_button);
        current_job_button = (Button)findViewById(R.id.current_job_button);
        expired_job_button = (Button)findViewById(R.id.expired_job_button);



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
        closeButton = (Button)findViewById(R.id.closeButton);
        detailedScroll = (ScrollView)findViewById(R.id.detailedScroll);

        popup_button_panel = (LinearLayout)findViewById(R.id.popup_button_panel);
        listViewPanel = (LinearLayout)findViewById(R.id.listViewPanel);
        detailsPanel = (LinearLayout)findViewById(R.id.detailsPanel);
        postEditPanel = (LinearLayout)findViewById(R.id.editJobPostPanel);





        //--


        picker = CountryPicker.newInstance("Select Location");

        job_title = (EditText)findViewById(R.id.job_title);
        job_description = (EditText)findViewById(R.id.job_description);;
        job_qualification = (EditText)findViewById(R.id.job_qualification);;

        job_type = (Spinner)findViewById(R.id.job_type);;
        job_type_opener = (ImageView)findViewById(R.id.job_type_opener);;

        job_vacancies = (EditText)findViewById(R.id.job_vavacies);;

        job_time = (Spinner)findViewById(R.id.job_time);;
        job_time_opener = (ImageView)findViewById(R.id.job_time_opener);;

        job_salary = (EditText)findViewById(R.id.job_salary);

        job_location = (EditText)findViewById(R.id.job_location);
        job_location_opener = (ImageView)findViewById(R.id.job_location_opener);

        job_deadline = (EditText) findViewById(R.id.job_deadline);;
        job_deadline_opener = (ImageView)findViewById(R.id.job_deadline_opener);;

        job_catagory = (Spinner)findViewById(R.id.job_catagory);;
        job_catagory_opener = (ImageView)findViewById(R.id.job_catagory_opener);;

        job_post_button = (Button)findViewById(R.id.job_post_button);






        favouriteButton = (Button)findViewById(R.id.favourite_post);
        editPostButton = (Button)findViewById(R.id.edit_post);
        deletePostButton = (Button)findViewById(R.id.delete_post);
        closePopupButton= (Button)findViewById(R.id.close_popup);


        showLayout(4);//turzo
        //-----------


        SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);


        String userIdLocal = prefs.getString("userid", "");


        fetch_all_jobs(Integer.parseInt(userIdLocal));


        all_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                all_job_button.startAnimation(buttonClick);

                if(server_job_id.size() > 0){



                    performAnimation(current_job_list,expired_job_list,all_job_list);
                   all_job_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    current_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    expired_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));


                    all_job_list.smoothScrollToPosition(0);
                    current_job_list.smoothScrollToPosition(0);
                    expired_job_list.smoothScrollToPosition(0);

                }else{

                    Toasty.warning(Company_Fetch_All_Jobs.this, "No jobs post data available!", Toast.LENGTH_LONG, true).show();
                }
            }
        });

        current_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                current_job_button.startAnimation(buttonClick);

                if(server_job_id2.size() > 0){

                    performAnimation(all_job_list,expired_job_list,current_job_list);
                   all_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                   current_job_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                   expired_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));


                    all_job_list.smoothScrollToPosition(0);
                    current_job_list.smoothScrollToPosition(0);
                    expired_job_list.smoothScrollToPosition(0);

                }else{

                    Toasty.warning(Company_Fetch_All_Jobs.this, "No current job post available!", Toast.LENGTH_LONG, true).show();
                }
            }
        });

        expired_job_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expired_job_button.startAnimation(buttonClick);

                if(server_job_id3.size() > 0){

                    performAnimation(all_job_list,current_job_list,expired_job_list);
                    all_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                   current_job_list.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                   expired_job_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


                    all_job_list.smoothScrollToPosition(0);
                    current_job_list.smoothScrollToPosition(0);
                    expired_job_list.smoothScrollToPosition(0);

                }else{

                    Toasty.warning(Company_Fetch_All_Jobs.this, "No expired job post available!", Toast.LENGTH_LONG, true).show();
                }
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeButton.startAnimation(buttonClick);

                //detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
               // detailsPanel.setVisibility(View.GONE);
                showLayout(4);

            }
        });


//------------------------------------------------------------------
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                job_location.setText(name);
                picker.dismiss();
            }
        });




        job_type_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                job_type_opener.startAnimation(buttonClick);
                job_type.startAnimation(buttonClick);

                job_type.performClick();


            }
        });

        job_location_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });

        job_deadline_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePickerDialog();

            }
        });

        //--
        job_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean titleFlag = false;
                boolean descriptionFlag = false;
                boolean educationalQualificationFlag = false;
                boolean jobTypeFlag = false;
                boolean vacancyFlag = false;
                boolean timeFlag = false;
                boolean salaryFlag = false;
                boolean locationFlag = false;
                boolean deadlineFlag = false;
                boolean catagoryFlag = false;


                String titleTxt = job_title.getText().toString();
                String descriptionTxt = job_description.getText().toString();
                String educationalQualificationTxt = job_qualification.getText().toString();
                String jobTypeTxt = job_type.getSelectedItem().toString();
                String vacancyTxt = job_vacancies.getText().toString();
                String timeTxt = job_time.getSelectedItem().toString();
                String salaryTxt = job_salary.getText().toString();
                String locationTxt = job_location.getText().toString();
                String deadlineTxt = job_deadline.getText().toString();
                int catagoryTxt = job_catagory.getSelectedItemPosition();



                //--
                if(titleTxt.equalsIgnoreCase("")){

                    titleFlag = false;
                }else{
                    titleFlag = true;
                }

                //--
                if(descriptionTxt .equalsIgnoreCase("")){
                    descriptionFlag = false;
                }else{
                    descriptionFlag = true;
                }

                //--
                if(educationalQualificationTxt.equalsIgnoreCase("")){
                    educationalQualificationFlag = false;
                }else {
                    educationalQualificationFlag = true;
                }


                //--
                if(vacancyTxt.equalsIgnoreCase("")){
                    vacancyFlag = false;
                }else{
                    vacancyFlag = true;
                }

                //--
                if(locationTxt .equalsIgnoreCase("")){

                    locationFlag = false;

                }else{

                    locationFlag = true;
                }

                //--
                if(deadlineTxt .equalsIgnoreCase("")){
                    deadlineFlag = false;
                }else{
                    deadlineFlag = true;
                }

                int jobTypeValue = 0;
                int jobTimeValue = 0;
                float jobSalaryValue = 0.0f;
                int vacancyVlue = 0;

                //--

                if(jobTypeTxt.equalsIgnoreCase("office")){

                    jobTypeValue = 1;

                }else if(jobTypeTxt.equalsIgnoreCase("remote")){

                    jobTypeValue = 2;

                }



                if(timeTxt.equalsIgnoreCase("full-time")){

                    jobTimeValue = 1;

                }else if(timeTxt.equalsIgnoreCase("part-time")){

                    jobTimeValue = 2;

                }


                if(vacancyTxt.equalsIgnoreCase("")){


                }else{

                    vacancyVlue = Integer.parseInt(vacancyTxt);
                }

                //--
                if(salaryTxt.equalsIgnoreCase("")){


                }else{

                    jobSalaryValue = Float.parseFloat(salaryTxt);
                }






                if(titleFlag && descriptionFlag && educationalQualificationFlag && vacancyFlag && locationFlag && deadlineFlag){

                    // to do
                    //Toasty.success(Company_Job_Post.this,"Ready to submut",Toasty.LENGTH_LONG,true).show();

                    editJobPost(temp_id,titleTxt,descriptionTxt,educationalQualificationTxt,jobTypeValue,vacancyVlue,jobTimeValue,jobSalaryValue,locationTxt,deadlineTxt,catagoryTxt,temp_priority);


                }else{

                    String msg = "";

                    if(!titleFlag){

                        msg = msg + "- Please type a Title for this job."+"\n\n";
                    }

                    if(!descriptionFlag){

                        msg = msg + "- Add a description."+"\n\n";
                    }

                    if(!educationalQualificationFlag){

                        msg = msg + "- Add required educational qualification for this job."+"\n\n";
                    }

                    if(!vacancyFlag){

                        msg = msg + "- Add how many number of employees do you want."+"\n\n";
                    }

                    /*

                    if(!salaryFlag){

                        msg = msg + "- Enter the amount of salary for this job post."+"\n\n";
                    }
                    */


                    if(!locationFlag){

                        msg = msg + "- Select your preferred location to find the employees."+"\n\n";
                    }

                    showErrortDialogue(msg);





                }



                //------------------







            }
        });



        //---------------------

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(temp_priority > 0){

                    setFavourite(temp_id,0);

                }else{

                    setFavourite(temp_id,1);
                }

            }
        });

        editPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showLayout(2);//turzo
                openEditWindow(temp_id,temp_position);

            }
        });

        deletePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setStatus(temp_id);

            }
        });

        closePopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showLayout(4);

            }
        });


        //------------------------------------------------------------- End of oncreate function
        //--------------------------------------------------------------


    }




    //--

    private void showLoadingBarAlert(){


        dialog = new Dialog(Company_Fetch_All_Jobs.this);

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
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.findUserAllJobPost, parameters, new com.android.volley.Response.Listener<JSONObject>() {

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

                            String dataList = response.optJSONArray("jobpostList").toString();

                            if(dataList.equalsIgnoreCase("[]")){

                                Toasty.warning(Company_Fetch_All_Jobs.this, "No jobs post data available!", Toast.LENGTH_LONG, true).show();
                            }else{

                                //parseFetchData(response.optJSONObject("jobpostList"));
                               // Log.d("Custom 1",dataList);

                                JSONObject jo = new JSONObject();
                                //JSONArray ja = new JSONArray();
// populate the array
                                try {

                                    jo.put("data",response.optJSONArray("jobpostList"));
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
                        Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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
                    //server_job_priority.add(newObj.optInt("priority"));

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
            }



            //---------------
        }

        //Log.d(TAG,server_job_title.toString());
        if(server_job_id.size() > 0 ){

            all_job_list.setAdapter(new FetchJobsAdapter(this,server_job_id.size(),server_job_id,server_job_title,server_job_deadline,server_job_priority));
            readyAllListView();

        }else{

            Toasty.warning(Company_Fetch_All_Jobs.this, "No job post data available!", Toast.LENGTH_LONG, true).show();

        }





    }

    public void readyAllListView(){



        for(int i=0; i<server_job_id.size(); i++){



           // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

            try {

                //--

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());
                Date strDate = null;



                //---------


                strDate = sdf.parse(server_job_deadline.get(i));
                //Log.d("DateChecker","--------------------> Expired"+"--------- "+" System Date : "+currentDateandTime+"  ---- Job Date : "+server_job_deadline.get(i)+" --- System Time : "+String.valueOf(System.currentTimeMillis())+"   ------- Job Time : "+strDate.getTime());
                if (System.currentTimeMillis() > strDate.getTime()) {


                    server_job_id3.add(server_job_id.get(i));
                    server_job_title3.add(server_job_title.get(i));
                    server_job_description3.add(server_job_description.get(i));
                    server_job_qualification3.add(server_job_qualification.get(i));
                    server_job_jobTypeId3.add(server_job_jobTypeId.get(i));
                    server_job_noOfOpening3.add(server_job_noOfOpening.get(i));
                    server_job_timing3.add(server_job_timing.get(i));
                    server_job_salary3.add(server_job_salary.get(i));
                    server_job_location3.add(server_job_location.get(i));
                    server_job_deadline3.add(server_job_deadline.get(i));
                    server_job_category3.add(server_job_category.get(i));
                    server_job_priority3.add(server_job_priority.get(i));

                }else{

                    Log.d("DateChecker","Current <-------------------- ");
                    server_job_id2.add(server_job_id.get(i));
                    server_job_title2.add(server_job_title.get(i));
                    server_job_description2.add(server_job_description.get(i));
                    server_job_qualification2.add(server_job_qualification.get(i));
                    server_job_jobTypeId2.add(server_job_jobTypeId.get(i));
                    server_job_noOfOpening2.add(server_job_noOfOpening.get(i));
                    server_job_timing2.add(server_job_timing.get(i));
                    server_job_salary2.add(server_job_salary.get(i));
                    server_job_location2.add(server_job_location.get(i));
                    server_job_deadline2.add(server_job_deadline.get(i));
                    server_job_category2.add(server_job_category.get(i));
                    server_job_priority2.add(server_job_priority.get(i));


                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(server_job_id2.size() > 0){

            current_job_list.setAdapter(new FetchJobsAdapter(this,server_job_id2.size(),server_job_id2,server_job_title2,server_job_deadline2,server_job_priority2));
        }

        if(server_job_id3.size() > 0 ){

            expired_job_list.setAdapter(new FetchJobsAdapter(this,server_job_id3.size(),server_job_id3,server_job_title3,server_job_deadline3,server_job_priority3));
        }


    }

    public void performAnimation(ListView lv1, ListView lv2, ListView lv3){

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(lv1, "scaleX", 1.0f,0.0f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(lv1, "scaleY", 1.0f,0.0f);

        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();

        ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(lv2, "scaleX", 1.0f,0.0f);
        ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(lv2, "scaleY", 1.0f,0.0f);

        scaleDownX2.setDuration(1000);
        scaleDownY2.setDuration(1000);

        AnimatorSet scaleDown2 = new AnimatorSet();
        scaleDown2.play(scaleDownX2).with(scaleDownY2);

        scaleDown2.start();

        ObjectAnimator scaleDownX3 = ObjectAnimator.ofFloat(lv3, "scaleX", 0.0f,1.0f);
        ObjectAnimator scaleDownY3 = ObjectAnimator.ofFloat(lv3, "scaleY", 0.0f,1.0f);

        scaleDownX3.setDuration(1000);
        scaleDownY3.setDuration(1000);

        AnimatorSet scaleDown3 = new AnimatorSet();
        scaleDown3.play(scaleDownX3).with(scaleDownY3);

        scaleDown3.start();


    }


public void openDetailsWindow(int postID){

    backButtonValue = 1;

        showLayout(3);

        titleShow.setText(server_job_title.get(postID));
        descriptionShow.setText(furnishedString(server_job_description.get(postID)));
        qualificationShow.setText(furnishedString(server_job_qualification.get(postID)));

        if(server_job_jobTypeId.get(postID) == 1){

            jobTypeShow.setText(String.valueOf("Office"));

        }else{

            jobTypeShow.setText(String.valueOf("Remote"));
        }


        vacancyShow.setText(String.valueOf(server_job_noOfOpening.get(postID)));

        if(server_job_timing.get(postID) == 1){

            jobTimeShow.setText("Full-Time");

        }else{

            jobTimeShow.setText("Part-Time");
        }



        jobSalaryShow.setText(String.valueOf(server_job_salary.get(postID)));

        locationShow.setText(server_job_location.get(postID));
        deadlineShow.setText(server_job_deadline.get(postID));

        if(server_job_category.get(postID) == 1){

            categoryShow.setText("IT/Telecommunication");
        }else{

            categoryShow.setText("Industrial");
        }


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
    //detailsPanel.smoothScrollToPosition(0);


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

public void finishThis(){

        finish();
}


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
        // String date2 = dayOfMonth+"-"+month+"-"+year;

        String day1 = completeNumber(dayOfMonth);
        String month1 = completeNumber(month+1);

        String niceFormat = day1+"-"+month1+"-"+year;

        job_deadline.setText(niceFormat);

        //-- update date api
        //UpdateUserBirthdate(Integer.parseInt(userIdLocal),1,String.valueOf(dayOfMonth),String.valueOf(month),String.valueOf(year));

    }

    private String completeNumber(int data){
        String temp = "";
        if(data < 10 ){

            temp = "0"+String.valueOf(data);
        }else {

            temp = String.valueOf(data);
        }

        return temp;
    }

    //-- edit job post
    // this method will store the info of user to  database
    private void editJobPost(int idP,String titleP,String descriptionP,String qualificationP,int jobTypeP,int vacancyP,int timeP,float salaryP, String locationP,String deadlineP,int catagoryP,int priority) {


        showLoadingBarAlert();


        JSONObject parameters = new JSONObject();
        try {




            parameters.put("id", idP);
            parameters.put("title", titleP);
            parameters.put("description", descriptionP);
            parameters.put("educationQualification",qualificationP);
            parameters.put("jobTypeId", jobTypeP);
            parameters.put("noOfOpening", vacancyP);
            parameters.put("salary", salaryP);
            parameters.put("location", locationP);
            parameters.put("deadLine", deadlineP);
            parameters.put("category", catagoryP);
            parameters.put("priority", priority);
            parameters.put("status", 1);
           // parameters.put("creator", 1);
            //parameters.put("applicantList", applicantList);
           // Toasty.info(Company_Fetch_All_Jobs.this,deadlineP,Toasty.LENGTH_LONG).show();




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());



        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.updateJobPost, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);


                        String status =response.optString("status");

                        if(status.equalsIgnoreCase("200")){

                            Toasty.success(Company_Fetch_All_Jobs.this,"Job edited successfully",Toasty.LENGTH_LONG,true).show();

                            hideLoadingBar();

                            Intent openAgain = new Intent(Company_Fetch_All_Jobs.this,Company_Fetch_All_Jobs.class);
                            startActivity(openAgain);
                            finish();


                        }else{

                            Toasty.error(Company_Fetch_All_Jobs.this,"Problem with the server",Toasty.LENGTH_LONG,true).show();

                            hideLoadingBar();
                        }




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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


    }

    public void showErrortDialogue(String msg){

        new MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.error_custom)
                .setHeaderColor(R.color.error_red)
                .setTitle("Error")
                .setDescription(msg)

                .setCancelable(false)
                .setPositiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



                    }
                })

                .show();
    }

    public void openEditWindow(int id, int position){

        //Toasty.info(this,server_job_title.get(position),Toasty.LENGTH_LONG,true).show();
        backButtonValue = 1;

        job_title.setText(server_job_title.get(position));
        job_description.setText(furnishedString(server_job_description.get(position)));
        job_qualification.setText(furnishedString(server_job_qualification.get(position)));

        //job_type = (Spinner)findViewById(R.id.job_type);;
        //job_type_opener = (ImageView)findViewById(R.id.job_type_opener);;

        job_vacancies.setText(String.valueOf(server_job_noOfOpening.get(position)));

        //job_time = (Spinner)findViewById(R.id.job_time);;
        //job_time_opener = (ImageView)findViewById(R.id.job_time_opener);;

        job_salary.setText(String.valueOf(String.valueOf(server_job_salary.get(position))));

        job_location.setText(server_job_location.get(position));
        //job_location_opener = (ImageView)findViewById(R.id.job_location_opener);

        job_deadline.setText(String.valueOf(server_job_deadline.get(position)));
        //job_deadline_opener = (ImageView)findViewById(R.id.job_deadline_opener);;

        //job_catagory = (Spinner)findViewById(R.id.job_catagory);;
        //job_catagory_opener = (ImageView)findViewById(R.id.job_catagory_opener);

        postEditPanel.setVisibility(View.VISIBLE);
        temp_id = id;

    }


    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showLayout(int i){


        popup_button_panel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        postEditPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        listViewPanel.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        //Toasty.info(Company_Fetch_All_Jobs.this,String.valueOf(i),Toasty.LENGTH_LONG).show();


        if(i == 1){

            popup_button_panel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));//turzo

        }else if(i == 2){

            postEditPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        }else if(i == 3){

            detailsPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        }else if(i == 4){

            listViewPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        }else{

            listViewPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
    }

    public void showPopUp(int id, int position, int priority){

        backButtonValue = 1;

        if(priority > 0){

            favouriteButton.setText("Remove from favourite");

        }else{

            favouriteButton.setText("Save to Favourite");

        }


        temp_id = id;
        temp_priority = priority;
        temp_position = position;
        showLayout(1);
    }

    public void setFavourite(int postID, int priorityStatus){


        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("postId", postID);
            parameters.put("postStaus", priorityStatus);




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.setPostPriority, parameters, new com.android.volley.Response.Listener<JSONObject>() {

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

                            if(priorityStatus > 0){
                                Toasty.success(Company_Fetch_All_Jobs.this, "Post added to your favourite list!", Toast.LENGTH_LONG, true).show();
                            }else{
                                Toasty.success(Company_Fetch_All_Jobs.this, "Post removed from your favourite list!", Toast.LENGTH_LONG, true).show();
                            }


                            Intent reload = new Intent(Company_Fetch_All_Jobs.this,Company_Fetch_All_Jobs.class);
                            startActivity(reload);
                            finish();

                        }else{

                            Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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

    public void setStatus(int postID){


        showLoadingBarAlert();

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("postId", postID);
            parameters.put("postStaus", 0);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.postStatusUpdate, parameters, new com.android.volley.Response.Listener<JSONObject>() {

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

                            Toasty.success(Company_Fetch_All_Jobs.this, "Post deleted successfully!", Toast.LENGTH_LONG, true).show();


                            Intent reload = new Intent(Company_Fetch_All_Jobs.this,Company_Fetch_All_Jobs.class);
                            startActivity(reload);
                            finish();

                        }else{

                            Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(Company_Fetch_All_Jobs.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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




    //----------------


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

       // Toasty.info(Company_Fetch_All_Jobs.this,String.valueOf(backButtonValue),Toasty.LENGTH_LONG,false).show();
        //backButtonValue--;
        if(backButtonValue == 1){
            showLayout(4);
            backButtonValue  = 0;
        }else{

            Intent openJobSeekerSignUp = new Intent(Company_Fetch_All_Jobs.this, Company_SearchBoard.class);
            startActivity(openJobSeekerSignUp);
            finish();
        }
    }
}
