package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ITipoUsuarioDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoUsuarioDAOTest {

	private static final Logger log=LoggerFactory.getLogger(TipoUsuarioDAOTest.class);
	private long tusuCodigo=30;
	
	@Autowired
	private ITipoUsuarioDAO tipoUsuarioDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposUsuarios tiposUsuarios = new TiposUsuarios();
		tiposUsuarios.setTusuCodigo(tusuCodigo);		
		tiposUsuarios.setTusuNombre("Director Comercial");		
		
		tipoUsuarioDAO.grabar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTiposUsuariosPorId(tusuCodigo);				
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		log.info(tiposUsuarios.getTusuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTiposUsuariosPorId(tusuCodigo);
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		tiposUsuarios.setTusuNombre("Gerente Comercial");
		
		tipoUsuarioDAO.modificar(tiposUsuarios);		
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTiposUsuariosPorId(tusuCodigo);
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		
		tipoUsuarioDAO.borrar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposUsuarios> losTiposUsuarios=tipoUsuarioDAO.consultarTodos();
		for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
			log.info(tiposUsuarios.getTusuNombre());
		}
	}	
}