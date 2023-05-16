select distinct c.nome as NomeCurso, p.nome as NomeDoCoordenador, ga.descricao as GrauAcademico,
me.descricao as Tipo, ac.nome as Area, toc.descricao as TipoDeOferta,  
coalesce(tc.descricao, 'Um ciclo') as TipoCiclo,
m.nome as CidadeDoCampus , u.nome as NomeDaUnidade,  ci.nome as Campus,
'https://sigaa.ufpi.br/sigaa/public/curso/portal.jsf?id=' || c.id_curso || '&lc=pt_BR' as website,
c.codigo_inep as INEP, c.dou as DOU, c.reconhecimentoportaria as Portaria, ca.descricao as Convênio
from curso c left join (
		select distinct cc.id_curso, cc.id_servidor
		from ensino.coordenacao_curso cc join comum.registro_entrada re on re.id_entrada = cc.id_registro_atribuidor
			join (
				select cc.id_curso, max(data) as data
				from ensino.coordenacao_curso cc join comum.registro_entrada re on re.id_entrada = cc.id_registro_atribuidor
				where cc.id_curso is not null
				and cc.id_cargo_academico = 1
				and cc.ativo
				and current_date between cc.data_inicio_mandato and cc.data_fim_mandato
				group by cc.id_curso
			) as ru on ru.id_curso = cc.id_curso and ru.data = re.data
		where cc.id_cargo_academico = 1
		and cc.ativo
		and current_date between cc.data_inicio_mandato and cc.data_fim_mandato
	) as cc on cc.id_curso = c.id_curso
	left join rh.servidor s on s.id_servidor = cc.id_servidor
	left join comum.pessoa p on p.id_pessoa = s.id_pessoa
	join graduacao.matriz_curricular mc on mc.id_curso = c.id_curso 
	join ensino.grau_academico ga on ga.id_grau_academico = mc.id_grau_academico
	join comum.modalidade_educacao me on me.id_modalidade_educacao = c.id_modalidade_educacao
	left join ensino.area_sesu ac on ac.id_area_sesu = c.id_area_sesu
	join ensino.tipo_oferta_curso toc on toc.id_tipo_oferta_curso = c.id_tipo_oferta_curso
	join ensino.turno t on t.id_turno = mc.id_turno
	left join ensino.tipo_ciclo_formacao tc on tc.id_tipo_ciclo_formacao = c.id_tipo_ciclo_formacao
	join comum.campus_ies ci on ci.id_campus = mc.id_campus
	join comum.endereco e on e.id_endereco = ci.id_endereco
	join comum.municipio m on m.id_municipio = e.id_municipio
	join comum.unidade u on u.id_unidade = c.id_unidade
	left join ensino.convenio_academico ca on ca.id_convenio_academico = c.id_convenio
where c.ativo
and c.nivel = 'G'
and mc.ativo
order by c.nome