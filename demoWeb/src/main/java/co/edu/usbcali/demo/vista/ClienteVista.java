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
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class ClienteVista {

	private final static Logger log=LoggerFactory.getLogger(ClienteVista.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;
	private List<Clientes> losClientes;
	private List<SelectItem> losTiposDocumentoItems;

	private InputText txtNombre;
	private InputText txtDireccion;
	private InputText txtTelefono;
	private InputText txtMail;

	private SelectOneMenu somTipoDocumento;

	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;

	private InputText txtIdentificacion;
	
	public String crearAction(){
		log.info("Ingreso a crear");
		
		try {
			Clientes clientes=new Clientes();
			clientes.setCliDireccion(txtDireccion.getValue().toString().trim());
			clientes.setCliId(Long.parseLong(txtIdentificacion.getValue().toString().trim()));
			clientes.setCliMail(txtMail.getValue().toString().trim());
			clientes.setCliNombre(txtNombre.getValue().toString().trim());
			clientes.setCliTelefono(txtTelefono.getValue().toString().trim());
			TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorId(Long.parseLong(somTipoDocumento.getValue().toString().trim()));					
			clientes.setTiposDocumentos(tiposDocumentos);
			
			delegadoDeNegocio.grabarClientes(clientes);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se creo con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String modificarAction(){
		log.info("Ingreso a modificar");
		
		try {
			Clientes clientes=new Clientes();
			clientes.setCliDireccion(txtDireccion.getValue().toString().trim());
			clientes.setCliId(Long.parseLong(txtIdentificacion.getValue().toString().trim()));
			clientes.setCliMail(txtMail.getValue().toString().trim());
			clientes.setCliNombre(txtNombre.getValue().toString().trim());
			clientes.setCliTelefono(txtTelefono.getValue().toString().trim());
			TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorId(Long.parseLong(somTipoDocumento.getValue().toString().trim()));					
			clientes.setTiposDocumentos(tiposDocumentos);
			
			delegadoDeNegocio.modificarClientes(clientes);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se modifico con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String borrarAction(){
		log.info("Ingreso a borrar");
		
		try {
			Clientes clientes=new Clientes();
			
			clientes.setCliId(Long.parseLong(txtIdentificacion.getValue().toString().trim()));
			
			delegadoDeNegocio.borrarClientes(clientes);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El cliente se elimino con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String limpiarAction(){
		log.info("Ingreso a limpiar");
		
		txtDireccion.resetValue();
		txtMail.resetValue();
		txtNombre.resetValue();
		txtTelefono.resetValue();
		somTipoDocumento.setValue("-1");
		txtIdentificacion.resetValue();

		btnBorrar.setDisabled(true);
		btnCrear.setDisabled(false);
		btnModificar.setDisabled(true);
		
		return "";
	}
	
	public void txtIdentificacionListener() {
		Clientes entity = null;

		try {
			Long cliId = Long.parseLong(txtIdentificacion.getValue().toString().trim());
			entity = delegadoDeNegocio.consultarClientesPorId(cliId);					
		} catch (Exception e) {
		}

		if (entity == null) {
			
			txtDireccion.resetValue();
			txtMail.resetValue();
			txtNombre.resetValue();
			txtTelefono.resetValue();
			somTipoDocumento.setValue("-1");

			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);
			
		} else {

			txtDireccion.setValue(entity.getCliDireccion());
			txtMail.setValue(entity.getCliMail());
			txtNombre.setValue(entity.getCliNombre());
			txtTelefono.setValue(entity.getCliTelefono());

			somTipoDocumento.setValue(entity.getTiposDocumentos().getTdocCodigo());

			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);

		}
	}

	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
	}

	public SelectOneMenu getSomTipoDocumento() {
		return somTipoDocumento;
	}

	public void setSomTipoDocumento(SelectOneMenu somTipoDocumento) {
		this.somTipoDocumento = somTipoDocumento;
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

	public List<SelectItem> getLosTiposDocumentoItems() {
		if (losTiposDocumentoItems == null) {
			losTiposDocumentoItems = new ArrayList<SelectItem>();
			try {
				List<TiposDocumentos> losTiposDocumentos = delegadoDeNegocio.consultarTodosTiposDocumentos();
				for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
					losTiposDocumentoItems
							.add(new SelectItem(tiposDocumentos.getTdocCodigo(), tiposDocumentos.getTdocNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return losTiposDocumentoItems;
	}

	public void setLosTiposDocumentoItems(List<SelectItem> losTiposDocumentoItems) {
		this.losTiposDocumentoItems = losTiposDocumentoItems;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<Clientes> getLosClientes() {

		if (losClientes == null) {
			try {
				losClientes = delegadoDeNegocio.consultarTodosClientes();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return losClientes;
	}

	public void setLosClientes(List<Clientes> losClientes) {
		this.losClientes = losClientes;
	}

}
