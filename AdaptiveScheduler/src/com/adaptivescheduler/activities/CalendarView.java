package com.adaptivescheduler.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaptivescheduler.adapters.CalendarAdapter;

public class CalendarView extends Activity{
	
	//calendar instances
	public Calendar Month, ItemMonth;

	//adapter instance
	public CalendarAdapter Adapter;
	
	//for grabbing some event values for showing the dot marker
	public Handler AdapterHandler; 
	
	//container to store calendar items which neeeds showing the event marker
	public ArrayList<String> Items;
	
	public Runnable calendarUpdater = new Runnable() {
		
		@Override
		public void run() {
			Items.clear();
			
			//print dates of the current week
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String itemValue;
			for(int i = 0; i < 7; i++)
			{
				itemValue = df.format(ItemMonth.getTime());
				ItemMonth.add(Calendar.DATE, 1);
				Items.add("2012-09-12");
				Items.add("2012-10-07");
				Items.add("2012-10-15");
				Items.add("2012-10-20");
				Items.add("2012-11-30");
				Items.add("2012-11-28");
			}
			
			Adapter.setItems(Items);
			Adapter.notifyDataSetChanged();
		}
	};

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(com.adaptivescheduler.R.layout.calendar);
		
		Month = Calendar.getInstance();
		ItemMonth = (Calendar) Month.clone();
		
		Items = new ArrayList<String>();
		Adapter = new CalendarAdapter(this, (GregorianCalendar) Month);
		
		GridView gridView = (GridView) findViewById(com.adaptivescheduler.R.id.gridview);
		gridView.setAdapter(Adapter);
		
		AdapterHandler = new Handler();
		AdapterHandler.post(calendarUpdater);
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", Month));
		
		RelativeLayout previous = (RelativeLayout) findViewById(com.adaptivescheduler.R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});
		
		RelativeLayout next = (RelativeLayout) findViewById(com.adaptivescheduler.R.id.next);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.DayString.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridValueString = separatedTime[2].replaceFirst("^0*", ""); // taking last part of date
				int gridValue = Integer.parseInt(gridValueString);
				
				// navigate to next or previous month on clicking offdays
				if((gridValue > 10) && (position < 8))
				{
					setPreviousMonth();
					refreshCalendar();
				}
				else if((gridValue < 7) && (position > 28))
				{
					setNextMonth();
					refreshCalendar();
				}
				
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				
				showToast(selectedGridDate);
			}
		});
	}
	
	protected void setNextMonth()
	{
		if(Month.get(Calendar.MONTH) == Month.getActualMaximum(Calendar.MONTH))
			Month.set((Month.get(Calendar.YEAR) + 1), Month.getActualMinimum(Calendar.MONTH), 1);
		else
			Month.set(Calendar.MONTH, Month.get(Calendar.MONTH) + 1);
	}
	
	protected void setPreviousMonth()
	{
		if(Month.get(Calendar.MONTH) == Month.getActualMinimum(Calendar.MONTH))
			Month.set((Month.get(Calendar.YEAR) - 1), Month.getActualMaximum(Calendar.MONTH), 1);
		else
			Month.set(Calendar.MONTH, Month.get(Calendar.MONTH) - 1);
	}
	
	protected void showToast(String string)
	{
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
	
	public void refreshCalendar()
	{
		TextView title = (TextView) findViewById(R.id.title);
		
		Adapter.refreshDays();
		Adapter.notifyDataSetChanged();
		AdapterHandler.post(calendarUpdater); // generate some calendar items
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", Month));
	}
}