package com.sssj;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void target이없을경우source를target으로사용한다() throws Exception {
        Paths result = folderResolver("aaa", "");
        assertEquals(result.source, result.target);
    }

    @Test
    public void target과source가같이들어오면각각을사용한다() {
        final String source = "aaa";
        final String target = "bbb";
        Paths result = folderResolver(source, target);
        assertEquals(source, result.source);
        assertEquals(target, result.target);
    }

    @Test
    public void source가없으면_현재폴더를_source로_사용한다() {
        String source = "";
        String target = "";
        Paths result = folderResolver(source, target);
        String currentPath = getCurrentPath();
        assertEquals(currentPath, result.source);
        assertEquals(currentPath, result.target);
    }

    @Test
    public void file_name에_골뱅이가_없으면_mdpi(){
        String fileName = "res1.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-mdpi/"+fileName, result);
    }

    @Test
    public void file_name에_골뱅이2x_then_xhdpi(){
        String fileName = "res1@2x.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-xhdpi/"+fileName, result);
    }




    private String moveFile(String fileName) {
        return "/res/drawable-mdpi/" + fileName;
    }

    static class Paths{
        public String source;
        public String target;

        public Paths(String source, String target) {
            this.source = source;
            this.target = target;
        }

    }

    private String getCurrentPath() {
        try {
            return new File("./").getCanonicalPath();
        } catch (IOException e) {
            return null;
        }
    }


    private Paths folderResolver(String source, String target) {
        if (source.isEmpty()) {
            return new Paths(getCurrentPath(), getCurrentPath());
        }
        if (target.isEmpty()) {
            return new Paths(source, source);
        }
        return new Paths(source, target);
    }
}