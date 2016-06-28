package co.edu.usbcali.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.dto.ClienteDTO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.Usuarios;

@RestController
@RequestMapping("/cliente")
public class ClienteRest {
	
	@Autowired
	private IDelegadoDeNegocio delegadoDeNegocio;
	
	
	@RequestMapping(value="/consultarClientePorId/{cliId}",method=RequestMethod.GET)
	public ClienteDTO consultarClientePorId(@PathVariable("cliId")long cliId)throws Exception{
		
		Clientes clientes=delegadoDeNegocio.consultarClientesPorId(cliId);
				
		if(clientes==null){
			throw new Exception("El cliente no existe");
		}
		
		ClienteDTO clienteDTO=new ClienteDTO();
		clienteDTO.setCliDireccion(clientes.getCliDireccion());
		clienteDTO.setCliId(clientes.getCliId());
		clienteDTO.setCliMail(clientes.getCliMail());
		clienteDTO.setCliNombre(clientes.getCliNombre());
		clienteDTO.setCliTelefono(clientes.getCliTelefono());
		clienteDTO.setTdocCodigo(clientes.getTiposDocumentos().getTdocCodigo());
				
		return clienteDTO;
	}
	
	@RequestMapping(value="/consultarTodos",method=RequestMethod.GET)
	public List<ClienteDTO> consultarTodos()throws Exception{
		List<Clientes> losClientes=delegadoDeNegocio.consultarTodosClientes();
		List<ClienteDTO> losClientesDTO=new ArrayList<ClienteDTO>(losClientes.size());
		for (Clientes clientes : losClientes) {
			ClienteDTO clienteDTO=new ClienteDTO();
			clienteDTO.setCliDireccion(clientes.getCliDireccion());
			clienteDTO.setCliId(clientes.getCliId());
			clienteDTO.setCliMail(clientes.getCliMail());
			clienteDTO.setCliNombre(clientes.getCliNombre());
			clienteDTO.setCliTelefono(clientes.getCliTelefono());
			clienteDTO.setTdocCodigo(clientes.getTiposDocumentos().getTdocCodigo());
			losClientesDTO.add(clienteDTO);
		}
		return losClientesDTO;
		
	}
	
	@RequestMapping(value="/grabar",method=RequestMethod.POST)
	public void grabar(@RequestBody ClienteDTO clienteDTO)throws Exception {
		
		try {
			
			Clientes clientes = new Clientes();
			
			clientes.setCliId(clienteDTO.getCliId());
			clientes.setCliNombre(clienteDTO.getCliNombre());
			clientes.setCliTelefono(clienteDTO.getCliTelefono());
			clientes.setCliMail(clienteDTO.getCliMail());
			clientes.setCliDireccion(clienteDTO.getCliDireccion());
			TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorId(clienteDTO.getTdocCodigo());				
			clientes.setTiposDocumentos(tiposDocumentos);
			
			delegadoDeNegocio.grabarClientes(clientes);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value="/modificar",method=RequestMethod.PUT)
	public void modificar(@RequestBody ClienteDTO clienteDTO)throws Exception{
		
		try {
			Clientes clientes=delegadoDeNegocio.consultarClientesPorId(clienteDTO.getCliId());					
			if(clientes==null){
				throw new Exception("El cliente que desea modificar no existe");
			}
			clientes.setCliDireccion(clienteDTO.getCliDireccion());
			clientes.setCliId(clienteDTO.getCliId());
			clientes.setCliMail(clienteDTO.getCliMail());
			clientes.setCliNombre(clienteDTO.getCliNombre());
			clientes.setCliTelefono(clienteDTO.getCliTelefono());
			TiposDocumentos tiposDocumentos=delegadoDeNegocio.consultarTiposDocumentosPorId(clienteDTO.getTdocCodigo());
			clientes.setTiposDocumentos(tiposDocumentos);
			
			delegadoDeNegocio.modificarClientes(clientes);
			
		} catch (Exception e) {
			throw e;
		}		
	}
	
	@RequestMapping(value="/borrar/{id}",method=RequestMethod.DELETE)
	public void borrar(@PathVariable("id") long cliId)throws Exception{
		
		try {
			Clientes clientes=delegadoDeNegocio.consultarClientesPorId(cliId);					
			if(clientes==null){
				throw new Exception("El cliente que desea modificar no existe");
			}
			
			delegadoDeNegocio.borrarClientes(clientes);
			
		} catch (Exception e) {
			throw e;
		}		
	}

}
