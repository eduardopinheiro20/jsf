/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

public enum Pagina {

    /**
     * páginas dos sistemas
     * Padrão: [sistema]_[nome_arquivo]("descrição","caminho/nome_arquivo","[sistema]")
     */
    almoxarifado_grupo("Grupos de materiais", "/estoque/restrito/grupo.xhtml", "ALMOXARIFADO"),
    almoxarifado_estoque("Estoques", "/estoque/restrito/estoque.xhtml", "ALMOXARIFADO"),
    almoxarifado_entrada("Entradas", "/estoque/restrito/entrada.xhtml", "ALMOXARIFADO"),
    almoxarifado_saida("Saídas", "/estoque/restrito/saida.xhtml", "ALMOXARIFADO"),
    almoxarifado_material("Materiais", "/estoque/restrito/material.xhtml", "ALMOXARIFADO"),
    almoxarifado_requisicao("Requisição de Materiais", "/estoque/restrito/requisicao.xhtml", "ALMOXARIFADO"),
    almoxarifado_destino("Destinos", "/estoque/restrito/destino.xhtml", "ALMOXARIFADO"),
    almoxarifado_consulta("Consultas", "/estoque/restrito/consulta.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_apuracao_custo("Relatório de Apuração de Custo", "/estoque/restrito/relatorio/relatorio-apuracao-custo.xhtml", "ALMOXARIFADO"),
    almoxarifado_alterar_mes("Alterar mês de trabalho", "/estoque/restrito/alterar-mes.xhtml", "ALMOXARIFADO"),
    almoxarifado_encerrar_mes("Encerramento mensal", "/estoque/restrito/encerrar-mes.xhtml", "ALMOXARIFADO"),
    almoxarifado_usuario("Usuários e permissões", "/estoque/restrito/usuario.xhtml", "ALMOXARIFADO"),
    almoxarifado_perfil("Perfil de acesso", "/estoque/restrito/perfil.xhtml", "ALMOXARIFADO"),
    almoxarifado_parametro("Parâmetros do sistema", "/estoque/restrito/parametro.xhtml", "ALMOXARIFADO"),
    
    rrm_requisicao("Requisições","/requisicao-material-war/restrito/requisicao.xhtml","RRM"),
    rrm_usuario("Usuários e permissões","/requisicao-material-war/restrito/usuario.xhtml","RRM"),
    rrm_perfil("Perfil de acesso","/requisicao-material-war/restrito/perfil.xhtml","RRM"),
    rrm_parametro("Parâmetros do sistema","/requisicao-material-war/restrito/parametro.xhtml","RRM"),

    patrimonio_tombo_movel("Bens móveis","/patrimonio-war/restrito/tombamento.xhtml","PATRIMONIO"),
    patrimonio_tombo_imovel("Bens imóveis","/patrimonio-war/restrito/imovel.xhtml","PATRIMONIO"),
    patrimonio_inventario("Inventários","/patrimonio-war/restrito/inventario/inventario.xhtml","PATRIMONIO"),
    patrimonio_transferencia("Transferências de bens","/patrimonio-war/restrito/transferencia.xhtml","PATRIMONIO"),
    patrimonio_baixa("Baixas","/patrimonio-war/restrito/baixa.xhtml","PATRIMONIO"),
    patrimonio_emprestimo("Cessão de uso","/patrimonio-war/restrito/emprestimo.xhtml","PATRIMONIO"),
    patrimonio_manutencao("Manutenções","/patrimonio-war/restrito/manutencao.xhtml","PATRIMONIO"),
    patrimonio_incorporacao_auto("Incorporação Automática de materiais do Almoxarifado","/patrimonio-war/restrito/incorporacao-automatica.xhtml","PATRIMONIO"),
    patrimonio_incorporacao_manu("Incorporação Manual de materiais do Almoxarifado","/patrimonio-war/restrito/incorporacao-individual.xhtml","PATRIMONIO"),
    patrimonio_incorporacao_lote("Incorporação em Lote de materiais do Almoxarifado","/patrimonio-war/restrito/incorporacao-lote.xhtml","PATRIMONIO"),
    patrimonio_consulta_tombo("Consulta de bens móveis","/patrimonio-war/restrito/consulta-tombamento.xhtml","PATRIMONIO"),
    patrimonio_consulta_fornecedor("Consulta de fornecedores","/patrimonio-war/restrito/consulta-fornecedor.xhtml","PATRIMONIO"),
    patrimonio_consulta_inventario("Consulta de inventários","/patrimonio-war/restrito/consulta-inventario.xhtml","PATRIMONIO"),
    patrimonio_consulta_personalizada("Consulta personalizada","/patrimonio-war/restrito/relatorio/relatorio-personalizado.xhtml","PATRIMONIO"),
    patrimonio_relatorio("Relatórios","/patrimonio-war/restrito/relatorio.xhtml","PATRIMONIO_"),
    patrimonio_setor("Cadastro de Setores","/patrimonio-war/restrito/setor.xhtml","PATRIMONIO"),
    patrimonio_grupo("Cadastro de Grupos","/patrimonio-war/restrito/gupo.xhtml","PATRIMONIO"),
    patrimonio_fornecedor("Cadastro de Fornecedores","/patrimonio-war/restrito/fornecedor.xhtml","PATRIMONIO"),
    patrimonio_seguradora("Cadastro de Seguradoras","/patrimonio-war/restrito/seguradora.xhtml","PATRIMONIO"),
    patrimonio_motivo_baixa("Cadastro de Motivos de baixa","/patrimonio-war/restrito/motivo-baixa.xhtml","PATRIMONIO"),
    patrimonio_usuario("Usuários e permissões","/patrimonio-war/restrito/usuario.xhtml","PATRIMONIO"),
    patrimonio_perfil("Perfil de acesso","/patrimonio-war/restrito/perfil.xhtml","PATRIMONIO"),
    patrimonio_parametro("Parâmetros do sistema","/patrimonio-war/restrito/parametro.xhtml","PATRIMONIO"),
    
    almoxarifado_relatorio_balanco_analitico("Relatório de Balanço Analítico", "/estoque/restrito/relatorio/relatorio-balanco-analitico.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_consolidado("Relatório de Balanço Consolidado", "/estoque/restrito/relatorio/relatorio-balanco-consolidado.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_exercicio("Relatório de Balanço por Exercício", "/estoque/restrito/relatorio/relatorio-balanco-exercicio.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_gratuito("Relatório de Balanço por Grupo Gratuito", "/estoque/restrito/relatorio/relatorio-balanco-gratuito.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_grupo("Relatório de Balanço por Grupo", "/estoque/restrito/relatorio/relatorio-balanco-grupo.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_mes("Relatório de Balanço por Mês", "/estoque/restrito/relatorio/relatorio-balanco-mes.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_balanco_sub_grupo("Relatório de Balanço por Sub-Grupo", "/estoque/restrito/relatorio/relatorio-balanco-sub-grupo.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_dados_contrato("Relatório de Dados do Contrato", "/estoque/restrito/relatorio/relatorio-dados-contrato.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_entrada_consolidada("Relatório de Entrada Consolidada", "/estoque/restrito/relatorio/relatorio-entrada-consolidada.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_entrada_consolidada_grupo("Relatório de Entrada Consolidada por Grupo", "/estoque/restrito/relatorio/relatorio-entrada-consolidado-grupo.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_entrada_itens("Relatórios de Listagens de itens", "/estoque/restrito/relatorio/relatorio-entrada-itens.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_entrada_sequencial("Relatório de Entradas Sequencial", "/estoque/restrito/relatorio/relatorio-entrada-sequencial.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_ata_registro("Relatório de Ata", "/estoque/restrito/relatorio/relatorio-estatistica-ata-registro.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_entrada_fornecedor("Relatórios de Entradas por Fornecedor", "/estoque/restrito/relatorio/relatorio-estatistica-entrada-fornecedor.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_entrada_grupo("Relatório de Entradas por Grupo", "/estoque/restrito/relatorio/relatorio-estatistica-entrada-grupo.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_entrada_material("Relatório de Entradas por Material", "/estoque/restrito/relatorio/relatorio-estatistica-entrada-material.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_entrada_of("Relatório de Entradas por OF", "/estoque/restrito/relatorio/relatorio-estatistica-entrada-of.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_entrada_recebedor("Relatório de Entradas por Recebedor", "/estoque/restrito/relatorio/relatorio-estatistica-entrada-recebedor.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_saida_material("Relatório de Saídas por Material", "/estoque/restrito/relatorio/relatorio-estatistica-saida-material.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_saida_destino("Relatório de Saídas por Destino", "/estoque/restrito/relatorio/relatorio-estatistica-saida-destino.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_saida_destino_material("Relatório de Saídas por Destino - Material", "/estoque/restrito/relatorio/relatorio-estatistica-saida-destino-material.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_estatistica_of_pendente("Relatório de OF - Itens pendentes", "/estoque/restrito/relatorio/relatorio-estatistica-of-pendente.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_ficha_estoque("Relatório de Ficha de Estoque", "/estoque/restrito/relatorio/relatorio-ficha.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_posicao_estoque("Relatório de Posição do Estoque", "/estoque/restrito/relatorio/relatorio-posicao-estoque.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_conferencia_estoque("Relatório de Conferência do Estoque", "/estoque/restrito/relatorio/relatorio-conferencia-contabil.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_conferencia_entrada("Relatório de Conferência de Entradas", "/estoque/restrito/relatorio/relatorio-conferencia-contabil.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_ordem_fornecimento("Relatório de Ordem de Fornecimento", "/estoque/restrito/relatorio/relatorio-ordem-fornecimento.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_saida_consolidada("Relatório de Saídas Consolidadas", "/estoque/restrito/relatorio/relatorio-saida-consolidada.xhtml", "ALMOXARIFADO"),
    almoxarifado_relatorio_saida_sequencial_data("Relatório de Saídas Sequencial por Data", "/estoque/restrito/relatorio/relatorio-saida-sequencial.xhtml", "ALMOXARIFADO"),
    
    patrimonio_relatorio_materiais_grupo("Relatório de Materiais por Grupo","/patrimonio-war/restrito/relatorio/relatorio-materiais-grupo.xhtml","PATRIMONIO"),
    patrimonio_relatorio_materiais_subgrupo("Relatório de Materiais por SubGrupo","/patrimonio-war/restrito/relatorio/relatorio-materiais-subgrupo.xhtml","PATRIMONIO"),
    patrimonio_relatorio_setores_primario("Relatório de Setores Primários","/patrimonio-war/restrito/relatorio/relatorio-materiais-setor.xhtml","PATRIMONIO"),
    patrimonio_relatorio_setores_secundarios("Relatório de Setores Secundários","/patrimonio-war/restrito/relatorio/relatorio-materiais-setorsec.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_exercicio("Relatório de Bens por Exercício","/patrimonio-war/restrito/relatorio/relatorio-tombo-exercicio.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_placa("Relatório de Bens por Placa","/patrimonio-war/restrito/relatorio/relatorio-tombo-placa.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_of("Relatório de Bens por OF/Empenho","/patrimonio-war/restrito/relatorio/relatorio-tombo-of.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_setor("Relatório de Bens por Setor","/patrimonio-war/restrito/relatorio/relatorio-tombo-setor.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_fornecedor("Relatório de Bens por Fornecedor","/patrimonio-war/restrito/relatorio/relatorio-tombo-fornecedor.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_grupo("Relatório de Bens por Grupo","/patrimonio-war/restrito/relatorio/relatorio-tombo-grupo.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_conservacao("Relatório de Bens por Conservação","/patrimonio-war/restrito/relatorio/relatorio-tombo-conservacao.xhtml","PATRIMONIO"),
    patrimonio_relatorio_tombo_baixa("Relatório de Bens por Baixa","/patrimonio-war/restrito/relatorio/relatorio-tombo-baixa.xhtml","PATRIMONIO"),
    patrimonio_relatorio_inventario_situacao("Relatório de Inventario por Situação","/patrimonio-war/restrito/relatorio/relatorio-inventario-situacao.xhtml","PATRIMONIO"),
    patrimonio_relatorio_fornecedor("Relatório de Fornecedores","/patrimonio-war/restrito/relatorio/relatorio-fornecedor.xhtml","PATRIMONIO"),
    patrimonio_relatorio_termo("Relatório de Termo de Responsabilidade","/patrimonio-war/restrito/relatorio/relatorio-termo-responsabilidade.xhtml","PATRIMONIO"),
    patrimonio_relatorio_transferencias("Relatório de Transferências","/patrimonio-war/restrito/relatorio/relatorio-transferencia-bens.xhtml","PATRIMONIO"),
    patrimonio_relatorio_ficha_bens("Relatório de Ficha de Bens","/patrimonio-war/restrito/relatorio/relatorio-ficha-bens.xhtml","PATRIMONIO"),
    patrimonio_relatorio_termo_baixa("Relatório de Termo de Baixa","/patrimonio-war/restrito/relatorio/relatorio-termo-baixa.xhtml","PATRIMONIO"),
    patrimonio_relatorio_of("Relatório de OF","/patrimonio-war/restrito/relatorio/relatorio-conferencia.xhtml","PATRIMONIO"),
    patrimonio_relatorio_etiquetas("Relatório de Etiquestas","/patrimonio-war/restrito/relatorio/relatorio-etiquetas.xhtml","PATRIMONIO"),
    patrimonio_relatorio_pendentes("Relatório de OF - Itens Pendentes","/patrimonio-war/restrito/relatorio/relatorio-of-pendente.xhtml","PATRIMONIO"),
    patrimonio_relatorio_entrada("Relatório de OF - Entradas na OF","/patrimonio-war/restrito/relatorio/relatorio-entrada-of.xhtml","PATRIMONIO"),
    
    issqn_taxas("Cadastro de Taxa","/app/issqn/taxa.xhtml","ISSQN"),
    issqn_motivo_cancelamento("Cadastro de Motivos de Cancelamento","/app/issqn/motivo-cancelamento.xhtml","ISSQN"),
    issqn_categoria("Cadastro de Categoria","/app/issqn/categoria.xhtml","ISSQN"),
    issqn_banco("Cadastro de Banco","/app/geral/banco.xhtml","ISSQN"),
    issqn_contribuinte("Cadastro de Contribuinte","/app/issqn/contribuinte.xhtml","ISSQN"),
    issqn_alteracoes_registro("Alterações de Registro","/app/issqn/historico-registro.xhtml","ISSQN"),
    issqn_codigo_fiscal("Códigos Fiscais","/app/issqn/codigo-fiscal.xhtml","ISSQN"),
    issqn_atividade("Cadastro de Atividade","/app/issqn/atividade.xhtml","ISSQN"),
    issqn_escritorio("Cadastro de Escritório","/app/issqn/escritorio.xhtml","ISSQN"),
    issqn_conselhos_regionais("Cadastro de Conselhos Regionais","/app/issqn/conselho-regional.xhtml","ISSQN"),
    issqn_ramos_atuacao("Cadastro de Ramos de Atuação","/app/issqn/ramo-atuacao.xhtml","ISSQN"),
    issqn_questoes_contribuinte("Cadastro de Questões do Contribuinte","/app/issqn/questao.xhtml","ISSQN"),
    issqn_orgao_fiscalizacao("Cadastro de Órgão de Fiscalização","/app/issqn/orgao-fiscalizacao.xhtml","ISSQN"),
    issqn_zona_municipio("Cadastro de Zona do Município","/app/issqn/zona-municipio.xhtml","ISSQN"),
    issqn_requisito("Cadastro de Requisitos","/app/issqn/cadastro-requisitos.xhtml","ISSQN"),
    issqn_segmento("Cadastro de Segmentos","/app/issqn/cadastro-segmentos.xhtml","ISSQN"),
    issqn_imovel("Cadastro de Imóvel","/app/geral/imovel.xhtml","ISSQN"),
    issqn_pessoa("Cadastro de Pessoa","/app/geral/pessoa.xhtml","ISSQN"),
    issqn_profissao("Cadastro de Profissão","/app/geral/profissao.xhtml","ISSQN"),
    issqn_religiao("Cadastro de Religião","/app/geral/religiao.xhtml","ISSQN"),
    issqn_consulta_parcelas("Consulta de Parcelas","/app/consulta-parcelas.xhtml","ISSQN"),
    issqn_calculo("Efetuar Cálculo","/app/movimento/calculo.xhtml","ISSQN"),
    issqn_cancelar_calculo("Cancelar Cálculo","/app/movimento/cancelamento.xhtml","ISSQN"),
    issqn_arquivo("Gerar arquivo para Emissão de Carnê/Boleto","/app/movimento/arquivo-febraban.xhtml","ISSQN"),
    issqn_emissao("Emissão de Carnê/Boleto","/app/movimento/emissao-carne.xhtml","ISSQN"),
    issqn_vencimento("Gerar vencimentos","/app/movimento/gerar-vencimentos.xhtml","ISSQN"),
    issqn_alvara_diversao("Alvará de Diversões","/app/fiscal/alvara-diversoes.xhtml","ISSQN"),
    issqn_termo_fiscalizacao("Termo de Fiscalização","/app/fiscal/termo-fiscalizacao.xhtml","ISSQN"),
    issqn_auto_infracao("Termo de Auto de Infração","/app/fiscal/auto-infracao.xhtml","ISSQN"),
    issqn_termo_apreensao("Termo de Apreensão","/app/fiscal/termo-apreensao.xhtml","ISSQN"),
    issqn_termo_intimacao("Termo de Intimação","/app/fiscal/termo-intimacao.xhtml","ISSQN"),
    issqn_consultar_jucesp("Consultar JUCESP","/app/geral/consultar-empresas.xhtml","ISSQN"),
    issqn_notificacoes("Notificações","/app/issqn/notificacao.xhtml","ISSQN"),
    issqn_relatorio_contribuinte("Relatório de Contribuintes","/app/relatorio/relatorio-contribuinte.xhtml","ISSQN"),
    issqn_relatorio_contribuinte_devedor("Relatório de Contribuintes Devedores","/app/relatorio/relatorio-contribuinte-devedores.xhtml","ISSQN"),
    issqn_relatorio_certidao_negativa("Relatório de Certidão Negativa","/app/relatorio/relatorio-certidao-negativa.xhtml","ISSQN"),
    issqn_relatorio_ficha_cadastral("Relatório de Ficha Cadastral","/app/relatorio/relatorio-ficha-cadastral.xhtml","ISSQN"),
    issqn_relatorio_listagem_debito("Relatório de Extrato de Débito","/app/relatorio/relatorio-listagem-debitos.xhtml","ISSQN"),
    issqn_relatorio_taxa("Relatório de Taxas","/app/relatorio/relatorio-taxa.xhtml","ISSQN"),
    issqn_relatorio_alvara("Relatório de Alvará","/app/relatorio/relatorio-alvara.xhtml","ISSQN"),
    issqn_relatorio_pagamento("Relatório de Pagamentos","/app/relatorio/relatorio-pagamento.xhtml","ISSQN"),
    issqn_relatorio_carta_cobranca("Relatório de Carta de Cobrança","/app/relatorio/carta-cobranca.xhtml","ISSQN"),
    issqn_relatorio_alerta_notificacao("Relatório de Alerta de Notificação Vencendo","/app/relatorio/alerta-notificacao.xhtml","ISSQN"),
    issqn_relatorio_termo_fiscalizacao("Relatório de Termo de Fiscalização","/app/relatorio/relatorio-termo-abertura.xhtml","ISSQN"),
    issqn_relatorio_historico_contribuinte("Relatório de Histórico de Contribuinte","/app/relatorio/relatorio-historico-registro.xhtml","ISSQN"),
    issqn_relatorio_certidao_baixa_inscricao("Relatório de Certidão de Baixa de Inscrição","/app/relatorio/relatorio-encerramento-inscricao.xhtml","ISSQN"),
    issqn_relatorio_certidao_inativacao_inscricao("Relatório de Certidão de Inativação de Inscrição","/app/relatorio/relatorio-inativacao-inscricao.xhtml","ISSQN"),
    issqn_relatorio_conferencia_parcela("Relatório de Conferência de Parcelas","/app/relatorio/relatorio-conferencia-parcelas.xhtml","ISSQN"),
    issqn_relatorio_contribuintes_segmento("Relatório de Contribuintes por Segmento","/app/relatorio/relatorio-por-segmento.xhtml","ISSQN"),
    issqn_usuario("Cadastro de Usuários","/app/admin/usuario.xhtml","ISSQN"),
    issqn_perfil("Cadastro de Perfis","/app/admin/perfil.xhtml","ISSQN"),
    issqn_parametros_sistemas("Cadastro de Parâmetros do Sistema","/app/admin/parametro.xhtml","ISSQN"),
    issqn_auditoria("Auditoria","/app/auditoria.xhtml","ISSQN");
    
    String pagina;
    String descricao;
    String sistema;

    Pagina(String descricao, String pagina, String sistema) {
        this.pagina = pagina;
        this.descricao = descricao;
        this.sistema = sistema;
    }

    public String getPagina() {
        return pagina;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSistema() {
        return sistema;
    }
}
