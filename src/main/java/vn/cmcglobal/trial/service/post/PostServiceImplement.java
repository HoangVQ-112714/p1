package vn.cmcglobal.trial.service.post;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.cmcglobal.trial.entity.post.Post;
import vn.cmcglobal.trial.mapper.IPostMapper;
import vn.cmcglobal.trial.payload.dto.PostDto;
import vn.cmcglobal.trial.payload.request.post.PostRequest;
import vn.cmcglobal.trial.repository.post.IPostRepository;
import vn.cmcglobal.trial.service.UploadImageService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImplement implements IPostService {
    IPostRepository postRepository;
    IPostMapper postMapper;
    UploadImageService uploadImageService;

    public Post findOne(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * @return Page<Post>
     */
    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return postRepository.findAll(pageable);
    }

    /**
     *
     */
    @Override
    @Transactional
    public void create(PostRequest postRequest, MultipartFile thumbnail) {
        Post post = new Post();
        postMapper.updatePost(post, postRequest, thumbnail.getOriginalFilename());
        postRepository.save(post);

        uploadImageService.save(thumbnail, "posts/" + post.getId());
    }

    @Override
    public PostDto findById(Long id) {
        Post post = findOne(id);
        return postMapper.toPostDto(post);
    }

    @Override
    public void update(long id, PostRequest PostRequest, MultipartFile file) {
        Post post = this.findOne(id);
        String thumbnail = post.getThumbnail();

        if (!file.isEmpty()) {
            thumbnail = file.getOriginalFilename();
        }

        postMapper.updatePost(post, PostRequest, thumbnail);
        uploadImageService.save(file, "posts/" + post.getId());

        postRepository.save(post);
    }

    /**
     * @param id
     */
    @Override
    public void delete(long id) {
        Post post = this.findOne(id);
        postRepository.delete(post);
    }
}
