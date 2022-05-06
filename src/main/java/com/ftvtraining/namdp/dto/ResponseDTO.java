package com.ftvtraining.namdp.dto;

import java.io.Serializable;

import com.ftvtraining.namdp.models.PhuLuc;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseDTO implements Serializable {

	public ResponseDTO(String message, boolean success, PhuLuc alteredRecord) {
		super();
		this.message = message;
		this.success = success;
		this.alteredRecord = alteredRecord;
	}

	@NonNull
	private String message;

	@NonNull
	private boolean success;

	@Nullable
	private PhuLuc alteredRecord;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public PhuLuc getAlteredRecord() {
		return alteredRecord;
	}

	public void setAlteredRecord(PhuLuc alteredRecord) {
		this.alteredRecord = alteredRecord;
	}

}
