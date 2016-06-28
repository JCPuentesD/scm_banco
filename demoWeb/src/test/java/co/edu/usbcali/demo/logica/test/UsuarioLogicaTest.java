package co.edu.usbcali.demo.logica.test;

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

import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.logica.ITipoUsuarioLogica;
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuarioLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuarioLogicaTest.class);
	private long tusuCodigo=20;
	private long usuCedula=30;
	
	@Autowired
	private IUsuarioLogica usuarioLogica;
	
	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuNombre("ANDRES MARTINEZ");
		usuarios.setUsuLogin("amartinez");
		usuarios.setUsuClave("1234");
		
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarTiposUsuariosPorId(tusuCodigo);		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuarioLogica.grabar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);						
		assertNotNull("El usuario no existe",usuarios);
		log.info(usuarios.getUsuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
		assertNotNull("El usuario no existe",usuarios);
		usuarios.setUsuNombre("JULIAN RODRIGUEZ");
		usuarios.setUsuLogin("jrodriguez");
		
		usuarioLogica.modificar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
		assertNotNull("El usuario no existe",usuarios);
		
		usuarioLogica.borrar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Usuarios> losUsuarios=usuarioLogica.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}

}