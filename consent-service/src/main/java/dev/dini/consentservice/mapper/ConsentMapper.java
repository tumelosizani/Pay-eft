package dev.dini.consentservice.mapper;

import dev.dini.consentservice.dto.ConsentRequestDTO;
import dev.dini.consentservice.dto.ConsentResponseDTO;
import dev.dini.consentservice.entity.Consent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    Consent toEntity(ConsentRequestDTO consentRequestDTO);

    ConsentResponseDTO toResponseDTO(Consent consent);
}
