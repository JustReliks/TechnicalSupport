package org.TechnicalSupport.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {

    private Long id;

    private String username;

    private Set<String> roles;
}