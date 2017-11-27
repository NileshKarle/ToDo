var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homePageService, $uibModal, toastr, $location, $filter, $interval,
						fileReader, $state) {

					getUser();

					function getUser() {
						var a = homePageService.getUser();
						a.then(function(response) {
							$scope.User = response.data;
						}, function(response) {

						});
					}

					$scope.imageSrc = "";

					$scope.$on("fileProgress", function(e, progress) {
						$scope.progress = progress.loaded / progress.total;
					});

					$scope.openImageUploader = function(type) {
						$scope.type = type;
						$('#imageuploader').trigger('click');
					}
					
					

					$scope.changeColor = function(note) {

						var a = homePageService.changeColor(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {

						});
					}

					$scope.AddNoteColor = "#ffffff";

					$scope.addNoteColorChange = function(color) {
						$scope.AddNoteColor = color;
					}

					$scope.ListView = true;

					$scope.ListViewToggle = function() {
						if ($scope.ListView == true) {
							$scope.ListView = false;
							listGrideView();
						} else {
							$scope.ListView = true;
							listGrideView();
						}
					}

					listGrideView();

					function listGrideView() {
						if ($scope.ListView) {
							var element = document
									.getElementsByClassName('card');
							for (var i = 0; i < element.length; i++) {
								element[i].style.width = "900px";
							}
						} else {
							var element = document
									.getElementsByClassName('card');
							for (var i = 0; i < element.length; i++) {
								element[i].style.width = "300px";
							}
						}
					}

					$scope.colors = [/* "#fff","#f1c40f","#280275" */

					{
						"color" : '#ffffff',
						"path" : 'image/white.png'
					}, {
						"color" : '#e74c3c',
						"path" : 'image/Red.png'
					}, {
						"color" : '#ff8c1a',
						"path" : 'image/orange.png'
					}, {
						"color" : '#fcff77',
						"path" : 'image/lightyellow.png'
					}, {
						"color" : '#80ff80',
						"path" : 'image/green.png'
					}, {
						"color" : '#99ffff',
						"path" : 'image/skyblue.png'
					}, {
						"color" : '#0099ff',
						"path" : 'image/blue.png'
					}, {
						"color" : '#1a53ff',
						"path" : 'image/darkblue.png'
					}, {
						"color" : '#9966ff',
						"path" : 'image/purple.png'
					}, {
						"color" : '#ff99cc',
						"path" : 'image/pink.png'
					}, {
						"color" : '#d9b38c',
						"path" : 'image/brown.png'
					}, {
						"color" : '#bfbfbf',
						"path" : 'image/grey.png'
					} ];

					if ($state.current.name == "home") {
						$scope.navBarColor = "#ffbb33";
						$scope.contentable = true;
						$scope.navBarHeading = "Google Keep";
					} else if ($state.current.name == "reminder") {
						$scope.navBarColor = "#607D8B";
						$scope.contentable = true;
						$scope.navBarHeading = "Reminder";
					} else if ($state.current.name == "trash") {
						$scope.navBarHeading = "Trash";
						$scope.contentable = false;
						$scope.navBarColor = "#636363";
					} else if ($state.current.name == "archive") {
						$scope.navBarColor = "#607D8B";
						$scope.contentable = true;
						$scope.navBarHeading = "Archive";
					}

					/* Edit a note in modal */

					$scope.EditNoteColor = "#ffffff";

					/* open a model */
					$scope.open = function(note) {
						$scope.EditNoteColor = note.noteColor;
						$scope.note = note;
						modalInstance = $uibModal.open({
							templateUrl : 'template/editNote.html',
							scope : $scope
						});
					};

					$scope.socialShare = function(note) {
						FB.init({
						appId : '132217884131949',
						status : true,
						cookie : true,
						xfbml : true,
						version : 'v2.4'
						});

						FB.ui({
						method : 'share_open_graph',
						action_type : 'og.likes',
						action_properties : JSON.stringify({
						object : {
						'og:title' : note.title,
						'og:description' :note.description
						}
						})
						}, function(response) {
						if (response && !response.error_message) {
							toastr.success('Note shared', 'successfully');
						} else {
							toastr.success('Note share', 'Error');
						}
						});
						};
					
					/* open collaborator modal */
					$scope.openCollaborator = function(note) {
						$scope.noteUser = $scope.User;
						console.log($scope.noteUser);
						modalInstance = $uibModal.open({
							templateUrl : 'template/collaboratorNote.html',
							scope : $scope
						});
					}

					$scope.changeColorInModal = function(color) {
						$scope.EditNoteColor = color;
					}

					/* toggle side bar */
					$scope.showSideBar = false;
					$scope.sidebarToggle = function() {
						if ($scope.showSideBar) {
							$scope.showSideBar = false;
							document.getElementById("mainWrapper").style.paddingLeft = "200px";
						} else {
							$scope.showSideBar = true;
							document.getElementById("mainWrapper").style.paddingLeft = "70px";
						}
					}

					/* toggle AddNote box */
					$scope.AddNoteBox = false;
					$scope.ShowAddNote = function() {
						$scope.AddNoteBox = true;
					}

					
					
					$scope.AddReminder='';
					$scope.openAddReminder=function(){
					   	$('#datepicker').datetimepicker();
					   	$scope.AddReminder= $('#datepicker').val();
				}
					
					
					
					
					$scope.reminder ="";
					$scope.openReminder=function(note){
						   	$('.reminder').datetimepicker();
						   	 var id = '#datepicker' + note.id;
						   	$scope.reminder = $(id).val();
						   	//note.reminderStatus=$scope.reminder;
						   	if($scope.reminder === "" || $scope.reminder === undefined){
						   		console.log(note);
						   		console.log($scope.reminder);
						   	}
						   	else{
						   		console.log($scope.reminder);
						   		note.reminderStatus=$scope.reminder;
						   		$scope.updateNote(note);
						   		$scope.reminder="";
						   }
					}
					
					
					
					getAllNotes();

					/* display notes */
					function getAllNotes() {
						var b = homePageService.allNotes();
						b.then(function(response) {
							$scope.userNotes = response.data;
							$interval(function(){
								var i=0;
								for(i;i<$scope.userNotes.length;i++){
									if($scope.userNotes[i].reminderStatus!='false'){
										
										var currentDate=$filter('date')(new Date(),'MM/dd/yyyy h:mm a');
										
										if($scope.userNotes[i].reminderStatus === currentDate){
											
											toastr.success('You have a reminder for a note', 'check it out');
										}
									}
									
								}
							},9000);
							
							
						}, function(response) {
							$scope.logout();
						});
					}

					/* archive notes */
					$scope.archiveNote = function(note) {
						note.archiveStatus = "true";
						note.noteStatus = "false";
						note.pin = "false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* unarchive notes */
					$scope.unarchiveNote = function(note) {
						note.noteStatus = "true";
						note.archiveStatus = "false";
						note.pin = "false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* restore note */
					$scope.restoreNote = function(note) {
						note.pin = "false";
						note.deleteStatus = "false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							modalInstance.close('resetmodel');
							getAllNotes();
						}, function(response) {
						});
					}

					/* Add notes to trash */
					$scope.deleteNote = function(note) {
						note.pin = "false";
						note.deleteStatus = "true";
						note.reminderStatus = "false";
						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* delete note forever */
					$scope.deleteNoteForever = function(id) {
						console.log("id is ..." + id);
						var a = homePageService.deleteNoteForever(id);
						a.then(function(response) {
							modalInstance.close('resetmodel');
							getAllNotes();
						}, function(response) {
						});
					}

					/* update the note */
					$scope.updateNote = function(note) {

						var a = homePageService.updateNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					$scope.pinStatus = false;

					/* pin unpin the notes */
					$scope.pinUnpin = function() {
						if ($scope.pinStatus == false) {
							$scope.pinStatus = true;
						} else {
							$scope.pinStatus = false;
						}
					}

					$scope.Reminder = false;

					/*$scope.addReminder = function() {
						if ($scope.Reminder == false) {
							$scope.Reminder = true;
						} else {
							$scope.Reminder = false;
						}
					}*/

					/* add a new note */
					$scope.addNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;

						if ($scope.notes.title == ""
								&& $scope.notes.description == ""
								&& $scope.imageSrc == "") {
							$scope.pinStatus = false;
							$scope.AddReminder='';
							$scope.AddNoteColor = "#ffffff";
							$scope.AddNoteBox = false;

						} else if ($scope.notes.title != ""
								|| $scope.notes.description != ""
								|| $scope.notes.image != "") {
							$scope.notes.pin = $scope.pinStatus;
							$scope.notes.noteStatus = "true";
							$scope.notes.reminderStatus = $scope.AddReminder;
							$scope.notes.archiveStatus = "false";
							$scope.notes.deleteStatus = "false";
							$scope.notes.image = $scope.imageSrc;
							$scope.imageSrc = "";
							$scope.notes.noteColor = $scope.AddNoteColor;

							var a = homePageService.addNote($scope.notes);
							a
									.then(
											function(response) {
												document
														.getElementById("notetitle").innerHTML = "";
												document
														.getElementById("noteDescription").innerHTML = "";
												$scope.pinStatus = false;
												$scope.AddReminder='';
												$scope.AddNoteColor = "#ffffff";s
												$scope.removeImage();
												 toastr.success('Note added', 'successfully');
												getAllNotes();
											}, function(response) {
											});
						}
					}

					$scope.changeProfile=function(user){
					var a=homePageService.changeProfile(user);
					a.then(function(response) {
					
					},function(response){
						
					});
					
					}
					
					$scope.removeImage = function() {
						$scope.AddNoteBox = false;
						$scope.addimg = undefined;
					}

					/* Update the header and title from modal */
					$scope.updateNoteModal = function(note) {
						note.title = document.getElementById("modifiedtitle").innerHTML;
						note.description = document
								.getElementById("modifieddescreption").innerHTML;
						note.noteColor = $scope.EditNoteColor;
						$scope.updateNote(note);
						modalInstance.close('resetmodel');
					}

					/* archive a note from a modal */
					$scope.UnarchiveandArchiveFromModal = function(note) {
						note.title = document.getElementById("modifiedtitle").innerHTML;
						note.description = document
								.getElementById("modifieddescreption").innerHTML;
						note.noteColor = $scope.EditNoteColor;
						if (note.archiveStatus == "false") {
							note.archiveStatus = "true";
							note.noteStatus = "false";
							note.pin = "false";
							$scope.updateNote(note);
							modalInstance.close('resetmodel');
						} else {
							note.archiveStatus = "false";
							note.noteStatus = "true";
							$scope.updateNote(note);
							modalInstance.close('resetmodel');
						}
					}

					/* add a new note to archive */
					$scope.addArchiveNote = function() {
						$scope.notes = {};
						$scope.notes.title = document
								.getElementById("notetitle").innerHTML;
						$scope.notes.description = document
								.getElementById("noteDescription").innerHTML;
						if ($scope.notes.title == ""
								&& $scope.notes.description == "") {
							$scope.pinStatus = false;
							$scope.AddNoteColor = "#ffffff";
							$scope.AddNoteBox = false;
						} else if ($scope.notes.title != ""
								|| $scope.notes.description != "") {
							$scope.notes.pin = "false";
							$scope.notes.noteStatus = "false";
							$scope.notes.reminderStatus = $scope.AddReminder;
							$scope.notes.deleteStatus = "false";
							$scope.notes.reminderStatus = "false";
							var a = homePageService.addNote($scope.notes);
							a
									.then(
											function(response) {
												document
														.getElementById("notetitle").innerHTML = "";
												document
														.getElementById("noteDescription").innerHTML = "";
												$scope.pinStatus = false;
												$scope.AddNoteColor = "#ffffff";
												$scope.AddReminder='';
												getAllNotes();
											}, function(response) {
											});
						}
					}

					/* make a copy of the note */
					$scope.copy = function(note) {
						note.id = 0;
						note.noteStatus = "true";
						note.archiveStatus = "false";
						note.deleteStatus = "false";
						note.reminderStatus = "false";
						note.pin = "false";
						var a = homePageService.addNote(note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* logout user */
					$scope.logout = function() {
						localStorage.removeItem('token');
						$location.path('/login');
					}

					/*
					 * $scope.open = function () {
					 * 
					 * var modalInstance = $modal.open({ templateUrl:
					 * 'myModalContent.html',
					 * 
					 * });
					 *  };
					 */
					$scope.type = {};
					$scope.type.image = '';

					$scope.$watch('imageSrc', function(newimg, oldimg) {
						if ($scope.imageSrc != '') {
							if ($scope.type === 'input') {
								$scope.addimg = $scope.imageSrc;
							} 
							else if($scope.type === 'user'){
								$scope.User.profile=$scope.imageSrc;
								$scope.changeProfile($scope.User);
							}
							else {
								console.log();
								$scope.type.image = $scope.imageSrc;
								$scope.updateNote($scope.type);
							}
						}

					});

				});