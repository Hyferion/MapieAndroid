package io.mapie.mapie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ViewActivity extends AppCompatActivity {


    private static ImageView imgPreview;
    private static VideoView videoPreview;

    private Uri fileUri;



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    StorageReference userPicRef = storageRef.child("userFiles/" + auth.getCurrentUser().getUid() + "/file.jpg" );
    StorageReference userVidRef = storageRef.child("userFiles/" + auth.getCurrentUser().getUid() + "/file.mp4" );
    File localPicture;
    File localVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        imgPreview = (ImageView) findViewById(R.id.imageView);
        videoPreview = (VideoView) findViewById(R.id.videoView);



        try {
            localPicture = File.createTempFile("file", "jpg");
            localVideo = File.createTempFile("file", "mp4");
        } catch( IOException e ) {

        }
        imgPreview.setRotation(90);

            userPicRef.getFile(localPicture).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                previewCapturedImage(localPicture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                userVidRef.getFile(localVideo).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        previewVideo(localVideo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });

        // Delete the file
        /*userPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
                userVidRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!

                    }
                });
            }
        });
*/

        /*
        TODO -Get the file from firebase and save it on the device
        -Identify if its a video or a picture
        -Get the URI and play it on the screen
        -Delete the File from firebase
         */
    }


    /**
     * Display image from a path to ImageView
     */
    public static void previewCapturedImage(File file) {
        try {
            // hide video preview
            videoPreview.setVisibility(View.GONE);

            imgPreview.setVisibility(View.VISIBLE);


            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
           // options.inSampleSize = 8;


            final Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Previewing recorded video
     */
    public void previewVideo(File file) {
        try {
            // hide image preview
            imgPreview.setVisibility(View.GONE);


            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(file.getPath());
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
