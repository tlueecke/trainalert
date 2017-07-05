alter table TERMINAL_WATCH_JOB add
	(FROM_TERMINAL VARCHAR(255),
	TO_TERMINAL VARCHAR(255));

update TERMINAL_WATCH_JOB set FROM_TERMINAL = TERMINAL_ID;

alter table TERMINAL_WATCH_JOB modify FROM_TERMINAL not null;

alter table TERMINAL_WATCH_JOB drop column TERMINAL_ID;