ALTER TABLE public.usuarios ALTER COLUMN senha TYPE text USING senha::text;
