package com.X.simclusters_v2.scalding
package multi_type_graph.assemble_multi_type_graph

import com.X.dal.client.dataset.KeyValDALDataset
import com.X.dal.client.dataset.SnapshotDALDataset
import com.X.scalding.Days
import com.X.scalding.Duration
import com.X.scalding.RichDate
import com.X.scalding_internal.multiformat.format.keyval.KeyVal
import com.X.simclusters_v2.thriftscala.LeftNode
import com.X.simclusters_v2.thriftscala.RightNodeTypeStruct
import com.X.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList
import com.X.simclusters_v2.thriftscala.NounWithFrequencyList
import com.X.simclusters_v2.thriftscala.MultiTypeGraphEdge
import com.X.wtf.scalding.jobs.common.AdhocExecutionApp
import com.X.wtf.scalding.jobs.common.ScheduledExecutionApp
import com.X.simclusters_v2.hdfs_sources._

/**
./bazel bundle src/scala/com/X/simclusters_v2/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc
scalding remote run \
--user cassowary \
--keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
--principal service_acoount@TWITTER.BIZ \
--cluster bluebird-qus1 \
--main-class com.X.simclusters_v2.scalding.multi_type_graph.assemble_multi_type_graph.AssembleMultiTypeGraphAdhocApp \
--target src/scala/com/X/simclusters_v2/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc \
--hadoop-properties "mapreduce.reduce.memory.mb=8192 mapreduce.map.memory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce.java.opts='-Xmx7618M' mapreduce.task.timeout=3600000" \
-- --date 2021-07-10 --outputDir /gcs/user/cassowary/adhoc/your_ldap/multi_type/multi_type

To run using scalding_job target:
scalding remote run --target src/scala/com/X/simclusters_v2/scalding/multi_type_graph/assemble_multi_type_graph:multi_type_graph-adhoc
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

capesospy-v2 update --build_locally \
 --start_cron assemble_multi_type_graph \
 src/scala/com/X/simclusters_v2/capesos_config/atla_proc.yaml
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
  override val firstTime: RichDate = RichDate("2021-08-21")
  override val batchIncrement: Duration = Days(7)
}
