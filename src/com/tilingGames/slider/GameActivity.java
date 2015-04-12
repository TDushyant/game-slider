package com.tilingGames.slider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);
        AppRater.app_launched(this);
    }

    public void StartGame(View v) {
    	RadioButton numberGame = (RadioButton)findViewById(R.id.numberGame);
    	RadioButton alphaGame = (RadioButton)findViewById(R.id.alphaGame);
    	RadioButton traditionalGame = (RadioButton)findViewById(R.id.strict);
    	boolean traditional = false;
    	if (traditionalGame.isChecked()) {
    		traditional = true;
    	}
    	if (numberGame.isChecked()) {
        	int noOfTiles = 0;
        	RadioGroup numericOption = (RadioGroup) findViewById(R.id.numericoptions);
        	int id = numericOption.getCheckedRadioButtonId();
        	if (id == -1){
    	    	noOfTiles = 9;
        	}
        	else{
        	    if (id == R.id.number9){
        	    	noOfTiles = 9;
        	    }
        	    else if (id == R.id.number16){
        	    	noOfTiles = 16;
        	    }
        	    else if (id == R.id.number25){
        	    	noOfTiles = 25;
        	    }
        	}
        	callNumberGameActivity(traditional,noOfTiles);
        }
    	else if (alphaGame.isChecked()) {
    		int alphaOption = 1;
    		RadioGroup alphaGroup = (RadioGroup) findViewById(R.id.alphaoptions);
        	int id = alphaGroup.getCheckedRadioButtonId();
        	if (id == -1){
        	    //no item selected
        	}
        	else{
        	    if (id == R.id.alphacapital){
        	    	alphaOption = 1;
        	    }
        	    else if (id == R.id.alphasmall){
        	    	alphaOption = 2;
        	    }
        	    else if (id == R.id.alphamix){
        	    	alphaOption = 3;
        	    }
        	}
        	callAlphaGameActivity(traditional,alphaOption);
    	}
    	else {
    		// error condition
    		Util.displayToast(getApplicationContext(), "Please select game type numeric OR alpha");
    	}
    }
    
	private void callAlphaGameActivity(boolean traditional, int alphaOption) {
		Util.displayToast(getApplicationContext(), "Alpha Game");
		Intent myIntent = new Intent(GameActivity.this, AlphaGameActivity.class);
		myIntent.putExtra("traditional", traditional);
		myIntent.putExtra("alphaOption", alphaOption);
		GameActivity.this.startActivity(myIntent);
		finish();
	}

	private void callNumberGameActivity(boolean traditional, int noOfTiles) {
		Util.displayToast(getApplicationContext(), "Number Game");
		Intent myIntent = new Intent(GameActivity.this, NumberGameActivity.class);
		myIntent.putExtra("traditional", traditional);
		myIntent.putExtra("noOfTiles", noOfTiles);
		startActivity(myIntent);
		finish();
	}

}
