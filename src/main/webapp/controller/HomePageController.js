var toDo = angular.module('toDo');

toDo
		.controller(
				'homeController',
				function($scope, homePageService, $uibModal, toastr, $location, $filter, $interval,
						fileReader, $state) {

					getUser();
					getUserLabel();
					getAllNotes();
					checktime();
					
					/*get the current user details*/
					function getUser() {
						var a = homePageService.getData('currentUser','POST');
						a.then(function(response) {
							$scope.User = response.data;
							console.log("user data");
							console.log(response.data);
						}, function(response) {

						});
					}
					
					
					/*get the current user labels*/
					function getUserLabel() {
						var a = homePageService.getData('note/currentUserLabel','POST');
						a.then(function(response) {
							$scope.UserLabels = response.data;
							console.log("inside the get user label");
							console.log($scope.UserLabels);
						}, function(response) {

						});
					}
					
					
					/*Add a label*/
					/*$scope.LabelNote=function(note,label){
//						var label= document.getElementById("labelName").value;
						note.labels.push(label);
					var a=homePageService.service('note/noteUpdate','POST',note);
					a.then(function(response) {
					},function(response){
					});
					}*/
					
					/*remove and add a label*/
					$scope.ToggleLabel=function(note,label){
						var index=-1;
						for(var i=0;i<note.labels.length;i++){
							if(note.labels[i].labelName == label.labelName){
								index=i;
							}
						}
						if(index == -1){
							note.labels.push(label);
						}
						else{
						note.labels.splice(index,1);
						}
						var a=homePageService.service('note/noteUpdate','POST',note);
						a.then(function(response) {
						},function(response){
						});
					}
					
					$scope.checkStatus=function(note,label){
						for(var i=0;i<note.labels.length;i++){
							if(note.labels[i].labelName===label.labelName){
								return true;
							}
						}
						return false;
					}
					
					
					
					/*Add a label*/
					$scope.addLabel=function(){
						var label= document.getElementById("labelName").value;
						if(label!=""){
							var obj={};
							obj.labelName=label;
							
					var a=homePageService.service('note/AddLabel','POST',obj);
					a.then(function(response) {
						document.getElementById("labelName").value="";
						getUserLabel();
					},function(response){
					});
					}
					}
					
					$scope.deleteLabel=function(label){
						var a=homePageService.service('note/DeleteLabel','POST',label);
						a.then(function(response) {
							getUserLabel();
							getAllNotes();
						},function(response){
						});
					}

					/*image upload*/
					$scope.imageSrc = "";

					$scope.$on("fileProgress", function(e, progress) {
						$scope.progress = progress.loaded / progress.total;
					});

					/*check from image upload type(add note, present note, user profile)*/
					$scope.openImageUploader = function(type) {
						$scope.type = type;
						$('#imageuploader').trigger('click');
					}
					
					
					/*update the note with new color*/
					$scope.changeColor = function(note) {
						var a = homePageService.service('note/changeColor','POST',note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {

						});
					}

					/*default color while adding a new note*/
					$scope.AddNoteColor = "#ffffff";

					/*set the color for the new note*/
					$scope.addNoteColorChange = function(color) {
						$scope.AddNoteColor = color;
					}

					
					read();
					function read(){
						$scope.ListView = localStorage.getItem('view');
					}
					
					$scope.ListViewToggle = function() {
						if ($scope.ListView === true) {
							$scope.ListView = false;
							localStorage.setItem('view',false);
							var element = document
							.getElementsByClassName('card');
					for (var i = 0; i < element.length; i++) {
						element[i].style.width = "900px";
						
					}
						} else {
							$scope.ListView = true;
							localStorage.setItem('view',true);
								var element = document
										.getElementsByClassName('card');
								for (var i = 0; i < element.length; i++) {
									element[i].style.width = "300px";
								}
						}
					}
					
					
					$scope.removeReminder=function(note){
						note.reminderStatus="";
						var a=homePageService.service('note/noteUpdate','POST',note);
						a.then(function(response) {
						},function(response){
						});
					}

					/*Add color and */
					$scope.colors = [
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

					/*change the navbar color and content*/
					if ($state.current.name == "home") {
						$scope.navBarColor = "#ffbb33";
						$scope.contentable = true;
						$scope.searching=false;
						$scope.navBarHeading = "Google Keep";
					} else if ($state.current.name == "reminder") {
						$scope.navBarColor = "#607D8B";
						$scope.contentable = true;
						$scope.searching=false;
						$scope.navBarHeading = "Reminder";
					} else if ($state.current.name == "trash") {
						$scope.navBarHeading = "Trash";
						$scope.contentable = false;
						$scope.searching=false;
						$scope.navBarColor = "#636363";
					} else if ($state.current.name == "archive") {
						$scope.navBarColor = "#607D8B";
						$scope.contentable = true;
						$scope.searching=false;
						$scope.navBarHeading = "Archive";
					}else if ($state.current.name == "searchPage") {
						$scope.navBarColor = "#3e50b4";
						$scope.contentable = true;
						$scope.searching=true;
						$scope.navBarHeading = "Search";
					}
					
					/*go to search page*/
					$scope.gotoSearch=function(){
						$location.path("/searchPage");
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

					/*share the note on facebook*/
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
					
					
					/*change the note color*/
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

					/*add a reminder to new note*/
					$scope.AddReminder='';
					$scope.openAddReminder=function(){
					   	$('#datepicker').datetimepicker();
					   	$scope.AddReminder= $('#datepicker').val();
				}
					
					/*Add a reminder to existing note*/
					$scope.reminder ="";
					$scope.openReminder=function(note){
						   	$('.reminder').datetimepicker();
						   	 var id = '#datepicker' + note.id;
						   	$scope.reminder = $(id).val();
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
					
					/*set tomorrows reminder*/
					$scope.tomorrowsReminder=function(notes){
						$scope.currentTime=$filter('date')(new Date().getTime() + 24 * 60 * 60 * 1000,'MM/dd/yyyy');
						notes.reminderStatus=$scope.currentTime+" 8:00 AM";
						$scope.updateNote(notes);
					}
					
					/*set next week reminder*/
					$scope.NextweekReminder=function(notes){
						$scope.currentTime=$filter('date')(new Date().getTime() + 7 * 24 * 60 * 60 * 1000,'MM/dd/yyyy');
						notes.reminderStatus=$scope.currentTime+" 8:00 AM";
						$scope.updateNote(notes);
					}
					
					/*set later todays reminder*/
					$scope.todaysReminder=function(notes){
						$scope.currentTime=$filter('date')(new Date(), 'MM/dd/yyyy');
						var currentDate=new Date().getHours();
						if(currentDate >= 7){
							notes.reminderStatus=$scope.currentTime+" 8:00 PM";	
						}
						if(currentDate < 7){
							notes.reminderStatus=$scope.currentTime+" 8:00 AM";
						}
						
						$scope.updateNote(notes);
					}
					

					$scope.TodaylaterReminder=true;
					
					/*check weather to display later todays reminder or not*/
					function checktime(){
						var currentDate=new Date().getHours();
						if(currentDate > 19){
							$scope.TodaylaterReminder=false;
						}
						if(currentDate > 1){
							$scope.TodaylaterReminder=true;
						}
					}
					
					

					/* display notes */
					function getAllNotes() {
						var b = homePageService.getData('note/AllNodes','GET');
						b.then(function(response) {
							$scope.userNotes = response.data;
							console.log($scope.userNotes);
							$interval(function(){
								var i=0;
								for(i;i<$scope.userNotes.length;i++){
									if($scope.userNotes[i].reminderStatus!='false'||$scope.userNotes[i].reminderStatus!=''){
										
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
						var a = homePageService.service('note/noteUpdate','POST',note);
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
						var a = homePageService.service('note/noteUpdate','POST',note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* restore note */
					$scope.restoreNote = function(note) {
						note.pin = "false";
						note.deleteStatus = "false";
						var a = homePageService.service('note/noteUpdate','POST',note);
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
						note.reminderStatus = "";
						var a = homePageService.service('note/noteUpdate','POST',note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}

					/* delete note forever */
					$scope.deleteNoteForever = function(id) {
						console.log("id is ..." + id);
						var a = homePageService.getData('note/DeleteNotes/'+id,'DELETE');
						a.then(function(response) {
							
							getAllNotes();
						}, function(response) {
						});
					}

					$scope.removeImageFromEdit=function(note){
						console.log("in removeImageFromEdit");
						console.log(note);
						note.image="";
						var a = homePageService.service('note/noteUpdate','POST',note);
						a.then(function(response) {
							getAllNotes();
						}, function(response) {
						});
					}
					
					
					/* update the note */
					$scope.updateNote = function(note) {

						var a = homePageService.service('note/noteUpdate','POST',note);
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

					$scope.Reminder = '';

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

							var a = homePageService.service('note/AddNotes','POST',$scope.notes);
							a
									.then(
											function(response) {
												document
														.getElementById("notetitle").innerHTML = "";
												document
														.getElementById("noteDescription").innerHTML = "";
												$scope.pinStatus = false;
												$scope.AddReminder='';
												$scope.AddNoteColor = "#ffffff";
												$scope.removeImage();
												getAllNotes();
												 toastr.success('Note added', 'successfully');
											}, function(response) {
											});
						}
					}

					/*change user profile picture*/
					$scope.changeProfile=function(user){
					var a=homePageService.service('profileChange','POST',user);
					a.then(function(response) {
					},function(response){
					});
					}
					
					/*remove the image after adding the new note*/
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
							var a = homePageService.service('note/AddNotes','POST',$scope.notes);
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
												toastr.success('Note added to Archive', 'success');
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
						note.pin = "false";
						var a = homePageService.service('note/AddNotes','POST',note);
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
					
					
					$scope.openCollaborator=function(note){
						$scope.note = note;
						$scope.user=$scope.User;
						modalInstance = $uibModal.open({
							templateUrl : 'template/collaboratorNote.html',
							scope : $scope,
						});
					}
					
					/*open a add label*/
					$scope.openAddLabel=function(){
						/*$scope.userlab=$scope.UserLabels;*/
					
						modalInstance = $uibModal.open({
							templateUrl : 'template/AddNewLabel.html',
							scope : $scope
						});
					}
					
					var collborators=[];
					
					$scope.getUserlist=function(note){
						var obj={};
						console.log(note);
						obj.note=note;
						$scope.User.lastName='';
						obj.ownerId=$scope.User;
						obj.shareWithId={'email':''};
						console.log(obj);
						var url='note/collaborate';
						
						var users=homePageService.service(url,'POST',obj);
				        users.then(function(response) {
							$scope.users= response.data; 
							note.collabratorUsers = response.data;
						}, function(response) {
							$scope.users={};
						});
						return collborators;
					}
					
					$scope.collborate=function(note){
						var obj={};
						obj.note=note;
						obj.ownerId=$scope.User;
						obj.shareWithId=$scope.shareWith;
						
						var url='note/collaborate';
						
						var users=homePageService.service(url,'POST',obj);
				        users.then(function(response) {
							$scope.users= response.data; 
							note.collabratorUsers = response.data; 
							$scope.shareWith=null;
							modalInstance.close('resetmodel');
						}, function(response) {
							$scope.users=response.data;
						});
					}

					$scope.getOwner = function(note) {
						var url = 'note/getOwner';
						var users = homePageService.service(url, 'POST', note);
						users.then(function(response) {
							$scope.owner = response.data;
						}, function(response) {
							$scope.users = {};
						});
					}

					$scope.removeCollborator = function(note, user) {
						var obj = {};
						var url = 'note/removeCollborator';
						obj.note = note;
						obj.ownerId = {
							'email' : ''
						};
						obj.shareWithId = user;
						var token = localStorage.getItem('token');
						var users = homePageService.service(url, 'POST', obj);
						users.then(function(response) {
							$scope.collborate(note, $scope.owner);
							modalInstance.close('resetmodel');
							getAllNotes();
						}, function(response) {
						});
					}
				});