package com.dw.demo.util.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 通用接口
 * @author qijiahai on 2019-05-08.
 * @version 1.0
 */
public interface IService<T> {
    /**
     * 根据主键查询
     * @param key
     * @return
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据条件查询一条数据
     * @param example
     * @return
     */
    T selectOne(Example example);

    /**
     * 保存所有数据
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 保存不为Null的数据，Null值字段会使用数据库默认值
     * @param entity
     * @return
     */
    int saveNotNull(T entity);

    /**
     * 根据主键更新所有字段
     * @param entity
     * @return
     */
    int updateAll(T entity);

    /**
     * 根据主键更新不为Null的字段
     * @param entity
     * @return
     */
    int updateNotNull(T entity);


    /**
     * 根据条件查询数据
     * @param example
     * @return
     */
    List<T> selectByExample(Example example);

    /**
     * 根据条件查询Count
     * @param example
     * @return
     */
    int selectCountByExample(Example example);

    /**
     * 根据条件查询分页数据
     * @param example
     * @param pageRowBounds
     * @return
     */
    Page<T> selectByExampleAndRowBounds(Example example, PageRowBounds pageRowBounds);


    
}
