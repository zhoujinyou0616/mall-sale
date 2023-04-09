
package com.eugene.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PageQuery extends Query {

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int pageNo = 1;

    private String orderBy;

    private String orderDirection = DESC;

    private String groupBy;

    private boolean needTotalCount = true;

    public int getPageNo() {
        if (pageNo < 1) {
            return 1;
        }
        return pageNo;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

}
