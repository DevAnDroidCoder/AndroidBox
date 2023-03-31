package com.dark.androidbox.Managers.CodeManager;

public class DataTypesManager implements CodeManager {
    public StringBuilder objName, code = new StringBuilder("");

    public Types.DataTypes type;

    public Types.VisibilityTypes visibilityTypes;

    public DataTypesManager(StringBuilder objName, Types.VisibilityTypes visibilityTypes, Types.DataTypes type) {
        this.objName = objName;
        this.visibilityTypes = visibilityTypes;
        this.type = type;
    }

    @Override
    public void setUpCode() {
        DataTypeBuilder(objName, visibilityTypes, type);
    }

    @Override
    public StringBuilder getCode() {
        return code;
    }

    public void DataTypeBuilder(StringBuilder ObjName, Types.VisibilityTypes visibilityTypes, Types.DataTypes types) {
        code.append(visibilityTypes.getTypes_label())
                .append(" ")
                .append(types.getTypes_label())
                .append(" ").append(ObjName.toString())
                .append(";");
    }
}
