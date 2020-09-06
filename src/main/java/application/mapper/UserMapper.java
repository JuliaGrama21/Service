package application.mapper;

import application.dto.RegistrationUserDto;
import application.model.security.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<RegistrationUserDto, User> {

    @Override
    public RegistrationUserDto toDto(User user) {
        return MODEL_MAPPER.map(user, RegistrationUserDto.class);
    }

    @Override
    public User fromDto(RegistrationUserDto registrationUserDto) {
        return MODEL_MAPPER.map(registrationUserDto, User.class);
    }

}