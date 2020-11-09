#!/bin/bash
echo "start build ffmpeg..."

NDK=/root/ff/android-ndk-r14b
PLATFORM=$NDK/platforms/android-21/arch-arm
TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64
CPU=armv7-a
# output directory
PREFIX=./android/$CPU

function buildFF
{
	echo "started...."

	./configure \
		--prefix=$PREFIX \
		--target-os=android \
		--cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
		--arch=arm \
		--cpu=$CPU \
		--sysroot=$PLATFORM \
		--extra-cflags="-I$PLATFORM/usr/include -fPIC -DANDROID -mfpu=neon -mfloat-abi=softfp" \
		--cc=$TOOLCHAIN/bin/arm-linux-androideabi-gcc \
		--nm=$TOOLCHAIN/bin/arm-linux-androideabi-nm \
		--enable-shared \
		--enable-runtime-cpudetect \
		--enable-gpl \
		--enable-small \
		--enable-cross-compile \
		--enable-asm \
		--enable-neon \
		--enable-jni \
		--enable-mediacodec \
		--enable-decoder=h264_mediacodec \
		--enable-hwaccel=h264_mediacodec \
		--disable-debug \
		--disable-static \
		--disable-doc \
		--disable-ffmpeg \
		--disable-ffplay \
		--disable-ffprobe \
		--disable-ffserver \
		--disable-postproc \
		--disable-avdevice \
		--disable-symver \
		--disable-stripping
	make -j16
	make install

	echo "ended......"
}

buildFF
