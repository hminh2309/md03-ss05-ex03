package com.example.session05.repositories;

import com.example.session05.models.entities.Course;
import com.example.session05.models.entities.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Viết JPQL lấy danh sách khóa học theo trạng thái
    @Query("SELECT c FROM Course c WHERE c.status = :status")
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);
}