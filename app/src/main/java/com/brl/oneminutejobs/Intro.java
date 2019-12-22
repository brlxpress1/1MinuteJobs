package com.brl.oneminutejobs;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.brl.oneminutejobs.company.CompanyLogin1BeforeCode;
import com.brl.oneminutejobs.jobseeker.JobSeekerLogin;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.brl.oneminutejobs.others.Connectivity;

import es.dmoral.toasty.Toasty;


public class Intro extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "Intro";
    private Button hire_now;
    private Button find_job;
    private boolean isConnectedToNet;
    private TextView app_version_txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        hire_now = (Button) findViewById(R.id.hire_now_button);
        find_job = (Button) findViewById(R.id.find_job_button);
        app_version_txt = (TextView)findViewById(R.id.app_version);


        app_version_txt.setText("Version : "+BuildConfig.VERSION_NAME);

        //-- other call

        //----------

        hire_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hire_now.startAnimation(buttonClick);

                if (Connectivity.isConnected(Intro.this)) {


                    Intent openJobSeekerSignUp = new Intent(Intro.this, CompanyLogin1BeforeCode.class);
                    startActivity(openJobSeekerSignUp);
                    finish();


                } else {

                    Toasty.error(Intro.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();

                }



            }
        });

        find_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                find_job.startAnimation(buttonClick);

                if (Connectivity.isConnected(Intro.this)) {


                    Intent openJobSeekerSignUp = new Intent(Intro.this, JobSeekerLogin.class);
                    startActivity(openJobSeekerSignUp);
                    finish();


                } else {

                    Toasty.error(Intro.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();

                }
            }
        });
    }

    //--

    public void showExitDialogue(){

        new MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.logout_icon)
                .setHeaderColor(R.color.error_red)
                .setTitle("Exit?")
                .setDescription("Do you want to exit from this app?")

                .setCancelable(false)
                .setPositiveText("Exit")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                        finish();
                    }
                })
                .setNegativeText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                    }
                })
                .show();
    }

    //--------------------

    @Override
    public void onBackPressed() {

        showExitDialogue();

    }
}



