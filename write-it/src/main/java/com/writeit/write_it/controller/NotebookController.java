package com.writeit.write_it.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.dto.request.notebook_note_version.CreateANotebookRequest;
import com.writeit.write_it.dto.response.Response;
import com.writeit.write_it.security.userdetails.CustomUserDetails;
import com.writeit.write_it.service.notebook.NotebookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {
    private final NotebookService notebookService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<AllNotebooksDTO>> getAllNotebooks(
            @AuthenticationPrincipal CustomUserDetails user) {

        AllNotebooksDTO response = notebookService.getAllNotebooksByUserId(user.getId());
        return ResponseEntity.ok(Response.ok(response, "Notebooks fetched successfully!"));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<Object>> createANotebook(@RequestBody @Valid CreateANotebookRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        notebookService.createANotebook(request, user.getId());
        return ResponseEntity.ok(Response.ok(null, "Notebook created successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Object>> updateANotebook(@PathVariable Long id, @RequestBody String notebookName) {
        notebookService.updateANotebook(id, notebookName);
        return ResponseEntity.ok(Response.ok(null, "Notebook updated successfully!"));
    }
}
