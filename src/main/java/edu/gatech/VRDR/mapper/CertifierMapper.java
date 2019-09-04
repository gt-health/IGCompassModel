package edu.gatech.VRDR.mapper;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Practitioner.PractitionerQualificationComponent;

import edu.gatech.IGFlatFhir.exception.MissingDataNodeException;
import edu.gatech.IGFlatFhir.exception.MissingInformationException;
import edu.gatech.IGFlatFhir.exception.MissingKeyException;
import edu.gatech.IGFlatFhir.exception.PathFormatException;
import edu.gatech.IGFlatFhir.exception.WrongTypeException;
import edu.gatech.IGFlatFhir.mapper.util.CommonUtil;
import edu.gatech.IGFlatFhir.model.IGMapDocument;
import edu.gatech.VRDR.model.Certifier;

public class CertifierMapper implements IGMapper<Certifier> {
	@Override
	public Certifier map(IGMapDocument document, String resourceRootPath) throws MissingInformationException,
			WrongTypeException, MissingDataNodeException, PathFormatException {
		Certifier certifier = new Certifier();
		HumanNameMapper humanNameMapper = new HumanNameMapper();
		AddressMapper addressMapper = new AddressMapper();
		CodeableConceptMapper ccMapper = new CodeableConceptMapper();
		HumanName humanName = null;
		Address address = null;
		CodeableConcept qualificationCode = null;
		String identifier = null;
		humanName = humanNameMapper.map(document, "Certifier.name");
		address = addressMapper.map(document, "Certifier.address");
		qualificationCode = ccMapper.map(document, "Certifier.qualification");
		identifier = CommonUtil.findValueFromIGKey(document, "Certifier.qualification.identifier");
		if(humanName != null) {
			certifier.addName(humanName);
		}
		if(address != null) {
			certifier.addAddress(address);
		}
		if(qualificationCode != null && identifier != null) {
			certifier.addQualification(new PractitionerQualificationComponent(qualificationCode)
					.addIdentifier(new Identifier().setValue(identifier)));
		}
		return certifier;
	}

}
