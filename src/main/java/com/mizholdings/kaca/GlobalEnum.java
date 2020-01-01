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

    public enum Video_type {
        CLASS("1", "班级错题"),
        HOMEWORK("3", "作业报告"),
        STUDENT("2", "学生错题");

        private String value;
        private String name;

        Video_type(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }


    public enum Period_time {
        YEAR("1", "本学年"),
        QUARTER("2", "本学期"),
        MONTH("3", "本月"),
        WEEK("4", "本周");

        private String value;
        private String name;

        Period_time(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }


    public enum Collect_type {
        NEW_COLLECT("0", "新增收藏"),
        CANCEL_COLLECT("1", "取消收藏");

        private String value;
        private String name;

        Collect_type(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String value() {
            return this.value;
        }
    }


}

