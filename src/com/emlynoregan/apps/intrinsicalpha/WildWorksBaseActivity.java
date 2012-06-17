package com.emlynoregan.apps.intrinsicalpha;

import com.emlynoregan.apps.intrinsicalpha.classes.WildWorks;

import android.app.Activity;
import android.os.Bundle;

public class WildWorksBaseActivity extends Activity 
{
	protected WildWorks _wildWorks;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoadWildWorks();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	LoadWildWorks();
    }
    
    protected void LoadWildWorks()
    {
    	_wildWorks = WildWorks.Load(this);
    }

    protected void SaveWildWorks()
    {
    	_wildWorks.Save(this);
    }
}
