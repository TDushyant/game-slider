package com.learnon.games.tiling.slider;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.res.Configuration;
import android.widget.LinearLayout;

public class AdsManager implements AdListener {
    private String publisherId = "your publisher id here";

    public void addAdsView(Activity activity, LinearLayout layout) {
        AdView adView;
        int screenLayout = activity.getResources().getConfiguration().screenLayout;
        if ((screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= 3) {
        	adView = new AdView(activity, AdSize.IAB_LEADERBOARD, publisherId);
        }
        else {
        	adView = new AdView(activity, AdSize.BANNER, publisherId);
        }
        layout.addView(adView);
        AdRequest request = new AdRequest();
        request.addTestDevice(AdRequest.TEST_EMULATOR);
        request.addTestDevice("Your test device id here - Find the id in Log Cat");
        adView.loadAd(request);
   }

	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		
	}
}