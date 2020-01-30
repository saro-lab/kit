package me.saro.kit.bytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class BytesWriter {

    private final Charset charset;
    private final ByteArrayOutputStream body;
    private byte fillByte = (byte)' ';

    public BytesWriter() {
        body = new ByteArrayOutputStream();
        charset = Charset.forName("UTF-8");
    }

    public BytesWriter(int initSize, Charset charset) {
        body = new ByteArrayOutputStream(initSize);
        this.charset = charset;
    }

    public BytesWriter fillByte(byte fillByte) {
        this.fillByte = fillByte;
        return this;
    }

    public BytesWriter add(byte[] buf, int offset, int length) {
        body.write(buf, offset, length);
        return this;
    }

    public BytesWriter add(byte[] buf) {
        return add(buf, 0, buf.length);
    }

    public BytesWriter add(String text) {
        return add(text.getBytes(charset));
    }

    public BytesWriter add(String text, int fixedSize) {
        byte[] buf = text.getBytes(charset);
        int leftSize = fixedSize - buf.length;
        if (leftSize < 0) {
            throw new RuntimeException("[" + text + "] is out of fixed-size[" + fixedSize + "] position: " + body.size());
        } else if (leftSize == 0) {
            return add(buf);
        } else {
            byte[] fill = new byte[leftSize];
            Arrays.fill(fill, fillByte);
            return add(buf).add(fill);
        }
    }

    public BytesWriter add(int data, int fixedSize) {
        return add(Integer.toString(data), fixedSize);
    }

    public BytesWriter add(short data, int fixedSize) {
        return add(Short.toString(data), fixedSize);
    }

    public BytesWriter add(long data, int fixedSize) {
        return add(Long.toString(data), fixedSize);
    }

    public void bind(OutputStream os) {
        try {
            body.writeTo(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toBytes() {
        return body.toByteArray();
    }

    @Override
    public String toString() {
        try {
            return body.toString(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
