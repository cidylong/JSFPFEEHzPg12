-- Table: public.apply

-- DROP TABLE public.apply;

CREATE TABLE public.apply
(
    apply_id text COLLATE pg_catalog."default" NOT NULL,
    applicant text COLLATE pg_catalog."default",
    target text COLLATE pg_catalog."default",
    comment text COLLATE pg_catalog."default",
    category text COLLATE pg_catalog."default",
    created timestamp without time zone,
    status text COLLATE pg_catalog."default",
    responsible text COLLATE pg_catalog."default",
    processed timestamp without time zone,
    target_owner text COLLATE pg_catalog."default",
    CONSTRAINT application_pkey PRIMARY KEY (apply_id)
)

    TABLESPACE pg_default;

ALTER TABLE public.apply
    OWNER to xxxxxx;