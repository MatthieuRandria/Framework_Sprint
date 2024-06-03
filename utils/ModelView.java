package utils;

import java.util.HashMap;

public class ModelView {
    String url;
    HashMap<String,Object> data = new HashMap<>();


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public ModelView(){}

    public ModelView(String url, HashMap<String, Object> data) {
        this.setUrl(url);
        this.setData(data);
    }

    public void add(String url,Object data){
        this.data.put(url,data);
    }
    
}