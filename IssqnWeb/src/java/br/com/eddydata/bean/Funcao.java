/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean;

import br.com.eddydata.suporte.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

public class Funcao {

    public static void notificacaoSucesso(String txt) {
        RequestContext.getCurrentInstance()
                .execute("$.growl.notice({ message: '" + txt + "' });");
    }

    public static void aviso(String txt) {
        RequestContext.getCurrentInstance()
                .execute(" setTimeout(function () {swal({title: 'Erro ao salvar!',text: '" + txt + "',type: 'error'}); }, 100);");
    }

    public static void mensagemAtencao(String txt, String comentario) {
        RequestContext.getCurrentInstance()
                .execute(" setTimeout(function () {swal({title: '" + txt + "',text: '" + comentario + "',type: 'warning'}); }, 100);");
    }

    public static void avisoErro(String txt) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, txt, txt);
        FacesContext.getCurrentInstance().addMessage(txt, msg);
    }

    public static void avisoSucesso(String txt) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, txt, txt));
    }

    public static void avisoAtencao(String txt) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, txt, txt));
    }

    public static void resetarFormulario(String id) {
        RequestContext.getCurrentInstance().reset(id);
    }

    public static void executarJavaScript(String comando) {
        RequestContext.getCurrentInstance().execute(comando);
    }

    public static void atualizarForm(String id) {
        RequestContext.getCurrentInstance().update(id);
    }

    public static List<String> atributosClasse(Class classe) {
        List<String> str = new ArrayList<>();
        for (Field atr : classe.getDeclaredFields()) {
            str.add(atr.getName());
        }
        return str;
    }

    public static String converterStringEmMD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            BigInteger i = new BigInteger(1, m.digest());
            s = String.format("%1$032x", new Object[]{i});
        } catch (NoSuchAlgorithmException e) {
        }
        return s;
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
            Logger.getLogger(Funcao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip;
        FileOutputStream fileWriter;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    public static String abreviarString(String str, Integer n) {
        if (str == null || str.trim().equals("") || n == null) {
            return "";
        }
        return (str.length() < n ? str : str.substring(0, n));
    }

    public static long diferencaDatas(String data1, String data2) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(data1);
            d2 = df.parse(data2);
        } catch (java.text.ParseException evt) {
        }

        long dt = (d2.getTime() - d1.getTime()) + 3600000;
        long dias = (dt / 86400000L);

        return dias;
    }

    public static double arredondar(double valor, int casas, int ceilOrFloor) {
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (ceilOrFloor == 0) {
            arredondado = Math.ceil(arredondado);
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

    public static String chaveAutenticacao() {
        Random rand = new Random();
        char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                sb.append("-");
            }
            int ch = rand.nextInt(letras.length);
            sb.append(letras[ch]);
        }

        return sb.toString();
    }

}
