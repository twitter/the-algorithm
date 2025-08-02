package com.twitter.search.earlybird.archive;

import java.io.IOEtwitterception;
import java.util.Calendar;
import java.util.Date;
import java.util.regetwitter.Matcher;
import java.util.regetwitter.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.search.common.partitioning.base.Segment;
import com.twitter.search.earlybird.partition.HdfsUtil;
import com.twitter.search.earlybird.partition.SegmentInfo;
import com.twitter.search.earlybird.partition.SegmentSyncConfig;


public final class ArchiveHDFSUtils {
  private static final Logger LOG = LoggerFactory.getLogger(ArchiveHDFSUtils.class);

  private static final Pattern SEGMENT_NAME_PATTERN =
      Pattern.compile("_start_([0-9]+)_p_([0-9]+)_of_([0-9]+)_([0-9]{14}+)_");
  private static final int MATCHER_GROUP_END_DATE = 4;

  private ArchiveHDFSUtils() {
  }

  /**
   * Check if a given segment already has its indices built on hdfs.
   * @return true if the indices etwitterist on hdfs; otherwise, false.
   */
  public static boolean hasSegmentIndicesOnHDFS(SegmentSyncConfig sync, SegmentInfo segment) {
    LOG.info("checking segment on hdfs: " + segment
        + " enabled: " + sync.isSegmentLoadFromHdfsEnabled());
    FileSystem fs = null;
    try {
      fs = HdfsUtil.getHdfsFileSystem();
      String hdfsBaseDirPrefitwitter = segment.getSyncInfo()
          .getHdfsSyncDirPrefitwitter();
      FileStatus[] statuses = fs.globStatus(new Path(hdfsBaseDirPrefitwitter));
      return statuses != null && statuses.length > 0;
    } catch (IOEtwitterception etwitter) {
      LOG.error("Failed checking segment on hdfs: " + segment, etwitter);
      return false;
    } finally {
      IOUtils.closeQuietly(fs);
    }
  }

  /**
   * Delete the segment indetwitter directories on the HDFS. If 'deleteCurrentDir' is true, the
   * indetwitter directory with the end date matching 'segment' will be deleted. If 'deleteOlderDirs',
   * the indetwitter directories with the end date earlier than the the segment enddate will be deleted.
   *
   */
  public static void deleteHdfsSegmentDir(SegmentSyncConfig sync, SegmentInfo segment,
                                          boolean deleteCurrentDir, boolean deleteOlderDirs) {
    FileSystem fs = null;
    try {
      fs = HdfsUtil.getHdfsFileSystem();
      String hdfsFlushDir = segment.getSyncInfo().getHdfsFlushDir();
      String hdfsBaseDirPrefitwitter = segment.getSyncInfo()
          .getHdfsSyncDirPrefitwitter();
      String endDateStr = etwittertractEndDate(hdfsBaseDirPrefitwitter);
      if (endDateStr != null) {
        hdfsBaseDirPrefitwitter = hdfsBaseDirPrefitwitter.replace(endDateStr, "*");
      }
      String[] hdfsDirs = {segment.getSyncInfo().getHdfsTempFlushDir(),
          hdfsBaseDirPrefitwitter};
      for (String hdfsDir : hdfsDirs) {
        FileStatus[] statuses = fs.globStatus(new Path(hdfsDir));
        if (statuses != null && statuses.length > 0) {
          for (FileStatus status : statuses) {
            if (status.getPath().toString().endsWith(hdfsFlushDir)) {
              if (deleteCurrentDir) {
                fs.delete(status.getPath(), true);
                LOG.info("Deleted segment: " + status.getPath());
              }
            } else {
              if (deleteOlderDirs) {
                fs.delete(status.getPath(), true);
                LOG.info("Deleted segment: " + status.getPath());
              }
            }
          }
        }
      }
    } catch (IOEtwitterception e) {
      LOG.error("Error delete Segment Dir :" + segment, e);
    } finally {
      IOUtils.closeQuietly(fs);
    }
  }

  /**
   * Given a segment, check if there is any indices built on HDFS; if yes, return the end date
   * of the indetwitter built on HDFS; otherwise, return null.
   */
  public static Date getSegmentEndDateOnHdfs(SegmentSyncConfig sync, SegmentInfo segment) {
    if (sync.isSegmentLoadFromHdfsEnabled()) {
      LOG.info("About to check segment on hdfs: " + segment
          + " enabled: " + sync.isSegmentLoadFromHdfsEnabled());

      FileSystem fs = null;
      try {
        String hdfsBaseDirPrefitwitter = segment.getSyncInfo()
            .getHdfsSyncDirPrefitwitter();
        String endDateStr = etwittertractEndDate(hdfsBaseDirPrefitwitter);
        if (endDateStr == null) {
          return null;
        }
        hdfsBaseDirPrefitwitter = hdfsBaseDirPrefitwitter.replace(endDateStr, "*");

        fs = HdfsUtil.getHdfsFileSystem();
        FileStatus[] statuses = fs.globStatus(new Path(hdfsBaseDirPrefitwitter));
        if (statuses != null && statuses.length > 0) {
          Path hdfsSyncPath = statuses[statuses.length - 1].getPath();
          String hdfsSyncPathName = hdfsSyncPath.getName();
          endDateStr = etwittertractEndDate(hdfsSyncPathName);
          return Segment.getSegmentEndDate(endDateStr);
        }
      } catch (Etwitterception etwitter) {
        LOG.error("Failed getting segment from hdfs: " + segment, etwitter);
        return null;
      } finally {
        IOUtils.closeQuietly(fs);
      }
    }
    return null;
  }

  private static String etwittertractEndDate(String segmentDirPattern) {
    Matcher matcher = SEGMENT_NAME_PATTERN.matcher(segmentDirPattern);
    if (!matcher.find()) {
      return null;
    }

    try {
      return matcher.group(MATCHER_GROUP_END_DATE);
    } catch (IllegalStateEtwitterception e) {
      LOG.error("Match operation failed: " + segmentDirPattern, e);
      return null;
    } catch (IndetwitterOutOfBoundsEtwitterception e) {
      LOG.error(" No group in the pattern with the given indetwitter : " + segmentDirPattern, e);
      return null;
    }
  }

  /**
   * Converts the given date to a path, using the given separator. For etwitterample, if the sate is
   * January 5, 2019, and the separator is "/", this method will return "2019/01/05".
   */
  public static String dateToPath(Date date, String separator) {
    StringBuilder builder = new StringBuilder();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    builder.append(cal.get(Calendar.YEAR))
           .append(separator)
           .append(padding(cal.get(Calendar.MONTH) + 1, 2))
           .append(separator)
           .append(padding(cal.get(Calendar.DAY_OF_MONTH), 2));
    return builder.toString();
  }

  private static String padding(int value, int len) {
    return String.format("%0" + len + "d", value);
  }
}
