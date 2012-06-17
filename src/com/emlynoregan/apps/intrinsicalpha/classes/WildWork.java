package com.emlynoregan.apps.intrinsicalpha.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class WildWork implements Serializable
{
	private static final long serialVersionUID = 1L;

	UUID _workId;
	String _workname;
	WildType _wildtype;
	boolean _complete;
	List<WildWork> _childworks = new ArrayList<WildWork>();
	WildWork _parentwork;
	double _icecream;
	String _notes;
	Date _created;
	Date _lastTouched; // last time the user touched it (complete, fail, ...)
	boolean _doNotSave = false;
	
	public boolean is_doNotSave() {
		return _doNotSave;
	}

	public void set_doNotSave(boolean _doNotSave) {
		this._doNotSave = _doNotSave;
	}

	public WildWork()
	{
		_workId = UUID.randomUUID(); // should be overwritten by deserialized value if this is deserialization
	}
	
	public UUID get_workId() {
		return _workId;
	}

	public String get_workname() {
		if (_workname == null)
			return "Unnamed " + _workId.toString();
		else
			return _workname;
	}
	public void set_workname(String _workname) {
		this._workname = _workname;
	}
	public WildType get_wildtype() {
		return (_wildtype == null?WildType.Explore:_wildtype);
	}
	public void set_wildtype(WildType _wildtype) {
		this._wildtype = _wildtype;
	}
	public boolean is_complete() {
		return _complete;
	}
	public void set_complete(boolean _complete) {
		this._complete = _complete;
	}
	public List<WildWork> get_childworks() {
		return _childworks;
	}
//	public void set_childworks(List<WildWork> _childworks) {
//		this._childworks = _childworks;
//	}
	public WildWork get_parentwork() {
		return _parentwork;
	}
	public void set_parentwork(WildWork _parentwork) {
		this._parentwork = _parentwork;
	}
	public double get_icecream() {
		return _icecream;
	}
	public void set_icecream(double _icecream) {
		this._icecream = _icecream;
	}
	public String get_notes() {
		return _notes;
	}
	public void set_notes(String _notes) {
		this._notes = _notes;
	}
	public Date get_created() {
		return _created;
	}
//	public void set_created(Date _created) {
//		this._created = _created;
//	}
	public Date get_lastTouched() {
		return _lastTouched;
	}
//	public void set_lastTouched(Date _lastTouched) {
//		this._lastTouched = _lastTouched;
//	}
	
	public List<WildWork> GetChildren()
	{
		List<WildWork> retval = new ArrayList<WildWork>();
		
		if (_childworks != null)
		{
			for (WildWork lchild : _childworks)
			{
				if (!lchild._doNotSave)
					retval.add(lchild);
			}
		}
		
		return retval;
	}
}
