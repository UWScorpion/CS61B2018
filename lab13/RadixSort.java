import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        //get the maxlenght of the string
        int max = 0;
        for (String str: asciis){
            max = Math.max(max, str.length());
        }
        for (int i = max-1; i >= 0; i--) {
            sortHelperLSD(asciis, i);
        }
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] cnt = new int[256];
        for (int i = 0; i< asciis.length; i++){
            if (asciis[i].length() - 1 < index){
                cnt[0]++;
            }else {
                int key = (int) asciis[i].charAt(index);
                cnt[key]++;
            }
        }
        int[] starts = new int[256];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += cnt[i];
        }

//        // add up the counts
//        for (int i = 1; i < 256; i++){
//            cnt[i] += cnt[i-1];
//        }
        String[] output = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++){
            if (asciis[i].length() - 1 < index){
                int place = starts[0];
                output[place] = asciis[i];
                starts[0] += 1;
            }else {
                int place = starts[asciis[i].charAt(index)];
                output[place] = asciis[i];
                starts[asciis[i].charAt(index)] += 1;
            }
        }
        System.arraycopy(output, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args){
        String[] strs = new String[]{"caa!a", "$%^abs", "1bce", "aghj", "b453", "chiu", "dsd234fs4535@e"};
        String[] newstr = RadixSort.sort(strs);
        System.out.println(Arrays.toString(newstr));
    }
}
