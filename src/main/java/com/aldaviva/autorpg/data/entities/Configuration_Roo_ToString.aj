// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.aldaviva.autorpg.data.entities;

import java.lang.String;

privileged aspect Configuration_Roo_ToString {
    
    public String Configuration.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Key: ").append(getKey()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Value: ").append(getValue());
        return sb.toString();
    }
    
}