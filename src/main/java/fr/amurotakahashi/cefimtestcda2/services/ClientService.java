package fr.amurotakahashi.cefimtestcda2.services;

import fr.amurotakahashi.cefimtestcda2.entities.Client;
import fr.amurotakahashi.cefimtestcda2.repositories.ClientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EntityManager entityManager;

    public long countClients() {
        return clientRepository.count();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Integer id) {
        return clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Client getClient(Client client) {
        if(client == null) {
            throw new InvalidParameterException("Client must not be null");
        }

        return getClientById(client.getId());
    }

    public List<Client> getClientsByLastName(String lastName) {
        return clientRepository.findByLastName(lastName);
    }

    public Client postClient(Client client) {
        if(client == null) {
            throw new InvalidParameterException("Client must not be null");
        }

        if(client.getId() == null) {
            return clientRepository.save(client);
        } else {
            Optional<Client> optionalClient = clientRepository.findById(client.getId());

            if(optionalClient.isEmpty()) {
                return clientRepository.save(client);
            } else {
                throw new EntityExistsException();
            }
        }
    }

    public Client patchClientLastName(Integer id, String lastName) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();

            client.setLastName(lastName);

            return clientRepository.save(client);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Client putClient(Integer id, Client client) {
        if(client == null) {
            throw new InvalidParameterException("Client must not be null");
        }

        client.setId(id);

        Optional<Client> optionalClient = clientRepository.findById(id);

        if(optionalClient.isPresent()) {
            return clientRepository.save(client);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public Client deleteClientById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if(optionalClient.isPresent()) {
            clientRepository.deleteById(id);

            return optionalClient.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    ////  ////

    public Map<String, Integer> getBestGenreForClient(Integer clientId) {
        List<Tuple> tuples = entityManager.createNativeQuery("""
                SELECT genre.name AS genre_name, count(book.*) AS reservations_count
                FROM client
                INNER JOIN reservation ON client.id = reservation.client_id
                INNER JOIN reservation_book ON reservation.id = reservation_book.reservation_id
                INNER JOIN book ON book.id = reservation_book.book_id
                INNER JOIN genre ON genre.id = book.genre_id
                WHERE client.id = :clientId
                GROUP BY genre.name;
                """, Tuple.class)
                .setParameter("clientId", clientId)
                .getResultList();

        Map<String, Integer> map = new HashMap<>();

        for(Tuple tuple : tuples) {
            map.put(tuple.get("genre_name", String.class), tuple.get("reservations_count", Integer.class));
        }

        return map;
    }
}
