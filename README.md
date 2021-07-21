# JSFPFEEHzPg12

Project was inited in June 2021 for Payara 5.2021.4 testing purpsose.

Project deployment and run time environment:
- OS: Centos 8
- JDK: 11.0.9
- JavaEE environment: Payara Community Edition 5.2021.4
- BackEnd Database: PostgreSQL-12
- JSF implementation: PrimeFaces-10.0.0

R&D Environment and deployment hierachy
- IDE: IntelliJ IDEA 2021.1
- JavaEE Run time hosts(Linux): centos8thssv1 (IP: 10.0.1.102), centos8thssv2 (IP: 10.0.1.152) in headless mode (access over network by using SSH only)
- Backend PostgreSQL-12 DataBase (Linux): centos8storage(IP: 10.0.1.105)
- System and Application unix user: payara

Application Platform install and configuration:
- download and extract payara-5.2021.4.zip into: /u01/app/payara5 on host centos8thssv1 by user payara
- run [/u01/app/payara5/bin/asadmin create-domain --template /u01/app/payara5/glassfish/common/templates/gf/appserver-domain.jar production] on centos8thssv1 by user payara
- this command will create a named "production" domain
- start domain by [/u01/app/payara5/bin/asadmin start-domain production] on centos8thssv1 by user payara
- configure domain on centos8thssv1 by user payara
- run [/u01/app/payara5/bin/asadmin change-admin-password] to reset default domain password
- run [/u01/app/payara5/bin/asadmin enable-secure-admin] to enable ssl access to let user to login by using admin_ui from web browser
- run [/u01/app/payara5/bin/asadmin restart-domain production] to make above change effective
- run [/u01/app/payara5/bin/asadmin login] to enable uses to access admin without password
- run [/u01/app/payara5/bin/asadmin add-library /home/payara/postgresql-42.2.18.jar] to add postgrsql jdbc driver to enable Payara server talk to PostgreSQL database.
- add JDBC data sources to Payara server
- create connection pool for data source: [/u01/app/payara5/bin/asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property url="jdbc\:postgresql\://10.0.1.105\:5433/ozssc":user="xxxxxx":password="xxxxxx":host="10.0.1.105":port=5433:database="ozssc" --steadypoolsize 128 --maxpoolsize 256 PG12pool-ozssc-105]
- create datasource: [/u01/app/payara5/bin/asadmin create-jdbc-resource --connectionpoolid PG12pool-ozssc-105 --enabled true jdbc/PG12_ozssc_105]
- run [/u01/app/payara5/bin/asadmin start-domain production] to restart domain
- Add one more node into domain from admin_ui over browser
- Node Name: centos8thssv2-production
- Host: centos8thssv2
- Type: SSH
- Node Directory: /u01/app/payara5
- Enforce to install: true
- Add two more Payara Server Instances on to two hosts
- ThssEJBServer (for EJB applications deployment)
- Name: ThssEJBServer
- Node: localhost-production (centos8thssv1)
- ThssWEBServer (for WEB applications deployment)
- Name: ThssWEBServer
- Node: centos8thssv2-production (centos8thssv2)
- assign JDBC datasources to instances to enable database access
- start all payara server instances over admin_ui by admin

Development
  - Developed entity apply with IdentifiedDataSerializable implementation
	implement MapStore
	implement DataSerializableFactory
	- Develop a EJB to fire map access
	
Configure hazelcast by
  - adding apply map
	add CP
	configure networking
	registered data serialize
	  - deploy hazelcast-config.xml to domain config directory (DAS server will pick it up)

Deployment
  - Entities with JPA was wrapped into a jar deployed into domain as lib
  - EJB module was wrapped into a jar deployed into ThssEJBServer instance as ejb application
  - Result as:
	- kick up map error as:
	  - EJB server instance side error as:
		[2021-07-22T08:26:19.285+1000] [Payara 5.2021.4] [WARNING] [] [com.hazelcast.spi.impl.proxyservice.ProxyService] [tid: _ThreadID=29 _ThreadName=RunLevelControllerThread-1626906306141] [timeMillis: 1626906379285] [levelValue: 900] [[
		[10.0.1.102]:5702 [thss] [4.2] Error while initializing proxy: IMap{name='apply'}
		com.hazelcast.core.OperationTimeoutException: IsKeyLoadFinishedOperation got rejected before execution due to not starting within the operation-call-timeout of: 60000 ms. Current time: 2021-07-22 08:26:19.282. Start time: 2021-07-22 08:25:19.266. Total elapsed time: 60016 ms. Invocation{op=com.hazelcast.map.impl.operation.IsKeyLoadFinishedOperation{serviceName='hz:impl:mapService', identityHash=1480123690, partitionId=160, replicaIndex=0, callId=-554, invocationTime=1626906319266 (2021-07-22 08:25:19.266), waitTimeout=-1, callTimeout=60000, tenantControl=fish.payara.nucleus.hazelcast.PayaraHazelcastTenant@53310b07, name=apply}, tryCount=250, tryPauseMillis=500, invokeCount=1, callTimeoutMillis=60000, firstInvocationTimeMs=1626906319266, firstInvocationTime='2021-07-22 08:25:19.266', lastHeartbeatMillis=1626906376891, lastHeartbeatTime='2021-07-22 08:26:16.891', target=[10.0.1.102]:5701, pendingResponse={VOID}, backupsAcksExpected=-1, backupsAcksReceived=0, connection=Connection[id=1, /10.0.1.102:54215->/10.0.1.102:5701, qualifier=null, endpoint=[10.0.1.102]:5701, alive=true, connectionType=MEMBER, planeIndex=0]}
		at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.newOperationTimeoutException(InvocationFuture.java:194)
		at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.resolve(InvocationFuture.java:134)
		at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.resolveAndThrowIfException(InvocationFuture.java:99)
		at com.hazelcast.spi.impl.AbstractInvocationFuture.get(AbstractInvocationFuture.java:617)
		at com.hazelcast.map.impl.proxy.MapProxySupport.waitUntilLoaded(MapProxySupport.java:741)
		at com.hazelcast.map.impl.proxy.MapProxyImpl.waitUntilLoaded(MapProxyImpl.java:112)
		at com.hazelcast.map.impl.proxy.MapProxySupport.initializeMapStoreLoad(MapProxySupport.java:330)
		at com.hazelcast.map.impl.proxy.MapProxySupport.initialize(MapProxySupport.java:265)
		at com.hazelcast.map.impl.proxy.MapProxyImpl.initialize(MapProxyImpl.java:112)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.doCreateProxy(ProxyRegistry.java:250)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.createProxy(ProxyRegistry.java:219)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.getOrCreateProxyFuture(ProxyRegistry.java:186)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.getOrCreateProxy(ProxyRegistry.java:166)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyServiceImpl.getDistributedObject(ProxyServiceImpl.java:153)
		at com.hazelcast.instance.impl.HazelcastInstanceImpl.getDistributedObject(HazelcastInstanceImpl.java:354)
		at com.hazelcast.instance.impl.HazelcastInstanceImpl.getMap(HazelcastInstanceImpl.java:173)
		at com.hazelcast.instance.impl.HazelcastInstanceProxy.getMap(HazelcastInstanceProxy.java:95)
		at com.longz.thss.github.ejb.listener.ThssEJBMapInit.init(ThssEJBMapInit.java:58)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.BeanCallbackInterceptor.intercept(InterceptorManager.java:1022)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.CallbackInvocationContext.proceed(CallbackInvocationContext.java:204)
		at com.sun.ejb.containers.interceptors.SystemInterceptorProxy.doCall(SystemInterceptorProxy.java:163)
		at com.sun.ejb.containers.interceptors.SystemInterceptorProxy.init(SystemInterceptorProxy.java:125)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.CallbackInterceptor.intercept(InterceptorManager.java:978)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.CallbackInvocationContext.proceed(CallbackInvocationContext.java:204)
		at org.jboss.weld.module.ejb.AbstractEJBRequestScopeActivationInterceptor.aroundInvoke(AbstractEJBRequestScopeActivationInterceptor.java:81)
		at org.jboss.weld.module.ejb.SessionBeanInterceptor.aroundInvoke(SessionBeanInterceptor.java:52)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.CallbackInterceptor.intercept(InterceptorManager.java:978)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.InterceptorManager.intercept(InterceptorManager.java:418)
		at com.sun.ejb.containers.interceptors.InterceptorManager.intercept(InterceptorManager.java:381)
		at com.sun.ejb.containers.BaseContainer.intercept(BaseContainer.java:2062)
		at com.sun.ejb.containers.AbstractSingletonContainer.createSingletonEJB(AbstractSingletonContainer.java:529)
		at com.sun.ejb.containers.AbstractSingletonContainer.access$000(AbstractSingletonContainer.java:81)
		at com.sun.ejb.containers.AbstractSingletonContainer$SingletonContextFactory.create(AbstractSingletonContainer.java:687)
		at com.sun.ejb.containers.AbstractSingletonContainer.instantiateSingletonInstance(AbstractSingletonContainer.java:421)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.initializeSingleton(SingletonLifeCycleManager.java:219)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.initializeSingleton(SingletonLifeCycleManager.java:180)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.doStartup(SingletonLifeCycleManager.java:159)
		at org.glassfish.ejb.startup.EjbApplication.start(EjbApplication.java:171)
		at org.glassfish.internal.data.EngineRef.start(EngineRef.java:123)
		at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:293)
		at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:364)
		at com.sun.enterprise.v3.server.ApplicationLifecycle.initialize(ApplicationLifecycle.java:623)
		at com.sun.enterprise.v3.server.ApplicationLoaderService.postConstruct(ApplicationLoaderService.java:335)
		at org.jvnet.hk2.internal.ClazzCreator.postConstructMe(ClazzCreator.java:303)
		at org.jvnet.hk2.internal.ClazzCreator.create(ClazzCreator.java:351)
		at org.jvnet.hk2.internal.SystemDescriptor.create(SystemDescriptor.java:463)
		at org.glassfish.hk2.runlevel.internal.AsyncRunLevelContext.findOrCreate(AsyncRunLevelContext.java:281)
		at org.glassfish.hk2.runlevel.RunLevelContext.findOrCreate(RunLevelContext.java:65)
		at org.jvnet.hk2.internal.Utilities.createService(Utilities.java:2102)
		at org.jvnet.hk2.internal.ServiceHandleImpl.getService(ServiceHandleImpl.java:93)
		at org.jvnet.hk2.internal.ServiceHandleImpl.getService(ServiceHandleImpl.java:67)
		at org.glassfish.hk2.runlevel.internal.CurrentTaskFuture$QueueRunner.oneJob(CurrentTaskFuture.java:1213)
		at org.glassfish.hk2.runlevel.internal.CurrentTaskFuture$QueueRunner.run(CurrentTaskFuture.java:1144)
		at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
		at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
		at java.base/java.lang.Thread.run(Thread.java:834)
		at ------ submitted from ------.()
		at com.hazelcast.internal.util.ExceptionUtil.cloneExceptionWithFixedAsyncStackTrace(ExceptionUtil.java:279)
		at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.returnOrThrowWithGetConventions(InvocationFuture.java:112)
		at com.hazelcast.spi.impl.operationservice.impl.InvocationFuture.resolveAndThrowIfException(InvocationFuture.java:100)
		at com.hazelcast.spi.impl.AbstractInvocationFuture.get(AbstractInvocationFuture.java:617)
		at com.hazelcast.map.impl.proxy.MapProxySupport.waitUntilLoaded(MapProxySupport.java:741)
		at com.hazelcast.map.impl.proxy.MapProxyImpl.waitUntilLoaded(MapProxyImpl.java:112)
		at com.hazelcast.map.impl.proxy.MapProxySupport.initializeMapStoreLoad(MapProxySupport.java:330)
		at com.hazelcast.map.impl.proxy.MapProxySupport.initialize(MapProxySupport.java:265)
		at com.hazelcast.map.impl.proxy.MapProxyImpl.initialize(MapProxyImpl.java:112)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.doCreateProxy(ProxyRegistry.java:250)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.createProxy(ProxyRegistry.java:219)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.getOrCreateProxyFuture(ProxyRegistry.java:186)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyRegistry.getOrCreateProxy(ProxyRegistry.java:166)
		at com.hazelcast.spi.impl.proxyservice.impl.ProxyServiceImpl.getDistributedObject(ProxyServiceImpl.java:153)
		at com.hazelcast.instance.impl.HazelcastInstanceImpl.getDistributedObject(HazelcastInstanceImpl.java:354)
		at com.hazelcast.instance.impl.HazelcastInstanceImpl.getMap(HazelcastInstanceImpl.java:173)
		at com.hazelcast.instance.impl.HazelcastInstanceProxy.getMap(HazelcastInstanceProxy.java:95)
		at com.longz.thss.github.ejb.listener.ThssEJBMapInit.init(ThssEJBMapInit.java:58)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.BeanCallbackInterceptor.intercept(InterceptorManager.java:1022)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.CallbackInvocationContext.proceed(CallbackInvocationContext.java:204)
		at com.sun.ejb.containers.interceptors.SystemInterceptorProxy.doCall(SystemInterceptorProxy.java:163)
		at com.sun.ejb.containers.interceptors.SystemInterceptorProxy.init(SystemInterceptorProxy.java:125)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.CallbackInterceptor.intercept(InterceptorManager.java:978)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.CallbackInvocationContext.proceed(CallbackInvocationContext.java:204)
		at org.jboss.weld.module.ejb.AbstractEJBRequestScopeActivationInterceptor.aroundInvoke(AbstractEJBRequestScopeActivationInterceptor.java:81)
		at org.jboss.weld.module.ejb.SessionBeanInterceptor.aroundInvoke(SessionBeanInterceptor.java:52)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.base/java.lang.reflect.Method.invoke(Method.java:566)
		at com.sun.ejb.containers.interceptors.CallbackInterceptor.intercept(InterceptorManager.java:978)
		at com.sun.ejb.containers.interceptors.CallbackChainImpl.invokeNext(CallbackChainImpl.java:72)
		at com.sun.ejb.containers.interceptors.InterceptorManager.intercept(InterceptorManager.java:418)
		at com.sun.ejb.containers.interceptors.InterceptorManager.intercept(InterceptorManager.java:381)
		at com.sun.ejb.containers.BaseContainer.intercept(BaseContainer.java:2062)
		at com.sun.ejb.containers.AbstractSingletonContainer.createSingletonEJB(AbstractSingletonContainer.java:529)
		at com.sun.ejb.containers.AbstractSingletonContainer.access$000(AbstractSingletonContainer.java:81)
		at com.sun.ejb.containers.AbstractSingletonContainer$SingletonContextFactory.create(AbstractSingletonContainer.java:687)
		at com.sun.ejb.containers.AbstractSingletonContainer.instantiateSingletonInstance(AbstractSingletonContainer.java:421)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.initializeSingleton(SingletonLifeCycleManager.java:219)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.initializeSingleton(SingletonLifeCycleManager.java:180)
		at org.glassfish.ejb.startup.SingletonLifeCycleManager.doStartup(SingletonLifeCycleManager.java:159)
		at org.glassfish.ejb.startup.EjbApplication.start(EjbApplication.java:171)
		at org.glassfish.internal.data.EngineRef.start(EngineRef.java:123)
		at org.glassfish.internal.data.ModuleInfo.start(ModuleInfo.java:293)
		at org.glassfish.internal.data.ApplicationInfo.start(ApplicationInfo.java:364)
		at com.sun.enterprise.v3.server.ApplicationLifecycle.initialize(ApplicationLifecycle.java:623)
		at com.sun.enterprise.v3.server.ApplicationLoaderService.postConstruct(ApplicationLoaderService.java:335)
		at org.jvnet.hk2.internal.ClazzCreator.postConstructMe(ClazzCreator.java:303)
		at org.jvnet.hk2.internal.ClazzCreator.create(ClazzCreator.java:351)
		at org.jvnet.hk2.internal.SystemDescriptor.create(SystemDescriptor.java:463)
		at org.glassfish.hk2.runlevel.internal.AsyncRunLevelContext.findOrCreate(AsyncRunLevelContext.java:281)
		at org.glassfish.hk2.runlevel.RunLevelContext.findOrCreate(RunLevelContext.java:65)
		at org.jvnet.hk2.internal.Utilities.createService(Utilities.java:2102)
		at org.jvnet.hk2.internal.ServiceHandleImpl.getService(ServiceHandleImpl.java:93)
		at org.jvnet.hk2.internal.ServiceHandleImpl.getService(ServiceHandleImpl.java:67)
		at org.glassfish.hk2.runlevel.internal.CurrentTaskFuture$QueueRunner.oneJob(CurrentTaskFuture.java:1213)
		at org.glassfish.hk2.runlevel.internal.CurrentTaskFuture$QueueRunner.run(CurrentTaskFuture.java:1144)
		at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
		at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
		at java.base/java.lang.Thread.run(Thread.java:834)
		]]
		
      - DAS server error as:
		[2021-07-22T08:25:29.388+1000] [Payara 5.2021.4] [INFO] [] [fish.payara.nucleus.hazelcast.PayaraHazelcastTenant] [tid: _ThreadID=41 _ThreadName=hz.festive_kirch.partition-operation.thread-0] [timeMillis: 1626906329388] [levelValue: 800] [[
		BLOCKED: tenant not available: ThssEEApp_ThssEEApp_thssMapInit_JsfEeHzPgApp-EJB-thssMapInit106617283483815141, module ThssEEApp, Operation: com.hazelcast.map.impl.operation.IsKeyLoadFinishedOperation]]

		[2021-07-22T08:25:39.608+1000] [Payara 5.2021.4] [INFO] [] [fish.payara.nucleus.hazelcast.PayaraHazelcastTenant] [tid: _ThreadID=41 _ThreadName=hz.festive_kirch.partition-operation.thread-0] [timeMillis: 1626906339608] [levelValue: 800] [[
		BLOCKED: tenant not available: ThssEEApp_ThssEEApp_thssMapInit_JsfEeHzPgApp-EJB-thssMapInit106617283483815141, module ThssEEApp, Operation: com.hazelcast.map.impl.operation.IsKeyLoadFinishedOperation]]
		
	  - restart EJB server instance (from admin_ui)
		- EJB server restart successfully (apply map access works fine)
		- check server instances from Admin_ui
		  - DAS server dead.
		  - A while later EJB server instance dead as well.