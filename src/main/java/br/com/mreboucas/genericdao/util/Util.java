package br.com.mreboucas.genericdao.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;


/**
 * @author Marcelo Reboucas
 */
public class Util {
/*    private static String reportDirectoryPath;

    public static boolean validaData(String data)
    {
        try
        {
            if (data.indexOf('/')!=2 || data.indexOf('/',3)!=5 )
                return false;

            int dia = Integer.parseInt(data.substring(0,2));
            int mes = Integer.parseInt(data.substring(3,5));
            int ano = Integer.parseInt(data.substring(6,10));

            if ( dia<1  || dia > 31  )
                return false;
            if ( mes<1  || mes > 12  )
                return false;
            if ( ano<1   )
                return false;
        }
        catch (Exception e)
        {
            //Conexao.imprimirLog(e.getMessage());
            return false;
        }

        return true;
    }

    *//**
     * Remove ocorr�ncias de caracteres especiais
     * @param entrada (String de entrada)
     * @return String tratada, com as devidas substitui��es realizadas
     *//*
    public static StringBuilder semAcentos(String str) {
        str = str.replace('�','c');
        str = str.replace('�','C');
        str = str.replace('�','a');
        str = str.replace('�','o');
        str = str.replace('�','A');
        str = str.replace('�','O');
        str = str.replace('�','a');
        str = str.replace('�','e');
        str = str.replace('�','i');
        str = str.replace('�','o');
        str = str.replace('�','u');
        str = str.replace('�','A');
        str = str.replace('�','E');
        str = str.replace('�','I');
        str = str.replace('�','O');
        str = str.replace('�','U');
        str = str.replace('�','a');
        str = str.replace('�','e');
        str = str.replace('�','o');
        str = str.replace('�','u');
        str = str.replace('�','A');
        str = str.replace('�','E');
        str = str.replace('�','O');
        str = str.replace('�','U');
        str = str.replace('�','a');
        str = str.replace('�','A');
        str = str.replace('�',' ');
        str = str.replace('�',' ');
        str = str.replace('`',' ');
        str = str.replace('�',' ');
        str = str.replace("'","");
        str = str.replace('"',' ');
        str = str.replace('.',' ');
		str = str.replace(',',' ');

        return new StringBuilder(str);
    }

    *//**
     * Remove ocorr�ncias de caracteres especiais para oracle
     * @param entrada (String de entrada)
     * @return String tratada, com as devidas substitui��es realizadas
     *//*
    public static StringBuilder semAcentosOracle(String str) {
        str = str.replace('�',' ');
        str = str.replace('�',' ');
        str = str.replace('`',' ');
        str = str.replace('�',' ');
        str = str.replace('"',' ');
        str = str.replace("'","");
        str = str.replace('�','a');
        str = str.replace('�','A');
        return new StringBuilder(str);
    }

    *//**
     * Remove ocorr�ncias de aspas
     * @param entrada (String de entrada)
     * @return String tratada, com as devidas substitui��es realizadas
     *//*
    public static String semAspas(String str) {
        if (str != null && str instanceof String) {
            str = str.replace("`","");
            str = str.replace("�","");
            str = str.replace("\"","");
            str = str.replace("'","");
        }
        return str;
    }

    public static void Email(String mailServer, String subject, String to, String cc, String from, String mensagem) throws AddressException, MessagingException
    {
        Properties mailProps = new Properties();

        // **********
        // AECJ em 28/04/2003
        // Defini��o do Email Server 
        // **********
        mailProps.put("mail.smtp.host", mailServer);
        //mailProps.put("mail.smtp.host", "172.25.131.104");
        Session mailSession = Session.getDefaultInstance(mailProps,null);

        // **********
        // AECJ em 28/04/2003
        // As tr�s linhas a seguir coloca no formato dos endere�os
        // supostamento v�lidos, de email de dados passados pelos par�metros
        // to(para), cc(com c�pia) e from(quem enviou)
        // **********
        InternetAddress destinatario = new InternetAddress(to);
        InternetAddress remetente = new InternetAddress(from);
        //InternetAddress[] comcopiapara;
        //InternetAddress[] copia = new InternetAddress[10];
        Vector<InternetAddress> copias = new Vector<InternetAddress>();


        if (!cc.equals(""))
        {
            String[] msg = cc.split(";");
            for (int i = 0; i < msg.length; i++)
            {
                copias.add(new InternetAddress(msg[i].trim()));
            }
        }
        InternetAddress[] copia = new InternetAddress[copias.size()];
        int tamanho=0;
        while(tamanho < copias.size() )
        {
            copia[tamanho] = copias.elementAt(tamanho);
            tamanho++;
        }

        // **********
        // AECJ em 28/04/2003
        // As duas linhas a seguir s�o respons�veis por setar os 
        // atributos e propriedades necess�rias do objeto message 
        // para que o e-mail foi enviado
        // Inicializa��o do objeto message 
        // **********
        Message message = new MimeMessage(mailSession);

        // **********
        // AECJ em 28/04/2003 
        // Defini��o de quem est� enviando o e-mail  
        // **********
        message.setFrom(remetente);

        // **********
        // AECJ em 28/04/2003 
        // Define os destinat�rios e qual o tipo de destinat�rio
        // Os poss�veis destinat�rios s�o: TO, CC, BCC
        // **********
        message.setRecipient(Message.RecipientType.TO, destinatario);
        if (!cc.equals(""))
        {
            message.setRecipients(Message.RecipientType.CC, copia );
        }

        // **********
        // AECJ em 28/04/2003 
        // Defini��o do Assunto do e-mail
        // **********
        message.setSubject(subject);

        // **********   
        // AECJ em 28/04/2003 
        // Defini��o do Conte�do da Mesagem e do tipo da Mensagem 
        // **********
        message.setContent(mensagem.toString(),"text/html");


        // **********
        // AECJ em 24/04/2003 
        // Finalmente envia o e-mail para o destinat�rio
        // **********
        Transport.send(message);

    }

    public static boolean isFuture(String dateStr)
    {
        try
        {
            // Se a data for vazia
            if (dateStr !=null && dateStr.trim().length() ==0)
                return false;

            // Se a data n�o for vazia
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = new GregorianCalendar();

            //Pega a data a ser testada
            Date data = df.parse(dateStr);
            Calendar cal2 = new GregorianCalendar();
            cal2.setTime(data);

            return cal.before(cal2);
        }
        catch (Exception e)
        {
            br.com.cagece.sgp.base.Util.imprimirLogDebug(e);
        }

        return false;
    }

    public static boolean isDate(String dateStr)
        throws java.text.ParseException
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = new GregorianCalendar();

        // gerando o calendar
        cal.setTime(df.parse(dateStr));

        // separando os dados da string para comparacao e validacao
        String[] data = dateStr.split("[/ ]");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];

        // testando se hah discrepancia entre a data que foi
        // inserida no caledar e a data que foi passada como
        // string. se houver diferenca, a data passada era
        // invalida
        if ( (new Integer(dia)).intValue() != (new
                    Integer(cal.get(Calendar.DAY_OF_MONTH))).intValue() ) {
            // dia nao casou
            return(false);
        } else if ( (new Integer(mes)).intValue() != (new
                    Integer(cal.get(Calendar.MONTH)+1)).intValue() ) {
            // mes nao casou
            return(false);
        } else if ( (new Integer(ano)).intValue() != (new
                    Integer(cal.get(Calendar.YEAR))).intValue() ) {
            // ano nao casou
            return(false);
        }

        return(true);                       
    }
    public static Map<String, String> getColumnsName(ResultSet rs) throws SQLException
    {
        Map<String, String> retorno = new HashMap<String, String>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();

        for (int i = 1; i <= numColumns; i++)
        {
            String columnName = rsmd.getColumnName(i);
            retorno.put(columnName, columnName);
        }
        return retorno;
    }

    public static <T> String exibirCampoTela(T texto, String textoPadrao)
    {
        String retorno = null;
        
        if(texto != null && !texto.toString().trim().equalsIgnoreCase(""))
        {
            retorno = String.valueOf(texto);
        }
        else
        {
            retorno = textoPadrao;
        }

        return retorno;
    }

    public static String formatarGregorianCalendar(GregorianCalendar gregorianCalendar)
    {
        String retorno = null;
        if(gregorianCalendar != null)
        {
            String dia = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH) + "";
            String mes = gregorianCalendar.get(gregorianCalendar.MONTH) + 1 + "";
            int ano = gregorianCalendar.get(gregorianCalendar.YEAR);
            if(dia.length() == 1)
            {
                dia = "0" + dia;
            }
            if(mes.length() == 1)
            {
                mes = "0" + mes;
            }
            retorno = dia + "/" + mes + "/" + ano;
        }
        else
        {
            retorno = "";
        }
        return retorno;
    }

    public static String formatarGregorianCalendarComHora(GregorianCalendar gregorianCalendar)
    {
        String retorno = null;
        if(gregorianCalendar != null)
        {
            String dia = gregorianCalendar.get(gregorianCalendar.DAY_OF_MONTH) + "";
            String mes = gregorianCalendar.get(gregorianCalendar.MONTH) + 1 + "";
            int ano = gregorianCalendar.get(gregorianCalendar.YEAR);
            String hora = gregorianCalendar.get(gregorianCalendar.HOUR_OF_DAY) + "";
            String minuto = gregorianCalendar.get(gregorianCalendar.MINUTE) + "";
            String segundo = gregorianCalendar.get(gregorianCalendar.SECOND) + "";
            if(dia.length() == 1)
            {
                dia = "0" + dia;
            }
            if(mes.length() == 1)
            {
                mes = "0" + mes;
            }
            if(hora.length() == 1)
            {
                hora = "0" + hora;
            }
            if(minuto.length() == 1)
            {
                minuto = "0" + minuto;
            }
            if(segundo.length() == 1)
            {
                segundo = "0" + segundo;
            }
            retorno = dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto + ":" + segundo;
        }
        else
        {
            retorno = "";
        }
        return retorno;
    }

    public static String converterNumero(String pNumero)
    {
        DecimalFormat pattern = new DecimalFormat("#,#0;(#,#0)");
        Number n = null;
        try
        {
            n = pattern.parse(pNumero);
        }
        catch (ParseException e)
        {
            System.err.println("oops:" + pNumero);
            n = new Integer(0);
        }

        return(n.toString());
    }

    public static String converterNumeroFormatado(String pNumero)
    {
        DecimalFormat lFormato = new DecimalFormat("###,##0.00");
        pNumero = lFormato.format(Double.parseDouble(pNumero));

        return(pNumero);
    }

    public static String formataNumeroBrParaUs(String numero)
    {
        return numero.replaceAll("\\.", "").replaceAll(",", ".");
    }

    public static String formataNumeroUsParaBr(String numero) throws ParseException
    {
        String retorno = "";
        if(numero != null && !numero.trim().equalsIgnoreCase("null") && numero.trim().length() > 0)
        {

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            //NumberFormat nf = NumberFormat.getCurrencyInstance();
            //DecimalFormat df = new DecimalFormat(".##0,00");
            retorno = nf.format(new Double(numero)).replace("R$ ", "");
        }
        return retorno;
    }
    
    public static String formataNumeroUsParaBr(Object numero) throws ParseException {
        String retorno = "";
        
        if (numero != null) {
      	  retorno = formataNumeroUsParaBr(numero.toString());
    		}
    
        return retorno;
    }

  
    *//**
     * @author Albercio
     * @param request
     * @param endereco
     * @return url
     * M�todo respons�vel por montar uma url para um determinado endere�o
     *//*
    public static String getUrl(HttpServletRequest request, String endereco) {

        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" + endereco;
    }

    public static long calculaDuracao(String dataInicio, String dataFim){
        long duracaoProjeto=0;
        dataInicio=dataInicio.substring(0, 10);
        dataFim=dataFim.substring(0, 10);
        String[] dataProjeto= dataInicio.split("/");
        int anoProjeto=Integer.parseInt(dataProjeto[2]);
        int mesProjeto=Integer.parseInt(dataProjeto[1].startsWith("0")?dataProjeto[1].replaceFirst("0", ""):dataProjeto[1])-1;
        int diaProjeto=Integer.parseInt(dataProjeto[0].startsWith("0")?dataProjeto[0].replaceFirst("0", ""):dataProjeto[0]);
        GregorianCalendar dtInicioProjeto=new GregorianCalendar(anoProjeto,mesProjeto,diaProjeto);
        dataProjeto=dataFim.split("/");
        anoProjeto=Integer.parseInt(dataProjeto[2]);
        mesProjeto=Integer.parseInt(dataProjeto[1].startsWith("0")?dataProjeto[1].replaceFirst("0", ""):dataProjeto[1])-1;
        diaProjeto=Integer.parseInt(dataProjeto[0].startsWith("0")?dataProjeto[0].replaceFirst("0", ""):dataProjeto[0]);
        GregorianCalendar dtFimProjeto=new GregorianCalendar(anoProjeto,mesProjeto,diaProjeto);
        duracaoProjeto=(dtFimProjeto.getTimeInMillis()-dtInicioProjeto.getTimeInMillis())/(24*60*60*1000);
        return duracaoProjeto+1;

    }

    public static BtpProtocoloBase gerarBtpProtocolo(String protocolo)
    {
        String proCodUnidade = protocolo.substring(0, protocolo.indexOf("."));
        String proNumProtocolo = protocolo.substring(protocolo.indexOf(".") + 1, protocolo.indexOf("/"));
        String proNumAno = protocolo.substring(protocolo.indexOf("/") + 1, protocolo.indexOf("-"));
        String proNumDigito = protocolo.substring(protocolo.indexOf("-") + 1);
        BtpProtocoloBase btpProtocoloBase = new BtpProtocoloBase();
        btpProtocoloBase.setProCodUnidade(proCodUnidade);
        btpProtocoloBase.setProNumProtocolo(proNumProtocolo);
        btpProtocoloBase.setProNumAno(proNumAno);
        btpProtocoloBase.setProNumDigito(proNumDigito);
        btpProtocoloBase.setProtocoloFormatado(protocolo);

        return btpProtocoloBase;
    }

    public static String gerarMaskNumProtocolo(String unidProt, String anoProt, String numProt, String digitoProt) {
        String retorno = "";

        // A m�scara que deve aparecer na tela para o usu�rio deve ser a seguinte
        // uuuu.ssssss/aaaa-dd
        // onde uuuu � a Unidade Protocolizadora
        // ssssss � o N�mero sequencial do Protocolo
        // aaaa � o Ano de gera��o do Protocolo
        // dd � o d�gito verificador

         tratamento em caso de par�metros nulos 
        if (unidProt == null) unidProt = "0";
        if (anoProt == null) anoProt = "0";
        if (numProt == null) numProt = "0";
        if (digitoProt == null) digitoProt = "0";
         tratamento em caso de par�metros nulos 

        retorno = CageceUtil.StrZero(unidProt, 4) + "." + CageceUtil.StrZero(numProt, 6)
            + "/" + anoProt + "-" + CageceUtil.StrZero(digitoProt, 2);

        return retorno;
    }
    
    public static BtpProtocoloBase gerarBtpProtocoloByAttributes(String proCodUnidade, String proCodAno, String proNum, String proDigito) {
    	
    	String numProtocoloFormatato = gerarMaskNumProtocolo(proCodUnidade, proCodAno, proNum, proDigito);
    	
    	BtpProtocoloBase btpProtocoloBase = gerarBtpProtocolo(numProtocoloFormatato);
    	
    	return btpProtocoloBase;
    	
    }

    public static String exibir(String texto)
    {
        String retorno = null;
        if(texto != null && texto.trim().length() >= 0)
        {
            retorno = texto;
        }else{
            retorno = "Valor n�o informado";
        }
        return retorno;	
    }

    public static String exibirStatusDesapropriacao(String texto)
    {
        String retorno = "-";
        if(texto != null && texto.trim().length() >= 0)
        {
            retorno = "OK";
        }
        return retorno;	
    }

    *//**
     * M�todo respons�vel por montar uma url para um determinado endere�o
     * @param hostname
     * @param contextPath
     * @param endereco
     * @return
     *//*
    public static String getUrl(String hostname, String contextPath, String endereco) {

    	//New form
    	return "http://" + hostname + contextPath + "/" + endereco;
    	//Old form
    	//return "http://" + hostname + ":8080" + contextPath + "/" + endereco;
    }

    *//**
     * M�todo respons�vel por verificar o valor informado e retornar o valor a ser exibido
     * @param numero
     * @param valorMinimo
     * @param valorDefault
     * @return
     *//*
    public static String exibirLong(Long numero, Long valorMinimo, String valorDefault){
        String retorno = null;
        if(numero != null && numero >= valorMinimo){
            retorno = numero.toString();
        }else{
            retorno = valorDefault;
        }
        return retorno;
    }

    *//**
     * M�todo respons�vel por converter uma String em um GregorianCalendar
     * @author Albercio
     * @param data
     * @return
     *//*
    public static GregorianCalendar StringToGregorianCalendar(String dataHora){
        GregorianCalendar retorno = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            retorno = new GregorianCalendar();
            retorno.setTime(df.parse(dataHora));
        } catch (ParseException e) {
            br.com.cagece.sgp.base.Util.imprimirLogDebug(e);
        }
        return retorno;
    }


    *//**
     * M�todo respons�vel por formatar um Calendar com data e hora no formato dd/MM/yyyy HH:mm:ss
     * @author Albercio
     * @param calendar
     * @return data formatada
     *//*
    public static String formatarCalendarComHora(Calendar calendar)
    {
        String retorno = null;
        if(calendar != null)
        {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            retorno = df.format(calendar.getTime());
        }
        else
        {
            retorno = "";
        }
        return retorno;
    }

    public static EnumStatusAtividade retornaStatusAtividade(BtpProjetoBase projeto) throws ParseException{
        EnumStatusAtividade retorno=null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if(projeto.getPrjCodEstado().equalsIgnoreCase("C"))
        {
            retorno=EnumStatusAtividade.CONCLUIDA;
        }
        else{
            if(projeto.getPrjVersaoLb()==null){
                retorno=EnumStatusAtividade.NAO_VALIDADA;
            }else{
                if(df.parse(projeto.getPrjDatFim()).compareTo(df.parse(df.format(new Date()))) < 0)
                {
                    retorno=EnumStatusAtividade.ATRASADA;
                }
                else if(df.parse(projeto.getPrjDatInicio()).compareTo(df.parse(df.format(new Date()))) > 0){
                    retorno=EnumStatusAtividade.A_INICIAR;
                }
                else if(projeto.getPrjStsAprovacao()==null || projeto.getPrjStsAprovacao().equals("A") || projeto.getPrjStsAprovacao().equals("R") 
                        ||  projeto.getPrjStsAprovacao().equals("E")){
                    if(projeto.getPrjCodEstado().equalsIgnoreCase("A"))
                      {
                    retorno=EnumStatusAtividade.EM_ANDAMENTO;
                    //}
                    else
                      {
                      if(projeto.getPrjCodEstado().equalsIgnoreCase("N")|| projeto.getPrjCodEstado().equals("E")|| projeto.getPrjCodEstado().equals("R"))
                      {
                      retorno=EnumStatusAtividade.NAO_VALIDADA;
                      }
                      }
                }else{
                    if(projeto.getPrjStsAprovacao().equals("N") || projeto.getPrjStsAprovacao().equals("E")|| projeto.getPrjStsAprovacao().equals("R")){
                        retorno=EnumStatusAtividade.NAO_VALIDADA;
                    }
                }
            }
        }

        return retorno;
    }
    public static EnumStatusAtividade retornaStatusAtividade(BtpProjetoAcaoBase acao) throws ParseException{
        EnumStatusAtividade retorno=null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if((acao.getPacPcoAcao() != null && acao.getPacPcoAcao().trim().equals("100")) || (acao.getPacCodEstado() != null && acao.getPacCodEstado().equals("C"))){
            retorno=EnumStatusAtividade.CONCLUIDA;
        }else{
            if(df.parse(acao.getPacDatFim()).compareTo(df.parse(df.format(new Date()))) < 0)
            {	
                retorno=EnumStatusAtividade.ATRASADA;
            }else{
                if(between(new Date(), df.parse(acao.getPacDatInicio()), df.parse(acao.getPacDatFim())) && acao.getPacPcoAcao()!= null && !acao.getPacPcoAcao().trim().equals("0")){
                    retorno=EnumStatusAtividade.EM_ANDAMENTO;
                }else{
                    if(acao.getPacPcoAcao()== null || acao.getPacPcoAcao().trim().equals("0")){
                        retorno=EnumStatusAtividade.A_INICIAR;
                    }else{
                        if(acao.getPacDatInicioLB()==null || acao.getPacDatInicioLB().trim().length() <= 0){
                            retorno=EnumStatusAtividade.NAO_VALIDADA;
                        }else if(acao.getPacPcoAcao()!= null){
                            retorno=EnumStatusAtividade.EM_ANDAMENTO;
                        }
                    }
                }
            }
        }


        return retorno;
    }
    public static EnumStatusAtividade retornaStatusAtividade(BtpProjetoAcaoTarefaBase tarefa) throws ParseException{
        EnumStatusAtividade retorno=null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if(tarefa.getPatPcoTarefa().trim().equals("100")){
            retorno=EnumStatusAtividade.CONCLUIDA;
        }
        else if(tarefa.getPatVersaoLb()==null){
            retorno=EnumStatusAtividade.NAO_VALIDADA;
        }else{
            if(df.parse(tarefa.getPatDatFim()).compareTo(df.parse(df.format(new Date()))) < 0)
            {
                retorno=EnumStatusAtividade.ATRASADA;
            }
            else{
                if(tarefa.getPatPcoTarefa()==null ||tarefa.getPatPcoTarefa().trim().equals("0")){
                    retorno=EnumStatusAtividade.A_INICIAR;
                }else{
                    retorno=EnumStatusAtividade.EM_ANDAMENTO;
                }
            }
        }

        return retorno;
    }

    public static boolean between(Date dataBase, Date dataInicial, Date dataFinal){
        boolean retorno = false;
        if(dataBase.compareTo(dataInicial) >= 0 && dataBase.compareTo(dataFinal) <= 0){
            retorno = true;
        }
        return retorno;
    }

    public static String formatDateToStringBr(Date data){
        String retorno = null;

        if(data != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            retorno = df.format(data);
        }
        return retorno;
    }
    
    public static String formatDateStringUsToStringBr(String data){
        String retorno = null;

        if(data != null){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            retorno = df.format(data);
        }
        return retorno;
    }

    public static String arrayJavaToJavaScript(Object[] array,String regex){
        StringBuilder retorno=new StringBuilder("{");
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(Arrays.toString(array));
        String aspaSimples="";
        if(array instanceof String[]){
            aspaSimples="'";
        }
        int i=0;
        while(matcher.find()){
            retorno.append((i++)+":"+aspaSimples+matcher.group()+aspaSimples+",");
        }
        retorno.append("length:"+i);	
        retorno.append("}");
        return retorno.toString();
    }

    public static void verificarPermissao(String comando, HttpServletRequest request) throws UsuarioNaoAutorizadoException, UsuarioNaoAutenticadoException{
        CageceUtil.verificarPermissao(comando, request);
    }

    public static void verificarPermissaoDWR(HttpServletRequest request) throws UsuarioNaoAutorizadoException, UsuarioNaoAutenticadoException{
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        String classe = stackTraceElementArray[2].getClassName();
        classe = classe.substring(classe.lastIndexOf(".") + 1);
        String metodo = stackTraceElementArray[2].getMethodName();
        String comando = classe + "?metodo=" + metodo;
        Conexao.imprimirLog("Verificando permiss�o de acesso para " + comando);
        verificarPermissao(comando, request);
    }

    *//**
     * M�todo respons�vel por formatar um Date com data e hora no formato dd/MM/yyyy HH:mm:ss
     * @author Albercio
     * @param date
     * @return data formatada
     *//*
    public static String formatDateWithHourToStringBr(Date date)
    {
        String retorno = null;
        if(date != null)
        {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            retorno = df.format(date);
        }
        else
        {
            retorno = "";
        }
        return retorno;
    }


    *//**
     * @param string
     * @return
     *//*
    public static String getToStringOrDefault(Object objeto, String padrao) {
        String retorno = null;
        if(objeto != null){
            retorno = objeto.toString();
        }else{
            retorno = padrao;
        }
        return retorno;
    }

    public static String formatDateToStringUsingPattern(Date data, String pattern){
        String retorno = null;

        if(data != null){
            DateFormat df = new SimpleDateFormat(pattern);
            retorno = df.format(data);
        }
        return retorno;
    }

    public static String formatStringToBD(String texto){
        String retorno = null;
        if(texto != null){
            retorno = texto.replaceAll("'", "''").trim();
        }
        return retorno;
    }

    public static Calendar dateToCalendar(Date date){
        Calendar calendar = null;
        if(date != null){
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }

    @SuppressWarnings("unchecked")
    public static boolean listIsNotEmpty(Collection list){
        boolean retorno = true;
        if(list == null || list.size() <= 0){
            retorno = false;
        }

        return retorno;
    }

    public static boolean stringIsNotEmpty(String string){
        return isNotNullOrIsNotEmpty(string);
    }

    *//**
     * M�todo utilizado para completar o tamanho de uma string inserindo o complemento a esquerda da string at� o tamanho limite
     * @author Albercio
     * @param str
     * @param complemento
     * @param tamanho
     * @return string formatada
     *//*
    public static String lpad(String str, String complemento, int tamanho){
        StringBuilder retorno = new StringBuilder();
        if(str != null){
            retorno.append(str);
        }
        while(retorno.length() < tamanho){
            retorno.insert(0, complemento);
        }

        return retorno.toString();
    }

    *//**
     * M�todo utilizado para completar o tamanho de uma string inserindo o complemento a direita da string at� o tamanho limite
     * @author Albercio
     * @param str
     * @param complemento
     * @param tamanho
     *//*
    public static String rpad(String str, String complemento, int tamanho){
        StringBuilder retorno = new StringBuilder();
        if(str != null){
            retorno.append(str);
        }
        while(retorno.length() < tamanho){
            retorno.insert(retorno.length(), complemento);
        }

        return retorno.toString();
    }

    *//**
     * @author: Marcelo Rebou�as
     * @date: 01/06/2012 - 10:50:43
     * @description: M�todo respons�vel por converter bytes em megabytes - MB.	
     * legend: 1Mb = 1048576 bytes
     *//*
    public static BigDecimal convertBytesToMb(BigDecimal bytes){

        BigDecimal valor1MBEmBytes = new BigDecimal(1048576);

        BigDecimal megaBytes = bytes.divide(valor1MBEmBytes).setScale(2, BigDecimal.ROUND_UP);  

        return megaBytes;
    }

    *//**
     * @author: Marcelo Rebou�as
     * @date: 10/07/2012 - 15:39:17
     * @description: M�todo respons�vel por gerar a chave prim�ria - PK de acordo com a sequence	
     *//*
    public static BigDecimal generatePK(String sequence, Connection con) throws SQLException{

        PreparedStatement pstmt;
        ResultSet rs;

        BigDecimal key = null;

        String sql =  "SELECT " + sequence + ".NEXTVAL KEY FROM DUAL";

        pstmt = con.prepareStatement(sql);

        rs = Conexao.executeQueryPs(pstmt, sql);

        if(rs.next()){
            key = rs.getBigDecimal("KEY");
        }

        rs.close();

        return key;
    }

	*//**
	 * @author: Marcelo Rebou�as
	 * @date: 19/07/2012 - 10:44:25
	 *//*
	@SuppressWarnings("rawtypes")
	public static boolean collectionIsNotEmpty(Collection collection) {
		boolean isNotEmpty = true;

		if (collection == null || collection.isEmpty()) {

			isNotEmpty = false;

		}

		return isNotEmpty;
	}

	*//**
	 * Remove as acentua��es e os espa�os encontrados no texto contido em uma string.
	 * 
	 * @param row - linha com texto contido ( String )
	 * @return String
	 *//*
    public static String removeRowSpacesAndAccents(String row) {
        return MappUtil.removeRowSpacesAndAccents(row);
    }

    *//**
     * Substitui as acentua��es encontradas no texto contido em uma string, por valores correspondentes em octal.
     * 
     * @param row - linha com texto contido ( String )
     * @return String
     *//*
    public static String replaceAccentsWithOctal(String row) {
    	for (Character c : row.toCharArray()) {
    		CharSequence in = c.toString();
    		CharSequence out = MappUtil.getCharOctalValue(c) != null ? MappUtil.getCharOctalValue(c) : in;

    		row.replace(in, out);
    	}

    	return row;
    }

    *//**
     * 
     * @param dateInput (ex.: 05/10/2012 08:00)
     * @return Data em formato SQL, proveniente da vis�o (via formul�rios).
     *//*
    public static java.sql.Date getSqlDate(String dateInput) {
        Map<String, Integer> dateHash = getYearMonthDayHourOfDayMinuteHash(dateInput);

        Calendar calendar = Calendar.getInstance();

        calendar.set(dateHash.get("year"), dateHash.get("month"), dateHash.get("day"),
                dateHash.get("hourOfDay"), dateHash.get("minute"));

        return new java.sql.Date(calendar.getTimeInMillis());
    }

    *//**
     * 
     * @param dateInput (ex.: 05/10/2012 08:00)
     * @return Map< String, Integer > (keys: year; month; day; hourOfDay; minute)
     *//*
    public static Map<String, Integer> getYearMonthDayHourOfDayMinuteHash(String dateInput) {
        Map<String, Integer> dateHash = new HashMap<String, Integer>();

        int year = Integer.valueOf(dateInput.split("/")[2].split(" ")[0].trim());
        int month = Integer.valueOf(dateInput.split("/")[1].trim());
        int day = Integer.valueOf(dateInput.split("/")[0].trim());
        int hourOfDay = Integer.valueOf(dateInput.split(" ")[1].split(":")[0].trim());
        int minute = Integer.valueOf(dateInput.split(" ")[1].split(":")[1].trim());

        dateHash.put("year", year);
        dateHash.put("month", month);
        dateHash.put("day", day);
        dateHash.put("hourOfDay", hourOfDay);
        dateHash.put("minute", minute);

        return dateHash;
    }

    public static String removeDecimalFormat(String input) {
        input = input.replaceAll("\\.", "").replaceAll("\\,", ".");

        while (input.contains(".") && input.split("\\.").length > 2) {
            for (int i = (input.length() - 1); i > -1; i--) {
                if (input.charAt(i) == '.') {
                    input = input.substring(0, i) + input.substring((i + 1), input.length());

                    break;
                }
            }
        }

        return input;
    }

    *//**
     * M�todo que realoca uma data (de forma incremental), caso esta esteja definida para um s�bado, ou domingo.
     * 
     * @param dateTime (org.joda.time.DateTime)
     * @return org.joda.time.DateTime
     *//*
    public static DateTime avoidWeekend(DateTime dateTime) {
    	Integer sabado = Integer.valueOf(GeneralDefinitionEnum.JODA_SABADO.getValue());
    	Integer domingo = Integer.valueOf(GeneralDefinitionEnum.JODA_DOMINGO.getValue());

    	while (dateTime.getDayOfWeek() == sabado || dateTime.getDayOfWeek() == domingo) {
    		dateTime = dateTime.plusDays(1);

    		// redefine a hora para 8:00, em caso de incremento de dia e a hora n�o condizer com 8:00:
    		if (dateTime.getHourOfDay() != 8) {
    			dateTime = new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 8, 0);
    		}
    	}

    	return dateTime;
    }

    *//**
     * @author: Marcelo Rebou�as 25/10/2012 - 17:05:49
     * @description: M�todo respons�vel por retornar a instancia da data atual para inser��o no prepared statament
     * @obs: dessa maneira a hora � persistida de forma correta no ps
     *//*
    public static Timestamp getTimestampNow() {
        Calendar today =  Calendar.getInstance();
        return new Timestamp(today.getTimeInMillis());
    }
    
    *//**
     * @author: Marcelo Rebou�as - 01/11/2012 - 14:36:51
     * @description: M�todo respons�vel por verificar se a exception lan�ada refere-se a viola��o de integridade;
     *//*
    public static String verificaSQLIntegrityConstraintViolation(Exception e) {
        
    	String exception = null;
        
        if (e.getMessage().contains("violated") && e.getMessage().contains("integrity")) {
            exception =  "Houve viola��o de integridade. Este registro est� sendo utilizado por outro cadastro.";
        } else {
            exception = e.getMessage();
        }

        return exception;
    }

    public static String getReportDirectoryPath() {
        return reportDirectoryPath.endsWith(File.separator) ? reportDirectoryPath : reportDirectoryPath + File.separator;
    }

    public static void setReportDirectoryPath(String reportDirPath) {
        reportDirectoryPath = reportDirPath;
    }

    *//**
     * @author Marcelo Rebou�as Jul 14, 2014 - 3:54:06 PM
     * TODO: Merece depois um refactor para ser adicionado dentro de um filtro interceptador
     * Essa gambi est� sendo invocada da view cabecalho.jsp
     * @param request void
     *//*
    public static void setaReportDirectoryPathLoadServer(HttpServletRequest request) {
    	reportDirectoryPath = request.getSession().getServletContext().getRealPath(EnumReport.FULL_REPORT.getReportDirectory());
    }

    *//**
	 * @author: Marcelo Rebou�as - 19/12/2012 - 11:26:04
	 * @description: M�todo respons�vel por converter timestamp para calendar.
	 *//*
	public static Calendar timestampToCalendar(Long mills) {
		
		if (mills != null) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);
			return calendar;
			
		}
		
		return null;
	}
	
	*//**
	 * @author Marcelo Rebou�as - 10:20 - 21/02/2013
	 * @param params
	 * @param retornaCodAndDescricao - true retorna cod e descri��o: <option>>1 - Op��o</option> e 
	 * 				                   false retorna apenas descri��o : <option>op��o</option>
	 * @return htmlRetorno
	 *//*
	public static String getHtmlOptionsComboBox(Map<String, String> params, boolean retornaCodAndDescricao) {
		StringBuilder htmlRetorno = new StringBuilder();
		
		if (params != null && params.size() > 0) {
			//option com c�digo e descri��o
			if (retornaCodAndDescricao) {
				htmlRetorno.append(retornaHtmlCodigoAndDescricao(params));
			//option apenas com descri��o
			} else {
				htmlRetorno.append(retornaHtmlDescricao(params));
			}
		} else {
			htmlRetorno.append(Constants.OPTION_SEM_RESULTADO);
		}
		
		return htmlRetorno.toString();
	}
	
	private static String retornaHtmlDescricao(Map<String, String> params) {
		
		StringBuilder html = new StringBuilder(Constants.OPTION_SELECIONE);
		
		for (String key : params.keySet()) {
			html.append("<option id='"+key.trim()+"' value='"+key.trim()+"'>" + params.get(key).trim() + "</option>\n");
		}
		
		return html.toString();
	}
	
	private static String retornaHtmlCodigoAndDescricao(Map<String, String> params) {
		
		StringBuilder html = new StringBuilder(Constants.OPTION_SELECIONE);
		String descricao = " - ";
		
		for (String key : params.keySet()) {
		    
		    if (params.get(key) != null || params.get(key).trim() != null) {
		        descricao = params.get(key).trim();
            } 
		    
			html.append("<option id='"+key.trim()+"' value='"+key.trim()+"'>" + key + " - " + descricao + "</option>\n");
		}
		
		return html.toString();
	}
	*//**
	 * @author: Marcelo Rebou�as - Feb 26, 2013 - 11:26:19 AM
	 * @description: M�todo respons�vel por verificar se o n�mero do protocolo � v�lido, se ele existe na base do protoloco
	 * @returns: void
	 * @refactor: levar esse m�todo depois para uma classe de neg�cio do protocolo ;-/
	 *//*
	public static void validaProtocolo(BtpProtocoloBase btpProtocoloBase) throws Exception{
		Connection con = null;
		ArrayList<BtpProtocoloBase> btpProtocoloBaseList = null;
		try {
			con = Conexao.abreConexao();
			Map<String, String> params = new HashMap<String, String>();
			params.put("proNumAno", btpProtocoloBase.getProNumAno());
			params.put("proNumDigito", btpProtocoloBase.getProNumDigito());
			params.put("proCodUnidade", btpProtocoloBase.getProCodUnidade());
			params.put("proNumProtocolo", btpProtocoloBase.getProNumProtocolo());
			
			btpProtocoloBaseList = new PrsProtocoloBase().consultar(null, params, null, con);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.fechaConexao(con);
		}
	
		if(btpProtocoloBaseList.size() != 1){
		String msgErro = "N�mero do protocolo inv�lido.";
			throw new Exception(msgErro);
		}
	}
	
	*//**
     * @author: Anderson Luna - Abr 18, 2013 - 11:26:19 AM
     * @description: M�todo respons�vel por converter um bean em um map
     * @returns: void
     *//*
	@SuppressWarnings("unused")
    public static  Map<String, Object> convertObjectToMap(Object obj) throws 
	    IllegalAccessException, 
	    IllegalArgumentException, 
	    InvocationTargetException {
	    
	        String nomeAtributo = "";
	        Class<?> pomclass = obj.getClass();
	        pomclass = obj.getClass();
	        Method[] methods = obj.getClass().getMethods();
	        Map<String, Object> map = new HashMap<String, Object>();
	        
	        for (Method m : methods) {
	           
	        	if (m.getName().startsWith("get") && !m.getName().startsWith("getClass")) {
	              
	        		Object value = (Object) m.invoke(obj);
	        		
	        		if ((value != null) && (!value.equals(""))) {
	                  
	        			nomeAtributo = m.getName().substring(3);
	        			nomeAtributo = nomeAtributo.toLowerCase().charAt(0) + nomeAtributo.substring(1);
	        			map.put(nomeAtributo, (Object) value);
	        			
	              }
	           }
	        }
	        
	    return map;
	}
	
    
    public static BtpColaborador getColaboradorLogado(HttpServletRequest request) {
    	return (BtpColaborador) request.getSession().getAttribute("usuarioLogado");
    }
    
    public static String getMatriculaColaboradorLogado(HttpServletRequest request) {
    	BtpColaborador btpColaborador = getColaboradorLogado(request);
    	return btpColaborador.getColCodMatricula();
    }
    *//**
     * @author: Marcelo Rebou�as - Aug 1, 2013 - 9:46:49 AM
     * @description: retorna o �ltimo dia do m�s do objeto calendar passado por par�metro
     * @returns: int
     *//*
    public static int getLastDayOfMonth(Calendar calendar) {
    	return Calendar.getInstance().getActualMaximum(calendar.DAY_OF_MONTH);
    }
    *//**
     * @author: Marcelo Rebou�as - Aug 1, 2013 - 11:45:55 AM
     * @description: converte string para calendar 
     * @returns: Calendar
     *//*
    public static Calendar stringToCalendar(String data) {
    	
    	Calendar calendar = null;
    	int indiceArrayHora = 3;
    	int indiceArrayMin = 4;
    	int indiceArraySeg = 5;
   
    	try {
    		//Valida a data
			if (data != null && data.length() >= 10 && isDate(data) ) {
				
				//Captura a data atual
				calendar = Calendar.getInstance();
				
				*//**
				 * -----QUEBRA A DATA COM O SPLIT-----
				 * 
				 * Nesse momento, pode haver dois contextos.
				 * 1) data = 24/10/2013 
				 * 	 -> 24 - �ndice 0 do array;
				 * 	 -> 10 - �ndice 1 do array;
				 * 	 -> 2013 - �ndice 2 do array;	  
				 * 
				 * 2) data = 24/10/2013 14:20:12
				 * 	 --Data:
				 * 	 	mesmos �ndices acima	  
				 * 	 --Hora:
				 * 	 -> 14 - �ndice 3 do array;
				 * 	 -> 20 - �ndice 4 do array; 
				 * 	 -> 12 - �ndice 5 do array; 
				 *//*
				String[] dateList = data.split("[/: ]");
				int arrayLength = dateList.length - 1;
				
				//Seta o dia
				calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateList[0]));
				//Seta o m�s
				calendar.set(Calendar.MONTH, Integer.valueOf(dateList[1]) - 1);
				//Seta o ano
				calendar.set(Calendar.YEAR, Integer.valueOf(dateList[2]));
				
				//seta a hora
				if (arrayLength >= indiceArrayHora) 
					calendar.set(Calendar.HOUR, Integer.valueOf(dateList[indiceArrayHora]));
				else 
					calendar.set(Calendar.HOUR, 0);
					
				//seta os minutos
				if (arrayLength >= indiceArrayMin) 
					calendar.set(Calendar.MINUTE, Integer.valueOf(dateList[indiceArrayMin]));
				else 
					calendar.set(Calendar.MINUTE, 0);
					
				//seta os segundos
				if (arrayLength >= indiceArraySeg) 
					calendar.set(Calendar.SECOND, Integer.valueOf(dateList[indiceArraySeg]));
				 else 
					calendar.set(Calendar.SECOND, 0);
				
			} else {
				Conexao.imprimirLog("Data n�o v�lida");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return calendar;
    }
    *//**
     * @author: Marcelo Rebou�as - Oct 16, 2013 - 9:51:24 AM
     * @description: remove todos os acentos de uma string, os espa�os laterais e a torna mai�scula ou n�o
     * @returns: String sem acentos
     *//*
    public static String removeAllAccents(String string, boolean isUpperCase) {
    	
    	if (string != null && !string.isEmpty()) {
    		
    		if (isUpperCase) {
    			
    			return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase().trim();
    			
    		} else {

    			return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").trim();
    			
    		}
    	}
    	
    	return string;
    }
    *//**
     * @author: Marcelo Rebou�as - Oct 24, 2013 - 11:25:10 AM
     * @description: converte uma string to timestamp
     * @returns: Timestamp
     *//*
    public static Timestamp stringToTimestamp(String date) {
    	
    	return new Timestamp(stringToCalendar(date).getTimeInMillis());
    }
    
    *//**
     * @author: Marcelo Rebou�as - Nov 11, 2013 - 10:34:33 AM
     * @description:
     * @returns: boolean
     *//*
    public static boolean containsKeyAndKeyIsNotnull(ResultSet rs, Object field) throws SQLException {
    	
    	if (rs != null && rs.getObject((String) field) != null && 
    			rs.getObject((String) field).toString().trim().length() > 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    *//**
     * @author: Marcelo Rebou�as - Nov 28, 2013 - 4:22:11 PM
     * @description: M�todo respons�vel por retornar um timestamp a partir de um Date, Calendar ou String
     * @returns: Timestamp
     *//*
    public static <M> Timestamp convertCalendarOrDateOrStringToTimestamp(M data) {
    	
    	Timestamp timestamp = null;
    	Calendar calendar = null;
    	
    	if (data != null) {
    		
    		if (data instanceof Date) {
    			
    			calendar = dateToCalendar((Date) data);
    			
    		} else if (data instanceof Calendar) {
    			
    			calendar = (Calendar) data;
    			
    		} else {
    			
    			calendar = stringToCalendar(data.toString());
    			
    		}
    		
    		timestamp = new Timestamp(calendar.getTimeInMillis());
    		
    	}
    	
		return timestamp;
    }
    *//**
     * @author: Marcelo Rebou�as - Dec 2, 2013 - 11:53:21 AM
     * @description: retorna o dia da semana, o dia do m�s, o m�s e o ano
     * @returns: String
     *//*
    @SuppressWarnings("static-access")
    public static String getDataExtenso(boolean comDiaSemana) {
    	
    	Calendar calendar = Calendar.getInstance();
		Integer diaMes = calendar.get(Calendar.DAY_OF_MONTH);
    	Integer mes = calendar.get(Calendar.MONTH) + 1;
    	Integer ano = calendar.get(Calendar.YEAR);
    	String diaSemanaExtenso = "";
    	
    	if (comDiaSemana) {
    		diaSemanaExtenso = EnumDiasSemana.getDescricaoDiaSemana(calendar.get(Calendar.DAY_OF_WEEK)) + ", ";
    	}
    	
    	return diaSemanaExtenso + diaMes + " de " + getMes(mes) + " de " + ano + "." ;
    	
    }
    
    *//**
     * @author: Marcelo Rebou�as - Dec 3, 2013 - 11:03:03 AM
     * @description: retorna a descricao do m�s
     * @returns: String
     *//*
	public static String getMes(Integer mes) {
    	
		Map<Integer, String> hash = new HashMap<Integer, String>();
    	
    	if (mes != null && mes >= 1 & mes <= 12 ) {
    		
    		hash.put(1, "Janeiro");
    		hash.put(2, "Fevereiro");
    		hash.put(3, "Mar\347o");
    		hash.put(4, "Abril");
    		hash.put(5, "Maio");
    		hash.put(6, "Junho");
    		hash.put(7, "Julho");
    		hash.put(8, "Agosto");
    		hash.put(9, "Setembro");
    		hash.put(10, "Outubro");
    		hash.put(11, "Novembro");
    		hash.put(12, "Dezembro");
    		
    		return hash.get(mes).toString();
    		
    	} else 
    		Conexao.imprimirLog("M�s n�o definido corretamente");
    		return "-";
    }
	
    *//**
     * @author: Marcelo Rebou�as - Dec 11, 2013 - 3:54:44 PM
     * @description: verifica se uma determinada chave est� presente e n�o � nula em um hash gen�rico
     * @returns: boolean
     * @param map -> � o pr�prio Map<M,M> map
     * @param key -> � a chave do map = "sua_chave"
     *//*
    public static <M extends Object> boolean containsKeyAndKeyIsNotnull(Map<M, M> map, Object key){
    	
    	if (map != null && map.containsKey(key) && map.get(key) != null && 
    			!map.get(key).equals("")) {
    		return Boolean.TRUE;
    	} else {
    		return Boolean.FALSE;
    	}
    }
    
    *//**
     * @author: Marcelo Rebou�as - Jan 13, 2014 - 10:47:29 AM
     * @description: flags dos tipos de projetos (desabilitados, cancelados e encerrados) 
     * @returns: String
     *//*
    public static String getFlagProjetoDesabilitadoAndCanceladoAndEncerrado() {
    	
    	return "'" + EstadoProjetoEnum.DESABILITADO.getFlag()  + "'," + 
    		   "'" + EstadoProjetoEnum.CANCELADO.getFlag() + "'," +  
    		   "'" + EstadoProjetoEnum.ENCERRADO.getFlag() + "'";  
    }
    
    *//**
     * @author Marcelo Rebou�as Feb 6, 2014 4:21:58 PM
     *//*
    public static boolean isNotNullOrIsNotEmpty(Object value) {
    
    	if (value == null || value.toString().trim().equals(""))
    		return Boolean.FALSE;

    	return Boolean.TRUE;
    }
    
    *//**
     * @author Marcelo Rebou�as Jul 25, 2014 - 3:25:15 PM
     * @description Calcula a diferen�a entre datas, levando em considera��o os finais de semana. 		
     * @param dataInicio
     * @param dataFim
     * @return Integer
     * @obs: se suas datas estiverem em fortmato String, fa�a uso do m�todo
     * stringToCalendar(String data) que encontra-se nessa classe.
     *//*
    public static Integer calcularDiferencaEntreDatas(Calendar dataInicio, Calendar dataFim) {

    	Integer diferencaDias = null;
    	
    	if (isNotNullOrIsNotEmpty(dataInicio) && isNotNullOrIsNotEmpty(dataFim)) {
	    	
	    	Days days = Days.daysBetween(new DateTime(dataInicio.getTimeInMillis()), new DateTime(dataFim.getTimeInMillis()));
	    	
	    	diferencaDias = days.getDays();
    	
    	}
    	
    	return diferencaDias;
    	
    }
    
    public static void imprimirLogDebug(Exception exception) {
		if(!Conexao.isProducao){
			exception.printStackTrace();
		}else{
			try{
				String conteudo = "Usu�rio logado : " + new Conexao().getUsuarioLogado();
				MailSender.sendMail(conteudo, "Erro", exception);
			}catch (Exception e) {}
		}
	}
	
	public static void imprimirLogDebug(Exception exception, String sql) {
		if(!Conexao.isProducao){
			exception.printStackTrace();
		}else{
			try{
				String conteudo = "Usu�rio logado : " + new Conexao().getUsuarioLogado() + "\n";
					   conteudo += "SQL: " + sql;
				MailSender.sendMail(conteudo, "Erro", exception);
			}catch (Exception e) {
				
			}
		}
	}

	public static void imprimirLogDebug(String string) {
		if(!Conexao.isProducao){
			Conexao.imprimirLog(string);
		}
	}
	
	*//**
	 * @author Marcelo Rebou�as Nov 17, 2014 - 2:50:19 PM
	 * @param rs
	 * @param field
	 * @return boolean
	 * @throws SQLException 
	 *//*
	public static boolean containsFieldAndFieldIsNotNull(ResultSet rs , Object field) throws SQLException {
		
		if (rs != null && field != null && rs.getObject((String) field) != null && 
				rs.getObject((String) field).toString().trim().length() > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		} 
		
	}
	
	/**,
	 * @author Marcelo Rebou�as Aug 8, 2016 - 3:45:16 PM [marceloreboucas10@gmail.com]
	 * @description verifica se uma coluna encontra-se presente no hash e se seu valor no resultset
	 * � diferente de null. 		
	 * @param columnsNameMap
	 * @param columnName
	 * @return Boolean
	 * @throws SQLException 
	 */
	/*public static Boolean hasColumnsNameAndValueIsNotNull(Map<String, String> columnsNameMap, String columnName, ResultSet rs) throws SQLException {
		return (containsKeyAndKeyIsNotnull(columnsNameMap, columnName) && containsFieldAndFieldIsNotNull(rs, columnName)) ? Boolean.TRUE : Boolean.FALSE; 
	}*/
	
	public static void removeTransients(final Object entidade) {
        if (entidade != null){
            try{
                final PropertyDescriptor[] properties = Introspector.getBeanInfo(entidade.getClass())
                    .getPropertyDescriptors();
                for(final PropertyDescriptor property: properties){
                    // get e set
                    if (property.getReadMethod() != null && property.getWriteMethod() != null){
                        final Field field = getField(property.getName(), entidade.getClass());
                        if (field != null){
                            if (field.isAnnotationPresent(Transient.class)){
                                property.getWriteMethod().invoke(entidade, new Object[] { null });
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void removerTodosExcetoId(final Object entidade) {
        if (entidade != null){
            try{
                final PropertyDescriptor[] properties = Introspector.getBeanInfo(entidade.getClass())
                    .getPropertyDescriptors();
                for(final PropertyDescriptor property: properties){
                    // get e set
                    if (property.getReadMethod() != null && property.getWriteMethod() != null){
                        final Field field = getField(property.getName(), entidade.getClass());
                        if (field != null){
                            if (!field.isAnnotationPresent(Id.class)){
                                property.getWriteMethod().invoke(entidade, new Object[] { null });
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

     /**
     * @author Marcelo Rebou�as Abr 3, 2014 - 11:50:22 AM
     * @description recupera o atributo 'field' de uma objeto por meio
     * de refex�o		
     * @param fieldName
     * @param clazz
     * @return Field
     */
    public static Field getField(final String fieldName, final Class<?> clazz) {
        
    	Field field = null;
        
        try {
            
        	field = clazz.getDeclaredField(fieldName);
        	
         } catch (final NoSuchFieldException e) {
            
        	 if (!clazz.getSuperclass().equals(Object.class)) {
        		 
                return getField(fieldName, clazz.getSuperclass());
                
            }
        }
        return field;
    }

    /* *
     * Realiza uma busca pelas anota��es 'SearchParameter' contidas nas entidades e insere apenas as n�o nulas
     * no hash de retorno. O acesso a um item do hash � realizado atrav�s de uma chave string com a descri��o
     * do atributo e o valor obtido ser� um objeto gen�rico.
     * 
     * @param entity
     * @return hash com todos os registros que contenham valor; obtidos das anota��es 'SearchParameter' nas entidades.
     */
    @SuppressWarnings("unchecked")
	public static <X, Y> Map<String, Y> getFilledSearchAnnotation(final X entity) {
		Map<String, Y> returnHash = new HashMap<String, Y>();

		if (entity != null) {
			try {
				final PropertyDescriptor[] properties = Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors();

				for (final PropertyDescriptor property : properties) {
					if (property.getReadMethod() != null && property.getWriteMethod() != null) {
						final Field field = getField(property.getName(), entity.getClass());
						
						
						if (field != null) {
							
							if (field.isAnnotationPresent(SearchParameter.class)) {
								if (property.getReadMethod().invoke(entity) != null) {
									returnHash.put(property.getName(), (Y) property.getReadMethod().invoke(entity));
								}
							}
						}
					}
				}
			} catch (IntrospectionException iex) {
				iex.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		return returnHash;
	}
}