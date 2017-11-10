var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homePageService, $location) {
					console.log("main controller");
					$scope.showSideBar = true;
					$scope.sidebarToggle = function() {

						$scope.showSideBar = !$scope.showSideBar;
					}
					
					
					$scope.AddNoteBox = false;
					
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}
					
					
					getAllNotes();

					
					function getAllNotes() {
						var b = homePageService.allNotes();
						b.then(function(response) {
							console.log(response.data);
							$scope.userNotes = response.data;
						}, function(response) {
						});
					}

					
					$scope.archiveNote=function(note){
						note.noteStatus="archive";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					$scope.deleteNote=function(note){
						note.noteStatus="delete";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					$scope.deleteNoteForever = function(id) {
						console.log("id is ..." + id);
						var a = homePageService.deleteNoteForever(id);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					$scope.popup=function(note){
						
					}
					
					$scope.updateNote = function(note) {
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					
					$scope.pinStatus = false;
					
					
					$scope.pinUnpin = function() {
						$scope.pinStatus = !$scope.pinStatus;
						console.log($scope.pinStatus);
					}
					
					$scope.addNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						$scope.notes.pin = $scope.pinStatus;
						$scope.notes.noteStatus = "note";
						var a = homePageService.addNote($scope.notes);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							getAllNotes();
						}, function(response) {
							});
					}

					$scope.addArchiveNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						$scope.notes.pin = $scope.pinStatus;
						$scope.notes.noteStatus = "archive";
						var a = homePageService.addNote($scope.notes);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							getAllNotes();
						}, function(response) {
							});
					}
					
					
					$scope.copy = function(note) {
						note.id = 0;
						note.pin = "false";
						var a = homePageService.addNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/*
					 * $scope.popupCard=function(){
					 * document.getElementById("card").width="80%";
					 * document.getElementById("card").height="50%";
					 * document.getElementById("card").position="absolute" }
					 */

					$scope.logout = function() {
						console.log("this is logout ");
						localStorage.removeItem('token');
						$location.path('/login')
					}

				});