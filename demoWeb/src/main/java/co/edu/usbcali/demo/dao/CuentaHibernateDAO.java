package co.edu.usbcali.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.demo.modelo.Cuentas;

@Repository
@Scope("singleton")
public class CuentaHibernateDAO implements ICuentaDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void grabar(Cuentas cuentas) {
		sessionFactory.getCurrentSession().save(cuentas);

	}

	@Override
	public void modificar(Cuentas cuentas) {
		sessionFactory.getCurrentSession().update(cuentas);

	}

	@Override
	public void borrar(Cuentas cuentas) {
		sessionFactory.getCurrentSession().delete(cuentas);

	}

	@Override
	public Cuentas consultarCuentasPorId(String cueNumero) {
		return sessionFactory.getCurrentSession().get(Cuentas.class, cueNumero);
	}

	@Override
	public List<Cuentas> consultarTodos() {
		return sessionFactory.getCurrentSession().createCriteria(Cuentas.class).list();
	}

	@Override
	public List<Cuentas> consultarCuentasPorCliId(Long cliId) {
		String hql="SELECT cue FROM Cuentas cue where cue.clientes.cliId="+cliId;
		List<Cuentas> lasCuentas=sessionFactory.getCurrentSession().createQuery(hql).list();
		return lasCuentas;
	}
}
