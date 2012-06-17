package com.emlynoregan.apps.intrinsicalpha;

import java.util.UUID;

import com.emlynoregan.apps.intrinsicalpha.classes.WildType;
import com.emlynoregan.apps.intrinsicalpha.classes.WildWork;
import com.emlynoregan.apps.intrinsicalpha.classes.WildWorks;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WildWorkEditActivity extends WildWorksBaseActivity
{
	UUID _wildWorkId;
	
	EditText _edWorkName;
	Spinner _spnWorkType;
	CheckBox _cbComplete;
	EditText _edIcecream;
	TextView _txtNotes;
	EditText _edNotes;
	TextView _txtParentName;
	ListView _lvChildren;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlayout);
        
        _edWorkName = (EditText) findViewById(R.id.edWorkName);
        _spnWorkType = (Spinner) findViewById(R.id.spnWorkType);
        _cbComplete = (CheckBox) findViewById(R.id.cbComplete);
        _edIcecream = (EditText) findViewById(R.id.edIceCream);
        _txtNotes = (TextView) findViewById(R.id.txtNotes);
        _edNotes = (EditText) findViewById(R.id.edNotes);
        _txtParentName = (TextView) findViewById(R.id.txtParentName);
        _lvChildren = (ListView) findViewById(R.id.lvChildren);
        
        GetWildWorkId(getIntent(), savedInstanceState);
        
        _edIcecream.setOnFocusChangeListener(
        	new OnFocusChangeListener() 
        	{
				@Override
				public void onFocusChange(View v, boolean hasFocus) 
				{
					if (!hasFocus)
						validateForm();
				}
        	}
        );
        
        _txtParentName.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
        		activityToWildWork();
        		SaveWildWorks();

        		Intent lintent = getIntent();
        		lintent.putExtra("workId", get_WildWork().get_workId().toString());
        		setResult(RESULT_OK, lintent);
        		finish();
        		
//        		Intent lintent = new Intent(WildWorkEditActivity.this, WildWorkEditActivity.class);
//        		lintent.putExtra("workId", get_WildWork().get_parentwork().get_workId().toString());
//        		startActivity(lintent);
			}
		});
        
        _txtNotes.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				_txtNotes.setVisibility(View.GONE);
				_edNotes.setVisibility(View.VISIBLE);
				_edNotes.requestFocus();
			}
		});
        
        _edNotes.setOnFocusChangeListener(new OnFocusChangeListener() 
        {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
				{
					_txtNotes.setText(_edNotes.getText().toString());
					_txtNotes.setVisibility(View.VISIBLE);
					_edNotes.setVisibility(View.GONE);
				}
			}
		});

        _lvChildren.setOnItemClickListener
        (
        	new OnItemClickListener() {
        		@Override
        		public void onItemClick(AdapterView<?> arg0, View view,
        				int position, long id) 
        		{
        			WildWork lwildWork = (WildWork)arg0.getAdapter().getItem(position);
        			
					Intent lintent = new Intent(WildWorkEditActivity.this, WildWorkEditActivity.class);
					lintent.putExtra("workId", lwildWork.get_workId().toString());
        			
					startActivityForResult(lintent, 1);
        		}
			}
        );
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.editmenu, menu);
	    return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addWork:
            {
            	if (get_WildWork() != null)
            	{
            		activityToWildWork();
            		WildWork lchildWork = _wildWorks.AddChildToWork(get_WildWork());
            		lchildWork.set_doNotSave(true);
            		SaveWildWorks();
            		
            		Intent lintent = new Intent(this, WildWorkEditActivity.class);
            		lintent.putExtra("workId", lchildWork.get_workId().toString());
            		startActivity(lintent);
            		return true;
            	}
            }
            case R.id.saveWork:
            {
            	if (get_WildWork() != null)
            	{
            		activityToWildWork();
            		SaveWildWorks();
            		Intent lintent = getIntent();
            		lintent.putExtra("workId", get_WildWork().get_workId().toString());
            		setResult(RESULT_OK, lintent);
            		finish();
            		return true;
            	}
            }
            case R.id.deleteWork:
            {
            	if (get_WildWork() != null)
            	{
            		_wildWorks.DeleteWork(get_WildWork());
            		SaveWildWorks();
            		setResult(RESULT_CANCELED);
            		finish();
            		return true;
            	}
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) 
    {
    	super.onSaveInstanceState(outState);

        SaveWildWork(outState);
    }
    
    private WildWork get_WildWork()
    {
    	WildWork retval = null;
    	
    	if (_wildWorks != null && _wildWorkId != null)
    	{
    		retval = _wildWorks.get_wildWorkById(_wildWorkId);
    	}
    	
    	return retval;
    }

    private void GetWildWorkId(Intent aIntent, Bundle savedInstanceState) 
    {
    	if (_wildWorkId == null)
    	{
        	if (savedInstanceState != null && savedInstanceState.containsKey("workId"))
        	{
        		_wildWorkId = UUID.fromString(savedInstanceState.getString("workId"));
        	}
        	else if (aIntent != null && aIntent.getExtras() != null && aIntent.getExtras().containsKey("workId"))
        	{
        		_wildWorkId = UUID.fromString(aIntent.getExtras().getString("workId"));
        	}
        	else
        	{
        		WildWork lnewWildWork = _wildWorks.AddWork();
        		lnewWildWork.set_doNotSave(true);
        	}
    	}
	}

	private void SaveWildWork(Bundle outState) 
	{
		if (outState != null && _wildWorkId != null)
		{
    		outState.putString("workId", _wildWorkId.toString());
		}
	}

	@Override
    protected void onResume() 
    {
    	super.onResume();
    	
        GetWildWorkId(getIntent(), null);
        
    	WildTypeAdapter ladapter = new WildTypeAdapter(this);

    	WildWork lwildWork = get_WildWork();
    	
    	_edWorkName.setText(lwildWork.get_workname());
        _spnWorkType.setAdapter(ladapter);

        int lposition = 0;
        for (lposition = 0; lposition < WildType.values().length; lposition++)
        {
        	if (WildType.values()[lposition].equals(lwildWork.get_wildtype()))
        		break;
        }
        _spnWorkType.setSelection(lposition);
        _cbComplete.setChecked(lwildWork.is_complete());
        _edIcecream.setText(Double.toString(lwildWork.get_icecream()));
        _edNotes.setText(lwildWork.get_notes());
        _txtNotes.setText(lwildWork.get_notes());
        if (lwildWork.get_parentwork() == null)
        {
        	_txtParentName.setVisibility(View.INVISIBLE);
        }
        else
        {
        	_txtParentName.setVisibility(View.VISIBLE);
        	_txtParentName.setText(lwildWork.get_parentwork().get_workname());
        }

		WildWorkListAdapter ladapter2 = new WildWorkListAdapter(this, lwildWork.GetChildren());

		_lvChildren.setAdapter(ladapter2);
    }

	protected void validateForm()
	{
		try
		{
			Double.parseDouble(_edIcecream.getText().toString());
			_edIcecream.setTextColor(Color.BLACK);
		}
		catch (Exception ex)
		{
			_edIcecream.setTextColor(Color.RED);
		}
	}

	protected void activityToWildWork()
	{
		WildWork lwildWork = get_WildWork();
		if (lwildWork != null)
		{
			lwildWork.set_workname(_edWorkName.getText().toString());
			int ltypePos = _spnWorkType.getSelectedItemPosition();
			
			lwildWork.set_wildtype(WildType.values()[ltypePos]);
			lwildWork.set_complete(_cbComplete.isChecked());
			try
			{
				double ldblIcecream = Double.parseDouble(_edIcecream.getText().toString());
				lwildWork.set_icecream(ldblIcecream);
			}
			catch (Exception ex)
			{
				// do nothing
			}
			lwildWork.set_notes(_edNotes.getText().toString());
			
			lwildWork.set_doNotSave(false);
		}
	}
}
