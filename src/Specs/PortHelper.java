package Specs;

public class PortHelper {

    private String num, name, type, description;

    /**
     * @param num
     * @param name
     * @param type
     * @param description
     */
    public PortHelper ( String num, String name, String type, String description) {
        this.num = num;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    /**
     * @return
     */
    public String getNum() {
        return num;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

}
