package com.xyvideo.zhujianju.service;


import com.xyvideo.zhujianju.model.DyLog;
import com.xyvideo.zhujianju.repository.DyLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DyLogService extends BaseService<DyLog> {
    @Autowired
    private DyLogRepository repository;
    public JpaRepository<DyLog,Long> getRepository(){
        return this.repository;
    }
    public Page<DyLog> findAllByDeletedFalse(int pageNo, int pageSize){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(pageNo-1,pageSize,sort);
        return this.repository.findAllByDeletedFalse(pageable);
    }
    public  List<DyLog> findAllByResourceType(String resourceType){
        return this.repository.findAllByResourceType(resourceType);
    }

}
