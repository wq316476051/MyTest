// IRecordService.aidl
package com.wang.mytest.feature.audio.record;

import com.wang.mytest.feature.audio.record.IRecordCallback;

// Declare any non-default types here with import statements

interface IRecordService {
    void startRecording();
    void pause();
    void resume();
    void stopRecording();
    int getAmplitude();
    int getState();
    void registerCallback(IRecordCallback callback);
    void unregisterCallback();
}
