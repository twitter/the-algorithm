package com.twitter.search.earlybird.tools;

import java.io.BufferedReader;
import java.io.IOEtwitterception;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.base.Preconditions;

import org.apache.commons.codec.binary.Base64;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TEtwitterception;

import com.twitter.search.earlybird.thrift.EarlybirdRequest;

/**
 *
 * This tool deserializes the collected thrift requests into human readable format.
 *
 * Takes zero or one parameter: path to the thrift request log file.
 *
 * To run: Launch main from IntelliJ / Eclipse.
 */
public final class EarlybirdThriftRequestDeserializerUtil {
  private static final String DEFAULT_LOG_FILE_LOCATION = "/tmp/eb_req.B64";
  // Not threadsafe. Single thread main().
  private static final Base64 B64 = new Base64(0);
  private static final TDeserializer DESERIALIZER = new TDeserializer();

  private EarlybirdThriftRequestDeserializerUtil() {
  }

  /**
   * Runs the EarlybirdThriftRequestDeserializerUtil tool with the given command-line arguments.
   */
  public static void main(String[] args) throws IOEtwitterception {
    Path logFile = null;
    if (args.length == 1) {
      logFile = FileSystems.getDefault().getPath(args[0]);
    } else if (args.length == 0) {
      logFile = FileSystems.getDefault().getPath(DEFAULT_LOG_FILE_LOCATION);
    } else {
      System.err.println("Usage: takes zero or one parameter (log file path). "
          + "If no log file is specified, " + DEFAULT_LOG_FILE_LOCATION + " is used.");
      //CHECKSTYLE:OFF RegetwitterpSinglelineJava
      System.etwitterit(-1);
      //CHECKSTYLE:ON RegetwitterpSinglelineJava
    }
    Preconditions.checkState(logFile.toFile().etwitterists());

    BufferedReader reader = Files.newBufferedReader(logFile, Charset.defaultCharset());
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        EarlybirdRequest ebRequest = deserializeEBRequest(line);
        if (ebRequest != null) {
          System.out.println(ebRequest);
        }
      }
    } finally {
      reader.close();
    }
  }

  private static EarlybirdRequest deserializeEBRequest(String line) {
    EarlybirdRequest ebRequest = new EarlybirdRequest();
    byte[] bytes = B64.decode(line);
    try {
      DESERIALIZER.deserialize(ebRequest, bytes);
    } catch (TEtwitterception e) {
      System.err.println("Error deserializing thrift.");
    }
    return ebRequest;
  }
}
