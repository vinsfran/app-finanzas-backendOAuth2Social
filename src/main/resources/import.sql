INSERT INTO usuarios (email, password, first_name, last_name, enabled, created_on, last_login, reset_token, email_verified, provider, provider_id) VALUES ('vinsfran2@gmail.com', '$2a$10$pQG5o8CbpWe8g9zqzimGT.m3qmM//WysZX8tN1pnAmuR6HdEoKtte', 'Vicente', 'Insfran', true, '2019-06-10', '2019-06-10', null, true, 'local', '');

INSERT INTO monedas (codigo, nombre) VALUES ('Gs.', 'Guarani');
INSERT INTO monedas (codigo, nombre) VALUES ('Us.', 'Dolar');
INSERT INTO monedas (codigo, nombre) VALUES ('Ps.', 'Peso Argentino');

INSERT INTO tipos_ahorros (nombre, usuario_id) VALUES ('CDA', 1);
INSERT INTO tipos_ahorros (nombre, usuario_id) VALUES ('BONOS', 1);
INSERT INTO tipos_ahorros (nombre, usuario_id) VALUES ('Acciones', 1);
INSERT INTO tipos_ahorros (nombre, usuario_id) VALUES ('Programado', 1);

INSERT INTO tipos_cobros (nombre, usuario_id) VALUES ('Mensual', 1);
INSERT INTO tipos_cobros (nombre, usuario_id) VALUES ('Trimestral', 1);
INSERT INTO tipos_cobros (nombre, usuario_id) VALUES ('Anual', 1);

INSERT INTO tipos_pagos (nombre, usuario_id) VALUES ('Efectivo', 1);
INSERT INTO tipos_pagos (nombre, usuario_id) VALUES ('Tarjeta', 1);
INSERT INTO tipos_pagos (nombre, usuario_id) VALUES ('Cheque', 1);

INSERT INTO entidades_financieras (nombre, usuario_id) VALUES ('BANCO ITAU', 1);
INSERT INTO entidades_financieras (nombre, usuario_id) VALUES ('BANCO BBVA', 1);
INSERT INTO entidades_financieras (nombre, usuario_id) VALUES ('BANCO CONTINENTAL', 1);
INSERT INTO entidades_financieras (nombre, usuario_id) VALUES ('FINANCIERA EL COMERCIO', 1);

INSERT INTO conceptos (nombre, tipo_concepto, monedas_id, usuario_id) VALUES ('Luz', 'egreso', 1, 1);
INSERT INTO conceptos (nombre, tipo_concepto, monedas_id, usuario_id) VALUES ('Agua', 'egreso', 1, 1);
INSERT INTO conceptos (nombre, tipo_concepto, monedas_id, usuario_id) VALUES ('Escuela', 'egreso', 1, 1);
INSERT INTO conceptos (nombre, tipo_concepto, monedas_id, usuario_id) VALUES ('Sueldo', 'ingreso', 1, 1);

-- INSERT INTO prestamos (cantidad_cuotas, cantidad_cuotas_pagadas, destino_prestamo, estado, fecha_desembolso, fecha_vencimiento, interes, monto_cuota, monto_pagado, monto_prestamo, tasa, entidad_financiera_id, moneda_id, usuario_id) VALUES (12,0,'Construccion',true,'2019-06-10','2019-06-28',1,50000,0,500000,2,1,1,1);
-- INSERT INTO prestamos (cantidad_cuotas, cantidad_cuotas_pagadas, destino_prestamo, estado, fecha_desembolso, fecha_vencimiento, interes, monto_cuota, monto_pagado, monto_prestamo, tasa, entidad_financiera_id, moneda_id, usuario_id) VALUES (12,0,'Construccion',true,'2019-06-10','2019-06-28',1,50000,0,500000,2,2,2,1);

INSERT INTO ahorros (cantidad_cobro, estado, cantidad_cuotas_cobradas, fecha_inicio, fecha_vencimiento, interes, monto_capital, monto_cuota, monto_interes_cuota, monto_pagado, monto_ultimo_pago, plazo_total, tasa, entidades_financieras_id, monedas_id, tipos_ahorros_id, tipos_cobros_id, usuario_id, cantidad_cuotas_pagadas) VALUES (0,'true',0,'2019-06-12','2019-06-13',2,500000,50000,0,0,0,12,1,1,1,1,1,1,0);
INSERT INTO ahorros (cantidad_cobro, estado, cantidad_cuotas_cobradas, fecha_inicio, fecha_vencimiento, interes, monto_capital, monto_cuota, monto_interes_cuota, monto_pagado, monto_ultimo_pago, plazo_total, tasa, entidades_financieras_id, monedas_id, tipos_ahorros_id, tipos_cobros_id, usuario_id, cantidad_cuotas_pagadas) VALUES (0,'true',0,'2019-06-12','2019-06-13',2,100000000,100000,0,0,0,12,1,3,1,1,1,1,0);

INSERT INTO meses (nombre, numero) VALUES ('Enero', 1);
INSERT INTO meses (nombre, numero) VALUES ('Febrero', 2);
INSERT INTO meses (nombre, numero) VALUES ('Marzo', 3);
INSERT INTO meses (nombre, numero) VALUES ('Abril', 4);
INSERT INTO meses (nombre, numero) VALUES ('Mayo', 5);
INSERT INTO meses (nombre, numero) VALUES ('Junio', 6);
INSERT INTO meses (nombre, numero) VALUES ('Julio', 7);
INSERT INTO meses (nombre, numero) VALUES ('Agosto', 8);
INSERT INTO meses (nombre, numero) VALUES ('Septiembre', 9);
INSERT INTO meses (nombre, numero) VALUES ('Octubre', 10);
INSERT INTO meses (nombre, numero) VALUES ('Noviembre', 11);
INSERT INTO meses (nombre, numero) VALUES ('Diciembre', 12);

INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SUEL', 'ingreso', 'conceptos_default', 'Sueldos');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('ALQU', 'egreso', 'conceptos_default', 'Alquileres');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('BEBI', 'egreso', 'conceptos_default', 'Bebidas');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('COMI', 'egreso', 'conceptos_default', 'Compras');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('DONA', 'egreso', 'conceptos_default', 'Donaciones');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('EDUC', 'egreso', 'conceptos_default', 'Educación');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('ENTR', 'egreso', 'conceptos_default', 'Entretenimiento');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('HOGA', 'egreso', 'conceptos_default', 'Hogar');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('MASC', 'egreso', 'conceptos_default', 'Mascotas');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('NINO', 'egreso', 'conceptos_default', 'Niños');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('REGA', 'egreso', 'conceptos_default', 'Regalos');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('ROPA', 'egreso', 'conceptos_default', 'Ropa');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SALU', 'egreso', 'conceptos_default', 'Salud');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SEGU', 'egreso', 'conceptos_default', 'Seguros');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SERV', 'egreso', 'conceptos_default', 'Servicios');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SUPE', 'egreso', 'conceptos_default', 'Supermercados');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('TECA', 'egreso', 'conceptos_default', 'Television Cable');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('TRAN', 'egreso', 'conceptos_default', 'Transporte');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('VEHI', 'egreso', 'conceptos_default', 'Vehiculo');

INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('MENS', 'mensual', 'tipos_cobros_default', 'Mensual');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('TRIM', 'trimestral', 'tipos_cobros_default', 'Trimestral');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('SEME', 'semestral', 'tipos_cobros_default', 'Semestral');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('ANUA', 'anual', 'tipos_cobros_default', 'Anual');

INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('BAPR', 'banco_prueba', 'entidades_financieras_default', 'Banco Prueba');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('COPR', 'cooperativa_prueba', 'entidades_financieras_default', 'Cooperativa Prueba');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('CAPR', 'casa_credito_prueba', 'entidades_financieras_default', 'Casa de Credito Prueba');

INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('CCDA', 'certificado_deposito_cda', 'tipos_ahorros_default', 'Certificado de Deposito (CDA)');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('BONO', 'bonos', 'tipos_ahorros_default', 'Bonos');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('ACCI', 'acciones', 'tipos_ahorros_default', 'Acciones');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('DEPF', 'deposito_plazo_fijo', 'tipos_ahorros_default', 'Deposito a Plazo Fijo');

INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('EFEC', 'efectivo', 'tipos_pagos_default', 'Efectivo');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('TADE', 'tarjeta_debito', 'tipos_pagos_default', 'Tarjeta de Debito');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('TACR', 'tarjeta_credito', 'tipos_pagos_default', 'Tarjeta de Credito');
INSERT INTO parametros (codigo, valor, grupo, descripcion) VALUES ('DECU', 'deposito_cuenta', 'tipos_pagos_default', 'Deposito en cuenta');



