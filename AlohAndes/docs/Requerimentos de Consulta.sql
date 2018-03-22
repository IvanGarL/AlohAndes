//Requerimento 1, caso  operador '1' y año 2018

Select Sum(cobro)
from
(select cobro from reservas 
where operador = '1' and FECHAFIN > '1/1/2018')filtro;

//Requerimento 2

select * 
from
(select oferta ,count(oferta) as populares 
from reservas
group by oferta
order by populares desc)
WHERE ROWNUM <= 20;