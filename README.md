# ISO8583 WSO2 EI Connector

The ISO8583 [Connector](https://docs.wso2.com/display/EI650/Working+with+Connectors) allows you to send ISO8583 standard messages through the ESB Profile of WSO2 Enterprise Integrator (WSO2 EI). ISO8583 is an international messaging standard for financial transaction card originated messages, and is commonly used in transactions between devices such as point-of-sale(POS) terminals and automated teller machines(ATMs).
Although there are various versions of the ISO8583 standard, this connector is developed based on the 1987 version. For more information on the ISO8583 standard, see [ISO8583 Documentation](https://en.wikipedia.org/wiki/ISO_8583).

## Compatibility

| Connector version | Supported jpos library version | Supported WSO2 ESB/EI version |
| ------------- | ---------------|------------- |
| [1.0.3](https://github.com/wso2-extensions/esb-connector-iso8583/tree/org.wso2.carbon.connector.iso8583-1.0.3) | 1.9.4 | EI 6.5.0    |
| [1.0.2](https://github.com/wso2-extensions/esb-connector-iso8583/tree/org.wso2.carbon.connector.iso8583-1.0.2) | 1.9.4 | EI 6.1.1, EI 6.3.0, EI 6.4.0    |


## Getting started

#### Download and install the connector

1. Download the connector from the [WSO2 Store](https://store.wso2.com/store/assets/esbconnector/details/e4cf3fd5-445f-4317-beb6-09998906fb0d) by clicking the **Download Connector** button.
2. You can then follow this [documentation](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+the+Management+Console) to add the connector to your WSO2 EI instance and to enable it (via the management console).
3. For more information on using connectors and their operations in your WSO2 EI configurations, see [Using a Connector](https://docs.wso2.com/display/EI650/Using+a+Connector).
4. If you want to work with connectors via WSO2 EI Tooling, see [Working with Connectors via Tooling](https://docs.wso2.com/display/EI650/Working+with+Connectors+via+Tooling).

#### Configuring the connector operations

To get started with the **ISO8583** connector and their operations, see [Configuring ISO8583 Operations](docs/config.md).

## Building from the source

Follow the steps given below to build the ISO8583 connector from the source code.

1. Get a clone or download the source from [Github](https://github.com/wso2-extensions/esb-connector-iso8583).
2. Run the following Maven command from the `esb-connector-iso8583` directory: `mvn clean install`.
3. The ZIP file with the ISO8583 connector is created in the `esb-connector-iso8583/target` directory.

## How you can contribute

As an open source project, WSO2 extensions welcome contributions from the community.
Check the [issue tracker](https://github.com/wso2-extensions/esb-connector-iso8583/issues) for open issues that interest you. We look forward to receiving your contributions.
