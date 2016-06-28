package co.edu.usbcali.demo.delegado;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.IConsignacionLogica;
import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.logica.IRetiroLogica;
import co.edu.usbcali.demo.logica.ITipoDocumentoLogica;
import co.edu.usbcali.demo.logica.ITipoUsuarioLogica;
import co.edu.usbcali.demo.logica.ITransaccionLogica;
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@Scope("singleton")
@Component("delegadoDeNegocio")
public class DelegadoDeNegocio implements IDelegadoDeNegocio {

	@Autowired
	IClienteLogica clienteLogica;
	
	@Autowired
	ITipoDocumentoLogica tiposDocumentosLogica;
	
	@Autowired
	ITipoUsuarioLogica tipoUsuarioLogica;
	
	@Autowired
	IUsuarioLogica usuarioLogica;
	
	@Autowired
	ITransaccionLogica transaccionLogica;
	
	@Autowired
	IConsignacionLogica consignacionLogica;
	
	@Autowired
	IRetiroLogica retiroLogica;
	
	@Autowired
	ICuentaLogica cuentaLogica;
	
	@Override
	public void grabarClientes(Clientes clientes) throws Exception {
		clienteLogica.grabar(clientes);		
	}

	@Override
	public void modificarClientes(Clientes clientes) throws Exception {
		clienteLogica.modificar(clientes);		
	}

	@Override
	public void borrarClientes(Clientes clientes) throws Exception {
		clienteLogica.borrar(clientes);		
	}

	@Override
	public Clientes consultarClientesPorId(long cliId) throws Exception {
		return clienteLogica.consultarClientesPorId(cliId);
	}

	@Override
	public List<Clientes> consultarTodosClientes() throws Exception {
		return clienteLogica.consultarTodos();
	}

	@Override
	public void grabarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.grabar(tiposDocumentos);		
	}

	@Override
	public void modificarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.modificar(tiposDocumentos);		
	}

	@Override
	public void borrarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception {
		tiposDocumentosLogica.borrar(tiposDocumentos);		
	}

	@Override
	public TiposDocumentos consultarTiposDocumentosPorId(long tdocCodigo) throws Exception {
		return tiposDocumentosLogica.consultarTiposDocumentosPorId(tdocCodigo);
	}

	@Override
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception {
		return tiposDocumentosLogica.consultarTodos();
	}

	@Override
	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.grabar(tiposUsuarios);		
	}

	@Override
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.modificar(tiposUsuarios);		
	}

	@Override
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.borrar(tiposUsuarios);
		
	}

	@Override
	public TiposUsuarios consultarTiposUsuariosPorId(long tusuCodigo) throws Exception {
		return tipoUsuarioLogica.consultarTiposUsuariosPorId(tusuCodigo);
	}

	@Override
	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception {
		return tipoUsuarioLogica.consultarTodos();
	}

	@Override
	public void grabarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.grabar(usuarios);		
	}

	@Override
	public void modificarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.modificar(usuarios);		
	}

	@Override
	public void borrarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.borrar(usuarios);		
	}

	@Override
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception {
		return usuarioLogica.consultarUsuariosPorId(usuCedula);
	}

	@Override
	public List<Usuarios> consultarTodosUsuarios() throws Exception {
		return usuarioLogica.consultarTodos();
	}

	@Override
	public void consignar(String cueNumero, long cliId, long usuCedula, BigDecimal conValor) throws Exception {
		transaccionLogica.consignar(cueNumero, cliId, usuCedula, conValor);
	}
	
	@Override
	public void retirar(String cueNumero, long cliId, long usuCedula, BigDecimal retValor) throws Exception {
		transaccionLogica.retirar(cueNumero, cliId, usuCedula, retValor);
	}

	@Override
	public List<Retiros> consultarTodosRetiros() throws Exception {
		return retiroLogica.consultarTodos();
	}

	@Override
	public List<Consignaciones> consultarTodosConsignaciones() throws Exception {
		return consignacionLogica.consultarTodos();
	}

	@Override
	public List<Cuentas> consultarCuentasPorCliente(long cliId) throws Exception {
		return cuentaLogica.consultarCuentasPorCliente(cliId);
	}

	@Override
	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception {
		return cuentaLogica.consultarCuentasPorId(cueNumero);
	}

}
