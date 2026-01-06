package org.example.movieapi.controller.tu.fixture;

import oracle.net.ns.Message;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonProvider {
    private static <T> boolean addField(
            StringBuilder builder,
            String fieldName,
            T fieldValue,
            boolean quoteValue,
            boolean previous)
    {
        if (Objects.isNull(fieldValue)) return previous;
        if (previous) builder.append(", ");
        builder.append('"')
                .append(fieldName)
                .append("\": ");
        if (quoteValue) builder.append('"');
        builder.append(fieldValue);
        if (quoteValue) builder.append('"');
        return true;
    }

    public static String movieJSON(String title, Integer releaseYear, Integer duration, String genres){
        var builder = new StringBuilder("{");
        boolean previous = false;
        previous = addField(builder, "title", title, true, previous);
        previous = addField(builder, "releaseYear", releaseYear, false, previous);
        previous = addField(builder, "duration", duration, false, previous);
        String genresJson = Objects.isNull(genres)
                ? "[]"
                : "["
                + Arrays.stream(genres.split(","))
                        .map(genre -> "\"" + genre + "\"")
                                .collect(Collectors.joining(", "))
                + "]";
        addField(builder, "genres", genresJson, false, previous);
        return builder.append("}").toString();
    }
}
