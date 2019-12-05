package com.brl.oneminutejobs.company;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.job_seeker.EmployeeJobSearch;
import com.brl.oneminutejobs.others.ConstantsHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Company_Job_Post extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "Company_Job_Post";

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

    private Dialog dialog;

    int[] applicantList;


   // String[] categoryFromServerNormalArray;
   ArrayList<String> categoryFromServerNormalArray = new ArrayList<String>();
    private int originalCategory = 1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v2_job_post);

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

        //-------------
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

        job_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

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

        job_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePickerDialog();

            }
        });

        job_time_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                job_time.performClick();
            }
        });

        job_catagory_opener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                job_catagory.performClick();
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
                int catagoryTxt = job_catagory.getSelectedItemPosition()+1;
                //Toasty.info(Company_Job_Post.this,String.valueOf(catagoryTxt),Toasty.LENGTH_SHORT).show();





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

                    jobSalaryValue = Float.parseFloat(salaryTxt+".00");
                }






                if(titleFlag && descriptionFlag && educationalQualificationFlag && vacancyFlag && locationFlag && deadlineFlag){

                    // to do
                    //Toasty.success(Company_Job_Post.this,"Ready to submut",Toasty.LENGTH_SHORT,true).show();

                    addJobPost(titleTxt,descriptionTxt,educationalQualificationTxt,jobTypeValue,vacancyVlue,jobTimeValue,jobSalaryValue,locationTxt,deadlineTxt,catagoryTxt);


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




                    if(!locationFlag){

                        msg = msg + "- Select your preferred location to find the employees."+"\n\n";
                    }

                    showErrortDialogue(msg);





                }



                //------------------







            }
        });



        //---------------------

        getCategoryAll();



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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //String date = "month/day/year: " + month + "/" + dayOfMonth + "/" + year;
        // String date2 = dayOfMonth+"-"+month+"-"+year;

        String day1 = completeNumber(dayOfMonth);
        String month1 = completeNumber(month+1);

       String niceFormat = day1+"-"+month1+"-"+year;
        //String niceFormat = year+"-"+month1+"-"+day1;

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

    private void showLoadingBarAlert(){


        dialog = new Dialog(Company_Job_Post.this);

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


    //-- Add a job post
    // this method will store the info of user to  database
    private void addJobPost(String titleP,String descriptionP,String qualificationP,int jobTypeP,int vacancyP,int timeP,float salaryP, String locationP,String deadlineP,int catagoryP) {


        showLoadingBarAlert();


        SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);


        String userIdLocal = prefs.getString("userid", "");

        JSONObject parameters = new JSONObject();
        try {




            parameters.put("title", titleP);
            parameters.put("description", descriptionP);
            parameters.put("educationQualification",qualificationP);
            parameters.put("jobTypeId", jobTypeP);
            parameters.put("timing", timeP);
            parameters.put("noOfOpening", vacancyP);
            parameters.put("salary", salaryP);
            parameters.put("location", locationP);
            parameters.put("deadLine", deadlineP);
            parameters.put("category", catagoryP);
            parameters.put("priority", 0);
            parameters.put("status", 1);
            parameters.put("creator", Integer.parseInt(userIdLocal));
            //parameters.put("applicantList", applicantList);




        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());



        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.createJobPost, parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);


                        String status =response.optString("status");

                        if(status.equalsIgnoreCase("200")){

                            Toasty.success(Company_Job_Post.this,"Job posted successfully",Toasty.LENGTH_SHORT,true).show();

                            Intent openJobSeekerSignUp = new Intent(Company_Job_Post.this, Company_Fetch_All_Jobs.class);
                            startActivity(openJobSeekerSignUp);
                            finish();

                        }else{

                            Toasty.error(Company_Job_Post.this,"Problem with the server",Toasty.LENGTH_SHORT,true).show();
                        }

                        hideLoadingBar();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(Company_Job_Post.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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


    //-- Get category all
    // this method will store the info of user to  database
    private void getCategoryAll() {


        showLoadingBarAlert();






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

                                categoryFromServerNormalArray.add(name);
                              // categoryFromServerNormalArray[i] = name;


                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                        //--

                       String[] mStringArray = new String[categoryFromServerNormalArray.size()];
                        mStringArray = categoryFromServerNormalArray.toArray(mStringArray);

                        readyCategorySpinner(mStringArray);



                        //---------------

                        hideLoadingBar();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Toasty.error(Company_Job_Post.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,error.toString());
                        hideLoadingBar();

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

    public void readyCategorySpinner(String[] temp){

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, temp);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        job_catagory.setAdapter(spinnerArrayAdapter);
    }



    @Override
    public void onBackPressed() {

        Intent openJobSeekerSignUp = new Intent(Company_Job_Post.this, Company_SearchBoard.class);
        startActivity(openJobSeekerSignUp);
        finish();

    }


}
