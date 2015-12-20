package credible.software.event

class Event {

    String guid;
    String url;
    String summary;
    String details;
    Date eventDate;

    Date dateCreated;

    static constraints = {
        guid blank: false, unique: true
        url nullable: true
        summary blank: false
        details blank: false
    }
}
