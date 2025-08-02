package com.twitter.search.earlybird.util;

import java.io.IOEtwitterception;

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
  ViewerWriter beginArray() throws IOEtwitterception;

  /**
   * Writes a mark for the beginning of an object.
   */
  ViewerWriter beginObject() throws IOEtwitterception;

  /**
   * Writes a mark for the end of an array.
   */
  ViewerWriter endArray() throws IOEtwitterception;

  /**
   * Writes a mark for the end of an object.
   */
  ViewerWriter endObject() throws IOEtwitterception;

  /**
   * Writes the name (key) of a property.
   */
  ViewerWriter name(String field) throws IOEtwitterception;

  /**
   * Writes the value of a property.
   */
  ViewerWriter value(String s) throws IOEtwitterception;

  /**
   * Writes a new line.
   */
  ViewerWriter newline() throws IOEtwitterception;
}
