CREATE TABLE IF NOT EXISTS public.login_attempt (
	id BIGSERIAL PRIMARY KEY,
	success boolean NOT NULL,
	login_email TEXT NOT NULL,
	user_id BIGINT,
	failure_reason TEXT,
	created_at timestamptz default current_timestamp,
	updated_at timestamptz default current_timestamp,
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);