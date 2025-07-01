package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	
	private int scheduleId;
	private String scheduleIitle;
	private int scheduleState;
	private String scheduleDate;
	private String scheduleLocation;
	private String createdAt;
	private String updatedAt;
	
	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", scheduleIitle=" + scheduleIitle + ", scheduleState="
				+ scheduleState + ", scheduleDate=" + scheduleDate + ", scheduleLocation=" + scheduleLocation
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", toString()=" + super.toString() + "]";
	}
	
}
