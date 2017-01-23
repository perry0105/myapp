(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('SkuDetailController', SkuDetailController);

    SkuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sku', 'UserSku'];

    function SkuDetailController($scope, $rootScope, $stateParams, previousState, entity, Sku, UserSku) {
        var vm = this;

        vm.sku = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:skuUpdate', function(event, result) {
            vm.sku = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
