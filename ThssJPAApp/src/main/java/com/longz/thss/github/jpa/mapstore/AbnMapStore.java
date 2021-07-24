package com.longz.thss.github.jpa.mapstore;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.longz.thss.github.jpa.entity.Abn;
import com.longz.thss.github.jpa.entity.Apply;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;

public class AbnMapStore implements MapStore<String, Abn>, MapLoaderLifecycleSupport, Serializable {
    private static final ILogger logger = Logger.getLogger(AbnMapStore.class.getName());
    private static final String JTA_PU_NAME = "PG12_OZSSC_JTAPU";

    @PersistenceUnit(unitName = JTA_PU_NAME)
    private EntityManagerFactory emf;
    @PersistenceContext(unitName = JTA_PU_NAME)
    private EntityManager em;

    /*private Set<String> keysInDatabase = null;

    public void setKeysInDatabase(Set<String> keys){
        this.keysInDatabase = keys;
    }

    public Set<String> getKeysInDatabase(){
        return this.keysInDatabase;
    }*/
    public AbnMapStore() { }

    @Override
    public synchronized void store(String s, Abn abn) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        storeAbnToDB(abn);
        em.flush();
    }

    @Override
    public synchronized void storeAll(Map<String, Abn> map) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        for(Map.Entry<String, Abn> entry : map.entrySet()){
            storeAbnToDB(entry.getValue());
        }
        em.flush();
    }

    protected void storeAbnToDB(Abn abn){
        Abn exist = em.find(Abn.class,abn.getAbn());
        if(exist != null){
            if(!abn.equals(exist))
                em.merge(abn);
        }else{
            em.persist(abn);
        }
    }

    @Override
    public synchronized void delete(String s) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        Abn exist =em.find(Abn.class,s);
        if(exist !=null){
            em.remove(exist);
        }
        em.flush();
    }

    @Override
    public synchronized void deleteAll(Collection<String> collection) {
        loadAllKeys();
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        for(String key : collection){
            Abn abn = em.find(Abn.class, key);
            if (abn != null){
                em.remove(abn);
            }
        }
        em.flush();
    }

    @Override
    public synchronized Abn load(String s) {
        return em.find(Abn.class,s);
    }

    @Override
    public synchronized Map<String, Abn> loadAll(Collection<String> keys) {
        Map<String, Abn> result = (Map<String,Abn>)new HashMap<String,Abn>();
        Query query = em.createNamedQuery("Abn.findAll", Abn.class);
        List<Abn> applies = query.getResultList();
        for (Abn abn : applies){
            if (keys.contains(abn.getAbn())){
                result.put(abn.getAbn(),abn);
            }
        }
        /*for (String key : keys){
            Abn abn = this.load(key);
            if (abn != null){
                result.put(key,abn);
            }
        }*/
        return result;
    }

    @Override
    public Iterable<String> loadAllKeys() {
        Query query = em.createNamedQuery("Abn.findAllKeys", Abn.class);
        Set<String> keys = new HashSet<String>(query.getResultList());
        /*setKeysInDatabase(keys);*/
        return keys;
    }

    @Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String s) {
        logger.log(Level.INFO, "Init AbnMapStore");
        /*keysInDatabase = new HashSet<String>();*/
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
        }
        if(em == null){
            em = emf.createEntityManager();
        }
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO,"Destroy AbnMapStore");
        /*keysInDatabase = null;*/
        em.close();
        emf.close();
    }
}