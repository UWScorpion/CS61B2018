import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> res = new HashMap<>();
        for (char c: inputSymbols){
            res.put(c, res.getOrDefault(c, 0)+1);
        }
        return res;
    }

    public static void main(String[] args){
        /**
         * 1: Read the file as 8 bit symbols.
         2: Build frequency table.
         3: Use frequency table to construct a binary decoding trie.
         4: Write the binary decoding trie to the .huf file.
         5: (optional: write the number of symbols to the .huf file)
         6: Use binary trie to create lookup table for encoding.
         7: Create a list of bitsequences.
         8: For each 8 bit symbol:
         Lookup that symbol in the lookup table.
         Add the appropriate bit sequence to the list of bitsequences.
         9: Assemble all bit sequences into one huge bit sequence.
         10: Write the huge bit sequence to the .huf file.
         */

        char[] files = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = HuffmanEncoder.buildFrequencyTable(files);
        BinaryTrie decoTrie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(decoTrie);
        Map<Character, BitSequence> lookup = decoTrie.buildLookupTable();
        List<BitSequence> sequences = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            sequences.add(lookup.get(files[i]));
        }
        BitSequence newfile = BitSequence.assemble(sequences);
        ow.writeObject(newfile);
    }
}
