INSERT INTO tb_perfil (autorizacao) VALUES ('PERFIL_ADMIN');
INSERT INTO tb_perfil (autorizacao) VALUES ('PERFIL_GERENTE');
INSERT INTO tb_perfil (autorizacao) VALUES ('PERFIL_USUARIO');

INSERT INTO tb_usuario (nome, email, login, password, user_state, first_access, user_uuid, termo_parceria) VALUES ('Hassan Kuntze', 'hassankrc@ctcea.org.br', 'hassankrc', '$2a$10$x6lljObKIzpQXgW5sMSwduYKOJ8OM3EgzORMbXkMh2iX7.jdczWFq', 1, 1, '72eafc15-9f68-4379-9f5a-d2bdaa81325a', 0);

INSERT INTO tb_perfil_usuario (id_usuario, id_perfil) VALUES (1, 1);

INSERT INTO tb_fornecedor (nome) VALUES ('Easytech Informática e Serviços LTDA EPP');
INSERT INTO tb_fornecedor (nome) VALUES ('CGK SISTAMAS DE INFORMAÇÃO LTDA - AF090/22');
INSERT INTO tb_fornecedor (nome) VALUES ('Telefonica Brasil - OS094-22');

INSERT INTO tb_localizacao (nome) VALUES ('Prédio ComDCiber - Sala de Reuniões SR1');
INSERT INTO tb_localizacao (nome) VALUES ('Vinculado ao perfil do usuário');
INSERT INTO tb_localizacao (nome) VALUES ('Prédio ComDCiber - Sala da GAP');

INSERT INTO tb_area (nome, responsavel, substituto_responsavel) VALUES ('GTI', 'Armando Furlan', 'N/A');
INSERT INTO tb_area (nome, responsavel, substituto_responsavel) VALUES ('GAP', 'Marcus Pires', 'N/A');
INSERT INTO tb_area (nome, responsavel, substituto_responsavel) VALUES ('GPS', 'Edni Paranhos', 'Mauricio Abe');

INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ADRIANO ALVARES DA SILVA', 'adriano_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ANA KEILLA SARAIVA DE SOUZA NERY', 'ana_nery@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ANA TEREZA VARANDAS FERREIRA', 'ana_ferreira@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ANDERSON FERNANDES DE M. SILVA', 'anderson_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ANDERSON TESCH HOSKEN ALVARENGA', 'anderson_alvarenga@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ANDREIA SANTIAGO DE OLIVEIRA', 'andreia_oliveira@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ARMANDO FURLAN JUNIOR', 'armando_junior@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('BRUNA MONIQUE OLIVEIRA ROCHA', 'bruna_rocha@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('CAINA FELIPE BENTO RAZZOLINI', 'caina_razzolini@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('CARLOS HENRIQUE MAGALHAES DUHAU', 'carlos_duhau@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('CHEISTON DE FIGUEIREDO SENA', 'cheiston_sena@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('CLEITON JOSE FELIPE', 'cleiton_felipe@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('CLEVERSON CARVALHO DE OLIVEIRA', 'cleverson_oliveira@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('DIORINESSE SANTANA DA SILVA', 'diorinesse_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EDNI DE CASTRO PARANHOS', 'edni_paranhos@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EDUARDO ARUME', 'eduardo_arume@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ELEN APARECIDA NUNES DOS SANTOS', 'elen_santos@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ELESSANDRA CRISTINI DELGADO GIMENES', 'elessandra_gimenes@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EMANUEL JONATA OLIVEIRA DE BRITO', 'emanuel_brito@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ERICO DE OLIVEIRA BORGES', 'erico_borges@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EVERTON NETTO AMANDIO', 'everton_amandio@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EVIANE ALEXANDRA CARDOSO', 'eviane_cardoso@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('EWALDO ALVES DA SILVA FILHO', 'ewaldo_filho@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('FABIANE COUTO MUNHOZ', 'fabiane_munhoz@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('FLAVIA HELENA REGIS FERNANDES', 'flavia_fernandes@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('FLAVIO DE CARVALHO PINHEIRO', 'flavio_pinheiro@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('FRANCISLEI VILELA', 'francislei_vilela@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('GEORGIA GUEDES BOAVENTURA', 'georgia_boaventura@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('GIULLIANO GONCALVES CONDE', 'giulliano_conde@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('GLAUCO BUENO DA SILVA', 'glauco_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('HASSAN KUNTZE RODRIGUES DA CUNHA', 'hassan_cunha@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ISAACK CORREA MACHADO', 'isaack_machado@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('JOAO CLAUDIO DE ALMEIDA ILDEFONSO', 'joao_ildefonso@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('LAUDICEIA EMERICK', 'laudiceia_emerick@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('LEANDRO DA SILVA CHAMPOSKI', 'leandro_champoski@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MARCELO FARIAS DE CARVALHO', 'marcelo_carvalho@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MARCIA ADJUTO BOAVENTURA ABRITTA AGUIAR', 'marcia_aguiar@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MARCUS VINICIUS ARAGAO DE CAMPOS', 'marcus_campos@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MARCUS VINICIUS MENDONCA PIRES', 'marcus_pires@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MAURICIO ABE MACHADO', 'mauricio_machado@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('MAURICIO HOFMAM DA SILVA', 'mauricio_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('NADIA GOUVEA ANDREZO CARNEIRO', 'nadia_carneiro@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('OSWALDO HIPOLITO DE ALMEIDA JUNIOR', 'oswaldo_junior@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('PEDRO EDUARDO DE SOUSA DIAS', 'pedro_dias@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('RAMON LARCHER MOREIRA', 'ramon_moreira@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('RICARDO NOBREGA GUIMARAES', 'ricardo_guimaraes@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('ROBERTO WAGNER GOMES', 'roberto_gomes@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('RONALDO BARBOSA DA SILVEIRA', 'ronaldo_silveira@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('THIAGO VALERIANO ARAUJO DA SILVA', 'thiago_silva@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('UBIRAJARA RIBEIRO DE CARVALHO', 'ubirajara_carvalho@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('VERA LUCIA DEMOLINER', 'vera_demoliner@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('WALTER MAURICIO PERRELLA', 'walter_perrella@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('WEBSTER S GOMES FERNANDES', 'webster_fernandes@dominio.com');
INSERT INTO tb_usuario_responsavel (nome, email) VALUES ('WELLINGTON DIONE DE FREITAS NASCIMENTO', 'wellington_nascimento@dominio.com');

INSERT INTO tb_ativo_tangivel (id, id_patrimonial, categoria, descricao, id_area, id_localizacao, id_fornecedor, data_aquisicao, codigo_serie, observacoes, link_documento, id_usuario_responsavel, estado_conservacao, gerar_id_patrimonial) VALUES (NEXT VALUE FOR hibernate_sequence, '1676', 1, 'Smart TV 75” 4K LED, Marca TCL, modelo 75P735 VA.', 2, 1, 1, '2023-04-06', '941463B306AA000577', null, null, 14, 'Novo', 0);
INSERT INTO tb_ativo_intangivel (id, id_patrimonial, categoria, descricao, id_area, id_localizacao, id_fornecedor, data_aquisicao, codigo_serie, observacoes, link_documento, id_usuario_responsavel, gerar_id_patrimonial) VALUES (NEXT VALUE FOR hibernate_sequence, '1716', 5, 'Microsoft Office 365 Business Standard Ativação On-Line 55', 3, 2, 2, '2022-11-11', 'Ativação realizada na Nuvem', null, null, 1, 0);
INSERT INTO tb_ativo_tangivel_locacao (id, id_patrimonial, categoria, descricao, id_area, id_localizacao, id_fornecedor, data_aquisicao, codigo_serie, observacoes, link_documento, id_usuario_responsavel, estado_conservacao, gerar_id_patrimonial) VALUES (NEXT VALUE FOR hibernate_sequence, '1500', 3, 'OptiPlex 5000 Small Form Factor - Dell', 1, 3, 3, '2022-09-01', 'BW3D6R3', null, null, 54, 'Novo', 0);