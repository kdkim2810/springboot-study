package com.std.boot.springboot.service.posts;

import com.std.boot.springboot.domain.posts.Posts;
import com.std.boot.springboot.domain.posts.PostsRepository;
import com.std.boot.springboot.web.dto.PostsListResponseDto;
import com.std.boot.springboot.web.dto.PostsResponseDto;
import com.std.boot.springboot.web.dto.PostsSaveRequestDto;
import com.std.boot.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 자동 생성해줌
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).
                                                    getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        /*
        JpaRepository에서 지원하고 있는 delete 메소드를 활용
        엔티티를 파라미터로 삭제하거나 deleteById메소드로 id값으로 삭제도 가능
        존재하는 게시글인지 먼저 선 조회 후 삭제
         */
        postsRepository.delete(posts);
    }
}
