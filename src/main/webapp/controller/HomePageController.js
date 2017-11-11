var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homePageService, $location, $state) {
					console.log("main controller");
					
					
					console.log($state.current.name);
					
					if($state.current.name=="home"){
						$scope.navBarColor= "#ffbb33";
						$scope.navBarHeading="Google Keep";
					}
					else if($state.current.name=="reminder"){
						$scope.navBarColor="#607D8B"
						$scope.navBarHeading="Reminder";
					}
					else if($state.current.name=="trash"){
						$scope.navBarHeading="Trash";
						$scope.navBarColor="#636363"
					}
					else if($state.current.name=="archive"){
						$scope.navBarColor= "#607D8B";
						$scope.navBarHeading="Archive";
					}
					
					/*$scope.cardColor={'white','blue','red'}*/
					
					/*toggle side bar*/
					$scope.showSideBar = true;
					$scope.sidebarToggle = function() {
						$scope.showSideBar = !$scope.showSideBar;
					}
					
					/*toggle AddNote box*/
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}
					
					
					getAllNotes();

					/*display notes*/
					function getAllNotes() {
						var b = homePageService.allNotes();
						b.then(function(response) {
							console.log(response.data);
							$scope.userNotes = response.data;
						}, function(response) {
						});
					}

					
					/*archive notes*/
					$scope.archiveNote=function(note){
						note.noteStatus="archive";
						note.pin="false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					/*unarchive notes*/
					$scope.unarchiveNote=function(note){
						note.noteStatus="note";
						note.pin="false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					 /*Add notes to trash*/ 
					$scope.deleteNote=function(note){
						note.pin="false";
						note.noteStatus="delete";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					/*delete note forever*/
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
					
					/*update the note*/
					$scope.updateNote = function(note) {
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					
					$scope.pinStatus = false;
					
					/*pin unpin the notes*/
					$scope.pinUnpin = function() {
						$scope.pinStatus = !$scope.pinStatus;
						console.log($scope.pinStatus);
					}
					
					
					/*add a new note*/
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
					
					
					/*add a new note to archive*/
					$scope.addArchiveNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						$scope.notes.pin = "false";
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
					
					
					/*make a copy of the note*/
					$scope.copy = function(note) {
						note.id = 0;
						note.noteStatus="note";
						note.pin = "false";
						var a = homePageService.addNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}


					/*logout user*/
					$scope.logout = function() {
						console.log("this is logout ");
						localStorage.removeItem('token');
						$location.path('/login')
					}

				});