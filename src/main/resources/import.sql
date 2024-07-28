/* Populate tables */

/* Creamos algunos usuarios con sus roles */

INSERT INTO public.users (username, password, enabled,email,created_at) VALUES ('daniel','$2a$10$3.9Jr9qv3gHSgxUs4fiS..7M9FWF2m10FauGsJqEUl/vJcf/VQMcK',true,'slaydep@gmail.com',now());

INSERT INTO public.authorities (authority) VALUES ('ROLE_ADMIN');
INSERT INTO public.authorities (authority) VALUES ('ROLE_USER');

INSERT INTO public.usuarios_roles (fk_usuario,fk_role) VALUES (1,1);
INSERT INTO public.usuarios_roles (fk_usuario,fk_role) VALUES (1,2);


INSERT INTO public.maderas (create_at,nombre,precio) VALUES (now(),'0mm',0);
INSERT INTO public.maderas (create_at,nombre,precio) VALUES (now(),'mdf 2,5mm',1.44);
INSERT INTO public.maderas (create_at,nombre,precio) VALUES (now(),'mdf 4mm',1.74);
INSERT INTO public.maderas (create_at,nombre,precio) VALUES (now(),'mdf 5,5mm',2.08);
INSERT INTO public.maderas (create_at,nombre,precio) VALUES (now(),'mdf 9mm',2.28);

INSERT INTO public.maquinas (create_at,nombre,precio) VALUES (now(),'laser 1',600);
INSERT INTO public.maquinas (create_at,nombre,precio) VALUES (now(),'sin maquina',0);

INSERT INTO public.calculos (create_at,descripcion,factor,horas,lado1,lado2,minutos,precio_fabrica,precio_venta,segundos,updated_at,fk_madera,fk_maquina) VALUES ('2023-05-22','Calculo caja mini',2,0,66,66,5,9272,18545,0,NULL,1,1);
INSERT INTO public.calculos (create_at,descripcion,factor,horas,lado1,lado2,minutos,precio_fabrica,precio_venta,segundos,updated_at,fk_madera,fk_maquina) VALUES ('2023-05-22','Calculo caja 3',2,0,12,12,10,6507,13014,30,NULL,1,1);
