package com.tilingGames.slider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridView;

public class NumberGameActivity extends Activity {

	GridView gridViewMain;
	int blankTileId;  // index starts with zero
	int numberOfTiles = 25;
	Chronometer stopWatch;
	int numberOfColumns = 5;
	boolean validMove = true;
	boolean traditional = false;
	
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
				        
				        long elapsedTime = SystemClock.elapsedRealtime() - stopWatch.getBase();
				        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
				        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
				        String timeElapsed = minutes+ "." + seconds;
				        //create message box to guide user to main screen
				        AlertDialog.Builder builder = new AlertDialog.Builder(NumberGameActivity.this);
				        builder.setMessage("You Win").setTitle("Congratulations");
				        builder.setPositiveButton("Goto Main Menu", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				                // User clicked OK button
				            	
				            	Util.displayToast(getApplicationContext(), "Play Game");
				        		Intent myIntent = new Intent(NumberGameActivity.this, GameActivity.class);
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
				        builder.show();
				        

				        //call save score
				        saveScoreWindow(NumberGameActivity.this,timeElapsed);
					}
				}
			}
        };
        
        gridViewMain.setOnItemClickListener(listner);
        stopWatch.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

	public static void saveScoreWindow(Context c,final String timeElapsed){
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage("Please Enter Your name :").setTitle("Save Score");
        final EditText input = new EditText(c);
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	writeFile(input.getText().toString(),timeElapsed);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User high Score the dialog
            	dialog.cancel();
            }
        });
        builder.setCancelable(false);
        builder.show();
		
	}
	
	public static void writeFile(String name,String timeElapsed) {
		
		File dir = new File(Environment.getExternalStorageDirectory().getPath() +"/SliderGame");
        if(!dir.exists())
        	dir.mkdir();
        
        File image = new File(dir +"/HighScore.txt");
        
        try {
        	if(!image.exists())
			image.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try{
        	FileWriter FW = new FileWriter(image,true);
        
        	FW.write(name + "|" + timeElapsed + ",");
        	
        	FW.flush();
        	FW.close();
        	
        	image.setReadOnly();
        }
        catch(Exception e){
        	
        }
		
	}
}
