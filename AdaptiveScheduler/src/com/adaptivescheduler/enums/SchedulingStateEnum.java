package com.adaptivescheduler.enums;

import com.adaptivescheduler.objects.Task;

/**
 * The enumeration to specify the behaviour of the {@link Task}.
 * @author Chris
 *
 */
public enum SchedulingStateEnum {
	
	/**
	 * Repeat the task on specified days. (i.e. MWF)
	 */
	DAILY   (0x00000001), 
	
	/**
	 * Repeat the task on specified days every week. (i.e. every MWF for the next 5 weeks)
	 */
	WEEKLY  (0x00000002),
	
	/**
	 * Repeat the task on specified numerical days every month. (i.e. the 1st of the month and the 15th of the month)
	 */
	MONTHLY (0x00000004), 
	
	/**
	 * Allow the task to be performed at the same time as another task. (i.e. laundry, reading, sewing can all be done at the same time)
	 */
	OVERLAP (0x00000008),
	
	/**
	 * Allow the task to fill in gaps in the schedule based on weight and priority.
	 */
	ADAPTIVE(0x00000010);
	
	/**
	 * The assigned state value.
	 */
	private int State;
	
	/*
	 * CONSTRUCTOR
	 */
	
	/**
	 * The constructor for the enumeration.
	 * @param state The state that is to be held.
	 */
	private SchedulingStateEnum(int state)
	{
		setState(state);
	}

	/*
	 * GETTERS AND SETTERS
	 */
	
	/**
	 * Gets the state.
	 * @return The assigned state value.
	 */
	public int getState() {
		return State;
	}

	/**
	 * Sets the state.
	 * @param state The value to assign to the state.
	 */
	public void setState(int state) {
		State = state;
	}
}