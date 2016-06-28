package co.edu.usbcali.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class UsuarioVista {

	private final static Logger log=LoggerFactory.getLogger(UsuarioVista.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;
	private List<Usuarios> losUsuarios;
	private List<SelectItem> losTiposUsuarioItems;
	
	private InputText txtUsuNombre;
	private InputText txtUsuLogin;
	private InputText txtUsuClave;

	private SelectOneMenu somTipoUsuario;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private InputText txtUsuCedula;

	public String crearAction(){
		log.info("Ingreso a crear");
		
		try {
			
			Usuarios usuarios = new Usuarios();
			
			usuarios.setUsuCedula(Long.parseLong(txtUsuCedula.getValue().toString().trim()));
			usuarios.setUsuNombre(txtUsuNombre.getValue().toString().trim());
			usuarios.setUsuLogin(txtUsuLogin.getValue().toString().trim());
			usuarios.setUsuClave(txtUsuClave.getValue().toString().trim());
			
			TiposUsuarios tiposUsuarios = delegadoDeNegocio.consultarTiposUsuariosPorId(Long.parseLong(somTipoUsuario.getValue().toString().trim()));
			usuarios.setTiposUsuarios(tiposUsuarios);
			
			delegadoDeNegocio.grabarUsuarios(usuarios);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se creo con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String modificarAction(){
		log.info("Ingreso a modificar");
		
		try {
			
			Usuarios usuarios = new Usuarios();
			
			usuarios.setUsuCedula(Long.parseLong(txtUsuCedula.getValue().toString().trim()));
			usuarios.setUsuNombre(txtUsuNombre.getValue().toString().trim());
			usuarios.setUsuLogin(txtUsuLogin.getValue().toString().trim());
			usuarios.setUsuClave(txtUsuClave.getValue().toString().trim());
			
			TiposUsuarios tiposUsuarios = delegadoDeNegocio.consultarTiposUsuariosPorId(Long.parseLong(somTipoUsuario.getValue().toString().trim()));
			usuarios.setTiposUsuarios(tiposUsuarios);
			
			delegadoDeNegocio.modificarUsuarios(usuarios);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se modifico con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String borrarAction(){
		log.info("Ingreso a borrar");
		
		try {
			
			Usuarios usuarios = new Usuarios();
			
			usuarios.setUsuCedula(Long.parseLong(txtUsuCedula.getValue().toString().trim()));
			
			delegadoDeNegocio.borrarUsuarios(usuarios);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se elimino con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String limpiarAction(){
		log.info("Ingreso a limpiar");
		
		txtUsuCedula.resetValue();
		txtUsuNombre.resetValue();
		txtUsuLogin.resetValue();
		txtUsuClave.resetValue();
		somTipoUsuario.setValue("-1");
		

		btnBorrar.setDisabled(true);
		btnCrear.setDisabled(false);
		btnModificar.setDisabled(true);
		
		return "";
	}
	
	public void txtUsuCedulaListener() {
		log.info("Ingreso a Listener");
		
		Usuarios entity = null;

		try {
			Long usuCedula = Long.parseLong(txtUsuCedula.getValue().toString().trim());
			entity = delegadoDeNegocio.consultarUsuariosPorId(usuCedula);
		} catch (Exception e) {
		}

		if (entity == null) {
			
			txtUsuNombre.resetValue();
			txtUsuLogin.resetValue();
			txtUsuClave.resetValue();
			somTipoUsuario.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);
			
		} else {

			txtUsuNombre.setValue(entity.getUsuNombre());
			txtUsuLogin.setValue(entity.getUsuLogin());
			txtUsuClave.setValue(entity.getUsuClave());
			
		    somTipoUsuario.setValue(entity.getTiposUsuarios().getTusuCodigo());
		    
			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);

		}
	}

	public InputText getTxtUsuCedula() {
		return txtUsuCedula;
	}

	public void setTxtUsuCedula(InputText txtUsuCedula) {
		this.txtUsuCedula = txtUsuCedula;
	}
	
	public InputText getTxtUsuNombre() {
		return txtUsuNombre;
	}

	public void setTxtUsuNombre(InputText txtUsuNombre) {
		this.txtUsuNombre = txtUsuNombre;
	}

	public InputText getTxtUsuLogin() {
		return txtUsuLogin;
	}

	public void setTxtUsuLogin(InputText txtUsuLogin) {
		this.txtUsuLogin = txtUsuLogin;
	}

	public InputText getTxtUsuClave() {
		return txtUsuClave;
	}

	public void setTxtUsuClave(InputText txtUsuClave) {
		this.txtUsuClave = txtUsuClave;
	}

	public SelectOneMenu getSomTipoUsuario() {
		return somTipoUsuario;
	}

	public void setSomTipoUsuario(SelectOneMenu somTipoUsuario) {
		this.somTipoUsuario = somTipoUsuario;
	}

	public CommandButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}
	
	public List<SelectItem> getLosTiposUsuarioItems() {
		
		if (losTiposUsuarioItems == null) {
			losTiposUsuarioItems = new ArrayList<SelectItem>();
			try {
				List<TiposUsuarios> losTiposUsuarios = delegadoDeNegocio.consultarTodosTiposUsuarios();
				for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
					losTiposUsuarioItems.add(new SelectItem(tiposUsuarios.getTusuCodigo(), tiposUsuarios.getTusuNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		return losTiposUsuarioItems;
	}

	public void setLosTiposUsuarioItems(List<SelectItem> losTiposUsuarioItems) {
		this.losTiposUsuarioItems = losTiposUsuarioItems;
	}	

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	
	public List<Usuarios> getLosUsuarios() {
		
		if (losUsuarios == null) {
			try {
				losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();						
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return losUsuarios;
	}

	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}	

}
