// IRecordService.aidl
package com.wang.soundrecorder;

// Declare any non-default types here with import statements
import com.wang.soundrecorder.RecordCallback;

interface IRecordService {
    String startRecord(String filePath);
    void pauseResumeRecord(String id);
    void stopRecord(String id);

    int getRecordState(String id);
    boolean isRecording();
    boolean isRecordingById(String id);
    boolean isPausedById(String id);

    void addRecordCallback(RecordCallback callback);
    void removeRecordCallback(RecordCallback callback);
}
