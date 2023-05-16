package br.ufpi.dadosabertosapi.utils;

public class ConstantesCKAN {
		
	//Nome do package/conjunto de dados no CKAN
	public static final String PACKAGE_FUNCOES_CONFIANCA = "funcoes-confianca";
	public static final String PACKAGE_PROGRAMA_STRICTO_SENSO = "programa-stricto-senso";
	public static final String PACKAGE_CURSOS_GRADUACAO = "curso-de-graduacao";
	
	public static final String FORMAT_CSV = "csv";
	public static final String FORMAT_XLS = "xls";

	//Paths of the queries at src/main/resources/META-INF/queries
	public static final String PATH_QUERY_SERVIDOR_POR_FUNCAO_CONFIANCA = "servidor_por_nivel_designacao";
	public static final String PATH_QUERY_PROGRAMAS_POS_STRICTO_SENSO = "programa_pos_stricto_senso";
	public static final String PATH_QUERY_CURSOS_GRADUACAO = "cursos_graduacao";
	
	//Tempo de atualização dos dados
	public static final String EXECUTA_POR_ANO = "POR_ANO";
	public static final String EXECUTA_POR_MES = "POR_MES";
	public static final String EXECUTA_POR_SEMESTRE = "POR_SEMESTRE";
	public static final String EXECUTA_POR_BIMESTRE = "POR_BIMESTRE";
	
	//Tipos bancos
	public static final String BANCO_SIGAA="SIGAA";
	public static final String BANCO_ADMINISTRATIVO="ADMINISTRATIVO";
	public static final String BANCO_REDMINE="REDMINE";
	public static final String BANCO_SISTEMASCOMUM="SISTEMAS_COMUM";
	
}
