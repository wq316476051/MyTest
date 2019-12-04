package com.wang.lib_java.art.clazz;

import com.wang.lib_java.Main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ClassParser {

	public static void main(String[] args) {
		ClassParser.parse("lib_java/User.class");
	}
	
	private static final int CONSTANT_CLASS = 7;
	private static final int CONSTANT_FIELD_REF = 9;
	private static final int CONSTANT_METHOD_REF = 10;
	private static final int CONSTANT_INTERFACE_METHOD_REF = 11;
	private static final int CONSTANT_STING = 8;
	private static final int CONSTANT_INTEGER = 3;
	private static final int CONSTANT_FLOAT = 4;
	private static final int CONSTANT_LONG = 5;
	private static final int CONSTANT_DOUBLE = 6;
	private static final int CONSTANT_NAME_AND_TYPE = 12;
	private static final int CONSTANT_UTF_8 = 1;
	private static final int CONSTANT_METHOD_HANDLE = 15;
	private static final int CONSTANT_METHOD_TYPE = 16;
	private static final int CONSTANT_INVOKE_DYNAMIC = 18;

	public static void parse(String filePath) {

		Path path = Paths.get(filePath);
		
		boolean exists = Files.exists(path);
		if (exists) {
			try {
				FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				buffer.order(ByteOrder.BIG_ENDIAN);
				
				// magic
				buffer.limit(4);
				channel.read(buffer);
				buffer.flip();
				System.out.println("magic:");
				while (buffer.hasRemaining()) {
					System.out.print(Integer.toHexString(Byte.toUnsignedInt(buffer.get())));
				}
				System.out.println();
				
				// minor version
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("minor:");
				System.out.println(Short.toUnsignedInt(buffer.getShort()));
				
				// major version
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("major:");
				System.out.println(Short.toUnsignedInt(buffer.getShort()));
				
				// constant pool count
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("constant pool count:");
				int constantPoolCount = Short.toUnsignedInt(buffer.getShort());
				System.out.println(constantPoolCount);
				
				Constant[] constants = new Constant[constantPoolCount];
				// constant poll
				for (int i = 1; i < constantPoolCount; i++) {
					buffer.clear();
					buffer.limit(1);
					channel.read(buffer);
					buffer.flip();
					int tag = Byte.toUnsignedInt(buffer.get());
					switch (tag) {
					case CONSTANT_CLASS: // Class
						buffer.clear();
						buffer.limit(2);
						channel.read(buffer);
						buffer.flip();
						ConstantClass clazz = new ConstantClass();
						clazz.tag = tag;
						clazz.index = i;
						clazz.classIndex = Short.toUnsignedInt(buffer.getShort());
						constants[i] = clazz;
						printConstant(i, "Class", "#" + clazz.classIndex);
						break;
					case CONSTANT_FIELD_REF: // Fieldref
						buffer.clear();
						buffer.limit(4);
						channel.read(buffer);
						buffer.flip();
						ConstantFieldRef fieldRef = new ConstantFieldRef();
						fieldRef.tag = tag;
						fieldRef.index = i;
						fieldRef.classIndex = Short.toUnsignedInt(buffer.getShort());
						fieldRef.nameAndTypeIndex = Short.toUnsignedInt(buffer.getShort());
						constants[i] = fieldRef;
						printConstant(i, "Fieldref", "#" + fieldRef.classIndex + ";#" + fieldRef.nameAndTypeIndex);
						break;
					case CONSTANT_METHOD_REF: // Methodref
						buffer.clear();
						buffer.limit(4);
						channel.read(buffer);
						buffer.flip();
						ConstantMethodRef methodRef = new ConstantMethodRef();
						methodRef.tag = tag;
						methodRef.index = i;
						methodRef.classIndex = Short.toUnsignedInt(buffer.getShort());
						methodRef.nameAndTypeIndex = Short.toUnsignedInt(buffer.getShort());
						constants[i] = methodRef;
						printConstant(i, "Methodref", "#" + methodRef.classIndex + ";#" + methodRef.nameAndTypeIndex);
						break;
					case CONSTANT_INTERFACE_METHOD_REF: // InterfaceMethodref
						
						break;
					case CONSTANT_STING: // String
						buffer.clear();
						buffer.limit(2);
						channel.read(buffer);
						buffer.flip();
						ConstantString string = new ConstantString();
						string.tag = tag;
						string.index = i;
						string.stringIndex = Short.toUnsignedInt(buffer.getShort());
						constants[i] = string;
						printConstant(i, "String", "#" + string.stringIndex);
						break;
					case CONSTANT_INTEGER: // Integer
						
						break;
					case CONSTANT_FLOAT: // Float
						
						break;
					case CONSTANT_LONG: // Long
						
						break;
					case CONSTANT_DOUBLE: // Double
						
						break;
					case CONSTANT_NAME_AND_TYPE: // NameAndType
						buffer.clear();
						buffer.limit(4);
						channel.read(buffer);
						buffer.flip();
						ConstantNameAndType nameAndType = new ConstantNameAndType();
						nameAndType.tag = tag;
						nameAndType.index = i;
						nameAndType.nameIndex = Short.toUnsignedInt(buffer.getShort());
						nameAndType.descriptorIndex = Short.toUnsignedInt(buffer.getShort());
						constants[i] = nameAndType;
						printConstant(i, "NameAndType", "#" + nameAndType.nameIndex + ";#" + nameAndType.descriptorIndex);
						break;
					case CONSTANT_UTF_8: // Utf8
						buffer.clear();
						buffer.limit(2);
						channel.read(buffer);
						buffer.flip();
						int length = Short.toUnsignedInt(buffer.getShort());
						
						buffer.clear();
						buffer.limit(length);
						channel.read(buffer);
						buffer.flip();
						byte[] bytes = new byte[length];
						for (int j = 0; j < length; j++) {
							bytes[j] = buffer.get();
						}
						String value = new String(bytes, StandardCharsets.UTF_8);
						
						ConstantUtf8 utf8 = new ConstantUtf8();
						utf8.tag = tag;
						utf8.index = i;
						utf8.length = length;
						utf8.value = value;
						constants[i] = utf8;
						printConstant(i, "Utf8", String.valueOf(utf8.value));
						break;
					default:
						System.out.println("unknown tag: " + tag);
						break;
					}
				}
				
				// access flag
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("access flag:");
				int accessFlag = Short.toUnsignedInt(buffer.getShort());
				System.out.println(accessFlag);
				
				// this class
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("this class:");
				int thisClass = Short.toUnsignedInt(buffer.getShort());
				
				int classIndex = ((ConstantClass)constants[thisClass]).classIndex;
				String thisClassName = ((ConstantUtf8)constants[classIndex]).value;
				System.out.println("#" + thisClass + "\t" + thisClassName);
				
				// super class
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("super class:");
				int superClass = Short.toUnsignedInt(buffer.getShort());
				
				int superClassIndex = ((ConstantClass)constants[superClass]).classIndex;
				String superClassName = ((ConstantUtf8)constants[superClassIndex]).value;
				System.out.println("#" + superClassIndex + "\t" + superClassName);
				
				// interface count
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("interface count:");
				int interfaceCount = Short.toUnsignedInt(buffer.getShort());
				System.out.println(interfaceCount);

				int[] interfacesIndex = new int[interfaceCount];
				for (int i = 0; i < interfaceCount; i++) {
					interfacesIndex[i] = Short.toUnsignedInt(buffer.getShort());
				}
				
				// field count
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("field count:");
				int fieldCount = Short.toUnsignedInt(buffer.getShort());
				System.out.println(fieldCount);

				FieldInfo[] fieldsIndexes = new FieldInfo[fieldCount];
				for (int i = 0; i < fieldCount; i++) {
					// field info
					buffer.clear();
					buffer.limit(8);
					channel.read(buffer);
					buffer.flip();
					/*
					 class FieldInfo {
						int accessFlags; // u2
						int nameIndex; // u2
						int descriptorIndex; // u2
						int attributeCount; // u2
						AttributeInfo[] attributes;
					}
					 */
					FieldInfo field = new FieldInfo();
					field.accessFlags = Short.toUnsignedInt(buffer.getShort());
					field.nameIndex = Short.toUnsignedInt(buffer.getShort());
					field.descriptorIndex = Short.toUnsignedInt(buffer.getShort());
					field.attributeCount = Short.toUnsignedInt(buffer.getShort());
					System.out.println("accessFlags = " + field.accessFlags);
					System.out.println("nameIndex = " + field.nameIndex);
					System.out.println("descriptorIndex = " + field.descriptorIndex);
					System.out.println("attributeCount = " + field.attributeCount);
					
					for (int j = 0; j < field.attributeCount; j++) {
						buffer.clear();
						buffer.limit(6);
						channel.read(buffer);
						buffer.flip();
						int attributeNameIndex = Short.toUnsignedInt(buffer.getShort()); // u2
						long attibuteLength = Integer.toUnsignedLong(buffer.getInt()); // u2
						System.out.println("attributeNameIndex = " + attributeNameIndex);
						System.out.println("attibuteLength = " + attibuteLength);
						for (int k = 0; k < attibuteLength; k++) {
							buffer.clear();
							buffer.limit(1);
							channel.read(buffer);
							buffer.flip();
							int info = Byte.toUnsignedInt(buffer.get());
							System.out.println("info = " + info);
						}
					}
				}
				
				// method count
				/*
				 method_info {
				 	u2	access_flags;
				 	u2	name_index;
				 	u2	descriptor_index;
				 	u2	attribute_count;
				 	attribute_info attributes[attribute_count]
				 } 
				 */
				buffer.clear();
				buffer.limit(2);
				channel.read(buffer);
				buffer.flip();
				System.out.println("method count:");
				int methodCount = Short.toUnsignedInt(buffer.getShort());
				System.out.println(methodCount);
				
				int[] methodIndexes = new int[methodCount];
				for (int i = 0; i < methodCount; i++) {
					buffer.clear();
					buffer.limit(8);
					channel.read(buffer);
					buffer.flip();
					
					MethodInfo methodInfo = new MethodInfo();
					methodInfo.accessFlags = Short.toUnsignedInt(buffer.getShort());
					methodInfo.nameIndex = Short.toUnsignedInt(buffer.getShort());
					methodInfo.descriptorIndex = Short.toUnsignedInt(buffer.getShort());
					methodInfo.attributeCount = Short.toUnsignedInt(buffer.getShort());

					System.out.println("accessFlags = " + methodInfo.accessFlags);
					System.out.println("nameIndex = " + methodInfo.nameIndex);
					System.out.println("descriptorIndex = " + methodInfo.descriptorIndex);
					System.out.println("attributeCount = " + methodInfo.attributeCount);
					
					for (int j = 0; j < methodInfo.attributeCount; j++) {
						buffer.clear();
						buffer.limit(6);
						channel.read(buffer);
						buffer.flip();
						int attributeNameIndex = Short.toUnsignedInt(buffer.getShort()); // u2
						long attibuteLength = Integer.toUnsignedLong(buffer.getInt()); // u2
						System.out.println("attributeNameIndex = " + attributeNameIndex);
						Constant constant = constants[attributeNameIndex];
						System.out.println(((ConstantUtf8)constant).value);
						System.out.println("attibuteLength = " + attibuteLength);
						for (int k = 0; k < attibuteLength; k++) {
							buffer.clear();
							buffer.limit(1);
							channel.read(buffer);
							buffer.flip();
							int info = Byte.toUnsignedInt(buffer.get());
							System.out.println("info = " + info);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void printConstant(int index, String type, String value) {
		System.out.println("#" + index + " = " + type + "\t\t" + value);
	}
	
	static class Constant {
		int tag;
		int index;
	}
	
	static class ConstantClass extends Constant {
		int classIndex; // u2
	}
	
	static class ConstantFieldRef extends Constant {
		int classIndex; // u2
		int nameAndTypeIndex; // u2
	}
	
	static class ConstantMethodRef extends Constant {
		int classIndex; // u2
		int nameAndTypeIndex; // u2
	}
	
	static class ConstantInterfaceMethodRef extends Constant {
		int classIndex; // u2
		int nameAndTypeIndex; // u2
	}
	
	static class ConstantString extends Constant {
		int stringIndex; // u2
	}
	
	static class ConstantInteger extends Constant {
		int value; // u4
	}
	
	static class ConstantFloat extends Constant {
		int value; // u4
	}
	
	static class ConstantLong extends Constant {
		long value; // u8
	}
	
	static class ConstantDouble extends Constant {
		double value; // u8
	}
	
	static class ConstantNameAndType extends Constant {
		int nameIndex; // u2
		int descriptorIndex; // u2
	}
	
	static class ConstantUtf8 extends Constant {
		int length; // u2
		String value;
	}
	
	static class ConstantMethodHandle extends Constant {
		int referenceKind;
		int referenceIndex; // u2
	}
	
	static class ConstantMethodType extends Constant {
		int descriptorIndex; // u2
	}
	
	static class ConstantInvokeDynamic extends Constant {
		int bootstrapMethodAttrIndex; // u2
		int nameAndTypeIndex; // u2
	}
	
	static class FieldInfo {
		int accessFlags; // u2
		int nameIndex; // u2
		int descriptorIndex; // u2
		int attributeCount; // u2
		AttributeInfo[] attributes;
	}
	
	static class MethodInfo {
		int accessFlags; // u2
		int nameIndex; // u2
		int descriptorIndex; // u2
		int attributeCount; // u2
		AttributeInfo[] attributes;
	}
	
	static class AttributeInfo {
		int attributeNameIndex; // u2
		long attributeLength; // u4
		int[] tags; // u1
	}
	
	static class CodeAttribute {
		int attributeNameIndex; // u2
		long attributeLength; // u4
		int maxStack; // u2
		int maxLocals; // u2
		long codeLength; // u4
		short[] codes;
		int exceptionTableLength;
		ExceptionTable[] exceptionTables;
		int attributesCount;
		AttributeInfo[] attributes;
	}
	
	static class ExceptionTable {
		
	}
}
