package com.wanshuw.inclassassignment10_wanshuw;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_PICK_PHOTO = 2;

    private ImageView imageView;
    private File photoFile;
    private Uri fileToUpload;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageView = (ImageView) findViewById(R.id.image);


        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

         startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.wanshuw.inclassassignment10_wanshuw",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir.getAbsolutePath()+"/" + imageFileName);

//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//        return image;
    }

    public void pickPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_TAKE_PHOTO) {

                fileToUpload = Uri.parse(photoFile.toURI().toString());


        } else if (requestCode == REQUEST_PICK_PHOTO) {

                fileToUpload = data.getData();

            }

        Picasso.with(this).load(fileToUpload).resize(imageView.getWidth(),imageView.getHeight()).centerInside();

    }



    public void upload(View view) {
      if (fileToUpload != null){
          StorageReference uploadRef = mStorageRef.child("image/upload.jpg");

          uploadRef.putFile(fileToUpload)
          .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                  @SuppressWarnings("VisibleForTest")
                          Uri downlloadUri = taskSnapshot.getDownloadUrl();
                  Toast.makeText(CameraActivity.this,"Upload successful!",Toast.LENGTH_SHORT).show();
              }

          })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                          Toast.makeText(CameraActivity.this,"Upload Fail",Toast.LENGTH_SHORT).show();
                      }

          });
      }
        else{
          Toast.makeText(this, "No photo",Toast.LENGTH_SHORT).show();

      }


      }

}