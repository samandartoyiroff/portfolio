package uz.tuit.portfolio.model;

public enum JobType {

    FULL_TIME("To‘liq stavkali", "Полная занятость", "Full-time"),
    PART_TIME("Yarim stavkali", "Неполная занятость", "Part-time"),
    CONTRACT("Shartnoma asosida", "По контракту", "Contract"),
    FREELANCE("Frilanser", "Фриланс", "Freelance"),
    INTERNSHIP("Amaliyot", "Стажировка", "Internship"),
    TEMPORARY("Vaqtincha ish", "Временная работа", "Temporary"),
    SEASONAL("Mavsumiy ish", "Сезонная работа", "Seasonal"),
    REMOTE("Masofaviy ish", "Удалённая работа", "Remote"),
    VOLUNTEER("Ko‘ngillilik asosida", "Волонтёрство", "Volunteer"),
    SHIFT("Navbatchilik asosida", "Сменная работа", "Shift work"),
    SELF_EMPLOYED("O‘zini o‘zi band qilgan", "Самозанятость", "Self-employed"),
    COMMISSION("Foiz asosida", "На комиссионной основе", "Commission-based"),
    APPRENTICESHIP("Shogirdlik dasturi", "Ученичество", "Apprenticeship");

    private final String uzName;
    private final String ruName;
    private final String enName;

    JobType(String uzName, String ruName, String enName) {
        this.uzName = uzName;
        this.ruName = ruName;
        this.enName = enName;
    }

    public String getUzName() {
        return uzName;
    }

    public String getRuName() {
        return ruName;
    }

    public String getEnName() {
        return enName;
    }
}

