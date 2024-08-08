package vn.cmcglobal.trial.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import vn.cmcglobal.trial.entity.post.Post;
import vn.cmcglobal.trial.payload.dto.PostDto;
import vn.cmcglobal.trial.payload.request.post.PostRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IPostMapper {
    PostDto toPostDto(Post post);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "thumbnail", target = "thumbnail")
    @Mapping(target = "id", ignore = true)
    void updatePost(@MappingTarget Post post, PostRequest request, String thumbnail);
}
