# cmkservicos
Sistema para controle e gestão dos atendimentos da CMK 

** Para utilizar uma base de dados local é preciso alterar os dados no application.properties ** 
** e depois de executar a aplicação pela primeira vez e antes de tentar fazer o primeiro acesso  **
** você precisa fazer a preparação abaixo:  **

** Incluir os dados iniciais de acesso na base de dados local  **
** no primeiro acesso, exclusivamente, executar os comandos abaixo: **

** Na tabela de endereços:  **

INSERT INTO public.endereco(cep)
	VALUES ('09100010');

** Na tabela usuário:  **

INSERT INTO public.usuario(ativo, cpf, email, nome, senha, telefone1, telefone2, endereco_id, data_nascimento)
	VALUES (true, '080.080.080-00', 'admin@teste.com', 'Admin', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', '1190001221', '', 1, '2000-01-01');
	
** Na tabela Grupo:  **

INSERT INTO public.grupo(nome)
	VALUES ('ADMIN');  
INSERT INTO public.grupo(nome)
	VALUES ('COORDENADOR');  
INSERT INTO public.grupo(nome)
	VALUES ('ANALISTA');  
INSERT INTO public.grupo(nome)
	VALUES ('GESTOR');  
INSERT INTO public.grupo(nome)
	VALUES ('TECNICO');  
	
** Na tabela Permissões:  **

INSERT INTO public.permissao(codigo, nome)
	VALUES (1, 'ROLE_CMK_ADMIN');  
INSERT INTO public.permissao(codigo, nome)
	VALUES (2, 'ROLE_CMK_COORDENADOR');  
INSERT INTO public.permissao(codigo, nome)
	VALUES (3, 'ROLE_CMK_ANALISTA');  
INSERT INTO public.permissao(codigo, nome)
	VALUES (4, 'ROLE_CMK_GESTOR');  
INSERT INTO public.permissao(codigo, nome)
	VALUES (5, 'ROLE_CMK_TECNICO');  
	
** na tabela usuario_grupo:  **

INSERT INTO public.usuario_grupo(
	codigo_usuario, codigo_grupo)
	VALUES (1, 1);

** Na tabela grupo_permissão: **

INSERT INTO public.grupo_permissao(
	codigo_grupo, codigo_permissao)
	VALUES (1, 1);  
INSERT INTO public.grupo_permissao(
	codigo_grupo, codigo_permissao)
	VALUES (2, 2);  
INSERT INTO public.grupo_permissao(
	codigo_grupo, codigo_permissao)
	VALUES (3, 3);  
INSERT INTO public.grupo_permissao(
	codigo_grupo, codigo_permissao)
	VALUES (4, 4);  
INSERT INTO public.grupo_permissao(
	codigo_grupo, codigo_permissao)
	VALUES (5, 5);

** Depois de executar esses comandos no banco de dados você já pode acessar a aplicação  **
** com os dados abaixo: **

** usuário: admin@teste.com **  
** senha: 123 **
	


