package com.salon_beaute.model.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String email;

    private String currentPassword;

    private String newPassword;
}
