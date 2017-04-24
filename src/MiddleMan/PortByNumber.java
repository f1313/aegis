package MiddleMan;

public class PortByNumber {

    private int num;
    private String name, description, type;

    /**
     * @return
     */
    public int getNum() {
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

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortByNumber that = (PortByNumber) o;

        return num == that.num;
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return num;
    }

}
