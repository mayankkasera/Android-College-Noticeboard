package dynamicdrillers.collegenoticeboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Happy-Singh on 3/1/2018.
 */

public class YourNoticesAdaptor extends RecyclerView.Adapter<YourNoticesAdaptor.YourNoticesViewHolder>  {


    List<Notice> noticelist;
    Context context;

    public YourNoticesAdaptor(List<Notice> noticelist) {
        this.noticelist = noticelist;
    }


    @Override
    public YourNoticesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_notice_show,parent,false);
        return new YourNoticesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final YourNoticesViewHolder holder, int position) {
        final SharedpreferenceHelper sharedpreferenceHelper = SharedpreferenceHelper.getInstance(holder.itemView.getContext());
        final Notice notice = noticelist.get(position);

        holder.AuthorName.setText(notice.getNoticeAuthor().toUpperCase());
        holder.NoticeTime.setText(notice.getTime());

        //Constants.PERSON_PROFILE_STORAGE_URL+"Person"+notice.getNoticeAuthorImage()+".png")
        Picasso.with(holder.itemView.getContext()).load("http://192.168.56.1/Web-API-College-Noticeboard/Storage/PersonProfiles/Personhappy123@gmail.com.png").into(holder.Author_Profile);

        if(notice.getNoticeTitle().length()>=50)
        {
            holder.NoticeTitle.setText(notice.getNoticeTitle().subSequence(0,50)+"...");
        }
        else{
            holder.NoticeTitle.setText(notice.getNoticeTitle());
        }

        if(notice.getNoticeDesc().length()>=70)
        {
            holder.NoticeDesc.setText(notice.getNoticeDesc().subSequence(0,70)+"...");
        }
        else{
            holder.NoticeDesc.setText(notice.getNoticeDesc());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String WEB_URL;

                if(notice.Notice_Type.equals("tg"))
                {
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteTgNotice.php";
                }
                else if(notice.getNotice_Type().equals("dept"))
                {
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteDeptNotice.php";
                }
                else
                    WEB_URL = Constants.WEB_API_URL+"FacultyDeleteCollegeNotice.php";



                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Realy Want To Delete ?");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {




                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, WEB_URL ,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(response);

                                            Toast.makeText(holder.itemView.getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                                        } catch (JSONException e) {
                                            Toast.makeText(holder.itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(holder.itemView.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();


                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> param = new HashMap<>();
                                param.put("Id",notice.getNoticeId());
                                return param;
                            }
                        };

                        MySingleton.getInstance(holder.itemView.getContext()).addToRequestQueue(stringRequest);

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return noticelist.size();
    }

    public  class  YourNoticesViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView Author_Profile;
        TextView AuthorName,NoticeTitle,NoticeDesc,NoticeTime;

        public YourNoticesViewHolder(View itemView) {
            super(itemView);
            Author_Profile = (CircleImageView)itemView.findViewById(R.id.authorimage);
            AuthorName = (TextView)itemView.findViewById(R.id.authorname);
            NoticeTitle = (TextView)itemView.findViewById(R.id.noticetitle);
            NoticeDesc = (TextView)itemView.findViewById(R.id.noticedescription);
            NoticeTime = (TextView)itemView.findViewById(R.id.noticetime);
        }
    }
}