package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;

public interface IConsignacionLogica {
	
	public void grabar(Consignaciones consignaciones) throws Exception;
	public void modificar(Consignaciones consignaciones) throws Exception;
	public void borrar(Consignaciones consignaciones) throws Exception;
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId consignacionesId) throws Exception;
	public List<Consignaciones> consultarTodos() throws Exception;
	public String ObtenerSecuencia() throws Exception;

}
