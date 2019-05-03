# Configuring the ISO8583 Connector

To configure the ISO8583 connector, download the following .jar files from the given locations and copy the files to the <EI_HOME>/lib directory:

* Download jpos-1.9.4.jar from [http://mvnrepository.com/artifact/org.jpos/jpos/1.9.4](http://mvnrepository.com/artifact/org.jpos/jpos/1.9.4). 
* Download jdom-1.1.3.jar from [http://mvnrepository.com/artifact/org.jdom/jdom/1.1.3](http://mvnrepository.com/artifact/org.jdom/jdom/1.1.3).
* Download commons-cli-1.3.1.jar from [http://mvnrepository.com/artifact/commons-cli/commons-cli/1.3.1](http://mvnrepository.com/artifact/commons-cli/commons-cli/1.3.1). 

To use the ISO8583 connector, add the <iso8583.init> element in your configuration before carrying out any other operation.

>> NOTE : For testing purposes, you need to have a test server (basically a java socket connection that listens on port 5010) to handle ISO8583 requests that come from the connector. You also need to generate responses by changing the relevant response fields, and then send the responses back to the connector.
You can test the connector with the sample Java server program that is provided in the following git location: 
https://github.com/wso2-docs/CONNECTORS/tree/master/ISO8583/ISO8583TestServer

**init**
```xml
<iso8583.init>
       <serverHost>localhost</serverHost>
       <serverPort>5010</serverPort>
</iso8583.init>
```
**Properties** 
* serverHost: The host of the server.
* serverPort: The port on which the server can start listening for messages.

Now that you have connected to the ISO8583 connector, take a look at the information in the following sections to start working with ISO8583 messages using the connector.

### Sending an ISO8583 message

To send an ISO8583 message, you need to use the <iso8583.sendMessage/> operation in your configuration.
You can use a REST client to send an XML message, set the Content-Type header in the REST client as application/xml, and then POST the body in XML format. 

Messages that you send should be structured in the following XML format:

```xml
<ISOMessage>
      <header>AAAAaw==</header>
      <data>
        <field id="0">0200</field>
        <field id="3">568893</field>
        <field id="4">000000020000</field>
        <field id="7">0110563280</field>
        <field id="11">456893</field>
        <field id="44">DFGHT</field>
        <field id="105">ABCDEFGHIJ 9871236548</field>
      </data>
</ISOMessage>
```
>> NOTE: You can include required header information within the header tag in <ISOMessage>. It supports 2-byte or 4-byte headers. 
To include header information, you need to convert the 2-byte or 4-byte header into a string using base64 encoding, and then specify the string value within the header tag.

>> NOTE : If you use the sample Java server program at https://github.com/wso2-docs/CONNECTORS/tree/master/ISO8583/ISO8583TestServer to send an ISO8583 request with a header value from the connector, you need to update the iso87ascii.xml file with the relevant headerLength information.

## Sample configuration

Following is a sample proxy service that illustrates how to connect to a test server with the init operation, and then use the sendMessage operation. 

**Sample Proxy**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<proxy xmlns="http://ws.apache.org/ns/synapse"
       name="ISO8583_Test"
       transports="https,http"
       statistics="disable"
       trace="disable"
       startOnLoad="true">
   <target>
      <inSequence>
         <iso8583.init>
            <serverHost>localhost</serverHost>
            <serverPort>5010</serverPort>
         </iso8583.init>
         <iso8583.sendMessage/>
         <respond/>
      </inSequence>
      <outSequence>
         <log/>
         <send/>
      </outSequence>
   </target>
   <description/>
</proxy>               
```

>> NOTE : The ISO8583 connector uses the jpos library, which is a third party library that provides a high-performance bridge between card messages generated at point of sale terminals, ATMs, and internal systems across the entire financial messaging network. The jposdef.xml file has the field definitions of standard ISO8583 messages. According to the field definitions, each ISO8583 message in XML format coming from the REST client is packed and sent to the test server.
Therefore, you need to create a file called jposdef.xml (with the contents given [here](https://github.com/wso2-extensions/esb-connector-iso8583/blob/master/src/main/resources/jposdef.xml)) in the <EI_HOME> directory.