package com.ayalamart.adapter;

import java.util.ArrayList;

import com.ayalamart.administrador.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class PostAdapter extends BaseAdapter {

	static class ViewHolder
	{
		TextView tvNombreIng;
		CheckBox cb;
	}

	private static final String TAG = "AdapterIngr";
	private static int convertViewCounter = 0;

	private ArrayList<PostData> data;
	private LayoutInflater inflater = null;

	public PostAdapter(Context c, ArrayList<PostData> d)
	{
		//Log.v(TAG, "Constructing CustomAdapter");

		this.data = d;
		inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount()
	{
		//Log.v(TAG, "in getCount()");
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		//Log.v(TAG, "in getItem() for position " + position);
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		//Log.v(TAG, "in getItemId() for position " + position);
		return position;
	}

	@Override
	public int getViewTypeCount()
	{
		//Log.v(TAG, "in getViewTypeCount()");
		return 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		//Log.v(TAG, "in getItemViewType() for position " + position);
		return 0;
	}

	@Override
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		ViewHolder holder;

		//Log.v(TAG, "in getView for position " + position + ", convertView is "
		//		+ ((convertView == null) ? "null" : "being recycled"));

		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.list_row_posts, null);

			convertViewCounter++;
         //   Log.v(TAG, convertViewCounter + " convertViews have been created");
			
			holder = new ViewHolder();


			holder.tvNombreIng = (TextView) convertView
					.findViewById(R.id.tvNombreIng);
			holder.cb = (CheckBox) convertView.findViewById(R.id.cbescogido);
			holder.cb.setOnClickListener(checkListener);
			convertView.setTag(holder);

		} else
			holder = (ViewHolder) convertView.getTag();

		// Para porde hacer click en el checkbox
		PostData d = (PostData) getItem(position);
		holder.cb.setTag(d);
		// Setting all values in listview	
		holder.tvNombreIng.setText(data.get(position).getNombres());
		holder.cb.setChecked(data.get(position).getChecked());
		return convertView;
	}

	public void setCheck(int position)
	{
		PostData d = data.get(position);

		d.setChecked(!d.getChecked());
		notifyDataSetChanged();
	}

	public void checkAll(boolean state)
	{
		for (int i = 0; i < data.size(); i++)
			data.get(i).setChecked(state);
	}

	public void cancelSelectedPost()
	{

		int i = 0;
		while (i < getCount())
		{
			if (data.get(i).getChecked())
			{
				data.remove(data.indexOf(data.get(i)));
			} else
				i++;
		}
		notifyDataSetChanged();

	}

	public boolean haveSomethingSelected()
	{
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).getChecked())
				return true;
		return false;
	}

	private OnClickListener checkListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			PostData d = (PostData) v.getTag();
			d.setChecked(!d.getChecked());
		}
	};
}
