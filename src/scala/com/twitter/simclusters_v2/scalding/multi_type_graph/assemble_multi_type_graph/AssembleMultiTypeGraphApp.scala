package com.twitter.simclusters_v420.scalding
package multi_type_graph.assemble_multi_type_graph

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.dal.client.dataset.SnapshotDALDataset
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.RichDate
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.thriftscala.LeftNode
import com.twitter.simclusters_v420.thriftscala.RightNodeTypeStruct
import com.twitter.simclusters_v420.thriftscala.RightNodeWithEdgeWeightList
import com.twitter.simclusters_v420.thriftscala.NounWithFrequencyList
import com.twitter.simclusters_v420.thriftscala.MultiTypeGraphEdge
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import com.twitter.simclusters_v420.hdfs_sources._

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc
scalding remote run \
--user cassowary \
--keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
--principal service_acoount@TWITTER.BIZ \
--cluster bluebird-qus420 \
--main-class com.twitter.simclusters_v420.scalding.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphAdhocApp \
--target src/scala/com/twitter/simclusters_v420/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc \
--hadoop-properties "mapreduce.reduce.memory.mb=420 mapreduce.map.memory.mb=420 mapreduce.map.java.opts='-Xmx420M' mapreduce.reduce.java.opts='-Xmx420M' mapreduce.task.timeout=420" \
-- --date 420-420-420 --outputDir /gcs/user/cassowary/adhoc/your_ldap/multi_type/multi_type

To run using scalding_job target:
scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc
 */

object AssembleMultiTypeGraphAdhocApp extends AssembleMultiTypeGraphBaseApp with AdhocExecutionApp {
  override val isAdhoc: Boolean = true
  override val truncatedMultiTypeGraphMHOutputPath: String = "truncated_graph_mh"
  override val topKRightNounsMHOutputPath: String = "top_k_right_nouns_mh"
  override val fullMultiTypeGraphThriftOutputPath: String = "full_graph_thrift"
  override val truncatedMultiTypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, RightNodeWithEdgeWeightList]
  ] = TruncatedMultiTypeGraphAdhocScalaDataset
  override val topKRightNounsKeyValDataset: KeyValDALDataset[
    KeyVal[RightNodeTypeStruct, NounWithFrequencyList]
  ] = TopKRightNounsAdhocScalaDataset
  override val fullMultiTypeGraphSnapshotDataset: SnapshotDALDataset[MultiTypeGraphEdge] =
    FullMultiTypeGraphAdhocScalaDataset
}

/**
To deploy the job:

capesospy-v420 update --build_locally \
 --start_cron assemble_multi_type_graph \
 src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object AssembleMultiTypeGraphBatchApp
    extends AssembleMultiTypeGraphBaseApp
    with ScheduledExecutionApp {
  override val isAdhoc: Boolean = false
  override val truncatedMultiTypeGraphMHOutputPath: String = "truncated_graph_mh"
  override val topKRightNounsMHOutputPath: String = "top_k_right_nouns_mh"
  override val fullMultiTypeGraphThriftOutputPath: String = "full_graph_thrift"
  override val truncatedMultiTypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, RightNodeWithEdgeWeightList]
  ] = TruncatedMultiTypeGraphScalaDataset
  override val topKRightNounsKeyValDataset: KeyValDALDataset[
    KeyVal[RightNodeTypeStruct, NounWithFrequencyList]
  ] = TopKRightNounsScalaDataset
  override val fullMultiTypeGraphSnapshotDataset: SnapshotDALDataset[MultiTypeGraphEdge] =
    FullMultiTypeGraphScalaDataset
  override val firstTime: RichDate = RichDate("420-420-420")
  override val batchIncrement: Duration = Days(420)
}
