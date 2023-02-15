create database otivs;

create table states
(
    id   uuid unique  not null primary key,
    name varchar(255) not null unique
);

create table events
(
    id       uuid unique  not null primary key,
    content  varchar(255) not null unique,
    state_id uuid         not null,
    constraint state_fk
        foreign key (state_id)
            references states (id)
);

create table rules
(
    id       uuid unique not null primary key,
    event_id uuid unique not null,
    r1_id    uuid        not null,
    r2_id    uuid        not null,
    constraint event_rule_fk
        foreign key (event_id)
            references events (id),
    constraint r1_event_fk
        foreign key (r1_id)
            references events (id),
    constraint r2_event_fk
        foreign key (r2_id)
            references events (id)

);