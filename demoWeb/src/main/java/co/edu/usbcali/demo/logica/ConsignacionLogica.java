package co.edu.usbcali.demo.logica;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IConsignacionDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;

@Service
@Scope("singleton")
public class ConsignacionLogica implements IConsignacionLogica {
	
	private Logger log = LoggerFactory.getLogger(ConsignacionLogica.class);
	
	@Autowired
	private IConsignacionDAO consignacionDAO;
		
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Consignaciones consignaciones) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		consignacionDAO.grabar(consignaciones);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Consignaciones consignaciones) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		consignacionDAO.modificar(consignaciones);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Consignaciones consignaciones) throws Exception {
		//No deberia realizarse esta validación
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		consignacionDAO.borrar(consignaciones);

	}

	@Override
	@Transactional(readOnly=true)
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId consignacionesId) throws Exception {
		return consignacionDAO.consultarConsignacionesPorId(consignacionesId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Consignaciones> consultarTodos() throws Exception {
		return consignacionDAO.consultarTodos();	
	}

	@Override
	public String ObtenerSecuencia() throws Exception {
		return consignacionDAO.ObtenerSecuencia();
	}

}
