var toDo = angular.module('toDo');

toDo.controller(
				'homeController',
				function($scope, homePageService, $uibModal, $location,$state) {
					
					
					$scope.changeColor=function(note){
						
						var a = homePageService.changeColor(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
							
						});
					}
					
					$scope.AddNoteColor="#ffffff";
					
					$scope.addNoteColorChange=function(color){
						$scope.AddNoteColor=color;
					}
					
					$scope.ListView=true;
					
					$scope.ListViewToggle=function(){
						if($scope.ListView==true){
							$scope.ListView=false;
							listGrideView();
						}
						else{
						$scope.ListView=true;
						listGrideView();
						}
					}
					
					listGrideView();
					
					function listGrideView(){
						if($scope.ListView){
							var element = document.getElementsByClassName('card');
							for(var i=0;i<element.length;i++){
								element[i].style.width="900px";
							}
						}
						else{
							var element = document.getElementsByClassName('card');
							for(var i=0;i<element.length;i++){
								element[i].style.width="300px";
							}
						}
					}
					
					$scope.colors=[/*"#fff","#f1c40f","#280275"*/
						
						{
							"color":'#ffffff',
							"path":'image/white.png'
						},
						{
							"color":'#e74c3c',
							"path":'image/Red.png'
						},
						{
							"color":'#ff8c1a',
							"path":'image/orange.png'
						},
						{
							"color":'#fcff77',
							"path":'image/lightyellow.png'
						},
						{
							"color":'#80ff80',
							"path":'image/green.png'
						},
						{
							"color":'#99ffff',
							"path":'image/skyblue.png'
						},
						{
							"color":'#0099ff',
							"path":'image/blue.png'
						},
						{
							"color":'#1a53ff',
							"path":'image/darkblue.png'
						},
						{
							"color":'#9966ff',
							"path":'image/purple.png'
						},
						{
							"color":'#ff99cc',
							"path":'image/pink.png'
						},
						{
							"color":'#d9b38c',
							"path":'image/brown.png'
						},
						{
							"color":'#bfbfbf',
							"path":'image/grey.png'
						}
					];
					
					
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
					
					/* Edit a note in modal */
					/*$scope.open = function(note) {
						$scope.note=note;
						$scope.modalInstance = $uibModal.open({
						templateUrl: 'template/editNote.html',
						data:note
						});
						};*/
					
					
						$scope.open = function (note) {
						$scope.note = note;
						modalInstance = $uibModal.open({
						templateUrl: 'template/editNote.html',
						scope : $scope
						});
						};
					
					
					/*toggle side bar*/
					$scope.showSideBar = false;
					$scope.sidebarToggle = function() {
						if($scope.showSideBar){
							$scope.showSideBar=false;
							document.getElementById("mainWrapper").style.paddingLeft = "200px";
						}
						else{
							$scope.showSideBar = true;
							document.getElementById("mainWrapper").style.paddingLeft = "70px";
						}
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
							$scope.userNotes = response.data;
							
						}, function(response) {
							$scope.logout();
						});
					}

					
					/*archive notes*/
					$scope.archiveNote=function(note){
						note.archiveStatus="true";
						note.noteStatus="false";
						note.pin="false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					/*unarchive notes*/
					$scope.unarchiveNote=function(note){
						note.noteStatus="true";
						note.archiveStatus="false";
						note.pin="false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					 /*restore note*/ 
					$scope.restoreNote=function(note){
						note.pin="false";
						note.deleteStatus="false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					 /*Add notes to trash*/ 
					$scope.deleteNote=function(note){
						note.pin="false";
						note.deleteStatus="true";
						note.reminderStatus="false";
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
					
					
					/*update the note*/
					$scope.updateNote = function(note) {
						console.log(note);
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					$scope.pinStatus =false;
					
					/*pin unpin the notes*/
					$scope.pinUnpin = function() {
						if($scope.pinStatus == false){
						$scope.pinStatus = true;
					}
					else {
						$scope.pinStatus=false;
					}
					}
					
					$scope.Reminder=false;
					
					$scope.addReminder=function(){
						if($scope.Reminder==false){
						$scope.Reminder=true;
						}else{
							$scope.Reminder=false;
						}
					}
					
					/*add a new note*/
					$scope.addNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						
						if($scope.notes.title=="" && $scope.notes.description== ""){
							$scope.pinStatus = false;
							$scope.AddNoteColor="#ffffff";
							$scope.AddNoteBox = false;
						}
						else if($scope.notes.title!="" || $scope.notes.description!= ""){
						$scope.notes.pin = $scope.pinStatus;
						$scope.notes.noteStatus = "true";
						$scope.notes.reminderStatus= "false";
						$scope.notes.archiveStatus= "false";
						$scope.notes.deleteStatus = "false";
						$scope.notes.noteColor=$scope.AddNoteColor;
						
						var a = homePageService.addNote($scope.notes);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							$scope.AddNoteColor="#ffffff";
							getAllNotes();
						}, function(response) {
							});
						}
					}
					
					
					$scope.updateNoteModal=function(note){
						note.title = document
								.getElementById("modifiedtitle").innerHTML;
						note.description = document
								.getElementById("modifieddescreption").innerHTML;
						$scope.updateNote(note);
						modalInstance.close('resetmodel');
					}
					
					/*add a new note to archive*/
					$scope.addArchiveNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						if($scope.notes.title=="" && $scope.notes.description== ""){
							$scope.pinStatus = false;
							$scope.AddNoteColor="#ffffff";
							$scope.AddNoteBox = false;
						}
						else if($scope.notes.title!="" || $scope.notes.description!= ""){
						$scope.notes.pin = "false";
						$scope.notes.noteStatus = "false";
						$scope.notes.archiveStatus = "true";
						$scope.notes.deleteStatus = "false";
						$scope.notes.reminderStatus = "false";
						var a = homePageService.addNote($scope.notes);
						a.then(function(response) {
							document.getElementById("notetitle").innerHTML = "";
							document.getElementById("noteDescription").innerHTML = "";
							$scope.pinStatus = false;
							$scope.AddNoteColor="#ffffff";
							getAllNotes();
						}, function(response) {
							});
						}
					}
					
					
					/*make a copy of the note*/
					$scope.copy = function(note) {
						note.id = 0;
						note.noteStatus="true";
						note.archiveStatus="false";
						note.deleteStatus="false";
						note.reminderStatus="false";
						note.pin = "false";
						var a = homePageService.addNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}


					/*logout user*/
					$scope.logout = function() {					
							localStorage.removeItem('token');
							$location.path('/login');						
					}
					
					/*$scope.open = function () {

					    var modalInstance = $modal.open({
					      templateUrl: 'myModalContent.html',
					      
					    });

					      };*/
					

				});