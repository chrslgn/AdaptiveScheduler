package com.adaptivescheduler.objects;

import java.util.Vector;

import android.graphics.Color;

import com.adaptivescheduler.enums.SchedulingStateEnum;

/**
 * The task is a set goal or event on which the user needs to work. It can have a deadline or be a specified number of occurrences. The task will keep track of how much time is expected to be spent as well as how much time has been spent and when that time was spent.
 * @author Chris
 *
 */
public class Task {

	/**
	 * How easy something can be re-arranged or moved. Tasks with a low weight number are more flexible than tasks with a higher weight number. (i.e. weight 1 is a very flexible task that can be done at any time, but a weight INT_MAX has to be done at the specified time)
	 */
	private int Weight;
	
	/**
	 * How important it is that a task be completed. Tasks with a low priority number are expected to be completed before tasks with a high priority number. (i.e. priority 2 is expected to be completed before priority 5)
	 */
	private int Priority;
	
	/**
	 * The shortest increment of time which can be dedicated to working the task. This is specified in minutes. (i.e. if a task can be completed over 30 minute intervals such as reading)
	 */
	private int ShortestIncrement;
	
	/**
	 * When the task needs to be completed. Specified as the time in milliseconds since epoch that the task needs to be completed by.
	 */
	private long Deadline;
	
	/**
	 * Which attributes are associated with the task as listed by {@link SchedulingStateEnum}.
	 */
	private int SchedulingState;
		
	/**
	 * How long a task is expected to take to be completed. Specified in milliseconds even though that level of granularity is not needed. Will also be used to decide the order in which tasks should be completed. (i.e. a task that will take 5 hours should be worked on before a task that is expected to take 30 minutes when they have the same priority and weight).
	 */
	private long ExpectedTimeToCompletion;
	
	/**
	 * How much time has been spent on the task. Specified in milliseconds even though that level of granularity is not needed. This will be used to track progress as well as give the user insight into making time estimates.
	 */
	private long TimeSpent;
	
	/**
	 * Tracks all of the start and stop times that were spent on the task. This is used to show how time was actually spent during graphically as well as track the individual start/stop times to provide an actual total of how much time was reported on a task.
	 */
	private Vector<TimeKeeper> TimeKept = new Vector<TimeKeeper>();
	
	/**
	 * The identifying color for the task.
	 */
	private Color ColorTag;
	
	/**
	 *The scheduled/planned occurrences of the task. 
	 */
	private Vector<TimeKeeper> ScheduledOccurences = new Vector<TimeKeeper>();
	
	/*
	 * CONSTRUCTORS
	 */
	
	/**
	 * The task contains all the information pertaining to the events that are necessary for the user to complete.
	 * It holds information that will be used for both UI and for data tracking.
	 * 
	 * @param weight How easily the task can be moved around and changed.
	 * @param priority How important the task is.
	 * @param shortestInc The shortest amount of time that can be allotted to working on the task in minutes.
	 * @param deadline The millisecond time since epoch for when the task needs to be completed.
	 * @param state The type of schedule that the task is expected to follow.
	 * @param expectedTTC The expected amount of time that will be spent to complete the task in milliseconds.
	 * @param tag The color the tag will use to present itself in the UI.
	 * @param occurences The planned times that the task should be worked on.
	 */
	public Task(int weight, int priority, int shortestInc, long deadline, int state, long expectedTTC, Color tag, Vector<TimeKeeper> occurences)
	{
		setWeight(weight);
		setPriority(priority);
		setShortestIncrement(shortestInc);
		setDeadline(deadline);
		setSchedulingState(state);
		setExpectedTimeToCompletion(expectedTTC);
		setColorTag(tag);
		setScheduledOccurences(occurences);
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	/**
	 * Sets the shortest alloted time on which the task can be worked.
	 * @param shortestIncrementInMinutes The number of minutes that the task can be work.
	 */
	public void setShortestIncrement(int shortestIncrementInMinutes) {
		ShortestIncrement = shortestIncrementInMinutes;
	}
	
	/**
	 * Gets the shortest alloted time on which the task can be worked.
	 * @return The number of minutes that the task can be worked.
	 */
	public int getShortestIncrement() {
		return ShortestIncrement;
	}
	
	/**
	 * Sets the weight to track how easily the task can be moved around. (lower is less rigid)
	 * @param weight The value relating how easily the task can be altered.
	 */
	public void setWeight(int weight) {
		Weight = weight;
	}
	
	/**
	 * Gets the weight to track how easily the task can be moved around. (lower is less rigid)
	 * @return The value relating how easily the task can be altered.
	 */
	public int getWeight() {
		return Weight;
	}

	/**
	 * Gets the priority to help rank the importance of the task. (lower is more important)
	 * @return The rank of the importance of the task.
	 */
	public int getPriority() {
		return Priority;
	}

	/**
	 * Sets the priority to help rank the importance of the task. (lower is more important)
	 * @param priority The rank of the importance of the task.
	 */
	public void setPriority(int priority) {
		Priority = priority;
	}

	/**
	 * Gets the deadline in milliseconds since epoch for when the task should be completed.
	 * @return The time in milliseconds since epoch for when the task should be completed.
	 */
	public long getDeadline() {
		return Deadline;
	}

	/**
	 * Sets the deadline in milliseconds since epoch for when the task should be completed.
	 * @param deadline The time in milliseconds since epoch for when the task should be completed.
	 */
	public void setDeadline(long deadline) {
		Deadline = deadline;
	}

	/**
	 * Gets the states associated with the task as listed by {@link SchedulingStateEnum}.
	 * @return The {@link SchedulingStateEnum} value associated with the task.
	 */
	public int getSchedulingState() {
		return SchedulingState;
	}

	/**
	 * Sets the states associated with the task as listed by {@link SchedulingStateEnum}.
	 * @param schedulingState The {@link SchedulingStateEnum} value associated with the task.
	 */
	public void setSchedulingState(int schedulingState) {
		SchedulingState = schedulingState;
	}

	/**
	 * Gets the expected amount of time it will take to complete the task in milliseconds.
	 * @return The expected amount of time it will take to complete the task in milliseconds.
	 */
	public long getExpectedTimeToCompletion() {
		return ExpectedTimeToCompletion;
	}

	/**
	 * Sets the expected amount of time it will take to complete the task in milliseconds.
	 * @param expectedTimeToCompletion The expected amount of time it will take to complete the task in milliseconds.
	 */
	public void setExpectedTimeToCompletion(long expectedTimeToCompletion) {
		ExpectedTimeToCompletion = expectedTimeToCompletion;
	}

	/**
	 * Gets the amount of time spent working on the task in milliseconds.
	 * @return The amount of time in milliseconds spent working on the task.
	 */
	public long getTimeSpent() {
		return TimeSpent;
	}

	/**
	 * Sets the amount of time spent working on the task in milliseconds.
	 * @param timeSpent The amount of time in milliseconds spent working on the task.
	 */
	public void setTimeSpent(long timeSpent) {
		TimeSpent = timeSpent;
	}

	/**
	 * Gets the color of the task to be displayed on the UI.
	 * @return The color of the task.
	 */
	public Color getColorTag() {
		return ColorTag;
	}

	/**
	 * Sets the color of the task to be displayed on the UI.
	 * @param colorTag The color of the task.
	 */
	public void setColorTag(Color colorTag) {
		ColorTag = colorTag;
	}

	/**
	 * Gets the expected occurrences of when the task should be worked.
	 * @return The times that the task is expected to receive work.
	 */
	public Vector<TimeKeeper> getScheduledOccurences() {
		return ScheduledOccurences;
	}

	/**
	 * Sets the expected occurrences of when the task should be worked.
	 * @param scheduledOccurences the times that the task is expected to receive work.
	 */
	public void setScheduledOccurences(Vector<TimeKeeper> scheduledOccurences) {
		ScheduledOccurences = scheduledOccurences;
	}
}