INSERT INTO usuarios (email, password, first_name, last_name, enabled, created_on, last_login, reset_token, email_verified, provider, provider_id, image_profile_name, image_profile_data) VALUES ('vinsfran2@gmail.com', '$2a$10$pQG5o8CbpWe8g9zqzimGT.m3qmM//WysZX8tN1pnAmuR6HdEoKtte', 'Vicente', 'Insfran', true, '2019-06-10', '2019-06-10', null, true, 'local', '', null, null);

INSERT INTO monedas (codigo, nombre) VALUES ('Gs.', 'Guarani');
INSERT INTO monedas (codigo, nombre) VALUES ('Us.', 'Dolar');
INSERT INTO monedas (codigo, nombre) VALUES ('Ps.', 'Peso Argentino');

INSERT INTO tipos_conceptos (nombre, signo) VALUES ('Ingreso', '+');
INSERT INTO tipos_conceptos (nombre, signo) VALUES ('Egreso', '-');

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

INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Luz', 2, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Agua', 2, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Escuela', 2, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Sueldo', 1, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Pago de Prestamo', 2, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Pago de Ahorro', 2, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Cobro de Ahorro', 1, 1);
INSERT INTO conceptos (nombre, tipo_concepto_id, usuario_id) VALUES ('Pago de Tarjeta', 2, 1);

INSERT INTO prestamos (cantidad_cuotas, cantidad_cuotas_pagadas, destino_prestamo, estado, fecha_desembolso, fecha_vencimiento, interes, monto_cuota, monto_pagado, monto_prestamo, tasa, entidad_financiera_id, moneda_id, usuario_id) VALUES (12,0,'Construccion',true,'2019-06-10','2019-06-28',1,50000,0,500000,2,1,1,1);
INSERT INTO prestamos (cantidad_cuotas, cantidad_cuotas_pagadas, destino_prestamo, estado, fecha_desembolso, fecha_vencimiento, interes, monto_cuota, monto_pagado, monto_prestamo, tasa, entidad_financiera_id, moneda_id, usuario_id) VALUES (12,0,'Construccion',true,'2019-06-10','2019-06-28',1,50000,0,500000,2,2,2,1);

INSERT INTO ahorros (cantidad_cobro, estado, cantidad_cuotas, fecha_inicio, fecha_vencimiento, interes, monto_capital, monto_cuota, monto_interes_cuota, monto_pagado, monto_ultimo_pago, plazo_total, tasa, entidades_financieras_id, monedas_id, tipos_ahorros_id, tipos_cobros_id, usuario_id, cantidad_cuotas_pagadas) VALUES (0,'true',12,'2019-06-12','2019-06-13',2,500000,50000,0,0,0,12,1,1,1,1,1,1,0);
INSERT INTO ahorros (cantidad_cobro, estado, cantidad_cuotas, fecha_inicio, fecha_vencimiento, interes, monto_capital, monto_cuota, monto_interes_cuota, monto_pagado, monto_ultimo_pago, plazo_total, tasa, entidades_financieras_id, monedas_id, tipos_ahorros_id, tipos_cobros_id, usuario_id, cantidad_cuotas_pagadas) VALUES (0,'true',12,'2019-06-12','2019-06-13',2,100000000,100000,0,0,0,12,1,3,1,1,1,1,0);

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
