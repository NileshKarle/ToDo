var toDo = angular.module('toDo',['ngSanitize']);

toDo.controller('homeController', function($scope,homePageService, $sce, $location) {
	console.log("main controller");
$scope.showSideBar=true;
	$scope.sidebarToggle=function() {
		console.log("this is ");
		$scope.showSideBar=!$scope.showSideBar;
	}
	
	getAllNotes();
	
	function getAllNotes() {
		var b=homePageService.allNotes();
		b.then(function(response) {
			console.log(response.data);
			$scope.userNotes=response.data;
			$scope.noteTitle=$scope.userNotes.title;
			$scope.noteDescription=$scope.userNotes.description;
		},function(response){
		});
	}
		
	
	$scope.addNote=function(){
		$scope.notes = {};
		console.log("homepage controller");
		console.log(document.getElementById("notetitle").innerHTML);
		$scope.notes.title=document.getElementById("notetitle").innerHTML;
		$scope.notes.description=document.getElementById("noteDescription").innerHTML;
		console.log($scope.notes);
		var a=homePageService.addNote($scope.notes);
			
		a.then(function(response) {
			document.getElementById("notetitle").innerHTML="";
			document.getElementById("noteDescription").innerHTML="";
			getAllNotes();
		},function(response){
		});
	}
	
	/*$scope.popupCard=function(){
		document.getElementById("card").width="80%";
		document.getElementById("card").height="50%";
		document.getElementById("card").position="absolute"
	}*/
	
	$scope.logout=function() {
		console.log("this is logout ");
		localStorage.removeItem('token');
		$location.path('/login')
	}
	
});