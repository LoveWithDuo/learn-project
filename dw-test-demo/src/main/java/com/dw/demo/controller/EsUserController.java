package com.dw.demo.controller;

import com.dw.demo.elasticsearch.entity.EsUser;
import com.dw.demo.elasticsearch.repository.EsUserRepository;
import com.dw.demo.service.EsUserService;
import com.dw.demo.util.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author zhanzhihong
 * @date 2021-05-27 13:22
 */
@RestController
@RequestMapping("/search")
public class EsUserController {
    @Autowired
    private EsUserService esUserService;
    @Autowired
    private EsUserRepository esUserRepository;

    @GetMapping("saveAll")
    public R saveAll(){
        return esUserService.userSaveAll();
    }

    @GetMapping("deleteAll")
    public R deleteAll(){
         esUserRepository.deleteAll();
         return R.success();
    }

    @GetMapping("findUserList")
    public R findUserList(@RequestParam(value = "name",required = false)String name,
                          @RequestParam(value = "phone",required = false)String phone,
                          @RequestParam(value = "pageNum")Integer pageNum,
                          @RequestParam(value = "pageSize")Integer pageSize){

        return esUserService.findUserList(name,phone,pageNum,pageSize);

    }

    @GetMapping("findById")
    public R findById(@RequestParam(value = "id")Long id){

        Optional<EsUser> byId = esUserRepository.findById(id);
        EsUser esUser = byId.get();
        return R.success(esUser);

    }





}
