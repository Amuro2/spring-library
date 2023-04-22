package fr.amurotakahashi.cefimtestcda2.controllers;

import fr.amurotakahashi.cefimtestcda2.entities.Client;
import fr.amurotakahashi.cefimtestcda2.services.ClientService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(clientService.getClientById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public List<Client> getClientsByLastName(@RequestParam String lastName) {
        return clientService.getClientsByLastName(lastName);
    }

    @PostMapping
    public ResponseEntity<Client> postClient(@RequestBody @Valid Client client) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.postClient(client));
        } catch(EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(clientService.getClient(client));
        }
    }

    @PatchMapping("/{id}/lastName")
    public ResponseEntity<Client> patchClientLastName(@PathVariable Integer id, @RequestBody String lastName) {
        try {
            return ResponseEntity.ok(clientService.patchClientLastName(id, lastName));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> putClient(@PathVariable Integer id, @RequestBody @Valid Client client) {
        try {
            return ResponseEntity.ok(clientService.putClient(id, client));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClientById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(clientService.deleteClientById(id));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
