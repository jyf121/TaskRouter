package com.here.hackweek;

public class Task {

	String name;;
	String duration;
	String time;
	String latitude;
	String longitude;
	String location;
	String travelTime;
	String distance;
	String planTime;

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Task [name=" + name + ", duration=" + duration + ", time=" + time + ", latitude=" + latitude
			+ ", longitude=" + longitude + ", location=" + location + ", travelTime=" + travelTime + ", distance="
			+ distance + "]";
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
