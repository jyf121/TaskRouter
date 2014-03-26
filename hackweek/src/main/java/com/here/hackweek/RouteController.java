package com.here.hackweek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.here.hackweek.domain.Tasks;
import com.here.hackweek.service.CalculationHelper;
import com.here.hackweek.service.HelperImpl;

/**
 * Handles requests for the application routes.
 */
@Controller
public class RouteController {

	private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

	@RequestMapping(value = "/optimizedRoute", method = RequestMethod.POST)
	public @ResponseBody
	List<Task> optimizedRoute(@RequestBody final Tasks tasks) throws JsonParseException, JsonMappingException,
		IOException
	{
		List<Task> result = new ArrayList<Task>(tasks.getTasks());

		ZenTaskRoute zenRoute = new ZenTaskRoute();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new StringHttpMessageConverter());
		// converters.add(new MappingJackson2HttpMessageConverter());

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setMessageConverters(converters);
		zenRoute.setHelper(new HelperImpl());
		CalculationHelper helperImpl = zenRoute.getHelper();
		helperImpl.setRestTemplate(restTemplate);
		List<Task> routeResult = zenRoute.calculateRouteFromTasks(result);

		logger.info("number of tasks: " + result.size());
		return routeResult;
	}
}
