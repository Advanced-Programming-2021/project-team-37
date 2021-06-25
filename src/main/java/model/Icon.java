package model;

public enum Icon {
    NORMAL,
    CONTINUOUS,
    QUICKPLAY,
    COUNTER,
    FIELD,
    EQUIP,
    RITUAL,
    NULL;

    public static Icon getIcon(String icon) {
        switch (icon){
            case "Normal":
                return NORMAL;
            case "Continuous":
                return CONTINUOUS;
            case "Quick-play":
                return QUICKPLAY;
            case "Counter":
                return COUNTER;
            case "Field":
                return FIELD;
            case "Equip":
                return EQUIP;
            case "Ritual":
                return RITUAL;
            default:
                return NULL;
        }
    }

}
