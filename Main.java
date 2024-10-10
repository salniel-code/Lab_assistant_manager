/*
* Name: Sally Nielsen
*/
import java.util.Scanner; 

class Main {
  public static Scanner userInput;
  public static final int NUM_ASSISTANTS = 20;
  public static final int NUM_SESSIONS = 70;
  
  public static void main(String[] args) {
    /*
    assistants is a 2D string array that contains ID number, assistant name, education credits
    First column hold ID number (1000-9999)
    Second column hold assistants name 
    Third column hold the education points/hp
    Fourth column hold total salary
    */
    String[][] assistants = new String[NUM_ASSISTANTS][4]; 
    int numOfAssistants = 4;
    /*
    sessions is a 2D string array than contains ID number, start time, end time, date, salary
    First column hold id
    Second column hold name
    third column hold start time of session
    fourth column hold end time of session
    fifth column hold date
    sixth column hold salary
    */
    String[][] sessions = new String[NUM_SESSIONS][6];
    int numOfSessions = 3;

    addTestAssistants(assistants);
    addTestSessions(sessions);
    // Loop, print menu after every choice is handled
    while(true){
      switch(menu()){
        case 1:
          // 1. Add lab assistant
          if(addAssistant(assistants, numOfAssistants)){
            numOfAssistants++;
          }
          break;
        case 2:
          // 2. Remove lab assistant
          removeAssistant(assistants, numOfAssistants, sessions, numOfSessions);
          break;
        case 3:
          // 3. Register working hours
          System.out.println("Unfinished");
          break;
        case 4:
          // 4. Print pay slip
          printPaySlip(sessions, numOfSessions, assistants, numOfAssistants); 
          break;
        case 5:
          // 5. Print assistant summary
          printAssistantSummary(assistants, numOfAssistants);
          break;
        case -1:
          // q. End program
          System.exit(0);
          break;
      }
    }
  }

  /**
  * Validates that a date given month and day = month is 0-12 and day is 0-31
  * @param month  a string for the month to be tested
  * @param day  a string for the day to be tested
  * @return an integer, false if month and day is not valid and true otherwise
  */
  private static boolean validDate(String month, String day){
    // Parse month and day to int
    int intMonth = Integer.parseInt(month);
    int intDay = Integer.parseInt(day);
    // If month is less than 0 or greater than 12, return false
    if(intMonth < 0 || intMonth > 12){
      return false;
    }
    // If dat is less than 0 or grater than 31, return false
    if(intDay < 0 || intDay > 31){
      return false; 
    }
    return true;
  }
  /**
  * Checks that int hour and int min given is hour 0-23 and min 0-58
  * @param hour  an integer, the hour to be checked
  * @param min  an integer, the minutes to be checked
  * @return boolean, true of valid hour and minutes, otherwise false
  */
  private static boolean validTime(int hour, int min){
    // Return false if hours are not 0-23 or min are not 0-59 
    if(hour < 0 || hour > 23){
      return false;
    }
    if(min < 0 || min > 59){
      return false;
    }
    return true;
  }
  /**
  * Asks user for id number, removes item/assistant from two arrays given, if id number exists in them
  * @param assistants  array where items are to be removed
  * @param numOfAssistants  int used for iterating through assistants
  * @param session  array where items are to be removed
  * @param numOfSessions int used for iterating through sessions
  */
  private static void removeAssistant(String[][] assistants, int numOfAssistants, String[][] sessions, int numOfSessions){
    // Ask user to id number to be removed
    String idNum = "";
    userInput = new Scanner(System.in);

    // Ask user for id
    System.out.printf("Enter lab assistant's ID number:");
    // validate id
    idNum = userInput.nextLine();
    // check if id number exists, if false- return
    if(findIndexAssistants(idNum, assistants, numOfAssistants)<0){ 
      System.out.printf("%nThere is no lab assistan registered with ID %s%n",idNum);
      return;
    }
    // Assign the returned index associated with idNum in assistants 
    int index = findIndexAssistants(idNum, assistants, numOfAssistants);
    String removedName = assistants[index][1];
    // Assign null to elements
    assistants[index][0] = "";
    assistants[index][1] = "";
    assistants[index][2] = "";
    assistants[index][3] = "";
    
      // check if any pay slips exists for id number, then remove tthem
      if(findIndexAssistants(idNum, sessions, numOfSessions)>0){
        int indexSession = findIndexAssistants(idNum, sessions, numOfSessions);
        sessions[indexSession][0] = "";
        sessions[indexSession][1] = "";
        sessions[indexSession][2] = "";
        sessions[indexSession][3] = "";
        sessions[indexSession][4] = "";
        sessions[indexSession][5] = "";
      }
    System.out.printf("%nLab assistant %s was removed from the system%n", removedName);
  }
  /**
  * Adds new data to assistants when input are valid. asks user for name and credits
  * @param assistants  array where items are to be added
  * @param numOfAssistans  int used for index place in assistants where data will be added
  */
  private static boolean addAssistant(String[][] assistants, int numOfAssistants){
    // Scanner
    userInput = new Scanner(System.in);
    // Ask user for name, no requirements of input validation- 
    System.out.println("Enter lab assistants's name:");
    String name = userInput.nextLine();
    // Declare string for credits
    String credits = "";
    // Ask user to enter credits, loop until input is 0-400
    while(true){
      System.out.println("Enter number of education credits:");
      credits = userInput.nextLine(); 
      // make sure credit input is digit/ can be converted to int
      if(!isCreditNumbers(credits)){
        System.out.println("Invalid credits - must be numbers.");
        continue;
      }
      // Validate that credits are between 0 and 400
      if(!creditsValid(credits)){
        System.out.println("Invalid credits - must be between 0-400.");
        continue;
      }
      // Break if credit input was valid
      break;
    } 
    
    // Loop through assistants and add
    String idNum;
    // Break when a unique id has been found
    while(true){
      idNum = Integer.toString(999 + (int)(Math.random()*8999)+1); 
      // If id does exists in assistans already, continue looping for unique id
      if(findIndexAssistants(idNum, assistants, numOfAssistants)>0){ 
        continue; 
      }
      break;
    }
    //add id number, name, credits, salary as "blank" for now
    assistants[numOfAssistants][0] = idNum;
    assistants[numOfAssistants][1] = name;
    assistants[numOfAssistants][2] = credits;
    assistants[numOfAssistants][3] = "";
    // Print confirmation of added assistant
    System.out.printf("%nLab assistant %s was assigned Id %s and added to the system%n", name, idNum);
    return true;
  }
  // validate that a given string contains number between 0-9
  private static boolean isCreditNumbers(String credits){
    int numLetters = credits.length();
    return credits.matches("[0-9]{"+ numLetters +"}");
  }
  // Validate credits are 0-400
  private static boolean creditsValid(String credits){
    // Convert credits to integer
    int creditInt = Integer.parseInt(credits);
    // if credits are greater than -1 and less than 400, return true. else false
    if(creditInt > -1 && creditInt < 400){ 
      return true;
    } 
    // return false if condition wasnt true
    return false;
  }
  /**
  * Prints a pay slip, if user input is a valid and existing id number in assistants and sessions (if it existed in assistants)
  * @param session  array from which items associated with id number are to be printed
  * @param numOfSessions int used for iterating through sessions
  * @param assistants  array from which items associated with id number are to be printed
  * @param numOfAssistants  int used for iterating through assistants
  */
  private static void printPaySlip(String[][] sessions, int numOfSessions, String[][] assistants, int numOfAssistants){

    String idNum = "";
    userInput = new Scanner(System.in);
    while(true){
      // Ask user for id
      System.out.printf("Enter lab assistant's ID number:");
      // validate id
      idNum = userInput.nextLine();
      // check if id number exists
      if(findIndexAssistants(idNum, assistants, numOfAssistants)<0){ 
        System.out.println("ID does not exist, try again!");
        continue; 
      }
      // check if any pay slips exists for id number
      if(findIndexAssistants(idNum, sessions, numOfSessions)<0){
        System.out.println("Payslip does not exist for this ID");
        return;
      }
      break;
    }
    // assigned returned index to index integer 
    int index = findIndexAssistants(idNum, assistants, numOfAssistants);
    String name = assistants[index][1];
    String credit = assistants[index][2];
    int salaryTotal = 0;
    int sessionsNum = 0;
    
    // print name, id number, credit
    System.out.printf("Name: %s(%s)%nNumber of education credits: %-15s%n", name, idNum, credit); 
    // Print sessions for id number
    System.out.printf("\nSessions: \n\n");
    // print date, start time, end time, salary
    System.out.printf("%-20s %-15s %-15s %-15s%n", "Date", "Start", "End", "Salary"); 
    // Loop through sessions and print elements
    for(int i = 0; i < numOfSessions; i++){
      // If id isnt null
      if(sessions[i][0]!= null){
        // If idNumber matches  any id in sessions, print
        if(idNum.equals(sessions[i][0])){
          System.out.printf("%-20s %-15s %-15s %s kr%n", sessions[i][4], sessions[i][2], sessions[i][3], sessions[i][5]);
          // increment sessions number
          sessionsNum++;
          // add to salaryTotal, every salary
          salaryTotal = salaryTotal + Integer.parseInt(sessions[i][5]);
        }
      }
    }
    // Print number of sessions and total salary of sessions
    System.out.printf("%nTotal number of sessions: %d%nTotal salary: %d kr%n", sessionsNum,salaryTotal);
  }
  
  /**
  * Finds the index position of an array using id number, if it exists, otherwise returns -1
  * @param session  array from which items associated with id number are to be printed
  * @param idNum int used for searching for right index place
  * @param assistants  array where the id number will be searched
  * @param numOfAssistants  int used for iterating through assistants
  * @return index number or -1 if not found
  */
  private static int findIndexAssistants(String idNum, String[][] assistants, int numOfAssistants){
    // Index to be returned by method
    int index = -1;
    // Loop through assistans, find where idNum matches, return index position
    for(int i = 0; i < numOfAssistants; i++){
      if(assistants[i][0] != null){
        if(idNum.equals(assistants[i][0])){
          index = i;
        }
      }
    }
    return index; 
  }
  
  /**
  * Prints a summary of assistants array: name, id, credits and salary
  * @param assistants  array from which items will be printed
  * @param numOfAssistants  int used for iterating through assistants
  */
  private static void printAssistantSummary(String[][] assistants, int numOfAssistants){
    // call sort method 
    sortSummary(assistants, numOfAssistants);
    // Print titles
    System.out.printf("%-20s %-15s %-15s %-15s%n", "Name", "ID", "Credits", "Salary");
    // Loop thorugh assistants and print items
    for(int i = 0; i < numOfAssistants; i++){
      if(assistants[i][0].length()>0){
        System.out.printf("%-20s %-15s %-15s %-15s%n", assistants[i][1], assistants[i][0], assistants[i][2], assistants[i][3]);
      }
    }
  }
  /**
  * Sorts array using bubble sort. checks if next columns name value is greater, switches if true
  * @param assistants  array from whcih items are to be sorted
  * @param numOfAssistants  int used for iterating through assistants
  */
  private static void sortSummary(String[][] assistants, int numOfAssistants){
    // Bubble sort assistants. assuming sorting isnt case sensitive
    for(int i = 0; i < numOfAssistants; i++){
      for(int j = 0; j < numOfAssistants-i-1; j++){
        // Make sure its not null
        if(assistants[j][0] != null && assistants[i][0]!= null){
          // compare name from j and j+1
          if(assistants[j][1].compareToIgnoreCase(assistants[j+1][1])>0){
            String[] temp = assistants[j];
            assistants[j] = assistants[j+1];
            assistants[j+1] = temp;
          }
        }
      }
    }
  }
  /**
  * PMenu that prints options of actions for user
  * @return an integer from input() method
  */
  private static int menu(){
    // Print menu options
    System.out.println("----------------------------------");
    System.out.println("# LTU Lab Assistant Manager");
    System.out.println("----------------------------------");
    System.out.println("1. Add lab assistant");
    System.out.println("2. Remove lab assistant");
    System.out.println("3. Register working hours");
    System.out.println("4. Print pay slip");
    System.out.println("5. Print assistant summary");
    System.out.println("q. End program");
    // Return inpu / choice
    return input();
  }
  /**
  * Reads anc checks user input. If integer and > 0, returns integer. If string and 1, returns -1
  * @return integer from valid input
  */
  private static int input(){
    // variable int will be returned
    int number = 0;
    // Loops until valid option is entered
    while(true){
      // Initiate scanner
      userInput = new Scanner(System.in);
      // If userInput has integer
      if(userInput.hasNextInt()){
        number = Math.abs(userInput.nextInt());
        // If number is greater than 0, break loop
        if(number > 0){ 
          break;
        }
      } // Check if input was string
      else if(userInput.hasNext()){
        String inputStr = userInput.next();
        // Check if input was q, if true, number -1 and break loop
        if(inputStr.equalsIgnoreCase("q")){
          number = -1;
          break;
        }
      } 
      System.out.println("Invalid input, try again:");
    }
    //Return number
    return number;
  }
  /**
  * Adds test data to array
  * @param assistants  array to which new testdata will be added
  */
  private static void addTestAssistants(String[][] assistants){
    // Entry 1
    assistants[0][0] = "4311"; 
    assistants[0][1] = "Johannes Jonsson"; 
    assistants[0][2] = "120"; 
    assistants[0][3] = ""; 
    // Entry 2
    assistants[1][0] = "1010"; 
    assistants[1][1] = "Sofia Svensson"; 
    assistants[1][2] = "30"; 
    assistants[1][3] = "480"; 
    // Entry 3
    assistants[2][0] = "2120"; 
    assistants[2][1] = "Fredrik Falk"; 
    assistants[2][2] = "70"; 
    assistants[2][3] = "240"; 
    // Entry 4
    assistants[3][0] = "6207"; 
    assistants[3][1] = "Malin Malkesson";  
    assistants[3][2] = "130";  
    assistants[3][3] = "";  
  }
  
  /**
  * PAdds test data to sessions.
  * @param session  array to which test data will be added
  */
  private static void addTestSessions(String[][] sessions){
    // Entry 1
    sessions[0][0] = "1010"; 
    sessions[0][1] = "Sofia Svensson";  
    sessions[0][2] = "08:30";
    sessions[0][3] = "08:50";
    sessions[0][4] = "2023-11-12";
    sessions[0][5] = "160";
    // Entry 2
    sessions[1][0] = "1010"; 
    sessions[1][1] = "Sofia Svensson";  
    sessions[1][2] = "09:30";
    sessions[1][3] = "10:50";
    sessions[1][4] = "2023-11-10";
    sessions[1][5] = "320";
    // Entry 2
    sessions[2][0] = "2120"; 
    sessions[2][1] = "Fredrik Falk"; 
    sessions[2][2] = "14:30";
    sessions[2][3] = "16:20";
    sessions[2][4] = "2023-10-09";
    sessions[2][5] = "240";
  }

  /**
  * Adds new data to sessions and updates assistants salary. Checks if id number exists in assistants before adding 
  * @param assistants the array to update and check wheter id number exists
  * @param numofAssistants integer that represents index of assistants 
  * @param sessions the array to add new data/session to
  * @param numOfSessions integer that represents index position to add new data
  * @return a boolean, true if not invalid input, false if invalid
  */
  /*
  private static boolean regWorkHours(String[][] assistants, int numOfAssistants, String[][] sessions, int numOfSessions){

    // variables for input
    String idNum = "";
    int startHour = 0;
    int startMin = 0;
    int endHour = 0;
    int endMin = 0;
    String date = "";
    int index = 0;
    userInput = new Scanner(System.in);
    while(true){
      // Ask user for id
      System.out.println("Enter lab assistant's ID number:");
      // assign input to idNum
      idNum = userInput.nextLine();
      // validate id number exists in assistants
      if(findIndexAssistants(idNum, assistants, numOfAssistants)<0){ 
        System.out.printf("%nThere is no lab assistan registered with ID %s%n",idNum);
        continue;
      }
      index = findIndexAssistants(idNum, assistants, numOfAssistants);
      break;
    }
    while(true){
      // Ask user for start time of session
      System.out.println("Enter start time of session:");
      // assign input to start
      userInput.useDelimiter(":|\\s");
      startHour = userInput.nextInt();
      startMin = userInput.nextInt(); 
      // Validate time input is [HH:MM]
      if(!validTime(startHour, startMin)){
        System.out.println("Not a valid time, try again!");
        continue;
      }
      break;
    }
    // To calculate salary later
    double totalTime = 0.0;
    while(true){
      // Ask user for end time of session
      System.out.println("Enter end time of session:");
      // assign input to start
      userInput.useDelimiter(":|\\s");
      endHour = userInput.nextInt();
      endMin = userInput.nextInt(); 
      // Validate that its numbers and not character-
      // Validate time input is [HH:MM]
      if(!validTime(endHour, endMin)){
        System.out.println("Not a valid time, try again!");
        continue;
      }
      // Calculate time
      double startTime = startHour*60 + startMin; 
      double endTime = endHour*60 + endMin;

      // Validate that end is after start
      if(startTime > endTime){
        System.out.println("End of session can not be before start of session");
        continue;
      }
      // calculate total time
      totalTime = endTime - startTime;
      break;
    }
    // Strings for date
    String year = "";
    String month = "";
    String day = "";
    while(true){
      // Ask user to enter date
      System.out.println("Enter date of the session [YYYY-MM-DD]:");
      // assign input to start
      userInput.useDelimiter("-|\\s");
      year = userInput.next();
      month = userInput.next(); 
      day = userInput.next(); 
      // Validate that date is [YYYY-MM-DD]
      if(!validDate(month, day)){
        System.out.println("Not a valid date, try again!");
        continue;
      }
      break;
    }
    date = year + "-" + month + "-" + day;
    System.out.println(date);
    // Calculate salary
    // Add new session to sessions
    int salary = calculateSal(index, assistants, totalTime);

    // add one salary to sessions, add full salary to assistants
    int credit = Integer.parseInt(assistants[index][2]);

    System.out.println("Not finished");
    return true; 
  }
  /**
  * Calculates salary for session
  * @param assistants  an array
  * @param totalTime an integer to calculate cost/salary of session
  * @return an integer for calculated data
  */
  /*
  private static int calculateSal(int index, String[][] assistants, double totalTime){
    int number = 0;
    int credit = Integer.parseInt(assistants[index][2]);
    return number;
  }
  */
}