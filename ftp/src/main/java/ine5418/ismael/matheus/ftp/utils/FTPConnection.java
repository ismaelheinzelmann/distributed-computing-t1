package ine5418.ismael.matheus.ftp.utils;

import ine5418.ismael.matheus.ftp.dto.FTPConfigDTO;
import lombok.Getter;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

@Getter
public class FTPConnection implements AutoCloseable {
    private final FTPClient client;

    public FTPConnection(FTPConfigDTO ftpConfigDTO) throws IOException {
        client = new FTPClient();
        client.connect(ftpConfigDTO.getHostname(), ftpConfigDTO.getPort());
        client.login(ftpConfigDTO.getUsername(), ftpConfigDTO.getPassword());
        client.enterLocalPassiveMode();
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
    }

    public void close() throws IOException {
        client.logout();
        client.disconnect();
    }
}
