package edu.uwp.kusd.homepage;

public class AppSection {

    private int icon;
    private Class className;
    private String sectionName;

     public AppSection(int icon, Class className, String sectionName) {
        this.icon = icon;
        this.className = className;
        this.sectionName = sectionName;
    }

    public int getIcon() {
        return icon;
    }

    public Class getActivityName() {
        return className;
    }

    public String getSectionName() {
        return sectionName;
    }
}
