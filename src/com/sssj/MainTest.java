package com.sssj;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void target이없을경우source를target으로사용한다() throws Exception {
        String[] result = folderResolver("aaa", "");
        String source = result[0];
        String target = result[1];

        assertEquals(source, target);
    }

    @Test
    public void target과source가같이들어오면각각을사용한다() {
        final String source = "aaa";
        final String target = "bbb";
        String[] result = folderResolver(source, target);
        final String source2 = result[0];
        final String target2 = result[1];
        assertEquals(source, source2);
        assertEquals(target, target2);
    }

    @Test
    public void source가없으면_현재폴더를_source로_사용한다() {
        String source = "";
        String target = "";
        String[] result = folderResolver(source, target);
        String source2 = result[0];
        String target2 = result[1];
        String currentPath = getCurrentPath();
        assertEquals(currentPath, source2);
        assertEquals(currentPath, target2);
    }

    static class Paths{
        public String source;
        public String target;
    }

    private String getCurrentPath() {
        try {
            return new File("./").getCanonicalPath();
        } catch (IOException e) {
            return null;
        }
    }


    private String[] folderResolver(String source, String target) {
        if (source.isEmpty()) {
            return new String[]{getCurrentPath(), getCurrentPath()};
        }
        if (target.isEmpty()) {
            return new String[]{source, source};
        }
        return new String[]{source, target};
    }
}