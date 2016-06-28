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

import co.edu.usbcali.demo.logica.ITipoDocumentoLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoDocumentoLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(TipoDocumentoLogicaTest.class);
	private long tdocCodigo=40;
	
	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(tdocCodigo);
		tiposDocumentos.setTdocNombre("Pasaporte");
		
		tipoDocumentoLogica.grabar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTiposDocumentosPorId(tdocCodigo);								
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTiposDocumentosPorId(tdocCodigo);
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		tiposDocumentos.setTdocNombre("NIT");
		
		tipoDocumentoLogica.modificar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTiposDocumentosPorId(tdocCodigo);
		assertNotNull("El tipo de documento no existe",tiposDocumentos);
		
		tipoDocumentoLogica.borrar(tiposDocumentos);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {		
		List<TiposDocumentos> losTiposDocumentos=tipoDocumentoLogica.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info(tiposDocumentos.getTdocNombre());
		}		
	}

}