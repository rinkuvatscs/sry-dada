package com.medical.solutions.service;

import java.util.List;

import com.medical.solutions.entity.Patient;

public interface PatientService {

	public Integer patientSignUp(Patient patient);

	public Boolean checkMobile(String mobile);

	public Boolean checkAdhaar(String adhaar);

	public Boolean checkEmail(String email);

	public String addpatient(Patient patient);

	public String deletepatient(Patient patient);

	public Patient getpatientByMobileNumber(String mobileNumber);

	public Patient getpatientByAdharNumber(String adharNumber);

	public Patient getpatientByEmail(String email);

	public List<Patient> getpatientByName(String name);

	public Patient getpatientById(Integer id);

	public String updatepatient(Patient patient);

}
