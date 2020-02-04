package com.example.dictionary;

class TrieNode{
    TrieNode children[];
    boolean isLeaf;
    public TrieNode(){
        this.children=new TrieNode[26];
    }
}

public class Trie {

    private TrieNode root;
    public Trie(){
        root=new TrieNode();
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
}
