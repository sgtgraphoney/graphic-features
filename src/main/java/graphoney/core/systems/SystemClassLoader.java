package graphoney.core.systems;

import java.io.*;

public class SystemClassLoader extends ClassLoader {

    private String systemClassPath;

    public SystemClassLoader(String systemClassPath, ClassLoader parent) {
        super(parent);
        this.systemClassPath = systemClassPath;
    }

    public void setSystemClassPath(String path) {
        systemClassPath = path;
    }

    public String getSystemClassPath() {
        return systemClassPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {

            byte[] bytecode = readBytecode(systemClassPath + name + ".class");
            return defineClass(name, bytecode, 0, bytecode.length);

        } catch (IOException e) {

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
