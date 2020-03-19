package com.grgbanking.huitong.driver_libs.interfaces;


import com.grgbanking.huitong.driver_libs.database.EntyType;

import java.util.List;

/**
 * Author: gongxiaobiao
 * Date: on 2020/3/17 14:56
 * Email: 904430803@qq.com
 * Description: 数据库操作接口
 */
public interface IDatabase {

    /**
     * @method
     * @description 添加一条记录
     * @date: 2020/3/17 14:58
     * @author: gongxiaobiao
     * @param
     * @return
     */
    public <T> boolean insert(EntyType type, T record);

    public <T> boolean update(EntyType type,T record);
    public  <T> boolean delete(EntyType type,T record);
    public List querry(EntyType type);
    public long countToday(EntyType type);
    public long countTotal(EntyType type);

}
