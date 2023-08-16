package com.imer1c;

import io.github.gaeqs.javayoutubedownloader.tag.StreamType;

public interface Option<O> {
    O get(StreamType type);
}
