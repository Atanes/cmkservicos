package br.com.iridiumit.cmkservicos.modelos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{nrcmk.not.null}")
	private Integer nrcmk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@NotEmpty(message = "{tipoequipamento.not.empty}")
	private String tipo;

	@NotEmpty(message = "{fabricante.not.empty}")
	private String fabricante;

	@NotEmpty(message = "{modelo.not.empty}")
	private String modelo;

	@NotEmpty(message = "{capacidade.not.empty}")
	private String capacidade;

	@NotEmpty(message = "{nrserie.not.empty}")
	private String nrserie;

	private String setor;

	private String area;

	private String planta;

	private String unidade;
	
	public Equipamento() {
		
	}
	
	public Equipamento(Cliente e) {
		this.cliente = e;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNrcmk() {
		return nrcmk;
	}

	public void setNrcmk(Integer nrcmk) {
		this.nrcmk = nrcmk;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(String capacidade) {
		this.capacidade = capacidade;
	}

	public String getNrserie() {
		return nrserie;
	}

	public void setNrserie(String nrserie) {
		this.nrserie = nrserie;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getTipoFabricanteModeloNrSerie() {

		String resposta = this.tipo + " - " + this.fabricante;
		
		if(this.modelo != null) {
			resposta += " - " + this.modelo;
		}
		
		if(this.nrserie != null) {
			resposta += " - " + this.nrserie;
		}

		return resposta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nrcmk == null) ? 0 : nrcmk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrcmk == null) {
			if (other.nrcmk != null)
				return false;
		} else if (!nrcmk.equals(other.nrcmk))
			return false;
		return true;
	}

}
