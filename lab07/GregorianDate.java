public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }


    // YOUR CODE HERE

    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < super.month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + super.dayOfMonth;
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }

    @Override
    public Date nextDate() {
        int newMonth = super.month;
        int newDay = super.dayOfMonth;
        int newYear = super.year;
        if (newDay == MONTH_LENGTHS[super.month-1]){
            newDay = 1;
            newMonth++;
            if (newMonth > 12){
                newMonth = 1;
                newYear++;
            }
        }else{
            newDay++;
        }
        return new GregorianDate(newYear, newMonth, newDay);



//        for(int i = 0; i < MONTH_LENGTHS.length;i++) {
//            if(newdayOfYear > MONTH_LENGTHS[i]) {
//                newdayOfYear -= MONTH_LENGTHS[i];
//                newMonth++;
//            }
       }


}