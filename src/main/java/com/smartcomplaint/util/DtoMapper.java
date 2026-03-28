package com.smartcomplaint.util;

import com.smartcomplaint.dto.UserDto;
import com.smartcomplaint.dto.ComplaintDto;
import com.smartcomplaint.entity.User;
import com.smartcomplaint.entity.Complaint;

public class DtoMapper {

    // ✅ Convert User entity → UserDto
    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());

        // role is String in User entity
        dto.setRole(user.getRole());

        return dto;
    }

    // ✅ Convert Complaint entity → ComplaintDto
    public static ComplaintDto toComplaintDto(Complaint complaint) {
        ComplaintDto dto = new ComplaintDto();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setCategory(complaint.getCategory());
        dto.setPriority(complaint.getPriority());
        dto.setStatus(complaint.getStatus());
        dto.setMediaUrl(complaint.getMediaUrl());
        dto.setCreatedAt(complaint.getCreatedAt());

        // include user info if complaint is linked
        if (complaint.getUser() != null) {
            dto.setUserEmail(complaint.getUser().getEmail());
            dto.setUserName(complaint.getUser().getName());
        }

        return dto;
    }
}
