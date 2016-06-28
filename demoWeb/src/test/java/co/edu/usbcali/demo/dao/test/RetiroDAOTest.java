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

import co.edu.usbcali.demo.dao.IRetiroDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IUsuarioDAO;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetiroDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(RetiroDAOTest.class);
	private long retCodigo=15;
	private String cueNumero="4008-5305-0080";
	private long usuCedula=25;	
		
	@Autowired
	private IRetiroDAO retiroDAO;
	
	@Autowired
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		RetirosId retirosId = new RetirosId();
		Retiros retiros = new Retiros();
		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		retCodigo = Long.parseLong(retiroDAO.ObtenerSecuencia());
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		retiros.setId(retirosId);
		retiros.setRetValor(new BigDecimal(100000));
		retiros.setRetFecha(new Date());
		retiros.setRetDescripcion("RETIRO DE CUENTA");
		
		Usuarios usuarios = usuarioDAO.consultarUsuariosPorId(usuCedula);
		
		retiros.setUsuarios(usuarios);
		
		retiroDAO.grabar(retiros);	
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		
		RetirosId retirosId = new RetirosId();		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroDAO.consultarRetirosPorId(retirosId);					
		assertNotNull("El retiro no existe",retiros);
		log.info("Código ret: "+retiros.getId().getRetCodigo()+" Número Cuenta: "+retiros.getId().getCueNumero()+" Valor: "+retiros.getRetValor());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		
		RetirosId retirosId = new RetirosId();		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroDAO.consultarRetirosPorId(retirosId);						
		assertNotNull("El retiro no existe",retiros);
		retiros.setRetValor(new BigDecimal(100001));				
		
		retiroDAO.modificar(retiros);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		
		RetirosId retirosId = new RetirosId();		
		Cuentas cuentas = cuentaDAO.consultarCuentasPorId(cueNumero);
		
		retCodigo = Long.parseLong(retiroDAO.ObtenerSecuencia()) - 1;
		
		retirosId.setRetCodigo(retCodigo);
		retirosId.setCueNumero(cuentas.getCueNumero());
		
		Retiros retiros = retiroDAO.consultarRetirosPorId(retirosId);
		assertNotNull("El retiro no existe",retiros);				
		
		retiroDAO.borrar(retiros);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Retiros> losRetiros=retiroDAO.consultarTodos();
		for (Retiros retiros : losRetiros) {
			log.info("Código: "+retiros.getId().getRetCodigo()+" Número Cuenta: "+retiros.getId().getCueNumero()+" Valor: "+retiros.getRetValor());			
		}
	}

}