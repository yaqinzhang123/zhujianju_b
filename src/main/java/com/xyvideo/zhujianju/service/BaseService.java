package com.xyvideo.zhujianju.service;

import com.xyvideo.zhujianju.model.ModelBase;
import com.xyvideo.zhujianju.util.PageChunk;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Qualifier
public abstract class BaseService<T extends ModelBase> {

    protected abstract JpaRepository<T,Long> getRepository();

    //更新
    public T update(T t){
        T origin = this.getRepository().findById(t.getId()).get();
        t.setUpdateTime(new Date());
        t.setCreateTime(origin.getCreateTime());
        t.setCreator(origin.getCreator());
        return this.getRepository().saveAndFlush(t);
    }
    //删除
    public void deleteById(Long id){
        T origin = this.getRepository().findById(id).get();
        origin.setUpdateTime(new Date());
        origin.setDeleted(true);
        this.getRepository().saveAndFlush(origin);
    }
    //查看所有
    public List<T> getList(){
        return this.getRepository().findAll().stream().filter(p->!p.isDeleted()).collect(Collectors.toList());
    }
    public Page<T> getList(int pageNo, int pageSize){
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(pageNo-1,pageSize,sort);
        return this.getRepository().findAll(pageable);
    }
    // 查看某一个
    public T getByID(Long id){
        return this.getRepository().findById(id).get().isDeleted()?null:this.getRepository().findById(id).get();
    }
    //新增
    public T add(T t){
        return this.getRepository().save(t);
    }
    //判断是否存在
    public boolean existsById(Long id){
        return this.getRepository().existsById(id);
    }
    private PageChunk<T> pageChunk(Page<T> page) {
        PageChunk<T> chunk = new PageChunk();
        chunk.setContent(page.getContent());
        chunk.setTotalPages(page.getTotalPages());
        chunk.setTotalElements(page.getTotalElements());
        chunk.setPageNumber(page.getPageable().getPageNumber() + 1);
        chunk.setNumberOfElements(page.getNumberOfElements());
        return chunk;
    }

}
