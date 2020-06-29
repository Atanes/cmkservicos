package br.com.iridiumit.cmkservicos.modelos;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Atendimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	//@NotNull(message = "{nros.not.empty}")
	private Integer nros;
	
	@Column(name="data_atendimento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MMM-yyyy")
	private Date dataAtendimento;
	
	@Column(name="inicio_atendimento")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date inicioAtendimento;
	
	@Column(name="fim_atendimento")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date fimAtendimento;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;
	
	//@NotEmpty(message = "{nota.not.empty}")
	private String nota;
	
	//@NotEmpty(message = "{diagnostico.not.empty}")
	private String diagnostico;
	
	private boolean mparada;
	
	@Column(name="mparada_datahora")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime mparadaDataHora;
	
	@Column(name="mliberada_datahora")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime mliberadaDataHora;
	
	private boolean cobrar;
	
	private boolean apto;
	
	private String observacoes;
	
	private String servicos;
	
	private boolean aptocomresalvas;
	
	private String resalvas;
	
	private boolean naoapto;
	
	private String ObsNaoApto;
	
	@NotEmpty(message = "{executor.not.null}")
	private String executor;
	
	@NotEmpty(message = "{aprovador.not.null}")
	private String aprovador;
	
	private String acompanhante;
	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public Date getInicioAtendimento() {
		return inicioAtendimento;
	}

	public void setFimAtendimento(Date fimAtendimento) {
		this.fimAtendimento = fimAtendimento;
	}
	
	public Date getFimAtendimento() {
		return fimAtendimento;
	}

	public void setInicioAtendimento(Date inicioAtendimento) {
		this.inicioAtendimento = inicioAtendimento;
	}

	public Integer getNros() {
		return nros;
	}

	public void setNros(Integer nros) {
		this.nros = nros;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public boolean isApto() {
		return apto;
	}

	public void setApto(boolean apto) {
		this.apto = apto;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public String getServicos() {
		return servicos;
	}

	public void setServicos(String servicos) {
		this.servicos = servicos;
	}

	public boolean isAptocomresalvas() {
		return aptocomresalvas;
	}

	public void setAptocomresalvas(boolean aptocomresalvas) {
		this.aptocomresalvas = aptocomresalvas;
	}

	public String getResalvas() {
		return resalvas;
	}

	public void setResalvas(String resalvas) {
		this.resalvas = resalvas;
	}

	public boolean isNaoapto() {
		return naoapto;
	}

	public void setNaoapto(boolean naoapto) {
		this.naoapto = naoapto;
	}

	public String getObsNaoApto() {
		return ObsNaoApto;
	}

	public void setObsNaoApto(String obsNaoApto) {
		ObsNaoApto = obsNaoApto;
	}

	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}
	
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getAcompanhante() {
		return acompanhante;
	}

	public void setAcompanhante(String acompanhante) {
		this.acompanhante = acompanhante;
	}

	public boolean isMparada() {
		return mparada;
	}

	public void setMparada(boolean mparada) {
		this.mparada = mparada;
	}

	public LocalDateTime getMparadaDataHora() {
		return mparadaDataHora;
	}

	public void setMparadaDataHora(LocalDateTime mparadaDataHora) {
		this.mparadaDataHora = mparadaDataHora;
	}

	public LocalDateTime getMliberadaDataHora() {
		return mliberadaDataHora;
	}

	public void setMliberadaDataHora(LocalDateTime mliberadaDataHora) {
		this.mliberadaDataHora = mliberadaDataHora;
	}

	public boolean isCobrar() {
		return cobrar;
	}

	public void setCobrar(boolean cobrar) {
		this.cobrar = cobrar;
	}
	
	public String resumoAtendimento() {
		return "Tipo: " + this.getChamado().getTipo() + ", Equipamento: " 
				+ this.getChamado().getEquipamento().getTipoFabricanteModeloNrSerie() 
				+ ", Descrição: " + this.getChamado().getDescricao();
	}
	
	public String dadosAtendimento() {
		return "O.S.: " + this.getNros() + ", Tipo: " + this.getChamado().getTipo() + ", Equipamento: " 
				+ this.getChamado().getEquipamento().getTipoFabricanteModeloNrSerie() + ", Descrição: "
				+ this.getChamado().getDescricao() + ", Status: " + this.getChamado().getStatus() 
				+ ", Executor : " + this.getExecutor() ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Atendimento other = (Atendimento) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}	

}
