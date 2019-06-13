CREATE TABLE IF NOT EXISTS public.user (
	id BIGSERIAL PRIMARY KEY,
	name TEXT NOT NULL,
	email TEXT NOT NULL,
	password TEXT NOT NULL,
	created_at timestamptz default current_timestamp,
	updated_at timestamptz default current_timestamp,
    CONSTRAINT email_uk UNIQUE(email)
);