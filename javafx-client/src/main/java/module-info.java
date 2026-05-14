 module com.gindho.client {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.web;
     requires com.fasterxml.jackson.databind;
     requires org.slf4j;
     requires java.net.http;

     opens com.gindho.client to javafx.fxml;
     opens com.gindho.client.controller to javafx.fxml, com.fasterxml.jackson.databind;
     opens com.gindho.client.model to com.fasterxml.jackson.databind;

     exports com.gindho.client;
     exports com.gindho.client.controller;
     exports com.gindho.client.model;
     exports com.gindho.client.service;
 }