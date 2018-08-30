package com.example.demo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/11/30.
 *
 * @author hadoop
 */
@Repository
public class BaseDao {


    @Autowired
    protected JdbcTemplate jdbc;


    /**
     *   获取集合中的一条数据
     * @param sql
     * @param obj
     * @return
     */
    protected Map<String ,Object> queryForMap(String sql , Object[] obj){
        List<Map<String,Object>> list = jdbc.queryForList(sql , obj);
        if(list!=null && list.size() >0 ){
            return list.get(0);
        }
        return null;
    }

}
