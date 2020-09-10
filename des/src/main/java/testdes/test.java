package testdes;

public class test {

     public static void main(String[] args) {
         String msg="这不难理解。在这个演示样例中，仅仅有一个名为 people 的变量。值是包括三个条目的数组，每一个条目是一个人的记录，当中包括名、姓和电子邮件地址。上面的演示样例演示怎样用括号将记录组合成一个值。\n" +
                 "\n" +
                 "当然，能够使用同样的语法表示多个值（每一个值包括多个记录）：\n" +
                 "\n" +
                 "{ \"programmers\": [\n" +
                 "  { \"firstName\": \"Brett\", \"lastName\":\"McLaughlin\", \"email\": \"brett@newInstance.com\" },\n" +
                 "  { \"firstName\": \"Jason\", \"lastName\":\"Hunter\", \"email\": \"jason@servlets.com\" },\n" +
                 "  { \"firstName\": \"Elliotte\", \"lastName\":\"Harold\", \"email\": \"elharo@macfaq.com\" }\n" +
                 " ],\n" +
                 "\"authors\": [\n" +
                 "  { \"firstName\": \"Isaac\", \"lastName\": \"Asimov\", \"genre\": \"science fiction\" },\n" +
                 "  { \"firstName\": \"Tad\", \"lastName\": \"Williams\", \"genre\": \"fantasy\" },\n" +
                 "  { \"firstName\": \"Frank\", \"lastName\": \"Peretti\", \"genre\": \"christian fiction\" }\n" +
                 " ],\n" +
                 "\"musicians\": [\n" +
                 "  { \"firstName\": \"Eric\", \"lastName\": \"Clapton\", \"instrument\": \"guitar\" },\n" +
                 "  { \"firstName\": \"Sergei\", \"lastName\": \"Rachmaninoff\", \"instrument\": \"piano\" }\n" +
                 " ]\n" +
                 "}";
         String msg2="v1tqbVvjZmjOgUXGzeORhK+y5j5h7SREeLTSN0CQVXbyTae8/6ZPoammvKb/hBqRf+fiFrxupdPY\n" +
                 "s834AUvqyKZxXoMCKvw2d1sd6gbeEQFQjHhF8qwGspm6mSLCo9T5qRIUDEgYveJETcavNrETTIln\n" +
                 "csV1719LNsO9e7UbmqbORNJv2cF7lADJeahjeTJBULl/kjrHGZ98LXu+PAWxwkSQEus1mjSS1QB1\n" +
                 "z/ycBMQn0rD0Bxk/TTDoIwNQ5wOtXquc3NH9ZAzfKpBGmpcbN8Jdb5LBMTmHI6wo5QBUBoIrCabD\n" +
                 "1DwUAb0u23iF+chi0fh7FH0XGHnysWaQ0vWoV9UldINadc32vWIyEN94w9j0Ae68iIvBnpwEMNVQ\n" +
                 "S45Y2+NOUet+xPGKIRIp9sLaHK7m5V1HufGnMaVEo6T1UeBlOHtdED0hNrGfLHwpk9ABML+2owmD\n" +
                 "hdSdLT0gDEtxl7Ih3jgN1HheTpSZw8tTIE53RUpFcHYequCV/jCCz1vSrLhnw2MYXKyCxfY2zqDO\n" +
                 "a+xx9XG9px8WjaAIVp6WQvFjMU9gav2wqD31Ljzk+VZ9x0LYJiK8l2CPvai/ayNrozBzPVxf8wG9\n" +
                 "PIL67T9ynXhzFfDV57zHS4Asqy63BKNslJ54kKREEAi3fPfkT3wr/aCfap5NI+dyYuvjJX4q7h8V\n" +
                 "R8WmE/wpWwM2NxYBzGwcxEwnQtscGuJd/Z8eLt6t9bU5tY9gS9kNgCyrLrcEo2yUnniQpEQQCLd8\n" +
                 "9+RPfCv9hP5o/iq5kxcXZ09oPc2FK8AkR9zOEmEpIH6PcTBcS/oYevHPiL1P9cBOoENAzV3eELfe\n" +
                 "P3Aii93DsqknrUfnGDz0znssqt/N47l1VVQulbRJMn8d965pA1BHMwNbzDH3y/9PLGGE8TakEFxa\n" +
                 "enE9meZMCB0KhWT5CTbsrV3vxHYybZY/eWq1QKLUHD7eNtQntEKAWq/2a8wzxiRdhu1hG5SeeJCk\n" +
                 "RBAIt3z35E98K/1bxmDOUmJqVRwxxRJ1KFxR7MWwHHGowU0u+Q3lxKps+tmMtxqlwXSaepKfEqqm\n" +
                 "uuAdYRjx66IpHT9ONO1bv86gzuTu+h7AoPhNUi6xOZBU/hwxxRJ1KFxRQCcqBg869STr/Zps18yC\n" +
                 "BvBsq+ZIHz54f1Y/du7hULWBC67WaMUhLdYMWtOGv3kCzGwHXwjOaIbp/XCV752wwYLF9jbOoM5r\n" +
                 "7HH1cb2nHxYmmksOyxCkX2Lr4yV+Ku4fnXjo08HF5bqY17NImiMRnQCU8Dc915WUJ4lUzYJ1KbLl\n" +
                 "DydJGLrIw7D0WP8YBAf0dRITKz5k50678hwPDV9MS1utVvIUSBWM7HH1cb2nHxaI73LKnUKHz0/N\n" +
                 "1mD8ZiPVED55+DXMFXClfBvLXmK3Ehbb3yflAzW3PAPdqKm4vFo=";
         String decode= testdes.DESUtils.getEncryptString(msg,"mykey");
        System.out.print(decode);
        String decode2= DESUtils.getDecryptString(msg2,"mykey");
         System.out.print(decode2);

    }


}
