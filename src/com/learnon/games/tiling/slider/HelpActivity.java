package com.learnon.games.tiling.slider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		String helpText = Util.prepareHelpText();
		TextView helpView = (TextView) this.findViewById(R.id.helpText);
		helpView.setText(helpText);
	}
}
