package com.brl.oneminutejobs.jobseeker;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.brl.oneminutejobs.utils.OtherUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.models.UploadFileResponse;
import com.brl.oneminutejobs.others.FileUploadService;
import com.brl.oneminutejobs.others.ServiceGenerator;
import com.obsez.android.lib.filechooser.ChooserDialog;


import java.io.File;

import androidx.core.content.ContextCompat;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JobSeekerCVUpload2 extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private String TAG = "JobSeekerCVUpload2";


    private Button uploadButton;

    private LinearLayout cvChooser;
    private EditText filePath;

    private static final int ACTIVITY_CHOOSE_FILE = 3;
    private String masterFilePath = "";

    Intent chooseFile;
    Intent intent;

    private Dialog dialog;
    Intent documentIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_cv_upload);

        cvChooser = (LinearLayout)findViewById(R.id.cv_select_panel);
        filePath = (EditText)findViewById(R.id.file_path);
        uploadButton = (Button)findViewById(R.id.upload_button);

        //--


        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");

        //--


        //SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        //Log.d(TAG,prefs.getString("userid", "null"));


        //------------
        cvChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cvChooser.startAnimation(buttonClick);

                if (ContextCompat.checkSelfPermission(JobSeekerCVUpload2.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted

                    askForPermission();


                }else{

                   // documentIntent = new Intent(Intent.ACTION_GET_CONTENT);

                    // documentIntent.setType("*/*");

                    //startActivityForResult(documentIntent, ACTIVITY_CHOOSE_FILE);

                    chooseFileFromStorage();






                }









            }
        });






        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadButton.startAnimation(buttonClick);

                if(masterFilePath.equalsIgnoreCase("")){

                    Toasty.error(JobSeekerCVUpload2.this, "Select a file first!", Toast.LENGTH_LONG, true).show();

                }else {

                    showLoadingBarAlert();
                    uploadImageWithId(masterFilePath,filePath.getText().toString());

                }

                //Toasty.success(JobSeekerCVUpload2.this,"Your CV uploaded successfully!",Toast.LENGTH_LONG, true).show();
            }
        });




    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        String path     = "";
        if(requestCode == ACTIVITY_CHOOSE_FILE)
        {
            Uri uri = data.getData();
            String FilePath = getRealPathFromURI(uri);

            Log.d(TAG,FilePath);

            /*
            if(fileExtentionCheck(FilePath)){
                //filePath.setText(fileNameSeperator(FilePath));
                if(fileSizeFinder(FilePath)){

                    filePath.setText(fileNameSeperator(FilePath));
                    masterFilePath = FilePath;

                }else {

                    filePath.setText(fileNameSeperator(""));
                    Toasty.error(JobSeekerCVUpload2.this, "File is too large! Maximum file size limit 5MB", Toast.LENGTH_LONG, true).show();
                }
            }else {

                Toasty.error(JobSeekerCVUpload2.this, "File type not supported!\n Supported formats : pdf,docx,jpg,png", Toast.LENGTH_LONG, true).show();
                filePath.setText("");
            }
            */



        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContentResolver().query( contentUri, proj, null, null,null);
        if (cursor == null) return null;
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    //--

    private void uploadImageWithId(String filePath, String shortFilePath) {
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        //File file = FileUtils.getFile(this, fileUri);
        File file = new File(filePath);;//FileUtils.getFile(this, fileUri);
        Uri myUri = Uri.parse(filePath);



        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(shortFilePath),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);



        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String userID = prefs.getString("userid", "null");
        if(userID.equalsIgnoreCase(null)){

            //-- Go to sign up screen


        }else {
            //--
            Call<UploadFileResponse> call = service.uploadImageWithId(body,Integer.parseInt(userID),"CV");
            call.enqueue(new Callback<UploadFileResponse>() {
                @Override
                public void onResponse(Call<UploadFileResponse> call,
                                       Response<UploadFileResponse> response) {
                    //Log.v("112233", response.body().getFileName()+"-------- "+response.body().getFileDownloadUri());
                    //Toasty.success(JobSeekerCVUpload2.this,response.body().toString(),Toast.LENGTH_LONG, true).show();
                    Log.d(TAG,response.body().getFileDownloadUri());


                    if(response.body().getStatus() == 200){

                        //--success
                        Toasty.success(JobSeekerCVUpload2.this,"CV uploaded successfully!",Toast.LENGTH_LONG, true).show();
                        Intent openSecondVerifier = new Intent(JobSeekerCVUpload2.this, JobSeekerModifiedDashboard.class);
                        startActivity(openSecondVerifier);
                        finish();

                    }else{

                        Toasty.error(JobSeekerCVUpload2.this,"User not created yet!",Toast.LENGTH_LONG, true).show();
                    }


                    hideLoadingBar();
                }

                @Override
                public void onFailure(Call<UploadFileResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    hideLoadingBar();
                }
            });


            //-------------------
        }


    }


    //---------------------------

    //---
    private void askForPermission(){

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                        chooseFileFromStorage();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                        //askForPermission();
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                        //askForPermission();
                        //Log.d(TAG,"----------------------- Read permission is not granted!");
                    }
                }).check();
    }

    private void showLoadingBarAlert(){


        dialog = new Dialog(JobSeekerCVUpload2.this);

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

    public void chooseFileFromStorage(){

        new ChooserDialog(JobSeekerCVUpload2.this)
                //.withStartFile(path)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                       /// Toast.makeText(JobSeekerCVUpload2.this, "FILE: " + path, Toast.LENGTH_SHORT).show();

                        //--

                        if(OtherUtils.fileExtentionCheck(path)){
                            //filePath.setText(fileNameSeperator(FilePath));
                            if(OtherUtils.fileSizeFinder(path)){

                                filePath.setText(OtherUtils.fileNameSeperator(path));
                                masterFilePath = path;

                            }else {

                                filePath.setText(OtherUtils.fileNameSeperator(""));
                                Toasty.error(JobSeekerCVUpload2.this, "File is too large! Maximum file size limit 5MB", Toast.LENGTH_LONG, true).show();
                            }
                        }else {

                            Toasty.error(JobSeekerCVUpload2.this, "File type not supported!\n Supported formats : pdf,docx,jpg,png", Toast.LENGTH_LONG, true).show();
                            filePath.setText("");
                        }



                        //------------------
                    }
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Log.d("CANCEL", "CANCEL");
                        dialog.cancel(); // MUST have
                    }
                })
                .build()
                .show();

    }

    @Override
    public void onBackPressed() {

        Intent openSecondVerifier = new Intent(JobSeekerCVUpload2.this, JobSeekerModifiedDashboard.class);
        startActivity(openSecondVerifier);
        finish();
    }

    //-------------------
}


