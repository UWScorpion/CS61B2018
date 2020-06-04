import java.util.ArrayList;

public class HuffmanDecoder {



    public static void main(String[] args){
        /**
         1: Read the Huffman coding trie.
         2: If applicable, read the number of symbols.
         3: Read the massive bit sequence corresponding to the original txt.
         4: Repeat until there are no more symbols:
         4a: Perform a longest prefix match on the massive sequence.
         4b: Record the symbol in some data structure.
         4c: Create a new bit sequence containing the remaining unmatched bits.
         5: Write the symbols in some data structure to the specified file.
         */
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie decoTrie = (BinaryTrie) or.readObject();
        BitSequence seq = (BitSequence) or.readObject();
        ArrayList<Character> chars = new ArrayList<>();

        while (seq.length() > 0) {
            Match ch = decoTrie.longestPrefixMatch(seq);
            chars.add(ch.getSymbol());
            seq = seq.allButFirstNBits(ch.getSequence().length());
        }

        char[] words = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            words[i] = chars.get(i);
        }

        FileUtils.writeCharArray(args[1], words);

    }

}
