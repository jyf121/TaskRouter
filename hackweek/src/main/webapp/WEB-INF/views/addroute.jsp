<style type="text/css">
/* .pageContent {
	background-color: #777;
} */
</style>

<div class="pageContent">
	<div class="container">

		<br> <br>

		<div class="row">

			<div class="col-sm-5" id="leftPane">
				<div class="row">
					<input class="col-sm-2" type="text" ng-model="taskName"
						placeholder="Task" required /> <input class="col-sm-5"
						ng-model="taskLocation" placeholder="Location" required /> <select
						class="col-sm-3" ng-model="taskTime" ng-options="t.value as t.label for t in times"></select>

<select
                        class="col-sm-2" ng-model="taskDuration" ng-options="t.value as t.label for t in durations"></select>

					<!-- <input type="number" min="5" step="5" class="col-sm-2"
						ng-model="taskDuration" placeholder="30" data-toggle="tooltip"
						data-placement="top" title="Duration in minutes" /> -->
				</div>

				<br>

				<div class="row">
					<button ng-click="addTask()" class="btn btn-primary">Add
						Task</button>
					<button ng-click="addPredefinedTasks()" class="btn btn-primary">Hidden Gems</button>
					<button ng-show="tasks.length > 1" ng-click="saveRoute()"
						class="btn btn-success">Show Route</button>
					<button ng-show="tasks.length > 1" ng-click="showOptimizedRoute()"
						class="btn btn-success">Show Zen Route</button>
						
				</div>
				<div class="row">
				</div>

				<div class="row">
					
				</div>
				<h3>{{message}}</h3>
				<table class="table table-condensed table-bordered">
				<thead><tr><th>#</th><th>Start Time</th><th>Task Detail</th></tr></thead>
				<tr ng-repeat="task in tasks | filter:q">
				<td class="{{task.style}}"><h4>{{$index + 1}}</h4></td>
				<td class="{{task.style}}"><h5>{{task.timeString}}</h5></td>
				<td class="{{task.style}}">
				<h5>{{task.displayString}}</h5> 
				</td>
				</tr>
				<tr class="info"><td colspan="3"><h4>{{travelTime}}</h4></td></tr>
				<tr class="info"><td colspan="3"><h4>{{distance}}</h4></td></tr>
				</table>
			</div>
			<!-- left panel -->

			<div class="col-sm-7">
				<!-- <br> -->
				<div id="mapContainer"></div>
				<div id="uiContainer"></div>

			</div>
			<!-- right panel -->

		</div>
		<!-- class=row -->



	</div>
	<!-- /.container -->
</div>

