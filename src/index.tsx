import { NativeModules, requireNativeComponent } from 'react-native';

const { JitsiMeetExtended } = NativeModules;
const JitsiMeetView = requireNativeComponent("JitsiView");

export  { JitsiMeetExtended, JitsiMeetView};
