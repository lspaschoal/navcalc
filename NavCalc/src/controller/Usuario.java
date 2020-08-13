package controller;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import model.ModelUsuario;


@Entity (name = "usuarios")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="email", nullable = false)
	private String email;
	@Column(name="senha", nullable = false)
	private String senha;
	@Column(name="nome", nullable = false)
	private String nome;
	@Column(name="sobrenome", nullable = false)
	private String sobrenome;
	@Column(name="dt_nascimento")
	private LocalDate dtNascimento;
	@Transient
	String msgErro;
	
	public boolean autentica(String usuario, String senha) {
		ModelUsuario mu = new ModelUsuario();
		Long id = mu.verificarLogin(usuario, senha);
		if(id > -1) {
			Usuario usuario_encontrado = getUsuario(id);
			this.id = id;
			this.setNome(usuario_encontrado.getNome());
			this.setSobrenome(usuario_encontrado.getSobrenome());
			this.setEmail(usuario_encontrado.getEmail());
			return true;
		}
		return false;
	}
	
	public Usuario getUsuario(long id) {
		ModelUsuario mu = new ModelUsuario();
		return mu.getUsuario(id);
	}
	
	public boolean salvar() {
		ModelUsuario mu = new ModelUsuario();
		if(mu.salvar(this)) {
			return true;
		}else {
			this.msgErro = mu.getMsgErro();
			return false;
		}	
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}
	
	public String getMsgErro() {
		return msgErro;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

}
