package dynamicdrillers.collegenoticeboard;

/**
 * Created by Happy-Singh on 2/24/2018.
 */

public class Notice {

    String NoticeId;
    String NoticeAuthor;
    String NoticeAuthorImage;
    String NoticeTitle;
    String NoticeDesc;
    String Time;
    String image;
    String Notice_Type;
    String Author_Type;


    public Notice(String noticeId, String noticeAuthor, String noticeTitle, String noticeDesc, String time, String image,String NoticeAuthorImage,String Notice_Type
    ,String Author_Type) {

        this.NoticeId = noticeId;
        this.NoticeAuthor = noticeAuthor;
        this.NoticeTitle = noticeTitle;
        this.NoticeDesc = noticeDesc;
        this.image = image;
        this.Time = time;
        this.NoticeAuthorImage=NoticeAuthorImage;
        this.Notice_Type = Notice_Type;
        this.Author_Type = Author_Type;
    }

    public String getAuthor_Type() {
        return Author_Type;
    }

    public void setAuthor_Type(String author_Type) {
        Author_Type = author_Type;
    }

    public String getNotice_Type() {
        return Notice_Type;
    }

    public void setNotice_Type(String notice_Type) {
        Notice_Type = notice_Type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getNoticeAuthorImage() {
        return NoticeAuthorImage;
    }

    public void setNoticeAuthorImage(String noticeAuthorImage) {
        NoticeAuthorImage = noticeAuthorImage;
    }
    public String getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(String noticeId) {
        NoticeId = noticeId;
    }

    public String getNoticeAuthor() {
        return NoticeAuthor;
    }

    public void setNoticeAuthor(String noticeAuthor) {
        NoticeAuthor = noticeAuthor;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        NoticeTitle = noticeTitle;
    }

    public String getNoticeDesc() {
        return NoticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        NoticeDesc = noticeDesc;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
