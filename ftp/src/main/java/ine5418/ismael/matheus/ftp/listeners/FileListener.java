package ine5418.ismael.matheus.ftp.listeners;

import com.google.gson.Gson;
import ine5418.ismael.matheus.ftp.dto.DeleteMessage;
import ine5418.ismael.matheus.ftp.dto.StoreMessage;
import ine5418.ismael.matheus.ftp.service.FTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileListener {
    final FTPService ftpService;

    @KafkaListener(topics = "store-file")
    public void listenStoreFile(@Payload String message) throws IOException {
        Gson gson = new Gson();
        StoreMessage storeMessage = gson.fromJson(message, StoreMessage.class);
        ftpService.storeFile(storeMessage.getFilePath(), storeMessage.getFileHex());
    }

    @KafkaListener(topics = "delete-file")
    public void listenDeleteFile(@Payload String message) throws IOException {
        Gson gson = new Gson();
        DeleteMessage deleteMessage = gson.fromJson(message, DeleteMessage.class);
        ftpService.deleteFile(deleteMessage.getFilePath());
    }
}
