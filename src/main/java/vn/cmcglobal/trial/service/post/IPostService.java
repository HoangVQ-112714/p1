package vn.cmcglobal.trial.service.post;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcglobal.trial.entity.post.Post;
import vn.cmcglobal.trial.payload.dto.PostDto;
import vn.cmcglobal.trial.payload.request.post.PostRequest;

public interface IPostService {
    public Page<Post> findPaginated(int pageNo, int pageSize);

    public void create(PostRequest PostRequest, MultipartFile thumbnail);

    public PostDto findById(Long id);

    public void update(long id, PostRequest PostRequest, MultipartFile file);

    public void delete(long id);
}
