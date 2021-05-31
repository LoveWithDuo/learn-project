package com.dw.demo.util.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用实现类
 *
 * @author qijiahai on 2019-05-08.
 * @version 1.0
 */
public abstract class BaseSerVice<T> implements IService<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public T selectOne(Example example) {
        return mapper.selectOneByExample(example);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int saveNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }


    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Example example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public Page<T> selectByExampleAndRowBounds(Example example, PageRowBounds pageRowBounds) {
        return (Page<T>) mapper.selectByExampleAndRowBounds(example, pageRowBounds);
    }

}
