package dev.dini.twofaservice.mapper;

import dev.dini.twofaservice.dto.TwoFaCreateDTO;
import dev.dini.twofaservice.dto.TwoFaResponseDTO;
import dev.dini.twofaservice.entity.TwoFaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TwoFaMapper {

    TwoFaResponseDTO toResponseDTO(TwoFaRequest twoFARequest);

    TwoFaRequest toEntity(TwoFaCreateDTO createRequestDTO);
}