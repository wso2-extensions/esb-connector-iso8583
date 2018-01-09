# Configuring ISO8583 Connector Operation

ISO8583 connector allows you to send the ISO8583 standard messages through WSO2 ESB. ISO8583 is a message standard which is using in financial transactions. There are various versions in ISO8583 standard, Here the connector is developed based on 1987 version.

For more information about ISO8583 Standard, go to [ISO Documentation](http://www.iso.org/iso/home.html) and  [ISO8583 Documentation](https://en.wikipedia.org/wiki/ISO_8583) . WSO2 ESB ISO8583 connector sends ISO8583 Standard Messages to java Testserver .

In order to use the ISO8583 connector, you need to download the jpos-1.9.4.jar from the http://mvnrepository.com/artifact/org.jpos/jpos/1.9.4 , download jdom-1.1.3.jar from http://mvnrepository.com/artifact/org.jdom/jdom/1.1.3 and download commons-cli-1.3.1.jar from http://mvnrepository.com/artifact/commons-cli/commons-cli/1.3.1. Then copy the jars to the <EI_HOME>/lib directory. The recommend EI version is 6.1.1 .

To use the ISO8583 connector, add the <iso8583.init> element in your configuration before connecting with Testserver.

**init**
```xml
<iso8583.init>
       <serverHost>localhost</serverHost>
       <serverPort>5010</serverPort>
</iso8583.init>
```
**Properties** 
* serverHost: The host of the server.
* serverPort: The port of the server (The server will start to listen on that port).

Now you have connected to ISO8583 Connector and with any Testserver, by using the following information you can send the messages from connector.

**Send an ISO8583 Messages**

To send the messages, use </iso8583.sendMessage> operation and using Rest-client to send the xml format messages. In Rest-client set the header application/xml as Content-Type.

POST the body in xml format and xml format message should be in the following structure.

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
>> NOTE : ISOMessage can have header information. It support 2-byte or 4-byte headers. 
To add the header to ISOMessage, you need to convert the 2-byte or 4-byte header into string using base64 encoding and give that string value within the header tag in xml message.

# Sample configuration

Following is a sample proxy service that illustrates how to connect to Testserver with the init operation to use the sendMessage operation. 

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
>> NOTE : The jpos library is a third party library and jposdef.xml has the field definitions of standard ISO8583 messages. According to the field definitions, each ISO8583 message in XML format that comes from the Rest client will be packed and sent to the Testserver.

>> NOTE : For testing purposes, you need to have Java TestServer (which is a java socket connection that needs to listen on port 5010) to handle ISO8583 requests that come from the connector. Further, it needs to generate responses by changing the relevant response fields and sending the responses back to the connector.
You can try the connector with the sample Java server program that is provided in https://github.com/wso2-docs/CONNECTORS/tree/master/ISO8583/ISO8583TestServer (If You send the ISO8583 request with header value from the connector, Update the iso87ascii.xml file with relevant headerLength in the sample Java server program).
