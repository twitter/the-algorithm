package com.twitter.ann.service.query_server.common

import com.twitter.logging.Logger
import com.twitter.search.common.file.AbstractFile
import scala.collection.JavaConverters._

object QueryServerUtil {

  private val log = Logger.get("QueryServerUtil")

  /**
   * Validate if the abstract file (directory) size is within the defined limits.
   * @param dir Hdfs/Local directory
   * @param minIndexSizeBytes minimum size of file in bytes (Exclusive)
   * @param maxIndexSizeBytes minimum size of file in bytes (Exclusive)
   * @return true if file size within minIndexSizeBytes and maxIndexSizeBytes else false
   */
  def isValidIndexDirSize(
    dir: AbstractFile,
    minIndexSizeBytes: Long,
    maxIndexSizeBytes: Long
  ): Boolean = {
    val recursive = true
    val dirSize = dir.listFiles(recursive).asScala.map(_.getSizeInBytes).sum

    log.debug(s"Ann index directory ${dir.getPath} size in bytes $dirSize")

    val isValid = (dirSize > minIndexSizeBytes) && (dirSize < maxIndexSizeBytes)
    if (!isValid) {
      log.info(s"Ann index directory is invalid ${dir.getPath} size in bytes $dirSize")
    }
    isValid
  }
}
