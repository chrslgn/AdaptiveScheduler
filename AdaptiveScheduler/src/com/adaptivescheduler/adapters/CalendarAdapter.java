package com.adaptivescheduler.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter{

	//static finals concat quicker with + symbol
	private static final String ZERO = "0";

	private Context AdapterContext;

	private Calendar Month;

	//calendar instance of previous month for getting complete view
	public GregorianCalendar PrevMonth;

	public GregorianCalendar PrevMonthMaxSet;

	private GregorianCalendar SelectedDate;

	private int FirstDay, MaxWeekNumber, MaxP, CalMaxP, LastWeekDay, LeftDays, MonthLength;

	private String ItemValue, CurrentDateString;

	private DateFormat FormattedDate;

	private ArrayList<String> Items;
	public static List<String> DayString;
	private View PreviousView;

	public CalendarAdapter(Context c, GregorianCalendar monthCalendar)
	{
		CalendarAdapter.DayString = new ArrayList<String>();
		Month = monthCalendar;
		SelectedDate = (GregorianCalendar) monthCalendar.clone();
		AdapterContext = c;
		Month.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Items = new ArrayList<String>();
		FormattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		CurrentDateString = FormattedDate.format(SelectedDate.getTime());
		refreshDays();
	}

	public void setItems(ArrayList<String> items)
	{
		for(int i = 0; i < items.size(); i++)
			if(items.get(i).length() == 1)
				items.set(i, ZERO + items.get(i));

		Items = items;
	}

	@Override
	public int getCount() {
		return DayString.size();
	}

	@Override
	public Object getItem(int position) {
		return DayString.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	//create a new view for each item reference by the Adapter
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		//if it's not recycled, initialze some attributes
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater) AdapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(com.adaptivescheduler.R.layout.calendar_item, null);
		}

		TextView dayView = (TextView) v.findViewById(com.adaptivescheduler.R.id.date);

		//separates daystring into parts
		String[] separatedTime = DayString.get(position).split("-");

		//taking last part of date
		String gridValue = separatedTime[2].replaceFirst("^0*", "");

		//checking whether the day is in the current month or not
		if((Integer.parseInt(gridValue) > 1) && (position < FirstDay))
		{
			//setting offdays to white color.
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}
		else if((Integer.parseInt(gridValue) < 7) && (position > 28))
		{
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}
		else
		{
			//setting the current month's days in blue color
			dayView.setTextColor(Color.BLUE);
		}
		
		if(DayString.get(position).equals(CurrentDateString))
		{
			setSelected(v);
			PreviousView = v;
		}
		else
		{
			v.setBackgroundResource(com.adaptivescheduler.R.drawable.list_item_background);
		}
		dayView.setText(gridValue);
		
		//create date string for comparison
		String date = DayString.get(position);
		
		if(date.length() == 1)
			date = ZERO + date;
		
		String monthStr = "" + (Month.get(GregorianCalendar.MONTH) + 1);
		if(monthStr.length() == 1)
			monthStr = ZERO + monthStr;
		
		//show icon if date is not empty and it exists in the items array
		ImageView iv = (ImageView) v.findViewById(com.adaptivescheduler.R.id.date_icon);
		if(date.length() > 0 && Items != null && Items.contains(date))
			iv.setVisibility(View.VISIBLE);
		else
			iv.setVisibility(View.INVISIBLE);
		
		return v;
	}
	
	public View setSelected(View view)
	{
		if(PreviousView != null)
			PreviousView.setBackgroundResource(com.adaptivescheduler.R.drawable.list_item_background);
		
		PreviousView = view;
		
		view.setBackgroundResource(com.adaptivescheduler.R.drawable.calendar_cel_selectl);
		return view;
	}
	
	public void refreshDays()
	{
		//clear items
		Items.clear();
		DayString.clear();
		PrevMonth = (GregorianCalendar) Month.clone();
		
		//month start day. ie; sun, mon, etc
		FirstDay = Month.get(GregorianCalendar.DAY_OF_WEEK);
		
		//finding number of weeks in current month
		MaxWeekNumber = Month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		
		//allocating maximum row number for the grid view
		MonthLength = getMaxP(); //previous month maximum day 31, 30...
		CalMaxP = MaxP - (FirstDay - 1); //calendar off day starting 24, 25...
		
		/*
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous, current, next) dates.
		 */
		PrevMonthMaxSet = (GregorianCalendar) PrevMonth.clone();
		
		//setting the start date as a previous month's required date
		PrevMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, CalMaxP + 1);
		
		//filling calendar gridview
		for(int i = 0; i < MonthLength; i++)
		{
			ItemValue = FormattedDate.format(PrevMonthMaxSet.getTime());
			PrevMonthMaxSet.add(GregorianCalendar.DATE, 1);
			DayString.add(ItemValue);
		}
	}
	
	private int getMaxP()
	{
		if(Month.get(GregorianCalendar.MONTH) == Month.getActualMinimum(GregorianCalendar.MONTH))
			PrevMonth.set(GregorianCalendar.MONTH, Month.get(GregorianCalendar.MONTH) - 1);
		else
			PrevMonth.set(GregorianCalendar.MONTH, Month.get(GregorianCalendar.MONTH) - 1);
		
		return PrevMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	}

}