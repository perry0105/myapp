(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('UserSkuDialogController', UserSkuDialogController);

    UserSkuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserSku', 'User', 'Sku'];

    function UserSkuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserSku, User, Sku) {
        var vm = this;

        vm.userSku = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.skus = Sku.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userSku.id !== null) {
                UserSku.update(vm.userSku, onSaveSuccess, onSaveError);
            } else {
                UserSku.save(vm.userSku, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:userSkuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
