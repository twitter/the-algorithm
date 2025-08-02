package com.twitter.search.earlybird.util;

import java.io.IOEtwitterception;
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
  public ViewerWriter beginArray() throws IOEtwitterception {
    writer.beginArray();
    return this;
  }

  @Override
  public ViewerWriter beginObject() throws IOEtwitterception {
    writer.beginObject();
    return this;
  }

  @Override
  public ViewerWriter endArray() throws IOEtwitterception {
    writer.endArray();
    return this;
  }

  @Override
  public ViewerWriter endObject() throws IOEtwitterception {
    writer.endObject();
    return this;
  }

  @Override
  public ViewerWriter name(String field) throws IOEtwitterception {
    writer.name(field);
    return this;
  }

  @Override
  public ViewerWriter value(String s) throws IOEtwitterception {
    writer.value(s);
    return this;
  }

  @Override
  public ViewerWriter newline() throws IOEtwitterception {
    out.append('\n');
    return this;
  }

  public void flush() throws IOEtwitterception {
    out.flush();
  }
}
