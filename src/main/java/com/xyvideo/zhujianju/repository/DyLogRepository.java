package com.xyvideo.zhujianju.repository;

import com.xyvideo.zhujianju.model.DyLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DyLogRepository extends JpaRepository<DyLog,Long> {
    Page<DyLog> findAllByDeletedFalse(Pageable pageable);
    List<DyLog> findAllByResourceType(String resourceType);
}
