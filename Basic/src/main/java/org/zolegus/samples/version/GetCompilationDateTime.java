package org.zolegus.samples.version;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @author oleg.zherebkin
 */
public class GetCompilationDateTime {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Class klass = GetCompilationDateTime.class;
        URL location = klass.getResource('/'+klass.getName().replace('.', '/')+".class");
        System.out.println(location.toString());
        URL location2 = klass.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getPath());
        System.out.println(new File(GetCompilationDateTime.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString());

//        StringBuilder sb = new StringBuilder();
//        String rn = klass.getName().replace('.', '/') + ".class";
//        String path = location.getPath();
//        String jarFile = path.substring(0, path.indexOf("!"));
//        JarFile jf = new JarFile(jarFile);
//        ZipEntry manifest = jf.getEntry("META-INF/MANIFEST.MF");
//        long manifestTime = manifest.getTime();  //in standard millis
//        sb.append("yts-j version ").append(GetCompilationDateTime.class.getPackage().getSpecificationVersion())
//                .append(" build ").append(new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(manifestTime).toString());
//        System.out.println(sb.toString());
    }


}
