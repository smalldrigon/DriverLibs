package com.grgbanking.huitong.driver_libs.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;


import org.greenrobot.greendao.annotation.Generated;



/**
 * Author: gongxiaobiao
 * Date: on 2020/3/17 10:53
 * Email: 904430803@qq.com
 * Description:左超时人数实体类
 */
@Entity
public class LeftUnPass {

    private String ticketNo;

    @Id(autoincrement = true)
    private Long id;//通过的记录id
    private String timeStamp;//记录时间
    @Generated(hash = 191632675)
    public LeftUnPass(String ticketNo, Long id, String timeStamp) {
        this.ticketNo = ticketNo;
        this.id = id;
        this.timeStamp = timeStamp;
    }
    @Generated(hash = 612391392)
    public LeftUnPass() {
    }
    public String getTicketNo() {
        return this.ticketNo;
    }
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTimeStamp() {
        return this.timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}