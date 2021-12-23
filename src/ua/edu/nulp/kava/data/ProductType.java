package ua.edu.nulp.kava.data;

public enum ProductType {
    ZERNOVA("Зернова"),
    MELENA("Мелена"),
    ROZCHYNNA("Розчинна"),
    PAKETY("Пакети");

    private String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ProductType get(String name) {
        if (name == null) {
            return null;
        }

        for (ProductType type : values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
