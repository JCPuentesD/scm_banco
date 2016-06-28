package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;

public interface IRetiroLogica {
	
	public void grabar(Retiros retiros) throws Exception;
	public void modificar(Retiros retiros) throws Exception;
	public void borrar(Retiros retiros) throws Exception;
	public Retiros consultarRetirosPorId(RetirosId retirosId) throws Exception;
	public List<Retiros> consultarTodos() throws Exception;
	public String ObtenerSecuencia() throws Exception;

}
