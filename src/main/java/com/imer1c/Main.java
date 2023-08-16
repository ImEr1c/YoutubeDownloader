package com.imer1c;

import io.github.gaeqs.javayoutubedownloader.JavaYoutubeDownloader;
import io.github.gaeqs.javayoutubedownloader.decoder.MultipleDecoderMethod;
import io.github.gaeqs.javayoutubedownloader.stream.StreamOption;
import io.github.gaeqs.javayoutubedownloader.stream.YoutubeVideo;
import io.github.gaeqs.javayoutubedownloader.stream.download.StreamDownloader;
import io.github.gaeqs.javayoutubedownloader.stream.download.StreamDownloaderNotifier;
import io.github.gaeqs.javayoutubedownloader.tag.Encoding;
import io.github.gaeqs.javayoutubedownloader.tag.StreamType;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {

        if (args[0].startsWith("http"))
        {
            ArgumentsParser parser = new ArgumentsParser(args[0], args);

            Methods.run(parser, args);
        }
        else
        {
            File f = new File(args[0]);

            try
            {
                Scanner scanner = new Scanner(f);

                ArgumentsParser parser = new ArgumentsParser(scanner.nextLine().trim(), args);

                Methods.run(parser, args);
            }
            catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public static StreamOption discoverLink(YoutubeVideo video, ArgumentsParser parser)
    {
        Predicate<StreamType> streamTypePredicate = parser.buildPredicate();
        StreamOption option = video.getStreamOptions().stream()
                .filter(target -> streamTypePredicate.test(target.getType()))
                .filter(target -> parser.isAudioOnly() ? target.getType().getAudioQuality() != null : target.getType().getVideoQuality() != null)
                .min(Comparator.comparingInt(o -> parser.isAudioOnly() ? o.getType().getAudioQuality().ordinal() : o.getType().getVideoQuality().ordinal())).orElse(null);

        return option;
    }

    public static String discover(YoutubeVideo video, Option<?> option)
    {
        List<String> list = new ArrayList<>();

        video.getStreamOptions().forEach(o -> list.add(option.get(o.getType()).toString()));

        return "Available: " + Arrays.toString(Utils.removeDuplicates(list).toArray());
    }

    public static void error(String err)
    {
        System.out.println(err);
        System.exit(0);
    }
}