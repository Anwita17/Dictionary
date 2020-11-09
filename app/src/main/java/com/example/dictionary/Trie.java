package com.example.dictionary;
import android.util.Log;
import android.widget.Toast;

import java.util.*;
class TrieNode{
    TrieNode children[];
    boolean isLeaf;
    public TrieNode(){
        this.children=new TrieNode[26];
    }
}

public class Trie {

    private TrieNode root;
    private String TAG="TEST";
    Map<String, boolean[][]> mBoggleSolver ;

    // Alphabet size
    private final int SIZE = 26;
    private final int M = 4;
    private final int N = 4;

    public Trie(){
        root=new TrieNode();
        mBoggleSolver= new HashMap<String, boolean[][]>();
    }

    public Map<String,boolean[][]> getBoggleSolve(){
        return mBoggleSolver;
    }
    public void insert(String word){
        TrieNode p=root;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            int index=c-'a';
            if(p.children[index]==null){
                TrieNode temp=new TrieNode();
                p.children[index]=temp;
                p=temp;
            }else{
                p=p.children[index];
            }
        }
        p.isLeaf=true;
    }
    public TrieNode searchNode(String word){
        TrieNode p=root;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            int index=c-'a';
            if(p.children[index]==null)
                return null;
            else
                p=p.children[index];
        }
        return p;
    }
    public boolean search(String word){
        TrieNode p=searchNode(word);
        if(p==null){
            return false;
        }else{
            if(p.isLeaf)
                return true;
            else
                return false;
        }
    }
    public boolean searchPrefix(String word){
        TrieNode p=searchNode(word);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }
    String retrieve(){
        Random rand = new Random();
        TrieNode temp=root,p=root;
        int index=rand.nextInt(25),i=0;
        char s;
        String ret="";
        while(true){
            if(p.children[index]!=null)
                temp=p.children[index];
            while(p.children[index]==null){
                index=rand.nextInt(25);
                if(p.children[index]!=null)
                    temp=p.children[index];
                //System.out.print(index);
            }
            i=index+97;
            s=(char)i;
            ret+=Character.toString(s);
            //System.out.println("s");
            //System.out.println(s);
            if(temp.isLeaf) {
                break;
            }
            p=p.children[index];
        }
        return ret;
    }
    //ArrayList<String> result = new ArrayList<>();
    void fillup(TrieNode q,String s,String fixed_string,String letter,Set<String> result){
        int i;
        for(i=0;i<26;i++){
            if(q.children[i]!=null){
                //System.out.println(s);
                letter=Character.toString((char)(i+97));
                //System.out.println(letter);
                //result.push(s);
                s=s+letter;
                if(q.children[i].isLeaf){
                    result.add(s);
                    //System.out.println(s);
                }
                q=q.children[i];
                fillup(q,s,fixed_string,letter,result);
            }
        }
        if(i==26){
            s=fixed_string;
            letter="";
            //System.out.println("Here : "+s);
        }
    }
    public Set<String> suffix(String s) {
        Set<String> result = new HashSet<String>();
        TrieNode p = root,q=root;
        int index=0,flag=0;
        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            index = c-'a';
            if(p.children[index]!=null){
                p = p.children[index];
            }
            else{
                System.out.println("Not present");
                flag=1;
                break;
            }
        }
        if(p.isLeaf)
            result.add(s);
        String tempresult=s;
        //System.out.println(tempresult);
        if(flag==0 && s.length()>=1){
            for(int i=0;i<26;i++){
                while(p.children[i]==null){
                    i++;
                    if(i==26){
                        flag=1;
                        break;
                    }
                }
                if(flag==1)
                    break;
                String letter=Character.toString((char)(i+97));
                //System.out.println(letter);
                tempresult+=letter;
                if((p.children[i]).isLeaf)
                    result.add(tempresult);
                fillup(p.children[i],tempresult,s,letter,result);
                tempresult=s;
            }
        }
        return result;
    }
    private boolean isSafe(int i, int j, boolean visited[][])
    {
        return (i >= 0 && i < M && j >= 0
                && j < N && !visited[i][j]);
    }
    private void searchWord(TrieNode root, char boggle[][], int i,
                           int j, boolean visited[][], String str) {
        // if we found word in trie / dictionary
        if (root.isLeaf) {
            Log.d("mWords", str);
            Log.d("mWords",visited.length+","+visited[0].length);
            mBoggleSolver.put(str,visited);
        }

        // If both I and j in  range and we visited
        // that element of matrix first time
        if (isSafe(i, j, visited)) {
            // make it visited
            visited[i][j] = true;

            // traverse all child of current root
            for (int K = 0; K < SIZE; K++) {
                if (root.children[K] != null) {
                    // current character
                    char ch = (char) (K + 'a');

                    // Recursively search reaming character of word
                    // in trie for all 8 adjacent cells of
                    // boggle[i][j]
                    if (isSafe(i + 1, j + 1, visited)
                            && boggle[i + 1][j + 1] == ch)
                        searchWord(root.children[K], boggle,
                                i + 1, j + 1,
                                visited, str + ch);
                    if (isSafe(i, j + 1, visited)
                            && boggle[i][j + 1] == ch)
                        searchWord(root.children[K], boggle,
                                i, j + 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j + 1, visited)
                            && boggle[i - 1][j + 1] == ch)
                        searchWord(root.children[K], boggle,
                                i - 1, j + 1,
                                visited, str + ch);
                    if (isSafe(i + 1, j, visited)
                            && boggle[i + 1][j] == ch)
                        searchWord(root.children[K], boggle,
                                i + 1, j,
                                visited, str + ch);
                    if (isSafe(i + 1, j - 1, visited)
                            && boggle[i + 1][j - 1] == ch)
                        searchWord(root.children[K], boggle,
                                i + 1, j - 1,
                                visited, str + ch);
                    if (isSafe(i, j - 1, visited)
                            && boggle[i][j - 1] == ch)
                        searchWord(root.children[K], boggle,
                                i, j - 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j - 1, visited)
                            && boggle[i - 1][j - 1] == ch)
                        searchWord(root.children[K], boggle,
                                i - 1, j - 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j, visited)
                            && boggle[i - 1][j] == ch)
                        searchWord(root.children[K], boggle,
                                i - 1, j,
                                visited, str + ch);
                }
            }

            // make current element unvisited
            visited[i][j] = false;
        }
        }
    public void findWords(char boggle[][])
    {
        // Mark all characters as not visited
        boolean[][] visited = new boolean[M][N];
        TrieNode pChild = root;

        String str = "";

        for(int i=0;i<M;i++)
            for(int j=0;j<N;j++)
                visited[i][j]=false;
        // traverse all matrix elements
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // we start searching for word in dictionary
                // if we found a character which is child
                // of Trie root
                if (pChild.children[(boggle[i][j]) - 'a'] != null) {
                    str = str + boggle[i][j];
                    searchWord(pChild.children[(boggle[i][j]) - 'a'],
                            boggle, i, j, visited, str);
                    str = "";
                }
            }
        }
    }
}