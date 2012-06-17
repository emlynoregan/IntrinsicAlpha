package com.emlynoregan.apps.intrinsicalpha;

import java.util.List;

import com.emlynoregan.apps.intrinsicalpha.classes.WildWork;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WildWorkListAdapter extends BaseAdapter {

    private LayoutInflater _inflater;
    private List<WildWork> _wildWorkList;

    public WildWorkListAdapter(Context aContext, List<WildWork> aWildWorkList) {
         _inflater = LayoutInflater.from(aContext);
         _wildWorkList = aWildWorkList;
    }

    public Object getItem(int position) throws IndexOutOfBoundsException{
        return _wildWorkList.get(position);
    }

    public long getItemId(int position) throws IndexOutOfBoundsException{
        if(position >= getCount() || position < 0 )
        	throw new IndexOutOfBoundsException();
        
        return position;
    }

    public int getCount() {
        return _wildWorkList.size();
     }

    public int getViewTypeCount(){
        return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {
        WildWork lwildWork = _wildWorkList.get(position);           

        if(convertView == null)
        { 
        	// If the View is not cached
            // Inflates the Common View from XML file
            convertView = this._inflater.inflate(R.layout.wildworkitemlayout, parent, false);
        }

        TextView lworkName = (TextView)convertView.findViewById(R.id.txtWorkName);
        
        lworkName.setText(lwildWork.get_workname());
        
        return convertView;
     }
}
