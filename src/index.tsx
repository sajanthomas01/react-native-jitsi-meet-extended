import { NativeModules } from 'react-native';

type JitsiMeetExtendedType = {
  multiply(a: number, b: number): Promise<number>;
};

const { JitsiMeetExtended } = NativeModules;

export default JitsiMeetExtended as JitsiMeetExtendedType;
