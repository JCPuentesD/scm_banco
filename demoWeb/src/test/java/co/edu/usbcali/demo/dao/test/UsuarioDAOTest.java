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
import co.edu.usbcali.demo.dao.IUsuarioDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuarioDAOTest {

	private static final Logger log=LoggerFactory.getLogger(UsuarioDAOTest.class);
	private long tusuCodigo=20;
	private long usuCedula=30;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private ITipoUsuarioDAO tipoUsuarioDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuNombre("ANDRES MARTINEZ");
		usuarios.setUsuLogin("amartinez");
		usuarios.setUsuClave("1234");
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTiposUsuariosPorId(tusuCodigo);		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuarioDAO.grabar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		Usuarios usuarios = usuarioDAO.consultarUsuariosPorId(usuCedula);				
		assertNotNull("El usuario no existe",usuarios);
		log.info(usuarios.getUsuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		Usuarios usuarios = usuarioDAO.consultarUsuariosPorId(usuCedula);
		assertNotNull("El usuario no existe",usuarios);
		usuarios.setUsuNombre("JULIAN RODRIGUEZ");
		usuarios.setUsuLogin("jrodriguez");
		
		usuarioDAO.modificar(usuarios);		
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		Usuarios usuarios = usuarioDAO.consultarUsuariosPorId(usuCedula);
		assertNotNull("El usuario no existe",usuarios);
		
		usuarioDAO.borrar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Usuarios> losUsuarios=usuarioDAO.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}	
}