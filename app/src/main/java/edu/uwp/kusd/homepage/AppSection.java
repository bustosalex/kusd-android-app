package edu.uwp.kusd.homepage;

public class AppSection {

    /**
     * The icon for the app section
     */
    private int icon;

    /**
     * The class name for the app section
     */
    private Class className;

    /**
     * The name of the section
     */
    private String sectionName;

    /**
     * Constructs an app section
     *
     * @param icon        the icon for the app section
     * @param className   the class name for the app section
     * @param sectionName the name of the section
     */
    public AppSection(int icon, Class className, String sectionName) {
        this.icon = icon;
        this.className = className;
        this.sectionName = sectionName;
    }

    /**
     * Gets the icon of the section
     *
     * @return the icon of the section
     */
    public int getIcon() {
        return icon;
    }

    /**
     * Gets the class name for the app section
     *
     * @return the class name for the app section
     */
    public Class getActivityName() {
        return className;
    }

    /**
     * Gets the section name
     *
     * @return the section name
     */
    public String getSectionName() {
        return sectionName;
    }
}
