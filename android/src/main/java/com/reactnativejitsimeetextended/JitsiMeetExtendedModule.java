package com.reactnativejitsimeetextended;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.BroadcastIntentHelper;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

@ReactModule(name = JitsiMeetExtendedModule.NAME)
public class JitsiMeetExtendedModule extends ReactContextBaseJavaModule {
    public static final String NAME = "JitsiMeetExtended";
    private JitsiMeetViewInterface jitsiMeetViewInterface;

    public String jitsiServerUrl ="https://meet.jit.si";

    public JitsiMeetExtendedModule(ReactApplicationContext reactContext, JitsiMeetViewInterface jitsiMeetViewReference) {
        super(reactContext);
      jitsiMeetViewInterface = jitsiMeetViewReference;
    }


    @Override
    @NonNull
    public String getName() {
        return NAME;
    }


  // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void multiply(int a, int b, Promise promise) {
        promise.resolve(a + b);
    }

   @ReactMethod
   public void activityMode(ReadableMap options) throws MalformedURLException {
//     registerForBroadcastMessages();
     JitsiMeetUserInfo _userInfo = new JitsiMeetUserInfo();

     UiThreadUtil.runOnUiThread(new Runnable() {
       @Override
       public void run() {

         if(jitsiMeetViewInterface.getJitsiMeetView() != null) {
           jitsiMeetViewInterface.getJitsiMeetView().leave();
           jitsiMeetViewInterface.getJitsiMeetView().dispose();
         }

         String roomId = String.valueOf(Math.random());
         Boolean chatEnabled = true;
         Boolean addPeopleEnabled = true;
         Boolean inviteEnabled = true;
         Boolean meetingNameEnabled = true;
         Boolean conferenceTimerEnabled = true;
         Boolean raiseHandEnabled = false;
         Boolean recordingEnabled = false;
         Boolean liveStreamEnabled = false;
         Boolean toolBoxEnabled = true;
         Boolean toolBoxAlwaysVisible = true;
         Boolean meetingPasswordEnabled = true;
         Boolean pipModeEnabled = true;
         URL serverUrl = null;
         try {
           serverUrl = new URL(jitsiServerUrl);
         } catch (MalformedURLException e) {
           e.printStackTrace();
         }

         if (options != null) {
           System.out.println("options are not null");

           if (options.hasKey("serverUrl") && options.getString("serverUrl") != null) {
             try {
               serverUrl = new URL(options.getString("serverUrl"));
             } catch (MalformedURLException e) {
               e.printStackTrace();
             }
           }

           if (options.hasKey("roomId") && options.getString("roomId") != null) {
             roomId = options.getString("roomId");
           }

           if (options.hasKey("chatEnabled")) {
             if (options.getBoolean("chatEnabled") == true || (options.getBoolean("chatEnabled") == false)) {
               chatEnabled = options.getBoolean("chatEnabled");
             }
           }

           if (options.hasKey("addPeopleEnabled")) {
             if (options.getBoolean("addPeopleEnabled") == true || (options.getBoolean("addPeopleEnabled") == false)) {
               addPeopleEnabled = options.getBoolean("addPeopleEnabled");
             }
           }

           if (options.hasKey("inviteEnabled")) {
             if (options.getBoolean("inviteEnabled") == true || (options.getBoolean("inviteEnabled") == false)) {
               inviteEnabled = options.getBoolean("inviteEnabled");
             }
           }

           if (options.hasKey("meetingNameEnabled")) {
             if (options.getBoolean("meetingNameEnabled") == true || (options.getBoolean("meetingNameEnabled") == false)) {
               meetingNameEnabled = options.getBoolean("meetingNameEnabled");
             }
           }

           if (options.hasKey("conferenceTimerEnabled")) {
             if (options.getBoolean("conferenceTimerEnabled") == true || (options.getBoolean("conferenceTimerEnabled") == false)) {
               conferenceTimerEnabled = options.getBoolean("conferenceTimerEnabled");
             }
           }

           if (options.hasKey("raiseHandEnabled")) {
             if (options.getBoolean("raiseHandEnabled") == true || (options.getBoolean("raiseHandEnabled") == false)) {
               raiseHandEnabled = options.getBoolean("raiseHandEnabled");
             }
           }

           if (options.hasKey("recordingEnabled")) {
             if (options.getBoolean("recordingEnabled") == true || (options.getBoolean("recordingEnabled") == false)) {
               recordingEnabled = options.getBoolean("recordingEnabled");
             }
           }

           if (options.hasKey("liveStreamEnabled")) {
             if (options.getBoolean("liveStreamEnabled") == true || (options.getBoolean("liveStreamEnabled") == false)) {
               liveStreamEnabled = options.getBoolean("liveStreamEnabled");
             }
           }

           if (options.hasKey("toolBoxEnabled")) {
             if (options.getBoolean("toolBoxEnabled") == true || (options.getBoolean("toolBoxEnabled") == false)) {
               toolBoxEnabled = options.getBoolean("toolBoxEnabled");
             }
           }

           if (options.hasKey("toolBoxAlwaysVisible")) {
             if (options.getBoolean("toolBoxAlwaysVisible") == true || (options.getBoolean("toolBoxAlwaysVisible") == false)) {
               toolBoxAlwaysVisible = options.getBoolean("toolBoxAlwaysVisible");
             }
           }

           if (options.hasKey("meetingPasswordEnabled")) {
             if (options.getBoolean("meetingPasswordEnabled") == true || (options.getBoolean("meetingPasswordEnabled") == false)) {
               meetingPasswordEnabled = options.getBoolean("meetingPasswordEnabled");
             }
           }

           if (options.hasKey("pipModeEnabled")) {
             if (options.getBoolean("pipModeEnabled") == true || (options.getBoolean("pipModeEnabled") == false)) {
               pipModeEnabled = options.getBoolean("pipModeEnabled");
             }
           }

           // check for user info
           if (options.hasKey("userInfo")) {
             ReadableMap user = options.getMap("userInfo");
             if (user.hasKey("displayName")) {
               _userInfo.setDisplayName(user.getString("displayName"));
             }
             if (user.hasKey("email")) {
               _userInfo.setEmail(user.getString("email"));
             }
             if (user.hasKey("avatar")) {
               String avatarURL = user.getString("avatar");
               try {
                 _userInfo.setAvatar(new URL(avatarURL));
               } catch (MalformedURLException e) {
               }
             }
           }
         }

         // build with options
         JitsiMeetConferenceOptions jitsiOptions
           = new JitsiMeetConferenceOptions.Builder()
           .setServerURL(serverUrl)
           .setRoom(roomId)
           .setUserInfo(_userInfo)
           .setFeatureFlag("chat.enabled", chatEnabled)
           .setFeatureFlag("add-people.enabled", addPeopleEnabled)
           .setFeatureFlag("invite.enabled", inviteEnabled)
           .setFeatureFlag("meeting-name.enabled", meetingNameEnabled)
           .setFeatureFlag("conference-timer.enabled", conferenceTimerEnabled)
           .setFeatureFlag("pip.enabled", pipModeEnabled)
           .setFeatureFlag("help.enabled", false)
           .setFeatureFlag("raise-hand.enabled", raiseHandEnabled)
           .setFeatureFlag("recording.enabled", recordingEnabled)
           .setFeatureFlag("live-streaming.enabled", liveStreamEnabled)
           .setFeatureFlag("toolbox.enabled", toolBoxEnabled)
           .setFeatureFlag("toolbox.alwaysVisible", toolBoxAlwaysVisible)
           .setFeatureFlag("meeting-password.enabled", meetingPasswordEnabled)
           // Settings for audio and video
           //.setAudioMuted(true)
           //.setVideoMuted(true)
           .build();

         // Launch the new activity with the given options. The launch() method takes care
         // of creating the required Intent and passing the options.
         JitsiMeetActivity.launch(getReactApplicationContext(), jitsiOptions);
       }
     });
   }

  // Example for sending actions to JitsiMeetSDK
  @ReactMethod
  public void hangUp() {
    Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
    LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(hangupBroadcastIntent);
  }

  @ReactMethod
  public void leaveMeet() {
    UiThreadUtil.runOnUiThread(new Runnable() {
      @Override
      public void run() {

        if (jitsiMeetViewInterface.getJitsiMeetView() != null) {
          // need to recheck
          Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
          LocalBroadcastManager.getInstance(jitsiMeetViewInterface.getJitsiMeetView().getContext()).sendBroadcast(hangupBroadcastIntent);
          jitsiMeetViewInterface.getJitsiMeetView().leave();
//        jitsiMeetViewInterface.getJitsiMeetView().dispose();
        }
      }
    });
  }


//  @ReactMethod
//  public void disposeMeet() {
//    if(jitsiMeetViewInterface.getJitsiMeetView() != null) {
//      Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
//      LocalBroadcastManager.getInstance(jitsiMeetViewInterface.getJitsiMeetView().getContext()).sendBroadcast(hangupBroadcastIntent);
//      jitsiMeetViewInterface.getJitsiMeetView().leave();
//      jitsiMeetViewInterface.getJitsiMeetView().dispose();
//    }
//  }


  @ReactMethod
  public void muteAudio(Boolean muted) {
    try {
      Intent muteBroadcastIntent = BroadcastIntentHelper.buildSetAudioMutedIntent(muted);
      LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(muteBroadcastIntent);
    }
    catch(Exception e) {
        Timber.i("error in muteAudio");
      }
  }

  @ReactMethod
  public void muteVideo(Boolean muted) {
    try {
      Intent muteVideoBroadcastIntent = BroadcastIntentHelper.buildSetVideoMutedIntent(muted);
      LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(muteVideoBroadcastIntent);
    }
    catch(Exception e) {
      Timber.i("error in muteVideo");
    }
  }

  @ReactMethod
  public void endPointTextMessage(String to, String message) {
    try {
      Intent endPointTextMessageBroadcastIntent = BroadcastIntentHelper.buildSendEndpointTextMessageIntent(to, message);
      LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(endPointTextMessageBroadcastIntent);
    }
    catch(Exception e) {
      Timber.i("error in endPointTextMessage");
    }
  }


    public static native int nativeMultiply(int a, int b);

}
