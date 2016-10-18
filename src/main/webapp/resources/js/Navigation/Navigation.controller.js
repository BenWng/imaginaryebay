'use strict';
(function(){
   angular
       .module("ShopApp").controller("navigationController",navigationController);
}())


function navigationController($scope,$http,UserService,$location){
    $scope.loggedInFlag=false;
    $scope.currentUser=UserService.returnUser();
    if ($scope.currentUser!==undefined){
        $scope.loggedInFlag=true;
        $scope.id = $scope.currentUser.id;
        $scope.adminFlag=$scope.currentUser.admin;
    }

    //ToDo: change this url
    $http.get("/item/sellercategories").then(
        function(res){
            $scope.categories=res.data;

        }
    )


    $scope.currentCategory="Category";


    $scope.changeCategory=function(category){
        $scope.currentCategory=category;
    }

    $scope.searchTerm=null;

    $scope.search=function(){
        var additionalInfo;
        if ($scope.currentCategory!=="Category" && $scope.searchTerm!==null) {
            var newSearchTerm = $scope.searchTerm.replace(" ", "+");
          additionalInfo = "cat=" +$scope.currentCategory+"&keyword="+newSearchTerm;

        }
        else if ($scope.currentCategory!=="Category"){
            additionalInfo = "cat=" +$scope.currentCategory;
        }
        else if ($scope.searchTerm!==null){
            var newSearchTerm = $scope.searchTerm.replace(" ", "+");
            additionalInfo = "keyword="+newSearchTerm;
        }
        else {
            additionalInfo = "";
        }


        $location.path("/app/search"+"/"+additionalInfo);
    }



}