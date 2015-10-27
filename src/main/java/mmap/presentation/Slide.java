package mmap.presentation;

public class Slide {
    private String content;
    private Integer dataX;
    private Integer dataY;


    public Slide(String content, Integer dataX, Integer dataY) {
        this.content = content;
        this.dataX = dataX;
        this.dataY = dataY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDataX() {
        return dataX;
    }

    public void setDataX(Integer dataX) {
        this.dataX = dataX;
    }

    public Integer getDataY() {
        return dataY;
    }

    public void setDataY(Integer dataY) {
        this.dataY = dataY;
    }

    public void moveX(int delta) {
        dataX += delta;
    }

}
