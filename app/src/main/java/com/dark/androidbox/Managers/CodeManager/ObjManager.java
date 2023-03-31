package com.dark.androidbox.Managers.CodeManager;

public class ObjManager implements CodeManager {

    public StringBuilder objName, code = new StringBuilder("");

    public Types.ObjTypes type;

    public ObjManager(StringBuilder objName, Types.ObjTypes type) {
        this.objName = objName;
        this.type = type;
    }

    @Override
    public void setUpCode() {
        ObjBuilder(objName, type);
    }

    @Override
    public StringBuilder getCode() {
        return code;
    }

    public void ObjBuilder(StringBuilder ObjName, Types.ObjTypes types) {
        code.append("public ")
                .append(types.getTypes_label())
                .append(" ").append(ObjName.toString())
                .append(" {\n\n}");
    }
}
