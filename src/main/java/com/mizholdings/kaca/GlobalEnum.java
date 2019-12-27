package com.mizholdings.kaca;

public class GlobalEnum {


    public enum MemberType {
        TEACHER("1", "教师"), STUDENT("3", "学生");
        private String value;
        private String name;

        MemberType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }


    public enum SubjectId {
        MATH("2", "数学"),
        SCIENCE("10", "科学"),
        PHYSICS("8", "物理"),
        BIOLOGY("7", "生物"),
        CHEMISTRY("9", "化学");

        private String value;
        private String name;

        SubjectId(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }

    public enum Is_need_question {
        NEED("1", "需要"),
        NEED_NOT("0", "不需要");

        private String value;
        private String name;

        Is_need_question(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }


}

