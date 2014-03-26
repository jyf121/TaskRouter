package com.here.hackweek.service;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.here.hackweek.Task;

public class HelperImpl implements CalculationHelper {

	private RestTemplate template;

	@Override
	public long getTimeDifferenceInMinute(Task task1, Task task2) {
		String startTimes[] = task1.getTime().split(":");
		String endTimes[] = task2.getTime().split(":");
		int startHour = Integer.parseInt(startTimes[0]);
		int startMin = Integer.parseInt(startTimes[1]);
		int endHour = Integer.parseInt(endTimes[0]);
		int endMin = Integer.parseInt(endTimes[1]);
		int minDiff = endMin - startMin;
		if (minDiff < 0) {
			minDiff += 60;
			endHour--;
		}
		return ((endHour - startHour) * 60 + minDiff);
	}

	@Override
	public long[] getTravelTimeAndDistance(String... locations) {
		// lat, lon, end lat lon
		String urlString =
			"http://route.cit.api.here.com/routing/7.2/calculateroute.json?app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg&waypoint0=geo!"
				+ locations[0]
				+ ","
				+ locations[1]
				+ "&routeattributes=summary,shape&waypoint1=geo!"
				+ locations[2]
				+ "," + locations[3] + "&mode=fastest;car;traffic:enabled";
		// HereRoute result = restTemplate.getForObject(urlString, HereRoute.class);
		String result = template.getForObject(urlString, String.class);
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);
		JsonObject response = jsonObject.getAsJsonObject("response");
		JsonArray routeArr = response.getAsJsonArray("route");
		JsonObject route = routeArr.get(0).getAsJsonObject();
		JsonObject routeSummary = route.getAsJsonObject("summary");
		Long travelTimeLong = routeSummary.getAsJsonPrimitive("trafficTime").getAsLong();
		Long travelDistance = routeSummary.getAsJsonPrimitive("distance").getAsLong();
		System.out.println(travelTimeLong);
		// in minutes, meter
		return new long[] { travelTimeLong / 60, travelDistance };
	}

	@Override
	public long calculatefrom1TaskToAnotherTotalDurationInMinute(Task task1, Task task2) {
		long travelTimeAndDistance[] =
			getTravelTimeAndDistance(
				task1.getLatitude(),
				task1.getLongitude(),
				task2.getLatitude(),
				task2.getLongitude());
		int duration = 0;
		if (StringUtils.hasText(task1.getDuration()))
			duration = Integer.parseInt(task1.getDuration());
		return travelTimeAndDistance[0] + duration;
	}

	@Override
	public void setRestTemplate(RestTemplate template) {

		this.template = template;
	}

	@Override
	public void calculateStartTimeAndDistance(Task starter, Task nextTask) {
		long[] timeAndDistance =
			getTravelTimeAndDistance(
				starter.getLatitude(),
				starter.getLongitude(),
				nextTask.getLatitude(),
				nextTask.getLongitude());
		String startTimes[] = starter.getTime().split(":");
		int diffMinutes = (int) (timeAndDistance[0]);
		if (StringUtils.hasText(starter.getDuration()))
			diffMinutes += Integer.parseInt(starter.getDuration());

		int startHour = Integer.parseInt(startTimes[0]);
		int startMin = Integer.parseInt(startTimes[1]);

		int hours = diffMinutes / 60; // since both are ints, you get an int
		int minutes = diffMinutes % 60;
		int calculatedMinutes = startMin + minutes;
		if (calculatedMinutes > 60) {
			hours++;
			calculatedMinutes -= 60;
		}
		System.out.printf("Task Total Time %d:%02d", hours, minutes);
		String newTimeString = "";
		if (calculatedMinutes < 10) {
			newTimeString = startHour + hours + ":0" + calculatedMinutes;
		} else
			newTimeString = startHour + hours + ":" + calculatedMinutes;
		nextTask.setPlanTime(nextTask.getTime());
		nextTask.setTime(newTimeString);
		nextTask.setTravelTime(String.valueOf(timeAndDistance[0]));
		nextTask.setDistance(String.valueOf(timeAndDistance[1]));
	}

}
