--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5
-- Dumped by pg_dump version 10.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: copierchamplieu(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.copierchamplieu() RETURNS void
    LANGUAGE plpgsql
    AS $$

DECLARE 
	lieuCourant RECORD;
	totalLieu integer;
	moyenneHabitant integer;
	moyenneTaille double precision;
	checksum text;
BEGIN	
	checksum:='';
	FOR lieuCourant IN SELECT * from lieu
	LOOP
		checksum:= checksum || lieuCourant.id || ', ';
		checksum:= checksum || lieuCourant.ville || ', ';
		checksum:= checksum || lieuCourant.taille || ', ';
		checksum:= checksum || lieuCourant.habitant || ', ';
		checksum:= checksum || lieuCourant.estcapitale || ', ';
	END LOOP; 
	
	select count(*) INTO totalLieu from lieu;
	select AVG(habitant) INTO moyenneHabitant from lieu;
	select AVG(taille) INTO moyenneTaille from lieu;
	INSERT INTO statlieu (date, nombre_lieu, moyene_habitant, moyene_taille, checksum) VALUES ( NOW(), totalLieu ,moyenneHabitant, moyenneTaille, MD5(checksum));

END 
$$;


ALTER FUNCTION public.copierchamplieu() OWNER TO postgres;

--
-- Name: copierchamppoisson(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.copierchamppoisson() RETURNS void
    LANGUAGE plpgsql
    AS $$

DECLARE 
	poissonCourant RECORD;
	totalPoisson integer;
	moyenneTaille double precision;
	moyennePoids double precision;
	checksum text;
BEGIN	
	checksum:='';
	FOR poissonCourant IN SELECT * from poisson
	LOOP
		checksum:= checksum || poissonCourant.id || ', ';
		checksum:= checksum || poissonCourant.nom || ', ';
		checksum:= checksum || poissonCourant.famille || ', ';
		checksum:= checksum || poissonCourant.taille || ', ';
		checksum:= checksum || poissonCourant.poids || ', ';
		checksum:= checksum || poissonCourant.id_lieu || ', ';
	END LOOP; 
	
	select count(*) INTO totalPoisson from poisson;
	select AVG(taille) INTO moyenneTaille from poisson;
	select AVG(poids) INTO moyennePoids from poisson;
	INSERT INTO statpoisson (date, nombre_poisson, moyene_taille, moyene_poids, checksum) VALUES ( NOW(), totalPoisson ,moyenneTaille, moyennePoids, MD5(checksum));

END 

$$;


ALTER FUNCTION public.copierchamppoisson() OWNER TO postgres;

--
-- Name: journaliser(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.journaliser() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

DECLARE
	avant_operation text;
	apres_operation text;
	operation text;
BEGIN

	IF TG_OP = 'UPDATE' THEN
		avant_operation:= '[ Ville : ' || OLD.ville || ' ]' || '[ Taille : ' || OLD.taille || ' ]' || '[ Habitants : ' || OLD.habitant || ' ]' || '[ EstCapitale : ' || OLD.estcapitale || ' ]';
		apres_operation:= '[ Ville : ' || NEW.ville || ' ]' || '[ Taille : ' || NEW.taille || ' ]' || '[ Habitants : ' || NEW.habitant || ' ]' || '[ EstCapitale : ' || NEW.estcapitale || ' ]';
		operation:= 'UPDATE';
	END IF;
	
	IF TG_OP = 'INSERT' THEN
		apres_operation:= '[ Ville : ' || NEW.ville || ' ]' || '[ Taille : ' || NEW.taille || ' ]' || '[ Habitants : ' || NEW.habitant || ' ]' || '[ EstCapitale : ' || NEW.estcapitale || ' ]';
		operation:= 'INSERT';
	END IF;
	
	IF TG_OP = 'DELETE' THEN
		avant_operation:= '[ Ville : ' || OLD.ville || ' ]' || '[ Taille : ' || OLD.taille || ' ]' || '[ Habitants : ' || OLD.habitant || ' ]' || '[ EstCapitale : ' || OLD.estcapitale || ' ]';
		operation:= 'DELETE';
	END IF;
	
	INSERT into journal(date, operation,objet,avant_operation,apres_operation) VALUES(NOW(), operation, 'lieu' ,avant_operation,apres_operation);
	
	IF TG_OP = 'DELETE' THEN
		return old;
	end if;
	
	RETURN new;
END

$$;


ALTER FUNCTION public.journaliser() OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: journal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.journal (
    id integer NOT NULL,
    date timestamp with time zone NOT NULL,
    operation text NOT NULL,
    avant_operation text,
    apres_operation text,
    objet text NOT NULL
);


ALTER TABLE public.journal OWNER TO postgres;

--
-- Name: journal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.journal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.journal_id_seq OWNER TO postgres;

--
-- Name: journal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.journal_id_seq OWNED BY public.journal.id;


--
-- Name: lieu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lieu (
    id integer NOT NULL,
    ville text,
    taille integer,
    habitant integer,
    estcapitale text
);


ALTER TABLE public.lieu OWNER TO postgres;

--
-- Name: lieu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lieu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lieu_id_seq OWNER TO postgres;

--
-- Name: lieu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lieu_id_seq OWNED BY public.lieu.id;


--
-- Name: poisson; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.poisson (
    id integer NOT NULL,
    nom text,
    famille text,
    taille integer,
    poids integer,
    id_lieu integer
);


ALTER TABLE public.poisson OWNER TO postgres;

--
-- Name: poisson_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.poisson_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.poisson_id_seq OWNER TO postgres;

--
-- Name: poisson_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.poisson_id_seq OWNED BY public.poisson.id;


--
-- Name: statlieu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.statlieu (
    id bigint NOT NULL,
    date timestamp with time zone NOT NULL,
    nombre_lieu integer,
    moyene_habitant double precision,
    moyene_taille double precision,
    checksum text
);


ALTER TABLE public.statlieu OWNER TO postgres;

--
-- Name: statLieu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."statLieu_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."statLieu_id_seq" OWNER TO postgres;

--
-- Name: statLieu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."statLieu_id_seq" OWNED BY public.statlieu.id;


--
-- Name: statpoisson; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.statpoisson (
    id integer NOT NULL,
    date timestamp with time zone NOT NULL,
    checksum text,
    moyene_taille double precision,
    moyene_poids text,
    nombre_poisson integer
);


ALTER TABLE public.statpoisson OWNER TO postgres;

--
-- Name: statPoisson_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."statPoisson_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."statPoisson_id_seq" OWNER TO postgres;

--
-- Name: statPoisson_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."statPoisson_id_seq" OWNED BY public.statpoisson.id;


--
-- Name: journal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.journal ALTER COLUMN id SET DEFAULT nextval('public.journal_id_seq'::regclass);


--
-- Name: lieu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lieu ALTER COLUMN id SET DEFAULT nextval('public.lieu_id_seq'::regclass);


--
-- Name: poisson id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poisson ALTER COLUMN id SET DEFAULT nextval('public.poisson_id_seq'::regclass);


--
-- Name: statlieu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statlieu ALTER COLUMN id SET DEFAULT nextval('public."statLieu_id_seq"'::regclass);


--
-- Name: statpoisson id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statpoisson ALTER COLUMN id SET DEFAULT nextval('public."statPoisson_id_seq"'::regclass);


--
-- Data for Name: journal; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.journal (id, date, operation, avant_operation, apres_operation, objet) FROM stdin;
38	2018-09-20 19:30:43.395288-04	UPDATE	[ Ville : Otawa ][ Taille : 12432 ][ Habitants : 123345 ][ EstCapitale : non ]	[oh ta wahh]	lieu
39	2018-09-20 19:34:07.348468-04	UPDATE	[ Ville : oh ta wahh ][ Taille : 12432 ][ Habitants : 123345 ][ EstCapitale : non ]	[ Ville : Ottawa ][ Taille : 2 ][ Habitants : 1 ][ EstCapitale : non ]	lieu
40	2018-09-20 19:34:24.345947-04	DELETE	[ Ville : Ottawa ][ Taille : 2 ][ Habitants : 1 ][ EstCapitale : non ]	\N	lieu
41	2018-09-20 19:34:37.223331-04	INSERT	\N	[ Ville : Ottawa ][ Taille : 123 ][ Habitants : 456 ][ EstCapitale : oui ]	lieu
42	2018-09-20 20:42:11.153905-04	DELETE	[ Ville : salut ][ Taille : 1414 ][ Habitants : 314 ][ EstCapitale : non ]	\N	lieu
43	2018-09-20 20:45:41.080491-04	UPDATE	[ Ville : Matano ][ Taille : 228 ][ Habitants : 143420000 ][ EstCapitale : non ]	[ Ville : Matane ][ Taille : 228 ][ Habitants : 143420000 ][ EstCapitale : non ]	lieu
44	2018-09-20 20:45:57.330142-04	INSERT	\N	[ Ville : Saint-Ulrich ][ Taille : 1234 ][ Habitants : 123 ][ EstCapitale : non ]	lieu
45	2018-09-20 21:05:14.60793-04	UPDATE	[ Ville : hagenau ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : oui ]	[ Ville : hagenau ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : non ]	lieu
46	2018-09-20 21:05:16.44287-04	DELETE	[ Ville : hagenau ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : oui ]	\N	lieu
47	2018-09-26 13:54:45.527684-04	UPDATE	[ Ville : sertyu ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : non ]	[ Ville : sertyu ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : non ]	lieu
48	2018-09-26 13:54:48.712493-04	UPDATE	[ Ville : Matane ][ Taille : 228 ][ Habitants : 143420000 ][ EstCapitale : non ]	[ Ville : Matane ][ Taille : 228 ][ Habitants : 143420000 ][ EstCapitale : non ]	lieu
49	2018-09-26 13:55:04.750157-04	UPDATE	[ Ville : hagenau ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : non ]	[ Ville : hagenau ][ Taille : 123 ][ Habitants : 12 ][ EstCapitale : non ]	lieu
50	2018-09-28 17:06:05.184311-04	UPDATE	[ Ville : Matane ][ Taille : 228 ][ Habitants : 143420000 ][ EstCapitale : non ]	[ Ville : Matane ][ Taille : 228 ][ Habitants : 14342 ][ EstCapitale : non ]	lieu
\.


--
-- Data for Name: lieu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lieu (id, ville, taille, habitant, estcapitale) FROM stdin;
29	Ottawa	123	456	oui
30	Saint-Ulrich	1234	123	non
14	sertyu	123	12	non
15	hagenau	123	12	non
2	Matane	228	14342	non
\.


--
-- Data for Name: poisson; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.poisson (id, nom, famille, taille, poids, id_lieu) FROM stdin;
3	brochet	Esox Lucuis	86	4521	2
5	silure	siluridé	156	6542	2
20	aze	aze	123	123	15
\.


--
-- Data for Name: statlieu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.statlieu (id, date, nombre_lieu, moyene_habitant, moyene_taille, checksum) FROM stdin;
1	2018-09-26 22:13:02.983209-04	5	28684121	366.19999999999999	
2	2018-09-26 22:35:35.259133-04	5	28684121	366.19999999999999	Ottawa-Saint-Ulrich-sertyu-Matane-hagenau
3	2018-09-26 22:37:32.505483-04	5	28684121	366.19999999999999	fdf85bdf9acdb11f145fea4ab0d1b18b
4	2018-09-28 17:03:16.857532-04	5	28684121	366.19999999999999	4627db04f7b91f7faac3832cbff9c9f6
5	2018-09-28 17:03:48.453046-04	5	28684121	366.19999999999999	4627db04f7b91f7faac3832cbff9c9f6
6	2018-09-28 17:06:09.432903-04	5	2989	366.19999999999999	298192107045b52b1c3924401acded1e
7	2018-09-28 17:06:14.699252-04	5	2989	366.19999999999999	298192107045b52b1c3924401acded1e
\.


--
-- Data for Name: statpoisson; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.statpoisson (id, date, checksum, moyene_taille, moyene_poids, nombre_poisson) FROM stdin;
1	2018-09-26 22:51:18.133171-04	bebd990ddb07035f226d64f5d8794f59	121.66666666666667	3728.66666666667	3
\.


--
-- Name: journal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.journal_id_seq', 50, true);


--
-- Name: lieu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lieu_id_seq', 30, true);


--
-- Name: poisson_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.poisson_id_seq', 20, true);


--
-- Name: statLieu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."statLieu_id_seq"', 7, true);


--
-- Name: statPoisson_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."statPoisson_id_seq"', 1, true);


--
-- Name: journal journal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_pkey PRIMARY KEY (id);


--
-- Name: lieu lieu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lieu
    ADD CONSTRAINT lieu_pkey PRIMARY KEY (id);


--
-- Name: poisson poisson_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poisson
    ADD CONSTRAINT poisson_pkey PRIMARY KEY (id);


--
-- Name: statlieu statLieu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statlieu
    ADD CONSTRAINT "statLieu_pkey" PRIMARY KEY (id);


--
-- Name: statpoisson statPoisson_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statpoisson
    ADD CONSTRAINT "statPoisson_pkey" PRIMARY KEY (id);


--
-- Name: fki_fk_id_lieu; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_fk_id_lieu ON public.poisson USING btree (id_lieu);


--
-- Name: lieu evenementlieu; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER evenementlieu BEFORE INSERT OR DELETE OR UPDATE ON public.lieu FOR EACH ROW EXECUTE PROCEDURE public.journaliser();


--
-- Name: poisson fk_id_lieu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.poisson
    ADD CONSTRAINT fk_id_lieu FOREIGN KEY (id_lieu) REFERENCES public.lieu(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

