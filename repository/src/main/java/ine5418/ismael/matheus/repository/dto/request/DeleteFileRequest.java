package ine5418.ismael.matheus.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFileRequest {
    private String id;
    private String fileName;
}
