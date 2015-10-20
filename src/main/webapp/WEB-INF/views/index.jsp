<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" ng-app="libraryApp">
<head>
    <meta charset="utf-8">
    <title>Library Manager Admin Page</title>
    <meta name="description" content="Library Manager Admin Page">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>
    <spring:url value="/resources/core/css/angular-busy.min.css" var="busyCss"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${indexCss}" rel="stylesheet"/>
    <link href="${busyCss}" rel="stylesheet"/>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body ng-controller="LibraryController as libraryCtrl" style="margin-top: 80px">
<nav>
    <div class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Library Manager Admin Page</a>

            </div>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <span class="pull-right alert alert-info alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> {{libraryCtrl.message}}
                    </span>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">


    <modal title="Book Edit Form" visible="showModal">
        <div cg-busy="{promise:myPromise,message:'Loading...'}">
            <form role="form" name="bookEditForm" ng-submit="actionBook(book)" novalidate>
                <div class="form-group">
                    <span class="alert alert-info alert-dismissible" role="alert">
                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> {{libraryCtrl.message}} </span>
                </div>


                <div class="form-group">
                    <label for="book-name" class="col-sm-2 control-label">Name</label>

                    <div class="col-sm-10">
                        <input type="hidden" data-ng-model="id" value=""/>
                        <input type="text" data-ng-model="book.name" class="form-control ng-dirty" id="book-name"
                               placeholder="Enter book name" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="book-author" class="col-sm-2 control-label">Author</label>

                    <div class="col-sm-10">
                        <input type="text" data-ng-model="book.author" class="form-control ng-dirty" id="book-author"
                               placeholder="Enter book author" required/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="g-recaptcha" data-sitekey="6LffLA8TAAAAALG1Cc8vSvHRLvUy4u5oiWsw82AR"
                             ng-show="(modalAction=='add')"></div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-info" ng-disabled="bookEditForm.$invalid">Save</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    </modal>
    <div class="row">
        <div cg-busy="{promise:resultPromise,message:'Loading...'}" infinite-scroll="loadMoreBook()"
             infinite-scroll-distance="0">
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">Operations</div>
                <div class="panel-body">
                    <p>
                        <button ng-click="bookEditModal(book, 'add')" class="btn btn-default">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Book
                        </button>
                    </p>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Author</th>
                        <th>Operations</th>
                    </tr>
                    </thead>
                    <tr ng-repeat="book in libraryCtrl.books">
                        <td>{{book.name}}</td>
                        <td>{{book.author}}</td>
                        <td>
                            <button type="button" class="btn btn-default btn-sm"
                                    ng-click="bookEditModal(book, 'update')">
                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> Edit
                            </button>
                            <button type="button" class="btn btn-danger btn-sm" ng-click="bookDelete($index, book)">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Delete
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

<spring:url value="/resources/core/js/angular.min.js" var="angularJs"/>
<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs"/>
<spring:url value="/resources/core/js/index.js" var="indexJs"/>
<spring:url value="/resources/core/js/angular-busy.min.js" var="angularbusyJs"/>
<spring:url value="/resources/core/js/ng-infinite-scroll.min.js" var="infinitescrollJs"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${angularJs}"></script>
<script src="${angularbusyJs}"></script>
<script src="${infinitescrollJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${indexJs}"></script>

</body>
</html>
