(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('UserSkuDetailController', UserSkuDetailController);

    UserSkuDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserSku', 'User', 'Sku'];

    function UserSkuDetailController($scope, $rootScope, $stateParams, previousState, entity, UserSku, User, Sku) {
        var vm = this;

        vm.userSku = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myappApp:userSkuUpdate', function(event, result) {
            vm.userSku = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
