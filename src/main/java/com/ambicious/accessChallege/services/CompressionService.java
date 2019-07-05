package com.ambicious.accessChallege.services;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

@Service
public class CompressionService {
    /**
     * Compresses a byte array
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public byte[] compressBytes (byte[] bytes) throws IOException {
        return compressBytes(bytes, Deflater.BEST_SPEED);

    }

    /**
     * Compresses a byte array
     *
     * @param bytes
     * @param level
     * @return
     * @throws IOException
     */
    public byte[] compressBytes (byte[] bytes, int level) throws IOException {
        Preconditions.checkNotNull(bytes);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(baos, new Deflater(level));

        try {
            deflaterOutputStream.write(bytes);
            deflaterOutputStream.flush();
        } finally {
            baos.close();
            deflaterOutputStream.close();
        }

        return baos.toByteArray();

    }

    /**
     * Decompresses a byte array
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public byte[] decompressBytes (byte[] bytes) throws IOException {
        Preconditions.checkNotNull(bytes);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);

        byte[] uncompressedBytes = null;

        try {
            ios.write(bytes);
            ios.flush();
            uncompressedBytes = baos.toByteArray();
        } finally {
            baos.close();
            ios.close();
        }

        return uncompressedBytes;
    }
}
