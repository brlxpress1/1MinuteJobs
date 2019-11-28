package com.brl.oneminutejobs.company;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.brl.oneminutejobs.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Company_applicant_list extends AppCompatActivity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    private String TAG = "Company_applicant_list";

    String[] listItem;

    private ArrayList<Integer> applicant_list_show_id = new ArrayList<Integer>();
    private   ArrayList<String> applicant_list_show_name = new ArrayList<String>();
    //Company_Fetch_All_Jobs company_fetch_all_jobs;

    private ListView applicantList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_company_applicant_list);

        applicantList = (ListView)findViewById(R.id.applicant_list);



        applicant_list_show_id = Company_Fetch_All_Jobs.applicant_list_show_id;
        applicant_list_show_name = Company_Fetch_All_Jobs.applicant_list_show_name;


        String[] mStringArray = new String[applicant_list_show_name.size()];
        mStringArray = applicant_list_show_name.toArray(mStringArray);
        Log.e(TAG,applicant_list_show_name.toString());

        //listItem = company_fetch_all_jobs.applicant_list_show_name.toArray(company_fetch_all_jobs.applicant_list_show_name.size();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mStringArray);
        applicantList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
