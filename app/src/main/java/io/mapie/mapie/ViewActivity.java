package io.mapie.mapie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class ViewActivity extends AppCompatActivity {


    private static ImageView imgPreview;
    private static VideoView videoPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        videoPreview = (VideoView) findViewById(R.id.videoPreview);


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
    public static void previewCapturedImage(Uri fileUri) {
        try {
            // hide video preview
            videoPreview.setVisibility(View.GONE);

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Previewing recorded video
     */
    public void previewVideo(Uri fileUri) {
        try {
            // hide image preview
            imgPreview.setVisibility(View.GONE);

            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(fileUri.getPath());
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
