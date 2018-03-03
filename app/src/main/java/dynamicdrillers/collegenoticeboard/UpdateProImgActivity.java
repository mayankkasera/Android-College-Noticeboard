package dynamicdrillers.collegenoticeboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProImgActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    ImageView SelectedImg;
    private Bitmap SelectedBitmap;
    Button SelectImg,UpdateImg;
    String Url = Constants.WEB_API_URL+"StudentImg.php";
    public static final String SharedprefenceName = "USER_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pro_img);

        SelectedImg = findViewById(R.id.img);

        SelectImg = findViewById(R.id.edit_pto_select_img);
        SelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_IMAGE_REQUEST);
            }
        });

        UpdateImg = findViewById(R.id.edit_pro_update_img);
        UpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });


        final SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);
        if(sharedPreference.getString("type",null).equals("other"))
        {
            Url = Constants.WEB_API_URL+"FacultyImg.php";
        }
        if(sharedPreference.getString("type",null).equals("admin"))
        {
            Url = Constants.WEB_API_URL+"AdminImg.php";
        }
        if(sharedPreference.getString("type",null).equals("hod"))
        {
            Url = Constants.WEB_API_URL+"HodImg.php";
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == this.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
            //    SelectedImg.setVisibility(View.VISIBLE);
                SelectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                SelectedImg.setImageBitmap(SelectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser(int i) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);
    }


    private void upload() {
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.WEB_API_URL+"HodImg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Toast.makeText(UpdateProImgActivity.this, s, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(!jsonObject.getBoolean("error"))
                            {
                                Toast.makeText(UpdateProImgActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Intent intent = new Intent(UpdateProImgActivity.this,FacultyDashboard.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UpdateProImgActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(UpdateProImgActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String imageAdmin = getStringImage(SelectedBitmap);
                final SharedPreferences sharedPreference = getSharedPreferences(SharedprefenceName, Context.MODE_PRIVATE);

                //Creating parameters


                //Adding parameters
                Map<String,String> map = new HashMap<>();

                map.put("Email",sharedPreference.getString("email",null));
                map.put("CollegeCode",sharedPreference.getString("collegecode",null));
                map.put("Data",imageAdmin);

                return  map;

                //returning parameters

            }
        };




        //Adding request to the queue

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

}
