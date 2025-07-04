package com.code.mvc.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetailsRequest {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
//need to know