package com.brl.oneminutejobs.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.hbb20.CountryCodePicker;
import com.brl.oneminutejobs.Intro;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.others.Connectivity;
import com.brl.oneminutejobs.others.ConstantsHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class CompanySignupBeforeCode extends AppCompatActivity {

    private String TAG = "CompanySignupBeforeCode";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);



    private Button next;
    private EditText name;
    private TextView countryCode;
    private EditText phone_number;
    private LinearLayout countryCodePanel;


    private CountryCodePicker ccp;
    private Dialog dialog;



    //public String otpID;
    private String userName;
    private String userPhone;
    private String userPurePhone;


    private  TextView login_helper_txt;

    private CheckBox termsCheck;
    private boolean is_checkboxClicked = false;
    private TextView term_text;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_signup_1);


        next = (Button)findViewById(R.id.next_button);
        name = (EditText)findViewById(R.id.name_input);
        countryCode = (TextView)findViewById(R.id.country_code_input);
        phone_number = (EditText)findViewById(R.id.phone_number_input);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        countryCodePanel = (LinearLayout)findViewById(R.id.country_code_panel);

        login_helper_txt = (TextView)findViewById(R.id.login_helper_txt);

        termsCheck = (CheckBox)findViewById(R.id.termsCheck);
        term_text = (TextView)findViewById(R.id.term_text);

        //-- initial calls
/*
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        */



        //------------------




        //-- Changing selection effect of input fields
        name.setOnFocusChangeListener((view, b) -> {
            if (view.isFocused()) {
                // Do whatever you want when the EditText is focused
                // Example:
                //editTextFrom.setText("Focused!");
                name.setBackgroundResource(R.drawable.edittext_box_border);
            }
            else{

                name.setBackgroundResource(R.drawable.shape_with_border1);

            }
        });



        phone_number.setOnFocusChangeListener((view, b) -> {
            if (view.isFocused()) {
                // Do whatever you want when the EditText is focused
                // Example:
                //editTextFrom.setText("Focused!");
                phone_number.setBackgroundResource(R.drawable.edittext_box_border);
            }
            else{
                phone_number.setBackgroundResource(R.drawable.shape_with_border1);
            }
        });

        login_helper_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent openCVwindow = new Intent(CompanySignupBeforeCode.this, CompanyLoginBeforeCode.class);
                startActivity(openCVwindow);
                finish();
            }
        });



        //-----------------



        // showLoadingBarAlert();
        if(userName != null && !userName.equalsIgnoreCase("")){

            name.setText(userName);

        }

        if(userPurePhone != null && !userPurePhone.equalsIgnoreCase("")){

            phone_number.setText(userPurePhone);

        }

        countryCodePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countryCodePanel.startAnimation(buttonClick);

                ccp.launchCountrySelectionDialog();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                next.startAnimation(buttonClick);

                //-- Check if user is connected with the internet
                if (Connectivity.isConnected(CompanySignupBeforeCode.this)) {


                    //-- Check if name input field is empty
                    String temp1 = name.getText().toString().trim();
                    if(temp1.equalsIgnoreCase("") || temp1 == null){

                        Toasty.error(CompanySignupBeforeCode.this, "Please enter your company name!", Toast.LENGTH_LONG, true).show();
                    }
                    else {

                        //-- Check if phone number input field is empty
                        String temp2 = phone_number.getText().toString().trim();
                        if(temp2.equalsIgnoreCase("") || temp2 == null){

                            Toasty.error(CompanySignupBeforeCode.this, "Please enter your phone number!", Toast.LENGTH_LONG, true).show();
                        }else {


                            if(is_checkboxClicked){

                                //-----
                                String tempName = name.getText().toString();
                                //String tempPhone = ccp.getSelectedCountryCodeWithPlus()+phone_number.getText().toString().trim();
                                String tempPhone = countryCode.getText().toString().trim()+phone_number.getText().toString().trim();

                                //Toasty.success(JobSeekerVerifyBeforeCode.this, "Name : "+tempName+"\nPhone : "+tempPhone, Toast.LENGTH_LONG, true).show();

                                //showLoadingBarAlert();
                                //-- task
                                userName = tempName;
                                userPhone = tempPhone;
                                userPurePhone = removePlusFromPhone(userPhone);

                                SharedPreferences.Editor editor = getSharedPreferences("CompanyData", MODE_PRIVATE).edit();
                                editor.putString("username", userName);
                                editor.putString("userphone", userPhone);
                                editor.putString("userphonepure", userPurePhone);

                                editor.apply();

                                /*


                                 */

                                // Intent openSecondVerifier = new Intent(JobSeekerVerifyBeforeCode.this,JobSeekerVerifyAfterCode.class);
                                // startActivity(openSecondVerifier);
                                // finish();

                                phone_number_check(removePlusFromPhone(userPhone));

                                //-----------------------
                            }else{

                                Toasty.error(CompanySignupBeforeCode.this, "Please agree with our Terms & Conditions!", Toast.LENGTH_LONG, true).show();

                            }
                        }

                    }


                } else {


                    Toasty.error(CompanySignupBeforeCode.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();

                }


            }
        });

        //--

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {

            @Override

            public void onCountrySelected() {

                ccp.startAnimation(buttonClick);
                countryCode.setText(ccp.getSelectedCountryCodeWithPlus().toString().trim());



            }

        });

        //-----------------

        //-- Hide keyboard

        phone_number.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            //addCourseFromTextBox();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        termsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                      if(isChecked){

                                                          //Toasty.info(CompanySignupBeforeCode.this,"Agreed",Toasty.LENGTH_SHORT).show();
                                                          is_checkboxClicked = true;

                                                      }else{

                                                         // Toasty.info(CompanySignupBeforeCode.this,"Opps!",Toasty.LENGTH_SHORT).show();
                                                          is_checkboxClicked = false;

                                                      }

                                                  }
                                              }
        );

        term_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                term_text.startAnimation(buttonClick);

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://brlbd.com/oneminutejobs/privacy.html"));
                startActivity(intent);
            }
        });




        //-----------------------


    }

    private void showLoadingBarAlert(){


        dialog = new Dialog(CompanySignupBeforeCode.this);

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
    private void phone_number_check(String userPhone) {






        JSONObject parameters = new JSONObject();
        try {
            parameters.put("userPhoneNumber", userPhone);
            parameters.put("userType",1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,parameters.toString());

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, ConstantsHolder.rawServer+ConstantsHolder.phoneCheck, parameters, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        //parseFetchData(response);
                        boolean userExist = response.optBoolean("userExist");
                        if(userExist){

                            Toasty.error(CompanySignupBeforeCode.this,"This phone number already exists! Try log in now.",Toast.LENGTH_LONG, true).show();

                        }else {


                            // Toasty.error(JobSeekerVerifyBeforeCode.this,"Can't update location! Please check your internet connection & try again.",Toast.LENGTH_LONG, true).show();
                            // Log.d(TAG,"Ready to verify");
                           // parseFetchData(response);

                            Intent openSecondVerifier = new Intent(CompanySignupBeforeCode.this, CompanySignupAfterCode.class);
                            startActivity(openSecondVerifier);
                            finish();


                        }

                        // hideLoadingBar();




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(CompanySignupBeforeCode.this, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();
                        // hideLoadingBar();

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

    private void parseFetchData(JSONObject jobj) {

        if (jobj != null) {

            JSONObject jobSeekerModel = jobj.optJSONObject("companyModel");

            SharedPreferences.Editor editor = getSharedPreferences("CompanyData", MODE_PRIVATE).edit();
            editor.putString("userid", "");
            editor.putString("username", "");
            editor.putString("userphoto", "");

            editor.apply();

            Intent openSecondVerifier = new Intent(CompanySignupBeforeCode.this, CompanySignupAfterCode.class);
            startActivity(openSecondVerifier);
            finish();

        }
    }



    private String removePlusFromPhone(String ph){

        String temp = "";

        for(int i=0; i<ph.length(); i++){

            if(i==0){

            }else{
                temp = temp + ph.charAt(i);
            }
        }

        return  temp;
    }



    @Override
    public void onBackPressed() {



        Intent introOpener = new Intent(CompanySignupBeforeCode.this, Intro.class);
        startActivity(introOpener);
        finish();

    }



}
