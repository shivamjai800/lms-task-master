package com.project.lms.Entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BookRecord")
public class BookRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "bookId")
	private int bookId;

	@Column(name = "unitBook")
	private String unitBookReceived;

	@Column(name = "username")
	private String userUsername;

	@Column(name = "approvingAdminId")
	private String adminId;

	@Column(name = "requestDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime startDateTime;

	@Column(name = "status")
	private String status;

	@Column(name = "remarks")
	@Size(max = 150)
	private String remarks;

	@Column(name = "returnDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime returnDateTime;

	public BookRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnitBookReceived() {
		return unitBookReceived;
	}

	public void setUnitBookReceived(String unitBookReceived) {
		this.unitBookReceived = unitBookReceived;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(LocalDateTime returnDateTime) {
		this.returnDateTime = returnDateTime;
	}

	@Override
	public String toString() {
		return "BookRecord [id=" + id + ", bookId=" + bookId + ", unitBookReceived=" + unitBookReceived
				+ ", userUsername=" + userUsername + ", adminId=" + adminId + ", startDateTime=" + startDateTime
				+ ", status=" + status + ", remarks=" + remarks + ", returnDateTime=" + returnDateTime + "]";
	}

}
