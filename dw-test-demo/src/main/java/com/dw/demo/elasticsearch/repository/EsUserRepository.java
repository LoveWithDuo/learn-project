package com.dw.demo.elasticsearch.repository;

import com.dw.demo.elasticsearch.entity.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zhanzhihong
 * @date 2021-05-27 13:27
 */
public interface EsUserRepository extends ElasticsearchRepository<EsUser,Long> {

    Page<EsUser> findByUserName(String userName, Pageable pageable);
}
