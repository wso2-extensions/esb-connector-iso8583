Product: Integration tests for WSO2 ESB ISO8583 connector

Pre-requisites:

 - Maven 3.x
 - Java 1.8

Tested Platform: 

 - Ubuntu 14.04
 - WSO2 EI 6.4.0
 - Java 1.8

Steps to follow in setting integration test.

 1. Download EI 6.4.0 from the official website

 2. Download the jpos-1.9.4.jar from the http://mvnrepository.com/artifact/org.jpos/jpos/1.9.4 ,<br/>
    download jdom-1.1.3.jar from http://mvnrepository.com/artifact/org.jdom/jdom/1.1.3 and <br/>
    download commons-cli-1.3.1.jar from http://mvnrepository.com/artifact/commons-cli/commons-cli/1.3 and <br/> then copy the
    jars to the <ESB_HOME>/repository/components/lib or <EI_HOME>/lib directory.

 3. Compress modified EI as wso2ei-6.4.0.zip and copy that zip file in to location "{ISO8583_CONNECTOR_HOME}/repository/".

 4. Obtain the Sample test server from https://github.com/wso2-docs/CONNECTORS/tree/master/ISO8583/ISO8583TestServer.

    Clone the JavaTestServer from above link and to run the test server follow the below instructions:

    1.  Add following jars as an external jars in java build path.
       - jpos-1.9.0.jar
       - log4j-1.2.17.jar
       - commons-cli-1.3.1.jar
       - jdom-1.1.3.jar
       - org.osgi.core-4.3.0.jar

    2.  Update the relative path in log4j.properties for the property "log4j.appender.R.File".

    3.  Run the main class (mockServer.java)
 
 5. Update the iso8583 (esb-connector-iso8583.properties) properties file at location "{ISO8583_HOME}/repository/" as below.
   
        i)      serverHost   - Here the host is localhost
        ii)     serverPort   - Here the port is 5010 , The above JavaTestserver will start to listen on that port.

   Note :   If you wanted to change the above host and port , then you should change the relevant properties in
            JavaTestServer as well.

 6. Navigate to "<ISO8583_HOME>/" and run the following command.
 
        $ mvn clean install -Dskip-tests=false
