/** Classe para tratar datas solicitadas pela aplicação */
public class Data {

    private int dia;
    private int mes;
    private int ano;
    private int diaAno;
    private String diaSemana;
    private boolean dataValida = false;
    static String excecao = "";


    /** 
     * Construtor simples para criação de um objeto da classe Data
     */
    public Data() {

    }

    /**
     * Contrutor da classe Data, recebe como parâmetro os elementos da data
     * validando as informações passadas por parâmetro. Ao receber data inválida,
     * retorna 0
     * Data válida exemplo: 01/01/2022
     * 
     * @param dia Dia para a data
     * @param mes Mês para a data
     * @param ano Ano para a data
     */
    public Data(int dia, int mes, int ano) {

        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.diaAno = diaAno(dia, mes, ano);
        this.diaSemana = DiaSemana(dia, mes, ano);
        this.dataValida = (dia > 0) && (mes > 0) && (ano > 0) ? (true) : (false);
    }

    /**
     * Contrutor da classe Data, recebe como parâmetro um inteiro que representa o
     * dia incrementado a partir do dia 01/01/0001. Exemplo, o dia 24/08/2022 seria
     * 738391.
     * Ao receber um dia inválido, retorna 0
     * Data válida exemplo: 01/01/2022
     * 
     * @param diaAno Valor inteiro referente à data selecionada
     */
    public Data(int diaAno) {
        int[] dataconvert = dataDiaAno(diaAno);
        this.dia = dataconvert[0];
        this.mes = dataconvert[1];
        this.ano = dataconvert[2];
        this.diaAno = diaAno(dia, mes, ano);
        this.diaSemana = DiaSemana(dia, mes, ano);
        this.dataValida = (dia > 0) && (mes > 0) && (ano > 0) ? (true) : (false);
    }

    /**
     * Método para verificar se o ano passado como parâmetro é bissexto
     * 
     * @param ano Ano de uma data desejada
     * @return Retorna 1 quando é bissexto e 0 quando não é bissexto
     */
    public static int verificaBissexto(int ano) {
        int anoBis = 0;
        if (((ano % 4 == 0) && (ano % 100 != 0)) || (ano % 400 == 0)) {
            anoBis = 1;
        }
        return anoBis;
    }

    /**
     * Método para retornar dia da semana em que a data passada como parâmetro
     * ocorre
     * 
     * @param dia Dia da data
     * @param mes Mês da data
     * @param ano Ano da data
     * @return Dia da semana em que a data ocorre, como por exemplo "Segunda-feira"
     */
    public static String DiaSemana(int dia, int mes, int ano) {
        String[] dSemana = { "Domingo", "Segunda-feira", "Terca-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
                "Sábado" };
        int diaDaSemanaMes[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
        if (mes < 3)
            ano -= 1;
        return dSemana[(ano + ano / 4 - ano / 100 + ano / 400 + diaDaSemanaMes[mes - 1] + dia) % 7];
    }

    /**
     * Método para verificar a quantidade de dias no mês, validando inclusive se o
     * ano é bissexto
     * algoritmo inspirado no algoritmo
     * 
     * @param mes Mes da data
     * @param ano Ano da Data
     * @return Quantidade de dias no mês
     */
    public static int diasMes(int mes, int ano) {
        int dMes = 31 - ((mes == 2) ? (3 - verificaBissexto(ano)) : (((mes - 1) % 7) % 2));
        return dMes;
    }

    /**
     * Método para retornar o dia acumulado de uma data. Exemplo, a data 24/08/2022 seria
     * 738391.
     * @param dia Dia da data
     * @param mes Mês da data
     * @param ano Ano da data
     * @return Retorna o inteiro referente à data passada como parâmetro
     */

    public static int diaAno(int dia, int mes, int ano) {
        int diaAno = 0;

        for (int a = 1; a < ano; a++) {
            for (int m = 1; m <= 12; m++) {
                diaAno += diasMes(m, a);
            }
        }
        // System.out.println("acum " + diaAno);
        for (int m = 1; m < mes; m++) {
            diaAno += diasMes(m, ano);
        }

        diaAno = diaAno + dia;
        return diaAno;
    }

    /**
     *Método para retornar a data de um dia acumulado. Exemplo, o dia 738391 seria a data 24/08/2022
     * @param diaAno Inteiro do dia acumulado
     * @return Retorna a data referente ao dia acumulado
     */

    public static int[] dataDiaAno(int diaAno) {
        int diaAnoTemp = diaAno;
        if (diaAno >= 1) {
            int mesTemp = 1;
            int ano = 1;

            while (diaAnoTemp - diasMes(mesTemp, ano) > 0) {
                diaAnoTemp = diaAnoTemp - diasMes(mesTemp, ano);
                mesTemp++;
                if (mesTemp > 12) {
                    mesTemp = 1;
                    ano = ano + 1;
                }
            }

            int[] dataDiaAnoTemp = { diaAnoTemp, mesTemp, ano };
            return dataDiaAnoTemp;
        } else {
            int[] dataDiaAnoTemp = { 0, 0, 0 };
            return dataDiaAnoTemp;
        }
    }

    /**
     * Método verifica se a data informada por parâmetro é uma data válida
     * @param dataEntr Data a ser verificada
     * @return Retorna objeto da classe Data
     */
    public static Data verificaData(String dataEntr) {
        int dia = 0;
        int mes = 0;
        int ano = 0;
        Data dataAvaliada = new Data();

        String[] vetDataStr = dataEntr.split("/");
        if (vetDataStr.length == 3) {
            if (verificaNumero(vetDataStr) != false) {
                dia = Integer.parseInt(vetDataStr[0]);
                mes = Integer.parseInt(vetDataStr[1]);
                ano = Integer.parseInt(vetDataStr[2]);
                if (verificaValores(dia, mes, ano) == true) {
                    dataAvaliada.setDia(dia);
                    dataAvaliada.setMes(mes);
                    dataAvaliada.setAno(ano);
                    Data diaAgenda = new Data(dia, mes, ano);
                    dataAvaliada = diaAgenda;
                }
            }
        }
        else{
            excecao = excecao + "O formato digitado para data esta incorreto. Formato correto: DD/MM/AAAA\n";
        }
        return dataAvaliada;
    }

    /**
     * Metodo verifica se os valores de dia, mês e ano passados como parâmetro formam uma data coesa
     * @param dia Dia da data
     * @param mes Mês da data
     * @param ano Ano da data
     * @return Retorna true caso os parâmetros formem uma data coesa e false caso contrário
     */
    public static boolean verificaValores(int dia, int mes, int ano) {
        boolean valorValido = false;
        if (dia > 0 && mes > 0 && ano > 0) {
            if (dia <= diasMes(mes, ano) && mes <= 12 && ano < 10000) {
                valorValido = true;
            } else {
                if (dia <= 0 || dia > diasMes(mes, ano)) {
                    excecao = excecao + "\"O dia \" + dia + \" nao e valido para o mes \" + mes\n"
                    + "O mes " + mes + " tem " + diasMes(mes, ano) + " dias.\n";
                }
                if (mes <= 0 || mes > 12) {
                    excecao = excecao + "O mes " + mes + " nao e valido.\n";
                }
                if (ano == 2038) {
                    excecao = excecao + "O ano " + ano
                    + " nao e contemplado na avaliacao. Problema Y2K38 - Gangnam Style. :-) \n";
                }
                if (ano > 10000) {
                    excecao = excecao + "Ano " + ano + " � invalido!\n";
                    excecao = excecao + "Os anos pos 10.000 nao sao avaliados.\n";
                }

            }
        }
        return valorValido;
    }

    /**
     * Método verifica se a data informada possui apenas números em sua composição
     * @param strData Array de Strings com dia, mês e ano em popsições diferentes
     * @return Retorna true caso seja numérico e false caso contrário
     */
    public static boolean verificaNumero(String[] strData) {
        boolean numValido = false;
        
        for (int i = 0; i < strData.length; i++) {
            //Regex para validar caracteres
            if(strData[i].matches("[0-9]*")){
                numValido = true;
            }
            else {
                excecao = excecao + "Apenas numeros no formato DD/MM/AAAA sao validos!\n ";
                numValido = false;
                return numValido;
            }
        }
        return numValido;
    }
 /**
     * Método verifica se a data informada está entre duas datas passadas por parâmetro
     * @param  verificar data a ser verificada.
     * @param  dtInicio data de inicio periodo de avaliação.
     * @param  dtFinal data final do periodo de avaliação..
     * @return Retorna true caso verificar esteja dentro do período, caso contrário retorna false.
     *     */
    public  static boolean verificaDataPeriodo(Data verificar, Data dtInicio, Data dtFinal) {
       return  verificar.getDiaAno() >= dtInicio.getDiaAno() && verificar.getDiaAno() <= dtFinal.getDiaAno();
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDiaAno() {
        return diaAno;
    }

    public void setDiaAno(int diaAno) {
        this.diaAno = diaAno;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean getDataValida() {
        return dataValida;
    }

    public void setDataValida(boolean dataValida) {
        this.dataValida = dataValida;
    }

}