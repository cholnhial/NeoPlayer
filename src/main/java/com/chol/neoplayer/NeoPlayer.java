package com.chol.neoplayer;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine;

import java.io.File;

@Command(name = "neoplayer", footer = "Copyright(c) 2017", description = "A simple MP3 music player.")
public class NeoPlayer
{
    @Option(names = {"-v", "--verbose"}, description = "Be verbose.")
    private boolean verbose = false;

    @Parameters(arity = "1...*", paramLabel = "FILE", description = "MP3 File(s) to play.")
    private File[] mp3FilesToPlay;

    @Option(names = {"-l', '--playlist"}, paramLabel = "FILE", description = "Play MP3 files listed in file.")
    private File playListFile;

    @Option(names = {"-r --repeat"}, description = "Repeat song or playlist.")
    private boolean isRepeatSet = false;

    @Option(names = {"-h", "--help"}, help = true, description = "Display this here help message.")
    private boolean hasRequestedHelp = false;

    public boolean hasHelpBeenRequested() {
        return hasRequestedHelp;
    }
    public static int main( String[] args)
    {
        NeoPlayer neoPlayer = CommandLine.populateCommand(new NeoPlayer(), args);

        if(neoPlayer.hasHelpBeenRequested()) {
            CommandLine.usage(neoPlayer, System.err);
            return 0;
        }

        return 0;
    }
}
