databaseChangeLog = {
    changeSet(author: "emaxwell", id: "changelog-security-1") {
        createTable(tableName: "person") {
            column(autoIncrement: "true", name: "id", type: "INT") {
                constraints(primaryKey: "true", primaryKeyName: "personPK")
            }
            column(name: "version", type: "INT") {
                constraints(nullable: "false")
            }
            column(name: "full_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "emaxwell", id: "changelog-security-2") {
        createTable(tableName: "person_security_role") {
            column(name: "security_role_id", type: "INT") {
                constraints(nullable: "false")
            }
            column(name: "person_id", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "emaxwell", id: "changelog-security-3") {
        createTable(tableName: "security_role") {
            column(autoIncrement: "true", name: "id", type: "INT") {
                constraints(primaryKey: "true", primaryKeyName: "security_rolePK")
            }
            column(name: "version", type: "INT") {
                constraints(nullable: "false")
            }
            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "emaxwell", id: "changelog-security-4") {
        addPrimaryKey(columnNames: "security_role_id, person_id", constraintName: "person_security_rolePK", tableName: "person_security_role")
    }

    changeSet(author: "emaxwell", id: "changelog-security-5") {
        addUniqueConstraint(columnNames: "username", constraintName: "UC_PERSONUSERNAME_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "person")
        addUniqueConstraint(columnNames: "email", constraintName: "UC_PERSONEMAIL_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "person")
    }

    changeSet(author: "emaxwell", id: "changelog-security-6") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_SECURITY_ROLEAUTHORITY_COL", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "security_role")
    }

    changeSet(author: "emaxwell", id: "changelog-security-7") {
        addForeignKeyConstraint(baseColumnNames: "person_id", baseTableName: "person_security_role", constraintName: "FK_a4m7f8l95pxepnu3b5uv4hpkt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "person")
    }

    changeSet(author: "emaxwell", id: "changelog-security-8") {
        addForeignKeyConstraint(baseColumnNames: "security_role_id", baseTableName: "person_security_role", constraintName: "FK_k2fmduslrakqpgc7pcinl9xxx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "security_role")
    }

    changeSet(author: "emaxwell", id: "changelog-security-11") {
        createTable(tableName: "oauth_client_details") {
            column(name: "client_id", type: "VARCHAR(50)") {
                constraints(nullable: "false")
            }
            column(name: "resource_ids", type: "VARCHAR(256)")
            column(name: "client_secret", type: "VARCHAR(256)")
            column(name: "scope", type: "VARCHAR(256)")
            column(name: "authorized_grant_types", type: "VARCHAR(256)")
            column(name: "web_server_redirect_uri", type: "VARCHAR(256)")
            column(name: "authorities", type: "VARCHAR(256)")
            column(name: "access_token_validity", type: "INT4")
            column(name: "refresh_token_validity", type: "INT4")
            column(name: "additional_information", type: "VARCHAR(4096)")
            column(name: "autoapprove", type: "VARCHAR(256)")
        }
    }

    changeSet(author: "emaxwell", id: "changelog-security-15") {
        addPrimaryKey(columnNames: "client_id", constraintName: "oauth_client_details_pkey", tableName: "oauth_client_details")
    }

    changeSet(author: "emaxwell", id: "changelog-security-16") {
        insert(tableName: 'oauth_client_details') {
            column(name: "client_id", value: "event-resource-app-client-id") // can be anything you want
            column(name: "resource_ids", value: "event-api")
            column(name: "client_secret", value: "event-resource-app-super-secret-client-secret") // can be anything you want
            column(name: "scope", value: "")
            column(name: "authorized_grant_types", value: "password")
            column(name: "web_server_redirect_uri", value: "")
            column(name: "authorities", value: "")
            column(name: "access_token_validity", value: 43200)
            column(name: "refresh_token_validity", value: 0)
            column(name: "additional_information", value: "")
            column(name: "autoapprove", value: true)
        }
    }
}
