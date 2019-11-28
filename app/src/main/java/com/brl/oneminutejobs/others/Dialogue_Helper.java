package com.brl.oneminutejobs.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.brl.oneminutejobs.company.Company_Fetch_All_Jobs;
import com.brl.oneminutejobs.company.Company_SearchBoard;
import com.brl.oneminutejobs.job_seeker.Job_Seeker_Dashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

import es.dmoral.toasty.Toasty;


public class Dialogue_Helper {

    public String TAG = "Dialogue_Helper";
    private Dialog dialog;







    //-- Showing name input
    public void askingForName(Activity actv, EditText edt, Job_Seeker_Dashboard job_seeker_dashboard ) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.name_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                               String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "You have to type your name!", Toast.LENGTH_LONG, true).show();
                                    askingForName(actv,edt,job_seeker_dashboard);


                                }else {

                                 edt.setText(nameTemp);

                                 job_seeker_dashboard.setName();


                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing Email input
    public void askingForEmail(Activity actv, EditText edt, Job_Seeker_Dashboard job_seeker_dashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.email_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "You have to type your Email!", Toast.LENGTH_LONG, true).show();
                                    askingForEmail(actv,edt,job_seeker_dashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    job_seeker_dashboard.setEmail();


                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing Experience input
    public void askingForExperience(Activity actv, EditText edt, Job_Seeker_Dashboard jobSeekerDashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.experience_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "You have to type your Experience!", Toast.LENGTH_LONG, true).show();
                                    askingForExperience(actv,edt,jobSeekerDashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    jobSeekerDashboard.setExperience();


                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing salary input
    public void askingForSalary(Activity actv, EditText edt, Job_Seeker_Dashboard jobSeekerDashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.salary_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "Enter the amount of your salary!", Toast.LENGTH_LONG, true).show();
                                    askingForSalary(actv,edt,jobSeekerDashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    jobSeekerDashboard.setSalary();


                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing current company input
    public void askingForCurrentCompany(Activity actv, EditText edt, Job_Seeker_Dashboard jobSeekerDashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.current_company_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "You have to type your current company name!", Toast.LENGTH_LONG, true).show();
                                    askingForCurrentCompany(actv,edt,jobSeekerDashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    jobSeekerDashboard.setCompany();


                                }
                            }
                        })
                /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
                        */
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing current designation input
    public void askingForDesignation(Activity actv, EditText edt, Job_Seeker_Dashboard jobSeekerDashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.designation_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText input_name = (EditText) promptsView
                .findViewById(R.id.name);



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "You have to type your designation!", Toast.LENGTH_LONG, true).show();
                                    askingForDesignation(actv,edt,jobSeekerDashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    jobSeekerDashboard.setDesignatiion();


                                }
                            }
                        })
                /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
                        */
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-------------------

    //-- Showing prepered location input
    public void askingForPreperedLocation(Activity actv, EditText edt, Job_Seeker_Dashboard jobSeekerDashboard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.prepered_location_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView input_name = (AutoCompleteTextView) promptsView
                .findViewById(R.id.name);

        LocationList ll = new LocationList();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(actv, android.R.layout.simple_list_item_1, ll.preparedLocationList);

        input_name.setAdapter(adapter);




        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    Toasty.error(actv, "Enter the location where you want the job most!", Toast.LENGTH_LONG, true).show();
                                    askingForPreperedLocation(actv,edt,jobSeekerDashboard);


                                }else {

                                    edt.setText(nameTemp);
                                    jobSeekerDashboard.setLocation();


                                }
                            }
                        })
                /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
                        */
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-- Showing prepered location input
    public void showLocationSearch(Activity actv, EditText edt, Company_SearchBoard company_searchBoard) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(actv);
        View promptsView = li.inflate(R.layout.prepered_location_input, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                actv);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView input_name = (AutoCompleteTextView) promptsView
                .findViewById(R.id.name);

        LocationList ll = new LocationList();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(actv, android.R.layout.simple_list_item_1, ll.preparedLocationList);

        input_name.setAdapter(adapter);




        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                    //Toasty.error(actv, "You must enter the location name!", Toast.LENGTH_LONG, true).show();
                                   // askingForPreperedLocation(actv,edt,company_searchBoard);


                                }else {

                                    edt.setText(nameTemp);
                                    //jobSeekerDashboard.setLocation();


                                }
                            }
                        })
                /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
                        */
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //-- Showing prepered location input
    public void job_post_popup(Context ct, int id, Company_Fetch_All_Jobs company_fetch_all_jobs,int priority,int position) {



        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(ct);
        View promptsView = li.inflate(R.layout.item_post_job_popup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ct,R.style.AppTheme);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final Button favourite_post = (Button) promptsView
                .findViewById(R.id.favourite_post);

        final Button edit_post = (Button) promptsView
                .findViewById(R.id.edit_post);

        final Button delete_post = (Button) promptsView
                .findViewById(R.id.delete_post);


        if(priority > 0){

            favourite_post.setText("Remove from favourite");

        }else{

            favourite_post.setText("Save to Favourite");

        }



        favourite_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(priority > 0){

                    setFavourite(id,0,ct,company_fetch_all_jobs);

                }else{

                    setFavourite(id,1,ct,company_fetch_all_jobs);

                }


            }
        });

        edit_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                company_fetch_all_jobs.openEditWindow(id,position);
               // Toasty.success(ct,String.valueOf(position),Toasty.LENGTH_LONG,true).show();


            }
        });


        delete_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setStatus(id,0,ct,company_fetch_all_jobs);


            }
        });





        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {



                            }
                        })
                /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {


                                //String nameTemp = "";
                                String nameTemp = input_name.getText().toString();

                                if(nameTemp.equals("")){

                                }else {

                                }
                            }
                        })
                        */
        ;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }




    //-------------------

    public void setFavourite(int postID, int statusMod, Context ct, Company_Fetch_All_Jobs company_fetch_all_jobs){


        showLoadingBarAlert(ct);

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("postId", postID);
            parameters.put("postStaus", statusMod);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(ct);
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

                            if(statusMod > 0){
                                Toasty.success(ct, "Post added to your favourite list!", Toast.LENGTH_LONG, true).show();
                            }else{
                                Toasty.success(ct, "Post removed from your favourite list!", Toast.LENGTH_LONG, true).show();
                            }


                            Intent reload = new Intent(ct,Company_Fetch_All_Jobs.class);
                            ct.startActivity(reload);
                            company_fetch_all_jobs.finishThis();

                        }else{

                            Toasty.error(ct, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(ct, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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

    private void showLoadingBarAlert(Context ct){


        dialog = new Dialog(ct);

        dialog.setContentView(R.layout.loading);

        dialog.setTitle("Please wait!");

        dialog.setCancelable(false);



       // DisplayMetrics displayMetrics = new DisplayMetrics();

       // ct.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        dialog.show();

    }



    private void hideLoadingBar(){



        dialog.dismiss();

    }

    public void setStatus(int postID, int statusMod, Context ct, Company_Fetch_All_Jobs company_fetch_all_jobs){


        showLoadingBarAlert(ct);

        JSONObject parameters = new JSONObject();
        try {


            parameters.put("postId", postID);
            parameters.put("postStaus", 0);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(ct);
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

                            Toasty.success(ct, "Post deleted successfully!", Toast.LENGTH_LONG, true).show();


                            Intent reload = new Intent(ct,Company_Fetch_All_Jobs.class);
                            ct.startActivity(reload);
                            company_fetch_all_jobs.finishThis();

                        }else{

                            Toasty.error(ct, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(ct, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
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


}
