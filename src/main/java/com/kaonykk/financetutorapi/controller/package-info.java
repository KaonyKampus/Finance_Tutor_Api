/*
 * This package contains the servlets that receive HTTP requests from the app.
 * They convert the incoming JSON into DTOs, call the service layer, and return
 * the result as JSON with the proper HTTP status code. When the service throws
 * an exception, the controller catches it and converts it into an error response.
 * It does not apply business rules or access the database.
 */
package com.kaonykk.financetutorapi.controller;
