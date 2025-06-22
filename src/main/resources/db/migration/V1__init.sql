
CREATE TABLE IF NOT EXISTS workspace (
    id                      INT             NOT NULL GENERATED ALWAYS AS IDENTITY,
    type                    VARCHAR(20)     NOT NULL,
    price                   INT             NOT NULL,
    available               BOOLEAN         NOT NULL DEFAULT TRUE,

    CONSTRAINT pk_workspace PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking (
    id                      INT             NOT NULL GENERATED ALWAYS AS IDENTITY,
    workspace_id            INT             NOT NULL,
    start_date              DATE            NOT NULL,
    end_date                DATE            NOT NULL,

    CONSTRAINT pk_booking PRIMARY KEY (id),
    CONSTRAINT fk_workspace FOREIGN KEY (workspace_id) REFERENCES workspace(id)
);
