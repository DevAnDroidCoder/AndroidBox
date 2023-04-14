package com.dark.androidbox.Adpaters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.dark.androidbox.Editor.Codes;
import com.dark.androidbox.Fragments.EditorFragment;
import com.dark.androidbox.R;
import com.dark.androidbox.System.NodeEvents;
import com.dark.androidbox.builder.LogicBuilder;
import com.dark.androidbox.databinding.CodeNodesBinding;
import com.google.android.material.button.MaterialButton;
import com.gyso.treeview.TreeViewEditor;
import com.gyso.treeview.adapter.DrawInfo;
import com.gyso.treeview.adapter.TreeViewAdapter;
import com.gyso.treeview.adapter.TreeViewHolder;
import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.model.NodeModel;

import java.util.List;

public class CodeAdapter extends TreeViewAdapter<Codes> {


    public Activity ctx;
    NodeEvents events;

    FragmentManager manager;

    TreeViewEditor editor;

    public CodeAdapter(FragmentManager manager, Activity activity, NodeEvents events, TreeViewEditor editor) {
        this.ctx = activity;
        this.events = events;
        this.manager = manager;
        this.editor = editor;
    }

    @Override
    public TreeViewHolder<Codes> onCreateViewHolder(@NonNull ViewGroup viewGroup, NodeModel<Codes> model) {
        CodeNodesBinding nodeBinding = CodeNodesBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new TreeViewHolder<>(nodeBinding.getRoot(), model);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder<Codes> holder) {
        View items = holder.getView();

        LinearLayout head_node = items.findViewById(R.id.head_node);

        RelativeLayout cardInfo = items.findViewById(R.id.card_info);

        TextView label = items.findViewById(R.id.label_codeBlock);

        TextView txt_info = items.findViewById(R.id.txt_info);

        TextView node_id = items.findViewById(R.id.node_id);

        MaterialButton delNode = items.findViewById(R.id.delNode);

        NodeModel<Codes> nodeObj = holder.getNode();

        final Codes blockData = nodeObj.value;

        cardInfo.setVisibility(View.GONE);

        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                events.NodeOnClick(blockData.itemId);
            }
        });

        head_node.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cardInfo.setVisibility(!(cardInfo.getVisibility() == View.VISIBLE) ? View.VISIBLE : View.GONE);
                return false;
            }
        });

        delNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.removeNode(holder.getNode());
            }
        });


        label.setText(blockData.label);
        if (blockData.itemId == 0) {
            String data = setUpClassInfo(new LogicBuilder(EditorFragment.sampleCode()));

            SpannableStringBuilder colorant = new SpannableStringBuilder(data);

            SetUpColor(colorant, data, "Type", "#8EBBFF");
            SetUpColor(colorant, data, "Returns", "#8EBBFF");
            SetUpColor(colorant, data, "Super Class", "#8EBBFF");
            SetUpColor(colorant, data, "Implementations", "#8EBBFF");
            SetUpColor(colorant, data, "Null", "#FF8E8E");

            txt_info.setText(colorant);

        } else {
            if (blockData.itemId == 1) {
                txt_info.setText("Click Here To Add Var");
                txt_info.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                if (blockData.itemId == 2) {
                    txt_info.setText("Click Here To Add Methods");
                    txt_info.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
            }
        }
        node_id.setText("Node -> ".concat(String.valueOf(blockData.getItemId())));

        Log.d("System Info", String.valueOf(new LogicBuilder(EditorFragment.sampleCode()).objType));
    }

    public String setUpClassInfo(LogicBuilder builder) {

        String Type, Returns, Inputs = "null", data;

        String superclass = builder.getClassExtends(builder.getClasses().get(0));
        if (superclass != null) {
            List<String> interfaces = builder.getClassImplements();
            if (!interfaces.isEmpty()) {
                StringBuilder sampleData;
                sampleData = new StringBuilder();
                for (int i = 0; i < interfaces.size(); i++) {
                    sampleData.append(", ").append(interfaces.get(i));
                }
                Inputs = "Super Class : ".concat(superclass).concat("\n").concat("Implementations ").concat(sampleData.substring(1));
            }

        } else {
            System.out.println("MyClass does not extend any class");
        }
        Type = "Type : Class";
        Returns = "Returns : Null";

        data = Type.concat("\n").concat(Returns).concat("\n").concat(Inputs);

        return String.valueOf(data);
    }

    @Override
    public BaseLine onDrawLine(DrawInfo drawInfo) {
        return null;
    }

    public void SetUpColor(SpannableStringBuilder colorant, String data, String word, String color) {
        ForegroundColorSpan txtColor1 = new ForegroundColorSpan(Color.parseColor(color));

        int start1 = data.indexOf(word);
        int end1 = start1 + word.length();
        colorant.setSpan(txtColor1, start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
