/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

/**
 *
 * @author David
 */
public class ArquivoRemessaBB extends Repositorio {

    private File file;
    private PrintWriter p;
    private String cnpj = "43976166000150";
    private String n_variacao = "027";
    private String nome_banco = "BANCO DO BRASIL";

    public ArquivoRemessaBB(EntityManager entityManager) {
        super(entityManager);
    }

    public void gerarRemessa(int exercicio, String orgaoId, Banco banco)
            throws RuntimeException {
        try {

            IssqnConfiguracao conf = getEntidadePura(IssqnConfiguracao.class,
                    "select c from IssqnConfiguracao c "
                    + "\n where c.orgao.idOrgao = ?1 "
                    + "\norder by c.id desc", orgaoId
            );
            if (conf == null) {
                throw new Exception("Informações do parâmetros não encontradas!");
            }

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            ServletContext servletContext = (ServletContext) context.getContext();
            String data = (Util.hoje().substring(0, 5) + Util.hoje().substring(6, 10)).replace("/", "");
            String data_comp = (Util.hoje().substring(0, 5) + Util.hoje().substring(6, 10)).replace("/", "");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date hora = Calendar.getInstance().getTime();
            String dataFormatada = sdf.format(hora).replace(":", "");
            int count = 1;
            int count_titulo = 1;
            file = new File(servletContext.getRealPath("") + "/resources/BB" + Util.hoje().replace("/", ""));
            p = new PrintWriter(file, "Cp1252");
            String l = "";

            List<Object[]> lstObj = createNativeQuery("SELECT DISTINCT P.NOME, CASE WHEN P.TP_PESSOA = 0 THEN '1' ELSE '2' END AS TP_PESSOA, CI.VL_PARCELA AS VL_TOTAL, TO_CHAR(CI.DT_VENCIMENTO, 'DDMMYY') AS DT_VENCIMENTO,\n"
                    + "TO_CHAR(CI.DT_VENCIMENTO, 'DDMMYYYY') AS DT_VENCIMENTO_COMP, CI.NOSSO_NUMERO, MI.DT_CALCULO, L.NOME || ' ' || EI.NR_IMOVEL AS LOG_IMOVEL,\n"
                    + "B.NOME as BAIRRO, C.NOME AS CIDADE, CASE WHEN EI.CEP = '  .   -   ' THEN '00000000' WHEN EI.CEP = '' THEN '00000000' ELSE cast(SUBSTR(REPLACE(REPLACE(EI.CEP,'.',''),'-',''),0,9) as integer) END AS CEP,\n"
                    + "TO_CHAR(MI.DT_CALCULO, 'DDMMYY') as DATA_BOLETO, TO_CHAR(MI.DT_CALCULO, 'DDMMYYYY') as DATA_BOLETO_COMP,\n"
                    + "REPLACE(TRIM(replace_cpf(coalesce(P.CPF_CNPJ,'0'))),' ','') AS CPF_CNPJ, C.UF,  TO_CHAR(cast((CI.DT_VENCIMENTO + INTERVAL '1 DAY') AS DATE), 'DDMMYYYY') AS DT_VENCTO_JUROS\n"
                    + "FROM ISSQN II\n"
                    + "INNER JOIN ISSQN_MOVIMENTO MI ON MI.ID_ISS = II.ID_ISS\n"
                    + "INNER JOIN ISSQN_MOVIMENTO_PARCELA CI ON CI.ID_MOVIMENTO = MI.ID_MOVIMENTO\n"
                    + "INNER JOIN IMOVEL EI ON EI.ID_IMOVEL = II.ID_IMOVEL\n"
                    + "INNER JOIN PESSOA P ON P.ID_PESSOA = II.ID_PESSOA\n"
                    + "INNER JOIN BAIRRO_LOGRADOURO BL ON BL.ID_BAIRROLOGRADOURO= EI.ID_BAIRROLOGRADOURO\n"
                    + "INNER JOIN BAIRRO B ON B.ID_BAIRRO = BL.ID_BAIRRO AND B.ID_CIDADE = BL.ID_CIDADE\n"
                    + "INNER JOIN CIDADE C ON C.ID_CIDADE = B.ID_CIDADE\n"
                    + "INNER JOIN LOGRADOURO L ON L.ID_LOGRADOURO = BL.ID_LOGRADOURO AND L.ID_CIDADE = BL.ID_CIDADE\n"
                    + "LEFT JOIN ABREVIATURA ALI ON ALI.ID_ABREVIATURA = L.ID_ABREVIATURA \n"
                    + "LEFT JOIN ABREVIATURA ABI ON ABI.ID_ABREVIATURA = B.ID_ABREVIATURA\n"
                    + "WHERE (CI.CANCELADO_PAGAMENTO = 0 OR CI.CANCELADO_PAGAMENTO IS NULL) AND CI.VL_PARCELA > 0 AND CI.DT_BANCO IS NULL \n"
                    + "AND (CI.VL_PAGO = 0 OR CI.VL_PAGO IS NULL) AND Extract('YEAR' From CI.DT_VENCIMENTO) >= " + exercicio + "\n ORDER BY 1");

            if (banco.getCnab() == 1) {
                l = "";
                l += "0"; //Identificação do Registro
                l += "1"; //Identificação do Arquivo Remessa
                l += Util.Texto.alinharEsquerda("REMESSA", 7); //Literal Remessa
                l += "01"; //Código de Serviço
                l += Util.Texto.alinharEsquerda("COBRANCA", 8); //Literal Serviço
                l += Util.Texto.alinharEsquerda(null, 7); //Brancos
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", Integer.parseInt(banco.getAgencia())), 4); //Prefixo da Agência: Número da Agência onde está cadastrado o convênio líder do cedente
                l += Util.Texto.alinharEsquerda(banco.getDvAgencia(), 1);  //Dígito Verificador - D.V. - do Prefixo da Agência
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Integer.parseInt(banco.getConta())), 8); //Número da Conta Corrente: Número da conta onde está cadastrado o Convênio Líder do Cedente
                l += Util.Texto.alinharEsquerda(banco.getDvConta(), 1); //Dígito Verificador - D.V. – do Número da Conta Corrente do Cedente
                l += Util.Texto.alinharEsquerda("000000", 6); //Complemento do Registro: “000000”
                l += Util.Texto.alinharEsquerda("PREFEITURA MUNICIPAL DE AMÉRICO BRASILIENSE", 30); //Nome Cedente 
                l += Util.Texto.alinharEsquerda("001BANCODOBRASIL", 18); //Banco do Brasiol 
                l += Util.Texto.alinharEsquerda(data, 6); //Data da Gravação do Arquivo
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000000", banco.getRemessa()), 7); //Nº Seqüencial de Remessa 
                l += Util.Texto.alinharEsquerda(null, 22); //Brancos
                l += Util.Texto.alinharEsquerda(banco.getNumeroConvenio(), 7); //Número do Convênio Líder
                l += Util.Texto.alinharEsquerda(null, 258); //Brancos
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", count), 6); //Nº Seqüencial do Registro de Um em Um
                l += "\n";

                // Layout do Arquivo-Remessa 400
                for (Object[] obj : lstObj) {
                    count++;

                    l += "7"; //Identificação do Registro
                    l += "02"; //Tipo de Inscrição do Cedente
                    l += Util.Texto.alinharEsquerda(cnpj, 14); //Número do CPF/CNPJ do Cedente
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000", Integer.parseInt(banco.getAgencia())), 4); //Prefixo da Agência: Número da Agência onde está cadastrado o convênio líder do cedente
                    l += Util.Texto.alinharEsquerda(banco.getDvAgencia(), 1);  //Dígito Verificador - D.V. - do Prefixo da Agência
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Integer.parseInt(banco.getConta())), 8); //Número da Conta Corrente: Número da conta onde está cadastrado o Convênio Líder do Cedente
                    l += Util.Texto.alinharEsquerda(banco.getDvConta(), 1); //Dígito Verificador - D.V. – do Número da Conta Corrente do Cedente
                    l += Util.Texto.alinharEsquerda(banco.getNumeroConvenio(), 7); //Número do Convênio Líder
                    l += Util.Texto.alinharEsquerda(null, 25); //Brancos
                    l += Util.Texto.alinharEsquerda(banco.getNumeroConvenio(), 7); //Número do Convênio Líder
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000000000", Util.extractInt(obj[5])), 10); //Uso da Empresa (nosso numero)
                    l += "00"; //Número da Prestação: “00” (Zeros)
                    l += "00"; //Grupo de Valor: “00” (Zeros)
                    l += Util.Texto.alinharEsquerda(null, 3); //Brancos
                    l += Util.Texto.alinharEsquerda(null, 1); //Indicativo de Mensagem ou Sacador/Avalista
                    l += Util.Texto.alinharEsquerda(null, 3); //Brancos
                    l += Util.Texto.alinharEsquerda(n_variacao, 3);
                    l += "0";  //Conta Caução: “0”
                    l += "000000"; //Número do Borderô: “000000”
                    l += Util.Texto.alinharEsquerda(null, 5); //Tipo de Cobrança
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00", Integer.parseInt(banco.getBoletoCarteira().substring(0, 2))), 2); //Carteira de Cobrança
                    l += "01"; //Comando
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000000000", Util.extractInt(obj[5])), 10); //Uso da Empresa (nosso numero)
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", Util.extractInt(obj[3])), 6); //Data do Vencimento do Título
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("00000000000.00", Util.extractDouble(obj[2]))), 13); //Valor do Titulo
                    l += "001"; // Número Banco
                    l += "0000"; //Preencher com zeros, o sistema indicará a agência cobradora de acordo com o CEP do endereço do Pagador
                    l += Util.Texto.alinharEsquerda(null, 1); //Dígito Verificador do Prefixo da Agência Cobradora: “Brancos”
                    l += "01"; //Espécie de Titulo
                    l += "N"; //Aceite de Titulo
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", Util.extractInt(obj[11])), 6); //Data do Vencimento do Título
                    l += "01"; //1ª instrução
                    l += "01"; //2ª instrução
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("00000000000.00", Util.truncarValor((Util.extractDouble(obj[2]) * 1 / 100) + 0.005, 2))), 13); //Valor a ser cobrado por Dia de Atraso (x 1% / 30)
                    l += "000000"; //Data Limite P/Concessão de Desconto
                    l += "0000000000000"; //Valor Desconto
                    l += "0000000000000"; //Valor do IOF
                    l += "0000000000000"; //Valor Abatimento
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00", Util.extractInt(obj[1])), 2); //Tipo de Inscrição do Sacado
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000000", Util.extractStr(obj[13]).equals("") ? "0" : Long.parseLong(Util.extractStr(obj[13]))), 14); //CPF/CNPJ Pagador
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[0]), 37); //Nome Sacado
                    l += Util.Texto.alinharEsquerda(null, 3); //Brancos
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[7]), 40); //Endereço Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[8]), 12); //Bairro Sacado
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[10])), 8); //CEP Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[9]), 15); //Cidade Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[14]), 2); //UF Sacado
                    l += Util.Texto.alinharEsquerda(null, 40); //Observações/Mensagem ou Sacador/Avalista
                    l += "00"; //Número de Dias Para Protesto
                    l += Util.Texto.alinharEsquerda(null, 1); //Brancos
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", count), 6); //Nº Seqüencial do Registro de Um em Um
                    l += "\n";
                }
                l += "9"; //Identificação do Registro
                l += Util.Texto.alinharEsquerda(null, 393); //Identificação do Registro
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", count), 6); //Nº Seqüencial do Registro de Um em Um
                l += "\n";
                p.print(l);
                p.close();

            } else {  // 240 posições

                // Registro Tipo 0 (Obrigatório) - Header de Arquivo Remessa
                l = "";
                l += "001"; //Preencher '001’
                l += "0000"; //Preencher '0000’
                l += "0"; //Preencher '0'
                l += Util.Texto.alinharEsquerda(null, 9); //Brancos
                l += "2"; //Tipo Beneficiário - CNPJ 
                l += Util.Texto.alinharEsquerda(cnpj, 14); //Número do CPF/CNPJ do Cedente
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000", Integer.parseInt(banco.getNumeroConvenio())), 9); //Número do Convênio Líder
                l += "0014"; //Preencher '0014'
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00", Integer.parseInt(banco.getBoletoCarteira().substring(0, 2))), 2); //Carteira de Cobrança
                l += Util.Texto.alinharEsquerda(n_variacao, 3);  // Variação Carteira
                l += Util.Texto.alinharEsquerda(null, 2); //Brancos
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", Integer.parseInt(banco.getAgencia())), 5); //Prefixo da Agência: Número da Agência onde está cadastrado o convênio líder do cedente
                l += Util.Texto.alinharEsquerda(banco.getDvAgencia(), 1);  //Dígito Verificador - D.V. - do Prefixo da Agência
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000", Integer.parseInt(banco.getConta())), 12); //Número da Conta Corrente: Número da conta onde está cadastrado o Convênio Líder do Cedente
                l += Util.Texto.alinharEsquerda(banco.getDvConta(), 1); //Dígito Verificador - D.V. – do Número da Conta Corrente do Cedente
                l += " ";
                l += Util.Texto.alinharEsquerda("PREFEITURA MUNICIPAL DE AMÉRICO BRASILIENSE", 30); //Nome Cedente 
                l += Util.Texto.alinharEsquerda(nome_banco, 30); //Nome Banco 
                l += Util.Texto.alinharEsquerda(null, 10); //Brancos
                l += "1"; //Preencher '1'
                l += Util.Texto.alinharEsquerda(data_comp, 8); //Data da Gravação do Arquivo
                l += Util.Texto.alinharEsquerda(dataFormatada.replace(":", ""), 6); //Hora/Minuto/Segundo da Gravação do Arquivo
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", banco.getRemessa()), 6); //Nº Seqüencial de Remessa 
                l += "030"; //Preencher '030'
                l += "00000"; //Preencher '00000'
                l += Util.Texto.alinharEsquerda(null, 20); //Brancos
                l += Util.Texto.alinharEsquerda(null, 20); //Brancos
                l += Util.Texto.alinharEsquerda(null, 29); //Brancos
                l += "\n";

                // Registro Tipo 1 (Obrigatório) - Header de Arquivo Remessa
                l += "";
                l += "001"; //Preencher '001’
                l += "0001";  //Preencher '0001'
                l += "1"; //Preencher '0'
                l += "R"; //Preencher 'R'
                l += "01"; //Preencher '01' - Cobrança Registrada
                l += Util.Texto.alinharEsquerda(null, 2); //Brancos
                l += "030"; //Preencher '030'
                l += " "; // Espaço em branco
                l += "2"; //Tipo Beneficiário - CNPJ 
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000000", Long.parseLong(cnpj)), 15); //Número do CPF/CNPJ do Cedente
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000", Integer.parseInt(banco.getNumeroConvenio())), 9); //Número do Convênio Líder
                l += "0014"; //Preencher '0014'
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00", Integer.parseInt(banco.getBoletoCarteira().substring(0, 2))), 2); //Carteira de Cobrança
                l += Util.Texto.alinharEsquerda(n_variacao, 3);  // Variação Carteira
                l += Util.Texto.alinharEsquerda(null, 2); //Brancos  
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", Integer.parseInt(banco.getAgencia())), 5); //Número da Agência onde está cadastrado o convênio líder do cedente
                l += Util.Texto.alinharEsquerda(banco.getDvAgencia(), 1);  //Dígito Verificador - D.V. - do Prefixo da Agência
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000", Integer.parseInt(banco.getConta())), 12); //Número da Conta Corrente: Número da conta onde está cadastrado o Convênio Líder do Cedente
                l += Util.Texto.alinharEsquerda(banco.getDvConta(), 1); //Dígito Verificador - D.V. – do Número da Conta Corrente do Cedente
                l += " "; // Espaço em branco
                l += Util.Texto.alinharEsquerda("PREFEITURA MUNICIPAL DE AMÉRICO BRASILIENSE", 30); //Nome Orgão
                l += Util.Texto.alinharEsquerda(null, 40); //Brancos
                l += Util.Texto.alinharEsquerda(null, 40); //Brancos
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", banco.getRemessa()), 8); //Nº Seqüencial de Remessa 
                l += Util.Texto.alinharEsquerda(data_comp, 8); //Data da Gravação do Arquivo
                l += "00000000"; //Preencher '00000000'
                l += Util.Texto.alinharEsquerda(null, 33); //Brancos
                count++;
                l += "\n";

                for (Object[] obj : lstObj) {
                    l += "001"; //Preencher com 001
                    l += "0001"; //Preencher com 0001
                    l += "3"; //Preencher com 3
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", count_titulo), 5); //Nº Seqüencial do titulo
                    l += "P"; //Preencher 'P'
                    l += " ";
                    l += "01"; //Preencher com 01
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", Integer.parseInt(banco.getAgencia())), 5); //Prefixo da Agência: Número da Agência onde está cadastrado o convênio líder do cedente
                    l += Util.Texto.alinharEsquerda(banco.getDvAgencia(), 1);  //Dígito Verificador - D.V. - do Prefixo da Agência
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000", Integer.parseInt(banco.getConta())), 12); //Número da Conta Corrente: Número da conta onde está cadastrado o Convênio Líder do Cedente
                    l += Util.Texto.alinharEsquerda(banco.getDvConta(), 1); //Dígito Verificador - D.V. – do Número da Conta Corrente do Cedente
                    l += " ";
                    l += Util.Texto.alinharEsquerda(banco.getNumeroConvenio(), 7); //Número do Convênio Líder
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("0000000000", Util.extractInt(obj[5])), 10); //Uso da Empresa (nosso numero)
                    l += Util.Texto.alinharEsquerda(null, 3); //Brancos
                    l += "1"; //Preencher '1'
                    l += "0"; //Preencher '0'
                    l += " "; // Preencher com espaço
                    l += "0"; //Preencher '0'
                    l += "0"; //Preencher '0'
                    l += Util.Texto.alinharEsquerda(Util.extractInt(obj[5]), 15); //Uso da Empresa (nosso numero)
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[4])), 8); //Data da vencimento do Título
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000000000.00", Util.extractDouble(obj[2]))), 15); //Valor do Titulo
                    l += "00000"; //Preencher '00000'
                    l += " "; // Preencher com espaço
                    l += "02";
                    l += "N"; // Preencher N
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[12])), 8); //Data da emissão do Título
                    l += "2"; // Preencher 2
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[15])), 8); // //Data Juros 
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000000000.00", Util.truncarValor((Util.extractDouble(obj[2]) * 1 / 100) + 0.005, 2))), 15); //Valor a ser cobrado por Dia de Atraso (x 1% / 30)
                    l += "0"; // Preencher 0
                    l += "00000000"; // Preencher 00000000
                    l += "000000000000000"; // Preencher 000000000000000
                    l += "000000000000000"; // Preencher 000000000000000
                    l += "000000000000000"; // Preencher 000000000000000
                    l += Util.Texto.alinharEsquerda(null, 25); //Brancos
                    l += "3"; // Não protestar
                    l += "00"; //Número de Dias Para Protesto
                    l += "0";
                    l += "000";
                    l += "09";
                    l += "0000000000";
                    l += " "; // Preencher com espaço
                    count_titulo++;
                    count++;
                    l += "\n";

                    //Segmento Q
                    l += "001"; // Preencher 001
                    l += "0001"; // Preencher 0001
                    l += "3";//Preencher '3'
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", count_titulo), 5); //Nº Seqüencial do titulo
                    l += "Q"; // Preencher Q
                    l += " "; // Preencher com espaço
                    l += "01"; //Preencher com 01
                    l += Util.Texto.alinharEsquerda(Util.extractInt(obj[1]), 1); //Tipo de Inscrição do Sacado
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000000000000", Util.extractStr(obj[13]).equals("") ? "0" : Long.parseLong(Util.extractStr(obj[13]))), 15); //CPF/CNPJ Pagador
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[0]), 40); //Nome Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[7]), 40); //Endereço Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[8]), 15); //Bairro Sacado
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[10])), 8); //CEP Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[9]), 15); //Cidade Sacado
                    l += Util.Texto.alinharEsquerda(Util.extractStr(obj[14]), 2); //UF Sacado
                    l += "0"; // Preencher 0
                    l += "000000000000000"; // Preencher 000000000000000
                    l += Util.Texto.alinharEsquerda(null, 40); //Brancos
                    l += "000"; // Preencher 000
                    l += Util.Texto.alinharEsquerda(null, 20); //Brancos
                    l += Util.Texto.alinharEsquerda(null, 8); //Brancos
                    count_titulo++;
                    count++;
                    l += "\n";

                    //Segmento R
                    l += "001"; // Preencher 001
                    l += "0001"; // Preencher 0001
                    l += "3";//Preencher '3'
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000", count_titulo), 5); //Nº Seqüencial do titulo
                    l += "R"; // Preencher R
                    l += " "; // Preencher com espaço
                    l += "01";  // Preencher com 01
                    l += "0"; //Tipo de Inscrição do Sacado
                    l += "00000000"; //CPF/CNPJ Pagador
                    l += "000000000000000"; //Nome Pagador
                    l += "0"; //Preencher com 0
                    l += "00000000"; //CPF/CNPJ Pagador
                    l += "000000000000000"; //Nome Pagador
                    l += "1"; //Cidade Sacado
                    l += Util.Texto.alinharEsquerda(Util.formatarDecimal("00000000", Util.extractInt(obj[15])), 8); // //Data Juros 
                    l += Util.Texto.alinharEsquerda(Util.desmascarar(",", Util.formatarDecimal("0000000000000.00", Util.truncarValor((Util.extractDouble(obj[2]) * 2 / 100) + 0.005, 2))), 15); //Valor a ser cobrado por Dia de Atraso (x 1% / 30)
                    l += "          ";
                    l += "                                        ";
                    l += "                                        ";
                    l += "                    ";
                    l += "00000000";
                    l += "000";
                    l += "00000";
                    l += "0";
                    l += "000000000000";
                    l += "0";
                    l += "0";
                    l += "0";
                    l += "         ";
                    count_titulo++;
                    count++;
                    l += "\n";
                }

                //Trailler de arquivo de remessa - Tipo 5
                l += "";
                l += "001"; //Preencher '001'
                l += "0001"; //Preencher '0001'
                l += "5"; //Preencher com 5
                l += Util.Texto.alinharEsquerda(null, 9); //Brancos
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", count), 6); //Nº Seqüencial do Registro de Um em Um
                l += Util.Texto.alinharEsquerda(null, 217); //Brancos
                l += "\n";

                //Trailler de arquivo de remessa - Tipo 9
                l += "";
                l += "001"; //Preencher '001'z
                l += "9999"; //Preencher '9999'
                l += "9"; //Preencher com 9
                l += Util.Texto.alinharEsquerda(null, 9); //Brancos
                l += "000001"; //Preencher '000001'
                l += Util.Texto.alinharEsquerda(Util.formatarDecimal("000000", count + 2), 6); //Nº Seqüencial do Registro de Um em Um
                l += "000000"; //Preencher com 000000
                l += Util.Texto.alinharEsquerda(null, 205); //Brancos
                l += "\n";
                p.print(l);
                p.close();
            }
            atualizarMovimento(exercicio);
            atualizarRemessa(banco);
            Util.downloadFile(("BB" + Util.hoje().replace("/", "")), ("/resources/BB" + Util.hoje().replace("/", "")));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar remessa\n" + e.getMessage());
        }
    }

    private void atualizarRemessa(Banco banco) {
        try {
            banco.setRemessa(banco.getRemessa() + 1);
            setEntidade(Banco.class, banco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar remessa\n" + e.getMessage());
        }
    }

    private void atualizarMovimento(int exercicio) {
        try {
            List<IssqnMovimentoParcela> lp = getListaPura(IssqnMovimentoParcela.class, "select m from IssqnMovimentoParcela m"
                    + " inner join m.movimento i where (m.canceladoPagamento = 0 or m.canceladoPagamento is null)"
                    + " and m.vlParcela > 0 and m.dtBanco is null and (m.vlPago = 0 or m.vlPago is null) and i.idExercicio = ?1", exercicio);
            for (IssqnMovimentoParcela mp : lp) {
                mp.setDtBanco(new Date());
                setEntidade(IssqnMovimentoParcela.class, mp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar novimento\n" + e.getMessage());
        }
    }
}
