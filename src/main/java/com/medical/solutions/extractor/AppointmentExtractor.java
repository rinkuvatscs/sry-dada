package com.medical.solutions.extractor;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.medical.solutions.entity.DoctorAddress;
import com.medical.solutions.entity.Appointment;
import com.medical.solutions.entity.Doctor;
import com.medical.solutions.entity.Patient;

public class AppointmentExtractor implements
		ResultSetExtractor<List<Appointment>> {

	@Override
	public List<Appointment> extractData(ResultSet rs) throws SQLException {

		List<Appointment> appointments = new ArrayList<>();
		Appointment appointment;
		while (rs.next()) {
			appointment = new Appointment();
			appointment.setAppointmentDesc(rs.getString("appointmentDesc"));
			appointment.setAppointmentId(rs.getInt("appointmentId"));
			appointment.setCreatedDate(rs.getDate("createdDate"));
			appointment.setdId(rs.getInt("dId"));
			appointment.setpId(rs.getInt("pId"));
			appointment.setAppointmentDate(rs.getDate("appointmentDate"));

			Object obj = createObject(rs);
			if (obj instanceof Patient) {
				appointment.setPatient((Patient) obj);
			} else {
				appointment.setDoctor((Doctor) obj);
			}
			appointment.setDoctorAddress(setDoctorAddress(rs));
			appointments.add(appointment);
		}
		return appointments;
	}

	private Object createObject(ResultSet rs) throws SQLException {

		Patient patient = new Patient();
		Doctor doctor = new Doctor();
		ResultSetMetaData rsmd = rs.getMetaData();
		boolean isPatient = false;
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if ("name".equals(rsmd.getColumnName(i))) {
				patient.setName(rs.getString("name"));
				doctor.setName(rs.getString("name"));
			} else if ("mobile".equals(rsmd.getColumnName(i))) {
				patient.setMobile(rs.getString("mobile"));
				doctor.setMobile(rs.getString("mobile"));
			} else if ("email".equals(rsmd.getColumnName(i))) {
				patient.setEmail(rs.getString("email"));
				doctor.setEmail(rs.getString("email"));
			} else if ("allergies".equals(rsmd.getColumnName(i))) {
				patient.setAllergies(rs.getString("allergies"));
				isPatient = true;
			} else if ("gender".equals(rsmd.getColumnName(i))) {
				patient.setGender(rs.getString("gender"));
				doctor.setGender(rs.getString("gender"));
			} else if ("dob".equals(rsmd.getColumnName(i))) {
				patient.setDOB(rs.getDate("dob"));
				doctor.setDOB(rs.getDate("dob"));
			} else if ("profilePicPath".equals(rsmd.getColumnName(i))) {
				patient.setProfilePicPath(rs.getString("profilePicPath"));
				doctor.setProfilePath(rs.getString("profilePicPath"));
			}
		}
		if (isPatient) {
			return patient;
		} else {
			return doctor;
		}
	}

	private DoctorAddress setDoctorAddress(ResultSet rs) throws SQLException {

		DoctorAddress doctorAddress = new DoctorAddress();
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			if ("latitude".equals(rsmd.getColumnName(i))) {
				doctorAddress.setLatitude(rs.getFloat("latitude"));
			} else if ("longitude".equals(rsmd.getColumnName(i))) {
				doctorAddress.setLongitude(rs.getFloat("longitude"));
			} else if ("pin".equals(rsmd.getColumnName(i))) {
				doctorAddress.setPin(new BigInteger(Integer.toString(rs
						.getInt("pin"))));
			} else if ("state".equals(rsmd.getColumnName(i))) {
				doctorAddress.setState(rs.getString("state"));
			} else if ("city".equals(rsmd.getColumnName(i))) {
				doctorAddress.setCity(rs.getString("city"));
			} else if ("landmark".equals(rsmd.getColumnName(i))) {
				doctorAddress.setLandmark(rs.getString("landmark"));
			}
		}
		return doctorAddress;
	}
}
