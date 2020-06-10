import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AugmentedStreetMapGraph{
    private class TrieNode{
        TrieNode[] children;
        String word;
        boolean isWord;
        public TrieNode(){
            children = new TrieNode[27];
            isWord = false;
        }
    }
    private String prefix;
    private HashSet<String> locations;
    private TrieNode root;
    public AugmentedStreetMapGraph(String prefix, HashSet<String> locations){
        this.prefix = prefix;
        this.locations = locations;
        root = new TrieNode();
    }
    private void insert(String word){
        if (word == null || word.length() == 0){
            return;
        }
        TrieNode curr = root;
        for (char ch: word.toCharArray()){
            if (ch == ' '){
                curr.children[26] = new TrieNode();
                curr = curr.children[26];
            }else {
                if (curr.children[ch - 'a'] == null) {
                    curr.children[ch - 'a'] = new TrieNode();
                }
                curr = curr.children[ch - 'a'];
            }
        }
        curr.word = word;
        curr.isWord = true;
    }
    private List<String> search(String prefix){
        List<String> res1 = new ArrayList<>();
        TrieNode curr = root;
        for (char ch: prefix.toCharArray()){
            if (ch == ' '){
                curr = curr.children[26];
            }else if (curr.children[ch - 'a'] != null){
                curr = curr.children[ch - 'a'];
            }else{
                return null;
            }
        }

        searchHelper(res1, curr);
        return res1;
    }
    private void searchHelper( List<String> res1, TrieNode curr){
        if (curr == null){
            return;
        }
        if (curr.isWord){
            res1.add(curr.word);
        }
        for (int i = 0; i < 27; i++){
            if (curr.children[i] != null){
                searchHelper(res1, curr.children[i]);
            }
        }
    }

    List<String> getLocationList(){

        for (String s: locations){
            insert(s);
        }
        return search(prefix);
    }
    public static void main(String [] args){
        HashSet<String> locations = new HashSet<>();
        locations.add("beijing");
        locations.add("beisdasdg");
        locations.add("asdsdasdg");
        locations.add("asdg");
        locations.add("sdsg");
        locations.add("abebe");
        locations.add("be");
        AugmentedStreetMapGraph ASG = new AugmentedStreetMapGraph("be", locations);
        List<String> rs = ASG.getLocationList();
        System.out.println(rs);
        System.out.println(GraphDB.cleanString("as233fr54gszxcAAsd"));
    }
}