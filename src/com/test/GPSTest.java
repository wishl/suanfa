package com.test;

import java.util.Arrays;

public class GPSTest {

    static class CarStatus{
        private long CollectTime;
        private int CarDoorStatus;
        private int CarDoorLock;
        private int CarWindowStatus;
        private int CarSunroofStatus;
        private int CarLight;
        private int CarLightExt;
        private int CarTrunk;
        private int CarKeyStatus;

        @Override
        public String toString() {
            return "CarStatus{" +
                    "CollectTime=" + CollectTime +
                    ", CarDoorStatus=" + CarDoorStatus +
                    ", CarDoorLock=" + CarDoorLock +
                    ", CarWindowStatus=" + CarWindowStatus +
                    ", CarSunroofStatus=" + CarSunroofStatus +
                    ", CarLight=" + CarLight +
                    ", CarLightExt=" + CarLightExt +
                    ", CarTrunk=" + CarTrunk +
                    ", CarKeyStatus=" + CarKeyStatus +
                    '}';
        }
    }

    static class Position{
        private int MotionStatus;
        private int FixStatus;
        private int GPSTime;
        private double Longitude;
        private double Latitude;
        private double Altitude;
        private int Speed;
        private int Azimuth;
        private int SNR;
        private int pAcc;

        @Override
        public String toString() {
            return "Position{" +
                    "MotionStatus=" + MotionStatus +
                    ", FixStatus=" + FixStatus +
                    ", GPSTime=" + GPSTime +
                    ", Longitude=" + Longitude +
                    ", Latitude=" + Latitude +
                    ", Altitude=" + Altitude +
                    ", Speed=" + Speed +
                    ", Azimuth=" + Azimuth +
                    ", SNR=" + SNR +
                    ", pAcc=" + pAcc +
                    '}';
        }
    }

    static class Message{
        private int FirmwareVersion;
        private int SoftwareVersion;
        private int HardwareVersion;
        private String ICCID;
        private String IMSI;

        @Override
        public String toString() {
            return "Message{" +
                    "FirmwareVersion=" + FirmwareVersion +
                    ", SoftwareVersion=" + SoftwareVersion +
                    ", HardwareVersion=" + HardwareVersion +
                    ", ICCID='" + ICCID + '\'' +
                    ", IMSI='" + IMSI + '\'' +
                    '}';
        }
    }

    static class WorkStatus{

        private long CollectTime;
        private int MotionStatus;
        private int GSM;
        private int SNR;
        private int Temperature;

        @Override
        public String toString() {
            return "WorkStatus{" +
                    "CollectTime=" + CollectTime +
                    ", MotionStatus=" + MotionStatus +
                    ", GSM=" + GSM +
                    ", SNR=" + SNR +
                    ", Temperature=" + Temperature +
                    '}';
        }
    }

    public static Position dealPositionData(byte[] bs){
        int index = 2;
        Position position = new Position();
        position.MotionStatus=bs[index++];
        position.FixStatus=bs[index++];
        byte[] tmp = new byte[4];
        System.arraycopy(bs, index, tmp, 0, 4);
        position.GPSTime=byteToInteger(tmp);
        index+=4;
        System.arraycopy(bs, index, tmp, 0, 4);
        position.Longitude=byteToInteger(tmp)/100000.00;
        index+=4;
        System.arraycopy(bs, index, tmp, 0, 4);
        position.Latitude=byteToInteger(tmp)/100000.00;
        index+=4;
        byte[] tmp1 = new byte[2];
        System.arraycopy(bs, index, tmp1, 0, 2);
        position.Altitude=byteToInteger(tmp1);
        index+=2;
        position.Speed=getSpeed(bs,index++);
        position.Azimuth=bs[index++]*2;
        position.SNR=bs[index++];
        position.pAcc=bs[index++];
        return position;
    }



    public static WorkStatus workStatus(byte[] bs){
        WorkStatus workStatus = new WorkStatus();
        int index = 2;
        byte[] tmp1 = new byte[4];
        System.arraycopy(bs, index, tmp1, 0, 4);
        workStatus.CollectTime=byteToLong(tmp1);
        index+=4;
        workStatus.MotionStatus=byteToInteger(bs[index]);
        index++;
        workStatus.GSM=byteToInteger(bs[index]);
        index++;
        workStatus.SNR=byteToInteger(bs[index]);
        index++;
        workStatus.Temperature=bs[index];
        index++;
        return workStatus;
    }

    public static Message getMessage(byte[] bs){
        Message message = new Message();
        int index = 2;
        byte[] tmp = new byte[2];
        System.arraycopy(bs, index, tmp, 0, 2);
        message.FirmwareVersion=byteToInteger(tmp);
        index+=2;
        System.arraycopy(bs, index, tmp, 0, 2);
        message.SoftwareVersion=byteToInteger(tmp);
        index+=2;
        message.HardwareVersion=byteToInteger(bs[index]);
        index++;
        byte[] tmp1 = new byte[10];
        System.arraycopy(bs, index, tmp1, 0, 10);
        message.ICCID=bytesToHexString(tmp1);
        index+=10;
        byte[] tmp2 = new byte[8];
        System.arraycopy(bs, index, tmp2, 0, 8);
        message.IMSI=bytesToHexString(tmp2);
        return message;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static final byte[] hexStringToBytes(String hex){
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static CarStatus getCarStatus(byte[] bs){
        CarStatus carStatus = new CarStatus();
        int index = 2;
        byte[] tmp = new byte[4];
        System.arraycopy(bs, index, tmp, 0, 4);
        carStatus.CollectTime = byteToLong(tmp);
        index+=4;
        carStatus.CarDoorStatus = byteToInteger(bs[index++]);
        carStatus.CarDoorLock = byteToInteger(bs[index++]);
        carStatus.CarWindowStatus = byteToInteger(bs[index++]);
        carStatus.CarSunroofStatus = byteToInteger(bs[index++]);
        carStatus.CarLight = byteToInteger(bs[index++]);
        carStatus.CarLightExt = byteToInteger(bs[index++]);
        carStatus.CarTrunk = byteToInteger(bs[index++]);
        carStatus.CarKeyStatus = byteToInteger(bs[index++]);
        return carStatus;
    }

    public static void main(String[] args) {
//        byte[] bs = {0x01, 0x03, 0x02, 0x00, 0x5D, 0x13, 0x3D, 0x6A, 0x00, (byte)0xAD, 0x4B, 0x57, 0x00, 0x22, 0x24, 0x04, 0x00, 0x01, (byte) 0xAD, 0x06, 0x7B, 0x32};
//        Position position = dealPositionData(bs);
//        System.out.println(position);
//        byte[] bs = {0x01, 0x02, 0x5D, 0x13, 0x38, (byte) 0xE9, 0x00, 0x1A, 0x16, 0x1D};
//        WorkStatus workStatus = workStatus(bs);
//        System.out.println(workStatus);
//        byte[] bs = {0x01, 0x01, 0x00, 0x01, 0x00, 0x00, 0x4A, (byte) 0x89, (byte) 0x86, 0x07, (byte) 0xB8, 0x10, 0x17, 0x30, 0x04, 0x50, 0x35, 0x04, 0x60, 0x04, 0x32, 0x60, 0x30, 0x01, 0x23};
//        Message message = getMessage(bs);
//        System.out.println(message);
          byte[] bs = {0x01, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x15, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
        System.out.println(Arrays.toString(bs));
//        CarStatus carStatus = getCarStatus(bs);
//        System.out.println(carStatus);
    }

    private static int getSpeed(byte[] bs,int index){
        int b = bs[index]&0xFF;
        if(b<127){
            return b;
        }
        return 2*b-127;
    }

    // 获取有符号的int
    public static int getValue(byte[] bs){// ？
        int result = 0;
        if(bs[0]<=0xff){// 负数
            for (int i = 0; i < bs.length; i++) {
                int b = ~bs[i];
                result = (result<<8)+b+1;
            }
            result *= -1;
        }else{
            for (int i = 0; i < bs.length; i++) {
                result = (result<<8) + (bs[i]&0xFF);
            }
        }
        return result;
    }


    public static long byteToLong(byte[] value) {
        int result = 0;
        for (int i = 0; i < value.length; i++) {
            result = (result<<8) + (value[i]&0xFF);
        }
        return result;
    }

    public static int byteToInteger(byte...value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }


    public static int fourBytesToInteger(byte[] value) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    public static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    public static int twoBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    public static int oneByteToInteger(byte value) {
        return value & 0xFF;
    }

}
