package com.travelsmart.blog_service.service.impl;

import com.travelsmart.blog_service.dto.request.BlogLikeRequest;
import com.travelsmart.blog_service.dto.request.BlogRequest;
import com.travelsmart.blog_service.dto.request.BlogUpdateRequest;
import com.travelsmart.blog_service.dto.response.BlogResponse;
import com.travelsmart.blog_service.dto.response.PageableResponse;
import com.travelsmart.blog_service.dto.response.Paging;
import com.travelsmart.blog_service.dto.response.ProfileUserResponse;
import com.travelsmart.blog_service.entity.BlogEntity;
import com.travelsmart.blog_service.entity.BlogFavoriteEntity;
import com.travelsmart.blog_service.entity.BlogFavoriteId;
import com.travelsmart.blog_service.entity.CategoryEntity;
import com.travelsmart.blog_service.mapper.BlogMapper;
import com.travelsmart.blog_service.repository.BlogFavoriteRepository;
import com.travelsmart.blog_service.repository.BlogRepository;
import com.travelsmart.blog_service.repository.CategoryRepository;
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
    @Override
    public BlogResponse create(BlogRequest blogRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
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
        ProfileUserResponse profileUserResponse = profileClient.getUserById(userId).getResult();
        blog.setUserId(userId);
        blog.setCreateDate(new Date());
        blog.setUserName(profileUserResponse.getUserName());
        return mappingOne(blogRepository.save(blog));
    }

    @Override
    public PageableResponse<List<BlogResponse>> findAll(Pageable pageable) {
        Page<BlogEntity> page = blogRepository.findAll(pageable);
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
        Page<BlogEntity> page = blogRepository.findByCategoryName(pageable,category.getName());
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

        blog.setTitle(blogUpdateRequest.getTitle());
        blog.setTags(blogUpdateRequest.getTags());
        blog.setContent(blogUpdateRequest.getContent());
        blog.setCode(HandleString.strToCode(blogUpdateRequest.getTitle()));
        return mappingOne(blogRepository.save(blog));
    }

    @Override
    public void deleteById(Long blogId) {

    }

    @Override
    public BlogResponse likePost(BlogLikeRequest blogLikeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        BlogEntity blog = blogRepository.findById(blogLikeRequest.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));

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
                .orElseThrow(() -> new RuntimeException("Blog not found"));
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
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        return mappingOne(blogEntity);
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
