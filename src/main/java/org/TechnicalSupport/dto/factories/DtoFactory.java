package org.TechnicalSupport.dto.factories;

public interface DtoFactory<ENTITY, DTO> {

    DTO toDto(ENTITY entity);

}
