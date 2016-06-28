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

import co.edu.usbcali.demo.dao.IUsuarioDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class UsuarioLogica implements IUsuarioLogica {
	
	private Logger log = LoggerFactory.getLogger(UsuarioLogica.class);
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	//@Autowired
	//private ITipoUsuarioDAO tipoUsuarioDAO;
	
	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Usuarios usuarios) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder(); 
		 
		Set<ConstraintViolation<Usuarios>> constraintViolations=validator.validate(usuarios);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Usuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		usuarioDAO.grabar(usuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Usuarios usuarios) throws Exception {
		
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Usuarios>> constraintViolations=validator.validate(usuarios);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Usuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		usuarioDAO.modificar(usuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Usuarios usuarios) throws Exception {
		
		Usuarios entity=usuarioDAO.consultarUsuariosPorId(usuarios.getUsuCedula());
		
		if(entity==null){
			throw new Exception("El usuario que desea eliminar no existe");
		}
		
		usuarioDAO.borrar(entity);

	}

	@Override
	@Transactional(readOnly=true)
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception {
		return usuarioDAO.consultarUsuariosPorId(usuCedula);		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuarios> consultarTodos() throws Exception {
		return usuarioDAO.consultarTodos();	
	}

}
