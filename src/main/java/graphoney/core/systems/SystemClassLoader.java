package graphoney.core.systems;

import graphoney.core.environment.EnvironmentManager;
import graphoney.core.environment.EnvironmentVariableException;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingLevel;

import java.io.*;

public class SystemClassLoader extends ClassLoader {

    private String systemClasspath;

    public SystemClassLoader(ClassLoader parent) throws EnvironmentVariableException {
        super(parent);
        this.systemClasspath = (String) EnvironmentManager.getInstance().getVariable("SYSTEM_CLASSPATH");
    }

    public void setSystemClasspath(String path) {
        systemClasspath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = systemClasspath + name + ".class";

        try {

            byte[] bytecode = readBytecode(path);
            return defineClass(name, bytecode, 0, bytecode.length);

        } catch (IOException e) {

            Logger.log(LoggingLevel.ERROR, "Could not load system from " + path + ".");
            return super.findClass(name);

        }
    }

    private byte[] readBytecode(String path) throws IOException {

        File file = new File(path);
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            throw new IOException("File is too large: " + path);
        }

        InputStream inputStream = new FileInputStream(file);

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int readBytesAmount;
        while (offset < bytes.length && (readBytesAmount = inputStream.read(bytes, offset,
                bytes.length - offset)) >= 0) {
            offset += readBytesAmount;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + path);
        }

        inputStream.close();
        return bytes;

    }
}
