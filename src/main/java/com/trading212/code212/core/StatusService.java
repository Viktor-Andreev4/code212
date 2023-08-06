package com.trading212.code212.core;

import com.trading212.code212.core.models.StatusDTO;
import com.trading212.code212.repositories.StatusRepository;
import com.trading212.code212.repositories.entities.StatusEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Optional<StatusDTO> getStatusById(int id) {
        Optional<StatusEntity> status = statusRepository.getStatusById(id);
        if (status.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new StatusDTO(status.get().statusId(), status.get().name()));
    }

    public StatusDTO addStatus (StatusDTO statusDTO) {
        StatusEntity statusEntity = statusRepository.addStatus(statusDTO.getId(), statusDTO.getDescription());
        return new StatusDTO(statusEntity.statusId(), statusEntity.name());
    }
}
