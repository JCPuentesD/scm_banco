package co.edu.usbcali.demo.logica.test;

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

import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(CuentaLogicaTest.class);
	private Long cliId=101234L;
	private String cueNumero="4008-5305-0085";
	
	@Autowired
	private ICuentaLogica cuentaLogica;
	
	@Autowired
	private IClienteLogica clienteLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		Cuentas cuentas = new Cuentas();
		cuentas.setCueNumero(cueNumero);
		cuentas.setCueSaldo(new BigDecimal(100000));
		cuentas.setCueActiva("S");
		cuentas.setCueClave("1234");
		
		Clientes clientes = clienteLogica.consultarClientesPorId(cliId);				
		
		cuentas.setClientes(clientes);
				
		cuentaLogica.grabar(cuentas);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);					
		assertNotNull("La cuenta no existe",cuentas);
		log.info(cuentas.getCueNumero());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);						
		assertNotNull("La cuenta no existe",cuentas);		
		cuentas.setCueSaldo(new BigDecimal(100001));
		
		cuentaLogica.modificar(cuentas);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);						
		assertNotNull("La cuenta no existe",cuentas);		
		
		cuentaLogica.borrar(cuentas);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Cuentas> lasCuentas=cuentaLogica.consultarTodos();
		for (Cuentas cuentas : lasCuentas) {
			log.info(cuentas.getCueNumero());
		}
	}
	
	@Test
	@Transactional(readOnly=true)
	public void ftest() throws Exception {
		List<Cuentas> lasCuentas=cuentaLogica.consultarCuentasPorCliente(cliId);
		for (Cuentas cuentas : lasCuentas) {
			log.info(cuentas.getCueNumero());
		}
	}

}
