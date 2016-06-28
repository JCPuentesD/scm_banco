package co.edu.usbcali.demo.dao.test;

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

import co.edu.usbcali.demo.dao.IConsignacionDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IUsuarioDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionDAOTest.class);
	private long conCodigo=15;
	private String cueNumero="4008-5305-0080";	
	private long usuCedula=25;	
	
	@Autowired
	private IConsignacionDAO consignacionDAO;
	
	@Autowired
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Consignaciones consignaciones = new Consignaciones();
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		conCodigo = Long.parseLong(consignacionDAO.ObtenerSecuencia());
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		consignaciones.setId(consignacionesId);
		consignaciones.setConValor(new BigDecimal(100000));
		consignaciones.setConFecha(new Date());
		consignaciones.setConDescripcion("APERTURA DE CUENTA");
		
		Usuarios usuarios = usuarioDAO.consultarUsuariosPorId(usuCedula);
		
		consignaciones.setUsuarios(usuarios);
		
		consignacionDAO.grabar(consignaciones);	
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionDAO.consultarConsignacionesPorId(consignacionesId);					
		assertNotNull("La consignación no existe",consignaciones);
		log.info("Código: "+consignaciones.getId().getConCodigo()+" Número Cuenta: "+consignaciones.getId().getCueNumero()+" Valor: "+consignaciones.getConValor());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionDAO.consultarConsignacionesPorId(consignacionesId);						
		assertNotNull("La consignación no existe",consignaciones);
		consignaciones.setConValor(new BigDecimal(100001));		
		
		consignacionDAO.modificar(consignaciones);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		ConsignacionesId consignacionesId = new ConsignacionesId();
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		conCodigo = Long.parseLong(consignacionDAO.ObtenerSecuencia()) - 1;
		
		consignacionesId.setConCodigo(conCodigo);
		consignacionesId.setCueNumero(cuentas.getCueNumero());
		
		Consignaciones consignaciones = consignacionDAO.consultarConsignacionesPorId(consignacionesId);
		assertNotNull("La consignación no existe",consignaciones);				
		
		consignacionDAO.borrar(consignaciones);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Consignaciones> lasConsignaciones=consignacionDAO.consultarTodos();
		for (Consignaciones consignaciones : lasConsignaciones) {
			log.info("Código: "+consignaciones.getId().getConCodigo()+" Número Cuenta: "+consignaciones.getId().getCueNumero()+" Valor: "+consignaciones.getConValor());			
		}
	}

}