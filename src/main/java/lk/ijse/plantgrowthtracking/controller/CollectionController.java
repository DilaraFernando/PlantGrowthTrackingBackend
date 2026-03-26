package lk.ijse.plantgrowthtracking.controller;

import jakarta.validation.Valid;
import lk.ijse.plantgrowthtracking.dto.CollectionCreateRequest;
import lk.ijse.plantgrowthtracking.dto.CollectionResponse;
import lk.ijse.plantgrowthtracking.service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@Valid @RequestBody CollectionCreateRequest request, Authentication authentication) {
        String email = authentication.getName();
        CollectionResponse response = collectionService.createCollection(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponse>> getMyCollections(Authentication authentication) {
        String email = authentication.getName();
        List<CollectionResponse> collections = collectionService.getMyCollections(email);
        return ResponseEntity.ok(collections);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        collectionService.deleteCollection(id, email);
        return ResponseEntity.ok().build();
    }
}
