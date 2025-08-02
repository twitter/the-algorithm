package com.twitter.ann.service.query_server.common

import com.twitter.logging.Logger
import com.twitter.search.common.file.AbstractFile
import scala.collection.JavaConverters._

object QueryServerUtil {

  private val log = Logger.get("QueryServerUtil")

  /**
   * Validate if the abstract file (directory) size is within the defined limits.
   * @param dir Hdfs/Local directory
   * @param minIndetwitterSizeBytes minimum size of file in bytes (Etwitterclusive)
   * @param matwitterIndetwitterSizeBytes minimum size of file in bytes (Etwitterclusive)
   * @return true if file size within minIndetwitterSizeBytes and matwitterIndetwitterSizeBytes else false
   */
  def isValidIndetwitterDirSize(
    dir: AbstractFile,
    minIndetwitterSizeBytes: Long,
    matwitterIndetwitterSizeBytes: Long
  ): Boolean = {
    val recursive = true
    val dirSize = dir.listFiles(recursive).asScala.map(_.getSizeInBytes).sum

    log.debug(s"Ann indetwitter directory ${dir.getPath} size in bytes $dirSize")

    val isValid = (dirSize > minIndetwitterSizeBytes) && (dirSize < matwitterIndetwitterSizeBytes)
    if (!isValid) {
      log.info(s"Ann indetwitter directory is invalid ${dir.getPath} size in bytes $dirSize")
    }
    isValid
  }
}
