package com.dark.androidbox.Services.codeEditor;

import android.graphics.Color;

import com.amrdeveloper.codeview.CodeView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Editor {
    public CodeView txtCode;

    Pattern classPattern = Pattern.compile("(public|private|protected)?\\s*(class)\\s+(\\w+)");
    Pattern functionPattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s*(\\(.*?\\))");
    Pattern variablePattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+(\\w+)(\\s*=.*?)?;");


    public Editor(CodeView codeView) {
        this.txtCode = codeView;
    }

    public void setUp(){

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
        txtCode.addSyntaxPattern(classPattern, Color.parseColor("#523565"));
        txtCode.addSyntaxPattern(functionPattern, Color.parseColor("#634736"));
        txtCode.addSyntaxPattern(variablePattern, Color.parseColor("#634538"));

    }
}
