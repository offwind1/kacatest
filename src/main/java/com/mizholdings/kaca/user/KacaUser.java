package com.mizholdings.kaca.user;

public class KacaUser extends UserBase {

    private KacaUser(String account, String password, String project) {
        super(account, password, project);
    }

    public static KacaUser kcct(String account, String password) {
        return new KacaUser(account, password, "kcct");
    }

    public static KacaUser kcsj(String account, String password) {
        return new KacaUser(account, password, "kcsj");
    }

}
