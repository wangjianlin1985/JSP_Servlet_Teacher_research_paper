// 
// 
// 

package test;

public class RandomT
{
    public static void main(final String[] args) {
        for (int i = 0; i < 10; ++i) {
            final int rd = (int)((Math.random() * 9.0 + 1.0) * 10000.0);
            String str = "abc";
            str = String.valueOf(str) + rd;
            System.out.println(str);
        }
    }
}
