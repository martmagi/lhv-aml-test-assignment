package com.assignment.matcher;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BlacklistNameController {

    private final BlacklistNameService blacklistNameService;

    public BlacklistNameController(BlacklistNameService blacklistNameService) {
        this.blacklistNameService = blacklistNameService;
    }

    @PostMapping
    public ResponseEntity<UUID> add(@RequestBody String name) {
        UUID uuid = this.blacklistNameService.add(name);
        return ResponseEntity.ok().body(uuid);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody String name) {
        this.blacklistNameService.update(id, name);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.blacklistNameService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> check(@RequestBody String name) {
        return ResponseEntity.ok().body(this.blacklistNameService.check(name));
    }
}
