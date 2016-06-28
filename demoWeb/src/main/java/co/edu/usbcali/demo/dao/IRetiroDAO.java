package co.edu.usbcali.demo.dao;

import java.util.List;

import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;

public interface IRetiroDAO {
	
	public void grabar(Retiros retiros);
	public void modificar(Retiros retiros);
	public void borrar(Retiros retiros);
	public Retiros consultarRetirosPorId(RetirosId retirosId);
	public List<Retiros> consultarTodos();
	public String ObtenerSecuencia();

}
