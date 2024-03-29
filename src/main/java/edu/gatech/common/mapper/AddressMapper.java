package edu.gatech.common.mapper;

import java.util.List;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.Address.AddressType;
import org.hl7.fhir.dstu3.model.Address.AddressUse;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.Extension;

import edu.gatech.IGCompass.mapper.util.CommonUtil;
import edu.gatech.IGCompass.model.IGMapDocument;

public class AddressMapper implements IGMapper<Address>{

	@Override
	public Address map(IGMapDocument document, String resourceRootPath){
		Address address = new Address();
		String use = null;
		String type = null;
		String text = null;
		List<String> lines = null;
		String city = null;
		String district = null;
		String state = null;
		String postalCode = null;
		String country = null;
		String insideCityLimits = null;
		//TODO: Address.Period
		use = CommonUtil.findValueFromIGKey(document,resourceRootPath+".use");
		type = CommonUtil.findValueFromIGKey(document,resourceRootPath+".type");
		text = CommonUtil.findValueFromIGKey(document,resourceRootPath+".text");
		lines = CommonUtil.findListFromIGKey(document,resourceRootPath+".line");
		city = CommonUtil.findValueFromIGKey(document,resourceRootPath+".city");
		district = CommonUtil.findValueFromIGKey(document,resourceRootPath+".distric");
		state = CommonUtil.findValueFromIGKey(document,resourceRootPath+".state");
		postalCode = CommonUtil.findValueFromIGKey(document,resourceRootPath+".postalCode");
		country = CommonUtil.findValueFromIGKey(document,resourceRootPath+".country");
		insideCityLimits = CommonUtil.findValueFromIGKey(document,resourceRootPath+".extension.Within-City-Limits-Indicator");
		if(use != null) {
			address.setUse(AddressUse.valueOf(use));
		}
		if(type != null) {
			address.setType(AddressType.valueOf(type));
		}
		if(text != null) {
			address.setText(text);
		}
		if(lines != null) {
			for(String line:lines) {
				address.addLine(line);
			}
		}
		if(city != null) {
			address.setCity(city);
		}
		if(district != null) {
			address.setDistrict(district);
		}
		if(state != null) {
			address.setState(state);
		}
		if(postalCode != null) {
			address.setPostalCode(postalCode);
		}
		if(country != null) {
			address.setCountry(country);
		}
		Extension insideCityLimitsExtension = new Extension();
		insideCityLimitsExtension.setUrl("http://hl7.org/fhir/us/vrdr/StructureDefinition/Within-City-Limits-Indicator");
		if(insideCityLimits != null && insideCityLimits.equalsIgnoreCase("True")) {
			insideCityLimitsExtension.setValue(new BooleanType(true));
		}
		else {
			insideCityLimitsExtension.setValue(new BooleanType(false)); //TODO: ask about unknown case
		}
		address.addExtension(insideCityLimitsExtension);
		return address;
	}
}