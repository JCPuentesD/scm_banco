package co.edu.usbcali.demo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;

@Repository
@Scope("singleton")
public class RetiroHibernateDAO implements IRetiroDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void grabar(Retiros retiros) {
		sessionFactory.getCurrentSession().save(retiros);

	}

	@Override
	public void modificar(Retiros retiros) {
		sessionFactory.getCurrentSession().update(retiros);

	}

	@Override
	public void borrar(Retiros retiros) {
		sessionFactory.getCurrentSession().delete(retiros);

	}

	@Override
	public Retiros consultarRetirosPorId(RetirosId retirosId) {
		return sessionFactory.getCurrentSession().get(Retiros.class, retirosId);
	}

	@Override
	public List<Retiros> consultarTodos() {
		return sessionFactory.getCurrentSession().createCriteria(Retiros.class).list();
	}

	@Override
	public String ObtenerSecuencia() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select nextval('sq_retiros')");
		
		return query.uniqueResult().toString();
	}

}
