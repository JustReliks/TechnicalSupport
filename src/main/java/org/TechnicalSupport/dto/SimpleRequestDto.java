package org.TechnicalSupport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRequestDto {

    private String name;
    private String message;
    private String phoneNumber;

}
