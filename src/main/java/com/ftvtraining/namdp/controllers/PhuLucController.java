package com.ftvtraining.namdp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.ftvtraining.namdp.dto.GetRecordsResponse;
import com.ftvtraining.namdp.dto.GetRecordsRequest;
import com.ftvtraining.namdp.dto.ResponseDTO;
import com.ftvtraining.namdp.models.PhuLuc;
import com.ftvtraining.namdp.services.PhuLucService;

@RestController
@RequestMapping("/api/phuluc")
@CrossOrigin("http://localhost:4200")
public class PhuLucController {

  @Autowired
  PhuLucService pLucService;

  @PostMapping
  public ResponseEntity<GetRecordsResponse> getAllPhuluc(@Valid @RequestBody GetRecordsRequest payload) {
    Pageable pageable = PageRequest.of(payload.getPageIndex(), payload.getPageSize());
    Page<PhuLuc> res = this.pLucService.getAllPL(payload, pageable);
    return ResponseEntity.ok().body(new GetRecordsResponse(true, res.getTotalElements(), res.getContent()));
  }

  @PostMapping("/native")
  public ResponseEntity<GetRecordsResponse> getPhuLucNatvie(@Valid @RequestBody GetRecordsRequest payload) {
    GetRecordsResponse res = this.pLucService.getPLProc(payload);
    return ResponseEntity.ok().body(res);

  }

  @GetMapping("/{id}")
  public PhuLuc getPhuLuc(@PathVariable(name = "id") Long id) {
    return this.pLucService.getOnePL(id);
  }

  @PostMapping("/new")
  public ResponseEntity<ResponseDTO> createPhuLuc(@RequestBody PhuLuc phuLuc) {
    PhuLuc insertedPL = this.pLucService.insertPhuLuc(phuLuc);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDTO("Successfully created", true, insertedPL));
  }

  @PutMapping
  public ResponseEntity<ResponseDTO> updatePhuLuc(@RequestBody PhuLuc phuLuc) {
    PhuLuc updatedPL = this.pLucService.updatePhuLuc(phuLuc);
    return ResponseEntity.ok().body(new ResponseDTO("Successfully updated", true, updatedPL));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO> deletePhuluc(@PathVariable(name = "id") Long id) {
    this.pLucService.deletePhuLuc(id);
    return ResponseEntity.ok().body(new ResponseDTO("Successfully deleted", true, null));
  }
}