package org.TechnicalSupport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.TechnicalSupport.entity.enums.RequestStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private String name;
    private String text;
    private String phoneNumber;

}
