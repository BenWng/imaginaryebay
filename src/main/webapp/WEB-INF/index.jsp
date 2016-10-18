<%--
  Created by IntelliJ IDEA.
  User: Ben_Big
  Date: 7/1/16
  Time: 9:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="ShopApp">
<head>

    <title>Imaginary Ebay</title>

    <!-- Bootstrap Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    <!-- JQuery-->
    <script   src="https://code.jquery.com/jquery-2.2.4.min.js"   integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>

    <!-- Bootstrap Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

    <!-- Angular JS-->
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.min.js"/>"></script>

    <!-- Angular cookies-->
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-cookies.js"/>"></script>

    <!-- ng Route-->
    <script src="<c:url value="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-route.js" />"> </script>



    <%--Disqus directive--%>
    <script src="<c:url value="/resources/js/dirDisqus.js"/>"></script>


    <%--countdown timer--%>
    <script src="https://cdn.rawgit.com/globaljake/simple-inline-countdown-directive/master/directive/countdownTimer.js"></script>

    <%--ui-bootstrap for Angular--%>
    <script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.0.0/ui-bootstrap-tpls.min.js"/>"></script>

    <!-- Angular app-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/config.js"/>"></script>

    <!--Service-->
    <script src="<c:url value="/resources/js/Service/UserService.js"/>"></script>


    <!--page controller-->
    <script src="<c:url value="/resources/js/Home/Home.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/DisplayController/Display.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Login/Login.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Register/Registration.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Navigation/Navigation.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Profile/Profile.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Contact/Contact.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/ItemChanges/Itemupdate.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Item/Item.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/ItemChanges/Itemupdate.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Admin/Admin.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Shop/Shop.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/User/Userupdate.controller.js"/>"></script>
    <script src="<c:url value="/resources/js/Search/Search.controller.js"/>"></script>

    <base href="/">


    <base href="/">

</head>
<body>

        <!-- Page goes here-->
        <div ng-view></div>


</body>
</html>
