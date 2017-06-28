package com.chol.neoplayer;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import jaco.mp3.player.MP3Player;
import picocli.CommandLine;
import com.mpatric.mp3agic.Mp3File;


public class NeoPlayer
{
    private MP3Player player;
    private NeoPlayerCommandOptions neoPlayerCommandOptions;
    private long milliSecondsToPlaySongs;

    public NeoPlayer(String args[]) {
        neoPlayerCommandOptions = CommandLine.populateCommand(new NeoPlayerCommandOptions(), args);
        player = new MP3Player();
        milliSecondsToPlaySongs = 0;
    }

    public void addFilesToPlayList(File[] mp3Files) throws IOException, UnsupportedTagException, InvalidDataException {
        for(File file : mp3Files) {
            Mp3File mp3File = new Mp3File(file.getAbsolutePath());
            milliSecondsToPlaySongs += mp3File.getLengthInMilliseconds();
            player.addToPlayList(file);
        }
    }

    public void addFileFromPlayList(File file) throws IOException, UnsupportedTagException, InvalidDataException {
        addFilesToPlayList(readFileList(file));
    }

    private File[] readFileList(File file) throws IOException {
        List<File> fileList = new ArrayList<>();

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null) {
            fileList.add(new File(line.trim()));
        }

        return fileList.toArray(new File[fileList.size()]);
    }

    public MP3Player getPlayer() {
        return player;
    }
    

    public NeoPlayerCommandOptions getNeoPlayerCommandOptions() {
        return neoPlayerCommandOptions;
    }
    
    public void waitForPlayer() {
        try {
            Thread.sleep(milliSecondsToPlaySongs);
        }
        catch (InterruptedException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args)
    {
        NeoPlayer neoPlayer = new NeoPlayer(args);

        try {
            if (neoPlayer.getNeoPlayerCommandOptions().hasHelpBeenRequested()) {
                CommandLine.usage(neoPlayer.getNeoPlayerCommandOptions(), System.err);
                return;
            }

            if (neoPlayer.getNeoPlayerCommandOptions().getMp3FilesToPlay() != null &&
                    neoPlayer.getNeoPlayerCommandOptions().getMp3FilesToPlay().length > 0) {
                neoPlayer.addFilesToPlayList(neoPlayer.getNeoPlayerCommandOptions().getMp3FilesToPlay());
            }

            if (neoPlayer.getNeoPlayerCommandOptions().getPlayListFile() != null) {
                neoPlayer.addFileFromPlayList(neoPlayer.getNeoPlayerCommandOptions().getPlayListFile());
            }

            neoPlayer.getPlayer().setRepeat(neoPlayer.getNeoPlayerCommandOptions().isRepeatSet());

            neoPlayer.getPlayer().play();
        }
        catch (FileNotFoundException ex) {
            System.err.println("ERROR: Playlist file or other files not found: " + ex.getMessage());
            System.exit(-1);
        }
        catch (IOException ex) {
            System.err.println("ERROR: An IO exception occurred");
            System.exit(-1);
        }
        catch(InvalidDataException|UnsupportedTagException ex) {
            System.err.printf("ERROR: an ID3 error occurred(%s)\n", ex.getMessage());
        }


        System.out.println("Playing...");
        neoPlayer.getPlayer().play();
        neoPlayer.waitForPlayer();
        
    }
}
