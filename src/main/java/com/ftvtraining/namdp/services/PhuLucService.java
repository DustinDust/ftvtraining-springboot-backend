package com.ftvtraining.namdp.services;

import com.ftvtraining.namdp.repositories.PhuLucRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftvtraining.namdp.dto.GetRecordsResponse;
import com.ftvtraining.namdp.dto.GetRecordsRequest;
import com.ftvtraining.namdp.exceptions.DatabaseRuntimeQueryException;
import com.ftvtraining.namdp.exceptions.RecordAlreadyExistException;
import com.ftvtraining.namdp.models.PhuLuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

@Service
public class PhuLucService {
  @Autowired
  PhuLucRepository pLucRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ObjectMapper mapper;

  public Page<PhuLuc> getAllPL(GetRecordsRequest payload, Pageable pageable) {
    Optional<String> maHopDong = Optional.ofNullable(payload.getMaHopDong());
    Optional<String> tenNguoiTao = Optional.ofNullable(payload.getNguoiTao());
    Optional<String> ngayNghiemThuUpperBound = Optional.ofNullable(payload.getNgayNghiemThuUpperBound());
    Optional<String> ngayNghiemThuLowerBound = Optional.ofNullable(payload.getNgayNghiemThuLowerBound());
    Page<PhuLuc> page = this.pLucRepository.findAll(new Specification<PhuLuc>() {
      @Override
      public Predicate toPredicate(Root<PhuLuc> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (maHopDong.isPresent()) {
          predicates.add(criteriaBuilder.like(root.get("maHopDong"),
              "%" + maHopDong.get() + "%"));
        }
        if (tenNguoiTao.isPresent()) {
          predicates.add(criteriaBuilder.like(root.get("nguoiTao"),
              "%" + tenNguoiTao.get() + "%"));
        }
        if (ngayNghiemThuLowerBound.isPresent()) {
          Date lowerBound = Date.valueOf(ngayNghiemThuLowerBound.get());
          System.out.println(lowerBound);
          predicates.add(
              criteriaBuilder.greaterThanOrEqualTo(root.get("ngayNghiemThu"),
                  lowerBound));
        }
        if (ngayNghiemThuUpperBound.isPresent()) {
          Date upperBound = Date.valueOf(ngayNghiemThuUpperBound.get());
          System.out.println(upperBound);
          predicates.add(
              criteriaBuilder.lessThanOrEqualTo(root.get("ngayNghiemThu"),
                  upperBound));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    }, pageable);
    System.out.println(
        "Items per page: " + pageable.getPageSize() +
            "\nCurrent page: " + pageable.getPageNumber() +
            "\nTotal page: " + page.getTotalPages());
    return page;
  }

  @SuppressWarnings("unchecked")
  public GetRecordsResponse getPLProc(GetRecordsRequest payload) {
    try {
      String data = mapper.writeValueAsString(payload);
      SimpleJdbcCall actor = new SimpleJdbcCall(jdbcTemplate).withCatalogName("PCKG_DM_PHU_LUC_2")
          .withProcedureName("get_phu_luc");
      SqlParameterSource inParams = new MapSqlParameterSource().addValue("pi_data", data);
      Map<String, Object> res = actor.execute(inParams);
      if (!res.containsKey("PO_ERR_CODE") || !(res.get("PO_ERR_CODE").toString().equals("0"))) {
        throw new DatabaseRuntimeQueryException((String) res.get("PO_MESSAGE"), res.get("PO_ERR_CODE").toString());
      } else {
        return new GetRecordsResponse(true, Long.valueOf(res.get("PO_LENGTH").toString()),
            (List<PhuLuc>) (res.get("PO_DATA")));
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error processing json");
    }
  }

  public PhuLuc getOnePL(Long id) throws NoSuchElementException {
    Optional<PhuLuc> phuLuc = this.pLucRepository.findById(id);
    if (phuLuc.isPresent()) {
      return phuLuc.get();
    } else {
      throw new NoSuchElementException("Could not find any record with id of " + id);
    }
  }

  public PhuLuc insertPhuLuc(PhuLuc phuLuc) throws RecordAlreadyExistException {
    Optional<PhuLuc> existingPL = this.pLucRepository.findById(phuLuc.getId());
    if (existingPL.isPresent()) {
      throw new RecordAlreadyExistException("Record with id of " + phuLuc.getId() + " already exists");
    }
    return this.pLucRepository.save(phuLuc);
  }

  public PhuLuc updatePhuLuc(PhuLuc phuLuc) {
    Optional<PhuLuc> exisitingPL = this.pLucRepository.findById(phuLuc.getId());
    if (!exisitingPL.isPresent()) {
      throw new NoSuchElementException("Record with id of " + phuLuc.getId() + " does not exist");
    } else {
      return this.pLucRepository.save(phuLuc);
    }
  }

  public void deletePhuLuc(long id) {
    Optional<PhuLuc> existingPL = this.pLucRepository.findById(id);
    if (!existingPL.isPresent()) {
      throw new NoSuchElementException("Record with id of " + id + " does not exist");
    }
    this.pLucRepository.deleteById(id);
  }
}