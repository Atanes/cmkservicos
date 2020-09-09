package br.com.iridiumit.cmkservicos.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Apontamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_apontamento;

	@NotNull(message = "Nome do técnico não pode estar em branco!")
	private String tecnico;

	@Column(name = "inicio_trabalho")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime inicioTrabalho;

	@Column(name = "fim_trabalho")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fimTrabalho;

	@NotNull(message = "Nome do responsável pelo lançamento não pode estar em branco!")
	private String responsavel;
	
	@Column(name = "data_apontamento")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataApontamento;

	public Long getId_apontamento() {
		return id_apontamento;
	}

	public void setId_apontamento(Long id_apontamento) {
		this.id_apontamento = id_apontamento;
	}

	public String getTecnico() {
		return tecnico;
	}

	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}

	public LocalDateTime getInicioTrabalho() {
		return inicioTrabalho;
	}

	public void setInicioTrabalho(LocalDateTime inicioTrabalho) {
		this.inicioTrabalho = inicioTrabalho;
	}

	public LocalDateTime getFimTrabalho() {
		return fimTrabalho;
	}

	public void setFimTrabalho(LocalDateTime fimTrabalho) {
		this.fimTrabalho = fimTrabalho;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public LocalDate getDataApontamento() {
		return dataApontamento;
	}

	public void setDataApontamento(LocalDate dataApontamento) {
		this.dataApontamento = dataApontamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_apontamento == null) ? 0 : id_apontamento.hashCode());
		result = prime * result + ((tecnico == null) ? 0 : tecnico.hashCode());
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
		Apontamento other = (Apontamento) obj;
		if (id_apontamento == null) {
			if (other.id_apontamento != null)
				return false;
		} else if (!id_apontamento.equals(other.id_apontamento))
			return false;
		if (tecnico == null) {
			if (other.tecnico != null)
				return false;
		} else if (!tecnico.equals(other.tecnico))
			return false;
		return true;
	}

}
