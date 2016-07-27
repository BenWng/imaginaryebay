/**
 * Created by Brian on 7/26/2016.
 */
(function (){
    angular.module("ShopApp").factory("ItemService", ItemService);

    function ItemService($http) {

        var service = {
            getItem: getItem
        };
        return service;

        function getItem(itemId){
            var itemCollectionUrl = "/item/";
            return $http.get(itemCollectionUrl + itemId);
        }        
    }
}());
