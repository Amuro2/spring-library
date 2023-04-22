package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Client;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidParameterException;
import java.util.List;

@SpringBootTest
@Transactional
public class ClientServiceTests {
    @Autowired
    private ClientService clientService;

    @Test
    void testGetAllClients() {
        List<Client> clients = clientService.getAllClients();

        System.out.println(clients);

    }

    @Test
    void testGetClientById() {
        int id = 1;
        Client client = clientService.getClientById(id);

        System.out.println(client);

        assert client != null;

    }

    @Test
    void testGetClientByIdNotExists() {
        int id = -9999;

        try {
            clientService.getClientById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testPostClient() {
        long clientsCount = clientService.countClients();

        Client client = new Client();

        clientService.postClient(client);

        assert clientService.countClients() == clientsCount + 1;

    }

    @Test
    void testPostClientAlreadyExists() {
        Client client = clientService.getClientById(1);

        try {
            clientService.postClient(client);

            assert false;
        } catch(EntityExistsException e) {
            assert true;
        }
    }

    @Test
    void testPostClientBadRequest() {
        try {
            clientService.postClient(null);

            assert false;
        } catch(InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void testPutClient() {
        int id = 1;
        Client client = new Client();

        clientService.putClient(id, client);


    }

    @Test
    void testPutClientNotExists() {
        int id = -9999;
        Client client = new Client();

        try {
            clientService.putClient(id, client);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testPutClientBadRequest() {
        int id = 1;

        try {
            clientService.putClient(id, null);

            assert false;
        } catch(InvalidParameterException e) {
            assert true;
        }
    }

    @Test
    void testDeleteClient() {
        int id = 1;

        long clientsCount = clientService.countClients();

        clientService.deleteClientById(id);

        assert clientService.countClients() == clientsCount - 1;

        try {
            clientService.getClientById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testDeleteClientNotExists() {
        int id = -9999;

        try {
            clientService.deleteClientById(id);

            assert false;
        } catch(EntityNotFoundException e) {
            assert true;
        }
    }

}
