package com.example.dictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Boggle {
    Set<String> result = new HashSet<String>();

    public List<String> findWords(char[][] board, Trie trie) {

        int m=board.length;
        int n=board[0].length;

        boolean[][] visited = new boolean[m][n];

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                dfs(board, visited, "", i, j, trie);
            }
        }

        return new ArrayList<String>(result);
    }

    void dfs(char[][] board, boolean[][] visited, String str, int i, int j, Trie trie){
        int m=board.length;
        int n=board[0].length;

        if(i<0 || j<0||i>=m||j>=n){
            return;
        }

        if(visited[i][j])
            return;

        str = str + board[i][j];

        if(!trie.searchPrefix(str))
            return;

        if(trie.search(str)){
            result.add(str);
        }

        visited[i][j]=true;
        dfs(board, visited, str, i-1, j, trie);
        dfs(board, visited, str, i+1, j, trie);
        dfs(board, visited, str, i, j-1, trie);
        dfs(board, visited, str, i, j+1, trie);
        dfs(board, visited, str, i+1, j+1, trie);
        dfs(board, visited, str, i-1, j-1, trie);
        dfs(board, visited, str, i-1, j+1, trie);
        dfs(board, visited, str, i+1, j-1, trie);
        visited[i][j]=false;
    }
}
