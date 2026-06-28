package com.amanat.backend;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= SIGNUP =================
    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {

            Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered!");
            }

            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);

            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully with ID: " + savedUser.getId());

            return ResponseEntity.ok("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        String password = request.get("password");

        Optional<User> optionalUser = userRepository.findByEmail(email);

        // ❌ Java 8 me isEmpty() nahi hota
        if (!optionalUser.isPresent()) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // ✅ Dynamic Name Return (Java 8 Safe)
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("name", user.getName());

        return ResponseEntity.ok(response);
    }
}
