import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

class Dates{
    public static class DateDiff{
        private LocalDate dateFirst;
        private LocalDate dateSecond;

        private DateDiff(LocalDate date1, LocalDate date2){
            dateFirst = date1;
            dateSecond =date2;
        }

        public String getStart(){
            return dateFirst.toString();
        }
        public String getEnd(){
            return dateSecond.toString();
        }
        public int getDiff(){
            return (int)Duration.between(dateFirst.atStartOfDay(), dateSecond.atStartOfDay()).toDays();
        }
        public String toString(){
            String formattedStartDate = dateFirst.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy"));
            String formattedEndDate =   dateSecond.format(DateTimeFormatter.ofPattern("EEEE dd.MM.yyyy"));

            return formattedStartDate + " --> " + formattedEndDate + ": " 
            + Duration.between(dateFirst.atStartOfDay(), dateSecond.atStartOfDay()).toDays() + " days";

        }
    }
    
    public static DateDiff[] dateDiffs(String ...dates){

        ArrayList<LocalDate> legalDates = new ArrayList<LocalDate>();
        ArrayList<DateDiff> dateDiffs = new ArrayList<DateDiff>();

        for(String dateStr : dates){
            int day = 0;
            int month = 0;
            int year = 0;
            LocalDate date;

            if(dateStr.charAt(2)== '.' || dateStr.charAt(1) == '.'){
                String[] parts = dateStr.split("\\.");

                day = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
                year = Integer.parseInt(parts[2]);
                if(parts[2].length()>4 || parts[2].length()<4){
                    month = 0;
                }
            }
            else if(dateStr.charAt(4)=='-' && dateStr.length()==10){
                String[] parts = dateStr.split("-");

                day = Integer.parseInt(parts[2]);
                month = Integer.parseInt(parts[1]);
                year = Integer.parseInt(parts[0]);
                if(parts[0].length()>4 || parts[0].length()<4){
                    month = 0;
                }
            }
            try{
                date = LocalDate.of(year, month, day);
                legalDates.add(date);
            }
            catch(DateTimeException e){
                System.out.printf("The date \"%s\" is illegal!\n",dateStr);
            }
        }
        Collections.sort(legalDates);
        for (int i = 1 ; i < legalDates.size(); i++){
            Dates.DateDiff diff = new DateDiff(legalDates.get(i-1), legalDates.get(i));
            dateDiffs.add(diff);
        }
        return dateDiffs.toArray(new DateDiff[dateDiffs.size()]);
    }
}