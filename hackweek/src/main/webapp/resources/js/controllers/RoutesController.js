/*'use strict';*/

var RoutesController = function($scope, $http, $location){
	
	$scope.routes = [];
	$scope.tasks = [];
	$scope.routeName = '';
	$scope.routeShapePointsArray = new Array();
	$scope.routeIndex =0;
	$scope.routeShapePointsArrayIndex = 0;
	$scope.distance="Magic happens when you click 'Show Zen Route'! ";
	$scope.travelTime="Add more task Please!";
	 $scope.markerContainer = new nokia.maps.map.Container();
	 
	
	nokia.Settings.set("app_id", "hNFPCL9BQEbtUF1DX8ab"); 
	nokia.Settings.set("app_code", "39ziI3UIlztuYO2qTUVd4w");
	  // Use staging environment (remove the line for production environment)
	nokia.Settings.set("serviceMode", "cit");
	  
	// We create a new instance of InfoBubbles bound to a variable so we can call it later on
	var infoBubbles = new nokia.maps.map.component.InfoBubbles();
	  var mapContainer = document.getElementById("mapContainer");
	  // Create a map inside the map container DOM node
	  $scope.map = new nokia.maps.map.Display(mapContainer, {
	    // Initial center and zoom level of the map
	    center: [37.0890999,-81.8521875],//41.8337329,-87.7321554],
	    zoomLevel: 3,
	    components: [   
		  infoBubbles,	
	      new nokia.maps.map.component.Behavior()
	    ]
	  });
	
	  // LOAD Time array from server
	  $http.get('times').success(function(data, status) {
		  $scope.times = data;
	  }).error(function(data, status) {
		console.log('!!! could not retrieve times from server, status: ' + status);  
	  });
	  
	  // LOAD Duration array from server
	  $http.get('durations').success(function(data, status) {
		  $scope.durations = data;
	  }).error(function(data, status) {
		console.log('!!! could not retrieve times from server, status: ' + status);  
	  });
	  
	  
	$scope.addTask = function(){
		
		if ($scope.taskName == '' || $scope.taskLocation == '')
			alert('Task name and location are required');
		else
		{
			console.log($scope.taskTime);
			console.log(String($scope.taskTime));
			var task = {name: $scope.taskName, duration: $scope.taskDuration, 
				time: $scope.taskTime, location: $scope.taskLocation};
			if(task.time!=undefined) {
				//task.displayString= task.time+" ";
				task.timeString=task.time;
			}
			else{
				//task.displayString="";
				task.timeString = "";
			}
			task.displayString += task.name;
			if(task.duration!= undefined)
				task.displayString+= " for "+task.duration + " minutes";
			task.displayString+=" in "+task.location;
			$scope.tasks.push(task);
			geocode($scope.taskLocation, task, $scope.tasks.length);
			

		}
	};
	/*
	 * ,
		'{ "name":"Aquarium","location":"Miami Seaquarium", "duration":"2hr"}',
		'{ "name":"Lunch","location":"Green Gables Café Miami", "time":"12:00PM","duration":"1hr"}',
		'{ "name":"Zoo","location":"Zoo Miami", "duration":"3hr"}',
		'{ "name":"Garden","location":"Pinecrest Gardens Miami", "duration":"3hr"}',
		'{ "name":"dinner","location":"naoe miami", "time":"19:00", "duration":"2hr"}',
		'{ "name":"end","location":"Sofitel Miami", "time":"21:30"}'
	 */
	$scope.addPredefinedTasks = function() {
		$scope.tasks=[];
		$scope.tasks = [
		{"name":"start", "location":"Epic Hotel", "time":"8:00"},
		{ "name":"Visit Zoo","location":"Zoo Miami", "duration":"120"},
		{ "name":"Visit Garden","location":"Pinecrest Gardens Miami", "duration":"120"},
		{ "name":"Visit Aquarium","location":"Miami Seaquarium", "duration":"60"},
		{ "name":"Lunch With Wise Man","location":"Mandolin Aegean Bistro", "time":"12:00","duration":"60"},
		{ "name":"Visit Museum","location":"Vizcaya Museum", "duration":"120"},
		{ "name":"Dinner","location":"naoe miami", "time":"19:00", "duration":"60"},
		{ "name":"end","location":"Sofitel Miami", "time":"20:30"}
		];
		for(var i=0; i < $scope.tasks.length; i++) {
			
			geocode($scope.tasks[i].Location, $scope.tasks[i], i+1);
			if($scope.tasks[i].time!=undefined) {
				//$scope.tasks[i].displayString= $scope.tasks[i].time+" ";
				$scope.tasks[i].timeString= $scope.tasks[i].time;
			}
			else {
				//$scope.tasks[i].displayString="";
				$scope.tasks[i].timeString= "";
			}
			$scope.tasks[i].displayString = $scope.tasks[i].name;
			if($scope.tasks[i].duration!= undefined)
				$scope.tasks[i].displayString+= " for "+$scope.tasks[i].duration + " minutes";
			$scope.tasks[i].displayString+=" in "+$scope.tasks[i].location;
		}
	}
	var geocode = function(placeName, task, index){
		var url = "http://places.cit.api.here.com/places/v1/discover/search?at=37.0917%2C-81.8327&q=" +
				task.location + "&result_types=place&pretty=true&app_id=hNFPCL9BQEbtUF1DX8ab&app_code=39ziI3UIlztuYO2qTUVd4w";
		$http.get(url).
	      success(function(data, status) {
	          task.position = data.results.items[0].position;
	          //$scope.tasks.push(task);
	        //  $scope.tasks.push(task);
	          initTaskControls();
	          $scope.createMarker(task.position, index, task.name+" at "+task.location);
	        }).
	        error(function(data, status) {
	        	console.log("Request failed, status: " + status);
	      });
	};
	
	$scope.createMarker = function(position, markerText, bubbleText) {
        var standardMarker = new nokia.maps.map.StandardMarker(position,{ text: markerText });
        // Next we need to add it to the map's object collection so it will be rendered onto the map.
        
        /* We would like to add event listener on mouse click or finger tap so we check
         * nokia.maps.dom.Page.browser.touch which indicates whether the used browser has a touch interface.
         */
        var TOUCH = nokia.maps.dom.Page.browser.touch,
        	CLICK = TOUCH ? "tap" : "click";

        /* Attach an event listener to the newly created marker
         * to show info bubble on click of the marker
         */
        standardMarker.addListener(
        	CLICK, 
        	function (evt) { 
        		// Set the tail of the bubble to the coordinate of the marker
        		infoBubbles.openBubble(bubbleText, standardMarker.coordinate);
        	}
        );
        $scope.markerContainer.objects.add(standardMarker);
        if( -1 == $scope.map.objects.indexOf($scope.markerContainer)) {
      	  $scope.map.objects.add($scope.markerContainer);
        }
        $scope.map.zoomTo($scope.markerContainer.getBoundingBox());

	}
	var calculateArrivalTime= function(startTime, travelTime, duration) {
		var timeArray = startTime.split(":");
		var travelMinute = Math.floor(travelTime/60);
		var travelHour = Math.floor(travelMinute/60);
		var travelLeftMinutes = travelMinute%60;
		var startHour = Number(timeArray[0]);
		var startMinutes = Number(timeArray[1]);
		var endHour = startHour+travelHour;
		var endMinutes = startMinutes+travelLeftMinutes;
		if(endMinutes>= 60) {
			endMinutes -= 60;
			endHour++;
		}
		if(duration != undefined) {
			durationHour = Math.floor(duration/60);
			durationMin = duration%60;
			endHour += durationHour;
			endMinutes +=durationMin;
			if(endMinutes>= 60){
				endMinutes -= 60;
				endHour++;
			}
		}
			
		return ""+endHour+":"+endMinutes;
	}
	$scope.saveRoute = function(messageText){
		var url = 'http://route.cit.api.here.com/routing/7.2/calculateroute.json?app_id=hNFPCL9BQEbtUF1DX8ab&app_code=39ziI3UIlztuYO2qTUVd4w';
		
		angular.forEach($scope.tasks, function(task, idx){
			url += '&waypoint' + idx + '=geo!' + task.position[0] + ',' + task.position[1];
		});
		
		url += '&routeattributes=summary,shape&mode=fastest;car;traffic:enabled';
		
		console.log(url);
		
		$http.get(url).
	      success(function(data, status) {
	    	  console.log('route retrieval successful!');
	    	  console.log(data);
	    	  if(messageText == undefined || messageText==null)
	    		  $scope.message= "Route based on your input. Zen: I can do better.";
	    	  else
	    		  $scope.message= messageText;
	          var shapePoints = data.response.route[0].shape;
	          $scope.routeShapePointsArrayIndex=0;
	          for (var i=0; i< shapePoints.length; i++)
	          {
	            var shapepoint = shapePoints[i].split(",");
	            $scope.routeShapePointsArray[$scope.routeShapePointsArrayIndex++] = Number(shapepoint[0]);
	            $scope.routeShapePointsArray[$scope.routeShapePointsArrayIndex++]=Number(shapepoint[1]);
	          }
	          var legs = data.response.route[0].leg;
	          for (var j=1; j< $scope.tasks.length ; j++)
	          {
	            $scope.tasks[j].travelTime = legs[j-1].travelTime;
	            $scope.tasks[j].distance = legs[j-1].length;
	            var planTime = $scope.tasks[j].time;
	            $scope.tasks[j].time = calculateArrivalTime($scope.tasks[j-1].time, $scope.tasks[j].travelTime, $scope.tasks[j-1].duration);
	            $scope.tasks[j].style="info";
				if(planTime==undefined) { 
					$scope.tasks[j].displayString="";
				}
				else
				{
					$scope.tasks[j].displayString="*(Plan start "+planTime+") ";
					if(planTime <  $scope.tasks[j].time)
						$scope.tasks[j].style="danger";
				}
				
				$scope.tasks[j].displayString+= $scope.tasks[j].time+" ";	
				$scope.tasks[j].timeString= $scope.tasks[j].displayString;
				$scope.tasks[j].displayString = $scope.tasks[j].name;
				if($scope.tasks[j].duration!= undefined)
					$scope.tasks[j].displayString+= " for "+$scope.tasks[j].duration + " minutes";
				$scope.tasks[j].displayString+=" in "+$scope.tasks[j].location;
				//$scope.tasks[j].displayString+=" Travel Time "+$scope.tasks[j].travelTime+ " seconds";
				//$scope.tasks[j].displayString+=" Distance "+$scope.tasks[j].distance + " meters";
	          }
	          $scope.distance = "Total travel distance:"+((data.response.route[0].summary.distance/1000)*0.621371).toFixed(2)+" Miles";
	          $scope.travelTime = "Total travel time: "+(data.response.route[0].summary.travelTime/60).toFixed(2) + " Minutes";
	          $scope.routes[$scope.routeIndex++] = data.response.route[0];
	          if($scope.sceneContainer != undefined){
	        	  $scope.map.objects.remove($scope.sceneContainer);
	        	  $scope.sceneContainer.objects.clear();
	          }
	          drawRouteOnMap($scope.routeShapePointsArray);

	        }).
	        error(function(data, status) {
	        	console.log('get route http request failed, status: ' + status);
	      });
	};
	
	$scope.showOptimizedRoute = function(){
		var url = "optimizedRoute";
		$scope.map.objects.remove($scope.markerContainer);
		$scope.markerContainer.objects.clear();
		var trTasks = [];
		angular.forEach($scope.tasks, function(task, idx){
			var taskObj = new Object();
			taskObj.name=task.name;
			if(task.duration!= undefined)
				taskObj.duration = String(task.duration);
			if(task.time != undefined)
				taskObj.time = String(task.time);
			taskObj.location = task.location;
			taskObj.latitude = String(task.position[0]);
			taskObj.longitude = String(task.position[1]);
			trTasks.push(taskObj);
			/*{"name": task.name, "duration": String(task.duration), 
				"time": String(task.time), "location": String(task.location), 
				//travelTime:"",distance:"",
				"latitude": String(task.position[0]), "longitude": String(task.position[1])}*/
		});
		$scope.message="Wise man said Zen is wise. Zen: should I use Kong Fu or Tai Chi?"
			+" It may take a while...";
		$http.post(url,angular.toJson({"tasks": trTasks})).
	      success(function(data, status) {
	    	 
	    	console.log(data);  
	    	var message = "Well done! Organized by time, distance, traffic. Can Zen do better? Wait until next Hack Week.";
	    	var taskList=data;
	    	$scope.tasks.length=0;
	    	
	    	for (var k=0; k < taskList.length; k++) {
	    		task = taskList[k];
	    		$scope.tasks[k] =new Object();
	    		$scope.tasks[k].name=task.name;
	    		$scope.tasks[k].duration=task.duration;
	    		$scope.tasks[k].time=task.time;
	    		$scope.tasks[k].location=task.location;
	    		$scope.tasks[k].position=[Number(task.latitude), Number(task.longitude)];
	    		$scope.tasks[k].travelTime=task.travelTime;
	    		$scope.tasks[k].planTime=task.planTime;
	    		$scope.tasks[k].distance=task.distance;
	    		$scope.createMarker($scope.tasks[k].position, ""+(k+1), task.name+" at "
	    				+task.location+"<br> Travel Time: "+task.travelTime+" Minutes, Distance: "+(Number(task.distance)/1000)+ " km");
	    	};
	    	$scope.saveOptimizedRoute(message);
	      }).
	        error(function(data, status) {
	        	console.log('get optimized route http POST request failed, status: ' + status);
	      });
	};
	$scope.saveOptimizedRoute = function(messageText){
		var url = 'http://route.cit.api.here.com/routing/7.2/calculateroute.json?app_id=hNFPCL9BQEbtUF1DX8ab&app_code=39ziI3UIlztuYO2qTUVd4w';
		
		angular.forEach($scope.tasks, function(task, idx){
			url += '&waypoint' + idx + '=geo!' + task.position[0] + ',' + task.position[1];
		});
		
		url += '&routeattributes=summary,shape&mode=fastest;car;traffic:enabled';
		
		console.log(url);
		
		$http.get(url).
	      success(function(data, status) {
	    	  console.log('route retrieval successful!');
	    	  console.log(data);
	    	  if(messageText == undefined || messageText==null)
	    		  $scope.message= "Route based on your input. Zen: I can do better.";
	    	  else
	    		  $scope.message= messageText;
	          var shapePoints = data.response.route[0].shape;
	          $scope.routeShapePointsArrayIndex=0;
	          for (var i=0; i< shapePoints.length; i++)
	          {
	            var shapepoint = shapePoints[i].split(",");
	            $scope.routeShapePointsArray[$scope.routeShapePointsArrayIndex++] = Number(shapepoint[0]);
	            $scope.routeShapePointsArray[$scope.routeShapePointsArrayIndex++]=Number(shapepoint[1]);
	          }
	          var legs = data.response.route[0].leg;
	          $scope.tasks[0].timeString = $scope.tasks[0].time;
	          $scope.tasks[0].displayString=$scope.tasks[0].name+" in "+$scope.tasks[0].location;
	          for (var j=1; j< $scope.tasks.length ; j++)
	          {
	            $scope.tasks[j].travelTime = legs[j-1].travelTime;
	            $scope.tasks[j].distance = legs[j-1].length;
	            var planTime = $scope.tasks[j].planTime;
	            $scope.tasks[j].style="info";
	            
				if(planTime==undefined) { 
					$scope.tasks[j].displayString="";
				}
				else {
					$scope.tasks[j].displayString="*(Plan start "+planTime+") ";
					if(planTime <  $scope.tasks[j].time)
						$scope.tasks[j].style="danger";
				}
				$scope.tasks[j].displayString+= $scope.tasks[j].time+" ";	
				$scope.tasks[j].timeString = $scope.tasks[j].displayString;
				$scope.tasks[j].displayString = $scope.tasks[j].name;
				if($scope.tasks[j].duration!= undefined)
					$scope.tasks[j].displayString+= " for "+$scope.tasks[j].duration + " minutes";
				$scope.tasks[j].displayString+=" in "+$scope.tasks[j].location;
				//$scope.tasks[j].displayString+=" Travel Time "+$scope.tasks[j].travelTime+ " seconds";
				//$scope.tasks[j].displayString+=" Distance "+$scope.tasks[j].distance + " meters";
	          }
	          $scope.distance = "Total travel distance:"+((data.response.route[0].summary.distance/1000)*0.621371).toFixed(2)+" Miles";
	          $scope.travelTime = "Total travel time: "+(data.response.route[0].summary.travelTime/60).toFixed(2) + " Minutes";
	          $scope.routes[$scope.routeIndex++] = data.response.route[0];
	          if($scope.sceneContainer != undefined){
	        	  $scope.map.objects.remove($scope.sceneContainer);
	        	  $scope.sceneContainer.objects.clear();
	          }
	          drawRouteOnMap($scope.routeShapePointsArray);

	        }).
	        error(function(data, status) {
	        	console.log('get route http request failed, status: ' + status);
	      });
	};
	
	var Walker = function (marker, path) {
	    this.path = path;
	    this.marker = marker;
	    this.idx = 0;
	    this.dir = -1;
	    this.isWalking = false;
	    var that = this;
	    this.walk = function () {
	      // Get the next coordinate from the route and set the marker to this coordinate
	      var coord = path.get(that.idx);
	      
	      marker.set("coordinate", coord);
	      
	      // Force immediate re-render of the map display
	      $scope.map.update(-1, true);
	      
	      // If we get to the end of the route reverse direction
	      if (!that.idx || that.idx === path.getLength() - 1) {
	        that.dir *= -1;
	      }
	      
	      that.idx += that.dir;
	      
	      /* Recursively call this function with time that depends on the distance to the next point
	       * which makes the marker move in similar random fashion
	       */
	      that.timeout = setTimeout(that.walk, Math.round(coord.distance(path.get(that.idx)) * 2.5));
	      that.isWalking = true;
	    };
	    
	    this.stop = function () {
	      clearTimeout(that.timeout);
	      this.isWalking = false;
	    };
	  };

	  var drawRouteOnMap = function(routeArr){

	    // We create a polygon route, marker 
	    var route = new nokia.maps.map.Polyline(
	        new nokia.maps.geo.Strip(
	          routeArr),
	        { color: "#00F6", width: 2 }
	      );
	      imageMarker = new nokia.maps.map.Marker(
	        route.path.get(0),
	        {
	          icon: "../../zen/resources/img/caricon.png", 
	          $id: "marker", 
	          anchor: {x: 5, y: 11}//{x: 21, y: 41}
	        }
	      );
	     
	      // Add all 3 of the created objects into one container for easier management
	      $scope.sceneContainer = new nokia.maps.map.Container([route, imageMarker]);

	    /* Once the map is initialized and ready (an event that is fired only once),
	     * add the above container to the map, and zoom in, 
	     * in such a way that the route is fully visible with the browser's window.
	     */
	    //$scope.map.addListener("displayready", function () {
	      $scope.map.objects.add($scope.sceneContainer);
	      $scope.map.zoomTo($scope.sceneContainer.getBoundingBox());
	      var walker = new Walker(imageMarker, route.path);
	      walker.walk();
	    //});
	  };
	  
	  var initTaskControls = function(){
		  $scope.taskName = '';
			$scope.taskDuration = 0;
			$scope.taskTime = '';
			$scope.taskLocation = '';  
	  };
	  initTaskControls();

};