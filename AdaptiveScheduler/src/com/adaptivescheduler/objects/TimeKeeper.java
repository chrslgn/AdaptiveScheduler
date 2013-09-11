package com.adaptivescheduler.objects;

/**
 * Keeps track of the start and stop times. Also provides the calculations and metrics for those start and stop times.
 * 
 * @author Chris
 *
 */
public class TimeKeeper {

	/**
	 * The time at which the event started.
	 */
	private long StartTime;
	
	/**
	 * The time at which the event stopped.
	 */
	private long StopTime;
	
	/*
	 * CONSTRUCTORS
	 */
	
	/**
	 * Constructor for the time keeper class when only a start time is known. This is expected to be called when a task has marked as started but not completed.
	 * @param start The time in milliseconds since epoch when this task was started.
	 */
	public TimeKeeper(long start)
	{
		setStartTime(start);
	}
	
	/**
	 * Constructor for the time keeper class when both the start and stop times are known. This is expected to be called when a task has been completed but a start time was not initiated prior to completion.
	 * @param start The time in milliseconds since epoch when this task started.
	 * @param stop The time in milliseconds since epoch when this task ended.
	 */
	public TimeKeeper(long start, long stop)
	{
		setStartTime(start);
		setStopTime(stop);
	}
	
	/*
	 * PUBLIC METHODS
	 */
	
	/**
	 * Gets the time spent from start to stop.
	 * @return The time between the start and stop points in milliseconds.
	 */
	public long timeSpent()
	{
		return StopTime - StartTime;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	
	/**
	 * Set the start value.
	 * @param start The time in milliseconds since epoch when this task started.
	 */	
	public void setStartTime(long start)
	{
		StartTime = start;
	}
	
	/**
	 * Get the start value.
	 * @return The time in milliseconds since epoch when this task started.
	 */
	public long getStartTime()
	{
		return StartTime;
	}
	
	/**
	 * Set the stop value.
	 * @param stop The time in milliseconds since epoch when this task ended.
	 */
	public void setStopTime(long stop)
	{
		StopTime = stop;
	}
	
	/**
	 * Get the stop value.
	 * @return The time in milliseconds since epoch when this task ended.
	 */
	public long getStopTime()
	{
		return StopTime;
	}	
}