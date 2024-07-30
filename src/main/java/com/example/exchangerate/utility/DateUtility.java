package com.example.exchangerate.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtility {

  public static String convertLocalDateToString(LocalDate localDate){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the format
    return localDate.format(formatter); // Format the date
  }

  public static LocalDate convertStringToLocalDate(String date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return LocalDate.parse(date, formatter);
  }
}
