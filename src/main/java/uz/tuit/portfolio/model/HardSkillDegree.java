package uz.tuit.portfolio.model;

public enum HardSkillDegree {

    BEGINNER("Boshlang‘ich", "Beginner", "Начальный"),
    INTERMEDIATE("O‘rta", "Intermediate", "Средний"),
    ADVANCED("Yuqori", "Advanced", "Продвинутый"),
    EXPERT("Mutaxassis", "Expert", "Эксперт"),
    MASTER("Usta / Professional", "Master", "Мастер");

    private final String uz;
    private final String en;
    private final String ru;

    HardSkillDegree(String uz, String en, String ru) {
        this.uz = uz;
        this.en = en;
        this.ru = ru;
    }

    public String getUz() {
        return uz;
    }

    public String getEn() {
        return en;
    }

    public String getRu() {
        return ru;
    }
}
