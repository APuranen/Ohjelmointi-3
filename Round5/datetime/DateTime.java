public class DateTime extends Date{

    private int seconds;
    private int minutes;
    private int hours;
    private String sTime;

    DateTime(int year, int month, int day, int hour, int minute, int second) throws DateException{
        super(year, month, day);

        int illegal = 0;

        if(second < 1 | 60 < second){illegal = 1;}
        if(minute < 1 | 59 < minute){illegal = 1;}
        if(hour < 1 | 24 < hour){illegal = 1;}

        if(illegal == 1){
            throw new DateException(String.format("Illegal time %d.%d.%d", hour, minute, second));
        }

        else{
            String sHour =  Integer.toString(hour);
            if(sHour.length() == 1) {sHour = ("0" + sHour);}
            String sMinute = Integer.toString(minute);
            if(sMinute.length() == 1) {sMinute = ("0" + sMinute);}
            String sSecond = Integer.toString(second);
            sTime = (sHour + ":" + sMinute + ":" + sSecond);

            this.seconds = second;
            this.minutes = minute;
            this.hours = hour;
        }

    }
    public int getHour(){
        return hours;
    }
    public int getMinute(){
        return minutes;
    }
    public int getSecond(){
        return seconds;
    }
    public String toString(){
        String date = super.toString();
        String timeDate = date + " " + sTime;
        return timeDate;
    }
}
