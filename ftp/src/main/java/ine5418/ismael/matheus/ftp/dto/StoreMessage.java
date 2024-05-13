package ine5418.ismael.matheus.ftp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreMessage {
    private String fileHex;
    private String filePath;
}
