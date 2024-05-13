package ine5418.ismael.matheus.gateway.dto.response;

import ine5418.ismael.matheus.gateway.dto.RedirectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedirectsResponse implements Response{
    private List<RedirectDTO> redirects;
}
