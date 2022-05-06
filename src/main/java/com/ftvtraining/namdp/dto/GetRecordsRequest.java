package com.ftvtraining.namdp.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class GetRecordsRequest implements Serializable {
	/**
	 * Auto generated default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Nullable
	private String nguoiTao;

	@Nullable
	private String maHopDong;

	@Nullable
	@Pattern(regexp = "^[1-9][0-9]{3}\\-[0-1][1-9]\\-[0-3][0-9]$", message = "Invalid Upper Bound Date String")
	private String ngayNghiemThuUpperBound;

	@Nullable
	@Pattern(regexp = "^[1-9][0-9]{3}\\-[0-1][1-9]\\-[0-3][0-9]$", message = "Invalid Lower Bound Date String")
	private String ngayNghiemThuLowerBound;

	@NotNull(message = "Page Index cannot be null")
	@Min(value = 0, message = "Invalid Page Index")
	private Integer pageIndex;

	@NotNull(message = "Page Size cannot be null")
	@Min(value = 1, message = "Invalid Page Size")
	private Integer pageSize;

	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getMaHopDong() {
		return maHopDong;
	}

	public void setMaHopDong(String maHopDong) {
		this.maHopDong = maHopDong;
	}

	public String getNgayNghiemThuUpperBound() {
		return ngayNghiemThuUpperBound;
	}

	public void setNgayNghiemThuUpperBound(String ngayNghiemThuUpperBound) {
		this.ngayNghiemThuUpperBound = ngayNghiemThuUpperBound;
	}

	public String getNgayNghiemThuLowerBound() {
		return ngayNghiemThuLowerBound;
	}

	public void setNgayNghiemThuLowerBound(String ngayNghiemThuLowerBound) {
		this.ngayNghiemThuLowerBound = ngayNghiemThuLowerBound;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
