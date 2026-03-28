package com.smartcomplaint.controller;

import com.smartcomplaint.entity.Complaint;
import com.smartcomplaint.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;   // ✅ added import

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // ✅ Submit complaint linked with JWT user (JSON body)
    @PostMapping("/submit")
    public ResponseEntity<Complaint> submitComplaint(@Valid @RequestBody Complaint complaint) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Complaint savedComplaint = complaintService.submitComplaint(complaint, email);
        return ResponseEntity.ok(savedComplaint);
    }

    // ✅ Submit complaint with file upload (image/video)
    @PostMapping("/submit-with-file")
    public ResponseEntity<Complaint> submitComplaintWithFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("priority") String priority,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Complaint complaint = new Complaint();
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setCategory(category);
        complaint.setPriority(priority);
        complaint.setStatus("Pending");

        // ✅ Handle file upload
        if (file != null && !file.isEmpty()) {
            String filePath = "uploads/" + file.getOriginalFilename();
            try {
                file.transferTo(new File(filePath));
                complaint.setMediaUrl(filePath);   // DB me save hoga
            } catch (Exception e) {
                throw new RuntimeException("File upload failed", e);
            }
        }

        Complaint savedComplaint = complaintService.submitComplaint(complaint, email);
        return ResponseEntity.ok(savedComplaint);
    }

    // ✅ Get all complaints
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    // ✅ Update complaint status (Admin/Officer only)
    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Complaint updatedComplaint = complaintService.updateComplaintStatus(id, status);
        return ResponseEntity.ok(updatedComplaint);
    }
}
