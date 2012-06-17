package com.emlynoregan.apps.intrinsicalpha;

import com.emlynoregan.apps.intrinsicalpha.classes.WildType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class WildTypeAdapter extends BaseAdapter implements SpinnerAdapter {

    private LayoutInflater _inflater;

    public WildTypeAdapter(Context aContext) {
         _inflater = LayoutInflater.from(aContext);
    }

    public Object getItem(int position) throws IndexOutOfBoundsException{
        return WildType.values()[position];
    }

    public long getItemId(int position) throws IndexOutOfBoundsException{
        if(position >= getCount() || position < 0 )
        	throw new IndexOutOfBoundsException();
        
        return position;
    }

    public int getCount() {
        return WildType.values().length;
     }

    public int getViewTypeCount(){
        return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	return customGetView(position, convertView, parent, R.layout.wildtypeitemlayout);
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
    	return customGetView(position, convertView, parent, R.layout.wildtypedropdownitemlayout);
    }
    
    private View customGetView(int position, View convertView, ViewGroup parent, int aLayout)
    {
        WildType lwildType = WildType.values()[position];           

        if(convertView == null)
        { 
        	// If the View is not cached
            // Inflates the Common View from XML file
            convertView = this._inflater.inflate(aLayout, parent, false);
        }

        TextView lwildTypeText = (TextView)convertView.findViewById(R.id.txtWildType);
        
        lwildTypeText.setText(lwildType.toString());
        
        return convertView;
    }
    
}
