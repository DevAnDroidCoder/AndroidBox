package com.dark.androidbox.builder;

import android.util.Log;

import com.dark.androidbox.Managers.CodeManager.CodeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicBuilder {
    public int objType = 0;
    private String codeString;
    private ArrayList<String> classes;
    private ArrayList<String> functions;


    //    0 = Class
//    1 = Methods
//    2 = Variables
    private ArrayList<String> variables;

    public LogicBuilder(String codeString) {
        this.codeString = codeString;
        this.classes = new ArrayList<String>();
        this.functions = new ArrayList<String>();
        this.variables = new ArrayList<String>();

        extractClasses();
        extractFunctions();
        extractVariables();
    }

    private void extractClasses() {
        Pattern classPattern = Pattern.compile("(public|private|protected)?\\s*(class)\\s+(\\w+)");
        Matcher matcher = classPattern.matcher(codeString);
        while (matcher.find()) {
            classes.add(matcher.group(3));
            objType = 0;
        }
    }

    private void extractFunctions() {
        Pattern functionPattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s*(\\(.*?\\))");
        Matcher matcher = functionPattern.matcher(codeString);
        while (matcher.find()) {
            functions.add(matcher.group(3));
            objType = 1;
        }
    }

    private void extractVariables() {
        Pattern variablePattern = Pattern.compile("(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+(\\w+)(\\s*=.*?)?;");
        Matcher matcher = variablePattern.matcher(codeString);
        while (matcher.find()) {
            variables.add(matcher.group());
            objType = 2;
        }
    }

    public String getCodeClass(String className) {
        String patternString = "(public|private|protected)?\\s*(class)\\s+" + className + "\\s*(\\{.*?\\})";
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(codeString);
        if (matcher.find()) {
            return matcher.group(3);
        }
        return "";
    }

    public String getFunctionInfo(String functionName) {
        String patternString = "(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+" + functionName + "\\s*(\\(.*?\\))\\s*(\\{.*?\\})";
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(codeString);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    public ArrayList<String> getFunctionInput(String functionName) {
        ArrayList<String> inputList = new ArrayList<String>();
        String patternString = "(public|private|protected)?\\s*(static)?\\s*(\\w+)\\s+" + functionName + "\\s*(\\(.*?\\))\\s*(\\{.*?\\})";
        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(codeString);
        if (matcher.find()) {
            String inputString = matcher.group(4);
            Pattern inputPattern = Pattern.compile("(\\w+)\\s+(\\w+)");
            Matcher inputMatcher = inputPattern.matcher(inputString);
            while (inputMatcher.find()) {
                inputList.add(inputMatcher.group(2));
            }
        }
        return inputList;
    }

    public String getClassExtends(String className) {
        Pattern classPattern = Pattern.compile("(public|private|protected)?\\s*(class)\\s+" + className + "\\s+extends\\s+(\\w+)");
        Matcher matcher = classPattern.matcher(codeString);
        if (matcher.find()) {
            Log.d("System Info Get Extends", matcher.group(3));
            return matcher.group(3);
        } else {
            Log.d("System Info Get Extends", "Null");
            return null;
        }
    }

//    public List<String> getClassImplements(String className) {
//        Pattern classPattern = Pattern.compile("(public|private|protected)?\\s*(class)\\s+" + className + "\\s+implements\\s+(\\w+(\\s*,\\s*\\w+)*)");
//        Matcher matcher = classPattern.matcher(codeString);
//        if (matcher.find()) {
//            String interfaceList = matcher.group(3);
//            Log.d("System Info Get Extends", matcher.group(3));
//            return Arrays.asList(interfaceList.split("\\s*,\\s*"));
//        } else {
//            Log.d("System Info Get Extends", "None !");
//            return Collections.emptyList();
//        }
//    }

    public List<String> getClassImplements() {
        Pattern implementsPattern = Pattern.compile("implements\\s+(\\w+(\\s*,\\s*\\w+)*)");
        Matcher matcher = implementsPattern.matcher(codeString);
        if (matcher.find()) {
            String interfaceList = matcher.group(1);
            Log.d("System Info Get Extends", matcher.group(1));
            return Arrays.asList(interfaceList.split("\\s*,\\s*"));
        } else {
            Log.d("System Info Get Extends", "None !");
            return Collections.emptyList();
        }
    }


    public ArrayList<String> getClasses() {
        return classes;
    }

    public ArrayList<String> getFunctions() {
        return functions;
    }

    public ArrayList<String> getVariables() {
        return variables;
    }

    public StringBuilder ObjectGenerator(CodeManager manager) {
        manager.setUpCode();
        return manager.getCode();
    }
}
