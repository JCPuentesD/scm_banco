package co.edu.usbcali.demo.delegado;

import java.math.BigDecimal;
import java.util.List;

import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

public interface IDelegadoDeNegocio {
	
	public void grabarClientes(Clientes clientes) throws Exception;
	public void modificarClientes(Clientes clientes) throws Exception;
	public void borrarClientes(Clientes clientes) throws Exception;
	public Clientes consultarClientesPorId(long cliId) throws Exception;
	public List<Clientes> consultarTodosClientes() throws Exception;
	
	public void grabarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception;
	public void modificarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception;
	public void borrarTiposDocumentos(TiposDocumentos tiposDocumentos) throws Exception;
	public TiposDocumentos consultarTiposDocumentosPorId(long tdocCodigo) throws Exception;
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception;
	
	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;
	public TiposUsuarios consultarTiposUsuariosPorId(long tusuCodigo) throws Exception;
	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception;
	
	public void grabarUsuarios(Usuarios usuarios) throws Exception;
	public void modificarUsuarios(Usuarios usuarios) throws Exception;
	public void borrarUsuarios(Usuarios usuarios) throws Exception;
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception;
	public List<Usuarios> consultarTodosUsuarios() throws Exception;
	
	public void consignar(String cueNumero, long cliId, long usuCedula, BigDecimal conValor) throws Exception;
	public void retirar(String cueNumero, long cliId, long usuCedula, BigDecimal retValor) throws Exception;
	
	public List<Consignaciones> consultarTodosConsignaciones() throws Exception;
	public List<Retiros> consultarTodosRetiros() throws Exception;
	public List<Cuentas> consultarCuentasPorCliente(long cliId) throws Exception;
	
	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception;

}
