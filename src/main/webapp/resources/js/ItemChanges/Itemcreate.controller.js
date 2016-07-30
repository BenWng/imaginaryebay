'use strict';
(function(){
    angular.
    module("ShopApp").controller("itemcreateController",itemcreateController);

}());


function itemcreateController($scope,$http, UserService, $location){

    var date = new Date();
    date = date.toISOString().substring(0,10);
    $scope.auctet = date;
    var maxDescLen = 255;
    $scope.remChar = maxDescLen;
    
    $scope.create=function(){

        var userr=UserService.returnUser();

        // need to be logged in to create an item
        if(userr == null){
            window.alert("You must be logged in to create an item.");
            $location.path("app/login");
        }
        else{

            // empty category
            var cat=$scope.category;
            if(cat == ""){
                cat=null;
            }

            // handle diff type of auctions
            var etToPass;
            var aucttype = $scope.auctiontype;
            // long auctions
            if (aucttype == "long") {
                etToPass = $scope.endtime;
            } // short auctions
            else{
                var currDate = new Date();
                currDate.setHours((currDate.getHours()*1) + ($scope.shortendtime*1));
                etToPass = currDate;
            }

            var newitem ={
                name:$scope.name,
                description:$scope.description,
                category:cat,
                endtime:etToPass,
                price:$scope.price,
                // backend handles assigning the right user to it
            };

            $http.post("/item", newitem)
                .then(
                    function(res){
                        window.alert("Item created successfully!");
                        var id = res.data.id;
                        $location.path("app/item/" + id);
                        uploadAll(id);

                    }, function(res){
                        window.alert("Item creation failed: " + res.data.detailedMessage);
                    });
            
        }

    };

    var pictures = [];

    $scope.addPic = function () {
        var file = document.getElementById('file').files[0]
        var fd = new FormData();
        fd.append("file", file);
        pictures.push(fd);
        console.log("added pic");
    };

    function uploadAll(id){
        for (var i = 0; i < pictures.length; i++) {
            uploadSinglePic(pictures[i], id);
        }
    };

    function uploadSinglePic(pic, id){
        $http.post("/item/" + id + "/picture", pic, {
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        }).success(function () {
            console.log("success");
        })
            .error(function (res) {
                console.log("fail");
                console.log(res.data.detailedMessage);
            });
    }

    $scope.lenCheck = function(){
        if ($scope.description.length > maxDescLen){
            $scope.description = $scope.description.substring(0, maxDescLen);
        }
        $scope.remChar = maxDescLen - $scope.description.length;
    }
    
}