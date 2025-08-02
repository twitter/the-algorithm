package com.twitter.ann.service.query_server.common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging.Logger
import com.twitter.search.common.file.AbstractFile

case class FaissIndetwitterPathProvider(
  override val minIndetwitterSizeBytes: Long,
  override val matwitterIndetwitterSizeBytes: Long,
  override val statsReceiver: StatsReceiver)
    etwittertends BaseIndetwitterPathProvider {

  override val log = Logger.get("FAISSIndetwitterPathProvider")

  override def isValidIndetwitter(dir: AbstractFile): Boolean = {
    dir.isDirectory &&
    dir.hasSuccessFile &&
    dir.getChild("faiss.indetwitter").etwitterists()
  }
}
