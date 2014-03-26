package com.here.hackweek.service;

import org.springframework.web.client.RestTemplate;

import com.here.hackweek.Task;

public interface CalculationHelper {

	public long getTimeDifferenceInMinute(Task task1, Task task2);

	public long calculatefrom1TaskToAnotherTotalDurationInMinute(Task task1, Task task2);

	public long[] getTravelTimeAndDistance(String... locations);

	public void setRestTemplate(RestTemplate template);

	public void calculateStartTimeAndDistance(Task starter, Task nextTask);
}
