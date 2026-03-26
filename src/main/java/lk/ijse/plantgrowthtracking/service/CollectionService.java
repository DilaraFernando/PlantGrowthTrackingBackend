package lk.ijse.plantgrowthtracking.service;

import lk.ijse.plantgrowthtracking.dto.CollectionCreateRequest;
import lk.ijse.plantgrowthtracking.dto.CollectionResponse;
import java.util.List;

public interface CollectionService {
    CollectionResponse createCollection(CollectionCreateRequest dto, String email);
    List<CollectionResponse> getMyCollections(String email);
    void deleteCollection(Long id, String email);
}
