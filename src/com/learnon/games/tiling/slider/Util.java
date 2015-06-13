package com.learnon.games.tiling.slider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class Util {
	private static int CAPITAL_BASE = 64;
	private static int SMALL_BASE = 96;
	private static boolean displayToastFlag = false;
	private static List <String> originalDataList = new ArrayList<String>();
	
    public static void displayToast(Context context,CharSequence cs) {
    	displayToast(context, cs, displayToastFlag);
    }
    
    public static void displayToast(Context context,CharSequence cs, boolean flag) {
    	if (flag) {
    		Toast.makeText(context,cs, Toast.LENGTH_SHORT).show();
    	}
    }

    public static boolean isSolved(ArrayAdapter<CharSequence> adapter) {
		boolean solved = true;
		for (int i = 0; i < originalDataList.size(); i++){
			if (!  adapter.getItem(i).equals(originalDataList.get(i))) {
				solved = false;
				break;
			}
		}
		return solved;
	}
    
    public static List<String> prepareNumberList(int length) {
    	List<String> dataList = new ArrayList<String>();
    	for(int i = 1; i < length ; i++) {
    		dataList.add(String.valueOf(i));
    	}
    	dataList.add("");
    	originalDataList.clear();
    	originalDataList.addAll(dataList);
    	Collections.shuffle(dataList);
    	return dataList;
    }

	public static List<String> prepareAlphaList(int length, int alphaOption) {
    	List<String> dataList = new ArrayList<String>();
    	int base = CAPITAL_BASE;
    	if (alphaOption == 2) {
    		base = SMALL_BASE; // for small letters
    	}
    	Random rand = null;
    	if (alphaOption == 3) {
    		rand = new Random();
    	}

    	for(int i = 1; i <= length ; i++) {
    		// since we have 24 slot to fill 26 chars (A-Z)
    		// Tile 23 & 24 will be filled with two chars 
    		// Tile 23 = "W, X" & Tile 24 = "Y, Z"
    		if (alphaOption == 3) {
    			base = randInt(rand);
    		}
    		if (i == 23) {
    			dataList.add(Character.toString((char)(base+i)) + ", " + Character.toString((char)(base+i+1)));
    		}
    		else if (i == 24) {
    			dataList.add(Character.toString((char)(base+i+1)) + ", " + Character.toString((char)(base+i+2)));
    		}
    		else {
    			dataList.add(Character.toString((char)(base+i)));
    		}
    	}
    	dataList.add("");
    	originalDataList.clear();
    	originalDataList.addAll(dataList);
    	Collections.shuffle(dataList);
    	return dataList;
	}

	public static String prepareHelpText() {
		StringBuffer sb = new StringBuffer();
		sb.append("Play this game by sliding tile to open slot.\n\n")
		.append("When tiles are arranged in correct order you win.\n\n")
		.append("You have options of playing game with numbers or alphabets.\n\n")
		.append("Number game has three options to play with 9, 16 or 25 tiles.\n\n")
		.append("Alphabet game has three options to play with CAPITAL, small or mIxED letters.\n\n")
		.append("Moreover there are two variants of the game.\n")
		.append(" 1. Easy: Any tile can be moved to open slot.\n")	
		.append(" 2. Challanging: Only adjacent tile can be moved to open slot.\n\n");
		return sb.toString();
	}
	
	public static boolean isValidMove(int numberOfColumns, int blankTileId, int position) {
		blankTileId++;
		position++;
		// TODO Auto-generated method stub
		int blankRowId = blankTileId / numberOfColumns;
		int blankColId = blankTileId % numberOfColumns;
		if (blankColId == 0) {
			blankColId = numberOfColumns;
			blankRowId--;
		}
		int positionRowId = position / numberOfColumns;
		int positionColId = position % numberOfColumns;
		if (positionColId == 0) {
			positionColId = numberOfColumns;
			positionRowId--;
		}
		if ((Math.abs(blankRowId - positionRowId) == 1 &&  blankColId == positionColId)
				|| (Math.abs(blankColId - positionColId) == 1 && blankRowId == positionRowId)) {
			return true;
		}
		return false;
	}

	public static int swapTiles(ArrayAdapter<CharSequence> adapter,	int blankTileId, int position) {
		CharSequence firstItem = (CharSequence) adapter.getItem(blankTileId);
		CharSequence secondItem = (CharSequence) adapter.getItem(position);
		adapter.remove(firstItem);
		adapter.insert(firstItem, position);
		adapter.remove(secondItem);
		adapter.insert(secondItem, blankTileId);
		blankTileId = position;
		adapter.notifyDataSetChanged();
		return blankTileId;
	}

	public static int randInt(Random rand) {
		int min = 1;
		int max = 50;
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    int returnInt = 0;
	    if (randomNum % 2 == 0) {
	    	returnInt =  CAPITAL_BASE;
	    }
	    else {
	    	returnInt =  SMALL_BASE;
	    }
	    return returnInt;
	}

	public static void saveScoreWindow(Activity act,	final String timeElapsed , final String gameType) {
			AlertDialog.Builder builder = new AlertDialog.Builder(act, AlertDialog. THEME_HOLO_DARK);
	        builder.setMessage("Please Enter Your name :").setTitle("Save Score");
	        //builder.setBackgroundDrawable(this.getApplicationContext().getResources().getDrawable(R.id.customButton));

	        final EditText input = new EditText(act);
	        builder.setView(input);
	        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	HighScoreBean scoreBean = new HighScoreBean(gameType, input.getText().toString(),timeElapsed);
	                // User clicked OK button
	            	writeFile(scoreBean.toString());
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
	public static void writeFile(String record) {
		
		File dir = new File(Environment.getExternalStorageDirectory().getPath() +"/SliderGame");
        if(!dir.exists())
        	dir.mkdir();
        
        File file = new File(dir +"/HighScore.txt");
        
        try {
        	if(!file.exists())
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try{
        	FileWriter fw = new FileWriter(file,true);
        
        	fw.write(record+"\n");
        	
        	fw.flush();
        	fw.close();
        	
        	file.setReadOnly();
        }
        catch(Exception e){
        	
        }
		
	}
	
    public static void sortLine() throws Exception {
	        BufferedReader reader = new BufferedReader(new FileReader("fileToRead"));
	        Map<String, String> map=new TreeMap<String, String>();
	        String line="";
	        while((line=reader.readLine())!=null){
	        	map.put(getField(line),line);
	        }
	        reader.close();
	        FileWriter writer = new FileWriter("fileToWrite");
	        for(String val : map.values()){
	        	writer.write(val);	
	        	writer.write('\n');
	        }
	        writer.close();
	    }

	    private static String getField(String line) {
	    	return line.split(" ")[0];//extract value you want to sort on
	    }
}
