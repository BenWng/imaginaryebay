/**
 * Created by Ben_Big on 3/27/16.
 */
'use strict';
(function(){
    angular.
    module("ShopApp").controller("loginController",loginController);

}());


function loginController($scope,$http,$route,UserService,$location,$q){

    $scope.onClick=function(){


        $http({
                  method:'POST',
                  url:"/login",
                  headers: {'Content-Type':'application/x-www-form-urlencoded'},
                  transformRequest: function(obj) {
                      var str = [];
                      for(var p in obj)
                          str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                      return str.join("&");
                  },
                  data:{username:$scope.email,password:$scope.password}
              }).then(
            function(res) {
                return $http.get("/user/email/"+$scope.email+"/");
            },
            function (res) {
                window.alert("user name or password is incorrect");
                return $q.reject();
            }
        ).then(
            function(res){
                var user=res.data;
                UserService.setUser(user);
                $location.path("/app/home");
            }
        );


        /*
         $http.post("/login",credential)
         .then(
         function(res){
         var user=res.data;
         if (user.id<0){
         window.alert("user name or password is incorrect");
         }
         else{
         UserService.setUser(user);
         console.log(UserService.returnUser());
         $location.path("#/app/home");
         }
         }) */
    }
}