package com.medical.solutions.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.medical.solutions.enums.MiscEnum;
import com.medical.solutions.service.MiscService;

@Component
public class MiscFactory {

	@Autowired
	private List<MiscService> miscServiceList;

	@Value("${isRestCall:NO}")
	private String isRestCall;

	public MiscService getMiscService() {

		MiscService tempMiscService = null;
		for (MiscService miscService : miscServiceList) {
			if ("YES".equalsIgnoreCase(isRestCall)) {
				if (MiscEnum.THROUGH_RESTTEMPLATE.equals(miscService
						.getMiscEnum())) {
					tempMiscService = miscService;
					break;
				}
			} else if ("NO".equalsIgnoreCase(isRestCall)) {
				if (MiscEnum.DIRECT_DATABASE.equals(miscService.getMiscEnum())) {
					tempMiscService = miscService;
					break;
				}
			}
		}
		return tempMiscService;
	}
}
