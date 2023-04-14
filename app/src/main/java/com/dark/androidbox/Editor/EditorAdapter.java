package com.dark.androidbox.Editor;

import android.content.Context;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.CodeViewAdapter;
import com.amrdeveloper.codeview.Keyword;
import com.amrdeveloper.codeview.Snippet;
import com.dark.androidbox.R;

import java.util.ArrayList;

public class EditorAdapter {

    ArrayList<Code> list_data = new ArrayList<>();
    public CodeView txtCode;
    public Context context;

    public EditorAdapter(Context context, CodeView txtCode) {
        this.context = context;
        this.txtCode = txtCode;
    }

    public void Refresh() {
        txtCode.setAdapter(new CodeViewAdapter(context, R.layout.code_items_list, R.id.label_codeBlock, list_data));
    }

    public void AddSnippet(String str, String code) {
        list_data.add(new Snippet(str, code));
    }

    public void AddKeyWord(String str) {
        list_data.add(new Keyword(str));
    }

}
