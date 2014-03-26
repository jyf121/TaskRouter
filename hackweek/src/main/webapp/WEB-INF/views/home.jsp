<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="../assets/ico/favicon.ico">

<title>Task Based Routing</title>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template 
    <link href="starter-template.css" rel="stylesheet"> -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style type="text/css">
#mapContainer {
	height: 700px;
	width: 640px;
	left: 0;
	top: 0;
	//
	/* position: absolute; */
}

/* CUSTOMIZE THE CAROUSEL
-------------------------------------------------- */

/* Carousel base class */
.carousel {
	height: 700px;
	margin-bottom: 60px;
}
/* Since positioning the image, we need to help out the caption */
.carousel-caption {
	z-index: 10;
}

/* Declare heights because of positioning of img element */
.carousel .item {
	height: 700px;
	background-color: #777;
}

.carousel-inner>.item>img {
	position: absolute;
	top: 0;
	left: 0;
	/* min-width: 100%; */
	max-width: none;
	height: 700px;
}

.jumbotron{
background: rgba(238, 238, 238, 0)
}
</style>

</head>

<body ng-app="project">

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">ZenTask</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="#/addroute">Tasks</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<br>


	<div ng-view></div>


	<script type="text/javascript" charset="UTF-8"
		src="http://js.cit.api.here.com/se/2.5.3/jsl.js?with=all"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="resources/js/lib/bootstrap.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/angular.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/angular-route.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/angular-cookies.js"></script>
	<script src="resources/js/app.js"></script>
	<script src="resources/js/controllers/RoutesController.js"></script>

</body>
</html>
