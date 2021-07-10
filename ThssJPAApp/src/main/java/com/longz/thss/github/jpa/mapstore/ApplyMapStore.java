package com.longz.thss.github.jpa.mapstore;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.longz.thss.github.jpa.entity.Apply;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;

public class ApplyMapStore implements MapStore<String, Apply>, MapLoaderLifecycleSupport, Serializable {
    private static final ILogger logger = Logger.getLogger(ApplyMapStore.class.getName());
    private static final String JTA_PU_NAME = "PG12_OZSSC_JTAPU";

    @PersistenceUnit(unitName = JTA_PU_NAME)
    private EntityManagerFactory emf;
    @PersistenceContext(unitName = JTA_PU_NAME)
    private EntityManager em;

    @Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String s) {
        logger.log(Level.INFO, "Init ApplyMapStore");
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
        }
        if(em == null){
            em = emf.createEntityManager();
        }
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO,"Destroy ApplyMapStore");
        em.close();
        emf.close();
    }

    @Override
    public void store(String s, Apply apply) {
        if(!em.isJoinedToTransaction()){
            em.joinTransaction();
        }
        storeApplyToDB(apply);
        em.flush();
    }

    @Override
    public void storeAll(Map<String, Apply> map) {
        if(!em.isJoinedToTransaction()){
            em.joinTransaction();
        }
        for(Map.Entry<String, Apply> entry : map.entrySet()) {
            storeApplyToDB(entry.getValue());
        }
        em.flush();
    }

    protected void storeApplyToDB(Apply apply){
        Apply exist = em.find(Apply.class,apply.getApplyId());
        if (exist != null){
            if (!apply.equals(exist)){
                em.merge(apply);
            }
        }else{
            em.persist(apply);
        }
    }

    @Override
    public void delete(String s) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        Apply exist =em.find(Apply.class,s);
        if(exist !=null){
            em.remove(exist);
        }
        em.flush();
    }

    @Override
    public void deleteAll(Collection<String> collection) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        for(String s : collection) {
            Apply exist = em.find(Apply.class, s);
            if (exist != null) {
                em.remove(exist);
            }
        }
        em.flush();
    }

    @Override
    public Apply load(String s) {
        return em.find(Apply.class,s);
    }

    @Override
    public Map<String, Apply> loadAll(Collection<String> collection) {
        Map<String,Apply> returnMap = new HashMap<>();
        for (String s : collection){
            Apply apply = load(s);
            if(apply != null){
                returnMap.put(s,apply);
            }
        }
        return returnMap;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        Query query = em.createNamedQuery("Apply.findAllIds",Apply.class);
        Set<String> keys = new HashSet<String>(query.getResultList());
        return keys;
    }
}
