
import java.util.Scanner;

public class Exam_08Pr {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int number;
        String and, or, result;
        boolean four, five, onlyOne;

        System.out.println("정수입력: ");
        number = sc.nextInt();

        four = number%4 ==0;
        five = number%5 ==0;

        onlyOne = (four && !five) || (!four && five) ? true : false;

        and = four && five ? "4와 5로 나누어집니다":"4와 5로 나누어지지않습니다";
        
        or = four || five ? "4 또는 5로 나누어집니다" : "4또는 5로 나누어지지않습니다.";

        System.out.println(number);
        
      
    }
    
}
