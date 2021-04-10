package com.reactnativejitsimeetextended;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.PermissionListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.BroadcastIntentHelper;
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import timber.log.Timber;

import static org.webrtc.ContextUtils.getApplicationContext;


public class RNJitsiViewManager extends SimpleViewManager<RNJitsiMeetView>  {

  public static final String REACT_CLASS = "JitsiView";
  private JitsiMeetViewInterface jitsiMeetViewInterface;
  public String jitsiServerUrl ="https://meet.jit.si";
  private ReactApplicationContext mReactContext;

  public RNJitsiViewManager(ReactApplicationContext reactContext, JitsiMeetViewInterface jitsiMeetViewReference) {
    jitsiMeetViewInterface = jitsiMeetViewReference;
    mReactContext = reactContext;
  }

  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      onBroadcastReceived(intent);
    }
  };

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected RNJitsiMeetView createViewInstance(ThemedReactContext reactContext) {

    if(jitsiMeetViewInterface.getJitsiMeetView() == null){
      RNJitsiMeetView view = new RNJitsiMeetView(reactContext.getCurrentActivity());
      jitsiMeetViewInterface.setJitsiMeetView(view);
    }
    registerForBroadcastMessages();
    return jitsiMeetViewInterface.getJitsiMeetView();
  }

  @ReactProp(name="options")
  public void setProps(RNJitsiMeetView jitsiMeetView,  ReadableMap options) throws MalformedURLException {

    // kill any already up call and dispose view
    if(jitsiMeetViewInterface.getJitsiMeetView() != null) {
//      Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
//      LocalBroadcastManager.getInstance(jitsiMeetView.getContext()).sendBroadcast(hangupBroadcastIntent);
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
    URL serverUrl = new URL(jitsiServerUrl);


    if (jitsiMeetViewInterface.getJitsiMeetView() != null) {

      RNJitsiMeetUserInfo _userInfo = new RNJitsiMeetUserInfo();
      System.out.println("jitsi meet is not null");

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
      RNJitsiMeetConferenceOptions jitsiOptions
        = new RNJitsiMeetConferenceOptions.Builder()
        .setServerURL(serverUrl)
        .setRoom(roomId)
        .setUserInfo(_userInfo)
        .setFeatureFlag("chat.enabled", chatEnabled)
        .setFeatureFlag("add-people.enabled", addPeopleEnabled)
        .setFeatureFlag("invite.enabled", inviteEnabled)
        .setFeatureFlag("meeting-name.enabled", meetingNameEnabled)
        .setFeatureFlag("conference-timer.enabled", conferenceTimerEnabled)
        .setFeatureFlag("pip.enabled", false)
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

      jitsiMeetViewInterface.getJitsiMeetView().join(jitsiOptions);
   }
  }

  private void registerForBroadcastMessages() {
    IntentFilter intentFilter = new IntentFilter();

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.getAction());
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());
                ... other events
         */
    for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
      intentFilter.addAction(type.getAction());
    }

    LocalBroadcastManager.getInstance(jitsiMeetViewInterface.getJitsiMeetView().getContext()).registerReceiver(broadcastReceiver, intentFilter);
  }

  // Example for handling different JitsiMeetSDK events
  private void onBroadcastReceived(Intent intent) {
    if (intent != null) {

      BroadcastEvent event = new BroadcastEvent(intent);
      WritableMap eventMap = Arguments.createMap();

      switch (event.getType()) {
        case CONFERENCE_JOINED:
          Timber.i("Conference Joined with url%s", event.getData().get("url"));
          eventMap = Arguments.createMap();
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "conferenceJoined",
            eventMap);
          break;

        case CONFERENCE_TERMINATED:
          Timber.i("TERMINATED TERMINATED%s", event.getData().get("url"));
          eventMap = Arguments.createMap();
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "conferenceTerminated",
            eventMap);

          Intent hangupBroadcastIntent = BroadcastIntentHelper.buildHangUpIntent();
          LocalBroadcastManager.getInstance(jitsiMeetViewInterface.getJitsiMeetView().getContext()).sendBroadcast(hangupBroadcastIntent);

          jitsiMeetViewInterface.getJitsiMeetView().dispose();
          break;

        case CONFERENCE_WILL_JOIN:
          Timber.i("CONFERENCE_WILL_JOIND%s", event.getData().get("url"));
          eventMap = Arguments.createMap();
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "conferenceWillJoin",
            eventMap);
          break;

        case AUDIO_MUTED_CHANGED:
          Timber.i("AUDIO_MUTED_CHANGED%s", event.getData().get("muted"));
          eventMap = Arguments.createMap();
          eventMap.putString("muted", (String) event.getData().get("muted"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "audioMuted",
            eventMap);
          break;

        case PARTICIPANT_JOINED:
          Timber.i("PARTICIPANT_JOINED%s", event.getData().get("name"));
          eventMap = Arguments.createMap();
          eventMap.putString("name", (String) event.getData().get("name"));
          eventMap.putString("email", (String) event.getData().get("email"));
          eventMap.putString("participantId", (String) event.getData().get("participantId"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "participantJoined",
            eventMap);
          break;

        case PARTICIPANT_LEFT:
          Timber.i("PARTICIPANT_LEFT%s", event.getData().get("name"));
          eventMap = Arguments.createMap();
          eventMap.putString("name", (String) event.getData().get("name"));
          eventMap.putString("email", (String) event.getData().get("email"));
          eventMap.putString("participantId", (String) event.getData().get("participantId"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "participantLeft",
            eventMap);
          break;

        case ENDPOINT_TEXT_MESSAGE_RECEIVED:
          Timber.i("ENDPOINT_TEXT_MESSAGE_RECEIVED%s", event.getData().get("senderId"));
          eventMap = Arguments.createMap();
          eventMap.putString("senderId", (String) event.getData().get("senderId"));
          eventMap.putString("message", (String) event.getData().get("message"));
          eventMap.putString("error", (String) event.getData().get("error"));
          mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetViewInterface.getJitsiMeetView().getId(),
            "messageReceived",
            eventMap);
          break;


      }
    }
  }

  @Override
  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
      .put("conferenceJoined", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceJoined")))
      .put("conferenceTerminated", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceTerminated")))
      .put("conferenceWillJoin", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceWillJoin")))
      .put("audioMuted", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onAudioMuted")))
      .put("participantJoined", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onParticipantJoined")))
      .put("participantLeft", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onParticipantLeft")))
      .put("messageReceived", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onMessageReceived")))
      .build();
  }
}
