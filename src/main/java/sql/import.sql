INSERT INTO `cliente` (`ID`, `FECHA_DE_ALTA`, `DIRECCION`, `EMAIL`, `ESTADO`, `NOMBRE`, `RUC`, `TELEFONO`) VALUES
(null, '2023-09-19', 'Calle Las Nueraz N 7, San Martin de Porres', 'ConstructuraValdez@gmail.com', 'Activo', 'Constructura Valdez S.A.C', '232132132', 967239934),
(null, '2023-09-20', 'Av. Los Pinos 123, Miraflores', 'InmobiliariaGarcia@hotmail.com', 'Activo', 'Inmobiliaria García', '543215432', 945678901),
(null, '2023-09-21', 'Jr. Las Flores 456, Surco', 'ProyectosIntegrales@gmail.com', 'Desactivado', 'Proyectos Integrales S.A.C', '99887766', 987654321),
(null, '2023-09-22', 'Calle Los Robles 789, San Isidro', 'ArquitecturaCreativa@gmail.com', 'Desactivo', 'Arquitectura Creativa E.I.R.L', '3322114455', 910111213),
(null, '2023-09-23', 'Av. Las Palmeras 101, San Borja', 'ObrasExpress@yahoo.com', 'Activo', 'Obras Express S.A.', '777888999', 922334455),
(null, '2023-09-24', 'Av. Los Laureles 234, La Molina', 'IngenieriaVision@gmail.com', 'Activo', 'Ingeniería Visión E.I.R.L', '6677889900', 913223344),
(null, '2023-09-25', 'Calle Las Azaleas 567, San Miguel', 'ConstructoraProgreso@hotmail.com', 'Activo', 'Constructora Progreso S.A.C', '555666777', 966551234),
(null, '2023-09-26', 'Jr. Las Orquídeas 890, Callao', 'DesarrollosUrbanos@gmail.com', 'Activo', 'Desarrollos Urbanos E.I.R.L', '123987654', 998877665),
(null, '2023-09-27', 'Av. Los Cedros 432, Lince', 'ArquitecturaModernista@yahoo.com', 'Activo', 'Arquitectura Modernista S.A.C', '1122334455', 987612345),
(null, '2023-09-28', 'Calle Los Pinos 765, Pueblo Libre', 'PlanificacionTotal@gmail.com', 'Activo', 'Planificación Total E.I.R.L', '876543211', 955667788),
(null, '2023-10-07', 'Calle Jose Galvez nro 12', 'FierrosAndinos@gmail.com', 'Activo', 'Fierros Andinos S.A.C', '34524586', 956234000),
(null, '2023-10-07', 'Calle Los Cruzeros nro 3', 'PinturasCorrales@gmail.com', 'Activo', 'Pinturas Corrales S.A.C', '97546341256', 976878787);


INSERT INTO `proveedor` (`ID`, `FECHA_DE_ALTA`, `ESTADO`, `NOMBRE`, `RUC`, `TELEFONO`) VALUES
(null, '2023-09-27', 'Desactivado', 'Ferreteria San Martin', 23123213, 956234654),
(null, '2023-09-27', 'Activo', 'Pinturas y Más', 12345678, 987654321),
(null, '2023-09-27', 'Desactivado', 'Lijas Express', 87654321, 345245678),
(null, '2023-09-27', 'Desactivado', 'Brochas Rápidas', 56789012, 654321789),
(null, '2023-09-27', 'Activo', 'Rodillos Premium', 98761234, 123456789),
(null, '2023-09-27', 'Activo', 'Thinner Supplies', 43219876, 345678901),
(null, '2023-09-27', 'Activo', 'Barnices de Calidad', 56784321, 678901234),
(null, '2023-09-27', 'Desactivado', 'Papeles Kraft', 23415678, 456789012),
(null, '2023-09-27', 'Activo', 'Ferreteria Juanito', 54321234, 567890123),
(null, '2023-09-27', 'Activo', 'Pinturas Express', 87654321, 234567890),
(null, '2023-09-27', 'Activo', 'Lijas y Brochas S.A.', 12345678, 789012345),
(null, '2023-09-27', 'Activo', 'Rodillos de Calidad', 98761234, 456789012),
(null, '2023-09-27', 'Activo', 'Thinner Rápido', 43219876, 123456789),
(null, '2023-09-27', 'Activo', 'Barniz de Lujo', 56784321, 890123456),
(null, '2023-09-27', 'Activo', 'Kraft Supplies', 23415678, 234567890),
(null, '2023-09-27', 'Activo', 'Ferreteria Express', 54321234, 345678901),
(null, '2023-09-27', 'Activo', 'Pinturas de Alta Calidad', 87654321, 567890123),
(null, '2023-09-27', 'Activo', 'Lijas de Oro', 12345678, 456789012),
(null, '2023-09-27', 'Activo', 'Rodillo Rápido', 98761234, 678901234),
(null, '2023-09-27', 'Activo', 'Thinner de Confianza', 43219876, 789012345);

INSERT INTO `usuario` (`ID`, `CONTRASENA`, `FECHA_DE_ALTA`, `ESTADO`, `IDINGRESO`, `NOMBRE`, `TIPO`) VALUES
(null, 'maria', '2023-09-12', 'Activo', 'GonzalezMaria', 'Maria Gonzalez', 'Bodeguero'),
(null, 'juan', '2023-09-13', 'Activo', 'juanPerez', 'Juan Perez', 'Personal de Inventario'),
(null, 'ana', '2023-09-14', 'Desactivado', 'anaLopez', 'Ana Lopez', 'Bodeguero'),
(null, 'admin', '2023-09-14', 'Activado', 'admin', 'Bruno', 'Administrador'),
(null, 'carlos', '2023-09-15', 'Activo', 'carlosMartinez', 'Carlos Martinez', 'Bodeguero');

INSERT INTO `producto` (`ID`, `CANTIDAD`, `FECHA_DE_ALTA`, `DESCRIPCION`, `ESTADO`, `IMAGEN`, `NOMBRE`, `PRECIO`, `ID_PRODUCTO_ORDEN`, `ID_PRODUCTO_PROVEEDOR`) VALUES
(null, 100, '2023-09-27', 'Papel para construccion', 'Activo', 'papelKraft.jpeg', 'Papel Kraft', 5, NULL, 15),
(null, 25, '2023-10-06', 'pintura gloss negro', 'Activo', 'pinturaGlossNegro.jpg', 'Pintura gloss negro', 20, NULL, 2),
(null, 25, '2023-10-06', 'Thinner de 1l ayuda a remover manchas\r\n', 'Desactivo', 'Tinner1L.jpeg', 'Thinner 1Litro', 15, NULL, 6),
(null, 24, '2023-10-06', 'rodillo para mano\r\n', 'Activo', 'rodilloPequeno.jpg', 'Rodillo', 10, NULL, 12),
(null, 23, '2023-10-07', 'Galon de tinner que contiene 5 l\r\n', 'Activo', 'GalonTinner.jpg', 'Galon Tinner 5L', 20, NULL, 7);