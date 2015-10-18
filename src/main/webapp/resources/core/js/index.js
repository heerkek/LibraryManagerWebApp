/**
 * Created by Emre on 16.10.2015.
 */
(function () {

    var app = angular.module('libraryApp', ['cgBusy', 'infinite-scroll']);

    app.controller('LibraryController', ["$http", "$scope", "$window", function ($http, $scope, $window) {

        $scope.showModal = false;
        $scope.bookEditModal = function (book, modalAction) {
            $scope.showModal = !$scope.showModal;
            $scope.book = book;
            $scope.modalAction = modalAction;
            library.message = "";
        };

        $scope.page = 1;

        var library = this;

        library.books = [];
        library.message = "";
        $scope.resultPromise = null;
        $scope.resultPromise = $scope.myPromise = $http.get('/1').success(function (data) {
            library.books = data;
            library.message = "listed books";
        });

        $scope.book = {};
        $scope.actionBook = function () {

            var recaptcha = false;
            $scope.myPromise = null;
            if ($scope.modalAction == 'add') {

                var grecaptchaResponse = grecaptcha.getResponse();
                $scope.myPromise = $http.post('/book/?grecaptchaResponse=' + grecaptchaResponse, $scope.book).success(function (data) {
                    if (data.success) {
                        library.message = "added '" + $scope.book.name + "' book";
                        library.books.push($scope.book);
                        $scope.showModal = false;
                    } else {
                        library.message = "not valid captche";

                    }
                }).error(function (data) {
                    library.message = data.message;
                }).finally(function (data) {
                    $scope.book = {};
                    grecaptcha.reset();
                });

            } else if ($scope.modalAction == 'update') {
                $scope.myPromise = $http.put('/book/', $scope.book).success(function (data) {
                    library.message = "updated the book";
                    library.books.push($scope.book);
                    $scope.showModal = false;
                }).error(function (data) {
                    library.message = data.message;
                }).finally(function (data) {
                    $scope.book = {};
                });;
            }
        }
        $scope.bookDelete = function (index, book) {
            var $deleteConfirm = $window.confirm("Are you sure you want to delete '" + book.name + "' ?");
            $scope.resultPromise = null;
            if ($deleteConfirm) {
                $scope.resultPromise = $http.delete('/book/' + book.id).success(function (data) {
                    library.message = "deleted '" + book.name + "' book";
                    library.books.splice(index, 1);
                }).error(function (data) {
                    library.message = data;
                });
            }
        }

        $scope.loadMoreBook = function () {
            $scope.resultPromise = $http.get('/' + ($scope.page + 1)).success(function (data) {
                if (data.length > 0) {
                    angular.forEach(data, function (book) {
                        library.books.push(book);
                    });

                    $scope.page = $scope.page + 1;
                    library.message = "loaded page " + $scope.page;
                }
            });
        };


    }]);


    app.directive('modal', function () {
        return {
            template: '<div class="modal fade" data-keyboard="false">' +
                '<div class="modal-dialog">' +
                '<div class="modal-content"  style="height: 300px;">' +
                '<div class="modal-header">' +
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '<h4 class="modal-title">{{ title }}</h4>' +
                '</div>' +
                '<div class="modal-body" ng-transclude></div>' +
                '</div>' +
                '</div>' +

                '</div>',
            restrict: 'E',
            transclude: true,
            replace: true,
            scope: true,
            link: function postLink(scope, element, attrs) {
                scope.title = attrs.title;

                scope.$watch(attrs.visible, function (value) {

                    if (value == true)
                        $(element).modal('show');
                    else
                        $(element).modal('hide');
                });

                $(element).on('shown.bs.modal', function () {
                    scope.$apply(function () {
                        scope.$parent[attrs.visible] = true;
                    });
                });

                $(element).on('hidden.bs.modal', function () {
                    scope.$apply(function () {
                        scope.$parent[attrs.visible] = false;
                    });
                });
            }
        };
    });

})();
