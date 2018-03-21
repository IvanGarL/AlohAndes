//----------------------------------
// Usuarios
//----------------------------------

create table usuarios(
id varchar(60),
contrasenia varchar(64),
login varchar (64),
tipo varchar (20)
);

alter table usuarios
modify contrasenia not null;

alter table usuarios
modify login not null;

alter table usuarios
add CONSTRAINT PK_Usuario PRIMARY KEY (id);

alter table usuarios
add constraint CHK_tipousvalido check (tipo in ('cliente','responsable'));


//----------------------------------
// clientes
//----------------------------------

create table clientes(
id varchar(60),
cedula varchar(20),
edad Number(3),
nombre varchar(60),
telefono varchar(20)
);

alter table clientes
modify cedula not null;

alter table clientes
modify edad not null;

alter table clientes
modify nombre not null;

alter table clientes
modify telefono not null;

alter table clientes
add CONSTRAINT CHK_Cliente CHECK (edad>=18);

alter table clientes
add CONSTRAINT PK_Cliente PRIMARY KEY (id);

alter table clientes
add FOREIGN KEY (id) references usuarios(id) ON DELETE CASCADE;

//----------------------------------
// operadores
//----------------------------------

create table operadores(
id varchar(60),
capacidad integer Default 0,
tipo varchar(10),
nombre varchar(60),
telefono varchar(20)
);

alter table operadores
modify capacidad not null;

alter table operadores
modify tipo not null;

alter table operadores
modify nombre not null;

alter table operadores
modify telefono not null;

alter table operadores
add constraint CHK_tipoopvalido check (tipo in ('hotel','pernat','percom','hostal','vivuni'));

alter table operadores 
add CONSTRAINT PK_Operador PRIMARY KEY (id);

//----------------------------------
// responsables
//----------------------------------

create table responsables(
id varchar(60),
cedula varchar(20),
edad Number(3),
nombre varchar(60),
telefono varchar(20),
operador varchar(60) 
);

alter table responsables
modify cedula not null;

alter table responsables
modify edad not null;

alter table responsables
modify telefono not null;

alter table responsables
modify operador not null;

alter table responsables
modify nombre not null;

alter table responsables
add CONSTRAINT CHK_Responsable CHECK (edad>=18);

alter table responsables
add CONSTRAINT PK_Responsable PRIMARY KEY (id);

alter table responsables
add FOREIGN KEY (id) references usuarios(id);

alter table responsables
add FOREIGN KEY (operador) references operadores(id);

//----------------------------------
// hoteles
//----------------------------------

create table hoteles(
id varchar(60),
direccion varchar(60),
estrellas number(1),
rut varchar(60),
habDisponibles integer DEFAULT 0,
habOcupadas integer DEFAULT 0
);

alter table hoteles
modify direccion not null;

alter table hoteles
modify rut not null;

alter table hoteles
modify habDisponibles not null;

alter table hoteles
modify habOcupadas not null;

alter table hoteles
add CONSTRAINT CHK_HotelesHabitaciones CHECK ( HabDisponibles >= 0 and habOcupadas >= 0);

alter table hoteles
add CONSTRAINT CHK_HotelesEst CHECK (estrellas>=0 and estrellas<=5);

alter table hoteles
add CONSTRAINT PK_Hotel PRIMARY KEY (id);

alter table hoteles
add FOREIGN KEY (id) references operadores(id);


//----------------------------------
// hostales
//----------------------------------

create table hostales(
id varchar(60),
direccion varchar(60),
horacierre number(2),
horaapertura number(2),
rut varchar(60),
habDisponibles integer DEFAULT 0,
habOcupadas integer DEFAULT 0
);

alter table hostales
modify direccion not null;

alter table hostales
modify horacierre not null;

alter table hostales
modify horaapertura not null;

alter table hostales
modify rut not null;

alter table hostales
modify habDisponibles not null;

alter table hostales
modify habOcupadas not null;

alter table hostales
add CONSTRAINT CHK_HostalesHabitaciones CHECK ( HabDisponibles >= 0 and habOcupadas >= 0);

alter table hostales
add CONSTRAINT CHK_HorasCierreHostal CHECK ( horacierre >= 0 and horacierre <= 23 and horaapertura >= 0 and horaapertura <= 23);

alter table hostales
add CONSTRAINT PK_Hostal PRIMARY KEY (id);

alter table hostales
add FOREIGN KEY (id) references operadores(id);

//----------------------------------
// personasnats
//----------------------------------


create table personasnats(
id varchar(60),
cedula varchar(20),
edad Number(3)

);

alter table personasnats
modify cedula not null;

alter table personasnats
modify edad not null;

alter table personasnats
add CONSTRAINT PK_Personasnats PRIMARY KEY (id);

alter table personasnats
add FOREIGN KEY (id) references operadores(id);

//----------------------------------
// personascom
//----------------------------------

create table personascom(
id varchar(60),
cedula varchar(20),
edad Number(3)

);

alter table personascom
modify cedula not null;

alter table personascom
modify edad not null;

alter table personascom
add CONSTRAINT PK_personascom PRIMARY KEY (id);

alter table personascom
add FOREIGN KEY (id) references operadores(id);

//----------------------------------
// viviendasunivs
//----------------------------------


create table viviendasunivs(
id varchar(60),
direccion varchar(60),
rut varchar(60),
habDisponibles integer DEFAULT 0,
habOcupadas integer DEFAULT 0
);

alter table viviendasunivs
modify direccion not null;

alter table viviendasunivs
modify habDisponibles not null;

alter table viviendasunivs
modify rut not null;

alter table viviendasunivs
modify habOcupadas  not null;

alter table viviendasunivs
add CONSTRAINT CHK_ViviendasHabitaciones CHECK ( HabDisponibles >= 0 and habOcupadas >= 0);

alter table viviendasunivs
add CONSTRAINT PK_ViviendaUnivs PRIMARY KEY (id);

alter table viviendasunivs
add FOREIGN KEY (id) references operadores(id);


//----------------------------------
// alojamientos
//----------------------------------

create table alojamientos(
id varchar(60),
capacidad number(2),
tipo varchar(10),
tamanho number(3) 
);

alter table alojamientos
modify capacidad not null;

alter table alojamientos
modify tamanho not null;

alter table alojamientos
add CONSTRAINT CHK_AlojamientoConSentido CHECK ( capacidad >= 0 and tamanho >= 0);

alter table alojamientos
add CONSTRAINT PK_alojamientos PRIMARY KEY (id);

alter table alojamientos
add constraint CHK_tipoalvalido check (tipo in ('habhot','aparta','vivuni','vivcom','habita'));

//----------------------------------
// ofertas
//----------------------------------

create table ofertas(
id varchar(60),
costo number(9,2),
fecharetiro date,
nombre varchar(60),
operador varchar(60),
alojamiento varchar(60)
);

alter table ofertas
modify costo not null;

alter table ofertas
modify operador not null;

alter table ofertas
modify alojamiento not null;

alter table ofertas
add CONSTRAINT PK_ofertas PRIMARY KEY (id);

alter table ofertas
add FOREIGN KEY (operador) references operadores(id);

alter table ofertas
add FOREIGN KEY (alojamiento) references alojamientos(id);

//----------------------------------
// reservas
//----------------------------------

create table reservas(
id varchar(60),
cobro number(9,2) default 0,
fecharealizacion date,
fechainicio date,
fechafin date,
operador varchar(60),
oferta varchar(60),
cliente varchar(60)  
);

alter table reservas
modify cobro not null;

alter table reservas
modify fecharealizacion not null;

alter table reservas
modify fechainicio not null;

alter table reservas
modify fechafin not null;

alter table reservas
modify operador not null;

alter table reservas
modify oferta not null;

alter table reservas
modify cliente not null;

alter table reservas
add CONSTRAINT PK_reservas PRIMARY KEY (id);

alter table reservas
add FOREIGN KEY (operador) references operadores(id);

alter table reservas
add FOREIGN KEY (oferta) references ofertas(id);

alter table reservas
add FOREIGN KEY (cliente) references clientes(id);

alter table reservas
add CONSTRAINT CHK_reservacoherente CHECK ( fechainicio < fechafin and fecharealizacion < fechainicio);

//----------------------------------
// servicios
//----------------------------------

create table servicios(
id varchar(60),
costo number (8,2),
descrpicion varchar(60),
nombre varchar(60),
oferta varchar(60)
);

alter table servicios
modify nombre not null;

alter table servicios
modify oferta not null;

alter table servicios
add CONSTRAINT PK_servicios PRIMARY KEY (id);

alter table servicios
add FOREIGN KEY (oferta) references ofertas(id);


//----------------------------------
// apartamentos
//----------------------------------

create table apartamentos(
id varchar(60),
direccion varchar(60),
menaje number(1),
amoblado number(1) ,
numhabitaciones number(1),
personacom varchar(60)
);

alter table apartamentos
modify direccion not null;


alter table apartamentos
modify menaje not null;


alter table apartamentos
modify amoblado not null;


alter table apartamentos
modify numhabitaciones not null;

alter table apartamentos
modify personacom not null;

alter table apartamentos
add CONSTRAINT CHK_booleanmenajeapto CHECK ( menaje = 1 or menaje = 0);

alter table apartamentos
add CONSTRAINT CHK_booleanamoblaapto CHECK ( menaje = 1 or menaje = 0);

alter table apartamentos
add CONSTRAINT CHK_aptohabitaciones CHECK ( numhabitaciones > 0);

alter table apartamentos
add CONSTRAINT PK_apartamentos PRIMARY KEY (id);

alter table apartamentos
add FOREIGN KEY (id) references alojamientos(id);

alter table apartamentos
add FOREIGN KEY (personacom) references personascom(id);

//----------------------------------
// vdascomunidad
//----------------------------------

create table vdascomunidad(
id varchar(60),
direccion varchar(60),
menaje number(1),
diasuso number(2) default 0 ,
numhabitaciones number(1),
personacom varchar(60)
);

alter table vdascomunidad
modify menaje not null;

alter table vdascomunidad
modify direccion not null;

alter table vdascomunidad
modify diasuso not null;

alter table vdascomunidad
modify numhabitaciones not null;

alter table vdascomunidad
modify personacom not null;

alter table vdascomunidad
add CONSTRAINT CHK_booleanmenajevda CHECK ( menaje = 1 or menaje = 0);

alter table vdascomunidad
add CONSTRAINT CHK_vdahabitaciones CHECK ( numhabitaciones > 0);

alter table vdascomunidad
add CONSTRAINT CHK_vdadiasuso CHECK ( diasuso >= 0 and diasuso <= 30);

alter table vdascomunidad
add CONSTRAINT PK_vdascomunidad PRIMARY KEY (id);

alter table vdascomunidad
add FOREIGN KEY (id) references alojamientos(id);

alter table vdascomunidad
add FOREIGN KEY (personacom) references personascom(id);

//----------------------------------
// habshotel
//----------------------------------

create table habshotel(
id varchar(60),
categoria varchar(20),
ubicacion varchar(30) ,
numero varchar(4),
hotel varchar(60)
);

alter table habshotel
modify ubicacion not null;

alter table habshotel
modify numero not null;

alter table habshotel
modify hotel not null;

alter table habshotel
add CONSTRAINT PK_habshotel PRIMARY KEY (id);

alter table habshotel
add FOREIGN KEY (id) references alojamientos(id);

alter table habshotel
add FOREIGN KEY (hotel) references hoteles(id);


//----------------------------------
// habsuniv
//----------------------------------

create table habsuniv(
id varchar(60),
ubicacion varchar(30),
numero varchar(4),
menaje number(1),
viviendauniv varchar(60)
);

alter table habsuniv
modify ubicacion not null;

alter table habsuniv
modify numero not null;

alter table habsuniv
modify menaje not null;

alter table habsuniv
add CONSTRAINT CHK_booleanmenajeuniv CHECK ( menaje = 1 or menaje = 0);

alter table habsuniv
add CONSTRAINT PK_habsuniv PRIMARY KEY (id);

alter table habsuniv
add FOREIGN KEY (id) references alojamientos(id);

alter table habsuniv
add FOREIGN KEY (viviendauniv) references viviendasunivs(id);


//----------------------------------
// habitaciones
//----------------------------------

create table habitaciones(
id varchar(60),
compartida number(1),
numero varchar(4),
hostal varchar(60),
personanat varchar(60)
);

alter table habitaciones
modify compartida not null;

alter table habitaciones
add CONSTRAINT CHK_habcompartida CHECK ( compartida = 1 or compartida = 0);

alter table habitaciones
add CONSTRAINT CHK_nohaydosduenhos CHECK ((hostal is not null and personanat is null) or (hostal is null and personanat is not null));

alter table habitaciones
add CONSTRAINT PK_habitaciones PRIMARY KEY (id);

alter table habitaciones
add FOREIGN KEY (id) references alojamientos(id);

alter table habitaciones
add FOREIGN KEY (hostal) references hostales(id);

alter table habitaciones
add FOREIGN KEY (personanat) references personasnats(id);


