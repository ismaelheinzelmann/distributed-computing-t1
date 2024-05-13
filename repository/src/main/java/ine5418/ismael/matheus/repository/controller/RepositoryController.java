package ine5418.ismael.matheus.repository.controller;

import ine5418.ismael.matheus.repository.dto.FileDTO;
import ine5418.ismael.matheus.repository.dto.request.*;
import ine5418.ismael.matheus.repository.dto.response.FailedResponse;
import ine5418.ismael.matheus.repository.dto.response.Response;
import ine5418.ismael.matheus.repository.dto.response.SuccessfulResponse;
import ine5418.ismael.matheus.repository.entity.FileEntity;
import ine5418.ismael.matheus.repository.entity.UserEntity;
import ine5418.ismael.matheus.repository.repository.UserRepository;
import ine5418.ismael.matheus.repository.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RepositoryController {
    final RepositoryService repositoryService;
    final UserRepository userRepository;

    @PostMapping(value = "store-file")
    public ResponseEntity<Response> storeFile(@RequestBody StoreFileRequest storeFileRequest) {
        try{
            repositoryService.storeFile(storeFileRequest);
            return new ResponseEntity<>(new SuccessfulResponse("File " + storeFileRequest.getFileName() + " stored."), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("delete-file")
    public ResponseEntity<Response> deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
        try{
            repositoryService.deleteFile(deleteFileRequest);
            return new ResponseEntity<>(new SuccessfulResponse("File " + deleteFileRequest.getFileName() + " deleted."), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("files")
    public ResponseEntity<List<FileDTO>> findFiles(@RequestBody GetFilesRequest getFilesRequest){
        try{
            return new ResponseEntity<>(repositoryService.findByUser(Long.valueOf(getFilesRequest.getId())), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
