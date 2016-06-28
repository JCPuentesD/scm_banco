package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.TiposUsuarios;

public interface ITipoUsuarioLogica {
	
	public void grabar(TiposUsuarios tiposUsuarios) throws Exception;
	public void modificar(TiposUsuarios tiposUsuarios) throws Exception;
	public void borrar(TiposUsuarios tiposUsuarios) throws Exception;
	public TiposUsuarios consultarTiposUsuariosPorId(long tusuCodigo) throws Exception;
	public List<TiposUsuarios> consultarTodos() throws Exception;

}