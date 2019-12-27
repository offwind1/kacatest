package com.mizholdings.kaca.user.serve;

import com.mizholdings.kaca.agent.app.*;
import com.mizholdings.util.Common;
import com.mizholdings.util.User;

public class App extends ServeBase {
    public App(User user) {
        super(user);
    }

    public AuthAgent authAgent() {
        return (AuthAgent) getAgent(Common.getMethodName());
    }

    public ParenthoodAgent parenthoodAgent() {
        return (ParenthoodAgent) getAgent(Common.getMethodName());
    }

    public SchoolAgent schoolAgent() {
        return (SchoolAgent) getAgent(Common.getMethodName());
    }

    public UserAgent userAgent() {
        return (UserAgent) getAgent(Common.getMethodName());
    }

    public VipAgent vipAgent() {
        return (VipAgent) getAgent(Common.getMethodName());
    }

    public WrongbookAgent wrongbookAgent() {
        return (WrongbookAgent) getAgent(Common.getMethodName());
    }

    public ExportAgent exportAgent() {
        return (ExportAgent) getAgent(Common.getMethodName());
    }
}
