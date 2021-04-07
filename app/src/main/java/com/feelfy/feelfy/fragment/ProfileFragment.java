package com.feelfy.feelfy.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.adapter.ImageAdapter;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.description.AdavancedSearch;
import com.feelfy.feelfy.description.AdvancedDiscriptionActivity;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.modules.HorizontalImageModule;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.modules.SwipeImageResponse;
import com.feelfy.feelfy.modules.UploadResponse;
import com.feelfy.feelfy.modules.UserExtraPics;
import com.feelfy.feelfy.modules.UserLogin;
import com.feelfy.feelfy.profile.ProfileActivity;
import com.feelfy.feelfy.registration.LoginActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.feelfy.feelfy.utils.FilePath;
import com.feelfy.feelfy.utils.TinderCard;
import com.google.gson.JsonObject;
import com.kyleduo.switchbutton.SwitchButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment {


    LinearLayout ll_subscribe, ll_discription, ll_advanced_search;
    RelativeLayout rel_camera;
    Context mContext;
    private RecyclerView recylerview_horizontal_selection;
    private ArrayList<HorizontalImageModule> horizontalImageModules = new ArrayList<>();
    CircleImageView iv_user_profile_pic;

    Uri fileUri;
    Uri fileUri2;
    Uri imageUrl;
    TextView name;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_CAMERA_REQUEST_CODE2 = 200;
    private static final int MY_GALLERY_REQUEST_CODE = 2;
    JsonObject jsonObject;
    Uri uri;
    File file;
    ApiInterface apiInterface;
    SwitchButton sb_text_state;
    RequestProfileUpdate requestProfileUpdate;
    String id, btnstring;
    RelativeLayout rel_camera1,rel_camera2,rel_camera3,rel_camera4,rel_camera5,rel_camera6;
    ImageView image_add1,image_add2,image_add3,image_add4,image_add5,image_add6;
    Bitmap single_bitmap1,single_bitmap2,single_bitmap3,single_bitmap4,single_bitmap5,single_bitmap6;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        requestProfileUpdate = AppPreferences.loadSharedPreferencesLogList(mContext);
        id = requestProfileUpdate.getId();

//        requestProfileUpdate=LoginActivity.requestProfileUpdate;
        name = view.findViewById(R.id.name);
        String namesa = requestProfileUpdate.getNickname();
        name.setText(namesa);
        findId(view);


        personInfo();
        getExtrapics();

//        setUpRecyclerViewHorizontal();
        onclick();

        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> req = apiInterface.getprofilevisibilty(u_id);
        req.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
               String visibility = response.body().getIsenable();
               if (visibility.equals("1")){
                   sb_text_state.setChecked(true);
                   sb_text_state.setThumbDrawableRes(R.drawable.custom_eye);
               }
               else {
                   sb_text_state.setChecked(false);
                   sb_text_state.setThumbDrawableRes(R.drawable.custom_hidden);
               }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });

        // requestProfileUpdate.setGender("f");
        sb_text_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    int Visibility = 1;
                    int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
                    sb_text_state.setThumbDrawableRes(R.drawable.custom_eye);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> req = apiInterface.isActive(u_id,Visibility);
                    req.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });


                } else {
                    sb_text_state.setThumbDrawableRes(R.drawable.custom_hidden);
                    int Visibility = 0;
                    int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
                    sb_text_state.setThumbDrawableRes(R.drawable.custom_eye);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> req = apiInterface.isActive(u_id,Visibility);
                    req.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }

            }
        });
        return view;
    }//========= End of onCreateView() ================================

    private void findId(View view) {
        ll_subscribe = view.findViewById(R.id.ll_subscribe);
        ll_discription = view.findViewById(R.id.ll_discription);
        ll_advanced_search = view.findViewById(R.id.ll_advanced_search);
        rel_camera = view.findViewById(R.id.rel_camera);
        iv_user_profile_pic = view.findViewById(R.id.iv_user_profile_pic);
        sb_text_state = view.findViewById(R.id.sb_text_state);

        rel_camera1 = view.findViewById(R.id.rel_camera1);
        rel_camera2 = view.findViewById(R.id.rel_camera2);
        rel_camera3 = view.findViewById(R.id.rel_camera3);
        rel_camera4 = view.findViewById(R.id.rel_camera4);
        rel_camera5 = view.findViewById(R.id.rel_camera5);
        rel_camera6 = view.findViewById(R.id.rel_camera6);

        image_add1 = view.findViewById(R.id.image_add);
        image_add2 = view.findViewById(R.id.image_add2);
        image_add3 = view.findViewById(R.id.image_add3);
        image_add4 = view.findViewById(R.id.image_add4);
        image_add5 = view.findViewById(R.id.image_add5);
        image_add6 = view.findViewById(R.id.image_add6);

//        recylerview_horizontal_selection = view.findViewById(R.id.recylerview_horizontal_selection);
    }

    private void onclick() {
        ll_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof HomeActivity) {
                    ((HomeActivity) mContext).replaceFragment(new SubscribeFragment());
                } else if (mContext instanceof ProfileActivity) {
                    ((ProfileActivity) mContext).replaceFragment(new SubscribeFragment());
                }
            }
        });
        ll_discription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdvancedDiscriptionActivity.class);
                intent.putExtra("model", requestProfileUpdate);
                startActivity(intent);
            }
        });
        ll_advanced_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdavancedSearch.class);
                intent.putExtra("model", requestProfileUpdate);
                startActivity(intent);
                //mContext.finish();
            }
        });

        rel_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        rel_camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //takeMultiPhoto();
                btnstring = "r1";
                showPictureDialog2();

            }
        });
        rel_camera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeMultiPhoto();
                btnstring = "r2";
                showPictureDialog2();
            }
        });
        rel_camera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeMultiPhoto();
                btnstring = "r3";
                showPictureDialog2();
            }
        });
        rel_camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeMultiPhoto();
                btnstring = "r4";
                showPictureDialog2();
            }
        });
        rel_camera5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeMultiPhoto();
                btnstring = "r5";
                showPictureDialog2();
            }
        });
        rel_camera6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeMultiPhoto();
                btnstring = "r6";
                showPictureDialog2();
            }
        });
    }

    private void listdata() {
        // horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_photo_camera, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
        horizontalImageModules.add(new HorizontalImageModule(R.drawable.ic_vector_gallary, R.drawable.ic_vector_add));
    }


//    private void setUpRecyclerViewHorizontal() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recylerview_horizontal_selection.setLayoutManager(linearLayoutManager);
//        ImageAdapter adapter = new ImageAdapter(mContext, horizontalImageModules);
//        recylerview_horizontal_selection.setAdapter(adapter);
//    }

    //____________________________Get Image From Camera_________________________________
    private void showPictureDialog() {
        final androidx.appcompat.app.AlertDialog.Builder pictureDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        pictureDialog.setTitle("Select Action");
        pictureDialog.setIcon(R.drawable.ic_camera);
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera", "Cancel"};

        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takeFromGallery();
                        break;
                    case 1:
                        takeFromCamera();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

//   GET EXTRA IMAGE DIALOG
    private void showPictureDialog2() {
        final androidx.appcompat.app.AlertDialog.Builder pictureDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        pictureDialog.setTitle("Select Action");
        pictureDialog.setIcon(R.drawable.ic_camera);
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera", "Cancel"};

        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        takeMultiPhoto();
                        break;
                    case 1:
                        takeFromCamera2();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takeFromCamera() {
        // Check if this device has a camera
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // Open default camera
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);             // start the image capture Intent
            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);        //100
        } else {
            // no camera on this device
            Toast.makeText(mContext, "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void takeFromCamera2() {
        // Check if this device has a camera
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    // Open default camera
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri2);             // start the image capture Intent
            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE2);        //100
        } else {
            // no camera on this device
            Toast.makeText(mContext, "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    private void takeFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), MY_GALLERY_REQUEST_CODE);
    }

    private void takeMultiPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("maxSelection",6);
        startActivityForResult(Intent.createChooser(intent,"Select Multiple Picture"), 230);
    }

    Bitmap bit_map,bit_map1,bit_map2,bit_map3,bit_map4,bit_map5;
    int i;
    Uri imageUri;
    Uri multi_uri_1= null,multi_uri_2 = null,multi_uri_3 = null,multi_uri_4 = null,multi_uri_5 = null,multi_uri_6 = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bitmap bitmap_camera,bitmap1,bitmap2,bitmap3,bitmap4,bitmap5,bitmap6;
            imageUrl = data.getData();
            fileUri = data.getData();
            fileUri2 = data.getData();
            uri = data.getData();
            if (requestCode == 100 && resultCode == RESULT_OK) {
                bitmap_camera = (Bitmap) data.getExtras().get("data");
                iv_user_profile_pic.setImageBitmap(bitmap_camera);
               // uploadImage(bitmap_camera);
               Uri uri_camer = getImageUri(bitmap_camera);
                multipartImageUpload(uri_camer);
            }

            // extra images camera
            else if(requestCode == 200 && resultCode == RESULT_OK){
                bitmap1 = (Bitmap) data.getExtras().get("data");
                if (btnstring == "r1"){
                    image_add1.setImageBitmap(bitmap1);
                    Uri r1camera = getImageUri(bitmap1);
                    extraimage1(r1camera);
                }
                else if (btnstring == "r2"){
                    bitmap2 = (Bitmap) data.getExtras().get("data");
                    image_add2.setImageBitmap(bitmap2);
                    Uri r2camera = getImageUri(bitmap2);
                    extraimage2(r2camera);
                }
                else if (btnstring == "r3"){
                    bitmap3 = (Bitmap) data.getExtras().get("data");
                    image_add3.setImageBitmap(bitmap3);
                    Uri r3camera = getImageUri(bitmap3);
                    extraimage3(r3camera);
                }
                else if (btnstring == "r4"){
                    bitmap4 = (Bitmap) data.getExtras().get("data");
                    image_add4.setImageBitmap(bitmap4);
                    Uri r4camera = getImageUri(bitmap4);
                    extraimage4(r4camera);
                }
                else if (btnstring == "r5"){
                    bitmap5 = (Bitmap) data.getExtras().get("data");
                    image_add5.setImageBitmap(bitmap5);
                    Uri r5camera = getImageUri(bitmap5);
                    extraimage5(r5camera);
                }
                else if (btnstring == "r6"){
                    bitmap6 = (Bitmap) data.getExtras().get("data");
                    image_add6.setImageBitmap(bitmap6);
                    Uri r6camera = getImageUri(bitmap6);
                    extraimage6(r6camera);
                }
            }

            //extra images gallery
            else if(requestCode == 230 && resultCode == RESULT_OK) {
                if (data.getData() !=null){

                    imageUri = data.getData();
                    if (btnstring == "r1"){
                        single_bitmap1 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add1.setImageBitmap(single_bitmap1);
                        rel_camera1.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage1(imageUri);
                    }
                    else if (btnstring == "r2"){
                        single_bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add2.setImageBitmap(single_bitmap2);
                        rel_camera2.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage2(imageUri);
                    }
                    else if (btnstring == "r3"){
                        single_bitmap3 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add3.setImageBitmap(single_bitmap3);
                        rel_camera3.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage3(imageUri);
                    }
                    else if (btnstring == "r4"){
                        single_bitmap4 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add4.setImageBitmap(single_bitmap4);
                        rel_camera4.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage4(imageUri);
                    }
                    else if (btnstring == "r5"){
                        single_bitmap5 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add5.setImageBitmap(single_bitmap5);
                        rel_camera5.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage5(imageUri);
                    }
                    else if (btnstring == "r6"){
                        single_bitmap6 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUrl);
                        image_add6.setImageBitmap(single_bitmap6);
                        rel_camera6.setBackgroundColor(Color.parseColor("#ffffff"));
                        extraimage6(imageUri);
                    }
                }
                else if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for ( i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        imageUri = item.getUri();

                       if(i==0){
                           bit_map = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                           multi_uri_1 = imageUri;
                           image_add1.setImageBitmap(bit_map);
                           rel_camera1.setBackgroundColor(Color.parseColor("#ffffff"));
                           extraimage1(imageUri);
                        } else if(i==1){
                            bit_map1 = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                            image_add2.setImageBitmap(bit_map1);
                            rel_camera2.setBackgroundColor(Color.parseColor("#ffffff"));
                            extraimage2(imageUri);
                        }else if(i==2){
                           bit_map2 = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                           image_add3.setImageBitmap(bit_map2);
                           rel_camera3.setBackgroundColor(Color.parseColor("#ffffff"));
                           extraimage3(imageUri);
                        }else if(i==3){
                           bit_map3 = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                           image_add4.setImageBitmap(bit_map3);
                           rel_camera4.setBackgroundColor(Color.parseColor("#ffffff"));
                           extraimage4(imageUri);
                        }else if(i==4){
                           bit_map4 = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                           image_add5.setImageBitmap(bit_map4);
                           rel_camera5.setBackgroundColor(Color.parseColor("#ffffff"));
                           extraimage5(imageUri);
                        }else if(i==5){
                           bit_map5 = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                           image_add6.setImageBitmap(bit_map5);
                           rel_camera6.setBackgroundColor(Color.parseColor("#ffffff"));
                           extraimage6(imageUri);
                        }
                    }
                }

            }

            else if(requestCode == 2 && resultCode == RESULT_OK) {
              Bitmap  bitmap_profile = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUrl);
//                //Setting image to ImageView
                iv_user_profile_pic.setImageBitmap(bitmap_profile);
//                RequestOptions options = new RequestOptions()
//                        .fitCenter()
//                        .dontAnimate()
//                        .placeholder(R.drawable.ic_attach_button)
//                        .error(R.drawable.ic_attach_button)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .priority(Priority.HIGH);
////
//                Glide.with(this).load(bitmap_profile)
//                        .apply(options)
//                        .into(iv_user_profile_pic);
//                uploadImage(bitmap_profile);
                multipartImageUpload(uri);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri( Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String photo = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        requestProfileUpdate.setProfilephoto(photo);
    }

    private void extraimage1(Uri imageUri){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path;
        path = FilePath.getPath(mContext, imageUri);
        file = new File(path);

        //For 1st Image
        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> req = apiInterface.post_extraimage(body, requestBody,u_id);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Pic successfully uploaded", Toast.LENGTH_SHORT).show();
                rel_camera1.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void extraimage2(Uri imageUri2){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path2;
        path2 = FilePath.getPath(getContext(), imageUri2);
        File file2 = new File(path2);
        //For 2nd Image
        RequestBody reqFile2 = RequestBody.create(MediaType.parse("*/*"), file2);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("file1", file2.getName(), reqFile2);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), file2.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall =apiInterface.post_extraimage2(body2,requestBody2,u_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                rel_camera2.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void extraimage3(Uri imageUri3){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path3;
        path3 = FilePath.getPath(getContext(), imageUri3);
        File file3 = new File(path3);
        //For 2nd Image
        RequestBody reqFile3 = RequestBody.create(MediaType.parse("*/*"), file3);
        MultipartBody.Part body3= MultipartBody.Part.createFormData("file2", file3.getName(), reqFile3);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("text/plain"), file3.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall =apiInterface.post_extraimage3(body3,requestBody3,u_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                rel_camera3.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void extraimage4(Uri imageUri4){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path4;
        path4 = FilePath.getPath(getContext(), imageUri4);
        File file4 = new File(path4);
        //For 2nd Image
        RequestBody reqFile4 = RequestBody.create(MediaType.parse("*/*"), file4);
        MultipartBody.Part body4 = MultipartBody.Part.createFormData("file3", file4.getName(), reqFile4);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("text/plain"), file4.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall =apiInterface.post_extraimage4(body4,requestBody4,u_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
                rel_camera4.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void extraimage5(Uri imageUri5){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path5;
        path5 = FilePath.getPath(getContext(), imageUri5);
        File file5 = new File(path5);
        //For 2nd Image
        RequestBody reqFile5 = RequestBody.create(MediaType.parse("*/*"), file5);
        MultipartBody.Part body5 = MultipartBody.Part.createFormData("file4", file5.getName(), reqFile5);
        RequestBody requestBody5 = RequestBody.create(MediaType.parse("text/plain"), file5.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall =apiInterface.post_extraimage5(body5,requestBody5,u_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
                rel_camera5.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void extraimage6(Uri imageUri6){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String path6;
        path6 = FilePath.getPath(getContext(), imageUri6);
        File file6 = new File(path6);
        //For 2nd Image
        RequestBody reqFile6 = RequestBody.create(MediaType.parse("*/*"), file6);
        MultipartBody.Part body6 = MultipartBody.Part.createFormData("file5", file6.getName(), reqFile6);
        RequestBody requestBody6 = RequestBody.create(MediaType.parse("text/plain"), file6.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall =apiInterface.post_extraimage6(body6,requestBody6,u_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                rel_camera6.setBackgroundColor(Color.parseColor("#ffffff"));
                getExtrapics();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void multipartImageUpload(Uri uri1) {
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        String name = requestProfileUpdate.getName();
        String path = FilePath.getPath(getContext(), uri1);
        file = new File(path);

        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> req = apiInterface.post_profilepic(body, requestBody, u_id);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "file upload successfully", Toast.LENGTH_SHORT).show();
                personInfo();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }
    private void getExtrapics(){
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
        progressDialog.show();
        Call<UserExtraPics> call = apiInterface.get_extrapics(u_id);
        call.enqueue(new Callback<UserExtraPics>() {
            @Override
            public void onResponse(Call<UserExtraPics> call, Response<UserExtraPics> response) {
                progressDialog.dismiss();
                String Eimg1 = response.body().getEimage1();
                String Eimg2 = response.body().getEimage2();
                String Eimg3 = response.body().getEimage3();
                String Eimg4 = response.body().getEimage4();
                String Eimg5 = response.body().getEimage5();
                String Eimg6 = response.body().getEimage6();
                if (Eimg1 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg1).into(image_add1);
                    rel_camera1.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if (Eimg2 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg2).into(image_add2);
                    rel_camera2.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if (Eimg3 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg3).into(image_add3);
                    rel_camera3.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if (Eimg4 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg4).into(image_add4);
                    rel_camera4.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if (Eimg5 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg5).into(image_add5);
                    rel_camera5.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                if (Eimg6 !=null) {
                    Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + Eimg6).into(image_add6);
                    rel_camera6.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onFailure(Call<UserExtraPics> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "No extra images", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void personInfo() {
        int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserLogin> call = apiInterface.get_profilephoto(u_id);

        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                String res = response.body().getImage();
                String names = response.body().getName();
                name.setText(names);
                requestProfileUpdate.setNickname(names);

                progressDialog.dismiss();
                Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + res).into(iv_user_profile_pic);


            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    //_____________________________________________________________

}
//==========================================
