package com.twitter.search.earlybird.util;

import java.io.IOException;
import java.io.Writer;

import com.google.gson.stream.JsonWriter;

/**
 * Wrapper class for JsonWriter that implements the
 * ViewerWriter interface.
 */
public class JsonViewerWriter implements ViewerWriter {

  private final JsonWriter writer;
  private final Writer out;

  public JsonViewerWriter(Writer out) {
    this.out = out;
    this.writer = new JsonWriter(out);
  }


  @Override
  public ViewerWriter beginArray() throws IOException {
    writer.beginArray();
    return this;
  }

  @Override
  public ViewerWriter beginObject() throws IOException {
    writer.beginObject();
    return this;
  }

  @Override
  public ViewerWriter endArray() throws IOException {
    writer.endArray();
    return this;
  }

  @Override
  public ViewerWriter endObject() throws IOException {
    writer.endObject();
    return this;
  }

  @Override
  public ViewerWriter name(String field) throws IOException {
    writer.name(field);
    return this;
  }

  @Override
  public ViewerWriter value(String s) throws IOException {
    writer.value(s);
    return this;
  }

  @Override
  public ViewerWriter newline() throws IOException {
    out.append('\n');
    return this;
  }

  public void flush() throws IOException {
    out.flush();
  }
}
