package co.edu.usbcali.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.demo.modelo.Usuarios;

@Repository
@Scope("singleton")
public class UsuarioHibernateDAO implements IUsuarioDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void grabar(Usuarios usuarios) {
		sessionFactory.getCurrentSession().save(usuarios);

	}

	@Override
	public void modificar(Usuarios usuarios) {
		sessionFactory.getCurrentSession().update(usuarios);

	}

	@Override
	public void borrar(Usuarios usuarios) {
		sessionFactory.getCurrentSession().delete(usuarios);

	}

	@Override
	public Usuarios consultarUsuariosPorId(long usuCedula) {
		return sessionFactory.getCurrentSession().get(Usuarios.class, usuCedula);
	}

	@Override
	public List<Usuarios> consultarTodos() {
		return sessionFactory.getCurrentSession().createCriteria(Usuarios.class).list();
	}

}
