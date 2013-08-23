package com.example.temp;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;

public class EditableListPreference extends DialogPreference {
	
	private EditableList mEditableList = null;
	private String mLastData = null;
	
	public EditableListPreference(Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);
		
	}
	
	
	@Override
	  protected View onCreateDialogView() {
		mEditableList = new EditableList(getContext());
	    return mEditableList;
	  }
	  
	  @Override
	  protected void onBindDialogView(View v) {
	    super.onBindDialogView(v);
	    mEditableList.setData(mLastData);
	  }
	  
	  @Override
	  protected void onDialogClosed(boolean positiveResult) {
	    super.onDialogClosed(positiveResult);

	    if (positiveResult) {
	      if (callChangeListener(mEditableList.getData())) {
	        mLastData=mEditableList.getData();
	        if(mLastData.length()>0)
	        	persistString(mLastData);
	      }
	    }
	  }

	  @Override
	  protected Object onGetDefaultValue(TypedArray a, int index) {
	    return("OPN");
	  }

	  @Override
	  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
	    mLastData=(restoreValue ?getPersistedString(mLastData) : (String)defaultValue);
	  }
}
