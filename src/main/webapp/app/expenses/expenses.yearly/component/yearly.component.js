(function () {

    'use strict';

    angular
        .module('expensesApp')
        .component('yearlyChart', {
            templateUrl: 'app/expenses/expenses.yearly/component/yearly.component.html',
            controller: 'YearlyController',
            controllerAs: 'vm',
            bindings: {
                data: '=',
                labels: '='
            }
        });


})();
