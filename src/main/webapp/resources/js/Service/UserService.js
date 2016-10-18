/**
 * Created by Ben_Big on 3/24/16.
 */
'use strict';
(function (){
    angular.module("ShopApp")
        .factory("UserService", UserService);

    function UserService($cookieStore) {

        var currentUser;

        var service = {
            returnUser: returnUser,
            setUser: setUser,
            deleteUser:deleteUser

        };
        return service;

        function returnUser() {
            if(currentUser!==undefined) {
                return currentUser;
            }
            else{
                currentUser=$cookieStore.get("user");
                return currentUser;
            }
        }

        function setUser(newUser) {
            currentUser=newUser;
            $cookieStore.remove("user");
            $cookieStore.put("user",currentUser);
        }

        function deleteUser(){
            currentUser=undefined;
            $cookieStore.remove("user");
        }

        
    }
}());
