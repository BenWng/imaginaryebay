/**
 * Created by Ben_Big on 8/2/16.
 */


'use strict';

(function(){
    angular.module("ShopApp").controller("searchController",searchController);
})();

function searchController($scope, UserService, $location){
    this.numOfItemsOnEachPage=9;
    this.totalUrl="item/count";
    this.itemUrl="/item/page/";
    this.picUrl="http://placehold.it/800x400";

    var path=$location.path();

    this.dumbParameter=path.split(/[/ ]+/).pop();

}