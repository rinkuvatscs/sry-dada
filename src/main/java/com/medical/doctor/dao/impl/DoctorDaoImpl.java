package com.medical.doctor.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.medical.doctor.constants.QueryConstants;
import com.medical.doctor.dao.DoctorDao;
import com.medical.doctor.entity.Doctor;
import com.medical.doctor.exceptionhandler.BadRequestException;
import com.medical.doctor.extractor.DoctorExtractor;

@Component
public class DoctorDaoImpl implements DoctorDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String addDoctor(Doctor doctor) {
		String response = null;
		if (!isDoctorExists(doctor)) {

			List<Object> args = new ArrayList<>();
			args.add(doctor.getName());
			args.add(doctor.getMobile());
			args.add(doctor.getHomeAddress());
			args.add(doctor.getAadhaarNumber());
			args.add(doctor.getHighestDegree());
			args.add(doctor.getExpertized());
			args.add(doctor.getIsGovernmentServent());
			args.add(doctor.getOneTimeFee());
			args.add(doctor.getDaysCheckFree());
			args.add(doctor.getClinicAddress());
			args.add(doctor.getEmail());
			args.add(doctor.getAge());
			args.add(doctor.getGender());
			int row = jdbcTemplate.update(QueryConstants.ADD_DOCTOR,
					args.toArray());
			if (row == 1) {
				response = doctor.getName() + " registered successfully";
			} else {
				response = "Sorry, " + doctor.getName()
						+ " not registered . Please try again";
			}
		} else {
			response = "Sorry, " + doctor.getName() + " already registered";
		}

		return response;
	}

	@Override
	public String deleteDoctor(Integer doctorId) {
		String response = null;
		List<Object> args = new ArrayList<>();
		args.add(doctorId);
		int row = jdbcTemplate.update(QueryConstants.DELETE_DOCTOR,
				args.toArray());
		if (row == 1) {
			response = "Doctor ID " + doctorId + " deleted successfully";
		} else {
			response = "Sorry, Doctor ID " + doctorId
					+ " not exists in record. Please check again";
		}

		return response;
	}

	@Override
	public boolean isDoctorExists(Doctor doctor) {

		boolean isExist = false;
		List<String> args = new ArrayList<>();
		args.add(doctor.getMobile());
		args.add(doctor.getAadhaarNumber());
		args.add(doctor.getEmail());
		List<Doctor> response = jdbcTemplate.query(
				QueryConstants.IS_DOCTOR_EXIST, new DoctorExtractor(),
				args.toArray());
		if (!StringUtils.isEmpty(response) && response.size() > 0) {
			isExist = true;
		}
		return isExist;
	}

	@Override
	public Doctor getDoctorById(Integer id) {

		if (!StringUtils.isEmpty(id)) {
			Object args[] = { id };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_ID, args,
					new DoctorExtractor());
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Doctor();
	}

	@Override
	public Doctor getDoctorByEmail(String email) {

		if (!StringUtils.isEmpty(email)) {
			Object args[] = { email };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_EMAIL, args,
					new DoctorExtractor());
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Doctor();
	}

	@Override
	public Doctor getDoctorByAdharNumber(String adharNumber) {

		if (!StringUtils.isEmpty(adharNumber)) {
			Object args[] = { adharNumber };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_ADHAR_NUMBER,
					new DoctorExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Doctor();
	}

	@Override
	public Doctor getDoctorByMobileNumber(String mobileNumber) {

		if (!StringUtils.isEmpty(mobileNumber)) {
			Object args[] = { mobileNumber };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_MOBILE_NUMBER,
					new DoctorExtractor(), args);
			if (!StringUtils.isEmpty(response) && response.size() > 0) {
				return response.get(0);
			}
		}
		return new Doctor();
	}

	@Override
	public List<Doctor> getDoctorByName(String name) {

		if (!StringUtils.isEmpty(name)) {
			Object args[] = { "%" + name + "%" };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_NAME, new DoctorExtractor(),
					args);
			if (!StringUtils.isEmpty(response) && !response.isEmpty()) {
				return response;
			}
		}
		return new ArrayList<Doctor>();
	}

	@Override
	public List<Doctor> getDoctorByExpertisted(String expertisted) {

		if (!StringUtils.isEmpty(expertisted)) {
			Object args[] = { "%" + expertisted + "%" };
			List<Doctor> response = jdbcTemplate.query(
					QueryConstants.GET_DOCTOR_BY_EXPERTISTED,
					new DoctorExtractor(), args);
			if (!StringUtils.isEmpty(response) && !response.isEmpty()) {
				return response;
			}
		}
		return new ArrayList<Doctor>();
	}

	@Override
	public List<Doctor> getDoctorByConsultingFee(String consultingFee) {

		Object args[] = { consultingFee };
		List<Doctor> response = jdbcTemplate.query(
				QueryConstants.GET_DOCTOR_BY_CONSULTING_FEE,
				new DoctorExtractor(), args);
		if (!StringUtils.isEmpty(response) && !response.isEmpty()) {
			return response;
		}
		return new ArrayList<Doctor>();
	}

	@Override
	public String updateDoctor(Doctor doctor) {

		String response = null;
		List<Object> args = new ArrayList<>();
		StringBuilder query = new StringBuilder(" UPDATE doctor_detail SET ");
		if (!StringUtils.isEmpty(doctor)) {
			if (isDoctorExists(doctor)) {
				boolean isDoctorName = false, isHomeAddress = false, isHighestDegree = false, isExpertized = false, isGovtServant = false;
				boolean IsOneTimeFees = false, isDayFreeInConsultingFee = false, isShopAddress = false;
				if (null != doctor.getName()) {
					query.append(" doctor_name = ? ");
					args.add(doctor.getName());
					isDoctorName = true;
				}
				if (null != doctor.getHomeAddress()) {
					if (isDoctorName) {
						query.append(" , doctor_homeaddress = ? ");
						args.add(doctor.getHomeAddress());
					} else {
						query.append(" doctor_homeaddress = ? ");
						args.add(doctor.getHomeAddress());
					}
					isHomeAddress = true;
				}
				if (null != doctor.getHighestDegree()) {
					if (isHomeAddress || isDoctorName) {
						query.append(", doctor_highestdegree = ? ");
						args.add(doctor.getHighestDegree());
					} else {
						query.append(" doctor_highestdegree = ? ");
						args.add(doctor.getHighestDegree());
					}
					isHighestDegree = true;
				}
				if (null != doctor.getExpertized()) {
					if (isHomeAddress || isDoctorName || isHighestDegree) {
						query.append(", doctor_expertized = ? ");
						args.add(doctor.getExpertized());
					} else {
						query.append(" doctor_expertized = ? ");
						args.add(doctor.getExpertized());
					}
					isExpertized = true;
				}
				if (null != doctor.getIsGovernmentServent()) {
					if (isHomeAddress || isDoctorName || isHighestDegree
							|| isExpertized) {
						query.append(", is_doctor_govt_servant = ? ");
						args.add(doctor.getIsGovernmentServent());
					} else {
						query.append(" is_doctor_govt_servant = ? ");
						args.add(doctor.getIsGovernmentServent());
					}
					isGovtServant = true;
				}
				// TODO will work
				if (null != doctor.getOneTimeFee()) {
					if (isHomeAddress || isDoctorName || isHighestDegree
							|| isExpertized || isGovtServant) {
						query.append(", onetime_consulting_fee = ? ");
						args.add(doctor.getOneTimeFee());
					} else {
						query.append(" onetime_consulting_fee = ? ");
						args.add(doctor.getOneTimeFee());
					}
					IsOneTimeFees = true;
				}
				if (null != doctor.getDaysCheckFree()) {
					if (isHomeAddress || isDoctorName || isHighestDegree
							|| isExpertized || isGovtServant || IsOneTimeFees) {
						query.append(", doctor_days_to_check_free_in_consulting_fee = ? ");
						args.add(doctor.getDaysCheckFree());
					} else {
						query.append(" doctor_days_to_check_free_in_consulting_fee = ? ");
						args.add(doctor.getDaysCheckFree());
					}
					isDayFreeInConsultingFee = true;
				}
				if (null != doctor.getClinicAddress()) {
					if (isHomeAddress || isDoctorName || isHighestDegree
							|| isExpertized || isGovtServant || IsOneTimeFees
							|| isDayFreeInConsultingFee) {
						query.append(", doctor_shop_address = ? ");
						args.add(doctor.getClinicAddress());
					} else {
						query.append(" doctor_shop_address = ? ");
						args.add(doctor.getClinicAddress());
					}
					isShopAddress = true;
				}
				if (isDoctorName || isHomeAddress || isHighestDegree
						|| isExpertized || isGovtServant || IsOneTimeFees
						|| isDayFreeInConsultingFee || isShopAddress) {
					if (null != doctor.getMobile()) {
						query.append(" WHERE doctor_number = ? ");
						args.add(doctor.getMobile());
					} else if (null != doctor.getAadhaarNumber()) {
						query.append(" WHERE doctor_adhaar_number = ? ");
						args.add(doctor.getAadhaarNumber());
					} else if (null != doctor.getDoctorId()) {
						query.append(" WHERE doctor_id = ? ");
						args.add(doctor.getDoctorId());
					} else {
						response = "Please Enter data to Update....!!!";
					}
				}
				if (StringUtils.isEmpty(response)) {
					int update = jdbcTemplate.update(query.toString(),
							args.toArray());
					if (update > 0) {
						response = "Doctor successfully Updated...!!!";
					} else {
						response = "There is some problem, please try again later...!!!";
					}
				}
			} else {
				response = "Doctor does not exist...!!!";
			}
		} else {
			response = "Doctor details are Empty, provide some details to update....!!!";
		}

		return response;
	}

	@Override
	public String deleteDoctor(Doctor doctor) {

		String response = null;
		int delete;
		if (!StringUtils.isEmpty(doctor)) {
			if (!StringUtils.isEmpty(doctor.getDoctorId())
					&& getDoctorById(doctor.getDoctorId()) != null) {
				Object args[] = { doctor.getDoctorId() };
				delete = jdbcTemplate.update(
						"DELETE FROM doctor_detail WHERE doctor_id = ? ", args);
				if (delete > 0) {
					response = "Doctor with Doctor ID " + doctor.getDoctorId()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(doctor.getAadhaarNumber())
					&& getDoctorByAdharNumber(doctor.getAadhaarNumber()) != null) {
				Object args[] = { doctor.getAadhaarNumber() };
				delete = jdbcTemplate
						.update("DELETE FROM doctor_detail WHERE doctor_adhaar_number = ? ",
								args);
				if (delete > 0) {
					response = "Doctor with Aadhar Number "
							+ doctor.getAadhaarNumber()
							+ " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else if (!StringUtils.isEmpty(doctor.getMobile())
					&& getDoctorByMobileNumber(doctor.getMobile()) != null) {
				Object args[] = { doctor.getMobile() };
				delete = jdbcTemplate.update(
						"DELETE FROM doctor_detail WHERE doctor_number = ? ",
						args);
				if (delete > 0) {
					response = "Doctor with Mobile Number "
							+ doctor.getMobile() + " successfully Deleted";
				} else {
					response = "Please try again later";
				}
			} else {
				throw new BadRequestException(
						"Please provide valid Doctor Id or Doctor Adhar Number or Doctor Mobile Number.");
			}
		} else {
			throw new BadRequestException(
					"Doctor can not be deleted without details");

		}
		return response;
	}

}