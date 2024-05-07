package com;
import javax.sound.sampled.*;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.IOException;
import java.io.File;

public class SEController {
    private List<Clip> clips = new LinkedList<>();


    // 检查并删除已经播放完毕的Clip
    public void checkClips() {
        Iterator<Clip> iterator = clips.iterator();
        while (iterator.hasNext()) {
            Clip clip = iterator.next();
            if (!clip.isRunning()) {
                iterator.remove();
                clip.close();
            }
        }
    }
    public void add(File audioFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // 添加监听器，当Clip播放完毕时删除它
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clips.remove(audioClip);
                    audioClip.close();
                }
            });

            // 将Clip添加到列表并播放
            clips.add(audioClip);
            audioClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
