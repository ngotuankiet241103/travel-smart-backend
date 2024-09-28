package com.travelsmart.blog_service.service.impl;

import com.travelsmart.blog_service.constant.BlogStatus;
import com.travelsmart.blog_service.dto.request.*;
import com.travelsmart.blog_service.dto.response.*;
import com.travelsmart.blog_service.dto.response.httpclient.MediaHttpResponse;
import com.travelsmart.blog_service.entity.*;
import com.travelsmart.blog_service.exception.CustomRuntimeException;
import com.travelsmart.blog_service.exception.ErrorCode;
import com.travelsmart.blog_service.mapper.BlogMapper;
import com.travelsmart.blog_service.repository.BlogFavoriteRepository;
import com.travelsmart.blog_service.repository.BlogImageRepository;
import com.travelsmart.blog_service.repository.BlogRepository;
import com.travelsmart.blog_service.repository.CategoryRepository;
import com.travelsmart.blog_service.repository.httpclient.MediaClient;
import com.travelsmart.blog_service.repository.httpclient.ProfileClient;
import com.travelsmart.blog_service.service.BlogService;
import com.travelsmart.blog_service.utils.HandleString;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final BlogMapper blogMapper;
    private final BlogFavoriteRepository blogFavoriteRepository;
    private final ProfileClient profileClient;
    private final MediaClient mediaClient;
    private final BlogImageRepository blogImageRepository;
    @Override
    public BlogResponse create(BlogRequest blogRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.toString().equals("ROLE_ADMIN"));
        BlogEntity blog = blogMapper.toBlogEntity(blogRequest);
        blog.setCode(HandleString.strToCode(blog.getTitle()));

        Set<CategoryEntity> categories = blogRequest.getCategories()
                .stream()
                .map(categoryName -> {
                    CategoryEntity category = categoryRepository.findByName(categoryName)
                            .orElse(null);
                    if(category == null){
                        category = CategoryEntity.builder()
                                .name(categoryName)
                                .code(HandleString.strToCode(categoryName))
                                .build();

                        categoryRepository.save(category);
                    }
                    return category;
                })
                .collect(Collectors.toSet());
        blog.setCategories(categories);
        if(blogRequest.getImageId() != null){

            blog.setImage(blogImageRepository.findById(blogRequest.getImageId())
                    .orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND)));
        }
        ProfileUserResponse profileUserResponse = profileClient.getUserById(userId).getResult();
        blog.setUserId(userId);
        blog.setCreateDate(new Date());
        blog.setUserName(profileUserResponse.getUserName());
        blog.setStatus(isAdmin ? BlogStatus.ACCEPT : BlogStatus.PENDING);
        return mappingOne(blogRepository.save(blog));
    }

    @Override
    public PageableResponse<List<BlogResponse>> findAll(Pageable pageable) {
        Page<BlogEntity> page = blogRepository.findByStatus(pageable, BlogStatus.ACCEPT);
        Paging paging = Paging.buildPaging(pageable,page.getTotalPages());
        PageableResponse<List<BlogResponse>> response = PageableResponse.<List<BlogResponse>>builder()
                .data(mappingList(page.getContent()))
                .paging(paging)
                .build();
        return response;
    }

    @Override
    public PageableResponse<List<BlogResponse>> findByCategoryCode(String categoryCode,
                                                                   Pageable pageable) {
        CategoryEntity category = categoryRepository.findByCode(categoryCode).orElse(null);
        if(category == null){
            return null;
        }
        System.out.println(category.getName());
        Page<BlogEntity> page = blogRepository.findByCategoryNameAndStatus(pageable,category.getName(),BlogStatus.ACCEPT);
        Paging paging = Paging.buildPaging(pageable,page.getTotalPages());
        return PageableResponse.<List<BlogResponse>>builder()
                .data(mappingList(page.getContent()))
                .paging(paging)
                .build();
    }

    @Override
    public BlogResponse update(Long blogId, BlogUpdateRequest blogUpdateRequest) {
        BlogEntity blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        BlogImageEntity image = null;
        if(blog.getImage() != null){
            image = blog.getImage();
        }
        blog.setImage(blogUpdateRequest.getImageId() != null ?
                blogImageRepository.findById(blogUpdateRequest.getImageId()).orElseThrow(() -> new CustomRuntimeException(ErrorCode.IMAGE_NOT_FOUND)) :
                null
        );
        blog.setTitle(blogUpdateRequest.getTitle());
        blog.setTags(blogUpdateRequest.getTags());
        blog.setContent(blogUpdateRequest.getContent());
        blog.setCode(HandleString.strToCode(blogUpdateRequest.getTitle()));
        blogRepository.save(blog);

        if(image != null &&  !image.getId().equals(blogUpdateRequest.getImageId())){
            blogImageRepository.deleteById(image.getId());
        }

        return mappingOne(blog);
    }

    @Override
    public void deleteById(Long blogId) {

    }

    @Override
    public BlogResponse likePost(BlogLikeRequest blogLikeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        BlogEntity blog = blogRepository.findById(blogLikeRequest.getBlogId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.BLOG_NOT_FOUND));

        BlogFavoriteEntity blogFavoriteEntity = BlogFavoriteEntity.builder()
                .id(BlogFavoriteId.builder()
                        .blogId(blog.getId())
                        .userId(userId)
                        .build())
                .build();
        blogFavoriteRepository.save(blogFavoriteEntity);
        return mappingOne(blog);
    }

    @Override
    public BlogResponse unlikePost(BlogLikeRequest blogLikeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        BlogEntity blog = blogRepository.findById(blogLikeRequest.getBlogId())
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.BLOG_NOT_FOUND));
        BlogFavoriteEntity blogFavoriteEntity = blogFavoriteRepository.findById(BlogFavoriteId.builder()
                        .userId(userId)
                        .blogId(blog.getId()).build())
                .orElseThrow(() -> new RuntimeException("User favorite blog not found"));
        blogFavoriteRepository.delete(blogFavoriteEntity);
        return mappingOne(blog);
    }

    @Override
    public BlogResponse findByCode(String blogCode) {
        BlogEntity blogEntity = blogRepository.findByCode(blogCode)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.BLOG_NOT_FOUND));

        return mappingOne(blogEntity);
    }

    @Override
    public BlogResponse updateStatus(Long id, BlogStatusRequest blogStatusRequest) {
        BlogEntity blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.BLOG_NOT_FOUND));
        blog.setStatus(blogStatusRequest.getStatus());
        return mappingOne(blogRepository.save(blog));
    }

    @Override
    public BlogImageResponse uploadImage(BlogImageRequest blogImageRequest) {
        MediaHttpResponse mediaHttpResponse = mediaClient.uploadFile(blogImageRequest.getFile()).getResult();
        BlogImageEntity blogImageEntity = BlogImageEntity.builder()
                .url(mediaHttpResponse.getUrl())
                .id(mediaHttpResponse.getId())
                .build();
        blogImageRepository.save(blogImageEntity);
        return BlogImageResponse.builder()
                .id(blogImageEntity.getId())
                .url(blogImageEntity.getUrl())
                .build();
    }

    @Override
    public void deleteImage(Long id) {
        mediaClient.deleteById(id);
        blogImageRepository.deleteById(id);
    }

    private List<BlogResponse> mappingList(List<BlogEntity> e){
        return e.stream()
                .map(this::mappingOne)
                .toList();
    }
    private BlogResponse mappingOne(BlogEntity blogEntity){
        BlogResponse blog = blogMapper.toBLogResponse(blogEntity);
        blog.setTotalLike(blogFavoriteRepository.countByIdBlogId(blog.getId()));
        blog.setCategories(blogEntity.getCategories().stream().map(CategoryEntity::getName).toList());
        return blog;
    }
}
