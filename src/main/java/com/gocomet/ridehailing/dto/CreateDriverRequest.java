package com.gocomet.ridehailing.dto;

import lombok.Data;

@Data
public class CreateDriverRequest {
    private Long id;
    private String name;
    private String phoneNumber;
}