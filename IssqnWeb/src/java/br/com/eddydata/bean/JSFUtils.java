/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean;

import java.io.File;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.model.CroppedImage;

/**
 *
 * @author David
 */
public class JSFUtils {

    public static Flash flashScope() {
        return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
    }

    public static void executarJavascript(String script) {
        RequestContext.getCurrentInstance().execute(script);
    }

    public static void criarObjetoDeSessao(Object obj, String nome) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(nome, obj);
    }

    public static Object pegarObjetoDaSessao(String nomeSessao) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return sessao.getAttribute(nomeSessao);
    }

    public static void redirecionarPagina(String pagina) {
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/" + pagina);
        } catch (IOException ex) {
            System.out.println("Erro ao redirecionar para a página " + pagina + "\n" + ex.getMessage());
        }
    }

    public static void atualizarObjeto(String id) {
        RequestContext.getCurrentInstance().update(id);
    }

    public static String obterCaminhoImagem(String nome_arquivo, CroppedImage imagem) throws Exception {
        return obterCaminhoImagem(nome_arquivo, (imagem == null ? null : imagem.getBytes()));
    }

    public static String obterCaminhoImagem(String nome_arquivo, CaptureEvent imagem) throws Exception {
        return obterCaminhoImagem(nome_arquivo, (imagem == null ? null : imagem.getData()));
    }

    public static String obterCaminhoImagem(String nome_arquivo, byte[] imagem) throws Exception {
        String caminhoImagem = "";
        nome_arquivo = (nome_arquivo == null || nome_arquivo.trim().equals("") ? "imagem.png" : nome_arquivo);

        if (imagem == null) {
            caminhoImagem = "resources/img/sem_foto.png";
        } else {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
                String imageUsers = servletContext.getRealPath("/resources/img");
                File dirImageUsers = new File(imageUsers);
                if (!dirImageUsers.exists()) {
                    dirImageUsers.createNewFile();
                }

                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(dirImageUsers, nome_arquivo));
                imageOutput.write(imagem, 0, imagem.length);
                imageOutput.flush();
                imageOutput.close();

                caminhoImagem = "resources/img/" + nome_arquivo;
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return caminhoImagem;
    }
}
