package ine5418.ismael.matheus.repository.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ine5418.ismael.matheus.repository.dto.DeleteMessage;
import ine5418.ismael.matheus.repository.dto.FileDTO;
import ine5418.ismael.matheus.repository.dto.StoreMessage;
import ine5418.ismael.matheus.repository.dto.request.DeleteFileRequest;
import ine5418.ismael.matheus.repository.dto.request.StoreFileRequest;
import ine5418.ismael.matheus.repository.entity.FileEntity;
import ine5418.ismael.matheus.repository.entity.UserEntity;
import ine5418.ismael.matheus.repository.repository.FileRepository;
import ine5418.ismael.matheus.repository.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepositoryService {
    final UserRepository userRepository;
    final FileRepository fileRepository;
    final KafkaTemplate<String, String> kafkaTemplate;


    public void storeFile(StoreFileRequest storeFileRequest) throws IOException {
        Optional<UserEntity> signed = userRepository.findById(Long.valueOf(storeFileRequest.getId()));
        if (signed.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<FileEntity> file = fileRepository.findByUserAndFilename(signed.get(), storeFileRequest.getFileName());
        if (file.isPresent()){
            throw new RuntimeException("File already exists");
        }
        String filePath = storeFileRequest.getId() + "/" + storeFileRequest.getFileName();
        Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
        String jsonMessage = GSON.toJson(new StoreMessage(storeFileRequest.getHexFileDump(), filePath));
        kafkaTemplate.send("store-file",jsonMessage);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUser(signed.get());
        fileEntity.setFilename(storeFileRequest.getFileName());
        fileEntity.setPath(filePath);
        fileRepository.save(fileEntity);
    }

    public void deleteFile(DeleteFileRequest deleteFileRequest) throws IOException {
        Optional<UserEntity> signed = userRepository.findById(Long.valueOf(deleteFileRequest.getId()));
        if (signed.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<FileEntity> file = fileRepository.findByUserAndFilename(signed.get(), deleteFileRequest.getFileName());
        if (file.isEmpty()){
            throw new RuntimeException("File don't exists");
        }
        Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
        String deleteMessage = GSON.toJson(new DeleteMessage(file.get().getPath()));
        kafkaTemplate.send("delete-file",deleteMessage);
        fileRepository.delete(file.get());
    }

    public List<FileDTO> findByUser(Long id) {
        Optional<UserEntity> signed = userRepository.findById(id);
        if (signed.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        List<FileEntity> files = fileRepository.findByUser(signed.get());
        List<FileDTO> dtos = new ArrayList<>();
        for (FileEntity file : files) {
            FileDTO dto = new FileDTO();
            dto.setFileName(file.getFilename());
            dto.setFilePath(file.getPath());
            dtos.add(dto);
        }
        return dtos;
    }
}
