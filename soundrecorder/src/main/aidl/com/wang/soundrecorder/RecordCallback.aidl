// RecordCallback.aidl
package com.wang.soundrecorder;

// Declare any non-default types here with import statements

interface RecordCallback {
    void onRecordStateChanged(String recordId, int recordState);
}
