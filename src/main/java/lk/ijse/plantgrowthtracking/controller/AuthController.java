package lk.ijse.plantgrowthtracking.controller;

import jakarta.validation.Valid;
import lk.ijse.plantgrowthtracking.dto.AuthRequest;
import lk.ijse.plantgrowthtracking.dto.AuthResponse;
import lk.ijse.plantgrowthtracking.dto.RegisterRequest;
import lk.ijse.plantgrowthtracking.service.AuthService;
import lk.ijse.plantgrowthtracking.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<APIResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
		authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new APIResponse<>(201, "Registration successful", null));
	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
		AuthResponse response = authService.authenticate(request);
		return ResponseEntity.ok(new APIResponse<>(200, "Login successful", response));
	}

	@PostMapping("/logout")
	public ResponseEntity<APIResponse<Void>> logout() {
		return ResponseEntity.ok(new APIResponse<>(200, "Logged out", null));
	}
}
