package com.dark.androidbox.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dark.androidbox.Adpaters.CodeAdapter;
import com.dark.androidbox.Adpaters.Codes;
import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.dark.androidbox.builder.LogicBuilder;
import com.google.android.material.button.MaterialButton;
import com.gyso.treeview.GysoTreeView;
import com.gyso.treeview.TreeViewEditor;
import com.gyso.treeview.layout.BoxDownTreeLayoutManager;
import com.gyso.treeview.layout.BoxRightTreeLayoutManager;
import com.gyso.treeview.layout.CompactRightTreeLayoutManager;
import com.gyso.treeview.layout.TableRightTreeLayoutManager;
import com.gyso.treeview.layout.TreeLayoutManager;
import com.gyso.treeview.line.AngledLine;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.line.SmoothLine;
import com.gyso.treeview.model.NodeModel;
import com.gyso.treeview.model.TreeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class EditorFragment extends Fragment implements NodeEvents {

    public GysoTreeView treeView;
    public TreeViewEditor editor;

    public MaterialButton code, node;
    public LogicBuilder builder = new LogicBuilder(sampleCode());
    CodeAdapter adapter;
    TreeLayoutManager treeLayoutManager;
    private NodeModel<Codes> parentToRemoveChildren = null;
    private NodeModel<Codes> targetNode;

    public EditorFragment() {}

    //For Testing Purposes Only
    public static String sampleCode() {
        return "import java.util.ArrayList;\n" +
                "import java.util.regex.Matcher;\n" +
                "import java.util.regex.Pattern;\n" +
                "\n" +
                "public class JavaCodeParser extends Fragment implements NodeEvents, Siddhi, Data, Events {\n" +
                "    private String codeString;\n" +
                "    private ArrayList<String> classes;\n" +
                "    private ArrayList<String> functions;\n" +
                "    private ArrayList<String> variables;\n" +
                "    \n" +
                "    public JavaCodeParser(String codeString) {\n" +
                "        this.codeString = codeString;\n" +
                "        this.classes = new ArrayList<String>();\n" +
                "        this.functions = new ArrayList<String>();\n" +
                "        this.variables = new ArrayList<String>();\n" +
                "        \n" +
                "        extractClasses();\n" +
                "        extractFunctions();\n" +
                "        extractVariables();\n" +
                "    }\n" +
                "    \n" +
                "    private void extractClasses() {\n" +
                "        Pattern classPattern = Pattern.compile(\"(public|private|protected)?\\\\s*(class)\\\\s+(\\\\w+)\");\n" +
                "        Matcher matcher = classPattern.matcher(codeString);\n" +
                "        while (matcher.find()) {\n" +
                "            classes.add(matcher.group(3));\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    private void extractFunctions() {\n" +
                "        Pattern functionPattern = Pattern.compile(\"(public|private|protected)?\\\\s*(static)?\\\\s*(\\\\w+)\\\\s*(\\\\(.*?\\\\))\");\n" +
                "        Matcher matcher = functionPattern.matcher(codeString);\n" +
                "        while (matcher.find()) {\n" +
                "            functions.add(matcher.group(3));\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    private void extractVariables() {\n" +
                "        Pattern variablePattern = Pattern.compile(\"(public|private|protected)?\\\\s*(static)?\\\\s*(\\\\w+)\\\\s+(\\\\w+)(\\\\s*=.*?)?;\");\n" +
                "        Matcher matcher = variablePattern.matcher(codeString);\n" +
                "        while (matcher.find()) {\n" +
                "            variables.add(matcher.group(4));\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    public String getCodeClass(String className) {\n" +
                "        String patternString = \"(public|private|protected)?\\\\s*(class)\\\\s+\" + className + \"\\\\s*(\\\\{.*?\\\\})\";\n" +
                "        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);\n" +
                "        Matcher matcher = pattern.matcher(codeString);\n" +
                "        if (matcher.find()) {\n" +
                "            return matcher.group(3);\n" +
                "        }\n" +
                "        return \"\";\n" +
                "    }\n" +
                "    \n" +
                "    public String getFunctionInfo(String functionName) {\n" +
                "        String patternString = \"(public|private|protected)?\\\\s*(static)?\\\\s*(\\\\w+)\\\\s+\" + functionName + \"\\\\s*(\\\\(.*?\\\\))\\\\s*(\\\\{.*?\\\\})\";\n" +
                "        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);\n" +
                "        Matcher matcher = pattern.matcher(codeString);\n" +
                "        if (matcher.find()) {\n" +
                "            return matcher.group(0);\n" +
                "        }\n" +
                "        return \"\";\n" +
                "    }\n" +
                "    \n" +
                "    public ArrayList<String> getFunctionInput(String functionName) {\n" +
                "        ArrayList<String> inputList = new ArrayList<String>();\n" +
                "        String patternString = \"(public|private|protected)?\\\\s*(static)?\\\\s*(\\\\w+)\\\\s+\" + functionName + \"\\\\s*(\\\\(.*?\\\\))\\\\s*(\\\\{.*?\\\\})\";\n" +
                "        Pattern pattern = Pattern.compile(patternString, Pattern.DOTALL);\n" +
                "        Matcher matcher = pattern.matcher(codeString);\n" +
                "        if (matcher.find()) {\n" +
                "            String inputString = matcher.group(4);\n" +
                "            Pattern inputPattern = Pattern.compile(\"(\\\\w+)\\\\s+(\\\\w+)\");\n" +
                "            Matcher inputMatcher = inputPattern.matcher(inputString);\n" +
                "            while (inputMatcher.find()) {\n" +
                "                inputList.add(inputMatcher.group(2));\n" +
                "            }\n" +
                "        }\n" +
                "        return inputList;\n" +
                "    }\n" +
                "    \n" +
                "    public ArrayList<String> getClasses() {\n" +
                "        return classes;\n" +
                "    }\n" +
                "    \n" +
                "    public ArrayList<String> getFunctions() {\n" +
                "        return functions;\n" +
                "    }\n" +
                "    \n" +
                "    public ArrayList<String> getVariables() {\n" +
                "        return variables;\n" +
                "   \n";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editor, container, false);

        treeView = root.findViewById(R.id.base_tree_view);

        code = root.findViewById(R.id.code);
        node = root.findViewById(R.id.node);

        initNODE();
        Logic();

        return root;
    }

    public void Logic() {

        code.setOnClickListener(v -> {

        });

        node.setOnClickListener(view -> {

        });

    }

    public void initNODE() {
        adapter = new CodeAdapter(requireActivity().getSupportFragmentManager(), getActivity(), this);

        treeLayoutManager = getTreeLayoutManager();

        treeView.setAdapter(adapter);
        treeView.setTreeLayoutManager(treeLayoutManager);

        setData(adapter);

        editor = treeView.getEditor();

        editor.requestMoveNodeByDragging(true);
    }

    private TreeLayoutManager getTreeLayoutManager() {
        int space_50dp = 30;
        int space_20dp = 20;
        BaseLine line = getLine();
        //return new BoxRightTreeLayoutManager(getContext(),space_50dp,space_20dp,line);
        //return new BoxDownTreeLayoutManager(getContext(),space_50dp,space_20dp,line);
        //return new BoxLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);


        //TODO !!!!! the layoutManagers below are just for test don't use in your projects. Just for test now
        return new TableRightTreeLayoutManager(getContext(),space_50dp,space_20dp,line);
        //return new TableLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableDownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new TableVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);

        //return new CompactRightTreeLayoutManager(getContext(), space_50dp, space_20dp, line);
        //return new CompactLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactDownTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new CompactVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);

        //return new CompactRingTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new ForceDirectedTreeLayoutManager(this,line);
    }

    private BaseLine getLine() {
        //return new SmoothLine(Color.parseColor("#8EBBFF"), 2);
        //return new StraightLine(Color.parseColor("#055287"),2);
        //return new DashLine(Color.parseColor("#F1286C"),3);
        return new AngledLine();
    }

    private void setData(CodeAdapter adapter) {

        NodeModel<Codes> functionSample = null;
        NodeModel<Codes> rootVariables;
        //root



        for (int i = 0; i < builder.getVariables().size(); i++) {

        }

        NodeModel<Codes> classSample = new NodeModel<>(new Codes(0, builder.getClasses().get(0)));

        TreeModel<Codes> treeModel = new TreeModel<>(classSample);

        //child nodes
        NodeModel<Codes> varSample = new NodeModel<>(new Codes(2, builder.getVariables().get(0)));

        int cac = 0;

        for (int i = 0; i < builder.getFunctions().size(); i++) {
            if (i == 5){
                break;
            }
            functionSample = new NodeModel<>(new Codes(i, builder.getFunctions().get(i)));
            treeModel.addNode(classSample, functionSample, varSample);
        }
        //build relationship
        //treeModel.addNode(classSample, varSample);

        //mark
        parentToRemoveChildren = classSample;
        targetNode = functionSample;

        //set data
        adapter.setTreeModel(treeModel);
    }

    @Override
    public void NodeOnLongClick() {

    }
}