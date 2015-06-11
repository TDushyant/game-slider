package com.learnon.games.tiling.slider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.learnon.games.tiling.slider.R;

public class MainActivity extends Activity {

	Button btnClosePopup;
	Button btnCreatePopup;
	RadioGroup numberOfTiles;
	RadioGroup gameStyle;
	int selectedNumberOfTiles;
	int selectedGameStyleId;
	private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest re = new AdRequest();
        re.addTestDevice("3A68FD0214E0AA25C9E4E6185B96D410");
        adView.loadAd(re);
    }
    
    @Override
    public void onDestroy() {
      if (adView != null) {
        adView.destroy();
      }
      super.onDestroy();
    }

    public void ButtonOnClick(View v) {
        switch (v.getId()) {
          case R.id.playGame:
        	  callGameActivity();
        	  break;
          case R.id.howToPlay:
              callHelpActivity();
              break;
          case R.id.quit:
        	  finish();
              break;
          case R.id.highScore:
        	  callHighScoreActivity();
              break;
          }
    }

	private void callGameActivity() {
		Util.displayToast(getApplicationContext(), "Play Game");
		Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
		MainActivity.this.startActivity(myIntent);
	}

	private void callHelpActivity() {
		Util.displayToast(getApplicationContext(), "Help");
		Intent myIntent = new Intent(MainActivity.this, HelpActivity.class);
		//overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		MainActivity.this.startActivity(myIntent);
	}

	private void callHighScoreActivity() {
		Util.displayToast(getApplicationContext(), "High Score");
		Intent myIntent = new Intent(MainActivity.this, HighScoreActivity.class);
		//overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		MainActivity.this.startActivity(myIntent);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//open new activity
			Intent myIntent = new Intent(MainActivity.this, AboutActivity.class);
			MainActivity.this.startActivity(myIntent);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
