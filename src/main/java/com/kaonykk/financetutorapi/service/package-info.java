/*
 * This package contains the business logic. It receives DTOs from the controller,
 * validates them against the business rules, and throws an exception when a rule
 * is not met. If everything is correct, it converts the DTOs into entities and
 * asks the DAO to retrieve or save data.
 * It does not know about HTTP, JSON, or SQL.
 */

package com.kaonykk.financetutorapi.service;



