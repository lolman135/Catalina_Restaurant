--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0 (Postgres.app)
-- Dumped by pg_dump version 17.0 (Postgres.app)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    customer_name character varying(255) NOT NULL,
    customer_phone character varying(255) NOT NULL,
    customer_address character varying(255) NOT NULL,
    order_status character varying(255) DEFAULT 'PENDING'::character varying NOT NULL,
    total_price double precision NOT NULL,
    created_at time(6) without time zone NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: canceled_orders; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.canceled_orders AS
 SELECT id,
    customer_name,
    customer_phone,
    customer_address,
    order_status,
    total_price,
    created_at
   FROM public.orders
  WHERE ((order_status)::text = 'CANCELED'::text);


ALTER VIEW public.canceled_orders OWNER TO postgres;

--
-- Name: delivered_orders; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.delivered_orders AS
 SELECT id,
    customer_name,
    customer_phone,
    customer_address,
    order_status,
    total_price,
    created_at
   FROM public.orders
  WHERE ((order_status)::text = 'DELIVERED'::text);


ALTER VIEW public.delivered_orders OWNER TO postgres;

--
-- Name: menu_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.menu_items (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    price double precision NOT NULL,
    image_url character varying(255) NOT NULL,
    category character varying(255)
);


ALTER TABLE public.menu_items OWNER TO postgres;

--
-- Name: menu_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.menu_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.menu_items_id_seq OWNER TO postgres;

--
-- Name: menu_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.menu_items_id_seq OWNED BY public.menu_items.id;


--
-- Name: order_items; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_items (
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    menu_item_id bigint NOT NULL,
    quantity integer NOT NULL
);


ALTER TABLE public.order_items OWNER TO postgres;

--
-- Name: order_items_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_items_id_seq OWNER TO postgres;

--
-- Name: order_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_items_id_seq OWNED BY public.order_items.id;


--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO postgres;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: menu_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_items ALTER COLUMN id SET DEFAULT nextval('public.menu_items_id_seq'::regclass);


--
-- Name: order_items id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items ALTER COLUMN id SET DEFAULT nextval('public.order_items_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Data for Name: menu_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.menu_items (id, name, description, price, image_url, category) FROM stdin;
18	Loaded Nachos	Tortilla chips with melted cheese, jalapeños, guacamole, and sour cream\r\n	65	/media/dishes/ad1b0add-509b-4317-a54c-020f2936df6f.jpg	SNACK
19	Fried Sweet Potato Fries	Crispy fried sweet potato fries served with a dipping sauce	55.2	/media/dishes/22de6c45-0ad8-45e2-a288-b2c6660ddb24.jpg	SNACK
20	Onion Rings	Crispy golden onion rings with a tangy dipping sauce	48.4	/media/dishes/b0f61e84-b294-4524-9ecd-ab170891087b.jpg	SNACK
23	Cheesy Jalapeno Poppers	Deep-fried jalapeños stuffed with melted cheese	61.7	/media/dishes/4e553a4b-db56-413e-acf1-fb6e5b21089a.jpg	SNACK
4	Pizza Peperonni	Simple pizza with cheese, meat and tomato	124.6	/media/dishes/pizza.png	PIZZA
9	Pizza Mexicana	Spicy pizza with chili peppers, jalapeños, cheese, and ground beef	139.5	/media/dishes/325db374-b16c-4c4a-a01a-64942ee591ca.jpg	PIZZA
10	Pizza BBQ Chicken	Grilled chicken, barbecue sauce, onions, and mozzarella	149	/media/dishes/dafc698c-c282-4447-84df-115401e2ae78.jpg	PIZZA
11	Taco Pizza	Fusion pizza with seasoned beef, lettuce, tomato, cheddar, and salsa drizzle	153.4	/media/dishes/e93fb3d1-bd17-4d09-855f-8f2f94bf5776.jpg	PIZZA
3	Classic American Burger	Beef patty, cheddar cheese, lettuce, tomato, and pickles on a toasted bun	99	/media/dishes/burger.png	BURGER
12	Mexican Beef Burger	Spicy beef patty, guacamole, jalapeños, and Mexican cheese	124.9	/media/dishes/76ce24b4-346e-48bf-a06e-c8f4c3733d47.jpg	BURGER
13	BBQ Bacon Burger	Beef patty, crispy bacon, barbecue sauce, onions, and cheddar cheese	134.7	/media/dishes/f2815cff-b1a1-4b27-a081-2a1ff2f9fd74.jpg	BURGER
14	Southwest Chicken Burger	Grilled chicken, chipotle mayo, avocado, and pepper jack cheese	119.2	/media/dishes/cbe0c25c-bf81-405c-a3aa-c68089ab6b08.jpg	BURGER
1	Cesar Salad	Romaine lettuce, grilled chicken, croutons, Caesar dressing, and parmesan	89	/media/dishes/cesar_salad.png	SALAD
15	Mexican Fiesta Salad	Avocado, black beans, corn, tomato, jalapeños, and cilantro with lime dressing\r\n	94.5	/media/dishes/6ed97ef9-22ad-41a0-8aea-9982e2c69c3b.jpg	SALAD
16	BBQ Chicken Salad	Grilled chicken, mixed greens, corn, black beans, and smoky barbecue dressing	102.3	/media/dishes/b5a59ed6-2f24-4c07-9317-ef52ae16fff9.jpg	SALAD
17	Tex-Mex Taco Salad	Crunchy tortilla bowl filled with lettuce, spiced beef, cheese, salsa, and sour cream\r\n	111.6	/media/dishes/f20edd62-60b0-4415-a584-c69856865a40.jpg	SALAD
\.


--
-- Data for Name: order_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_items (id, order_id, menu_item_id, quantity) FROM stdin;
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, customer_name, customer_phone, customer_address, order_status, total_price, created_at) FROM stdin;
\.


--
-- Name: menu_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.menu_items_id_seq', 23, true);


--
-- Name: order_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_items_id_seq', 1, false);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- Name: menu_items menu_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.menu_items
    ADD CONSTRAINT menu_items_pkey PRIMARY KEY (id);


--
-- Name: order_items order_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: order_items order_items_menu_item_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_menu_item_id_fkey FOREIGN KEY (menu_item_id) REFERENCES public.menu_items(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: order_items order_items_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_items
    ADD CONSTRAINT order_items_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

