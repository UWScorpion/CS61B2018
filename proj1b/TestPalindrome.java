import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testisPalindrome(){
        boolean test1 = palindrome.isPalindrome("aba");
        boolean test2 = palindrome.isPalindrome("cat");
        boolean test3 = palindrome.isPalindrome("vbnmghhgmnbv");
        boolean test4 = palindrome.isPalindrome("");
        boolean test5 = palindrome.isPalindrome("a");
        assertTrue(test1);
        assertFalse(test2);
        assertTrue(test3);
        assertTrue(test4);
        assertTrue(test5);
    }
    static OffByOne offbyone = new OffByOne();
    @Test
    public void testNewIsPalindrome(){
        boolean test1 = offbyone.isPalindrome("flake");
        //boolean test2 = offbyone.isPalindrome("cat");
        //boolean test3 = offbyone.isPalindrome("vbnmghhgmnbv");
        boolean test4 = offbyone.isPalindrome("");
        boolean test5 = offbyone.isPalindrome("a");
        assertTrue(test1);
        //assertFalse(test2);
        //assertTrue(test3);
        assertTrue(test4);
        assertTrue(test5);
    }
}
