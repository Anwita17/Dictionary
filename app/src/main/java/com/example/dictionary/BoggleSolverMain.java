package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoggleSolverMain extends AppCompatActivity {
    EditText[][] et= new EditText[4][4];
    private final int M = 4;
    private final int N = 4;
    private char[][] mBoggle= new char[M][N];
    Trie trie = new Trie();
    ArrayList<String> mWordsPossible;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boggle_solver_main);
        loadData();

        et[0][0] = (EditText) findViewById(R.id.a11);
        et[0][1] = (EditText) findViewById(R.id.a12);
        et[0][2] = (EditText) findViewById(R.id.a13);
        et[0][3] = (EditText) findViewById(R.id.a14);
        et[1][0] = (EditText) findViewById(R.id.a21);
        et[1][1] = (EditText) findViewById(R.id.a22);
        et[1][2] = (EditText) findViewById(R.id.a23);
        et[1][3] = (EditText) findViewById(R.id.a24);
        et[2][0] = (EditText) findViewById(R.id.a31);
        et[2][1] = (EditText) findViewById(R.id.a32);
        et[2][2] = (EditText) findViewById(R.id.a33);
        et[2][3] = (EditText) findViewById(R.id.a34);
        et[3][0] = (EditText) findViewById(R.id.a41);
        et[3][1] = (EditText) findViewById(R.id.a42);
        et[3][2] = (EditText) findViewById(R.id.a43);
        et[3][3] = (EditText) findViewById(R.id.a44);

        et[0][0].requestFocus();
        showKeyboard();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int next_i, next_j, prev_i, prev_j;
                next_j = (j + 1) % 4;
                next_i = j == 3 ? i + 1 : i;
                prev_j = (j - 1 +4) % 4;
                prev_i = j == 0 ? i - 1 : i;
                Log.d("mTag",prev_i+","+prev_j+"  "+i+","+j+"  "+next_i+","+next_j);
                final int finalJ = j;
                if(i==0 && j==0)
                    et[i][j].addTextChangedListener(new GenericTextWatcher(this,null, et[i][j], et[next_i][next_j]));
                else if(i==3 && j==3)
                    et[i][j].addTextChangedListener(new GenericTextWatcher(this,et[prev_i][prev_j], et[i][j], null));
                else {
                    et[i][j].setOnKeyListener(new DeleteKeyListner(et[prev_i][prev_j], et[i][j], et[next_i][next_j]));
                    et[i][j].addTextChangedListener(new GenericTextWatcher(this,et[prev_i][prev_j], et[i][j], et[next_i][next_j]));
                }
            }
        }
    }
    void loadData(){
        BufferedReader reader ;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));
            int flag=0,counter=0;
            String mWord;
            while ((mWord = reader.readLine()) != null) {
                mWord=mWord.toLowerCase();
                trie.insert(mWord);
            }
            Log.d("mTag","Data loaded");

        }catch (IOException e) {
            Toast.makeText(this,"HelloException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    public void onSubmitBoard(View view){
        closeKeyboard();
        for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                mBoggle[i][j]=et[i][j].getText().toString().toLowerCase().charAt(0);
            }
        }
        /*for(int i=0;i<M;i++){
            for(int j=0;j<N;j++){
                Log.d("mBoard",mBoggle[i][j]+"");
            }
        }*/
        trie.findWords(mBoggle);
        Map<String,boolean[][]> mBoardSolve=trie.getBoggleSolve();
        int count=0;
        mWordsPossible=new ArrayList<String >();
        for(String words:mBoardSolve.keySet())
            mWordsPossible.add(words);
        /*Log.d("mWords",count+"");
        boolean[][] mboard = mBoardSolve.get("dote");
        Log.d("mWords",mboard.length+","+mboard[0].length);*/

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.pop_up, null);
        TextView mTotalWords = popupView.findViewById(R.id.totalText);
        mTotalWords.setText("Total "+mWordsPossible.size()+" Words are possible");
        expListView =(ExpandableListView)popupView.findViewById(R.id.lvExp);
        prepareListData();
        listAdapter= new ExpandableListAdapter(this,listDataHeader,listDataChild);
        expListView.setAdapter(listAdapter);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        Button dismiss=(Button)popupView.findViewById(R.id.tryagain);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                showKeyboard();
            }
        });
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        if (mWordsPossible.size() > 0) {
            Comparator c = new Comparator<String>() {
                public int compare(String s1, String s2) {
                    return Integer.compare(s2.length(), s1.length());
                }
            };
            Collections.sort(mWordsPossible, c);
            int currLength = mWordsPossible.get(0).length();
            List<String> temp = new ArrayList<String>();
            int start = 0;

            for (int i = 0; i < mWordsPossible.size(); i++) {
                if (currLength == mWordsPossible.get(i).length()) {
                    // if currLength is equal to current word length just append a comma and this word
                } else {
                    listDataHeader.add(currLength + " LETTER WORDS");
                    listDataChild.put(currLength + " LETTER WORDS", mWordsPossible.subList(start, i));
                    start = i;
                    // if not update currLength, jump to a new line and print new length with the current word
                    currLength = mWordsPossible.get(i).length();
                    //temp.add(word);
                    //System.out.println();
                    //System.out.print(currLength+ " - "+word);
                }
            }
            listDataHeader.add(currLength + " LETTER WORDS");
            listDataChild.put(currLength + " LETTER WORDS", mWordsPossible.subList(start, mWordsPossible.size()));
            for (String name : listDataChild.keySet()) {
                // search  for value
                List<String> url = listDataChild.get(name);
                Log.d("TAG", "Key = " + name + ", Value = " + url.toString());
            }
        }
    }
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}
class GenericTextWatcher implements TextWatcher {
    private EditText view,prev,next;
    private Activity activity;
    private Button mbutton;
    public GenericTextWatcher(Activity activity, EditText prev, EditText view, EditText next) {
        this.view = view;
        this.prev = prev;
        this.next = next;
        this.activity=activity;
        mbutton =activity.findViewById(R.id.submit_button);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Log.d("mTag","call");
        String text=editable.toString();
        if(text.equals(" ")||text.equals("\n")) {
            view.setText("");
            view.requestFocus();
            return;
        }
        if(text.length()==1) {
            if(next!=null)
                next.requestFocus();
            else
                mbutton.setEnabled(true);

        }
        if(text.length()==0 && prev!=null) {
            prev.requestFocus();
            if(mbutton.isEnabled())
                mbutton.setEnabled(false);
        }
    }
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

}
class DeleteKeyListner implements View.OnKeyListener {
    private EditText cur,prev,next;
    public DeleteKeyListner(EditText prev,EditText cur, EditText next) {
        this.cur = cur;
        this.prev = prev;
        this.next = next;
    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i== KeyEvent.KEYCODE_DEL){
            prev.requestFocus();
        }
        /*else if(i==KeyEvent.KEYCODE_SPACE){
            cur.setText("");
            cur.requestFocus();
        }
        else{
            next.requestFocus();
        }*/
        return false;
    }
}
