import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void testequalChars(){
        boolean test1 = offByOne.equalChars('a', 'b');
        boolean test2 = offByOne.equalChars('a', 'e');
        boolean test3 = offByOne.equalChars('a', 'z');
        boolean test4 = offByOne.equalChars('&', '%');
        assertTrue(test1);
        assertFalse(test2);
        assertFalse(test3);
        assertTrue(test4);
    }
}
