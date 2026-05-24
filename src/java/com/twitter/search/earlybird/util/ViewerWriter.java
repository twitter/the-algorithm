package com.twitter.search.earlybird.util;

import java.io.IOException;

/**
 * Interface class for writer.  Writer should be passed in
 * and have these methods.  Currently keeps the hierarchy for
 * completed and valid json, methods mirror the ones found in
 * JsonWriter
 * http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/stream/JsonWriter.html
 */
public interface ViewerWriter {
  /**
   * Writes a mark for the beginning of an array.
   */
  ViewerWriter beginArray() throws IOException;

  /**
   * Writes a mark for the beginning of an object.
   */
  ViewerWriter beginObject() throws IOException;

  /**
   * Writes a mark for the end of an array.
   */
  ViewerWriter endArray() throws IOException;

  /**
   * Writes a mark for the end of an object.
   */
  ViewerWriter endObject() throws IOException;

  /**
   * Writes the name (key) of a property.
   */
  ViewerWriter name(String field) throws IOException;

  /**
   * Writes the value of a property.
   */
  ViewerWriter value(String s) throws IOException;

  /**
   * Writes a new line.
   */
  ViewerWriter newline() throws IOException;
}
