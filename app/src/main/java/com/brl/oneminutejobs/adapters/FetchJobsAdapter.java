package com.brl.oneminutejobs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brl.oneminutejobs.R;
import com.brl.oneminutejobs.company.CompanyFetchAllJobs;
import com.brl.oneminutejobs.utils.OtherUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FetchJobsAdapter extends BaseAdapter {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    int count;

    ArrayList<Integer> server_job_id;
    ArrayList<String> server_job_title;
    ArrayList<String> server_job_deadline;
    ArrayList<Integer> server_job_priority;


    Context context;

    // int [] imageId;

    private static LayoutInflater inflater=null;

    public FetchJobsAdapter(CompanyFetchAllJobs company_fetch_all_jobs, int count1, ArrayList<Integer> server_job_id1, ArrayList<String> server_job_title1, ArrayList<String> server_job_deadline1, ArrayList<Integer> server_job_priority1) {

        // TODO Auto-generated constructor stub





        count = count1;

        server_job_id = server_job_id1;
        server_job_title = server_job_title1;
        server_job_deadline = server_job_deadline1;
        server_job_priority = server_job_priority1;

        context=company_fetch_all_jobs;

        // imageId=prgmImages;

        inflater = (LayoutInflater)context.

                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override

    public int getCount() {

        // TODO Auto-generated method stub

        return count;

    }



    @Override

    public Object getItem(int position) {

        // TODO Auto-generated method stub

        return position;

    }



    @Override

    public long getItemId(int position) {

        // TODO Auto-generated method stub

        return position;

    }



    public class Holder

    {


        CircleImageView titleAvatar;
        TextView title;
        TextView deadline;
        TextView applicantList;
        Button detailsButton;
        ImageView star_icon;
        LinearLayout itemClick;


    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        final FetchJobsAdapter.Holder holder=new FetchJobsAdapter.Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.item_company_all_job, null);






        holder.titleAvatar=(CircleImageView) rowView.findViewById(R.id.title_image);
        holder.title = (TextView) rowView.findViewById(R.id.title);
        holder.deadline = (TextView) rowView.findViewById(R.id.deadline);
        holder.applicantList = (TextView) rowView.findViewById(R.id.applicant_button);
        holder.detailsButton = (Button) rowView.findViewById(R.id.button);
        holder.star_icon = (ImageView)rowView.findViewById(R.id.star_icon);
        holder.itemClick = (LinearLayout)rowView.findViewById(R.id.item_click);

       char temp  = server_job_title.get(position).toLowerCase().charAt(0);


        Glide
                .with(context)
                .load(OtherUtils.putImage(temp))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_avatar)
                        .fitCenter())
                .into(holder.titleAvatar);






        holder.title.setText(server_job_title.get(position));


        holder.deadline.setText("Deadline : "+ OtherUtils.organizeDate((server_job_deadline.get(position) )));
        Log.d("Turzo","Working utils putimage");

        if(server_job_priority.get(position) > 0){

            holder.star_icon.setVisibility(View.VISIBLE);

        }else{

            holder.star_icon.setVisibility(View.INVISIBLE);
        }


        holder.applicantList.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                holder.applicantList.startAnimation(buttonClick);


                //(int userID, String photoUrl, String userName, String email, String Experience, String expectedSalary)
                //((CompanySearchBoard)context).searchItemClick(position,holder.masterLayout,Integer.parseInt(temp),jobSeekerPhotoUrl.get(position),jobSeekerName.get(position),jobSeekerDesignation.get(position),String.valueOf(jobSeekerExperience.get(position)),String.valueOf(jobSeekerExpectedSalary.get(position)));

                ((CompanyFetchAllJobs)context).showApplicantList(position);

            }

        });





        holder.detailsButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                holder.detailsButton.startAnimation(buttonClick);
                int temp = server_job_id.get(position);


                ((CompanyFetchAllJobs)context).openDetailsWindow(position,temp);


            }

        });

        holder.itemClick.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText( context, "Long click!", Toast.LENGTH_SHORT).show();
                int id = server_job_id.get(position);
                //CompanyFetchAllJobs company_fetch_all_jobs = new CompanyFetchAllJobs();


                //DialogueHelper dh = new DialogueHelper();
                //dh.job_post_popup(context,id, company_fetch_all_jobs,server_job_priority.get(position),position);

                ((CompanyFetchAllJobs)context).showPopUp(server_job_id.get(position),position,server_job_priority.get(position));

                return true;
            }

        });


        return rowView;


    }





}


