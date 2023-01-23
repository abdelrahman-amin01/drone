
create table drone (
                       id varchar(100) not null,
                       battery_capacity double,
                       model varchar(255),
                       state varchar(255),
                       weight_limit double,
                       primary key (id)
);
create table drone_medications (
                                   drone_id varchar(100) not null,
                                   medication_code varchar(255) not null
);

create table medication (
                            code varchar(255) not null,
                            image varbinary(255),
                            name varchar(255),
                            weight double,
                            primary key (code)
);

alter table drone_medications
    add constraint FK4qbbsp117vqi28ydvm5x6k21
        foreign key (medication_code)
            references medication;

alter table drone_medications
    add constraint FK78qcv47wdkws8msos565gamdw
        foreign key (drone_id)
            references drone;