package com.here.hackweek.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.here.hackweek.Task;

public class TaskFixture {

	String task0 =
		"{ \"name\":\"start\",\"location\":\"2951 S Bayshore Dr Miami, FL\", \"time\":\"8:00\", \"latitude\":\"25.727568\", \"longitude\":\"-80.239990\"}";
	String task1 =
		"{ \"name\":\"museum\",\"location\":\"Vizcaya Museum\", \"duration\":\"120\",  \"latitude\":\"25.744508\", \"longitude\":\"-80.210478\" }";
	String task2 =
		"{ \"name\":\"aquarium\",\"location\":\"Miami Seaquarium\", \"duration\":\"120\", \"latitude\":\"25.734581\", \"longitude\":\"-80.164850\"}";
	String task3 =
		"{ \"name\":\"Lunch\",\"location\":\"Green Gables Café Miami\", \"time\":\"12:00\",\"duration\":\"60\", \"latitude\":\"25.752412\", \"longitude\":\"-80.261449\" }";
	String task4 =
		"{ \"name\":\"Zoo\",\"location\":\"Zoo Miami\", \"duration\":\"60\", \"latitude\":\"25.614216\", \"longitude\":\"-80.398484\"}";
	String task5 =
		"{ \"name\":\"garden\",\"location\":\"Pinecrest Gardens Miami\", \"duration\":\"180\", \"latitude\":\"25.669584\", \"longitude\":\"-80.285618\"}";
	String task6 =
		"{ \"name\":\"dinner\",\"location\":\"naoe miami\", \"time\":\"19:00\", \"duration\":\"120\",\"latitude\":\"25.769775\", \"longitude\":\"-80.185674\" }";
	String task7 =
		"{ \"name\":\"end\",\"location\":\"Sofitel Miami\", \"time\":\"23:30\", \"latitude\":\"25.78252\", \"longitude\":\"-80.29093\"}";

	/*
	 * Task [name=start, duration=null, time=8:00, latitue=25.727568, longitude=-80.239990] Task [name=museum,
	 * duration=120, time=8:11, latitue=25.744508, longitude=-80.210478] Task [name=Lunch, duration=60, time=12:00,
	 * latitue=25.752412, longitude=-80.261449] Task [name=aquarium, duration=120, time=13:21, latitue=25.734581,
	 * longitude=-80.164850] Task [name=Zoo, duration=60, time=16:18, latitue=25.614216, longitude=-80.398484] Task
	 * [name=dinner, duration=120, time=19:00, latitue=25.769775, longitude=-80.185674]
	 */
	public List<Task> getTasks() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Task taskObject0 = mapper.readValue(task0, Task.class);
		List<Task> resulTasks = new ArrayList<Task>();
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task1, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task2, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task3, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task4, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task5, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task6, Task.class);
		resulTasks.add(taskObject0);
		taskObject0 = mapper.readValue(task7, Task.class);
		resulTasks.add(taskObject0);
		for (Task task : resulTasks) {
			System.out.println(task);
		}

		return resulTasks;
	}

	public static void main(String args[]) throws Exception {
		TaskFixture fixture = new TaskFixture();
		fixture.getTasks();
	}
	/*
	 * JsonFactory f = new JsonFactory(); JsonParser jp = f.createJsonParser(task0); // advance stream to START_ARRAY
	 * first: jp.nextToken(); ObjectMapper mapper = new ObjectMapper(); // and then each time, advance to opening
	 * START_OBJECT /*while (jp.nextToken() == JsonToken.START_OBJECT)) { Task foobar = mapper.readValue(jp, Foo.class);
	 * // process // after binding, stream points to closing END_OBJECT }
	 * 
	 * }
	 */
}
