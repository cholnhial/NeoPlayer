# NeoPlayer

## What is NeoPlayer
NeoPlayer is a command-line MP3 player that I created to demonstrate picoli (http://picocli.info/) command arguments
parsing abilities. It uses another library JACo MP3 Player to play MP3 files.

## How to build it

To build you will need maven installed. Simply run:
    
    $ mvn package
    
The jar will be in `target` directory of the projeect.
## How to use it

### Show usage
To display usage:

    $ java -jar neoplayer.jar --help

### Play some music    
Playing  single file or a list of files
    
    $ java -jar neoplayer.jar file1.mp3 file2.mp3
    
Playing from a playlist containing songs like:

`/home/bob/Music/Bob Marley - No Woman No Cry.mp3
/home/bob/Music/Justin Bieber - Baby`

    $ java -jar neoplayer.jar -l playlist.lst

### Special options
A few other options allow you to shuffle and repeat a playlist or file.

To <b>repeat</b> a playlist or file add the `-r` option.

To <b>shuffle</b> shuffle the playlist you add the `-s` option.