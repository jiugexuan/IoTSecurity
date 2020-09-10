package testmd5;

public class testmd5 {
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
        String m1 = MD5Util.md5(msg);
        System.out.println("m1="+m1);
    }
}
