package com.emlynoregan.apps.intrinsicalpha.classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class WildWorks implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final String fileName = "wildworks.ser";

	List<WildWork> _allWorks = new ArrayList<WildWork>();
	
	UUID _currentWorkId;
	
	public WildWork get_currentWork ()
	{
		return get_wildWorkById(_currentWorkId);
	}
	
	public void set_currentWork(WildWork aCurrentWork)
	{
		if (aCurrentWork != null)
		{
			_currentWorkId = aCurrentWork.get_workId();
		}
	}

	public List<WildWork> get_allWorks() {
		return _allWorks;
	}

	public void set_allWorks(List<WildWork> _allWorks) {
		this._allWorks = _allWorks;
	}

	public UUID get_currentWorkId() {
		return _currentWorkId;
	}

	public void set_currentWorkId(UUID _currentWorkId) {
		this._currentWorkId = _currentWorkId;
	}
	
	public static WildWorks Load(Context aContext)
	{
		WildWorks retval = null;
		
        try
        {
           FileInputStream fileIn = aContext.openFileInput(fileName);
           try
           {
	           ObjectInputStream in = new ObjectInputStream(fileIn);
	           try
	           {
	        	   retval = (WildWorks) in.readObject();
	           }
	           finally
	           {
	        	   in.close();
	           }
           }
           finally
           {
        	   fileIn.close();
           }
        }
		catch(Exception ex)
		{
		      Log.e(WildWorks.class.getSimpleName() + "-Load", ex.toString() );
		}
        
        if (retval == null)
        {
        	Log.w(WildWorks.class.getSimpleName() + "-Load", "Creating new, empty WildWorks");
        	retval = new WildWorks();
        }
        
		return retval;
	}
	
	public void RemoveAllDoNotSaves()
	{
		ArrayList<WildWork> lforDelete = new ArrayList<WildWork>();

		for (WildWork lwildWork : _allWorks)
		{
			if (lwildWork.is_doNotSave())
				lforDelete.add(lwildWork);
		}

		for (WildWork lwildWork : lforDelete)
		{
			DeleteWork(lwildWork);
		}
	}

	public void Save(Context aContext)
	{
//		ArrayList<WildWork> lforDelete = new ArrayList<WildWork>();
//
//		for (WildWork lwildWork : _allWorks)
//		{
//			if (lwildWork.is_doNotSave())
//				lforDelete.add(lwildWork);
//			if (lwildWork._childworks == null)
//				lwildWork._childworks = new ArrayList<WildWork>();
//		}
//		
//		for (WildWork lwildWork : lforDelete)
//		{
//			DeleteWork(lwildWork);
//		}
//		
		try
		{
			FileOutputStream fos = aContext.openFileOutput(fileName, Context.MODE_PRIVATE);
			try
			{
				ObjectOutputStream out = new ObjectOutputStream(fos);
				try
				{
					out.writeObject(this);
				}
				finally
				{
					out.close();
				}
			}
			finally
			{
				fos.close();
			}
		}
		catch(Exception ex)
		{
		      Log.e(this.getClass().getSimpleName() + "-Save", ex.toString() );
		 
		}
	}

	public List<WildWork> GetAllTopLevelWorks()
	{
		List<WildWork> retval = new ArrayList<WildWork>();
		
		for (WildWork lwildWork : _allWorks)
		{
			if (lwildWork.get_parentwork() == null && !lwildWork.is_doNotSave())
			{
				retval.add(lwildWork);
			}
		}
		
		return retval;
	}
	
	public WildWork AddWork() 
	{
		WildWork retval = new WildWork();
		
		_allWorks.add(retval);
		
		return retval;
	}

	public WildWork AddChildToWork(WildWork aWildWork) 
	{
		WildWork retval = new WildWork();
		retval.set_parentwork(aWildWork);
		
		if (aWildWork._childworks == null)
			aWildWork._childworks = new ArrayList<WildWork>();

		aWildWork._childworks.add(retval);
		
		_allWorks.add(retval);
		
		return retval;
	}

	public void AddExistingWork(WildWork aWildWork) 
	{
		_allWorks.add(aWildWork);
	}

	public void DeleteWork(WildWork aWildWork) 
	{
		if (aWildWork != null)
		{
			if (aWildWork.get_parentwork() != null)
			{
				aWildWork.get_parentwork().get_childworks().remove(aWildWork);
			}
			
			
			for (int t = 0; t < _allWorks.size(); t++)
			{
				WildWork lwildWork = _allWorks.get(t);

				if (lwildWork.get_workId().equals(aWildWork.get_workId()))
				{
					_allWorks.remove(t);
					break;
				}
			}
			
			if (_currentWorkId != null && _currentWorkId.equals(aWildWork.get_workId()))
			{
				_currentWorkId = null;
			}
			
			if (aWildWork.get_childworks() != null)
			{
				for (WildWork lchild : aWildWork.get_childworks())
				{
					lchild.set_parentwork(null);
					DeleteWork(lchild);
				}
			}
		}
	}

	public WildWork get_wildWorkById(UUID aWildWorkId) 
	{
		WildWork retval = null;
		
		if (aWildWorkId != null && _allWorks != null)
		{
			for (WildWork lwildWork : _allWorks)
			{
				if (lwildWork.get_workId().equals(aWildWorkId))
				{
					// could cache this if I'm feeling less lazy.
					retval = lwildWork;
					break;
				}
			}
		}
		
		return retval;
	}
}
