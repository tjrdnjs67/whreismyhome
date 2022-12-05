use ssafyvue;

create table interest(
    `userid` VARCHAR(16),
    `aptCode` int,
    `regtime` timestamp not null default current_timestamp,
    primary key(`userid`,`aptCode`),
    foreign key (`userid`) references member(`userid`) on update restrict on delete restrict,
    foreign key (`aptCode`) references houseinfo(`aptCode`) on update restrict on delete restrict
);

alter table member add column `token` varchar(1000) null default null;