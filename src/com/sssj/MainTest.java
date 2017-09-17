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
    public void target이없을경우source를target으로사용해서_변환된파일이름과_합쳐진결과를반환한다(){
        String fileName = "resFile-01@2x.xml";
        String source = "sourceFolder";
        Paths path = folderResolver(source, "");
        String toSave = source + "/res/drawable-xhdpi/resfile_01.xml";
        String result = getSaveTargetAndName(path, fileName);
        assertEquals(toSave, result);
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
    public void file_name에_골뱅이가_없으면_mdpi() {
        String fileName = "res1.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-mdpi/" + fileName, result);
    }

    @Test
    public void file_name에_골뱅이2x_then_xhdpi() {
        String fileName = "res1@2x.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-xhdpi/" + fileName.replace("@2x", ""), result);
    }

    @Test
    public void file_name에_골뱅이3x_then_xxxhdpi() {
        String fileName = "res1@3x.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-xxxhdpi/" + fileName.replace("@3x", ""), result);
    }

    @Test
    public void moveFile은_하이픈을_언더바로_바꿔서_결과에_하이픈이_없어야한다(){
        String fileName = "res-1-19-dd.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-mdpi/" + fileName.replace("-", "_"), result);
    }

    @Test
    public void moveFile은_대문자를_소문자로_변환해서_보내준다(){
        String fileName = "ResFileABCDEFG.xml";
        String result = moveFile(fileName);
        assertEquals("/res/drawable-mdpi/" + fileName.toLowerCase(), result);
    }

    private String getSaveTargetAndName(Paths path, String fileName){
        String target = folderResolver(path.source, path.target).target;
        return  target + moveFile(fileName);
    }
    private String moveFile(String fileName) {
        fileName = fileName.replace("-", "_").toLowerCase();
        if (fileName.contains("@3x")) {
            return "/res/drawable-xxxhdpi/" + fileName.replace("@3x", "");
        }
        if (fileName.contains("@2x")) {
            return "/res/drawable-xhdpi/" + fileName.replace("@2x", "");
        }
        return "/res/drawable-mdpi/" + fileName;
    }

    static class Paths {
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


    private Paths folderResolver_0_1(String source, String target) {
        if (source.isEmpty()) {
            return new Paths(getCurrentPath(), getCurrentPath());
        }
        if (target.isEmpty()) {
            return new Paths(source, source);
        }
        return new Paths(source, target);
    }
    private Paths folderResolver(String source, String target){
        return folderResolver_0_1(source, target);
    }
}