package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = null;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco=metodo(sessao, 0.10);
			
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			
			preco=metodo(sessao, 0.20);
			preco=acrescentaHoras(sessao, preco);
		} else if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco=metodo(sessao, 0.20);
			preco=acrescentaHoras(sessao, preco);
			
		}  else {
			
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	private static BigDecimal metodo(Sessao sessao, double taxa){
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.05) { 
			return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(taxa)));
		} else {
			return sessao.getPreco();
		}
	}

	private static BigDecimal acrescentaHoras(Sessao sessao,BigDecimal preco){
		if(sessao.getDuracaoEmMinutos() > 60){
			return preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}
}