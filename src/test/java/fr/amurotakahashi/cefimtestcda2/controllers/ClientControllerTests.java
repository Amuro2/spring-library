package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.ControllerTests;
import fr.amurotakahashi.cefimtestcda2.entities.Client;
import fr.amurotakahashi.cefimtestcda2.services.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ClientControllerTests extends ControllerTests {
    @Autowired
    private ClientService clientService;

    @Test
    void testGetAllClients() throws Exception {
        String contentAsString = get("/api/client/all");
        List<Client> clients = Arrays.asList(objectMapper.readValue(contentAsString, Client[].class));

        System.out.println(clients);

        assert clients.size() > 0;
    }

    @Test
    void testGetClientById() throws Exception {
        int id = 1;
        String contentAsString = get("/api/client/" + id);
        Client client = objectMapper.readValue(contentAsString, Client.class);

        assert client.getId().equals(id);
    }

    @Test
    void testGetClientByIdNotExists() throws Exception {
        request(HttpMethod.GET, "/api/client/-9999", HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetClientByName() throws Exception {
        String contentAsString = get("/api/client/search?name=");
        List<Client> clients = Arrays.asList(objectMapper.readValue(contentAsString, Client[].class));

        System.out.println(clients);
    }

    @Test
    void testPostClient() throws Exception {
        long clientsCount = clientService.countClients();

        Client client = new Client("PERSONNE", "Personne", "JOojozejgojzogorzo", -9999);

        String contentAsString = post("/api/client", client);

        System.out.println(contentAsString);

        assert clientService.countClients() == clientsCount + 1;
    }

    @Test
    void testPostClientAlreadyExists() throws Exception {
        long clientsCount = clientService.countClients();

        Client client = new Client();
        client.setId(1);

        request(HttpMethod.POST, "/api/client", client, HttpStatus.CONFLICT);
        assert clientService.countClients() == clientsCount;
    }

    @Test
    void testPostClientBadRequest() throws Exception {
        request(HttpMethod.POST, "/api/client", new StringBuilder(), HttpStatus.BAD_REQUEST);
        request(HttpMethod.POST, "/api/client", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatchClient() throws Exception {
        String lastName = "Lorem ipsum";
        String contentAsString = patch("/api/client/1/lastName", lastName);
        Client client = objectMapper.readValue(contentAsString, Client.class);

        System.out.println(contentAsString);

        assert client.getLastName().equals(lastName);
    }

    @Test
    void testPatchClientNotExists() throws Exception {
        request(HttpMethod.PATCH, "/api/client/-9999/name", "Lorem ipsum", HttpStatus.NOT_FOUND);
    }

    @Test
    void testPatchClientBadRequest() throws Exception {
        request(HttpMethod.PATCH, "/api/client/1/name", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutClient() throws Exception {
        int id = 1;
        Client client1 = clientService.getClientById(id);
        Client client = new Client();

        String contentAsString = put("/api/client/" + id, client);
        System.out.println(contentAsString);
    }

    @Test
    void testPutClientNotExists() throws Exception {
        Client client = new Client();

        request(HttpMethod.PUT, "/api/client/-9999", client, HttpStatus.NOT_FOUND);
    }

    @Test
    void testPutClientBadRequest() throws Exception {
        request(HttpMethod.PUT, "/api/client/1", new StringBuilder("uhfezihfzefhzeiogh"), HttpStatus.BAD_REQUEST);
        request(HttpMethod.PUT, "/api/client/1", null, HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteClient() throws Exception {
        long clientsCount = clientService.countClients();

        int id = 1;
        String contentAsString = delete("/api/client/" + id);
        Client client = objectMapper.readValue(contentAsString, Client.class);

        System.out.println(contentAsString);

        assert clientService.countClients() == clientsCount - 1;
        assert clientService.getClientById(id) == null;

        assert client != null;
        assert client.getId().equals(id);
    }

    @Test
    void testDeleteClientNotExists() throws Exception {
        request(HttpMethod.DELETE, "/api/client/-9999", HttpStatus.NOT_FOUND);
    }

}
