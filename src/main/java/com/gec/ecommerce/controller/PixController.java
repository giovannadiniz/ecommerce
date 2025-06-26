package com.gec.ecommerce.controller;

import com.gec.ecommerce.dto.request.PixChargeRequest;
import com.gec.ecommerce.service.PixService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    @GetMapping
    public ResponseEntity listarEVPs(){

        String response = this.pixService.listarEVPs();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

}
