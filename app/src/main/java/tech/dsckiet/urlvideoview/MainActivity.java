package tech.dsckiet.urlvideoview;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity.Java";
    boolean portrait = true;
    YouTubePlayerView youTubePlayerView;
    PlayerUiController playerUiController;
    int height=0,width=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         youTubePlayerView = findViewById(R.id.youtube_player_view);
         playerUiController = youTubePlayerView.getPlayerUiController();
        //for loading video into player
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);
                if(state == PlayerConstants.PlayerState.ENDED){
                    Toast.makeText(MainActivity.this,"Video Ended",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "nQdm4LCNr9s";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        //for getting dimensions of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Log.i(TAG, "Screen: " + width + "x" + height);

        //for initial youtube player screen
        youTubePlayerView.setLayoutParams(new FrameLayout.LayoutParams(width,(width*9)/16));
        Log.i(TAG, "onCreate: height: " + (height*9)/16);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //for setting title in player UI
//        playerUiController.setVideoTitle("Title");

        //for action of full screen button in player UI
        playerUiController.setFullScreenButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(portrait){
                    portrait = false;
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    youTubePlayerView.setLayoutParams(new FrameLayout.LayoutParams(height,width));
                }else{
                    portrait = true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    youTubePlayerView.setLayoutParams(new FrameLayout.LayoutParams(width,(width*9)/16));
                }
            }
        });

        getLifecycle().addObserver(youTubePlayerView);
    }


    @Override
    public void onBackPressed() {
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            youTubePlayerView.setLayoutParams(new FrameLayout.LayoutParams(width,(width*9)/16));
        }else{
            Toast.makeText(this, "Back Button pressed", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

}
