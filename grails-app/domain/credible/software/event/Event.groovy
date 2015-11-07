package credible.software.event

class Event {

    String guid;
    String url;
    String summary;
    String details;
    String thumbnailImgUrl;
    String detailImgUrl;

    static constraints = {
        guid blank: false, unique: true
        url nullable: true
        summary blank: false
        details blank: false
        thumbnailImgUrl nullable: true
        detailImgUrl nullable: true
    }
}
