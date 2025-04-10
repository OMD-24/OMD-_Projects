package Contest_181;

import java.util.Scanner;

public class Q1 {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int a,b,c,d;
        int x=0;
        int y=0;
        a = sc.nextInt();
        b = sc.nextInt();
        c = sc.nextInt();
        d = sc.nextInt();



        x = a-c;
        y = b-d;

        System.out.println(x +","+ y);

    }

}
