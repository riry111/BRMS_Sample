# BRMS_Sample

## Install order-server-side
    $ cd ./BRMS_Sample/order-server-side
    $ mvn package jar:jar install

## Install order-rules
    cd ./BRMS_Sample/order-rules
    mvn install

## Deploy order-server-side.war
* Deploy order-server-side/target/order-server-side.war  (ex: jboss-eap-6.4/standalone/deployments)


## Execute Rule by Sample Client
* Sample EJB Client 
 - order-client/src/com/example/order/client/EJBClient.java
* Sample REST Client
 - order-client/src/com/example/order/client/RestClient.java
