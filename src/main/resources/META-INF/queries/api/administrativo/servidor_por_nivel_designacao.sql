select pes.nome, ati.descricao as descricao, un.nome as Unidade, nd.sigla as Nivel, 
nd.id_nivel_designacao as idNivel, un2.nome as UnidResponsavel, un2.id_unidade as unidade2
from rh.designacao as d
 join rh.servidor as s on (s.id_servidor = d.id_servidor)
 join comum.pessoa as pes on (s.id_pessoa = pes.id_pessoa)
 join rh.atividade as ati on (ati.id_atividade = d.id_atividade)
 join comum.unidade as un on (d.id_unidade = un.id_unidade)
 join comum.unidade as un2 on ( CAST(SPLIT_PART(un.hierarquia, '.',3) AS INTEGER) = un2.id_unidade)
 join funcional.nivel_designacao as nd on (nd.id_nivel_designacao = d.id_nivel_designacao)
where d.inicio < CURRENT_DATE AND d.fim > CURRENT_DATE
AND d.remuneracao = true ORDER BY unidade2, idNivel, descricao,nome asc ;