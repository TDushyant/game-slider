package com.learnon.games.tiling.slider;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.learnon.games.tiling.slider.R;


public class HighScoreActivity extends FragmentActivity {

	ListView lv;
	ArrayAdapter<String> as1;
	ArrayList<String> a2=new ArrayList<String>();
	String gameType;
	
	
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_high_score_all);
        expListView = (ExpandableListView) findViewById(R.id.exp_listview);
        fillList();
    }

    private void fillList() {
        LinkedHashMap<String, List<HighScoreBean>> input = readFile(); // get the collection here
        if (input == null) {
			Util.displayToast(this, "There aren't any scores saved!",true);
			finish();
        }
        else {
	        HighScoreAdapter adapter = new HighScoreAdapter(LayoutInflater.from(this), input);
	        expListView.setAdapter(adapter);
	    }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public LinkedHashMap<String, List<HighScoreBean>> readFile() {
	    LinkedHashMap<String, List<HighScoreBean>> result = new LinkedHashMap<String, List<HighScoreBean>>();
	    
		BufferedReader br = null;
		
		File highscorefile = new File(Environment.getExternalStorageDirectory().getPath() +"/SliderGame/HighScore.txt");
        if(!highscorefile.exists())
        {
        	return null;
        }
        else{
        	try {
    			String sCurrentLine;
     
    			br = new BufferedReader(new FileReader(highscorefile));
    			HighScoreBean bean;
    			String[] temp;
     
    			while ((sCurrentLine = br.readLine()) != null) {
    				temp = sCurrentLine.split("\\|");
    				bean = new HighScoreBean(temp[0],temp[1],temp[2]);
    				List<HighScoreBean> highScoreList = result.get(temp[0]);
    				if (highScoreList == null) {
    					List<HighScoreBean> scoreList = new ArrayList<HighScoreBean>();
    					scoreList.add(bean);
    					result.put(temp[0], scoreList);
    				}
    				else {
    					highScoreList.add(bean);
    				}
    			}
    			
    			for (List<HighScoreBean> value : result.values()) {
					Collections.sort(value);
				}
     
    		} catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			
    			if(br!=null)
    			{
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
        	
        	return result;
        }
	}
}
