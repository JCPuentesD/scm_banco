package co.edu.usbcali.demo.vista;

import java.math.BigDecimal;
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
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class TransaccionVistaMejorada {

	private final static Logger log=LoggerFactory.getLogger(TransaccionVistaMejorada.class);
	
	@ManagedProperty(value = "#{delegadoDeNegocio}")
	IDelegadoDeNegocio delegadoDeNegocio;
	
	private String strCueNumero;
	private long numCliId;
	private long numUsuCedula;
	private BigDecimal bValor;
	private Long cliId;
	
	private List<Consignaciones> lasConsignaciones;
	private List<Retiros> losRetiros;
	private List<SelectItem> losUsuariosItems;
	private List<SelectItem> lasCuentasItems;

	private InputText txtCliId;	
	private InputText txtValor;
	
	private SelectOneMenu somCuenta;
	private SelectOneMenu somUsuario;	
		
	private CommandButton btnConsignar;
	private CommandButton btnRetirar;	
	private CommandButton btnLimpiar;	
	
	public String consignarAction(){
		log.info("Ingreso a consignar");
		
		try {
			
			if(somUsuario.getValue().toString().trim().compareTo("-1")==0) {
				throw new Exception("Debe seleccionar un usuario");
			}
			
			if(txtValor.getValue().toString().trim()=="") {				
				throw new Exception("Debe ingresar un valor");
			}
			
			strCueNumero = somCuenta.getValue().toString().trim();
			numCliId = Long.parseLong(txtCliId.getValue().toString().trim());
			numUsuCedula = Long.parseLong(somUsuario.getValue().toString().trim());
			
			bValor = new BigDecimal(txtValor.getValue().toString().trim());
			
			delegadoDeNegocio.consignar(strCueNumero, numCliId, numUsuCedula, bValor);
			
			lasConsignaciones = null;
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se realizó la consignación con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.info("Mensaje de Error: "+e.getMessage());
		}
		
		return "";
	}
	
	public String retirarAction(){
		log.info("Ingreso a retirar");
		
		try {
			
			if(txtValor.getValue().toString().trim()=="") {
				throw new Exception("Debe ingresar un valor");
			}
			
			if(somUsuario.getValue().toString().trim().compareTo("-1")==0) {
				throw new Exception("Debe seleccionar un usuario");
			}
			
			strCueNumero = somCuenta.getValue().toString().trim();
			numCliId = Long.parseLong(txtCliId.getValue().toString().trim());
			numUsuCedula = Long.parseLong(somUsuario.getValue().toString().trim());
			bValor = new BigDecimal(txtValor.getValue().toString().trim());
			
			delegadoDeNegocio.retirar(strCueNumero, numCliId, numUsuCedula, bValor);
			
			losRetiros = null;
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se realizó el retiro con exito", ""));
			
			limpiarAction();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.info("Mensaje de Error: "+e.getMessage());
		}
		
		return "";
	}
			
	public String limpiarAction(){
		log.info("Ingreso a limpiar");
		
		txtCliId.resetValue();
		txtValor.resetValue();
		
		somCuenta.setValue("-1");
		somUsuario.setValue("-1");
		
		return "";
	}
	
	public void txtCliIdListener() {
		log.info("Ingreso a txtCliIdListener");
		
		Clientes entity = null;

		try {
			cliId = Long.parseLong(txtCliId.getValue().toString().trim());
			entity = delegadoDeNegocio.consultarClientesPorId(cliId);					
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.info("Mensaje de Error: "+e.getMessage());
		}

		if (entity == null) {
			
			txtValor.resetValue();
			
			somUsuario.setValue("-1");
			somCuenta.setValue("-1");
			
			log.info("No encontro el cliente");
			
		} else {
			log.info("Cliente: " + entity.getCliNombre());
			
			//if (lasCuentasItems == null) {
				lasCuentasItems = new ArrayList<SelectItem>();
				try {
					List<Cuentas> lasCuentas = delegadoDeNegocio.consultarCuentasPorCliente(entity.getCliId());
					for (Cuentas cuentas : lasCuentas) {
						lasCuentasItems.add(new SelectItem(cuentas.getCueNumero(), cuentas.getCueNumero()));
						log.info("La cuenta: "+cuentas.getCueNumero());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			//}	
		}
	}
	
	public String getStrCueNumero() {
		return strCueNumero;
	}

	public void setStrCueNumero(String strCueNumero) {
		this.strCueNumero = strCueNumero;
	}

	public long getNumCliId() {
		return numCliId;
	}

	public void setNumCliId(long numCliId) {
		this.numCliId = numCliId;
	}

	public long getNumUsuCedula() {
		return numUsuCedula;
	}

	public void setNumUsuCedula(long numUsuCedula) {
		this.numUsuCedula = numUsuCedula;
	}

	public BigDecimal getbValor() {
		return bValor;
	}

	public void setbValor(BigDecimal bValor) {
		this.bValor = bValor;
	}

	public List<SelectItem> getLosUsuariosItems() {
		if (losUsuariosItems == null) {
			losUsuariosItems = new ArrayList<SelectItem>();
			try {
				List<Usuarios> losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
				for (Usuarios usuarios : losUsuarios) {
					losUsuariosItems.add(new SelectItem(usuarios.getUsuCedula(), usuarios.getUsuNombre()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return losUsuariosItems;
		
	}

	public void setLosUsuariosItems(List<SelectItem> losUsuariosItems) {
		this.losUsuariosItems = losUsuariosItems;
	}	

	public InputText getTxtCliId() {
		return txtCliId;
	}

	public void setTxtCliId(InputText txtCliId) {
		this.txtCliId = txtCliId;
	}

	public InputText getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(InputText txtValor) {
		this.txtValor = txtValor;
	}

	public SelectOneMenu getSomUsuario() {
		return somUsuario;
	}

	public void setSomUsuario(SelectOneMenu somUsuario) {
		this.somUsuario = somUsuario;
	}

	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}

	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}

	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}

	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}
	
	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	
	public List<Consignaciones> getLasConsignaciones() {
		
		if (lasConsignaciones == null) {
			try {
				lasConsignaciones = delegadoDeNegocio.consultarTodosConsignaciones();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return lasConsignaciones;
	}

	public void setLasConsignaciones(List<Consignaciones> lasConsignaciones) {
		this.lasConsignaciones = lasConsignaciones;
	}
	
	public List<Retiros> getLosRetiros() {
		
		if (losRetiros == null) {
			try {
				losRetiros = delegadoDeNegocio.consultarTodosRetiros();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return losRetiros;
	}

	public void setLosRetiros(List<Retiros> losRetiros) {
		this.losRetiros = losRetiros;
	}

	public List<SelectItem> getLasCuentasItems() {
		return lasCuentasItems;
	}

	public void setLasCuentasItems(List<SelectItem> lasCuentasItems) {
		this.lasCuentasItems = lasCuentasItems;
	}

	public SelectOneMenu getSomCuenta() {
		return somCuenta;
	}

	public void setSomCuenta(SelectOneMenu somCuenta) {
		this.somCuenta = somCuenta;
	}

	public Long getCliId() {
		return cliId;
	}

	public void setCliId(Long cliId) {
		this.cliId = cliId;
	}
	
}
