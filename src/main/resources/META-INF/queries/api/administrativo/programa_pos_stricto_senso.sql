select c.nome as Programa,  u.nome as Unidade, u.sigla as Sigla, u.sigla_academica as SiglaAcademica, ac.nome as AreaCNPQ
from public.curso c join comum.unidade u on u.id_unidade = c.id_unidade
	join comum.area_conhecimento_cnpq ac on ac.id_area_conhecimento_cnpq = c.id_area_curso
where c.ativo
and c.nivel in ('E', 'D')
order by programa