package com.xyvideo.zhujianju.service;

import com.xyvideo.zhujianju.model.Department;
import com.xyvideo.zhujianju.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService<Department>{
    @Autowired
    private DepartmentRepository repository;
    @Override
    public DepartmentRepository getRepository() {
        return this.repository;
    }
    public Page<Department> findAllByDeletedFalse(int pageNo, int pageSize){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(pageNo-1,pageSize,sort);
        return this.repository.findAllByDeletedFalse(pageable);
    }
    public Department findByName(String name){
        return this.repository.findByDeletedFalseAndName(name);
    }

    public List<Department> findByNameAndMainDepartment(String name,String mainDepartment){
        if(name.isEmpty()&&mainDepartment.isEmpty()){
            return this.getList(1,10).getContent();
        }else if(name.isEmpty()&&!mainDepartment.isEmpty()){
            return this.repository.findAllByDeletedFalseAndMainDepartment(mainDepartment);
        }else if(!name.isEmpty()&&mainDepartment.isEmpty()){
            return this.repository.findAllByDeletedFalseAndName(name);
        }
        return this.repository.findByDeletedFalseAndNameAndMainDepartment(name,mainDepartment);
    }
}
