package com.wang.soundrecorder.config

import android.media.MediaRecorder

/*
8,000 Hz是电话所用采样率, 对于人的说话已经足够
11,025 Hz是AM调幅广播所用采样率
22,050 Hz和24,000 Hz- FM是调频广播所用采样率
32,000 Hz是miniDV 数码视频 camcorder、DAT (LP mode)所用采样率
44,100 Hz是音频 CD, 也常用于 MPEG-1 音频（VCD, SVCD, MP3）所用采样率 （超过该采样率，人耳很难分辨）
47,250 Hz是商用 PCM 录音机所用采样率
48,000 Hz是miniDV、数字电视、DVD、DAT、电影和专业音频所用的数字声音所用采样率
50,000 Hz是商用数字录音机所用采样率
96,000 或者 192,000 Hz - DVD-Audio、一些 LPCM DVD 音轨、BD-ROM（蓝光盘）音轨、和 HD-DVD （高清晰度 DVD）音轨所用所用采样率
2.8224 MHz是Direct Stream Digital 的 1 位 sigma-delta modulation 过程所用采样率。
 */
object RecordAttribute {
    const val SOURCE = MediaRecorder.AudioSource.MIC
    const val SAMPLE_RATE = 48000 // CD 的采样率一般为 44100
    const val AUDIO_CHANNEL = 2
    const val ENCODER = MediaRecorder.AudioEncoder.AAC
    const val OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4
}