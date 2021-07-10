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
	
