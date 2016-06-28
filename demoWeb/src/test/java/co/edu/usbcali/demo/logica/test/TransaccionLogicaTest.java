package co.edu.usbcali.demo.logica.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.logica.ITransaccionLogica;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TransaccionLogicaTest {
	
	private String cueNumero="4008-5305-0080";	
	private long usuCedula=25;
	private long cliId=801234;
	private BigDecimal valor= new BigDecimal("100000");
	
	@Autowired 
	private ITransaccionLogica transaccionLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		transaccionLogica.consignar(cueNumero, cliId, usuCedula, valor);		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest() throws Exception {
		transaccionLogica.retirar(cueNumero, cliId, usuCedula, valor);		
	}

}
