package com.eugene.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
public class PageResponse<T> extends Response<Page<T>> {

    public static <T> PageResponse<T> of(Collection<T> data, int totalCount, int pageNo, int pageSize) {
        PageResponse<T> response = new PageResponse<>();
        Page<T> page = new Page<>();
        Page.PageInfo pageInfo = new Page.PageInfo(totalCount, pageNo, pageSize);
        page.setPageInfo(pageInfo);
        page.setList(data);
        response.setData(page);
        return response;
    }

    /**
     * 构造一个空数据的分页响应结构体
     *
     * @param pageNo
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> PageResponse<T> ofEmpty(int pageNo, int pageSize) {
        return of(Collections.emptyList(), 0, pageNo, pageSize);
    }
}
