package com.longz.thss.github.ejb.listener;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.IMap;
import com.longz.thss.github.jpa.entity.Apply;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;

@Singleton(name = "thssMapInit",mappedName = "JsfEeHzPgApp-EJB-thssMapInit")
@Startup
public class ThssEJBMapInit implements Serializable {
    private static final ILogger logger = Logger.getLogger(ThssEJBMapInit.class.getName());
    private HazelcastInstance ejbHzInstance = null;
    private IMap<String, Apply> applyIMap = null;
    private String ejbHzInstanceName = null;

    @PostConstruct
    public void init(){
        setEjbHzInstance();
        applyIMap = getMap("apply");
    }

    public <K extends Object, V extends Object> IMap<K, V> getMap(String name) {
        if (isNull()) {
            logger.log(Level.SEVERE, "Hazelcast Instance cannot be null");
            return null;
        }
        return ejbHzInstance.getMap(name);
    }

    private boolean isNull() {
        return ejbHzInstance == null;
    }

    public void setEjbHzInstance(){
        Config thssHzConfig = new ClasspathXmlConfig("META-INF/hazelcast-config-ejb.xml");
        ejbHzInstance = Hazelcast.newHazelcastInstance(thssHzConfig);
        if(ejbHzInstance != null){
            ejbHzInstanceName = ejbHzInstance.getName();
        }
    }
}
