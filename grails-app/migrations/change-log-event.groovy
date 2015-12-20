databaseChangeLog = {
    changeSet(author: "emaxwell", id: "changelog-event-1") {
        createTable(tableName: "event") {
            column(autoIncrement: "true", name: "id", type: "INT") {
                constraints(primaryKey: "true", primaryKeyName: "eventPK")
            }
            column(name: "version", type: "INT") {
                constraints(nullable: "false")
            }
            column(name: "guid", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "url", type: "VARCHAR(255)") {
                constraints(nullable: "true")
            }
            column(name: "summary", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "details", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
            column(name: "event_date", type: "DATETIME") {
                constraints(nullable: "false")
            }
            column(name: "date_created", type: "DATETIME") {
                constraints(nullable: "false")
            }
        }
    }
}
