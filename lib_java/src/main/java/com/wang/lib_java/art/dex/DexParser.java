package com.wang.lib_java.art.dex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DexParser {

	public static void main(String[] args) {
		DexParser.parse("lib_java/classes.dex");
	}

	public static void parse(String filePath) {
		Path dex = Paths.get(filePath);
		boolean exists = Files.exists(dex);
		if (exists) {
			try {
				FileChannel channel = FileChannel.open(dex, StandardOpenOption.READ);
				ByteBuffer buffer = ByteBuffer.allocate(10240);
				buffer.order(ByteOrder.LITTLE_ENDIAN);

				parseHeader(channel, buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File not exist.");
		}
	}

	private static void parseHeader(FileChannel channel, ByteBuffer buffer) throws IOException {
		// magic
		buffer.limit(8);
		channel.read(buffer);
		buffer.flip();
		printMagic(buffer);

		// checksum
		buffer.clear();
		buffer.limit(4);
		channel.read(buffer);
		buffer.flip();
		int checkSum = buffer.getInt();
		System.out.println("CheckSum: " + getUnsignedInt(checkSum));

		// signature
		buffer.clear();
		buffer.limit(20);
		channel.read(buffer);
		buffer.flip();
//		printSignature(buffer);
		System.out.println("Signature: " + new String(buffer.array(), buffer.arrayOffset(), buffer.remaining()));

		// file_size
		buffer.clear();
		buffer.limit(4);
		channel.read(buffer);
		buffer.flip();
		System.out.println("FileSize: " + Integer.toHexString(buffer.getInt()));

		// header_size
		buffer.clear();
		buffer.limit(4);
		channel.read(buffer);
		buffer.flip();
		System.out.println("HeaderSize: " + Integer.toHexString(buffer.getInt()));

		// endian_tag
		buffer.clear();
		buffer.limit(4);
		channel.read(buffer);
		buffer.flip();
		System.out.println("EndianTag: " + Integer.toHexString(buffer.getInt()));
	}
	



	public static long getUnsignedInt(int data) {
		return data & 0x0FFFFFFFFL;
	}

	private static void printMagic(ByteBuffer buffer) {
		System.out.print("Magic: ");
		int max = buffer.remaining();
		int i = 0;
		while (i++ < max) {
			String hex = Integer.toHexString(buffer.get());
			System.out.print(((hex.length() == 1) ? ("0" + hex) : hex) + " ");
		}
		System.out.println();
	}
}
