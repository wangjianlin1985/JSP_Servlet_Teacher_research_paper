// 
// 
// 

package test;

public class SplitString
{
    public static void main(final String[] args) {
        final String a = "a/b/c/";
        final String[] tempStrings = a.split("/");
        for (int i = 0; i < tempStrings.length; ++i) {
            System.out.println(String.valueOf(tempStrings[i]) + "aa");
        }
    }
}
