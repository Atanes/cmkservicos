package br.com.iridiumit.cmkservicos.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;


public class PageUtils {

	private UriComponentsBuilder uriBuilder;
	private Pageable pagina;

	public PageUtils(HttpServletRequest httpServletRequest, Pageable pageable) {
		pagina = pageable;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
	}
	
	public String urlPaginacao(String chave, String valor) {

		if (chave != null) {
			return uriBuilder.replaceQueryParam(chave, valor).build(true).encode().toUriString();
		} else {
			return uriBuilder.build(true).encode().toUriString() + "?";
		}

	}

	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String ordem) {
		String direcao = "asc";
		
		Order order = pagina.getSort() != null ? pagina.getSort().getOrderFor(ordem) : null;
		
		if (order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public boolean ordenada(String propriedade) {
		return pagina.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}


