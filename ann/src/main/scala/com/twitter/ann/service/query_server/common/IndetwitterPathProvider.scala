package com.twitter.ann.service.query_server.common

import com.twitter.ann.common.IndetwitterOutputFile
import com.twitter.ann.hnsw.HnswCommon._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging.Logger
import com.twitter.search.common.file.AbstractFile
import com.twitter.search.common.file.AbstractFile.Filter
import com.twitter.search.common.file.PathUtils
import com.twitter.util.Try
import java.io.IOEtwitterception
import java.util.concurrent.atomic.AtomicReference
import scala.collection.JavaConverters._
import scala.math.Ordering.comparatorToOrdering

abstract class IndetwitterPathProvider {
  def provideIndetwitterPath(rootPath: AbstractFile, group: Boolean = false): Try[AbstractFile]
  def provideIndetwitterPathWithGroups(rootPath: AbstractFile): Try[Seq[AbstractFile]]
}

abstract class BaseIndetwitterPathProvider etwittertends IndetwitterPathProvider {
  protected val minIndetwitterSizeBytes: Long
  protected val matwitterIndetwitterSizeBytes: Long
  protected val statsReceiver: StatsReceiver
  protected val log: Logger
  private val invalidPathCounter = statsReceiver.counter("invalid_indetwitter")
  private val failToLocateDirectoryCounter = statsReceiver.counter("find_latest_path_fail")
  private val successProvidePathCounter = statsReceiver.counter("provide_path_success")

  private val latestGroupCount = new AtomicReference(0f)
  private val latestIndetwitterTimestamp = new AtomicReference(0f)
  private val latestValidIndetwitterTimestamp = new AtomicReference(0f)

  private val INDEtwitter_METADATA_FILE = "ANN_INDEtwitter_METADATA"

  private val latestIndetwitterGauge = statsReceiver.addGauge("latest_indetwitter_timestamp")(
    latestIndetwitterTimestamp.get()
  )
  private val latestValidIndetwitterGauge = statsReceiver.addGauge("latest_valid_indetwitter_timestamp")(
    latestValidIndetwitterTimestamp.get()
  )
  private val latestGroupCountGauge = statsReceiver.addGauge("latest_group_count")(
    latestGroupCount.get()
  )

  private val latestTimeStampDirectoryFilter = new AbstractFile.Filter {

    /** Determines which files should be accepted when listing a directory. */
    override def accept(file: AbstractFile): Boolean = {
      val name = file.getName
      PathUtils.TIMESTAMP_PATTERN.matcher(name).matches()
    }
  }

  private def findLatestTimeStampValidSuccessDirectory(
    path: AbstractFile,
    group: Boolean
  ): AbstractFile = {
    log.info(s"Calling findLatestTimeStampValidSuccessDirectory with ${path.getPath}")
    // Get all the timestamp directories
    val dateDirs = path.listFiles(latestTimeStampDirectoryFilter).asScala.toSeq

    if (dateDirs.nonEmpty) {
      // Validate the indetwitteres
      val latestValidPath = {
        if (group) {
          // For grouped, check all the individual group indetwitteres and stop as soon as a valid indetwitter
          // is found.
          dateDirs
            .sorted(comparatorToOrdering(PathUtils.NEWEST_FIRST_COMPARATOR)).find(file => {
              val indetwitterMetadataFile = file.getChild(INDEtwitter_METADATA_FILE)
              val indetwitteres = file.listFiles().asScala.filter(_.isDirectory)
              val isValid = if (indetwitterMetadataFile.etwitterists()) {
                // Metadata file etwitterists. Check the number of groups and verify the indetwitter is
                // complete
                val indetwitterMetadata = new IndetwitterOutputFile(indetwitterMetadataFile).loadIndetwitterMetadata()
                if (indetwitterMetadata.numGroups.get != indetwitteres.size) {
                  log.info(
                    s"Grouped indetwitter ${file.getPath} should have ${indetwitterMetadata.numGroups.get} groups but had ${indetwitteres.size}")
                }
                indetwitterMetadata.numGroups.get == indetwitteres.size
              } else {
                // True if the file doesn't etwitterist. This is to make this change backwards
                // compatible for clients using the old version of the dataflow job
                true
              }

              isValid && indetwitteres.forall(indetwitter => {
                indetwitter.hasSuccessFile && isValidIndetwitter(indetwitter) && QueryServerUtil
                  .isValidIndetwitterDirSize(indetwitter, minIndetwitterSizeBytes, matwitterIndetwitterSizeBytes)
              })
            })
        } else {
          // For non-grouped, find the first valid indetwitter.
          dateDirs
            .sorted(comparatorToOrdering(PathUtils.NEWEST_FIRST_COMPARATOR)).find(file => {
              file.hasSuccessFile && QueryServerUtil
                .isValidIndetwitterDirSize(file, minIndetwitterSizeBytes, matwitterIndetwitterSizeBytes)
            })
        }
      }

      if (latestValidPath.nonEmpty) {
        // Log the results
        successProvidePathCounter.incr()
        if (group) {
          latestGroupCount.set(latestValidPath.get.listFiles().asScala.count(_.isDirectory))
          log.info(
            s"findLatestTimeStampValidSuccessDirectory latestValidPath ${latestValidPath.get.getPath} and number of groups $latestGroupCount")
        } else {
          val latestValidPathSize =
            latestValidPath.get.listFiles(true).asScala.map(_.getSizeInBytes).sum
          log.info(
            s"findLatestTimeStampValidSuccessDirectory latestValidPath ${latestValidPath.get.getPath} and size $latestValidPathSize")
        }
        return latestValidPath.get
      }
    }

    // Fail if no indetwitter or no valid indetwitter.
    failToLocateDirectoryCounter.incr()
    throw new IOEtwitterception(s"Cannot find any valid directory with SUCCESS file at ${path.getName}")
  }

  def isValidIndetwitter(indetwitter: AbstractFile): Boolean

  override def provideIndetwitterPath(
    rootPath: AbstractFile,
    group: Boolean = false
  ): Try[AbstractFile] = {
    Try {
      val latestValidPath = findLatestTimeStampValidSuccessDirectory(rootPath, group)
      if (!group) {
        val latestPath = PathUtils.findLatestTimeStampSuccessDirectory(rootPath)
        // since latestValidPath does not throw etwitterception, latestPath must etwitterist
        assert(latestPath.isPresent)
        val latestPathSize = latestPath.get.listFiles(true).asScala.map(_.getSizeInBytes).sum
        log.info(s"provideIndetwitterPath latestPath ${latestPath
          .get()
          .getPath} and size $latestPathSize")
        latestIndetwitterTimestamp.set(latestPath.get().getName.toFloat)
        // latest directory is not valid, update counter for alerts
        if (latestPath.get() != latestValidPath) {
          invalidPathCounter.incr()
        }
      } else {
        latestIndetwitterTimestamp.set(latestValidPath.getName.toFloat)
      }
      latestValidIndetwitterTimestamp.set(latestValidPath.getName.toFloat)
      latestValidPath
    }
  }

  override def provideIndetwitterPathWithGroups(
    rootPath: AbstractFile
  ): Try[Seq[AbstractFile]] = {
    val latestValidPath = provideIndetwitterPath(rootPath, true)
    latestValidPath.map { path =>
      path
        .listFiles(new Filter {
          override def accept(file: AbstractFile): Boolean =
            file.isDirectory && file.hasSuccessFile
        }).asScala.toSeq
    }
  }
}

case class ValidatedIndetwitterPathProvider(
  override val minIndetwitterSizeBytes: Long,
  override val matwitterIndetwitterSizeBytes: Long,
  override val statsReceiver: StatsReceiver)
    etwittertends BaseIndetwitterPathProvider {

  override val log = Logger.get("ValidatedIndetwitterPathProvider")

  override def isValidIndetwitter(dir: AbstractFile): Boolean = {
    isValidHnswIndetwitter(dir)
  }
}
