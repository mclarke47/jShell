package org.mclarke.os.windows;

import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static boolean isCurrentUserAdmin(){
        List<String> groups =  Arrays.asList(new com.sun.security.auth.module.NTSystem().getGroupIDs());

        return groups.contains("S-1-5-32-544");
    }
}
