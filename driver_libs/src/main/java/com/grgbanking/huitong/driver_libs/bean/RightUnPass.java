package com.grgbanking.huitong.driver_libs.bean;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
/**
 * Author: gongxiaobiao
 * Date: on 2020/3/17 10:53
 * Email: 904430803@qq.com
 * Description 右超时人数实体类
 */
@Entity
public class RightUnPass {
    private String ticketNo;
    @Id(autoincrement = true)
    private Long id;//通过的记录id
    private String timeStamp;//记录时间
    @Generated(hash = 929749008)
    public RightUnPass(String ticketNo, Long id, String timeStamp) {
        this.ticketNo = ticketNo;
        this.id = id;
        this.timeStamp = timeStamp;
    }
    @Generated(hash = 1602250723)
    public RightUnPass() {
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