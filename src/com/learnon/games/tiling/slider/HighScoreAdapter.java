package com.learnon.games.tiling.slider;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighScoreAdapter extends BaseExpandableListAdapter {
	    private LinkedHashMap<String, List<HighScoreBean>> input;

	    private LayoutInflater inflater;

	    public HighScoreAdapter(LayoutInflater inflater, LinkedHashMap<String, List<HighScoreBean>> input) {
	        super();
	        this.input = input;
	        this.inflater = inflater;
	    }

	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return getChildData(groupPosition, childPosition);
	    }

	    private HighScoreBean getChildData(int groupPosition, int childPosition) {
	        String key = getKey(groupPosition);
	        List<HighScoreBean> list = input.get(key);
	        return list.get(childPosition);
	    }

	    private String getKey(int keyPosition) {
	        int counter = 0;
	        Iterator<String> keyIterator = input.keySet().iterator();
	        while (keyIterator.hasNext()) {
	            String key = keyIterator.next();
	            if (counter++ == keyPosition) {
	                return key;
	            }
	        }
	        // will not be the case ...
	        return null;
	    }

//	    @Override
//	    public long getChildId(int groupPosition, int childPosition) {
//	        return getChildData(groupPosition, childPosition).getId();
//	    }

	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	        LinearLayout layoutView = null;
	        if (convertView == null) {
	            // inflate what you need, for testing purposes I am using android
	            // built-in layout
	        	layoutView = (LinearLayout) inflater.inflate(R.layout.activity_high_score, parent, false);
	        } else {
	        	layoutView = (LinearLayout) convertView;
	        }
	        HighScoreBean highScoreBean = getChildData(groupPosition, childPosition);
	        //TextView tv1 = (TextView) findViewById(R.id.hsName);
	        TextView tv1 = (TextView) layoutView.getChildAt(0);
	        TextView tv2 = (TextView) layoutView.getChildAt(1);
	        tv1.setText(highScoreBean.getName());
	        tv2.setText(highScoreBean.getTime());
	        return layoutView;
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) {
	        String key = getKey(groupPosition);
	        return input.get(key).size();
	    }

	    @Override
	    public Object getGroup(int groupPosition) {
	        return getKey(groupPosition);
	    }

	    @Override
	    public int getGroupCount() {
	        return input.size();
	    }

	    @Override
	    public long getGroupId(int groupPosition) {
	        return 0;
	    }

	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	            ViewGroup parent) {
	        TextView simpleTextView = null;
	        if (convertView == null) {
	            // inflate what you need, for testing purposes I am using android
	            // built-in layout
	            simpleTextView = (TextView) inflater.inflate(android.R.layout.simple_list_item_1,
	                    parent, false);
	        } else {
	            simpleTextView = (TextView) convertView;
	        }
	        simpleTextView.setText(getKey(groupPosition));
	        simpleTextView.setBackgroundColor(Color.GRAY);
	        return simpleTextView;
	    }

	    @Override
	    public boolean hasStableIds() {
	        // TODO Auto-generated method stub
	        return false;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

}

