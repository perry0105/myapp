(function() {
    'use strict';

    angular
        .module('myappApp')
        .controller('SkuDialogController', SkuDialogController);

    SkuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sku', 'UserSku'];

    function SkuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sku, UserSku) {
        var vm = this;

        vm.sku = entity;
        vm.clear = clear;
        vm.save = save;
        vm.userskus = UserSku.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sku.id !== null) {
                Sku.update(vm.sku, onSaveSuccess, onSaveError);
            } else {
                Sku.save(vm.sku, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myappApp:skuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
