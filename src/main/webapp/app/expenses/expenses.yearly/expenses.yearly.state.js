(function() {
    'use strict';

    angular
        .module('expensesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('yearlyExpenses', {
            parent: 'expenses',
            url: '/yearly',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/expenses/expenses.yearly/expenses.yearly.html',
                    controller: 'ExpensesYearlyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
