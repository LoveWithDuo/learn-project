package com.dw.demo.service.impl;


import com.dw.demo.elasticsearch.entity.EsUser;
import com.dw.demo.elasticsearch.repository.EsUserRepository;
import com.dw.demo.entity.User;
import com.dw.demo.service.EsUserService;
import com.dw.demo.service.UserService;
import com.dw.demo.util.common.PageModel;
import com.dw.demo.util.common.R;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhanzhihong
 * @date 2021-05-27 15:28
 */
@Service
public class EsUserServiceImpl implements EsUserService {
    @Autowired
    private EsUserRepository esUserRepository;
    @Autowired
    UserService userService;
    @Override
    public R userSaveAll() {
        //查询数据
        List<User> users = userService.selectByExample(Example.builder(User.class).andWhere(Sqls.custom()).build());
        List<EsUser> esUsers = new ArrayList<>();
        if (users!=null && users.size()>0){
            for (User user : users){
                EsUser esUser = new EsUser();
                BeanUtils.copyProperties(user,esUser);
                esUsers.add(esUser);
            }
        }
        //插入所有
        Iterable<EsUser> esUsersList = esUserRepository.saveAll(esUsers);
        int result = 0;
        //成功数量
        Iterator<EsUser> iterator = esUsersList.iterator();
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return R.success(result);
    }

    @Override
    public R findUserList(String name, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<EsUser> userPage = esUserRepository.findByUserName(name,pageable);
        List<EsUser> content = userPage.getContent();
        int totalPages = userPage.getTotalPages();
        PageModel pageModel = new PageModel();
        pageModel.setPage(pageNum);
        pageModel.setSize(pageSize);
        pageModel.setPageList(content);
        pageModel.setTotal(Long.valueOf(totalPages));
        return R.success(pageModel);
    }

    @Override
    public R findUserList(String name,String phone, Integer pageNum, Integer pageSize) {
        //分页信息
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        //创建查询条件生成器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //匹配查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //过滤
        if (StringUtils.isNotEmpty(name)){
            //根据名称查询
            MatchQueryBuilder userName = QueryBuilders.matchQuery("userName", name);
            boolQueryBuilder.must(userName);

        }
        if (StringUtils.isNotEmpty(phone)){
            //根据手机号查询
            MatchQueryBuilder userName = QueryBuilders.matchQuery("phone", phone);
            boolQueryBuilder.must(userName);
        }
        //过滤是否删除的用户
        boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("archive", false));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        //排序
        nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("createdAt").order(SortOrder.DESC));
        //es查询
        Page<EsUser> page = esUserRepository.search(nativeSearchQueryBuilder.build());
        PageModel pageModel = new PageModel();
        pageModel.setPage(pageNum);
        pageModel.setSize(pageSize);
        pageModel.setPageList(page.getContent());
        pageModel.setTotal(Long.valueOf(page.getTotalElements()));
        return R.success(pageModel);
    }
}
