package application.mapper;

import org.modelmapper.ModelMapper;

public interface Mapper<DTO, ORIGIN> {

    ModelMapper MODEL_MAPPER = new ModelMapper();

    DTO toDto(ORIGIN origin);
    ORIGIN fromDto(DTO dto);

}