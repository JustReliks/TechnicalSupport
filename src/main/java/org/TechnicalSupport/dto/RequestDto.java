package org.TechnicalSupport.dto;

import lombok.*;
import org.TechnicalSupport.entity.enums.RequestStatus;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class RequestDto extends SimpleRequestDto {

    private String createdBy;
    private Instant createAt;
    private RequestStatus status;

    @Builder
    public RequestDto(String name, String phoneNumber, String message,
                      String createdBy, Instant createdAt, RequestStatus status) {
        setName(name);
        setMessage(message);
        setPhoneNumber(phoneNumber);
        this.createAt = createdAt;
        this.createdBy = createdBy;
        this.status = status;
    }

}
