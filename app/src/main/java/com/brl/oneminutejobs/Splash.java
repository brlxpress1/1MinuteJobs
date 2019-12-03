package com.brl.oneminutejobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.brl.oneminutejobs.others.ConstantsHolder;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.brl.oneminutejobs.company.Company_Dashboard;
import com.brl.oneminutejobs.company.Company_SearchBoard;
import com.brl.oneminutejobs.company.Company_Signup_1;
import com.brl.oneminutejobs.job_seeker.Job_Seeker_Dashboard;
import com.brl.oneminutejobs.job_seeker.Job_Seeker_Modified_Dashboard;
import com.brl.oneminutejobs.job_seeker.Job_Seeker_Verify_1;
import com.brl.oneminutejobs.others.Connectivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class Splash extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "Splash";
    private DatabaseReference mDatabase;
    private String versionCodeFromFirebase = "";
    private String serverURLFromFirebase = "";
    private int increaser = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_splash);


        if (Connectivity.isConnected(Splash.this)) {

            //--

            Log.d(TAG,"--------- Working ----------");

            //--
            // Get a reference to our posts
            DatabaseReference ref=
                    FirebaseDatabase.getInstance().getReference();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        serverURLFromFirebase = dataSnapshot.child("version").child("app_data").child("server_url").getValue().toString();
                        versionCodeFromFirebase = dataSnapshot.child("version").child("app_data").child("version_code").getValue().toString();

                      ConstantsHolder.rawServer = serverURLFromFirebase;
                        //ConstantsHolder.rawServer = "http://192.168.70.165:8090/";

                        int versionCode = BuildConfig.VERSION_CODE;
                        String versionCodeString =  String.valueOf(versionCode);
                        if(versionCodeFromFirebase.equalsIgnoreCase(versionCodeString)){


                            //---

                            //----------------------







                            // firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();









                            //--
                            SharedPreferences prefs1 = getSharedPreferences("UserType", MODE_PRIVATE);
                            int userType = prefs1.getInt("type", 0);
                            Log.d(TAG,"Logging with user type : "+ String.valueOf(userType));

                            if(userType == 1){

                                //--
                                SharedPreferences prefs = getSharedPreferences("CompanyData", MODE_PRIVATE);
                                String userIdLocal = prefs.getString("userid", "");
                                Log.d(TAG,userIdLocal);

                                if(userIdLocal != null && !userIdLocal.equalsIgnoreCase("")){

                                    Intent openJobSeekerSignUp = new Intent(Splash.this, Company_SearchBoard.class);
                                    startActivity(openJobSeekerSignUp);
                                    finish();

                                }else {

                                    Intent openJobSeekerSignUp = new Intent(Splash.this, Company_Signup_1.class);
                                    startActivity(openJobSeekerSignUp);
                                    finish();
                                }
                                //-------------

                            }else if(userType == 2){

                                //--
                                SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
                                String userIdLocal = prefs.getString("userid", "");
                                Log.d(TAG,userIdLocal);

                                if(userIdLocal != null && !userIdLocal.equalsIgnoreCase("")){

                                    Intent openJobSeekerSignUp = new Intent(Splash.this, Job_Seeker_Modified_Dashboard.class);
                                    startActivity(openJobSeekerSignUp);
                                    finish();

                                }else {

                                    Intent openJobSeekerSignUp = new Intent(Splash.this, Job_Seeker_Verify_1.class);
                                    startActivity(openJobSeekerSignUp);
                                    finish();
                                }
                                //-------------

                            }else {


                                Intent openJobSeekerSignUp = new Intent(Splash.this, Intro.class);
                                startActivity(openJobSeekerSignUp);
                                finish();

                            }


                            //------------













                            //-------------------



                        }else{

                            showUpdateDialogue();

                        }


                        Log.d(TAG,serverURLFromFirebase+"-----------------------------"+versionCodeFromFirebase+"------------------- Current : "+versionCodeString);
                    }
                }//onDataChange

                @Override
                public void onCancelled(DatabaseError error) {

                }//onCancelled
            });


            //---------------------
        } else {

        //Toasty.error(Splash.this, "You have no internet access! Please turn on your WiFi or mobile data.", Toast.LENGTH_LONG, true).show();
        showErrorDialogue();

    }











    }

    public void showErrorDialogue(){

        new MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.error_custom)
                .setHeaderColor(R.color.error_red)
                .setTitle("Error!")
                .setDescription("Seems like you don't have internet access. Please turn on your WiFi/Data & press 'Refresh' button.")
                .setPositiveText("Refresh")
                .setCancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                Intent openSecondVerifier = new Intent(Splash.this,Splash.class);
                startActivity(openSecondVerifier);
                finish();
            }
            })
                .show();
    }

    public void showUpdateDialogue(){

        new MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.update_icon)
                .setHeaderColor(R.color.infoColor)
                .setTitle("Update available!")
                .setDescription("New version is available to download. Press update button.")
                .setPositiveText("Update")
                .setCancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        openAppInPlayStore();
                    }
                })
                .show();
    }

    public void openAppInPlayStore(){

        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(increaser > 0){

            Intent openAgain = new Intent(Splash.this,Splash.class);
            startActivity(openAgain);
            finish();
        }
        increaser++;
/*

        */
    }
}


