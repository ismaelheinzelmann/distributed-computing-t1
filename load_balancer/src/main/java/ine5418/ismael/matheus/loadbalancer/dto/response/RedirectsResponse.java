package ine5418.ismael.matheus.loadbalancer.dto.response;

import ine5418.ismael.matheus.loadbalancer.dto.InstanceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectsResponse implements Response{
    private List<InstanceDto> redirects;
}
