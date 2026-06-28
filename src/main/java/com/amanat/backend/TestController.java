package com.amanat.backend;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public TestController(UserRepository userRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/test-save-user")
    public String testSaveUser() {
        User user = new User();
        user.setName("Test Writer");
        user.setEmail("test@amanat.com");
        user.setGoogleId("test-google-123");

        userRepository.save(user);

        return " Test user saved! Check MongoDB → amanat → users collection.";
    }

    @GetMapping("/test-encrypt")
    public String testEncrypt() throws Exception {
        String plainText = "This is a secret private journal entry. Only I can read it!";
        String encrypted = encryptionService.encrypt(plainText, encryptionService.getDemoKey());
        String decrypted = encryptionService.decrypt(encrypted, encryptionService.getDemoKey());

        return "<h2>Encryption Test</h2>"
                + "<p><strong>Original:</strong> " + plainText + "</p>"
                + "<p><strong>Encrypted:</strong> " + encrypted + "</p>"
                + "<p><strong>Decrypted:</strong> " + decrypted + "</p>"
                + "<p>✅ Encryption works perfectly!</p>";

    }

    @GetMapping("/api/user")
    public User getLoggedInUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
}
