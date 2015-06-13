package com.learnon.games.tiling.slider;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class NumberGameActivity extends Activity {

	GridView gridViewMain;
	int blankTileId;  // index starts with zero
	int numberOfTiles = 25;
	Chronometer stopWatch;
	int numberOfColumns = 5;
	boolean validMove = true;
	boolean traditional = false;
	String gameType = "";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        stopWatch = (Chronometer) findViewById(R.id.stopWatch);
        //stopWatch.setBackgroundColor(Color.GRAY);
        gridViewMain = (GridView) findViewById(R.id.gridView1);
//        gridViewMain.setBackgroundResource(R.drawable.wall_paper);
        
        // create dataList and set it to gridView using adapter
        Intent intent = getIntent();
        int  numberOfTiles = intent.getIntExtra("noOfTiles", 25);
        traditional = intent.getBooleanExtra("traditional", false);
        if (traditional) {
        	gameType = "Numeric - " + numberOfTiles +" Tiles - Challanging" ;
        }
        else {
        	gameType = "Numeric - " + numberOfTiles +" Tiles - Easy" ;
        }
        
        List<String> dataList = Util.prepareNumberList(numberOfTiles);
        setBlankTileId(dataList);
        numberOfColumns = (int)(Math.sqrt(numberOfTiles));
        gridViewMain.setNumColumns(numberOfColumns);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_layout,dataList);
        	     //android.R.layout.simple_selectable_list_item,dataList);
        gridViewMain.setAdapter(adapter);
        
        // on click lister
        OnItemClickListener listner = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				 String timeElapsed = "";
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
				        stopWatch.stop();
				        stopWatch.setBackgroundColor(Color.GREEN);
						Util.displayToast(getApplicationContext(), "You Win", true);
				        
				        //Time Calculations.
				        long elapsedTime = SystemClock.elapsedRealtime() - stopWatch.getBase();
				        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
				        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
				        if(seconds < 10)
				        	timeElapsed = minutes+ ".0" + seconds;
				        else
				        	timeElapsed = minutes+ "." + seconds;	
				        
				        //create message box to guide user to main screen
				        AlertDialog.Builder builder = getFinishDialog();
				        builder.show();
				        

				        //call save score
				        Util.saveScoreWindow(NumberGameActivity.this,timeElapsed,gameType);
					}
				}
			}

			private AlertDialog.Builder getFinishDialog() {
				AlertDialog.Builder builder = new AlertDialog.Builder(NumberGameActivity.this, AlertDialog. THEME_HOLO_LIGHT);
				builder.setMessage("You Win").setTitle("Congratulations");
				builder.setPositiveButton("Goto Main Menu", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				        // User clicked OK button
				    	
				    	Util.displayToast(getApplicationContext(), "Main Menu");
						Intent myIntent = new Intent(NumberGameActivity.this, MainActivity.class);
						NumberGameActivity.this.startActivity(myIntent);
						finish();
				    }
				});
				builder.setNegativeButton("View High Scores", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				        // User high Score the dialog
				    	Util.displayToast(getApplicationContext(), "High Score");
						Intent myIntent = new Intent(NumberGameActivity.this, HighScoreActivity.class);
						NumberGameActivity.this.startActivity(myIntent);
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
	public void onConfigurationChanged(Configuration newConfig) {
		//System.out.println("Change in screen orientation");
	}

	
}
