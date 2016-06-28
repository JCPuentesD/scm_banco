package co.edu.usbcali.demo.logica;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class TransaccionLogica implements ITransaccionLogica {
	
	private Logger log = LoggerFactory.getLogger(TransaccionLogica.class);
	private long conCodigo=0;
	private long retCodigo=0;
	
	@Autowired
	private IConsignacionLogica consignacionLogica;
	
	@Autowired
	private IRetiroLogica retiroLogica;
	
	@Autowired
	private IClienteLogica clienteLogica;
	
	@Autowired
	private IUsuarioLogica usuarioLogica;
	
	@Autowired
	private ICuentaLogica cuentaLogica;
	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void consignar(String cueNumero, long cliId, long usuCedula, BigDecimal conValor) throws Exception {

		if(conValor.intValue()<=0) {			
			throw new Exception("El valor a consignar no puede ser negativo");			
		}
				
		Clientes clientes = clienteLogica.consultarClientesPorId(cliId);
		
		if(clientes==null){
			throw new Exception("El cliente no existe o no ha sido ingresado");
		}
			
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		if(cuentas==null){
			throw new Exception("La cuenta no existe o no ha sido ingresada");
		}
		
		if(cuentas.getClientes().getCliId() == clientes.getCliId()) {
			
			Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
			
			if(usuarios==null){
				throw new Exception("El usuario no existe o no ha sido seleccionado");
			}
			
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");			
			String strFecha = formatoFecha.format(new Date());
			Date fecha = formatoFecha.parse(strFecha);
			
			ConsignacionesId consignacionesId = new ConsignacionesId();
			
			conCodigo = Long.parseLong(consignacionLogica.ObtenerSecuencia()); 
			
			consignacionesId.setConCodigo(conCodigo);
			consignacionesId.setCueNumero(cuentas.getCueNumero());
			
			Consignaciones consignaciones = new Consignaciones();
			
			consignaciones.setId(consignacionesId);
			consignaciones.setConValor(conValor);
			consignaciones.setConFecha(fecha);
			consignaciones.setConDescripcion("CONSIGNACION DE CUENTA");
			consignaciones.setUsuarios(usuarios);
			
			consignacionLogica.grabar(consignaciones);
			log.info("consignación exitosa");
			
			//Se debita la cuenta del usuario
			Double saldoCuenta = cuentas.getCueSaldo().doubleValue() + conValor.doubleValue();
			cuentas.setCueSaldo(new BigDecimal(saldoCuenta));
			
			cuentaLogica.modificar(cuentas);
			log.info("cuenta acredita exitosamente");
			
		} else {
			throw new Exception("La cuenta no corresponde al cliente: " + clientes.getCliNombre());
		}
		
	}
	
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void retirar(String cueNumero, long cliId, long usuCedula, BigDecimal retValor) throws Exception {
		
		if(retValor.intValue()<=0) {			
			throw new Exception("El valor a retirar no puede ser negativo");			
		}
				
		Clientes clientes = clienteLogica.consultarClientesPorId(cliId);
		
		if(clientes==null){
			throw new Exception("El cliente no existe o no ha sido ingresado");
		}
			
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId(cueNumero);
		
		if(cuentas==null){
			throw new Exception("La cuenta no existe o no ha sido ingresada");
		}
		
		if(cuentas.getClientes().getCliId() == clientes.getCliId()) {
			
			Usuarios usuarios = usuarioLogica.consultarUsuariosPorId(usuCedula);
			
			if(usuarios==null){
				throw new Exception("El usuario no existe o no ha sido seleccionado");
			}
			
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");			
			String strFecha = formatoFecha.format(new Date());
			Date fecha = formatoFecha.parse(strFecha);
			
			RetirosId retirosId = new RetirosId();
			
			retCodigo = Long.parseLong(retiroLogica.ObtenerSecuencia());
			
			retirosId.setRetCodigo(retCodigo);		
			retirosId.setCueNumero(cuentas.getCueNumero());
			
			Retiros retiros = new Retiros();
			
			retiros.setId(retirosId);
			retiros.setRetValor(retValor);
			retiros.setRetFecha(fecha);
			retiros.setRetDescripcion("RETIRO DE CUENTA");		
			retiros.setUsuarios(usuarios);
			
			//Se debita la cuenta del usuario
			Double saldoCuenta = cuentas.getCueSaldo().doubleValue() - retValor.doubleValue();
			
			if(saldoCuenta<0) {
				throw new Exception("Fondos insuficientes para realizar el retiro");
			}
			
			cuentas.setCueSaldo(new BigDecimal(saldoCuenta));
			
			cuentaLogica.modificar(cuentas);
			log.info("cuenta debitada exitosamente");
			
			retiroLogica.grabar(retiros);
			log.info("retiro exitoso");
			
		} else {
			throw new Exception("La cuenta no corresponde al cliente: " + clientes.getCliNombre());
		}
	}	

}
