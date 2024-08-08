package vn.cmcglobal.trial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import response.UserResponse;
import vn.cmcglobal.trial.dto.user.CreateRequest;
import vn.cmcglobal.trial.dto.user.UpdateRequest;
import vn.cmcglobal.trial.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "avatar", target = "avatar")
    User createUser(CreateRequest request, String avatar);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "password", target = "password")
    void updateUser(@MappingTarget User user, UpdateRequest request, String password, String avatar);

    @Mapping(target = "password", ignore = true)
    UserResponse getUser(User user);
}
