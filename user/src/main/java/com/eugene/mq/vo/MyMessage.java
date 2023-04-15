package com.eugene.mq.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyMessage implements Serializable {
    private Integer id;
    private String name;
    private String status;
    private Date createTime;
}
