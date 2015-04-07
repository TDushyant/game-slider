package com.tilingGames.slider;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
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
	public void onConfigurationChanged(Configuration newConfig) {}
}
