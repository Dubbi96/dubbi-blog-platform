package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.application.dto.CreatePostDto;
import com.dubbi.blogplatform.application.dto.GetAllPostDto;
import com.dubbi.blogplatform.application.dto.GetPostDto;
import com.dubbi.blogplatform.application.dto.UpdatePostDetailDto;
import com.dubbi.blogplatform.application.service.JwtService;
import com.dubbi.blogplatform.domain.entity.*;
import com.dubbi.blogplatform.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest/*
@WithMockUser
@WithUserDetails*/
@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostQueryRepository postQueryRepository;
    @Mock
    private PostCategoryRepository postCategoryRepository;
    @Mock
    private PostImageQueryRepository postImageQueryRepository;
    @Mock
    private PostImageRepository postImageRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PostServiceImpl postService;

    @Value("${imageserver.url}")
    private static String IMAGE_SERVER_BASE_URL;

    private CreatePostDto prepareCreatePostDtoWithImage() {
        new CreatePostDto();
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("Test Post")
                .content("Test Content")
                .postCategoryId(1L).build();
        // Mocking a file upload scenario
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "testImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "<<png data>>".getBytes(StandardCharsets.UTF_8));

        return CreatePostDto.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .postImage(Collections.singletonList(imageFile).toArray(new org.springframework.web.multipart.MultipartFile[0]))
                .postCategoryId(createPostDto.getPostCategoryId())
                .build();
    }
    private User mockUserToken() {
        new User();
        return User.builder()
                .age(99L)
                .city("Seoul")
                .email("user@example.com")
                .nickname("Test Nickname")
                .password("test pass")
                .profilePicture(Image.builder()
                        .url("http://www.testurl.com")
                        .fileName("test file name").build())
                .refreshToken("validRefresh")
                .build();
    }
    private PostCategory mockPostCategory() {
        new PostCategory();
        return PostCategory.builder()
                                    .id(1L)
                                    .categoryName("TestCategory")
                                    .build();
    }
    @BeforeEach
    void setUp(){
        User testUser = mockUserToken();
        String validToken = "validTokenTest";
        when(jwtService.extractEmail(validToken)).thenReturn(Optional.of(testUser.getEmail()));
    }

    @Test
    void createPost_Success(){
        // 준비
        String accessToken = "validToken";
        new CreatePostDto();
        CreatePostDto createPostDto = CreatePostDto.builder()
                .title("Test Post")
                .content("Test Content")
                .postCategoryId(1L)
                .postImage(List.of(new MockMultipartFile("image", "test.png", "image/png", "test image content".getBytes())).toArray(new org.springframework.web.multipart.MultipartFile[0])).build();
        User user = new User(); // 필요한 사용자 객체 초기화
        PostCategory postCategory = new PostCategory(); // 필요한 카테고리 객체 초기화
        Post post = new Post(); // 필요한 포스트 객체 초기화
        Image image = new Image(); // 필요한 이미지 객체 초기화

        when(jwtService.extractEmail(accessToken)).thenReturn(Optional.of("test@example.com"));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(postCategoryRepository.findById(1L)).thenReturn(Optional.of(postCategory));
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // 실행
        postService.createPost(accessToken, createPostDto);

        // 검증
        verify(postRepository, times(1)).save(any(Post.class));
        verify(postImageRepository, times(1)).save(any(PostImage.class));
    }
    @Test
    void getAllPost_Success() {
        // Given
        String accessToken = "validAccessToken";
        User user = new User(); // Mock User initialization
        List<Post> posts = Arrays.asList(new Post(), new Post()); // Mock List of Posts
        when(jwtService.extractEmail(accessToken)).thenReturn(Optional.of("user@example.com"));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(postQueryRepository.findAllPost(user)).thenReturn(posts);

        // When
        List<GetAllPostDto> result = postService.getAllPost(accessToken);

        // Then
        assertThat(result.size()).isEqualTo(posts.size());
        verify(postQueryRepository, times(1)).findAllPost(user);
    }
    @Test
    void updatePostDetail_Success() {
        // Given
        String accessToken = "validAccessToken";
        UpdatePostDetailDto dto = UpdatePostDetailDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build(); // 실제 변경될 내용을 포함
        Long postId = 1L;
        Post post = new Post();
        post.setTitle("Original Title");
        post.setContent("Original Content");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(jwtService.extractEmail(accessToken)).thenReturn(Optional.of("user@example.com"));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(new User()));

        // When
        postService.updatePostDetail(accessToken, dto, postId);

        // Then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post updatedPost = postArgumentCaptor.getValue();

        assertEquals("Updated Title", updatedPost.getTitle());
        assertEquals("Updated Content", updatedPost.getContent());
    }

    @Test
    void deactivatePost_Success() {
        // Given
        String accessToken = "validAccessToken";
        Long postId = 1L;

        // When
        Long result = postService.deactivatePost(accessToken, postId);

        // Then
        assertEquals(postId, result);
        verify(postRepository, times(1)).deactivatePostById(postId);
    }

    @Test
    void createPost_IOExceptionWhenUploadingImage() {
        // Given
        String accessToken = "validToken";
        CreatePostDto createPostDto = prepareCreatePostDtoWithImage();
        when(jwtService.extractEmail(accessToken)).thenReturn(Optional.of("user@example.com"));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUserToken()));
        when(postCategoryRepository.findById(anyLong())).thenReturn(Optional.of(mockPostCategory()));
        doThrow(RestClientException.class).when(restTemplate).postForEntity(anyString(), any(), any());

        // When & Then
        assertThrows(RuntimeException.class, () -> postService.createPost(accessToken, createPostDto));
        verify(postRepository, never()).save(any(Post.class));
        verify(postImageRepository, never()).save(any(PostImage.class));
    }

    @Test
    void getPost_NonExistentPost() {
        // Given
        String accessToken = "validAccessToken";
        Long nonExistentPostId = 999L;
        when(jwtService.extractEmail(accessToken)).thenReturn(Optional.of("user@example.com"));
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(new User()));
        when(postQueryRepository.findPost(any(User.class),eq(nonExistentPostId))).thenReturn(Optional.empty()); // 수정된 부분

        // When
        GetPostDto result = postService.getPost(accessToken, nonExistentPostId);

        // Then
        assertNull(result);
    }
}
