package ine5418.ismael.matheus.loadbalancer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInstanceRequest {
    private String instancePath;
}
