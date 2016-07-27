(function () {

    'use strict';

    angular
        .module('expensesApp')
        .controller('YearlyController',YearlyController);

    YearlyController.$inject = ['$scope'];

    function YearlyController($scope) {
        $scope.getLabels = getLabels;
        $scope.getData = getData;
        var vm = this;

        function getLabels() {
            console.log(vm.labels.length);
            return vm.labels;
        }

        function getData() {
            console.log(vm.data);
            return vm.data;
        }

    }


})();
