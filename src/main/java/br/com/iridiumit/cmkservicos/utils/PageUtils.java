package br.com.iridiumit.cmkservicos.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class PageUtils {
	
	public String URIPaginacao(HttpServletRequest httpServletRequest, String parametro) {
		
		String urlFiltro = httpServletRequest.getParameter(parametro);
		
		if(urlFiltro != null) {
			return httpServletRequest.getRequestURI() + 
					"?" + parametro + "=" + urlFiltro + "&"; 
		}else {
			return httpServletRequest.getRequestURI() + "?";
		}	
		
	}

}
