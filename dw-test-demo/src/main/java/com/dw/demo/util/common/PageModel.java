package com.dw.demo.util.common;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@Data
public class PageModel<T> {
    /**
     * 页数
     */
    @Min(value = 1, message = "页数不能小于1")
    private Integer page = 1;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "每页大小不能小于1")
    @Max(value = 1000, message = "每页大小不能大于1000")
    private Integer size = 10;

    private Long total;

    private List<T> pageList;

    //页数
    public int page() {
        return (page - 1) * size;
    }

    //条数
    public int size() {
        return size;
    }
}
