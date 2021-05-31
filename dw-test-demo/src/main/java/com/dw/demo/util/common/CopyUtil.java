package com.dw.demo.util.common;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ZhangJiaYan
 * @Date 2020/4/18
 * @Description copy工具
 */
public class CopyUtil {

    /**
     * 拷贝集合
     * @param list 原始数据
     * @param clazz 目标
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> copyList(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try{
            if(list == null){
                return result;
            }
            for(Object obj:list){
                T t = clazz.newInstance();
                copyAll(obj,t);
                result.add(t);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     *  过滤掉为空的属性
     * @param src 源对象
     * @param target 目标UI东西
     */
    public static void copyIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, nullPropertyNames(src));
    }


    /**
     *  取空属性名
     * @param source 源对象
     * @return 结果
     */
    public static String[] nullPropertyNames (Object source) {

        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null){
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 拷贝新对象
     * @param src 源对象
     * @param target 目标对象
     * @param <T> 目标
     * @return 结果集
     */
    public static <T>T copyNew(Object src, Class<T> target){
        try{
            T t = target.newInstance();
            BeanUtils.copyProperties(src, t);
            return t;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  拷贝全部属性
     * @param src 源对象
     * @param target 目标对象
     */
    public static void copyAll(Object src, Object target){
        BeanUtils.copyProperties(src, target);
    }

}
