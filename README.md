# react-native-jitsi-meet-extended

### Don't use it for now , under testing 

JItsi Meet wrapper for react-native.
This package was made as the react-native-jitsi-meet is kind of outdated and no one is releasing any new version of it with new jitsi meet sdk


## Installation

```sh
npm install react-native-jitsi-meet-extended

or

yarn add react-native-jitsi-meet-extended
```


## Note
At the moment this package only works with **Android** as I don't own a Mac to develop and test it for **IOS**, Hope to see some community support for developing it and also one of my friend will soon start looking into the IOS part. Also, there may be some bugs and issues in the java code, it would be great if some super devs from the community can verify and correct them :) + there are issues with TS as I'm not that much into typescript.

## Android setup

1) In android/build.gradle, add the following code
```
allprojects {
    repositories {
        mavenLocal()
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url("$rootDir/../node_modules/react-native/android")
        }
        maven {
            // Android JSC is installed from npm
            url("$rootDir/../node_modules/jsc-android/dist")
        }

        google()
        jcenter()
        maven { // <---- Add this block
            url "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases"
        }
        maven { url 'https://www.jitpack.io' }
    }
}
```
2) Set minimum SDK level to 24
```
buildscript {
    ext {
        buildToolsVersion = "29.0.3"
        minSdkVersion = 24 // <-- this line
        compileSdkVersion = 29
        targetSdkVersion = 29
        ndkVersion = "20.1.5948944"
    }
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```
3) Remove allow back up from Androidmanifest.xml

Checking on how this can be fixed but for now, remove it from xml
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.sdktest">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
      android:name=".MainApplication"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher"
      android:roundIcon="@mipmap/ic_launcher_round"
      android:allowBackup="false" <-- this line
      android:theme="@style/AppTheme">
      <activity
        android:name=".MainActivity"
        android:label="@string/app_name"
        android:configChanges="keyboard|keyboardHidden|orientation|screenSize|uiMode"
        android:launchMode="singleTask"
        android:windowSoftInputMode="adjustResize">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
      </activity>
    </application>
</manifest>

```


## Usage

The package can be invoked in two modes
1) As an intent from react-native app
2) As a view that can be embedded inside the react-native app

#### ⁍ ‣ Invoking it as native activity (Eg)
For checking props that can be passed, please check the section after example section
```js
import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { JitsiMeetExtended, JitsiMeetView } from 'react-native-jitsi-meet-extended';

export default function App() {

  const runActivity = () => {
    const options = {
      roomId: "cowboybtr125d44d5",
      userInfo: {
        displayName: "APJ"
       }
     }

    JitsiMeetExtended.activityMode(options)
  }

  return (
    <View style={styles.container}>
     
      <TouchableOpacity onPress={runActivity} style={styles.button}>
        <Text>Start meet activity</Text>
      </TouchableOpacity>
    
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 0.9,
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    borderRadius: 10,
    width: 250,
    height: 50,
    padding:10,
    margin:10,
    borderWidth:2,
    borderColor: "gray",
    justifyContent:"center",
    alignItems:"center",
  }
});

```
This will start Jitsi meet on top of your application.

#### ⁍ ‣ Starting as a view inside react-native (Eg)
For checking props that can be passed, please check the section after example section
```js
import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { JitsiMeetExtended, JitsiMeetView } from 'react-native-jitsi-meet-extended';

export default function App() {

  const [showMeet, setShowMeet] = React.useState<Boolean>(false);


  function conferenceTerminated(nativeEvent: any) {
    console.log(nativeEvent)
  }

 const runMeet = () => {
    setShowMeet(true);
  }

  return (
    <View style={styles.container}>
     
    {showMeet && (
        <JitsiMeetView
          style={{
            flex: 10,
            height: '100%',
            width: '100%',
          }}
          options={{
            roomId: "randomfox895678dc5d6",
            chatEnabled: false,
            inviteEnabled: false,
            meetingNameEnabled: false,
            userInfo: {
              displayName: "Nikola Tesla"
            }
          }}
        
          onConferenceTerminated={(e: any) => conferenceTerminated(e)}
        />
      )}

      <TouchableOpacity onPress={runMeet} style={styles.button}>
        <Text>Start meet as view inside app</Text>
      </TouchableOpacity>
    
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 0.9,
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: {
    borderRadius: 10,
    width: 250,
    height: 50,
    padding:10,
    margin:10,
    borderWidth:2,
    borderColor: "gray",
    justifyContent:"center",
    alignItems:"center",
  }
});

```
This will start Jitsi meet as a view inside react-native.

## Usage Options and Props
⁍ To JitsiMeetView you can pass a prop called **options** which should be an object and it supports the following features.

| key                    | Data type | Default             | Description                                                                                                                                            |
|------------------------|-----------|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| roomId                 | String    | random string       | Room id for Jitsi Meet                                                                                                                                 |
| serverUrl              | String    | https://meet.jit.si | Valid server url                                                                                                                                       |
| chatEnabled            | boolean   | true                | Enable or disable chat icon in Jitsi                                                                                                                   |
| addPeopleEnabled       | boolean   | true                | Allow adding people or not                                                                                                                             |
| inviteEnabled          | boolean   | true                | Allow inviting people or not                                                                                                                           |
| meetingNameEnabled     | boolean   | true                | Show or hide roomid/name in Jitsi meet                                                                                                                 |
| raiseHandEnabled       | boolean   | false               | Enable or disable raise hand in Jitsi meet                                                                                                             |
| conferenceTimerEnabled | boolean   | true                | Show or hide conference timer in Jitsi meet                                                                                                            |
| recordingEnabled       | boolean   | false               | Enable or disable recording in Jitsi meet                                                                                                              |
| liveStreamEnabled      | boolean   | false               | Enable or disable live-stream in Jitsi meet                                                                                                            |
| toolBoxEnabled         | boolean   | true                | Enable or disable toolbox in Jitsi meet                                                                                                                |
| toolBoxAlwaysVisible   | boolean   | true                | Make toolbox always visible or not                                                                                                                     |
| meetingPasswordEnabled | boolean   | true                | Enable or disable meeting password                                                                                                                     |
| pipModeEnabled         | boolean   | false               | **Only for activity mode**, This flag is not  available in JitsiMeet view mode                                                                         |
| userInfo               | Object    |                     | This can have three keys as follows ```    userInfo:{      displayName: "APJ",      email: "test@example.com",      avatarURL: "valid URL"     } ``` |

### Supported events (only in JitsiMeetView)
1) **onConferenceJoined**
2) **onConferenceTerminated** 
3) **onConferenceWillJoin** 
4) **onAudioMuted** 
5) **onParticipantJoined** 

```js
    <JitsiMeetView
          style={{
            flex: 10,
            height: '100%',
            width: '100%',
          }}
          options={{
            roomId: "randomfox895678dc5d6",
            chatEnabled: false,
            inviteEnabled: false,
            meetingNameEnabled: false,
            userInfo: {
              displayName: "Nikola Tesla"
            }
          }}
        
          onConferenceTerminated={(e: any) => conferenceTerminated(e)}
          onConferenceJoined={(e: any) => conferenceJoined(e)}
          onConferenceWillJoin={(e: any) => conferenceWillJoin(e)}
          onAudioMuted={(e: any) => audioMuted(e)}
          onParticipantJoined={(e: any) => participantJoined(e)}
        />

```

### Supported actions 
1) **leaveMeet()**
2) **muteAudio()**
3) **muteVideo()**
4) **endPointTextMessage()**

```js
import { JitsiMeetExtended } from 'react-native-jitsi-meet-extended';

// code
// functions

JitsiMeetExtended.leaveMeet(); // used to leave a meeting 

JitsiMeetExtended.muteAudio(true); // used to mute and unmute audio, pass true or false 

JitsiMeetExtended.muteVideo(true); // used to disable or enable video, pass true or false 

JitsiMeetExtended.endPointTextMessage(to, message); // accepts to parameter and message,  haven't checked it yet



```
### Trouble setting it up ?
for linking related issues please check [https://github.com/skrafft/react-native-jitsi-meet](https://github.com/skrafft/react-native-jitsi-meet).

The package name of this package is 
**com.reactnativejitsimeetextended.JitsiMeetExtendedPackage**
and you can import it in mainActivity by calling 
**new JitsiMeetExtendedPackage()**

### Support on

[Patreon](https://www.patreon.com/sajanthomas01)

[Buymeacoffee](https://www.buymeacoffee.com/sajanthomas01)



This documentation was made possible using [https://www.makeareadme.com/](https://www.makeareadme.com/)

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
