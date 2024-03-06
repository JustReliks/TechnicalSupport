package org.TechnicalSupport.dto.factories;

import org.TechnicalSupport.dto.RequestDto;
import org.TechnicalSupport.entity.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestDtoFactory implements DtoFactory<Request, RequestDto> {
    @Override
    public RequestDto toDto(Request request) {
        return RequestDto.builder()
                .status(request.getStatus().getStatusEnum())
                .createdAt(request.getCreatedAt())
                .createdBy(request.getAuthor().getUsername())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .message(request.getMessage())
                .id(request.getId())
                .build();
    }
}
