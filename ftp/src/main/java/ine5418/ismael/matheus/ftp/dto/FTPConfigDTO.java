package ine5418.ismael.matheus.ftp.dto;

import lombok.Data;

@Data
public class FTPConfigDTO {
    private String hostname;
    private String username;
    private String password;
    private int port;
}
