package com.reactnativejitsimeetextended;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;


import org.jitsi.meet.sdk.JitsiMeetView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JitsiMeetExtendedPackage implements ReactPackage, JitsiMeetViewInterface {

  private RNJitsiMeetView jitsiMeetView = null;

  public void setJitsiMeetView(RNJitsiMeetView rnJitsiMeetView) {
    jitsiMeetView = rnJitsiMeetView;
  }

  public RNJitsiMeetView getJitsiMeetView() {
    return jitsiMeetView;
  }

  @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new JitsiMeetExtendedModule(reactContext, this));
        return modules;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
      return Arrays.<ViewManager>asList(
        new RNJitsiViewManager(reactContext, this)
      );
    }
}
