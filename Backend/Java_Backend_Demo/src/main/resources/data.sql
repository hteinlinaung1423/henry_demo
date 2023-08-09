START TRANSACTION;

--
-- Database: `henry_demo`
--

--
-- Dumping data for table `roles`
--

INSERT INTO public.roles (name) VALUES
('SUPER_ADMIN'),
('NORMAL_ADMIN'),
('USER');

COMMIT;