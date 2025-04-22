package dev.dini.consentservice.mapper;

import dev.dini.consentservice.dto.ConsentRequestDTO;
import dev.dini.consentservice.dto.ConsentResponseDTO;
import dev.dini.consentservice.entity.Consent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consentedAt", ignore = true)
    Consent toEntity(ConsentRequestDTO consentRequestDTO);

    ConsentResponseDTO toResponseDTO(Consent consent);
}
