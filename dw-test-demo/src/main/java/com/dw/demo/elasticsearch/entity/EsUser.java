package com.dw.demo.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhanzhihong
 * @date 2021-05-27 13:34
 * @Document 作用在类，标记实体类为文档对象，一般有两个属性
 * indexName：对应索引库名称
 * type：对应在索引库中的类型
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 * @Id 作用在成员变量，标记一个字段作为id主键
 * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：
 * type：字段类型，取值是枚举：FieldType
 * index：是否索引，布尔类型，默认是true
 * store：是否存储，布尔类型，默认是false
 * analyzer：分词器名称
 */
@Document(indexName = "user", type = "issue")
@Data
public class EsUser implements Serializable {
    @Id
    @Field(type = FieldType.Long, store = true)
    private Long id;

    /**
     * 用户名
     */
    @Field(type = FieldType.Text, store = true, analyzer = "ik_max_word")
    private String userName;

    /**
     * 密码
     */
    @Field(type = FieldType.Text, store = true, index = false)
    private String password;

    /**
     * 加密盐值
     */
    @Field(type = FieldType.Text, store = true, index = false)
    private String salt;

    /**
     * 邮箱
     */
    @Field(type = FieldType.Text, store = true)
    private String email;

    /**
     * 联系方式
     */
    @Field(type = FieldType.Text, store = true)
    private String phone;

    /**
     * 年龄：1男2女
     */
    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer sex;

    /**
     * 年龄
     */
    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer age;

    /**
     * 最后登录时间
     */
    @Field(type = FieldType.Date, store = true, index = false)
    private Date lastLoginTime;
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, store = true, index = false)
    private Date createdAt;

    /**
     * 是否删除：0=正常，1=删除
     */
    @Field(type = FieldType.Boolean, store = true)
    private Boolean archive;

}
