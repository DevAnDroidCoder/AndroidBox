package com.dark.androidbox.Editor;

import android.content.Context;
import android.graphics.Color;

import com.amrdeveloper.codeview.CodeView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Editor {
    public CodeView txtCode;
    EditorAdapter adapter;
    Pattern defaultChar = Pattern.compile("(class|void|public|private|protected|static|final|new|return|int|short|long|float|double|boolean|if|else|for|while|do|switch|case|break|continue|try|catch|finally|throw|throws|interface|extends|implements|package|import)");
    Pattern objChar = Pattern.compile("(String|Array|ArrayList)");
    Pattern symbolChar = Pattern.compile("(String|Array|ArrayList)");
    Pattern variablePattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+(\\w+)(\\s*=.*?)?;");


    public Editor(Context context, CodeView codeView) {
        this.txtCode = codeView;
        this.adapter = new EditorAdapter(context, codeView);
    }

    public void setUp() {

        //setup number line
        txtCode.setEnableLineNumber(true);
        txtCode.setEnableRelativeLineNumber(true);

        //Add Auto Complete words
        Map<Character, Character> pairCompleteMap = new HashMap<>();
        pairCompleteMap.put('{', '}');
        pairCompleteMap.put('[', ']');
        pairCompleteMap.put('(', ')');
        pairCompleteMap.put('<', '>');
        pairCompleteMap.put('"', '"');

        txtCode.enablePairComplete(true);
        txtCode.setPairCompleteMap(pairCompleteMap);


        //Add Syntax highlighter
        txtCode.addSyntaxPattern(defaultChar, Color.parseColor("#6b518f"));
        txtCode.addSyntaxPattern(objChar, Color.parseColor("#9e9d4c"));
        txtCode.addSyntaxPattern(variablePattern, Color.parseColor("#634538"));
        txtCode.addSyntaxPattern(symbolChar, Color.parseColor("#51848f"));

        //Add Suggestions an Auto Complete
        adapter.AddSnippet("getString", "public void String getString() { \n return null; \n}");
        adapter.Refresh();

    }

    public void setDynamicString(StringBuilder rexTxt, String color){
        try {
            Pattern pattern = Pattern.compile(String.valueOf(rexTxt));
            txtCode.addSyntaxPattern(pattern, Color.parseColor(color));
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }
}
