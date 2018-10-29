/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.referencia;

import java.util.Objects;

/**
 *
 * @author Thiago Martos
 */
public enum OrgaoExpedidor {

    OUTRO(0, ""),
    SSP(9, "Secretaria de Segurança Pública"),
    MA(3, "Ministério da Aeronáutica"),
    ME(4, "Ministério do Exército"),
    MM(5, "Ministério da Marinha"),
    PF(6, "Polícia Federal"),
    CIC(10, "Carteira de Identidade Classista"),
    CRA(11, "Conselho Regional de Administração"),
    CRAS(12, "Conselho Regional de Assistência Social"),
    CRB(13, "Conselho Regional de Biblioteconomia"),
    CRC(14, "Conselho Regional de Contabilidade"),
    CRCI(15, "Conselho Regional de Corretores de Imóveis"),
    COREN(16, "Conselho Regional de Enfermagem"),
    CREA(17, "Conselho Regional de Engenharia e Arquitetura"),
    CONRE(18, "Conselho Regional de Estatística"),
    CRF(19, "Conselho Regional de Farmácia"),
    CRFT(20, "Conselho Regional de Fisioterapia e Terapia"),
    CRM(21, "Conselho Regional de Medicina"),
    CRMV(22, "Conselho Regional de Medicina Veterinária"),
    OMB(23, "Ordem dos Músicos do Brasil"),
    ORN(24, "Conselho Regional de Nutrição"),
    ORO(25, "Conselho Regional de Odontologia"),
    CRPR(26, "Conselho Regional de Profissionais de Relac."),
    CRP(27, "Conselho Regional de Psicologia"),
    CRQ(28, "Conselho Regional de Química"),
    CRRC(29, "Conselho Regional de Representantes Comerciais"),
    OAB(30, "Ordem dos Advogados do Brasil"),
    OE(31, "Outros emissores"),
    CE(32, "Carteira de Estrangeiro");

    public Integer id;
    public String descricao;

    private OrgaoExpedidor(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public static OrgaoExpedidor parse(Integer id) {
        OrgaoExpedidor orgao_exp = null;
        for (OrgaoExpedidor item : OrgaoExpedidor.values()) {
            if (Objects.equals(item.getId(), id)) {
                orgao_exp = item;
                break;
            }
        }
        return orgao_exp;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
