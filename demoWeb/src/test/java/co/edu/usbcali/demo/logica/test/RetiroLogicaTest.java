package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
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

import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.logica.IRetiroLogica;
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetiroLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(RetiroLogicaTest.class);
	private long retCodigo=15;
	private String cueNumero="4008-5305-0080";
	private long usuCedula=25;
	
	@Autowired
	private IRetiroLogica retiroLogica;
	
	@Autowired
	private ICuentaLogica cuentaLogica;
	
	@Autowired
	private IUsuarioLogica usuarioLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		RetirosId retirosId = new RetirosId();
		Retiros retiros = new Retiros();
		
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		retCodigo = Long.parseLong(retiroLogica.ObtenerSecuencia());
		
		retirosId.setRetCodigo(retCodigo);		
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		retiros.setId(retirosId);
		retiros.setRetValor(new BigDecimal(100000));
		retiros.setRetFecha(new Date());
		retiros.setRetDescripcion("RETIRO DE CUENTA");
		
		Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
		
		retiros.setUsuarios(usuarios);
		
		retiroLogica.grabar(retiros);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		RetirosId retirosId = new RetirosId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroLogica.consultarRetirosPorId(retirosId);					
		assertNotNull("El retiro no existe",retiros);
		log.info("Código ret: "+retiros.getId().getRetCodigo()+" Número Cuenta: "+retiros.getId().getCueNumero()+" Valor: "+retiros.getRetValor());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		RetirosId retirosId = new RetirosId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroLogica.consultarRetirosPorId(retirosId);						
		assertNotNull("La consignación no existe",retiros);
		retiros.setRetValor(new BigDecimal(100001));		
		
		retiroLogica.modificar(retiros);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		RetirosId retirosId = new RetirosId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		retCodigo = Long.parseLong(retiroLogica.ObtenerSecuencia()) - 1;
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroLogica.consultarRetirosPorId(retirosId);
		assertNotNull("El retiro no existe",retiros);				
		
		retiroLogica.borrar(retiros);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Retiros> lasRetiros=retiroLogica.consultarTodos();
		for (Retiros retiros : lasRetiros) {
			log.info("Código: "+retiros.getId().getRetCodigo()+" Número Cuenta: "+retiros.getId().getCueNumero()+" Valor: "+retiros.getRetValor());			
		}
	}

}