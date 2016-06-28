package co.edu.usbcali.demo.logica;

import java.math.BigDecimal;

public interface ITransaccionLogica {
	
	public void consignar(String cueNumero, long cliId, long usuCedula, BigDecimal conValor) throws Exception;
	public void retirar(String cueNumero, long cliId, long usuCedula, BigDecimal retValor) throws Exception;
	
}
