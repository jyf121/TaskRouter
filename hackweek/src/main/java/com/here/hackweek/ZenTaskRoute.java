package com.here.hackweek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.here.hackweek.service.CalculationHelper;
import com.here.hackweek.service.HelperImpl;
import com.here.hackweek.service.TaskFixture;

/**
 * TODO: add embedded server to run these tests.
 * 
 * <p>
 * &copy; 2013 HERE, a Nokia business
 * </p>
 * Assumptions Task time format 13:42 task array will have a start task - name is start, time is required for this task
 * end task - name is end, time is required for this task each task will have a duration to help calculate how much each
 * task at least take Route will return reordered list of task with start time for each task and distance
 * 
 * @author 3D Operational Tools Team
 */
public class ZenTaskRoute {

	final static long MIN_GAP_TIME = 600;// in seconds

	@Autowired
	CalculationHelper helper;

	public List<Task> calculateRouteFromTasks(List<Task> tasks) {
		List<Task> flexTimeTasks = new ArrayList<Task>();
		List<Task> fixedTimeTasks = new ArrayList<Task>();
		Task startTask = null;
		Task endTask = null;

		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			if ("start".equals(task.name)) {
				startTask = task;
				continue;
			}
			if ("end".equals(task.name)) {
				endTask = task;
				continue;
			}
			if (!StringUtils.hasText(task.getTime())) {
				flexTimeTasks.add(task);
			} else
				fixedTimeTasks.add(task);
		}
		List<Task> optimizedTasks = new ArrayList<Task>();
		optimizedTasks.add(startTask);
		Collections.sort(fixedTimeTasks, new CustomComparator());
		// check between startTask and first fixed time tasks
		// if it can fit a flex task
		for (int i = 0; i < fixedTimeTasks.size(); i++) {
			Task currentFixedTimeTask = fixedTimeTasks.get(i);
			decideNextTask(optimizedTasks, flexTimeTasks, startTask, currentFixedTimeTask);
			startTask = currentFixedTimeTask;
		}
		optimizedTasks.add(endTask);
		return optimizedTasks;
	}

	private void
		decideNextTask(List<Task> optimizedTasks, List<Task> flexTasks, Task starter, Task currentFixedTimeTask)
	{
		long diff = helper.getTimeDifferenceInMinute(starter, currentFixedTimeTask);
		// check what should be the next task
		SortedMap<Integer, Integer> flexTaskIndexMap = new TreeMap<Integer, Integer>();
		if (diff > helper.calculatefrom1TaskToAnotherTotalDurationInMinute(starter, currentFixedTimeTask)) {
			Task nextTask = starter;
			boolean changedNexTask = false;
			flexTaskIndexMap.clear();
			for (int i = 0; i < flexTasks.size(); i++) {
				nextTask = flexTasks.get(i);
				long totalTime = helper.calculatefrom1TaskToAnotherTotalDurationInMinute(starter, nextTask);
				long travelToNextFixedTimeTask =
					helper.calculatefrom1TaskToAnotherTotalDurationInMinute(nextTask, currentFixedTimeTask);
				long canditTime = totalTime + travelToNextFixedTimeTask;
				if (diff > canditTime) {
					long travelTimeAndDistance[] =
						helper.getTravelTimeAndDistance(
							starter.getLatitude(),
							starter.getLongitude(),
							nextTask.getLatitude(),
							nextTask.getLongitude());
					flexTaskIndexMap.put((int) travelTimeAndDistance[0], i);
				}
			}

			if (!flexTaskIndexMap.isEmpty()) {
				Integer lowestTravelTimeInteger = flexTaskIndexMap.firstKey();
				Integer nextTaskIndex = flexTaskIndexMap.get(lowestTravelTimeInteger);
				nextTask = flexTasks.get(nextTaskIndex);
				helper.calculateStartTimeAndDistance(starter, nextTask);
				optimizedTasks.add(nextTask);

				for (Iterator<Task> iterator = flexTasks.iterator(); iterator.hasNext();) {
					Task task = iterator.next();
					if (task.equals(nextTask))
						iterator.remove();
				}
				// flexTasks.remove(nextTaskIndex);
				changedNexTask = true;
				flexTaskIndexMap.clear();
			}
			if (changedNexTask)
				decideNextTask(optimizedTasks, flexTasks, nextTask, currentFixedTimeTask);
			else {
				helper.calculateStartTimeAndDistance(starter, currentFixedTimeTask);
				optimizedTasks.add(currentFixedTimeTask);
				starter = currentFixedTimeTask;
			}
		} else {// go to currentFixedTimeTask now
			optimizedTasks.add(currentFixedTimeTask);
			starter = currentFixedTimeTask;
		}

	}

	public static void main(String[] args) throws Exception {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new StringHttpMessageConverter());
		// converters.add(new MappingJackson2HttpMessageConverter());

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setMessageConverters(converters);
		String[] locationsStrings = { "42.1724", "-85.56446", "41.89209", "-87.60976" };
		ZenTaskRoute zenRoute = new ZenTaskRoute();
		zenRoute.setHelper(new HelperImpl());
		CalculationHelper helperImpl = zenRoute.getHelper();
		helperImpl.setRestTemplate(restTemplate);

		helperImpl.getTravelTimeAndDistance(locationsStrings);
		TaskFixture fixture = new TaskFixture();
		List<Task> routeResult = zenRoute.calculateRouteFromTasks(fixture.getTasks());
		for (int i = 0; i < routeResult.size(); i++) {
			System.out.println(routeResult.get(i));
		}
	}

	public CalculationHelper getHelper() {
		return helper;
	}

	public void setHelper(CalculationHelper helper) {
		this.helper = helper;
	}
}
