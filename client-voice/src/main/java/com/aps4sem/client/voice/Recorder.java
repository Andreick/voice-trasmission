package com.aps4sem.client.voice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;

public class Recorder extends Thread {
    
    public TargetDataLine audio_in;
    public DatagramSocket data_out;
    public InetAddress serverIP;
    public int serverPort;
    
    private final byte byteBuffer[] = new byte[512];

    @Override
    public void run() {
        int i = 0;
        while (ClientVoice.calling)
        {
            try {
                audio_in.read(byteBuffer, 0, byteBuffer.length);
                DatagramPacket data = new DatagramPacket(byteBuffer, byteBuffer.length, serverIP, serverPort);
                System.out.println("send #" + i++);
                data_out.send(data);
            } catch (IOException ex) {
                Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        audio_in.close();
        audio_in.drain();
        System.out.println("Recorder stop");
    }
}