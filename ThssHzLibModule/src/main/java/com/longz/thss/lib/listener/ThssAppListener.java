package com.longz.thss.lib.listener;

import com.hazelcast.cluster.Member;
import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.config.Config;
import com.hazelcast.core.*;
import com.hazelcast.logging.ILogger;
import com.hazelcast.logging.Logger;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class ThssAppListener implements MembershipListener, DistributedObjectListener, LifecycleListener {
    private static final ILogger logger = Logger.getLogger(ThssAppListener.class.getName());

    /*private static DistributedObject distObj = null;*/
    private Set<DistributedObject> distributedObjectSet = null;
    private Set<Member> clusteredMembers = null;

    @Inject
    HazelcastInstance dasHzInstance;

    public ThssAppListener(){
        distributedObjectSet = new HashSet<>();
        clusteredMembers = new HashSet<>();
    }

    @Override
    public void memberAdded(MembershipEvent me) {
        Member member = me.getMember();
        clusteredMembers.add(member);
        logger.info("-------Clustered Hazelcast member added:|"+me.getMember().getClass().getName()+"@"+me.getMember().getAddress().toString()+"|--------Member size: |"+clusteredMembers.size());
        /*logger.log(Level.WARNING,"-------Clustered Hazelcast member added:|"+me.getMember().getClass().getName()+"@"+me.getMember().getAddress().toString()+"|--------Member size: |"+clusteredMembers.size());*/
        /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void memberRemoved(MembershipEvent me) {
        logger.info("-------Hazelcast member removed:|"+me.getMember().getClass().getName()+"@"+me.getMember().getAddress().toString()+"|--------");
        /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void distributedObjectCreated(DistributedObjectEvent doe) {
        DistributedObject distObj = doe.getDistributedObject();
        distributedObjectSet.add(distObj);
        /*logger.log(Level.WARNING,"--------Distributed Obj Created: " + distObj.getName() + ", service=" + distObj.getServiceName()+"---------- Distributed Object size: "+distributedObjectSet.size());*/
        logger.info("--------Distributed Obj Created: " + distObj.getName() + ", service=" + distObj.getServiceName()+"---------- Distributed Object size: "+distributedObjectSet.size());
        /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void distributedObjectDestroyed(DistributedObjectEvent doe) {
        logger.info("-------Destroyed " + doe.getObjectName() + ", service=" + doe.getServiceName()+"-----------");
        /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stateChanged(LifecycleEvent lifecycleEvent) {
        switch (lifecycleEvent.getState()){
            case STARTING:{
                logger.info("-------Hazelcast service Starting.--------");
                break;
            }
            case STARTED: {
                logger.info("---------Hazelcast service Started.---------");
                logger.info("---------after started, service pop up.");
                checkInstancesAfterStarted();
                break;
            }
            case MERGED: {
                logger.info("---------Merged---------");
                break;
            }
            case MERGING: {
                logger.info("---------Merging---------");
                break;
            }
            case SHUTDOWN: {
                logger.info("---------Shutdown---------");
                /*sessionService.setFailedConnection(true);*/
                break;
            }
            case MERGE_FAILED: {
                logger.finest("---------Merging Failed---------");
                break;
            }
            case SHUTTING_DOWN: {
                logger.info("---------Shutting down---------");
                break;
            }
            case CLIENT_CONNECTED: {
                logger.info("---------Client joined.---------");
                break;
            }
            case CLIENT_DISCONNECTED: {
                logger.info("---------Client exited.---------");
                break;
            }
            case CLIENT_CHANGED_CLUSTER: {
                logger.info("---------Client changed cluster.---------");
                break;
            }
            default: {
                throw new IllegalStateException("---------Unexpected value: " + lifecycleEvent+"---------");
            }
            /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
        }
        /*throw new UnsupportedOperationException("Not supported yet.");*/ //To change body of generated methods, choose Tools | Templates.
    }
    protected void checkInstancesAfterStarted(){
        Set<HazelcastInstance> instances = Hazelcast.getAllHazelcastInstances();
        if (instances.size()>0){
            logger.info("-------DAS server instance after started: "+instances.size());
            dasHzInstance = instances.iterator().next();
            handleDASConfigure();
        }else{
            logger.info("------ Hazelcast has no instance after started.");
        }
    }

    protected void handleDASConfigure(){
        if (dasHzInstance != null){
            getConfigFromHzInstance();
        }else{
            logger.info("-------DAS instance not found by @Inject, have to try lookup from context.");
            hzInstanceLookup();
        }
    }

    protected void hzInstanceLookup(){
        Context ctx = null;
        try {
            ctx = new InitialContext();
            HazelcastInstance instance = (HazelcastInstance) ctx.lookup("payara/Hazelcast");
            if (instance != null){
                dasHzInstance = instance;
                getConfigFromHzInstance();
            }else{
                logger.info("-----There is no Hazelcast instance found even in context lookup.");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void getConfigFromHzInstance(){
        Config dasConfig = dasHzInstance.getConfig();
        if (dasConfig !=null){
            logger.info("-------- DAS configure pick up as: "+dasConfig.toString());
        }else{
            logger.info("--------DAS configure not found");
        }
    }
}