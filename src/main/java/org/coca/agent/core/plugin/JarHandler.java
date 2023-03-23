package org.coca.agent.core.plugin;

import java.io.File;
import java.util.List;
import java.util.jar.JarFile;

public class JarHandler {

    protected List<Jar> findAllJars(){
        
        return null;
    }

    private static class Jar {
        private final JarFile jarFile;
        private final File sourceFile;

        private Jar(JarFile jarFile, File sourceFile) {
            this.jarFile = jarFile;
            this.sourceFile = sourceFile;
        }
    }
}
