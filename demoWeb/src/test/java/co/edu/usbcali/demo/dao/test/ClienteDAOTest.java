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

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ClienteDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ClienteDAOTest.class);
	private Long cliId=142021L;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Clientes clientes = new Clientes();
		clientes.setCliId(cliId);
		clientes.setCliNombre("Juan Perez");
		clientes.setCliTelefono("4545456");
		clientes.setCliDireccion("Avenida Sherman Sidney");
		clientes.setCliMail("jperez@gmail.com");
		
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTiposDocumentosPorId(20L);
		
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.grabar(clientes);	
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		Clientes clientes = clienteDAO.consultarClientePorId(cliId);	
		assertNotNull("El cliente no existe",clientes);
		log.info(clientes.getCliNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		Clientes clientes = clienteDAO.consultarClientePorId(cliId);		
		assertNotNull("El cliente no existe",clientes);
		clientes.setCliNombre("Homero J Simpson");
		
		clienteDAO.modificar(clientes);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		Clientes clientes = clienteDAO.consultarClientePorId(cliId);		
		assertNotNull("El cliente no existe",clientes);		
		
		clienteDAO.borrar(clientes);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Clientes> losClientes=clienteDAO.consultarTodos();
		for (Clientes clientes : losClientes) {
			log.info(clientes.getCliNombre());
		}
	}

}