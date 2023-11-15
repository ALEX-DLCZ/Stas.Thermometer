package stas.thermometer.presentations;

public class MsgOrganizer<T1,T2>{

    // T1 Type of message :MsgAlert, MsgMeasurement,...
    private T1 msg1;

    // T2 Type of measurement : MsgTemperature, MsgHumidity,...
    private T2 msg2;

    public MsgOrganizer(T1 msg1, T2 msg2) {
        this.msg1 = msg1;
        this.msg2 = msg2;
    }

    public T1 getMsg1() {
        return msg1;
    }

    public void setMsg1(T1 msg1) {
        this.msg1 = msg1;
    }

    public T2 getMsg2() {
        return msg2;
    }

    public void setMsg2(T2 msg2) {
        this.msg2 = msg2;
    }
}
