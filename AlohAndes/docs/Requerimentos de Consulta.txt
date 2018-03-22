Select Sum(cobro)
from
(select cobro from reservas 
where operador = '%1$s' and FECHAFIN > '1/1/2018')filtro;


select * 
from
(select oferta ,count(oferta) as populares 
from reservas
group by oferta
order by populares desc)
WHERE ROWNUM <= 20;