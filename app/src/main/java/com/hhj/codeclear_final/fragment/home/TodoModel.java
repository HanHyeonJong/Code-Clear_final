package com.hhj.codeclear_final.fragment.home;

public class TodoModel {
    private int id;                 // DB 에서 자동으로 추가 되도록
    private String content;         // 1건의 텍스트
    private String date;            // 저장된 날짜
    private int checked = 0;        // 체크박스 - 기본값 false
    private int feeling = 0;        // 기분

    public TodoModel() {
    }

    public TodoModel(int id, String content, String date, int checked) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.checked = checked;
    }

    public TodoModel(int id, String content, String date, int checked, int feeling) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.checked = checked;
        this.feeling = feeling;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", checked=" + checked +
                ", feeling=" + feeling +
                '}';
    }
}
