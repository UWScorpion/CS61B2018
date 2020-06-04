import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    //Huffman trie Node
    private static class Node implements Comparable<Node>, Serializable{
        private final char ch;
        private final int freq;
        private final Node left, right;
        Node(char ch, int freq, Node left, Node right){
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        private boolean isLeaf(){
            return (left == null)&&(right == null);
        }
        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
    private Node root;
    public BinaryTrie(Map<Character, Integer> frequencyTable){
        root = buildTrie(frequencyTable);

    }
    private static Node buildTrie(Map<Character, Integer> frequencyTable){
        //build the Huffman trie using the MinPQ
        MinPQ<Node> pq = new MinPQ();
        for (Character key: frequencyTable.keySet()){
            pq.insert(new Node(key, frequencyTable.get(key), null, null));
        }
        while(pq.size() > 1){
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence){
        Node curr = root;
        int i = 0;
        for (; i < querySequence.length() && !curr.isLeaf(); i++){
            if (querySequence.bitAt(i) == 0){
                curr = curr.left;
            }else{
                curr = curr.right;
            }
        }
        if (i > querySequence.length()) {
            return null;
        }else{
            return new Match(querySequence.firstNBits(i), curr.ch);
        }
    }

    public Map<Character, BitSequence> buildLookupTable(){
        Map<Character, BitSequence> res = new HashMap<>();
        find(root, res, "");
        return res;
    }

    private void find(Node curr, Map<Character, BitSequence> res, String s){
        if (curr == null){
            return;
        }
        if (curr.isLeaf()){
            res.put(curr.ch, new BitSequence(s));
        }
        find (curr.left, res, s + '0');
        find(curr.right, res, s + '1');
    }
}
