package com.tilingGames.slider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.widget.ArrayAdapter;
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
		.append("\t1. Easy:               Any tile can be moved to open slot.\n")	
		.append("\t2. Challanging: Only adjacent tile can be moved to open slot.\n\n");
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

	public static String prepareAboutText() {
		StringBuffer sb = new StringBuffer();
		sb.append("About LeanOn Solutions.\n\n")
		.append("\t Purpose is to provide game with learning opportunity.\n\n");
		return sb.toString();
	}
}
