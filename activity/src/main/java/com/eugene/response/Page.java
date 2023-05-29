package com.eugene.response;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Collection;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    private PageInfo pageInfo;
    private Collection<T> list;

    @Getter
    @Setter
    @ToString
    public static class PageInfo {
        // 总条数
        private int total = 0;
        // 页码
        private int pageNo = 1;
        // 每页大小
        private int pageSize = DEFAULT_PAGE_SIZE;
        // 总页数
        private int pageCount = 0;

        public PageInfo(int totalCount, int pageNo, int pageSize) {
            this.total = totalCount;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            if (pageNo < 1) {
                return 1;
            }
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            if (pageNo < 1) {
                this.pageNo = 1;
            } else {
                this.pageNo = pageNo;
            }
        }

        public int getPageCount() {
            return this.total % this.pageSize == 0 ? this.total
                    / this.pageSize : (this.total / this.pageSize) + 1;
        }

    }

}