package org.coca.agent.core.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarFile;

public class JarHandler {
    private static List<File> classfiles = new LinkedList<>();
    public static List<String> MOUNT = Arrays.asList("plugins", "activations");
    protected static List<Jar> findAllJars(){
        LinkedList<Jar> jars = new LinkedList<>();
        for (File path : classfiles) {
            if (path.exists() && path.isDirectory()) {
                String[] jarFileNames = path.list((dir, name) -> name.endsWith(".jar"));
                for (String fileName : jarFileNames) {
                    try {
                        File file = new File(path, fileName);
                        Jar jar = new Jar(new JarFile(file), file);
                        jars.add(jar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jars;
    }

    public static class Jar {
        protected final JarFile jarFile;
        protected final File sourceFile;

        private Jar(JarFile jarFile, File sourceFile) {
            this.jarFile = jarFile;
            this.sourceFile = sourceFile;
        }
    }

    public static void main(String[] args) {
        String classResourcePath = JarHandler.class.getName().replaceAll("\\.", "/") + ".class";
        System.out.println(classResourcePath);
        URL resource = ClassLoader.getSystemClassLoader().getResource(classResourcePath);
        System.out.println(resource.toString());
    }
}
