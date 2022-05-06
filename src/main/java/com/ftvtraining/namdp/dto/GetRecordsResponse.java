package com.ftvtraining.namdp.dto;

import lombok.Data;
import com.ftvtraining.namdp.models.PhuLuc;
import java.util.List;

@Data
public class GetRecordsResponse {
	boolean success = true;

	long length;

	List<PhuLuc> phuLuc;


	public GetRecordsResponse(boolean success, long length, List<PhuLuc> phuLuc) {
		super();
		this.success = success;
		this.length = length;
		this.phuLuc = phuLuc;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public List<PhuLuc> getPhuLuc() {
		return phuLuc;
	}

	public void setPhuLuc(List<PhuLuc> phuLuc) {
		this.phuLuc = phuLuc;
	}
	
	
}
