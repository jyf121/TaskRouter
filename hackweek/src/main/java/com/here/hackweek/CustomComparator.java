package com.here.hackweek;

import java.util.Comparator;

public class CustomComparator implements Comparator<Task> {

	@Override
	public int compare(Task o1, Task o2) {
		if (o1.getTime() == null)
			return -1;
		else if (o2.getTime() == null)
			return 1;
		return o1.getTime().compareTo(o2.getTime());
	}

}
