'use strict';

var App = angular.module('project', ['ngRoute']);

App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'main'
    });

    $routeProvider.when('/addroute', {
        templateUrl: 'addroute',
        controller: RoutesController
    });
    
    $routeProvider.otherwise({redirectTo: '/'});
}]);