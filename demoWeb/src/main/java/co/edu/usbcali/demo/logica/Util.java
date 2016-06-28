package co.edu.usbcali.demo.logica;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Util {

	private Logger log = LoggerFactory.getLogger(Util.class);
	
	@Autowired
	private Validator validator;
	
	public Util() {
	}
	
	public void validate(Object object) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
	}
}
