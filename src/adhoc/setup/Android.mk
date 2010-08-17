LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE:= adhocsetup
LOCAL_SRC_FILES:=native_task.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE:= startstopadhoc
LOCAL_SRC_FILES:=startstopadhoc.c

include $(BUILD_EXECUTABLE)
