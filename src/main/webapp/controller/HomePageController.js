var toDo = angular.module('toDo');

toDo.controller('homeController', function($scope,homePageService,$location) {
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
		},function(response){
		});
	}
		
	$scope.deleteNote=function(id){
		console.log("id is ..."+id);
			var a=homePageService.deleteNote(id);
				a.then(function(response) {
					getAllNotes();
				},function(response){
				});
		}
	
	$scope.updateNote = function(note){
		console.log("id is ..."+note.id);
			var a=homePageService.updateNote(note);
				a.then(function(response) {
					getAllNotes();
				},function(response){
				});
		}
	
	$scope.addNote=function(){
		$scope.notes = {};
		$scope.notes.title=document.getElementById("notetitle").innerHTML;
		$scope.notes.description=document.getElementById("noteDescription").innerHTML;
		var a=homePageService.addNote($scope.notes);
			
		a.then(function(response) {
			document.getElementById("notetitle").innerHTML="";
			document.getElementById("noteDescription").innerHTML="";
			getAllNotes();
		},function(response){
		});
	}
	
	$scope.copy=function(note){
		/*$scope.notes = {};
		$scope.notes.title=document.getElementById("notetitle").innerHTML;
		$scope.notes.description=document.getElementById("noteDescription").innerHTML;*/
		note.id=0;
		console.log("this is for copy id "+note.id);
		var a=homePageService.addNote(note);
			
		a.then(function(response) {
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