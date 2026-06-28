package com.amanat.backend;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "journal_entries")

@Data
public class JournalEntry {

    @Id
    private String id;
    private String userId;  // Owner of the journal
    private String encryptedContent;  // Encrypted text

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private String title;  // Optional title

    // Tags for search
    private List<String> tags = new ArrayList<>();
}
