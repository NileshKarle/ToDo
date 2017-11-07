var toDo = angular.module('toDo');

toDo.controller('homeController', function($scope,homePageService, $location) {
$scope.showSideBar=true;
	$scope.sidebarToggle=function() {
		console.log("this is ");
		$scope.showSideBar=!$scope.showSideBar;
	}
	
	$scope.addNote=function(){
		console.log("homepage controller");
		var a=homePageService.addNote($scope.note);
			
		a.then(function(response) {
				$location.path('/home')
		},function(response){
				$location.path('/login')
		});
	}
	
});