package com.imer1c;

import io.github.gaeqs.javayoutubedownloader.tag.*;

import java.util.function.Predicate;

public class ArgumentsParser {
    private final String link;
    private final boolean videoOnly;
    private final boolean audioOnly;
    private final Encoding videoEncoding, audioEncoding;
    private final Container container;
    private final VideoQuality videoQuality;
    private final AudioQuality audioQuality;
    private final FPS fps;

    public ArgumentsParser(String link, String[] args)
    {
        if (args.length == 0)
        {
            throw new RuntimeException("There are no arguments, so no link");
        }

        this.link = link;
        this.audioOnly = Utils.containsArray(args, "--audio-only");
        this.videoOnly = Utils.containsArray(args, "--video-only");

        if (this.audioOnly && this.videoOnly)
        {
            throw new RuntimeException("Can't have both video and audio only");
        }

        this.videoEncoding = Utils.containsArray(args, "--v-encoding") ? Encoding.valueOf(Utils.nextInArray(args, "--v-encoding").toUpperCase()) : null;
        this.audioEncoding= Utils.containsArray(args, "--a-encoding") ? Encoding.valueOf(Utils.nextInArray(args, "--a-encoding").toUpperCase()) : null;
        this.container = Utils.containsArray(args, "--format") ? Container.valueOf(Utils.nextInArray(args, "--format").toUpperCase()) : null;
        this.videoQuality = Utils.containsArray(args, "--v-quality") ? VideoQuality.valueOf(Utils.moveLetterToStart(Utils.nextInArray(args, "--v-quality")).toUpperCase()) : null;
        this.audioQuality = Utils.containsArray(args, "--a-quality") ? AudioQuality.valueOf(Utils.moveLetterToStart(Utils.nextInArray(args, "--a-quality")).toUpperCase()) : null;
        this.fps = Utils.containsArray(args, "--fps") ? (Utils.nextInArray(args, "--fps").equals("30") ? FPS.f30 : FPS.f60) : null;
    }

    public String getLink()
    {
        return link;
    }

    public Predicate<StreamType> buildPredicate()
    {
        return type ->
                ((this.videoOnly && !type.hasAudio()) ||
                        (this.audioOnly && !type.hasVideo()) ||
                        (!this.videoOnly && !this.audioOnly))
                &&
                        this.equalOrNull(this.videoEncoding, type.getVideoEncoding())
                &&
                        this.equalOrNull(this.audioEncoding, type.getAudioEncoding())
                &&
                        this.equalOrNull(this.container, type.getContainer())
                &&
                        this.equalOrNull(this.videoQuality, type.getVideoQuality())
                &&
                        this.equalOrNull(this.audioQuality, type.getAudioQuality())
                &&
                        this.equalOrNull(this.fps, type.getFps());
    }

    private boolean equalOrNull(Object o, Object o1)
    {
        return o == null || o.equals(o1);
    }

    public boolean isAudioOnly()
    {
        return this.audioOnly;
    }
}
