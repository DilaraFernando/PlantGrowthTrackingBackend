package lk.ijse.plantgrowthtracking.service.impl;

import lk.ijse.plantgrowthtracking.dto.CollectionCreateRequest;
import lk.ijse.plantgrowthtracking.dto.CollectionResponse;
import lk.ijse.plantgrowthtracking.entity.PlantCollection;
import lk.ijse.plantgrowthtracking.entity.User;
import lk.ijse.plantgrowthtracking.repository.PlantCollectionRepository;
import lk.ijse.plantgrowthtracking.repository.UserRepository;
import lk.ijse.plantgrowthtracking.service.CollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {
    private final PlantCollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public CollectionServiceImpl(PlantCollectionRepository collectionRepository, UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CollectionResponse createCollection(CollectionCreateRequest dto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (collectionRepository.existsByNameAndOwner(dto.getName(), user)) {
            throw new lk.ijse.plantgrowthtracking.exception.AlreadyExistsException("Collection name already exists");
        }
        PlantCollection collection = PlantCollection.builder()
            .name(dto.getName())
            .category(dto.getCategoryEnum())
            .location(dto.getLocation())
            .owner(user)
            .build();
        PlantCollection saved = collectionRepository.save(collection);
        return CollectionResponse.fromEntity(saved);
    }

    @Override
    public List<CollectionResponse> getMyCollections(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return collectionRepository.findByOwner(user).stream()
                .map(CollectionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCollection(Long id, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        PlantCollection collection = collectionRepository.findById(id).orElseThrow();
        if (!collection.getOwner().getId().equals(user.getId())) {
            throw new SecurityException("Not authorized");
        }
        collectionRepository.delete(collection);
    }
}
