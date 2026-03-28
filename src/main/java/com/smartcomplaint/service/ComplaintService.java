package com.smartcomplaint.service;

import com.smartcomplaint.entity.Complaint;
import com.smartcomplaint.entity.User;
import com.smartcomplaint.repository.ComplaintRepository;
import com.smartcomplaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Submit complaint linked with user (JWT email)
    public Complaint submitComplaint(Complaint complaint, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        complaint.setUser(user);          // link complaint with user
        complaint.setStatus("Pending");   // default status
        return complaintRepository.save(complaint);
    }

    // ✅ Get all complaints
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // ✅ Update complaint status
    public Complaint updateComplaintStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}
