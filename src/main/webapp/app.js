 var app = angular.module('ToDo', ['ui.router']);
     
app.config(function ($routeProvider) {
	var register={
    	templateUrl: 'template/Register.html',
    	controller: 'registerController'
    }
	
	$routeProvider.state(register);
	
 });
