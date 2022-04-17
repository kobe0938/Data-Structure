/**
 * In a nonleap year for the French Revolutionary Calendar, the first
 * twelve months have 30 days and month 13 has five days.
 */
public class FrenchRevolutionaryDate extends Date {

    public FrenchRevolutionaryDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public int dayOfYear() {
        return (super.month - 1) * 30 + super.dayOfMonth;
    }

    @Override
    public Date nextDate() {
        int newDay, newMonth, newYear;
        if ((super.dayOfMonth == 30) || (super.dayOfMonth == 5 && super.month == 13)) {
            newDay = 1;
            newMonth = super.month + 1;
        } else {
            newDay = super.dayOfMonth + 1;
            newMonth = super.month;
        }
        if (newMonth == 14) {
            newMonth = 1;
            newYear = super.year + 1;
        } else {
            newYear = super.year;
        }
        return new FrenchRevolutionaryDate(newYear, newMonth, newDay);
    }
}