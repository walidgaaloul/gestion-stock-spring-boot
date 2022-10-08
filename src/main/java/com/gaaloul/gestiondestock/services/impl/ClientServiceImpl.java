package com.gaaloul.gestiondestock.services.impl;

import com.gaaloul.gestiondestock.dto.ClientDto;
import com.gaaloul.gestiondestock.dto.CommandeClientDto;
import com.gaaloul.gestiondestock.exception.EntityNotFoundException;
import com.gaaloul.gestiondestock.exception.ErrorCodes;
import com.gaaloul.gestiondestock.exception.InvalideEntityException;
import com.gaaloul.gestiondestock.exception.InvalideOperationException;
import com.gaaloul.gestiondestock.model.CommandeClient;
import com.gaaloul.gestiondestock.repository.ClientRepository;
import com.gaaloul.gestiondestock.repository.CommandeClientRepository;
import com.gaaloul.gestiondestock.services.ClientService;
import com.gaaloul.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;


    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository) {
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
           log.error("client is not valid {}",dto);
           throw new InvalideEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID,errors);
        }
        return ClientDto.fromEntity(clientRepository.save(ClientDto.toEntity(dto)));

    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null){
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id).map(ClientDto::fromEntity).orElseThrow(()->
                new EntityNotFoundException("Aucun client avec ID"+id+"n'as été trouver dans DB",ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(ClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            return;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if (!commandeClients.isEmpty()){
            throw new InvalideOperationException("impossible de supprimer ceClient deja utiliser ",ErrorCodes.CLIENT_ALREADY_IN_USE);

        }
        clientRepository.deleteById(id);

    }
}
