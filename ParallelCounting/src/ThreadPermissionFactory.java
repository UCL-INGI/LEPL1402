package src;

import com.github.guillaumederval.javagrading.Grade;

import java.security.PermissionCollection;
import java.security.Permissions;

public class ThreadPermissionFactory implements Grade.PermissionCollectionFactory {

    @Override
    public PermissionCollection get() {
        PermissionCollection coll = new Permissions();
        coll.add(new RuntimePermission("modifyThreadGroup"));
        coll.add(new RuntimePermission("modifyThread"));
        coll.add(new RuntimePermission("getStackTrace"));
        return coll;
    }

}
