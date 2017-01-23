package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UserSkuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserSku and its DTO UserSkuDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface UserSkuMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "sku.id", target = "skuId")
    UserSkuDTO userSkuToUserSkuDTO(UserSku userSku);

    List<UserSkuDTO> userSkusToUserSkuDTOs(List<UserSku> userSkus);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "skuId", target = "sku")
    UserSku userSkuDTOToUserSku(UserSkuDTO userSkuDTO);

    List<UserSku> userSkuDTOsToUserSkus(List<UserSkuDTO> userSkuDTOs);

    default Sku skuFromId(Long id) {
        if (id == null) {
            return null;
        }
        Sku sku = new Sku();
        sku.setId(id);
        return sku;
    }
}
