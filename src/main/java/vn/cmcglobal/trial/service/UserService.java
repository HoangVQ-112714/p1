package vn.cmcglobal.trial.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import response.UserResponse;
import vn.cmcglobal.trial.dto.user.CreateRequest;
import vn.cmcglobal.trial.dto.user.UpdateRequest;
import vn.cmcglobal.trial.entity.user.User;
import vn.cmcglobal.trial.mapper.UserMapper;
import vn.cmcglobal.trial.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UploadImageService uploadImageService;
    UserRepository userRepository;

    public void createUser(CreateRequest request, MultipartFile file) {
        User user = userMapper.createUser(request, file.getOriginalFilename());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // level, default
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (!file.isEmpty()) {
            uploadImageService.save(file, "user");
        }

        userRepository.save(user);
    }

    public UserResponse userDetail(String id) {
        return userMapper.getUser(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."))
        );
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUser(UpdateRequest request, MultipartFile file, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        String password = user.getPassword();
        String avatar = user.getAvatar();

        if (!request.getPassword().isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); // level, default
            password = passwordEncoder.encode(request.getPassword());
        }

        if (!file.isEmpty()) {
            uploadImageService.save(file, "user");
            avatar = file.getOriginalFilename();
        }

        userMapper.updateUser(user, request, password, avatar);

        userRepository.save(user);
    }

    public Page<User> pagination(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        int startItem = pageSize * pageNumber;

        List<User> posts = getUsers();
        List<User> list;

        if (posts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, posts.size());
            list = posts.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), posts.size());
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        userRepository.delete(user);
    }

    public Boolean checkUniqueColumn(String field, String id, String value) {
        return switch (field) {
            case "email" -> {
                if (id.isEmpty()) {
                    yield !userRepository.existsByEmail(value);
                }
                yield !userRepository.existsByEmailAndIdNot(value, id);
            }
            case "username" -> {
                if (id.isEmpty()) {
                    yield !userRepository.existsByUsername(value);
                }
                yield !userRepository.existsByUsernameAndIdNot(value, id);
            }
            default -> true;
        };
    }
}
