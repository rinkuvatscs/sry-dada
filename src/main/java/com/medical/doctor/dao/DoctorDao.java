package com.medical.doctor.dao;

import java.util.List;

import com.medical.doctor.entity.Doctor;

public interface DoctorDao {

	public String addDoctor(Doctor doctor);

	public boolean isDoctorExists(Doctor doctor);

	public String deleteDoctor(Integer doctorId);
	
	public Doctor getDoctorById(Integer id);
	
	public Doctor getDoctorByEmail(String email);
	
	public Doctor getDoctorByAdharNumber(String adharNumber);
	
	public Doctor getDoctorByMobileNumber(String mobileNumber);
	
	public List<Doctor> getDoctorByName(String name);
	
	public List<Doctor> getDoctorByExpertisted(String expertisted);
	
	public List<Doctor> getDoctorByConsultingFee(String consultingFee);
	
	public String updateDoctor(Doctor doctor);
	
	public String deleteDoctor(Doctor doctor);
}