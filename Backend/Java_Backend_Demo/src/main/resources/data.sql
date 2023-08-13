START TRANSACTION;

--
-- Database: `henry_demo`
--

--
-- Dumping data for table `roles`
--

INSERT INTO public.roles (name) VALUES
('ROLE_SUPER_ADMIN'),
('ROLE_NORMAL_ADMIN'),
('ROLE_USER');

--
-- Dumping data for table `users`
--

INSERT INTO public.users (user_name,email,password,role_id) VALUES
('Superadmin','superadmin@test.com','$2a$10$Ijdek/psC9xVVMA.QiHW..pyB/JI0MZMNay/ZwG4TMcK22SSrFgcK',1),
('Normaladmin','normaladmin@test.com','$2a$10$O7d3vFLdQSY1lHBZWvL0h.A43ojU8FmfCvNcl0EU5mzNQ7D0lu0B2',2);

--
-- Dumping data for table `todo`
--

INSERT INTO public.todo (name,description,created_date,updated_date,is_completed,user_id) VALUES
('Hello','Hello World','2023-08-12T14:24:11.931578Z','2023-08-12T14:24:11.931578Z',true,1),
('Hello 2','Hello World 2','2023-08-12T14:24:11.931578Z','2023-08-12T14:24:11.931578Z',true,1),
('Hello 3','Hello World 3','2023-08-12T14:24:11.931578Z','2023-08-12T14:24:11.931578Z',false,2),
('Hello 4','Hello World 4','2023-08-12T14:24:11.931578Z','2023-08-12T14:24:11.931578Z',false,2),
('Hello 5','Hello World 5','2023-08-12T14:24:11.931578Z','2023-08-12T14:24:11.931578Z',true,1);
COMMIT;