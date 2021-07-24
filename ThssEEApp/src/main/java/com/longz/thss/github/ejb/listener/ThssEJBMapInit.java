package com.longz.thss.github.ejb.listener;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;
import com.hazelcast.map.IMap;
import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.longz.thss.github.jpa.entity.Abn;
import com.longz.thss.github.jpa.entity.Apply;
import javassist.NotFoundException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;
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
        Set<String> mappedEntities = new HashSet<String>(Arrays.asList("Abn","Apply"));
        if (injectedHzInstance != null) {
            logger.info(injectedHzInstance.getName()+" was injected into EJB.");
            Config injectConfig = injectedHzInstance.getConfig();

            for (String mapname: mappedEntities){
                MapConfig theMapConfig = injectConfig.findMapConfig(mapname.toLowerCase(Locale.ROOT));
                if (theMapConfig != null){
                    logger.info(theMapConfig.toString());
                    switch (mapname){
                        case "Abn":{
                            Map<String, Abn> abnmap = injectedHzInstance.getMap("abn");
                            if (abnmap != null){
                                logger.info("Map has size: "+abnmap.size()+ " For class: "+Abn.class.getName());
                                /*Abn abn = abnmap.get("61315477824");
                                if(abn != null){
                                    logger.info(abn.toString());
                                }else{
                                    logger.info("No Abn found from map.");
                                }*/
                            }else{
                                try {
                                    throw new NotFoundException("Abn Map not found");
                                } catch (NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                        case "Apply":{
                            Map<String, Apply> applymap = injectedHzInstance.getMap("apply");
                            if (applymap != null){
                                logger.info("Map has size: "+applymap.size()+ " For class: "+Apply.class.getName());
                                /*Apply apply = applyIMap.get("APPLS20170216104417899");
                                if (apply != null){
                                    logger.info(apply.toString());
                                }else{
                                    logger.info("Apply not found in map.");
                                }*/
                            }else{
                                try {
                                    throw new NotFoundException("Apply Map not found");
                                } catch (NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                        default: {
                            logger.info("No map name matched in switch.");
                            /*try {
                                throw new NotFoundException("Map not found");
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            }*/
                        }
                    }
                    /*Map<Object,Object> map = injectedHzInstance.getMap(mapname.toLowerCase(Locale.ROOT));*/
                }
            }
        }
        /*if (injectedHzInstance != null) {
            logger.info(injectedHzInstance.getName()+" was injected into EJB.");
            Config injectConfig = injectedHzInstance.getConfig();
            logger.info(injectConfig.getPartitionGroupConfig().toString());
            NetworkConfig netcon = injectConfig.getNetworkConfig();
            logger.info(netcon.toString());
            MapConfig injectedMap = injectConfig.findMapConfig("abn");
            SerializationConfig thefactory = injectConfig.getSerializationConfig();
            if (injectedMap == null){
                logger.info("------------------ Apply map config not found.");
            }else{

                logger.info(injectedMap.toString());
                logger.info(thefactory.toString());
            }

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
        }*/
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
