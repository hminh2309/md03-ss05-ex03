package com.example.session05.models.dtos;

import com.example.session05.models.entities.CourseStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private CourseStatus status;
}