package com.murilonerdx.epictask.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationFieldError {
    private String field;
    private String defaultMessage;
}
