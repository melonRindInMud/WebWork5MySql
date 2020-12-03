package com.guapi.webwork5.Service;

import com.guapi.webwork5.Data.LoginInfor;

public class CheckLogin {

    public static boolean Check(LoginInfor infor) {
        if ("admin".equals(infor.getUsername()) && "123456".equals(infor.getPassword()))
            return true;
        else
            return false;
    }
}
