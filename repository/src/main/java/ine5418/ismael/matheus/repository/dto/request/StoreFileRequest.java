package ine5418.ismael.matheus.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreFileRequest {
    private String fileName;
    private String id;
    private String hexFileDump;
}
