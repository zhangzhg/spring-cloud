package com.cloud.cm;

public interface ErrorCode {
    enum Sys {
        noAuth(403, "没有访问权限");

        private int code;
        private String name;

        Sys(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
