<h2>Spring Web Service SOAP Server and Client CRUD</h2>

<p>This building block is about a simple CRUD application between a Spring WS SOAP server and client. The XML
Schema Definition(XSD) file can be found in the server resource directory, which defines the Java classes to interact
with the underlying SOAP protocol. This example consists of a server and client, each in a separate directory.</p>
<h3>CRUD operations</h3>
<p>This CRUD application manages a small customer database with operations like getAllCustomers, getCustomerById,
addCustomer, updateCustomer and deleteCustomer.</p>
<h3>Fault Exceptions</h3>
<p>The underlying SOAP protocol detects by itself errors like for example object null, when a customer does not exist
in the database during a getCustomerById operation. SOAP will generate a standard FAULT message, which can be
modified in this example by <i>CustomerNotFoundException</i> and <i>DetailSoapFaultDefinitionExceptionResolver</i>
classes independently. Note that the latter has been configured in a bean in the <i>WSConfig</i> class.</p>
<h3>Summary</h3>
<ul style="list-style-type:disc;">
  <li>Java 17</li>
  <li>MySQL</li>
  <li>Docker(for MySQL DB)</li>
  <li>Spring WS SOAP</li>
</ul>
<h3>Testing</h3>
<p>You can start up the SOAP server at localhost:8080(default) and run a curl command to test the server ie
<i>curl --header "content-type: text/xml" -d @requestId.xml http://localhost:8080/</i>, to enquire the databse 
with a getCustomerById operation.</p>
<p>You can also start up the SOAP Client/´(configured at port 8081) and enquire the database 
in the <i>XmlSoapClientApplication</i> class.</p>