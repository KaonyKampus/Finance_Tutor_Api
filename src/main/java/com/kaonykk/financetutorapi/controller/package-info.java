/*
The controller logic:
The controller receives the JSON from the app (front end), converts it into a DTO and sends it to the service, which validates it according to the business rules. If something does not meet the rules, the service throws an exception, which the controller converts into an error response. If everything is correct, the service converts the DTO into an entity and asks the DAO to retrieve or save information in the database. The response travels back through the same layers and is returned to the app as JSON.
 */
package com.kaonykk.financetutorapi.controller;
