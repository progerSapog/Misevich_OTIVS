insert into states
values ('bfa7eade-423b-400c-8070-56031c762f36', 'Начально событие'),
       ('922974a3-7df1-4ab2-869e-694db4093681', 'Промежуточное событие'),
       ('151ae006-85a9-43cf-8de9-e1197a4057e3', 'Конечное событие');

insert into events
values ('81c07256-02be-44c1-8113-159c852d8c0b', 'Компьютер включен в розетку?', 'bfa7eade-423b-400c-8070-56031c762f36'),
       ('9f9a349b-5ca4-44de-82b5-8093a744bfc0', 'Требуется включить компьютер в розетку.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('6a042790-e366-45b0-af3f-54dd4e583b4f', 'Розетка подключена к сети?', '922974a3-7df1-4ab2-869e-694db4093681'),
       ('d332d7ba-2c33-4d75-a4c9-83ad7d8b1b1e',
        'Требуется подключить розетку к сети или подключить компьютер в работающую розетку.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('398b43c5-aaaa-4bdd-b7f0-93ee69a17819', 'При нажатии кнопки включения издаёт звуки?',
        '922974a3-7df1-4ab2-869e-694db4093681'),
       ('c358fe2e-31d2-47fd-a5b1-e0d4f2bc3c00', 'Напряжение на материнской плате есть?',
        '922974a3-7df1-4ab2-869e-694db4093681'),
       ('3231c329-40b7-4327-b9e7-31dbb4ba5010', 'Биппер издает звуки?', '922974a3-7df1-4ab2-869e-694db4093681'),
       ('d1a561a6-60bf-4948-b72b-27545f57a6de',
        'Неисправен блок питания, с малой вероятностью может быть неисправна материнская плата.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('d2490ea6-5a57-4342-837d-81861f6f08c1',
        'Скорее всего неисправна материнская плата или процессор. Требуется более детальный анализ - обратитесь к специалисту.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('203260b4-9a0a-47d8-84cc-bc8595c762c6', 'Биппер встроен в материнскую плату?',
        '922974a3-7df1-4ab2-869e-694db4093681'),
       ('6c1d49d3-9d7b-413d-87ce-4c82c4f5c708',
        'Требуется прослушать сигнал биппера, найти в документации значение сигнала. Или обратиться к специалисту.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('1b65039d-dbbb-40dc-9811-2a5531c93728',
        'Требуется подключить внешнее звуковое устройство и прослушать сигнал биппера. Найти в документации значение сигнала. Или обратиться к специалисту.',
        '151ae006-85a9-43cf-8de9-e1197a4057e3'),
       ('dc1e923f-2d71-43e5-8025-73f521ba9148', 'Неисправна материнская плата', '151ae006-85a9-43cf-8de9-e1197a4057e3');

insert into rules
values ('45935c52-3143-43a0-936e-4c670dec2704', '81c07256-02be-44c1-8113-159c852d8c0b',
        '6a042790-e366-45b0-af3f-54dd4e583b4f', '9f9a349b-5ca4-44de-82b5-8093a744bfc0'),
       ('c5d2e585-d72c-4ace-9d1a-8e723c19a792', '6a042790-e366-45b0-af3f-54dd4e583b4f',
        '398b43c5-aaaa-4bdd-b7f0-93ee69a17819', 'd332d7ba-2c33-4d75-a4c9-83ad7d8b1b1e'),
       ('04a8c627-652b-464b-8cc2-f39d8c6fa288', '398b43c5-aaaa-4bdd-b7f0-93ee69a17819',
        '3231c329-40b7-4327-b9e7-31dbb4ba5010', 'c358fe2e-31d2-47fd-a5b1-e0d4f2bc3c00'),
       ('1d8ed00a-a883-46c3-8a82-6ab307642b78', 'c358fe2e-31d2-47fd-a5b1-e0d4f2bc3c00',
        'd2490ea6-5a57-4342-837d-81861f6f08c1', 'd1a561a6-60bf-4948-b72b-27545f57a6de'),
       ('ab98c75a-a1b2-45b9-9955-dc2ddc60b41e', '3231c329-40b7-4327-b9e7-31dbb4ba5010',
        '6c1d49d3-9d7b-413d-87ce-4c82c4f5c708', '203260b4-9a0a-47d8-84cc-bc8595c762c6'),
       ('73df7460-7e14-443c-b744-856aca875674', '203260b4-9a0a-47d8-84cc-bc8595c762c6',
        'dc1e923f-2d71-43e5-8025-73f521ba9148', '1b65039d-dbbb-40dc-9811-2a5531c93728');