package uz.tuit.portfolio.model;

public enum EducationType {

    KINDERGARTEN("Bog‘cha", "Детский сад", "Kindergarten"),
    PRIMARY_SCHOOL("Boshlang‘ich maktab", "Начальная школа", "Primary school"),
    SECONDARY_SCHOOL("O‘rta maktab", "Средняя школа", "Secondary school"),
    HIGH_SCHOOL("O‘rta maxsus maktab", "Старшая школа", "High school"),
    COLLEGE("Kollej", "Колледж", "College"),
    LYCEUM("Litsey", "Лицей", "Lyceum"),
    UNIVERSITY("Universitet", "Университет", "University"),
    INSTITUTE("Institut", "Институт", "Institute"),
    ACADEMY("Akademiya", "Академия", "Academy"),
    BACHELOR("Bakalavr", "Бакалавр", "Bachelor"),
    MASTER("Magistr", "Магистр", "Master"),
    PHD("PhD (Doktorantura)", "Докторантура (PhD)", "PhD"),
    DOCTORATE("Doktorantura", "Докторантура", "Doctorate"),
    STUDY_CENTRE("O‘quv markazi", "Учебный центр", "Study centre"),
    ONLINE_COURSE("Onlayn kurs", "Онлайн курс", "Online course"),
    BOOTCAMP("Bootcamp dasturi", "Буткемп программа", "Bootcamp"),
    TRAINING("Trening", "Тренинг", "Training"),
    CERTIFICATION("Sertifikatlash kursi", "Курс сертификации", "Certification course"),
    WORKSHOP("Seminar / Workshop", "Семинар / Воркшоп", "Workshop"),
    INTERNSHIP("Amaliyot", "Стажировка", "Internship"),
    PROFESSIONAL_DEVELOPMENT("Malaka oshirish", "Повышение квалификации", "Professional development"),
    CONTINUING_EDUCATION("Qo‘shimcha ta’lim", "Дополнительное образование", "Continuing education"),
    LANGUAGE_COURSE("Til kursi", "Языковой курс", "Language course"),
    TECHNICAL_SCHOOL("Texnik maktab", "Техническая школа", "Technical school");

    private final String uzName;
    private final String ruName;
    private final String enName;

    EducationType(String uzName, String ruName, String enName) {
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
