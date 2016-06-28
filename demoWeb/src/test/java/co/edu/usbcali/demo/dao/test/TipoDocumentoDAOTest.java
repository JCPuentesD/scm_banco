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

import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoDocumentoDAOTest {

	private static final Logger log=LoggerFactory.getLogger(TipoDocumentoDAOTest.class);
	private long tdocCodigo=40;
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(tdocCodigo);
		tiposDocumentos.setTdocNombre("Pasaporte");
		
		tipoDocumentoDAO.grabar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTiposDocumentosPorId(tdocCodigo);				
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTiposDocumentosPorId(tdocCodigo);
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		tiposDocumentos.setTdocNombre("NIT");
		
		tipoDocumentoDAO.modificar(tiposDocumentos);		
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTiposDocumentosPorId(tdocCodigo);
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		
		tipoDocumentoDAO.borrar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposDocumentos> losTiposDocumentos=tipoDocumentoDAO.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info(tiposDocumentos.getTdocNombre());
		}
	}	
}
