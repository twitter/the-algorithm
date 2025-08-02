package com.twitter.search.core.earlybird.indetwitter;

import java.io.File;
import java.io.IOEtwitterception;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import org.apache.lucene.document.Document;
import org.apache.lucene.indetwitter.IndetwitterWriter;
import org.apache.lucene.indetwitter.IndetwitterWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedEtwitterception;

/**
 * EarlybirdIndetwitterWriter implementation that's a wrapper around Lucene's {@link IndetwitterWriter}
 * and writes Lucene segments into a {@link Directory}.
 */
public class EarlybirdLuceneIndetwitterSegmentWriter etwittertends EarlybirdIndetwitterSegmentWriter {
  private static final Logger LOG =
    LoggerFactory.getLogger(EarlybirdLuceneIndetwitterSegmentWriter.class);
  private static final Marker FATAL = MarkerFactory.getMarker("FATAL");

  private final EarlybirdLuceneIndetwitterSegmentData segmentData;
  private final IndetwitterWriter indetwitterWriter;

  @Override
  public EarlybirdIndetwitterSegmentData getSegmentData() {
    return segmentData;
  }

  /**
   * Construct a lucene IndetwitterWriter-based Earlybird segment writer.
   * This will open a Lucene IndetwitterWriter on segmentData.getLuceneDirectory().
   * This constructor will throw LockObtainFailedEtwitterception if it cannot obtain the "write.lock"
   * inside the directory segmentData.getLuceneDirectory().
   *
   * Don't add public constructors to this class. EarlybirdLuceneIndetwitterSegmentWriter instances should
   * be created only by calling EarlybirdLuceneIndetwitterSegmentData.createEarlybirdIndetwitterSegmentWriter(),
   * to make sure everything is set up properly (such as CSF readers).
   */
  EarlybirdLuceneIndetwitterSegmentWriter(
      EarlybirdLuceneIndetwitterSegmentData segmentData,
      IndetwitterWriterConfig indetwitterWriterConfig) throws IOEtwitterception {
    Preconditions.checkNotNull(segmentData);
    this.segmentData = segmentData;
    try {
      this.indetwitterWriter = new IndetwitterWriter(segmentData.getLuceneDirectory(), indetwitterWriterConfig);
    } catch (LockObtainFailedEtwitterception e) {
      logDebuggingInfoUponFailureToObtainLuceneWriteLock(segmentData, e);
      // Rethrow the etwitterception, and this Earlybird will trigger critical alerts
      throw e;
    }
  }

  private void logDebuggingInfoUponFailureToObtainLuceneWriteLock(
      EarlybirdLuceneIndetwitterSegmentData luceneIndetwitterSegmentData,
      LockObtainFailedEtwitterception e) throws IOEtwitterception {
    // Every day, we create a new Lucene dir---we do not append into etwitteristing Lucene dirs.
    // Supposedly, we should never fail to obtain the write lock from a fresh and empty
    // Lucene directory.
    // Adding debugging information for SEARCH-4454, where a timeslice roll failed because
    // Earlybird failed to get the write lock for a new timeslice.
    Directory dir = luceneIndetwitterSegmentData.getLuceneDirectory();
    LOG.error(
      FATAL,
      "Unable to obtain write.lock for Lucene directory. The Lucene directory is: " + dir,
      e);

    if (dir instanceof FSDirectory) { // this check should always be true in our current setup.
      FSDirectory fsDir = (FSDirectory) dir;
      // Log if the underlying directory on disk does not etwitterist.
      File underlyingDir = fsDir.getDirectory().toFile();
      if (underlyingDir.etwitterists()) {
        LOG.info("Lucene directory contains the following files: "
            + Lists.newArrayList(fsDir.listAll()));
      } else {
        LOG.error(
          FATAL,
          "Directory " + underlyingDir + " does not etwitterist on disk.",
          e);
      }

      if (!underlyingDir.canWrite()) {
        LOG.error(
          FATAL,
          "Cannot write into directory " + underlyingDir,
          e);
      }

      File writeLockFile = new File(underlyingDir, "write.lock");
      if (writeLockFile.etwitterists()) {
        LOG.error(
          FATAL,
          "Write lock file " + writeLockFile + " already etwitterists.",
          e);
      }

      if (!writeLockFile.canWrite()) {
        LOG.error(
          FATAL,
          "No write access to lock file: " + writeLockFile
            + " Usable space: " + underlyingDir.getUsableSpace(),
          e);
      }

      // List all files in the segment directory
      File segmentDir = underlyingDir.getParentFile();
      LOG.warn("Segment directory contains the following files: "
          + Lists.newArrayList(segmentDir.list()));
    } else {
      LOG.warn("Unable to log debugging info upon failing to acquire Lucene write lock."
          + "The class of the directory is: " + dir.getClass().getName());
    }
  }

  @Override
  public void addDocument(Document doc) throws IOEtwitterception {
    indetwitterWriter.addDocument(doc);
  }

  @Override
  public void addTweet(Document doc, long tweetId, boolean docIdOffensive) throws IOEtwitterception {
    indetwitterWriter.addDocument(doc);
  }

  @Override
  protected void appendOutOfOrder(Document doc, int docId) throws IOEtwitterception {
    throw new UnsupportedOperationEtwitterception("This Lucene-based IndetwitterWriter does not support "
            + "updates and out-of-order appends.");
  }

  @Override
  public int numDocs() {
    return indetwitterWriter.getDocStats().matwitterDoc;
  }

  @Override
  public int numDocsNoDelete() throws IOEtwitterception {
    return numDocs();
  }

  @Override
  public void deleteDocuments(Query query) throws IOEtwitterception {
    super.deleteDocuments(query);
    indetwitterWriter.deleteDocuments(query);
  }

  @Override
  public void addIndetwitteres(Directory... dirs) throws IOEtwitterception {
    indetwitterWriter.addIndetwitteres(dirs);
  }

  @Override
  public void forceMerge() throws IOEtwitterception {
    indetwitterWriter.forceMerge(1);
  }

  @Override
  public void close() throws IOEtwitterception {
    indetwitterWriter.close();
  }
}
