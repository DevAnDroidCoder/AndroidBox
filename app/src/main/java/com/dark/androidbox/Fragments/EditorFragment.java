package com.dark.androidbox.Fragments;

import static com.dark.androidbox.Utilities.DarkUtilities.ShowMessage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.amrdeveloper.codeview.CodeView;
import com.dark.androidbox.Adpaters.CodeAdapter;
import com.dark.androidbox.Adpaters.NodeListAdapter;
import com.dark.androidbox.Editor.Codes;
import com.dark.androidbox.Editor.Editor;
import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.dark.androidbox.builder.LogicBuilder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textview.MaterialTextView;
import com.gyso.treeview.GysoTreeView;
import com.gyso.treeview.TreeViewEditor;
import com.gyso.treeview.adapter.TreeViewHolder;
import com.gyso.treeview.layout.TableRightTreeLayoutManager;
import com.gyso.treeview.layout.TreeLayoutManager;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.line.SmoothLine;
import com.gyso.treeview.listener.TreeViewControlListener;
import com.gyso.treeview.model.NodeModel;
import com.gyso.treeview.model.TreeModel;

import java.util.ArrayList;

public class EditorFragment extends Fragment implements NodeEvents, TreeViewControlListener {

    public GysoTreeView treeView;
    public TreeViewEditor editor;
    public MaterialButton code, node, focusMid, add;

    public CodeView txtCode;
    public MaterialSwitch dragLock;
    public LogicBuilder builder = new LogicBuilder(sampleCode());

    public MaterialAlertDialogBuilder dialogBuilder;
    public Editor codeEditor;
    public AlertDialog basic_dlgBuilder;
    CodeAdapter adapter;
    NodeListAdapter nodesListAdapter;
    TreeLayoutManager treeLayoutManager;
    TreeModel<Codes> treeModel;
    NodeModel<Codes> rootClass;
    private NodeModel<Codes> parentToRemoveChildren = null;
    private NodeModel<Codes> targetNode;

    public EditorFragment() {
    }

    //For Testing Purposes Only
    public static String sampleCode() {
        return "import java.util.ArrayList;\n" +
                "import java.util.regex.Matcher;\n" +
                "import java.util.regex.Pattern;\n" +
                "\n" +
                "public class JavaCodeParser extends Fragment implements NodeEvents, Data, Events {\n" +
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
                "    public ArrayList<String> getVariables() {\n" +
                "        return variables;\n" +
                "    }\n" +
                "}";
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
        txtCode = root.findViewById(R.id.codeTxt);

        focusMid = root.findViewById(R.id.focusMid);

        add = root.findViewById(R.id.add);

        dragLock = root.findViewById(R.id.drag);

        initNODE();
        Logic();

        dragLock.setChecked(true);

        return root;
    }

    public void Logic() {

        codeEditor = new Editor(getContext(), txtCode);
        codeEditor.setUp();
        codeEditor.setDynamicString(new StringBuilder(rootClass.value.label), "#9e9d4c");

        txtCode.setVisibility(View.GONE);
        treeView.setVisibility(View.VISIBLE);

        code.setOnClickListener(v -> {
            txtCode.setText(NodeToCode());
            txtCode.setVisibility(View.VISIBLE);
            treeView.setVisibility(View.GONE);
        });

        node.setOnClickListener(view -> {
            CodeToNode(txtCode.getText().toString());
            txtCode.setVisibility(View.GONE);
            treeView.setVisibility(View.VISIBLE);
        });

        focusMid.setOnClickListener(view -> {
            editor.focusMidLocation();
        });

        add.setOnClickListener(view -> NodeList());

    }

    public void NodeList() {
        dialogBuilder = new MaterialAlertDialogBuilder(getActivity());

        ArrayList<String> data = new ArrayList<>();

        data.add("Class");
        data.add("String");
        data.add("Void");
        data.add("int");
        data.add("Enum");
        data.add("Interface");

        nodesListAdapter = new NodeListAdapter(data, this);

        View actionView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view, null);

        ListView action_list = actionView.findViewById(R.id.list_actions);

        action_list.setAdapter(nodesListAdapter);

        dialogBuilder.setView(action_list);

        dialogBuilder.setTitle("Nodes List")
                .setMessage("Choose Nodes From Here")
                .setCancelable(true)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        basic_dlgBuilder.dismiss();
                    }
                });

        basic_dlgBuilder = dialogBuilder.show();
    }

    @Override
    public void AddNode(ArrayList<String> data, int i) {
        treeModel = new TreeModel<>(rootClass);

        NodeModel<Codes> funNode = new NodeModel<>(new Codes(1, i, data.get(i), new StringBuilder("public static void getYourName() { \n\n }")));
        treeModel.addNode(rootClass, funNode);

        adapter.setTreeModel(treeModel);

        basic_dlgBuilder.dismiss();
    }

    public void initNODE() {
        adapter = new CodeAdapter(requireActivity().getSupportFragmentManager(), getActivity(), this, treeView.getEditor());

        treeLayoutManager = getTreeLayoutManager();

        treeView.setAdapter(adapter);
        treeView.setTreeLayoutManager(treeLayoutManager);

        CodeToNode(sampleCode());

        editor = treeView.getEditor();

        dragLock.setOnCheckedChangeListener((btn, b) -> editor.requestMoveNodeByDragging(b));

        treeView.setTreeViewControlListener(this);
    }

    private TreeLayoutManager getTreeLayoutManager() {
        int space_50dp = 75;
        int space_20dp = 75;
        BaseLine line = getLine();
        //return new BoxRightTreeLayoutManager(getContext(),space_50dp,space_20dp,line);
        //return new BoxDownTreeLayoutManager(getContext(),space_50dp,space_20dp,line);
        //return new BoxLeftTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxUpTreeLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxHorizonLeftAndRightLayoutManager(this,space_50dp,space_20dp,line);
        //return new BoxVerticalUpAndDownLayoutManager(this,space_50dp,space_20dp,line);


        //TODO !!!!! the layoutManagers below are just for test don't use in your projects. Just for test now
        return new TableRightTreeLayoutManager(getContext(), space_50dp, space_20dp, line);
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
        return new SmoothLine(Color.parseColor("#8EBBFF"), 2);
        //return new StraightLine(Color.parseColor("#055287"),2);
        //return new DashLine(Color.parseColor("#F1286C"),3);
        //return new AngledLine();
    }

    @Override
    public void NodeOnClick(int id) {
//        if (id == 0) {
//            StringBuilder data = builder.ObjectGenerator(new ObjManager(new StringBuilder("MyClass"), Types.ObjTypes.Class));
//
//            NodeModel<Codes> classSample2 = new NodeModel<>(new Codes(1, new LogicBuilder(data.toString()).getClasses().get(0), data, Types.ObjTypes.Class.name()));
//
//            treeModel.addNode(rootClass, classSample2);
//            adapter.setTreeModel(treeModel);
//
//        } else {
//            if (id == 1) {
//                StringBuilder data = builder.ObjectGenerator(new DataTypesManager(
//                        new StringBuilder("newString"),
//                        Types.VisibilityTypes.Public,
//                        Types.DataTypes.String));
//
//                ShowMessage(getContext(), data);
//
//                NodeModel<Codes> classSample2 = new NodeModel<>(new Codes(1, new LogicBuilder(data.toString()).getVariables().get(0), data));
//
//                treeModel.addNode(rootClass, classSample2);
//                adapter.setTreeModel(treeModel);
//            }
//        }
    }

    public void CodeToNode(String code) {

        //Setting Up The Raw Code
        LogicBuilder classBuilder = new LogicBuilder(code);

        if (classBuilder.getClasses().size() != 0) {
            rootClass = new NodeModel<>(new Codes(0, 0, new LogicBuilder(code).getClasses().get(0), new StringBuilder(code)));
            treeModel = new TreeModel<>(rootClass);
        } else {
            ShowMessage(getContext(), new StringBuilder("No Class Found"));
        }


        //Setting Up Functions
        if (classBuilder.getFunctions().size() != 0) {

            for (int i = 0; i < classBuilder.getFunctions().size(); i++) {
                NodeModel<Codes> funNode = new NodeModel<>(new Codes(1, i + 1, classBuilder.getFunctions().get(i), new StringBuilder(classBuilder.getFunctionInfo(classBuilder.getFunctions().get(i)))));

                treeModel.addNode(rootClass, funNode);
            }
        } else {
            ShowMessage(getContext(), new StringBuilder("No Function Found"));
        }

        //Setting Up Variables
        if (classBuilder.getVariables().size() != 0) {

            for (int i = 0; i < classBuilder.getVariables().size(); i++) {
                NodeModel<Codes> varNode = new NodeModel<>(new Codes(2, i + 1, builder.getVariables().get(i), new StringBuilder(builder.getVariablesCode().get(i))));

                treeModel.addNode(rootClass, varNode);
            }
        } else {
            ShowMessage(getContext(), new StringBuilder("No Variable Found"));
        }

        //Setting The Node Data
        parentToRemoveChildren = rootClass;
        targetNode = rootClass.getChildNodes().get(0);

        //Allotting Data
        adapter.setTreeModel(treeModel);
    }

    public String NodeToCode() {
        return rootClass.value.data.toString();
    }

    @Override
    public void onScaling(int state, int percent) {

    }

    @Override
    public void onDragMoveNodesHit(@Nullable NodeModel<?> draggingNode, @Nullable NodeModel<?> hittingNode, @Nullable View draggingView, @Nullable View hittingView) {
        NodeModel<Codes> da = (NodeModel<Codes>) draggingNode;
        ShowMessage(getContext(), new StringBuilder(da.value.label));
    }

    @Override
    public void onDropNode(View view) {

        Object fTag = view.getTag(com.gyso.treeview.R.id.the_hit_target);
        boolean getHit = fTag != null;

        TreeViewHolder<Codes> targetHolder = (TreeViewHolder<Codes>) treeView.treeViewContainer.getTreeViewHolder((NodeModel) fTag);
        NodeModel<Codes> targetHolderNode = targetHolder.getNode();

        TreeViewHolder<Codes> releasedChildHolder = (TreeViewHolder<Codes>) view.getTag(com.gyso.treeview.R.id.item_holder);
        NodeModel<Codes> releasedChildHolderNode = releasedChildHolder.getNode();


        if (getHit) {
            View child = targetHolder.getView();

            for (int i = 0; i < ((ViewGroup) child).getChildCount(); i++) {
                View childView = ((ViewGroup) child).getChildAt(i);
                if (childView instanceof MaterialTextView) {
                    int typeInfoDataText = Integer.parseInt(((TextView) childView).getText().toString());

                    if (typeInfoDataText == 2)
                        //Node Is Not Merge
                        ShowMessage(getContext(), new StringBuilder(releasedChildHolderNode.value.label));
                    else
                        //Node Is Merge
                        NodeMerge(targetHolderNode, releasedChildHolderNode);

                }
            }
        }
    }

    public void NodeMerge(NodeModel<Codes> target, NodeModel<Codes> released) {

        LogicBuilder targetLogic = new LogicBuilder(target.value.data.toString());
        LogicBuilder releasedLogic = new LogicBuilder(released.value.data.toString());
        LogicBuilder classBuilder = new LogicBuilder(rootClass.value.data.toString());
        
        StringBuilder targetData = target.value.data;
        StringBuilder releasedData = released.value.data;

        int targetNodeType = target.value.type;
        int releasedNodeType = released.value.type;

        if (targetNodeType != 2 && targetNodeType != 3){
            if (targetNodeType == 0 && releasedNodeType == 1){
                if (!classBuilder.getFunctions().contains(releasedLogic.getFunctions().get(0))){

                    String cacheClass = target.value.data.toString();
                    cacheClass = cacheClass.substring(0, cacheClass.length() - 1);

                    target.value.writeData(new StringBuilder(cacheClass + "\n" + released.value.data + "\n}"));
                }
            }
        }

    }
}
