var toDo = angular.module('toDo');
toDo.controller('forgotpasswordController', function($scope, forgotpasswordService ,$location){
	
	$scope.changePassword = function(){
		console.log("its in the forgotpassword controller")
		var b=forgotpasswordService.changePassword($scope.user,$scope.error3);
		
			b.then(function(response) {
				console.log("successfully redirected..")
				$location.path('/login')
			},function(response){
				console.log("unsuccessfull ...")
				$scope.error3=response.data.responseMessage;
			});
			
	}
});