package ine5418.ismael.matheus.gateway.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGatewayRequest {
    private String path;
    private String redirect;
}
