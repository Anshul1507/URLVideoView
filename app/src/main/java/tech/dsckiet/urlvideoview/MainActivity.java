package tech.dsckiet.urlvideoview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog bar;
    private String path="https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private MediaController ctlr;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main);
        bar=new ProgressDialog(MainActivity.this);
        bar.setTitle("Connecting server");
        bar.setMessage("Please Wait... ");
        bar.setCancelable(false);
        bar.show();
        if(bar.isShowing()) {
            videoView = findViewById(R.id.vv);
            Uri uri = Uri.parse(path);
            videoView.setVideoURI(uri);
            videoView.start();
            ctlr = new MediaController(this);
            ctlr.setMediaPlayer(videoView);
            videoView.setMediaController(ctlr);
            videoView.requestFocus();
        }
        bar.dismiss();
    }
}
