ALTER TABLE public.clientes RENAME TO usuarios;
ALTER TABLE public.usuarios RENAME COLUMN id_cliente TO id_usuario;
ALTER TABLE public.usuarios ADD senha varchar(50) NOT NULL;
ALTER TABLE public.usuarios ALTER COLUMN email SET NOT NULL;

ALTER TABLE public.pedidos RENAME COLUMN id_cliente TO id_usuario;
ALTER TABLE public.pedidos RENAME CONSTRAINT pedidos_id_cliente_fkey TO pedidos_id_usuario_fkey;