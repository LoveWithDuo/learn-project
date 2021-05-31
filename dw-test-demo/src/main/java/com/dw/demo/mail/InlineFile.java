package com.dw.demo.mail;
/**
 * 内嵌类型，目前只用到img
 * @author fufei
 *
 */
public class InlineFile extends MailType {
    private String filePath;
    private String cid;

    public InlineFile(String filePath, String cid) {
        super();
        this.filePath = filePath;
        this.cid = cid;
    }

    public InlineFile() {
        super();
    }


    @Override
    public char getType() {
        return MailType.TYPE_FILE;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

}