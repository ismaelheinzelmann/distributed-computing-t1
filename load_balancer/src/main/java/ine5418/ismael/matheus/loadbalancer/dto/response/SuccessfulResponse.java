package ine5418.ismael.matheus.loadbalancer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessfulResponse implements Response {
    private String message;
}
