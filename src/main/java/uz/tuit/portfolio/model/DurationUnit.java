package uz.tuit.portfolio.model;

public enum DurationUnit {
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    private final int days;

    DurationUnit(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
