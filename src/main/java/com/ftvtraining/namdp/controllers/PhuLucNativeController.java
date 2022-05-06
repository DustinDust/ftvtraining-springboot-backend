package com.ftvtraining.namdp.controllers;

import javax.validation.Valid;

import com.ftvtraining.namdp.dto.GetRecordsResponse;
import com.ftvtraining.namdp.dto.GetRecordsRequest;
import com.ftvtraining.namdp.services.PhuLucNativeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/native/phuluc")
public class PhuLucNativeController {
  @Autowired
  PhuLucNativeService pLucNativeService;

  @PostMapping
  public ResponseEntity<GetRecordsResponse> getMethodName(@Valid @RequestBody GetRecordsRequest payload) {
    GetRecordsResponse res = this.pLucNativeService.getAllPhuLucNative(payload);
    return ResponseEntity.ok().body(res);
  }
}
