package com.example.session05.controllers;

import com.example.session05.models.dtos.ApiResponse;
import com.example.session05.models.dtos.CourseResponse;
import com.example.session05.models.dtos.PageResponse;
import com.example.session05.models.entities.CourseStatus;
import com.example.session05.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "ACTIVE") CourseStatus status) { // Thêm tham số lọc status, mặc định là ACTIVE

        // Gọi hàm getPagedCoursesByStatus từ Service
        PageResponse<CourseResponse> pagedCourses = courseService.getPagedCoursesByStatus(page, size, sortBy, direction, status);

        // Đóng gói vào ApiResponse
        ApiResponse<PageResponse<CourseResponse>> response = ApiResponse.<PageResponse<CourseResponse>>builder()
                .success(true)
                .message("OK")
                .data(pagedCourses)
                .build();

        return ResponseEntity.ok(response);
    }
}