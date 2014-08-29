package com.zolegus.samples.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by zolegus on 22.08.2014.
 */
@Path("hello")
public class HelloWorldResource {

    @GET
    @Path("text")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayhello() {
        return "text_plain";
    }

    @GET
    @Path("xml")
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> text_xml" + "</hello>";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "text_html" + "</title>"
                + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }
}