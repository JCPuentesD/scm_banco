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

import co.edu.usbcali.demo.logica.ITipoUsuarioLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoUsuarioLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(TipoUsuarioLogicaTest.class);
	private long tusuCodigo=30;
	
	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		TiposUsuarios tiposUsuarios = new TiposUsuarios();
		tiposUsuarios.setTusuCodigo(tusuCodigo);		
		tiposUsuarios.setTusuNombre("Director Comercial");		
		
		tipoUsuarioLogica.grabar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarTiposUsuariosPorId(tusuCodigo);				
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		log.info(tiposUsuarios.getTusuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarTiposUsuariosPorId(tusuCodigo);
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		tiposUsuarios.setTusuNombre("Gerente Comercial");
		
		tipoUsuarioLogica.modificar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		TiposUsuarios tiposUsuarios = tipoUsuarioLogica.consultarTiposUsuariosPorId(tusuCodigo);
		assertNotNull("El tipo de documento no existe",tiposUsuarios);
		
		tipoUsuarioLogica.borrar(tiposUsuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {		
		List<TiposUsuarios> losTiposUsuarios=tipoUsuarioLogica.consultarTodos();
		for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
			log.info(tiposUsuarios.getTusuNombre());
		}
	}

}