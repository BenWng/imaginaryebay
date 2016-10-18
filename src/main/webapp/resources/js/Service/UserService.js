/**
 * Created by Ben_Big on 3/24/16.
 */
'use strict';
(function (){
    angular.module("ShopApp")
        .factory("UserService", UserService);

    function UserService($cookies) {

        var currentUser;

        var service = {
            returnUser: returnUser,
            setUser: setUser

        };
        return service;

        function returnUser() {
            if(currentUser!==undefined) {
                return currentUser;
            }
            else{
                return $cookies.get("user");
            }
        }

        function setUser(newUser) {
            currentUser=newUser;
            $cookies.put("user",currentUser);
        }
        
    }
}());
