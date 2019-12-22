package com.brl.oneminutejobs.others;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brl.oneminutejobs.jobseeker.JobSeekerDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ExtraSkillSetFetcher {

    public String TAG = "ExtraSkillSetFetcher";
    ArrayList<String> skillIdsMaster = new ArrayList<String>();
    ArrayList<String> skillNameList = new ArrayList<String>();
    ArrayList<String> finalNameList = new ArrayList<String>();


    public void fetch_skill_list(ArrayList<String> skillIds, Activity act, JobSeekerDashboard jobSeekerDashboard) {





        RequestQueue rq = Volley.newRequestQueue(act);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, ConstantsHolder.rawServer+ConstantsHolder.fetchSkillList, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String respo=response.toString();
                        Log.d(TAG,respo);

                        //Log.d(TAG,respo);


                        parseFetchData(response,jobSeekerDashboard,skillIds);



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toasty.error(act, "Server error,please check your internet connection!", Toast.LENGTH_LONG, true).show();
                        //Toast.makeText(Login_A.this, "Something wrong with Api", Toast.LENGTH_SHORT).show();

                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.getCache().clear();
        rq.add(jsonObjectRequest);

        //-----------------

    }

    private void parseFetchData(JSONObject jobj, JobSeekerDashboard jobSeekerDashboard, ArrayList<String> skillIds){

        if(skillNameList.size() > 0){
            skillNameList.clear();
            skillIdsMaster.clear();
        }





        if(jobj != null){

            int status =jobj.optInt("status");
            if(status == 200){

                // Getting the Job Seeker info
                //JSONObject skills = jobj.optJSONObject("skills");
                try {
                    JSONArray skills = jobj.getJSONArray("skills");

                    // Log.d(TAG,skills.toString());
                    for(int i=0; i<skills.length(); i++){

                        JSONObject listData = skills.getJSONObject(i);



                        String id =  listData.optString("id");
                        String name =  listData.optString("name");
                        Log.d(TAG,id+"------------"+name);


                        skillIdsMaster.add(id);
                        skillNameList.add(name);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }else {
                // Go to Login
            }
        }else {

            // Go to Login
        }

        Log.d(TAG,skillIds.toString());


        String tempS = "";
        for(int i=0; i<skillIds.size(); i++){

            Log.d("id_testing",skillIds.get(i));
            int temp = Integer.parseInt(skillIds.get(i));
            Log.d("id_testing",skillNameList.get(temp-1));

            if(i >= skillIds.size()-1){

                tempS = tempS + skillNameList.get(temp-1)+"\n";
            }else {

                tempS = tempS + skillNameList.get(temp-1)+",\n";
            }

        }


        jobSeekerDashboard.fetchExtraSkillSet(tempS);



    }


}
