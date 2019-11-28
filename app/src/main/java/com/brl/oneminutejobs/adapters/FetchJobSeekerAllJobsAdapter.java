package com.brl.oneminutejobs.adapters;

import android.content.Context;
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
import com.brl.oneminutejobs.job_seeker.EmployeeJobSearch;
import com.brl.oneminutejobs.job_seeker.JobSeekerAppliedJobs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FetchJobSeekerAllJobsAdapter extends BaseAdapter {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    int count;

    ArrayList<Integer> server_job_id;
    ArrayList<String> server_job_title;
    ArrayList<String> server_job_deadline;
    ArrayList<Integer> server_job_priority;
    ArrayList<String> server_job_companyName;
    ArrayList<String> server_job_pinned;

    Context context;

    // int [] imageId;

    private static LayoutInflater inflater=null;

    public FetchJobSeekerAllJobsAdapter(JobSeekerAppliedJobs jobSeekerAppliedJobs, int count1, ArrayList<Integer> server_job_id1, ArrayList<String> server_job_title1, ArrayList<String> server_job_deadline1, ArrayList<Integer> server_job_priority1, ArrayList<String> server_job_companyName1, ArrayList<String> server_job_pinned1) {

        // TODO Auto-generated constructor stub





        count = count1;

        server_job_id = server_job_id1;
        server_job_title = server_job_title1;
        server_job_deadline = server_job_deadline1;
        server_job_priority = server_job_priority1;
        server_job_companyName = server_job_companyName1;
        server_job_pinned = server_job_pinned1;

        context=jobSeekerAppliedJobs;

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
        ImageView push_icon;
        LinearLayout itemClick;


    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        final FetchJobSeekerAllJobsAdapter.Holder holder=new FetchJobSeekerAllJobsAdapter.Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.item_employee_all_job, null);






        holder.titleAvatar=(CircleImageView) rowView.findViewById(R.id.title_image);
        holder.title = (TextView) rowView.findViewById(R.id.title);
        holder.deadline = (TextView) rowView.findViewById(R.id.deadline);
        holder.applicantList = (TextView) rowView.findViewById(R.id.company_name);
        holder.detailsButton = (Button) rowView.findViewById(R.id.button);
        holder.push_icon = (ImageView)rowView.findViewById(R.id.push_icon);
        holder.itemClick = (LinearLayout)rowView.findViewById(R.id.item_click);

        char temp  = server_job_title.get(position).toLowerCase().charAt(0);

        holder.applicantList.setText(server_job_companyName.get(position));

        //holder.applicantList.setText(server_job_pinned.get(position));

        if(server_job_pinned.get(position).equalsIgnoreCase("true")){

            holder.push_icon.setVisibility(View.VISIBLE);


        }else{

            holder.push_icon.setVisibility(View.GONE);
        }


        Glide
                .with(context)
                .load(putImage(temp))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_avatar)
                        .fitCenter())
                .into(holder.titleAvatar);






        holder.title.setText(server_job_title.get(position));
        holder.deadline.setText("Deadline : "+server_job_deadline.get(position));




        holder.applicantList.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                holder.applicantList.startAnimation(buttonClick);


                //(int userID, String photoUrl, String userName, String email, String Experience, String expectedSalary)
                //((Company_SearchBoard)context).searchItemClick(position,holder.masterLayout,Integer.parseInt(temp),jobSeekerPhotoUrl.get(position),jobSeekerName.get(position),jobSeekerDesignation.get(position),String.valueOf(jobSeekerExperience.get(position)),String.valueOf(jobSeekerExpectedSalary.get(position)));



            }

        });





        holder.detailsButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                holder.detailsButton.startAnimation(buttonClick);
                int temp = server_job_id.get(position);


                // ((Company_Fetch_All_Jobs)context).openDetailsWindow(position);
                ((JobSeekerAppliedJobs)context).showDetailedWindow(server_job_id.get(position),position,server_job_priority.get(position));


            }

        });

        holder.itemClick.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText( context, "Long click!", Toast.LENGTH_SHORT).show();
                int id = server_job_id.get(position);
                //Company_Fetch_All_Jobs company_fetch_all_jobs = new Company_Fetch_All_Jobs();


                //Dialogue_Helper dh = new Dialogue_Helper();
                //dh.job_post_popup(context,id, company_fetch_all_jobs,server_job_priority.get(position),position);

                //((EmployeeJobSearch)context).showDetailedWindow(server_job_id.get(position),position,server_job_priority.get(position));
               // ((EmployeeJobSearch)context). makePinnedPost(server_job_id.get(position),position,server_job_priority.get(position),server_job_pinned.get(position));

                return true;
            }

        });


        return rowView;


    }




    private int putImage(char value){

        int returnValue = 0;

        if(value == 'a'){


            returnValue = R.drawable._a;

        }else if(value == 'b'){


            returnValue = R.drawable._b;

        }
        else if(value == 'c'){


            returnValue = R.drawable._c;

        }
        else if(value == 'd'){


            returnValue = R.drawable._d;

        }
        else if(value == 'e'){


            returnValue = R.drawable._e;

        }
        else if(value == 'f'){


            returnValue = R.drawable._f;

        }
        else if(value == 'g'){


            returnValue = R.drawable._g;

        }
        else if(value == 'h'){


            returnValue = R.drawable._h;

        }
        else if(value == 'i'){


            returnValue = R.drawable._i;

        }
        else if(value == 'j'){


            returnValue = R.drawable._j;

        }
        else if(value == 'k'){


            returnValue = R.drawable._k;

        }
        else if(value == 'l'){


            returnValue = R.drawable._l;

        }
        else if(value == 'm'){


            returnValue = R.drawable._m;

        }
        else if(value == 'n'){


            returnValue = R.drawable._n;

        }
        else if(value == 'o'){


            returnValue = R.drawable._o;

        }
        else if(value == 'p'){


            returnValue = R.drawable._p;

        }
        else if(value == 'q'){


            returnValue = R.drawable._q;

        }
        else if(value == 'r'){


            returnValue = R.drawable._r;

        }
        else if(value == 's'){


            returnValue = R.drawable._s;

        }
        else if(value == 't'){


            returnValue = R.drawable._t;

        }
        else if(value == 'u'){


            returnValue = R.drawable._u;

        }
        else if(value == 'v'){


            returnValue = R.drawable._v;

        }
        else if(value == 'w'){


            returnValue = R.drawable._w;

        }
        else if(value == 'x'){


            returnValue = R.drawable._x;

        }
        else if(value == 'y'){


            returnValue = R.drawable._y;

        }
        else if(value == 'z'){


            returnValue = R.drawable._z;

        }
        else {


            returnValue = R.drawable._a;

        }

        return returnValue;
    }















}




