package com.emlynoregan.apps.intrinsicalpha;

import com.emlynoregan.apps.intrinsicalpha.classes.WildWork;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WildWorkTopListActivity extends WildWorksBaseActivity 
{
	ListView _lvTopList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toplistlayout);

        _wildWorks.RemoveAllDoNotSaves();
        _wildWorks.Save(this);
        
        _lvTopList = (ListView) findViewById(R.id.lvTopLevel);

        _lvTopList.setOnItemClickListener
        (
        	new OnItemClickListener() {
        		@Override
        		public void onItemClick(AdapterView<?> arg0, View view,
        				int position, long id) 
        		{
        			WildWork lwildWork = (WildWork)arg0.getAdapter().getItem(position);
        			
					Intent lintent = new Intent(WildWorkTopListActivity.this, WildWorkEditActivity.class);
					lintent.putExtra("workId", lwildWork.get_workId().toString());
        			
					startActivityForResult(lintent, 1);
        		}
			}
        );
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.toplistmenu, menu);
	    return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addWork:
                addWork();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	private void addWork() 
	{
		WildWork lwildWork = _wildWorks.AddWork();
		lwildWork.set_doNotSave(true);
		SaveWildWorks();
		
		Intent laddWorkIntent = new Intent(this, WildWorkEditActivity.class);
		laddWorkIntent.putExtra("workId", lwildWork.get_workId().toString());
		
		startActivityForResult(laddWorkIntent, 1);
	}
	
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) 
    {
        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) 
//            {
            	UpdateActivity();
//            }
        }
    }

    @Override
	protected void onResume() {
		super.onResume();
		
		UpdateActivity();
	}
    
    public void UpdateActivity()
    {
		WildWorkListAdapter ladapter = new WildWorkListAdapter(this, _wildWorks.GetAllTopLevelWorks());

		_lvTopList.setAdapter(ladapter);
    }
}
