package edu.uwp.kusd;

/**
 * Created by Cabz on 10/25/2016.
 */

public class LunchObj {
    private String title;
    private String fileUrl;
    private  String category;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getfileURL(){
        return fileUrl;
    }

    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public  void cloneLunch(LunchObj object){
        this.category = object.getCategory();
        this.title = object.getTitle();
        this.fileUrl = object.getfileURL();
        }
    }


