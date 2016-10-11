package edu.uwp.kusd;

public class AppSection {

    private int icon;
    private Class className;

    AppSection(int icon, Class className) {
        this.icon = icon;
        this.className = className;
    }

    public int getIcon() {
        return icon;
    }

    public Class getActivityName() {
        return className;
    }
}
