create table if not exists users
(
    id bigint generated always as identity primary key,
    email varchar not null,
    name varchar not null
);

create table if not exists categories
(
    id bigint generated always as identity primary key,
    name varchar not null
);

create table if not exists locations
(
    id bigint generated always as identity primary key,
    lat float not null,
    lon float not null
);

create table if not exists events
(
    id bigint generated always as identity primary key,
    annotation varchar not null,
    category_id bigint not null references categories (id) on delete cascade,
    confirmed_requests int,
    created_on timestamp without time zone not null,
    description varchar not null,
    event_date timestamp without time zone not null,
    user_id bigint not null references users (id) on delete cascade,
    location_id bigint not null references locations (id) on delete cascade,
    paid bool,
    participant_limit int,
    published_on timestamp without time zone,
    request_moderation bool,
    title varchar not null,
    state varchar not null,
    views int
);

create table if not exists participation_requests
(
    id bigint generated always as identity primary key,
    created timestamp without time zone not null,
    event_id bigint not null references events (id) on delete cascade,
    user_id bigint not null references users (id) on delete cascade,
    status varchar not null
);

create table if not exists compilations
(
    id bigint generated always as identity primary key,
    pinned bool not null,
    title varchar not null
);

create table if not exists compilations_events
(
    events_id bigint not null references events (id) on delete cascade ,
    compilation_id bigint not null references compilations (id) on delete cascade
);

create table if not exists subscriptions
(
    id bigint generated always as identity primary key,
    user_id bigint not null references users (id) on delete cascade,
    follower_id bigint not null references users (id) on delete cascade,
    date timestamp without time zone not null
);

insert into users (id, email, name) overriding system value
values (1, 'a.s.pushkin@mail.ru', 'Александр Сергеевич Пушкин');
insert into users (id, email, name) overriding system value
values (2, 'tolstoy@mail.ru', 'Лев Николаевич Толстой');
insert into users (id, email, name) overriding system value
values (3, 'aaakhmatova@mail.ru', 'Анна Андреевна Ахматова');
insert into users (id, email, name) overriding system value
values (4, 'tstvetaeva@mail.ru', 'Марина Ивановна Цветаева');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (1, 4, 1, '2024-01-18 15:00:00.0000');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (2, 3, 1, '2024-01-18 15:10:00.0000');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (3, 1, 2, '2024-01-18 15:20:00.0000');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (4, 4, 2, '2024-01-18 15:30:00.0000');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (5, 1, 4, '2024-01-18 14:00:00.0000');
insert into subscriptions(id, user_id, follower_id, date) overriding system value
values (6, 1, 3, '2024-01-18 14:40:00.0000');
insert into categories (id, name) overriding system value
values (1, 'Выступления');
insert into locations (id, lat, lon) overriding system value values (1, 55.7522, 37.6156);
insert into locations (id, lat, lon) overriding system value values (2, 55.7523, 37.6156);
insert into events (id, annotation, category_id, confirmed_requests, created_on, description, event_date, user_id, location_id, paid, participant_limit, published_on, request_moderation, title, state, views) overriding system value
values (1, 'Выход в свет: поэма Медный Всадник', 1, 2, '2024-01-18 10:00:00.0000', 'Я хочу представить Вам свою петербургскую повесть, об истории безумия и гибели мелкого чиновника Евгения, потерявшего возлюбленную во время наводнения', '2025-01-01 15:00:00.0000', 1, 1, false, 2, '2024-01-18 12:00:00.0000', false, 'Петербургская повесть', 'PUBLISHED', 1);
insert into events (id, annotation, category_id, confirmed_requests, created_on, description, event_date, user_id, location_id, paid, participant_limit, published_on, request_moderation, title, state, views) overriding system value
values (2, 'Моё первое издание: Вечерний альбом', 1, 0, '2024-01-17 17:00:00.0000', 'Москва, я хочу представить тебе своё первое творение и надеюсь, что оно отзовётся в сердцах миллиона москвичей', '2024-12-12 18:00:00.0000', 4, 2, false, 0, '2024-01-17 19:00:00.0000', false, 'Вечерний альбом', 'PUBLISHED', 0);
insert into participation_requests (id, created, event_id, user_id, status) overriding system value values (1, '2024-01-18 15:30:00.0000', 1, 3, 'PENDING');
insert into participation_requests (id, created, event_id, user_id, status) overriding system value values (2, '2024-01-18 14:00:00.0000', 1, 2, 'PENDING');