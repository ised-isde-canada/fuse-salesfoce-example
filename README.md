# Initial Setup
- Setup Java Key Store - To connect to Salesforce you will need to create a Java Key Store locally on your computer and install the certificate that the Salesforce admin provides you for you connect app.
- Setup env variables. For simplicity env variables are configured in the JwtOAuthAuthentication java file as default values. Update the default values with your project specific values
	
	@Value("${sf-sts-connected-app-consumer-key}") // added default 
	private String SF_CONNECTED_APP_CONSUMER_KEY;
	
	@Value("${sf-sts-connected-app-username:integration.user@ised.myorg}") // added default 
	private String SF_CONNECTED_APP_USERNAME;
	
	@Value("${sf-login-url-key:https://test.salesforce.com}") // added default 
	private String SF_LOGIN_URL;
	
	@Value("${sf-sts-connected-app-certificate-name:ca.ised.integration.salesforce}") // Keystore name
	private String SF_CONNECTED_APP_CERTIFICATE_NAME;
	
	@Value("${sf-sts-connected-app-certificate-password:SomePassword}") // added default 
	private String SF_CONNECTED_APP_CERTIFICATE_PASSWORD;
	
	@Value("${java-keystore-path:C:/Users/ISED/keystore.jks}") // Keystore location
	private String JAVA_KEYSTORE_PATH;
	
	@Value("${java-keystore-password:password}") // Keystore password
	private String JAVA_KEYSTORE_PASSWORD;
	
	@Value("${sf-sts-endpoint-domain:ised.myorg.salesforce.com}") // added default 
	private String SF_ENDPOINT_DOMAIN;

- Now that your app has been configured let's test it out. Run Application. Java's main method and see the app standup. If everything worked you should be able to call the below API in your new app. This example has a read and write route but let's have a look at the read route to make sure everything is working. On the cmd line ...

	curl http://localhost:8181/cases/list


----------------------------------------------------

The below text was generateed on project creation.

----------------------------------------------------




# Spring-Boot Camel QuickStart

This example demonstrates how you can use Apache Camel with Spring Boot.

The quickstart uses Spring Boot to configure a little application that includes a Camel route that triggers a message every 5th second, and routes the message to a log.

### Building

The example can be built with

    mvn clean install

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)

The example can be built and run on OpenShift using a single goal:

    mvn fabric8:deploy

When the example runs in OpenShift, you can use the OpenShift client tool to inspect the status

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the
running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quickstart template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-camel-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 

