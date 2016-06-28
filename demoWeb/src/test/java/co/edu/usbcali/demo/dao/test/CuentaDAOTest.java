package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentaDAOTest.class);
	private Long cliId=101234L;
	private String cueNumero="4008-5305-0085";	
	
	@Autowired
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Cuentas cuentas = new Cuentas();
		cuentas.setCueNumero(cueNumero);
		cuentas.setCueSaldo(new BigDecimal(100000));
		cuentas.setCueActiva("S");
		cuentas.setCueClave("1234");
		
		Clientes clientes = clienteDAO.consultarClientePorId(cliId);
		
		cuentas.setClientes(clientes);
				
		cuentaDAO.grabar(cuentas);	
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);					
		assertNotNull("La cuenta no existe",cuentas);
		log.info(cuentas.getCueNumero());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);						
		assertNotNull("La cuenta no existe",cuentas);		
		cuentas.setCueSaldo(new BigDecimal(100001));
		
		cuentaDAO.modificar(cuentas);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);						
		assertNotNull("La cuenta no existe",cuentas);		
		
		cuentaDAO.borrar(cuentas);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Cuentas> lasCuentas=cuentaDAO.consultarTodos();
		for (Cuentas cuentas : lasCuentas) {
			log.info(cuentas.getCueNumero());
		}
	}

}
