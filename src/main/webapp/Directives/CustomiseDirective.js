
var app = angular.module("toDo");
app.directive("topNavigationBar", function() {
    return {
    	templateUrl :'template/TopNavigationBar.html'
    };
});
app.directive("sideNavigationBar", function() {
        return {
        	templateUrl :'template/SideNavigationBar.html'
        };
});
