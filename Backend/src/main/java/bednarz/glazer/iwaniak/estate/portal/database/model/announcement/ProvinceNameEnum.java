package bednarz.glazer.iwaniak.estate.portal.database.model.announcement;

public enum ProvinceNameEnum {
    LOWER_SILESIAN("dolnośląskie"),
    KUYAVIAN_POMERANIAN("kujawsko-pomorskie"),
    LUBLIN("lubelskie"),
    LUBUSZ("lubuskie"),
    LODZ("łódzkie"),
    LESSER_POLAND("małopolskie"),
    MASOVIAN("mazowieckie"),
    OPOLE("opolskie"),
    SUBCARPATHIAN("podkarpackie"),
    PODLASKIE("podlaskie"),
    POMERANIAN("pomorskie"),
    SILESIAN("śląskie"),
    HOLY_CROSS("świętokrzyskie"),
    WARMIAN_MASURIAN("warmińsko-mazurskie"),
    GREATER_POLAND("wielkopolskie"),
    WEST_POMERANIAN("zachodniopomorskie");
    final String  polishName;

    ProvinceNameEnum(String s) {
        polishName=s;
    }

    @Override
    public String toString() {
        return  polishName;
    }
}
