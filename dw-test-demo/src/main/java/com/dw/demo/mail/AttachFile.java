package com.dw.demo.mail;
/**
 * 附件类型
 * @author fufei
 *
 */
public class AttachFile extends MailType {
    private String filePath;
    private String fileName;

    @Override
    public char getType() {
        return MailType.TYPE_ATTACH;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
