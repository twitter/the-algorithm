package com.ExTwitter.ann.service.query_server.common

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.logging.Logger
import com.ExTwitter.search.common.file.AbstractFile

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
