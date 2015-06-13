package com.learnon.games.tiling.slider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.learnon.games.tiling.slider.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.GridView;

public class AlphaGameActivity extends Activity {

	GridView gridViewMain;
	int blankTileId;  // index starts with zero
	int numberOfTiles = 24;
	Chronometer stopWatch;
	boolean validMove = true;
	boolean traditional = false;
	int alphaOption = 1; 
	String gameType = "";
	int numberOfColumns = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        stopWatch = (Chronometer) findViewById(R.id.stopWatch);
        //stopWatch.setBackgroundColor(Color.GRAY);
        gridViewMain = (GridView) findViewById(R.id.gridView1);
        
        // create dataList and set it to gridView using adapter
        Intent intent = getIntent();
        alphaOption = intent.getIntExtra("alphaOption", 1);
        traditional = intent.getBooleanExtra("traditional", false);
        
        
        String base = "CAPITAL";
    	if (alphaOption == 2) {
    		base = "small"; // for small letters
    	}else if (alphaOption == 3)
    	{
    		base = "mIxED";
    	}
    	if (traditional) {
    		gameType = "Alpah - " + base +" - Challanging";
    	}
    	else {
    		gameType = "Alpah - " + base +" - Easy";
    	}
        
        gridViewMain.setNumColumns(numberOfColumns);
        List<String> dataList = Util.prepareAlphaList(numberOfTiles, alphaOption);
        setBlankTileId(dataList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_layout,dataList);
        gridViewMain.setAdapter(adapter);
        
        // on click lister
        OnItemClickListener listner = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Util.displayToast(getApplicationContext(),String.valueOf(position) + "/" + String.valueOf(id));
				@SuppressWarnings("unchecked")
				ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) parent.getAdapter();
				if (traditional) {
					if (Util.isValidMove(numberOfColumns, blankTileId,position)) {
						validMove = true;
					}
					else {
						validMove = false;
					}
				}
				if (validMove) {
					blankTileId = Util.swapTiles(adapter, blankTileId, position);
					if (Util.isSolved(adapter)) {
						Util.displayToast(getApplicationContext(), "You Win", true);
				        stopWatch.stop();
				        stopWatch.setBackgroundColor(Color.GREEN);
				        
				        
				        //call save score
				        long elapsedTime = SystemClock.elapsedRealtime() - stopWatch.getBase();
				        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
				        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
				        String timeElapsed;
				        if(seconds < 10)
				        	timeElapsed = minutes+ ".0" + seconds;
				        else
				        	timeElapsed = minutes+ "." + seconds;
				        
				        
				        //create message box to guide user to main screen
				        AlertDialog.Builder builder = getFinishDialog();
				        builder.show();
				        Util.saveScoreWindow(AlphaGameActivity.this,timeElapsed,gameType);
					}
				}
			}

			private AlertDialog.Builder getFinishDialog() {
				AlertDialog.Builder builder = new AlertDialog.Builder(AlphaGameActivity.this, AlertDialog. THEME_HOLO_LIGHT);
				builder.setMessage("You Win").setTitle("Congratulations");
				builder.setPositiveButton("Goto Main Menu", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				        // User clicked OK button
				    	
				    	Util.displayToast(getApplicationContext(), "Main Menu");
						Intent myIntent = new Intent(AlphaGameActivity.this, MainActivity.class);
						AlphaGameActivity.this.startActivity(myIntent);
						finish();
				    }
				});
				builder.setNegativeButton("View High Scores", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				        // User high Score the dialog
				    	Util.displayToast(getApplicationContext(), "High Score");
						Intent myIntent = new Intent(AlphaGameActivity.this, HighScoreActivity.class);
						AlphaGameActivity.this.startActivity(myIntent);
						finish();
				    }
				});
				builder.setCancelable(false);
				return builder;
			}
        };
        
        gridViewMain.setOnItemClickListener(listner);
        stopWatch.start();
    }

	private void setBlankTileId(List<String> dataList) {
    	for(int i = 0; i <= dataList.size() ; i++) {
    		if (dataList.get(i).trim().length() == 0) {
    			blankTileId = i;
    			return;
    		}
    	}
    	blankTileId = dataList.size() - 1;  // default value if not set above
	}
	
	// To prevent restart of activity on screen orientation change
	public void onConfigurationChanged(Configuration newConfig) {}
}
