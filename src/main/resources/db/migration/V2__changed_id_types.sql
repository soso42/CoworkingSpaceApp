ALTER TABLE workspace
ALTER COLUMN    id             TYPE BIGINT;

ALTER TABLE booking
ALTER COLUMN    id             TYPE BIGINT,
ALTER COLUMN    workspace_id   TYPE BIGINT;
