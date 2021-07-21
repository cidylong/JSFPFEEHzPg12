package com.longz.thss.github.jpa.mapstore;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.longz.thss.github.jpa.entity.Apply;
import com.longz.thss.github.jpa.utils.ThssLibUtils;

import java.io.Serializable;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class ApplyJDBCMapStore implements MapStore<String, Apply>, MapLoaderLifecycleSupport, Serializable {
    private static final ILogger logger = Logger.getLogger(ApplyJDBCMapStore.class.getName());
    private Connection con = null;
    private PreparedStatement allKeysStatement = null;

    public ApplyJDBCMapStore(){
        try {
            con = DriverManager.getConnection("jdbc:postgresql://10.0.1.105:5433/ozssc", "tomcat", "tacmot");
            allKeysStatement = con.prepareStatement("select apply_id from apply order by apply_id desc");
            logger.info("-------------- ApplyApplyJDBCMapStore constructor build connection and allKeysStatement: "+con.toString()+"|"+allKeysStatement.toString());
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());/*logger.log(Level.SEVERE,throwables.toString());*/
        }
    }

    public ApplyJDBCMapStore(Connection con, PreparedStatement allKeysStatement) {
        this.con = con;
        this.allKeysStatement = allKeysStatement;
    }

    @Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String s) {
        if(con ==null){
            try {
                con = DriverManager.getConnection("jdbc:postgresql://10.0.1.105:5433/ozssc", "tomcat", "tacmot");
            } catch (SQLException throwables) {
                logger.log(Level.SEVERE,throwables.toString());
            }
        }
        if (allKeysStatement == null){
            try {
                allKeysStatement = con.prepareStatement("select apply_id from apply order by apply_id desc");
            } catch (SQLException throwables) {
                logger.log(Level.SEVERE,throwables.toString());
            }
        }
    }

    @Override
    public void destroy() {
        try {
            con.close();
            allKeysStatement.close();
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }
    }

    @Override
    public synchronized void store(String s, Apply apply) {
        try {
            con.createStatement().executeUpdate(String.format("insert into apply values(%s,%s,%s,%s,%s, %t,%s,%s,%t,%s)",
                    apply.getApplyId(),apply.getApplicant(), apply.getTarget(),apply.getComment(),apply.getCategory(),apply.getCreated(),
                    apply.getStatus(), apply.getResponsible(), apply.getProcessed(), apply.getTargetOwner()));
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }
        /*String sql="insert into apply values(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            storeEntity(ps,apply);
            ps.executeBatch();
            con.commit();
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }*/
    }

    @Override
    public synchronized void storeAll(Map<String, Apply> map) {
        for (Map.Entry<String,Apply> entry : map.entrySet()){
            store(entry.getKey(), entry.getValue());
        }
    }

    protected void storeEntity(PreparedStatement ps, Apply apply){
        try {
            ps.setString(1, apply.getApplyId());
            ps.setString(2, apply.getApplicant());
            ps.setString(3, apply.getTarget());
            ps.setString(4, apply.getComment());
            ps.setString(5, apply.getCategory());
            ps.setTimestamp(6, Timestamp.valueOf(apply.getCreated()));
            ps.setString(7, apply.getStatus());
            ps.setString(8, apply.getResponsible());
            ps.setTimestamp(9, Timestamp.valueOf(apply.getProcessed()));
            ps.setString(10,apply.getTargetOwner());
            ps.addBatch();
            /*ps.executeUpdate();*/
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }
    }
    
    @Override
    public synchronized void delete(String s) {
        try {
            con.createStatement().executeUpdate(String.format("delete from apply where apply_id = %s",s));
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }
    }

    @Override
    public synchronized void deleteAll(Collection<String> collection) {
        for (String key : collection){
            delete(key);
        }
    }

    @Override
    public synchronized Apply load(String s) {
        try {
            ResultSet rs = con.createStatement().executeQuery(String.format("select * from apply where apply_id = $s",s));
            if (!rs.next()){
                return null;
            }else{
                return new Apply(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
                        ThssLibUtils.getLocalDateTimeFromLong(rs.getLong(6)),rs.getString(7),rs.getString(8),
                        ThssLibUtils.getLocalDateTimeFromLong(rs.getLong(9)),rs.getString(10));
            }
        } catch (SQLException throwables) {
            logger.log(Level.SEVERE,throwables.toString());
        }
        return null;
    }

    @Override
    public synchronized Map<String, Apply> loadAll(Collection<String> collection) {
        Map<String, Apply> result = new HashMap<>();
        for (String key: collection){
            result.put(key,load(key));
        }
        logger.info("--------------ApplyApplyJDBCMapStore loadAll loaded size: "+result.size());
        return result;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        return new StatementIterable<String>(allKeysStatement);
        /*return null;*/
    }
}
