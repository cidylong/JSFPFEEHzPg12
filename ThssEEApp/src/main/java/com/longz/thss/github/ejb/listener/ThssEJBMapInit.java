package com.longz.thss.github.ejb.listener;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.IMap;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.longz.thss.github.jpa.entity.Apply;
import javassist.NotFoundException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;

@Singleton(name = "thssMapInit",mappedName = "JsfEeHzPgApp-EJB-thssMapInit")
@Startup
public class ThssEJBMapInit implements Serializable {
    private static final ILogger logger = Logger.getLogger(ThssEJBMapInit.class.getName());
    private HazelcastInstance ejbHzInstance = null;
    private IMap<String, Apply> applyIMap = null;
    private String ejbHzInstanceName = null;

    @Inject
    private HazelcastInstance injectedHzInstance;

    @PostConstruct
    public void init(){
        /*setEjbHzInstance();*/
        /*Config config = new ClasspathXmlConfig("META-INF/hazelcast-config-ejb.xml");*/
        if (injectedHzInstance != null) {
            logger.info(injectedHzInstance.getName()+" was injected into EJB.");
            Config injectConfig = injectedHzInstance.getConfig();
            logger.info(injectConfig.getPartitionGroupConfig().toString());
            NetworkConfig netcon = injectConfig.getNetworkConfig();
            logger.info(netcon.toString());
            MapConfig injectedMap = injectConfig.findMapConfig("apply");
            SerializationConfig thefactory = injectConfig.getSerializationConfig();
            if (injectedMap == null){
                logger.info("------------------ Apply map config not found.");
            }else{
                /*MapStoreConfig thestore =new MapStoreConfig().setEnabled(true).setClassName("com.longz.thss.github.jpa.mapstore.ApplyJDBCMapStore");
                thestore.setInitialLoadMode(MapStoreConfig.InitialLoadMode.EAGER);*/
                /*MapConfig applymapstorecon = ("apply");*/
                /*injectedMap.getMapStoreConfig().setEnabled(true);
                injectedMap.getMapStoreConfig().setClassName("com.longz.thss.github.jpa.mapstore.ApplyJDBCMapStore");*/
                /*injectedHzInstance.getConfig().addMapConfig(applymapstorecon);*/
                logger.info(injectedMap.toString());
                logger.info(thefactory.toString());
            }
            /*injectedHzInstance.getConfig().addMapConfig(config.getMapConfig("apply"));
            logger.info("-------------------- Map configure was added to injected Instance.");*/
            applyIMap =  injectedHzInstance.getMap("apply");
            if (applyIMap !=null) {
                logger.info("--------------- Apply maps size: " + applyIMap.size());
                Apply apply = applyIMap.get("APPLS20170216104417899");
                if (apply != null){
                    logger.info(apply.toString());
                }else{
                    logger.info("Apply not found in map.");
                }
            }else{
                try {
                    throw new NotFoundException("Apply map not found.");
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
