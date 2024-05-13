package ine5418.ismael.matheus.loadbalancer.service;

import ine5418.ismael.matheus.loadbalancer.dto.InstanceDto;
import ine5418.ismael.matheus.loadbalancer.dto.request.AddInstanceRequest;
import ine5418.ismael.matheus.loadbalancer.dto.response.FailedResponse;
import ine5418.ismael.matheus.loadbalancer.dto.response.RedirectsResponse;
import ine5418.ismael.matheus.loadbalancer.dto.response.Response;
import ine5418.ismael.matheus.loadbalancer.dto.response.SuccessfulResponse;
import ine5418.ismael.matheus.loadbalancer.entity.InstanceEntity;
import ine5418.ismael.matheus.loadbalancer.repository.InstanceRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BalanceService {
    final InstanceRepository instanceRepository;
    private final HashMap<Integer, Long> instancesUsage = new HashMap<>();

    BalanceService(InstanceRepository instanceRepository){
        this.instanceRepository = instanceRepository;
        Iterable<InstanceEntity> instances = this.instanceRepository.findAll();
        for (InstanceEntity instance : instances) {
            instancesUsage.put(instance.getId(), System.currentTimeMillis());
        }
    }

    public Response addInstance(AddInstanceRequest addInstanceRequest){
        if (instanceRepository.findByInstancePath(addInstanceRequest.getInstancePath()).isPresent()) {
            return new FailedResponse("Instance already exists.");
        }
        InstanceEntity instanceEntity = new InstanceEntity();
        instanceEntity.setInstancePath(addInstanceRequest.getInstancePath());
        instanceRepository.save(instanceEntity);
        instancesUsage.put(instanceEntity.getId(), System.currentTimeMillis());
        return new SuccessfulResponse("Instance " + addInstanceRequest.getInstancePath() + " successfully added.");
    }

    public InstanceDto getInstance(Integer instanceId){
        Optional<InstanceEntity> instanceEntity = instanceRepository.findById(instanceId);
        if (instanceEntity.isPresent()) {
            return new InstanceDto(instanceEntity.get().getInstancePath());
        }
        throw new RuntimeException("Instance not found.");
    }

    public RedirectsResponse getInstances(){
        RedirectsResponse redirectsResponse = new RedirectsResponse();
        Iterable<InstanceEntity> redirectList = instanceRepository.findAll();
        List<InstanceDto> instanceDtoList = new ArrayList<>();
        for (InstanceEntity redirect : redirectList) {
            InstanceDto instanceDto = new InstanceDto();
            instanceDto.setInstancePath(redirect.getInstancePath());
            instanceDtoList.add(instanceDto);
        }
        redirectsResponse.setRedirects(instanceDtoList);
        return redirectsResponse;
    }

    public Response removeRedirect(String path){
        if (instanceRepository.findByInstancePath(path).isEmpty()) {
            return new FailedResponse("Redirect not found");
        }
        instanceRepository.delete(instanceRepository.findByInstancePath(path).get());
        return new SuccessfulResponse("Gateway " + path + " successfully removed.");
    }

    public Integer computeNextInstance(){
        if (instancesUsage.isEmpty()){
            throw new RuntimeException("No instances found.");
        }
        if (instancesUsage.size() == 1){
            return 0;
        }

        Long nextInstanceTime = System.currentTimeMillis();
        Integer nextInstanceId = 0;
        for (Map.Entry<Integer, Long> entry : instancesUsage.entrySet()) {
            if (entry.getValue() < nextInstanceTime){
                nextInstanceTime = entry.getValue();
                nextInstanceId = entry.getKey();
            }
        }
        instancesUsage.put(nextInstanceId, System.currentTimeMillis());
        return nextInstanceId;
    }

    public InstanceDto getNextInstance(){
        if (instancesUsage.isEmpty()){
            throw new RuntimeException("No instances found.");
        }
        Integer nextInstanceId = computeNextInstance();
        InstanceDto instanceDto = new InstanceDto();
        Optional<InstanceEntity> nextInstance = instanceRepository.findById(nextInstanceId);
        instanceDto.setInstancePath(nextInstance.get().getInstancePath());
        return instanceDto;
    }

}
