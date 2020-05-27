/** A class for palindrome operations*/
public class Palindrome{

    /**Given a String, wordToDeque should
     * return a Deque where the characters
     * appear in the same order as in the String.
     * For example, if the word is “persiflage”,
     * then the returned Deque should have ‘p’ at the front,
     * followed by ‘e’, and so forth. */

    public Deque<Character> wordToDeque(String word){
        Deque<Character> res = new LinkedListDeque();
        for (char c: word.toCharArray()){
            res.addLast(c);
        }
        return res;
    }
    /**The isPalindrome method should return true if the given word is a palindrome,
     * and false otherwise. A palindrome is defined as a word that is the same
     * whether it is read forwards or backwards.
     * For example “a”, “racecar”, and “noon” are all palindromes.
     * “horse”, “rancor”, and “aaaaab” are not palindromes.
     * Any word of length 1 or 0 is a palindrome.
     * ‘A’ and ‘a’ should not be considered equal.
     * You don’t need to do anything special for capital letters to work properly.
     * In fact, if you forget that capital letters exist, your code will work fine.*/
    public boolean isPalindrome(String word){
        if (word.length() == 0 || word.length() == 1){
            return true;
        }
        Deque<Character> chars = this.wordToDeque(word);
        while(chars.size() > 1){
            if (chars.removeFirst() != chars.removeLast()){
                return false;
            }
        }
        return true;
    }

    /**The method will return true if the word is a palindrome
     * according to the character comparison test provided by the CharacterComparator
     * passed in as argument cc. A character comparator is defined as shown below:*/

    /***/
    public boolean isPalindrome(String word, CharacterComparator cc){
        if (word.length() == 0 || word.length() == 1){
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        while(deque.size() > 1) {
            if(!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
