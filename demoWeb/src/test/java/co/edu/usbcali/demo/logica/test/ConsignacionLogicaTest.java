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

import co.edu.usbcali.demo.logica.IConsignacionLogica;
import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionLogicaTest {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionLogicaTest.class);
	private long conCodigo=15;
	private String cueNumero="4008-5305-0080";	
	private long usuCedula=25;
	
	@Autowired
	private IConsignacionLogica consignacionLogica;
	
	@Autowired
	private ICuentaLogica cuentaLogica;
	
	@Autowired
	private IUsuarioLogica usuarioLogica;

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		conCodigo = Long.parseLong(consignacionLogica.ObtenerSecuencia());
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = new Consignaciones();
		
		consignaciones.setId(consignacionesId);
		consignaciones.setConValor(new BigDecimal(100000));
		consignaciones.setConFecha(new Date());
		consignaciones.setConDescripcion("APERTURA DE CUENTA");
		
		Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
		
		consignaciones.setUsuarios(usuarios);
		
		consignacionLogica.grabar(consignaciones);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionLogica.consultarConsignacionesPorId(consignacionesId);					
		assertNotNull("La consignación no existe",consignaciones);
		log.info("Código: "+consignaciones.getId().getConCodigo()+" Número Cuenta: "+consignaciones.getId().getCueNumero()+" Valor: "+consignaciones.getConValor());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionLogica.consultarConsignacionesPorId(consignacionesId);						
		assertNotNull("La consignación no existe",consignaciones);
		consignaciones.setConValor(new BigDecimal(100001));		
		
		consignacionLogica.modificar(consignaciones);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		conCodigo = Long.parseLong(consignacionLogica.ObtenerSecuencia()) - 1;
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionLogica.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignación no existe",consignaciones);				
		
		consignacionLogica.borrar(consignaciones);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception {
		List<Consignaciones> lasConsignaciones=consignacionLogica.consultarTodos();
		for (Consignaciones consignaciones : lasConsignaciones) {
			log.info("Código: "+consignaciones.getId().getConCodigo()+" Número Cuenta: "+consignaciones.getId().getCueNumero()+" Valor: "+consignaciones.getConValor());			
		}
	}

}
