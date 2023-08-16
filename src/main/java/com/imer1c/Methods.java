package com.imer1c;

import io.github.gaeqs.javayoutubedownloader.JavaYoutubeDownloader;
import io.github.gaeqs.javayoutubedownloader.decoder.MultipleDecoderMethod;
import io.github.gaeqs.javayoutubedownloader.stream.StreamOption;
import io.github.gaeqs.javayoutubedownloader.stream.YoutubeVideo;
import io.github.gaeqs.javayoutubedownloader.tag.StreamType;

import java.io.IOException;

public class Methods {
    public static void run(ArgumentsParser parser, String[] args)
    {
        String link = parser.getLink();

        YoutubeVideo video = JavaYoutubeDownloader.decodeOrNull(link, MultipleDecoderMethod.AND, "html");

        String output;
        if (Utils.containsArray(args, "--v-encoders"))
        {
            output = Main.discover(video, StreamType::getVideoEncoding);
        }
        else if (Utils.containsArray(args, "--a-encoders"))
        {
            output = Main.discover(video, StreamType::getAudioEncoding);
        }
        else if (Utils.containsArray(args, "--formats"))
        {
            output = Main.discover(video, StreamType::getContainer);
        }
        else if (Utils.containsArray(args, "--v-qualities"))
        {
            output = Main.discover(video, StreamType::getVideoQuality);
        }
        else if (Utils.containsArray(args, "--a-qualities"))
        {
            output = Main.discover(video, StreamType::getAudioQuality);
        }
        else if (Utils.containsArray(args, "--fps-values"))
        {
            output = Main.discover(video, StreamType::getFps);
        }
        else if (Utils.containsArray(args, "--link"))
        {
            output = Main.discoverLink(video, parser).getUrl().toString();
        }
        else
        {
            try
            {
                StreamOption option = Main.discoverLink(video, parser);
                new ProcessBuilder("fdm", "--fs", "--download", option.getUrl().toString()).inheritIO().start().waitFor();
            }
            catch (IOException | InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            output = "Downloading now using free download manager";
        }

        System.out.println(output);
    }
}
