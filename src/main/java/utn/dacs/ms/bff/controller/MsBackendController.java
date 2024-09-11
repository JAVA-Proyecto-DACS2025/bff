package utn.dacs.ms.bff.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.service.MsApiBackendService;

@RestController
@RequestMapping("/backend")
@Slf4j
public class MsBackendController {

    @Autowired
    private MsApiBackendService apiBackendService;

    @GetMapping("/ping")
    public String ping() {
        return apiBackendService.ping();
    }
    
    @GetMapping("/version")
    public BuildInfoDTO version() {
        return apiBackendService.version();
    }
    /*
    @GetMapping("/reason")
    public List<ReasonDTO> getMotivos() {
    	return apiConectorService.getReason();
    }*/
   
}
