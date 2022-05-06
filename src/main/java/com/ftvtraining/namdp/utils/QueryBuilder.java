package com.ftvtraining.namdp.utils;

public class QueryBuilder {
  private StringBuilder query;

  public QueryBuilder(String type) {
    if (type.equals("LENGTH")) {
      this.query = new StringBuilder("SELECT count(1) FROM DM_PHU_LUC_2 pl");
    } else if (type.equals("RECORDS")) {
      this.query = new StringBuilder("SELECT * FROM DM_PHU_LUC_2 pl");
    }
  }

  public QueryBuilder initCondition() {
    this.query.append(" WHERE 1=1");
    return this;
  }

  public QueryBuilder addNguoiTao(String nguoiTao) {
    this.query.append(" AND pl.NGUOI_TAO LIKE '%" + nguoiTao + "%'");
    return this;
  }

  public QueryBuilder addMaHopDong(String maHopDong) {
    this.query.append(" AND pl.MA_HOP_DONG LIKE '%" + maHopDong + "%'");
    return this;
  }

  public QueryBuilder addNgayNghiemThuUpperBound(String ngayNghiemThuUpperBound) {
    this.query.append(" AND pl.NGAY_NGHIEM_THU >= TO_DATE('" + ngayNghiemThuUpperBound + "', 'yyyy-mm-dd')");
    return this;
  }

  public QueryBuilder addNgayNghiemThuLowerBound(String ngayNghiemThuLowerBound) {
    this.query.append(" AND pl.NGAY_NGHIEM_THU >= TO_DATE('" + ngayNghiemThuLowerBound + "', 'yyyy-mm-dd')");
    return this;
  }

  public String getQuery() {
    String res = this.query.toString();
    System.out.println(this.query);
    this.query = new StringBuilder("SELECT * FROM DM_PHU_LUC_2 pl");
    return res;
  }
}
