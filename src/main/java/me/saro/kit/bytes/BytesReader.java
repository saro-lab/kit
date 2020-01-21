package me.saro.kit.bytes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

public class BytesReader {
    private final Charset charset;
    private final InputStream body;
    private byte fillByte = (byte)' ';

    public BytesReader(InputStream is) {
        body = is;
        this.charset = Charset.forName("UTF-8");
    }

    public BytesReader(byte[] data) {
        body = new ByteArrayInputStream(data);
        this.charset = Charset.forName("UTF-8");
    }

    public BytesReader(InputStream is, Charset charset) {
        body = is;
        this.charset = Charset.forName("UTF-8");
    }

    public BytesReader(byte[] data, Charset charset) {
        body = new ByteArrayInputStream(data);
        this.charset = Charset.forName("UTF-8");
    }

    public BytesReader(String data, Charset charset) {
        body = new ByteArrayInputStream(data.getBytes(charset));
        this.charset = charset;
    }


}
