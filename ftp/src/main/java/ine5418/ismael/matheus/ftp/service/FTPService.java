package ine5418.ismael.matheus.ftp.service;

import ine5418.ismael.matheus.ftp.dto.FTPConfigDTO;
import ine5418.ismael.matheus.ftp.utils.FTPConnection;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;

@Service
public class FTPService {
    private final FTPConfigDTO ftpConfigDTO;
    private final Environment env;

    @Autowired
    public FTPService(Environment environment) {
        this.env = environment;
        ftpConfigDTO = new FTPConfigDTO();
        ftpConfigDTO.setHostname(Objects.requireNonNull(env.getProperty("ftp.credentials.host")));
        ftpConfigDTO.setUsername(Objects.requireNonNull(env.getProperty("ftp.credentials.user")));
        ftpConfigDTO.setPassword(Objects.requireNonNull(env.getProperty("ftp.credentials.password")));
        ftpConfigDTO.setPort(
                Integer.parseInt(Objects.requireNonNull(env.getProperty("ftp.credentials.port"))));
    }


    private FTPConnection createConnection() throws IOException {
        return new FTPConnection(ftpConfigDTO);
    }

    private void createFolder(FTPClient client, String path, String folderName) throws IOException {
        client.makeDirectory(path + "/" + folderName);
    }

    boolean fileExists(FTPClient client, String path) throws IOException {
        return client.listFiles(path).length > 0;
    }

    void renameFile(FTPClient client, String from, String to) throws IOException {
        client.rename(from, to);
    }

    boolean userPathExists(FTPClient client, String path) throws IOException {
        return client.listDirectories("/" + path).length > 0;
    }

    public void storeFile(String filePath, String hexFile) throws IOException {
        byte[] fileDump = HexUtils.fromHexString(hexFile);
        List<String> args = List.of(filePath.split("/"));
        try (FTPConnection connection = createConnection()){
            FTPClient client = connection.getClient();
            if (!userPathExists(client, args.get(0))){
                createFolder(client, "/", args.get(0));
            }
            client.storeFile(filePath, new ByteArrayInputStream(fileDump));
        }
    }

    public void deleteFile(String filePath) throws IOException {
        try (FTPConnection connection = createConnection()){
            FTPClient client = connection.getClient();
            if (fileExists(client, filePath)){
                client.deleteFile(filePath);
            }
        }
    }
}
