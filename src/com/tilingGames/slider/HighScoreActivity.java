package com.tilingGames.slider;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class HighScoreActivity extends Activity {

	ListView lv;
	ArrayAdapter<String> as1;
	ArrayList<String> a2=new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		lv = (ListView)findViewById(R.id.l1);
		
		//read the file
		String fileContents = readFile();
		
		if(fileContents != null)
		{
			
			String[] userScores = fileContents.split(",");
			int len = userScores.length;
			String[] temp;
			
			userScores = sortAccordingToTime(userScores);
						
			for(int i =0;i<len;i++){
				temp = userScores[i].split("\\|");
				a2.add("Name : " + temp[0] + "\n \t \t Time : " + temp[1].replace('.', ':'));
			}
			
			
			
			 as1 = new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a2);
	
		 		lv.setAdapter(as1);
		}
		else{
			Util.displayToast(this, "There aren't any scores saved!",true);
			finish();
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

	public String readFile() {
		BufferedReader br = null;
		
		File highscorefile = new File(Environment.getExternalStorageDirectory().getPath() +"/SliderGame/HighScore.txt");
        if(!highscorefile.exists())
        {
        	return null;
        }
        else{
        	StringBuilder fileContents = new StringBuilder();
        	try {
        		 
    			
    			String sCurrentLine;
     
    			br = new BufferedReader(new FileReader(highscorefile));
     
    			while ((sCurrentLine = br.readLine()) != null) {
    				fileContents.append(sCurrentLine);
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
        	
        	return fileContents.toString();
        }
	}
	
	public String[] sortAccordingToTime(String[] userScores){
		int len = userScores.length;
		String temp = "";
		
		for(int i=0;i<len;i++){
			String Score = userScores[i].split("\\|")[1];
			
			for(int j =0;j<len;j++){
				
				
				String compareScore = userScores[j].split("\\|")[1];
				
				if(compareTimeString(Score,compareScore)){
					temp = userScores[i];
					userScores[i]=userScores[j];
					userScores[j]=temp;
				}
				
			}
			
		}
		
		return userScores;
	}
	
	public boolean compareTimeString(String scr1,String scr2){
		
		float score1 = Float.parseFloat(scr1);
		float score2 = Float.parseFloat(scr2);
		
		if(score1<score2)
		{
			return true;
		}
		
		return false;
	}
}
