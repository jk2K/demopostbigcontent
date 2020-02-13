package com.jk2k.demopostbigcontent;

public class BigData {

    private String job_num;
    private int historyId;
    private String jenkins;
    private String branch_id;
    private String result_json;
    private String result_urlencoded;


    public String getResult_urlencoded() {
        return result_urlencoded;
    }

    public void setResult_urlencoded(String result_urlencoded) {
        this.result_urlencoded = result_urlencoded;
    }

    public String getJob_num() {
        return job_num;
    }

    public void setJob_num(String job_num) {
        this.job_num = job_num;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getJenkins() {
        return jenkins;
    }

    public void setJenkins(String jenkins) {
        this.jenkins = jenkins;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getResult_json() {
        return result_json;
    }

    public void setResult_json(String result_json) {
        this.result_json = result_json;
    }
}
