
public class Player {
    private String name;
    private EnumColor color;

    Player(String name, EnumColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumColor getColor() {
        return color;
    }

    public void setColor(EnumColor color) {
        this.color = color;
    }

    public String getDataToSave() {
        return String.format("%s&sep&%s\n", getName(), EnumColor.colorToInt(getColor()));
    }
}
