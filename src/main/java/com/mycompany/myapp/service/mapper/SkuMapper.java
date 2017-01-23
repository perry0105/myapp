package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SkuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sku and its DTO SkuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkuMapper {

    SkuDTO skuToSkuDTO(Sku sku);

    List<SkuDTO> skusToSkuDTOs(List<Sku> skus);

    @Mapping(target = "userSkus", ignore = true)
    Sku skuDTOToSku(SkuDTO skuDTO);

    List<Sku> skuDTOsToSkus(List<SkuDTO> skuDTOs);
}
