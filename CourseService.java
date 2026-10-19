package com.example.session05.services;

import com.example.session05.models.dtos.CourseResponse;
import com.example.session05.models.dtos.PageResponse;
import com.example.session05.models.entities.Course;
import com.example.session05.models.entities.CourseStatus;
import com.example.session05.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Phương thức cũ: Lấy tất cả danh sách phân trang (không điều kiện)
    public PageResponse<CourseResponse> getPagedCourses(int page, int size, String sortBy, Sort.Direction direction) {
        if (page < 0) page = 0;

        Sort sort = (sortBy == null || sortBy.trim().isEmpty())
                ? Sort.by(direction, "id")
                : Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Course> coursePage = courseRepository.findAll(pageable);

        List<CourseResponse> items = coursePage.getContent().stream()
                .map(this::convertToCourseResponse)
                .collect(Collectors.toList());

        return PageResponse.<CourseResponse>builder()
                .items(items)
                .page(coursePage.getNumber())
                .size(coursePage.getSize())
                .totalItems((int) coursePage.getTotalElements())
                .totalPages(coursePage.getTotalPages())
                .isLast(coursePage.isLast())
                .build();
    }

    // Phương thức mới: Lấy danh sách phân trang có kèm theo điều kiện lọc Status (JPQL)
    public PageResponse<CourseResponse> getPagedCoursesByStatus(int page, int size, String sortBy, Sort.Direction direction, CourseStatus status) {
        if (page < 0) page = 0;

        Sort sort = (sortBy == null || sortBy.trim().isEmpty())
                ? Sort.by(direction, "id")
                : Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        // Gọi hàm findAllByStatus từ Repository mà bạn đã định nghĩa bằng @Query
        Page<Course> coursePage = courseRepository.findAllByStatus(status, pageable);

        List<CourseResponse> items = coursePage.getContent().stream()
                .map(this::convertToCourseResponse)
                .collect(Collectors.toList());

        return PageResponse.<CourseResponse>builder()
                .items(items)
                .page(coursePage.getNumber())
                .size(coursePage.getSize())
                .totalItems((int) coursePage.getTotalElements())
                .totalPages(coursePage.getTotalPages())
                .isLast(coursePage.isLast())
                .build();
    }

    // Hàm chuyển đổi Entity sang DTO: Đã bổ sung thêm thuộc tính .status()
    private CourseResponse convertToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .status(course.getStatus()) // Map thêm trường trạng thái
                .build();
    }
}