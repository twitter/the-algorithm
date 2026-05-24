package com.twitter.ann.service.query_server.common

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging.Logger
import com.twitter.search.common.file.AbstractFile

case class FaissIndexPathProvider(
  override val minIndexSizeBytes: Long,
  override val maxIndexSizeBytes: Long,
  override val statsReceiver: StatsReceiver)
    extends BaseIndexPathProvider {

  override val log = Logger.get("FAISSIndexPathProvider")

  override def isValidIndex(dir: AbstractFile): Boolean = {
    dir.isDirectory &&
    dir.hasSuccessFile &&
    dir.getChild("faiss.index").exists()
  }
}
