package org.javahispano.javacup.model.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Compressor {
	public static byte[] compress(byte[] b, String name) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ZipOutputStream zos = new ZipOutputStream(baos);
    	ZipEntry entry = new ZipEntry(name);
    	entry.setSize(b.length);
    	zos.putNextEntry(entry);
    	zos.write(b);
    	zos.closeEntry();
    	zos.close();
    	
    	return baos.toByteArray();  
	}

}
