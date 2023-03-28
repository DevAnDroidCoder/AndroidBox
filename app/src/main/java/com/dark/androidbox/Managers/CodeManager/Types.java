package com.dark.androidbox.Managers.CodeManager;

import androidx.annotation.NonNull;

public class Types {

    public enum VisibilityTypes {

        Public(new StringBuilder("public"), 0),
        Private(new StringBuilder("private"), 1),
        Static(new StringBuilder("static"), 2);

        private final StringBuilder Types_label;
        private final int Types_position;

        VisibilityTypes(StringBuilder label, int pos) {
            Types_label = label;
            Types_position = pos;
        }

        @NonNull
        public StringBuilder getTypes_label() {
            return Types_label;
        }

        public int getTypes_position() {
            return Types_position;
        }
    }

    public enum ObjTypes {
        Void(new StringBuilder("void"), 0),
        Class(new StringBuilder("class"), 1),
        Interface(new StringBuilder("interface"), 2),
        Enum(new StringBuilder("enum"), 3);

        private final StringBuilder Types_label;
        private final int Types_position;

        ObjTypes(StringBuilder label, int pos) {
            Types_label = label;
            Types_position = pos;
        }

        @NonNull
        public StringBuilder getTypes_label() {
            return Types_label;
        }

        public int getTypes_position() {
            return Types_position;
        }
    }

    public enum DataTypes {
        String(new StringBuilder("String"), 0),
        Int(new StringBuilder("int"), 1),
        List(new StringBuilder("List"), 2),
        Bool(new StringBuilder("boolean"), 3);

        private final StringBuilder Types_label;
        private final int Types_position;

        DataTypes(StringBuilder label, int pos) {
            Types_label = label;
            Types_position = pos;
        }

        @NonNull
        public StringBuilder getTypes_label() {
            return Types_label;
        }

        public int getTypes_position() {
            return Types_position;
        }
    }

}
