package com.example.temp;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditableList extends RelativeLayout {
	
	private ArrayList<String> mListItems = null;
	private ListView mListView = null;
	private EditText mAddText = null;
	private Button mAdd = null;
	private EditableListAdapter mEditableListAdapter = null;

	public EditableList(Context context) {
		super(context);
		initEditableList(null);
	}

	public EditableList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initEditableList(attrs);
	}

	public EditableList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initEditableList(attrs);
	}
	
	private void initEditableList(AttributeSet attrs) {
		LayoutInflater mLayoutInflater = ((Activity)getContext()).getLayoutInflater();
		
		View mRootView = mLayoutInflater.inflate(R.layout.editablelist, this, true);
		mListView = (ListView) mRootView.findViewById(R.id.list);
		
		mAddText = (EditText) mRootView.findViewById(R.id.addValue);
		mAdd = (Button) mRootView.findViewById(R.id.add);
		mAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAddText.getText().length()<1) return;
				mListItems.add(mAddText.getText().toString());
				mAddText.setText("");
				mEditableListAdapter.notifyDataSetChanged();
			}
		});
		
		
		
	}
	
	public String getData() {
		return EditableList.join(mListItems, "-");
	}
	
	public void setData(String value) {
		if(value.length()>0) {
			String values[] = value.split("-");
			mListItems = new ArrayList<String>(Arrays.asList(values));
			mEditableListAdapter = new EditableListAdapter(getContext(), R.layout.item_row, mListItems);
			mListView.setAdapter(mEditableListAdapter);
			mEditableListAdapter.notifyDataSetChanged();
		} else {
			mListItems = new ArrayList<String>();
			mEditableListAdapter = new EditableListAdapter(getContext(), R.layout.item_row, mListItems);
			mListView.setAdapter(mEditableListAdapter);
			mEditableListAdapter.notifyDataSetChanged();
		}
		
	}
	
	
	public static String join(AbstractCollection<String> s, String delimiter) {
	    if (s == null || s.isEmpty()) return "";
	    Iterator<String> iter = s.iterator();
	    StringBuilder builder = new StringBuilder(iter.next());
	    while( iter.hasNext() )
	    {
	        builder.append(delimiter).append(iter.next());
	    }
	    return builder.toString();
	}
	private class EditableListAdapter extends ArrayAdapter<String> {

		private ArrayList<String> listItems = null;
		public EditableListAdapter(Context context, int resource, ArrayList<String> objects) {
			super(context, resource, objects);
			listItems = objects;
		}
		
		@Override
        public View getView(final int position, View convertView, ViewGroup parent) 
        {
			
			View row = null;
			LayoutInflater mLayoutInflater = ((Activity)getContext()).getLayoutInflater();

            row = mLayoutInflater.inflate(R.layout.item_row, parent, false);

            TextView value = (TextView) row.findViewById(R.id.text);
            ImageButton deleteButton = (ImageButton) row.findViewById(R.id.delete);
            value.setText(listItems.get(position));
            deleteButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	deleteValue(listItems.get(position));
                    }
                }
            );
            return(row);
			
        }
		
	}
	
	private void deleteValue(String value) {
		mListItems.remove(value);
		mEditableListAdapter.notifyDataSetChanged();
	}
	
}
