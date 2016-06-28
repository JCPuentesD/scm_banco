package co.edu.usbcali.demo.logica;

import java.util.ArrayList;
import java.util.Iterator;
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

import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@Service
@Scope("singleton")
public class CuentaLogica implements ICuentaLogica {
	
	private Logger log = LoggerFactory.getLogger(ClienteLogica.class);
	private List<Cuentas> listCuentas=null;
	
	@Autowired
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	IClienteLogica clienteLogica;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Cuentas cuentas) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		cuentaDAO.grabar(cuentas);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Cuentas cuentas) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		cuentaDAO.modificar(cuentas);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Cuentas cuentas) throws Exception {
		// No deberia ir esta validación
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		cuentaDAO.borrar(cuentas);

	}

	@Override
	@Transactional(readOnly=true)
	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception {
		return cuentaDAO.consultarCuentasPorId(cueNumero);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cuentas> consultarTodos() throws Exception {
		return cuentaDAO.consultarTodos();
	}

	/*
	@Override
	public List<Cuentas> consultarCuentasPorCliente(long cliId) throws Exception {
		
		listCuentas = new ArrayList<Cuentas>();
				
		Clientes clientes = clienteLogica.consultarClientesPorId(cliId);
		
		if(clientes==null){
			throw new Exception("El cliente no existe o no ha sido ingresado");
		}
		
		Set<Cuentas> hashCuentas = clientes.getCuentases();
		
		for (Iterator<Cuentas> iterator = hashCuentas.iterator(); iterator.hasNext();) {
			Cuentas cuentas = (Cuentas) iterator.next();			
			listCuentas.add(cuentas);
		}
		
		if (listCuentas.size() == 0) {
			throw new Exception("El cliente no tiene ninguna cuenta asociada");
		}
		
		return listCuentas;
		
	}
	*/
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Cuentas> consultarCuentasPorCliente(long cliId) throws Exception {
			
		return cuentaDAO.consultarCuentasPorCliId(cliId);
		
	}

}
