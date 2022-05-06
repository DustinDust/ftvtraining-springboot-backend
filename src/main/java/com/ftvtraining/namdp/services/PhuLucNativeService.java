package com.ftvtraining.namdp.services;

import java.util.List;

import javax.persistence.EntityManager;

import com.ftvtraining.namdp.dto.GetRecordsResponse;
import com.ftvtraining.namdp.dto.GetRecordsRequest;
import com.ftvtraining.namdp.models.PhuLuc;
import com.ftvtraining.namdp.utils.QueryBuilder;

import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class PhuLucNativeService {

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  EntityManager entityManager;

  @SuppressWarnings("unchecked")
  public GetRecordsResponse getAllPhuLucNative(GetRecordsRequest payload) {
    Session session = entityManager.unwrap(Session.class);
    QueryBuilder recQBuilder = new QueryBuilder("RECORDS");
    QueryBuilder lengthQBuilder = new QueryBuilder("LENGTH");
    recQBuilder.initCondition();
    lengthQBuilder.initCondition();
    if (payload.getNguoiTao() != null) {
      recQBuilder.addNguoiTao(payload.getNguoiTao());
      lengthQBuilder.addNguoiTao(payload.getNguoiTao());
    }
    if (payload.getMaHopDong() != null) {
      recQBuilder.addMaHopDong(payload.getMaHopDong());
      lengthQBuilder.addMaHopDong(payload.getMaHopDong());
    }
    if (payload.getNgayNghiemThuLowerBound() != null) {
      recQBuilder.addNgayNghiemThuLowerBound(payload.getNgayNghiemThuLowerBound());
      lengthQBuilder.addNgayNghiemThuLowerBound(payload.getNgayNghiemThuLowerBound());
    }
    if (payload.getNgayNghiemThuUpperBound() != null) {
      recQBuilder.addNgayNghiemThuUpperBound(payload.getNgayNghiemThuUpperBound());
      lengthQBuilder.addNgayNghiemThuUpperBound(payload.getNgayNghiemThuUpperBound());
    }
    List<PhuLuc> pls = session.createNativeQuery(recQBuilder.getQuery())
        .addEntity(PhuLuc.class)
        .setFirstResult(payload.getPageIndex() * payload.getPageSize())
        .setMaxResults(payload.getPageSize())
        .list();

    Long length = ((Number) session.createNativeQuery(lengthQBuilder.getQuery())
        .getSingleResult()).longValue();

    return new GetRecordsResponse(true, length, pls);
  }
}
