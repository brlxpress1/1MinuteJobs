package com.brl.oneminutejobs.company;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brl.oneminutejobs.others.ConstantsHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.jobseeker.JobSeekerLogin;
import com.brl.oneminutejobs.others.Connectivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class CompanyLogin2AfterCode extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private String TAG = "CompanyLogin2AfterCode";


    private Dialog dialog;
    private EditText otpCodeInput;
    private Button verifyButton;
    private TextView resendButton;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            verificationCallbacks;
    private PhoneAuthCredential credential;

    private String otpID;
    private FirebaseAuth fbAuth;
    private LinearLayout verifyPanel;
    private String tempCompanyIDforDestroy = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login_2);


        otpCodeInput = (EditText)findViewById(R.id.otp_code_input);
        verifyButton = (Button)findViewById(R.id.verify_button);
        resendButton = (TextView)findViewById(R.id.resend_button);
        verifyPanel = (LinearLayout)findViewById(R.id.verifyPanel);

        verifyPanel.setVisibility(View.INVISIBLE);


        //-- Changing selection effect of input fields
        otpCodeInput.setOnFocusChangeListener((view, b) -> {
            if (view.isFocused()) {
                // Do whatever you want when the EditText is focused
                // Example:
                //editTextFrom.setText("Focused!");
                otpCodeInput.setBackgroundResource(R.drawable.edittext_box_border);
            }
            else{
                otpCodeInput.setBackgroundResource(R.drawable.shape_with_border1);
            }
        });



        //-----------------




        fbAuth = FirebaseAuth.getInstance();

        showLoadingBarAlert();

        SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);

        String userPhone = prefs.getString("userphone", "");


        send_otp_by_firebase(userPhone);

        resendButton.setText("Did not get the OTP code? "+ Html.fromHtml("<p><u>Resend</u></p>"));

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyButton.startAnimation(buttonClick);

                if (Connectivity.isConnected(CompanyLogin2AfterCode.this)) {


                    verifyCode();

                } else {

                    Toasty.error(CompanyLogin2AfterCode.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();

                }

            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resendButton.startAnimation(buttonClick);

                if (Connectivity.isConnected(CompanyLogin2AfterCode.this)) {


                    resendCode();

                } else {

                    Toasty.error(CompanyLogin2AfterCode.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();

                }

            }
        });


        tempCompanyIDforDestroy = prefs.getString("userid", "");
        logOutTheUser();




    }

    private void showLoadingBarAlert(){


        dialog = new Dialog(CompanyLogin2AfterCode.this);

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

    //-- OTP related task

    //--

    private void send_otp_by_firebase(String number){


        SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);
        //Log.d(TAG,prefs.getString("userid", "null"));

        String userName = prefs.getString("username", "");
        String userPhone = prefs.getString("userphone", "");
        Log.d(TAG,"From shared preference : "+userName+"--------------"+userPhone);

        if(userPhone.equalsIgnoreCase("")){

            // Go to sign up page

            Intent introOpener = new Intent(CompanyLogin2AfterCode.this, CompanyLogin1BeforeCode.class);
            startActivity(introOpener);
            finish();
        }else {


            //registerUser(userName,userPhone);



            verificationCallBack();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(

                    number,        // Phone number to verify

                    60,                 // Timeout duration

                    TimeUnit.SECONDS,   // Unit of timeout

                    this,               // Activity (for callback binding)

                    verificationCallbacks);

            startCountDownTimer();




            //--


        }







    }



    private void verificationCallBack(){



        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {



            @Override

            public void onVerificationCompleted(PhoneAuthCredential credential) {

                // This callback will be invoked in two situations:

                // 1 - Instant verification. In some cases the phone number can be instantly

                //     verified without needing to send or enter a verification code.

                // 2 - Auto-retrieval. On some devices Google Play services can automatically

                //     detect the incoming verification SMS and perform verification without

                //     user action.

                Log.d(TAG, "onVerificationCompleted:" + credential);



                //credential = PhoneAuthProvider.getCredential(verificationId, code);

                signInWithPhoneAuthCredential(credential);

            }



            @Override

            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,

                // for instance if the the phone number format is not valid.

                Log.w(TAG, "onVerificationFailed", e);



                //Toast.makeText(Verification.this, "Enter Correct Number.", Toast.LENGTH_SHORT).show();

                Toasty.error(CompanyLogin2AfterCode.this, "Enter a valid phone number!", Toast.LENGTH_LONG, true).show();


                SharedPreferences.Editor editor = getSharedPreferences("CompanyData", MODE_PRIVATE).edit();
                editor.putString("userid", "");
                //editor.putString("userphone", userPhone);
                editor.apply();

                Intent openSecondVerifier = new Intent(CompanyLogin2AfterCode.this, JobSeekerLogin.class);
                startActivity(openSecondVerifier);
                finish();



                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    // Invalid request

                    // ...

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    // The SMS quota for the project has been exceeded

                    // ...

                }



                // Show a message and update the UI

                // ...

            }



            @Override
            public void onCodeSent(String verificationId,

                                   PhoneAuthProvider.ForceResendingToken token) {

                // The SMS verification code has been sent to the provided phone number, we

                // now need to ask the user to enter the code and then construct a credential

                // by combining the code with a verification ID.

                Log.d(TAG, "onCodeSent:" + verificationId);



                // Save verification ID and resending token so we can use them later



                Log.d(TAG,verificationId+"------------"+token);

                otpID = verificationId;

                Toasty.info(CompanyLogin2AfterCode.this, "OTP code sent to your phone number, please enter the code.", Toast.LENGTH_LONG, true).show();
                verifyPanel.setVisibility(View.VISIBLE);
                hideLoadingBar();






                // ...

            }

        };

    }






    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        fbAuth.signInWithCredential(credential)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithCredential:success");



                            SharedPreferences.Editor editor = getSharedPreferences("CompanyData", MODE_PRIVATE).edit();
                            editor.putString("userid", tempCompanyIDforDestroy);
                            //editor.putString("username", nam);
                            //editor.putString("userphone", userPhone);
                            editor.apply();


                            SharedPreferences.Editor typeEditor = getSharedPreferences("UserType", MODE_PRIVATE).edit();
                            typeEditor.putInt("type", 1);
                            typeEditor.apply();

                            //--
                            Intent openSecondVerifier = new Intent(CompanyLogin2AfterCode.this, CompanySearchBoard.class);
                            startActivity(openSecondVerifier);
                            finish();

                            //---------------------

                            // registerUser(userNameLocal,userPhoneLocal);





                        } else {

                            // Sign in failed, display a message and update the UI

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                // The verification code entered was invalid

                                //Toast.makeText(Verification.this, "Verification code is invalid!", Toast.LENGTH_LONG).show();

                                Toasty.error(CompanyLogin2AfterCode.this, "OTP code is invalid! Please enter correct OTP code.", Toast.LENGTH_LONG, true).show();
                                hideLoadingBar();





                            }

                        }

                    }

                });

    }



    private void verifyCode(){


        //showLoadingBarAlert();
        String temp = otpCodeInput.getText().toString().trim();

        if(temp.equals("")){



            //Toast.makeText(Verification.this, "You must enter the verification code!", Toast.LENGTH_SHORT).show();

            Toasty.error(CompanyLogin2AfterCode.this, "Enter the verification code first!", Toast.LENGTH_LONG, true).show();



        }else {



            showLoadingBarAlert();



            credential = PhoneAuthProvider.getCredential(otpID, temp);

            signInWithPhoneAuthCredential(credential);

        }



    }



    public void resendCode() {



        SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);
        //Log.d(TAG,prefs.getString("userid", ""));


        String userPhone = prefs.getString("userphone", "");

        String temp = userPhone;



        if(temp.equalsIgnoreCase("")){

        }else {


            //Toasty.success(JobSeekerLoginVerify.this, "OTP code was sent to your phone number, please check.", Toast.LENGTH_LONG, true).show();

            send_otp_by_firebase(temp);



        }

    }



    //-------------------------


    public void startCountDownTimer(){

        new CountDownTimer(ConstantsHolder.otpLifeTime, 1000) {

            public void onTick(long millisUntilFinished) {
                resendButton.setText(String.valueOf(millisUntilFinished / 1000) );
                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //mTextField.setText("done!");
                resendButton.setText("Did not get your code? Resend");
            }
        }.start();
    }


    public void logOutTheUser(){

        SharedPreferences.Editor editor = getSharedPreferences("CompanyData", MODE_PRIVATE).edit();
        editor.putString("userid", "");
        //editor.putString("username", nam);
       // editor.putString("userphone", userPhone);
        editor.apply();


        SharedPreferences.Editor typeEditor = getSharedPreferences("UserType", MODE_PRIVATE).edit();
        typeEditor.putInt("type", 0);
        typeEditor.apply();
    }



    //---------------

    @Override
    public void onBackPressed() {



        Intent openSecondVerifier = new Intent(CompanyLogin2AfterCode.this, CompanyLogin1BeforeCode.class);
        startActivity(openSecondVerifier);
        finish();

    }


}

