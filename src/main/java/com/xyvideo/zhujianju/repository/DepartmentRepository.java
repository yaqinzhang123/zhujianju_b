package com.xyvideo.zhujianju.repository;

import com.xyvideo.zhujianju.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findByDeletedFalseAndName(String name);
    Page<Department> findAllByDeletedFalse(Pageable pageable);
    List<Department> findByDeletedFalseAndNameAndMainDepartment(String name,String mainDepartment);
    List<Department> findAllByDeletedFalseAndName(String name);
    List<Department> findAllByDeletedFalseAndMainDepartment(String name);
}
