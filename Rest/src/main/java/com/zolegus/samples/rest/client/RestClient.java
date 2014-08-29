package com.zolegus.samples.rest.client;

import com.zolegus.samples.rest.User;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;


public class RestClient {
    public static void main(String[] args) {
        try {
            User user = new User("Bill", "Smith", 50, 101);
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);
            WebResource webResource = client
                    .resource("http://localhost:9090/JerseyJSONExample/rest/jsonServices/send");
            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, st);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            String output = response.getEntity(String.class);

            System.out.println("Server response .... \n");
            System.out.println(output);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
