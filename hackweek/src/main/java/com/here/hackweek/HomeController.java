package com.here.hackweek;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.here.hackweek.domain.KeyValue;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final KeyValue[] times = {
		new KeyValue("12:00 AM", "00:00"), 
		new KeyValue("12:30 AM", "00:30"),
		new KeyValue("01:00 AM", "01:00"),
		new KeyValue("01:30 AM", "01:30"),
		new KeyValue("02:00 AM", "02:00"),
		new KeyValue("02:30 AM", "02:30"),
		new KeyValue("03:00 AM", "03:00"),
		new KeyValue("03:30 AM", "03:30"),
		new KeyValue("04:00 AM", "04:00"),
		new KeyValue("04:30 AM", "04:30"),
		new KeyValue("05:00 AM", "05:00"),
		new KeyValue("05:30 AM", "05:30"),
		new KeyValue("06:00 AM", "06:00"),
		new KeyValue("06:30 AM", "06:30"),
		new KeyValue("07:00 AM", "07:00"),
		new KeyValue("07:30 AM", "07:30"),
		new KeyValue("08:00 AM", "08:00"),
		new KeyValue("08:30 AM", "08:30"),
		new KeyValue("09:00 AM", "09:00"),
		new KeyValue("09:30 AM", "09:30"),
		new KeyValue("10:00 AM", "10:00"),
		new KeyValue("10:30 AM", "10:30"),
		new KeyValue("11:00 AM", "11:00"),
		new KeyValue("11:30 AM", "11:30"),
		new KeyValue("12:00 PM", "12:00"),
		new KeyValue("12:30 PM", "12:30"),
		new KeyValue("01:00 PM", "13:00"),
		new KeyValue("01:30 PM", "13:30"),
		new KeyValue("02:00 PM", "14:00"),
		new KeyValue("02:30 PM", "14:30"),
		new KeyValue("03:00 PM", "15:00"),
		new KeyValue("03:30 PM", "15:30"),
		new KeyValue("04:00 PM", "16:00"),
		new KeyValue("04:30 PM", "16:30"),
		new KeyValue("05:00 PM", "17:00"),
		new KeyValue("05:30 PM", "17:30"),
		new KeyValue("06:00 PM", "18:00"),
		new KeyValue("06:30 PM", "18:30"),
		new KeyValue("07:00 PM", "19:00"),
		new KeyValue("07:30 PM", "19:30"),
		new KeyValue("08:00 PM", "20:00"),
		new KeyValue("08:30 PM", "20:30"),
		new KeyValue("09:00 PM", "21:00"),
		new KeyValue("09:30 PM", "21:30"),
		new KeyValue("10:00 PM", "22:00"),
		new KeyValue("10:30 PM", "22:30"),
		new KeyValue("11:00 PM", "23:00"),
		new KeyValue("11:30 PM", "23:30"),
	};
	
	private static KeyValue[] durations = { new KeyValue("15 min", "15"),
		new KeyValue("30 min", "30"),
		new KeyValue("45 min", "30"),
		new KeyValue("1 hr", "60"),
		new KeyValue("1.5 hr", "90"),
		new KeyValue("2 hr", "120"),
		new KeyValue("2.5 hr", "150"),
		new KeyValue("3 hr", "180"),
		new KeyValue("3.5 hr", "210"),
		new KeyValue("4 hr", "240"),
		new KeyValue("4.5 hr", "270"),
		new KeyValue("5 hr", "300"),
		new KeyValue("5.5 hr", "330"),
		new KeyValue("6 hr", "360"),
		new KeyValue("6.5 hr", "390"),
		new KeyValue("7 hr", "420"),
		new KeyValue("7.5 hr", "450"),
		new KeyValue("8 hr", "480"),
		new KeyValue("8.5 hr", "510"),
		new KeyValue("9 hr", "540"),
		new KeyValue("9.5 hr", "570"),
		new KeyValue("10 hr", "600"),
		new KeyValue("10.5 hr", "630"),
		new KeyValue("11 hr", "660"),
		new KeyValue("11.5 hr", "690"),
		new KeyValue("12 hr", "720"),
	};
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		return "home";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		return "main";
	}
	
	@RequestMapping(value = "/addroute", method = RequestMethod.GET)
	public String addRoute(Locale locale, Model model) {
		return "addroute";
	}

	@RequestMapping(value = "/times", method = RequestMethod.GET)
	public @ResponseBody KeyValue[] times(Locale locale, Model model) {
		return times;
	}
	
	@RequestMapping(value = "/durations", method = RequestMethod.GET)
	public @ResponseBody KeyValue[] durations(Locale locale, Model model) {
		return durations;
	}
}
