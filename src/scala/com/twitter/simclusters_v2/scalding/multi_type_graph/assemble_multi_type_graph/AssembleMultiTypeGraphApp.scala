package com.tw ter.s mclusters_v2.scald ng
package mult _type_graph.assemble_mult _type_graph

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.thr ftscala.LeftNode
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeTypeStruct
 mport com.tw ter.s mclusters_v2.thr ftscala.R ghtNodeW hEdge  ghtL st
 mport com.tw ter.s mclusters_v2.thr ftscala.NounW hFrequencyL st
 mport com.tw ter.s mclusters_v2.thr ftscala.Mult TypeGraphEdge
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport com.tw ter.s mclusters_v2.hdfs_s ces._

/**
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/mult _type_graph/assemble_mult _type_graph:mult _type_graph-adhoc
scald ng remote run \
--user cassowary \
--keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
--pr nc pal serv ce_acoount@TW TTER.B Z \
--cluster blueb rd-qus1 \
--ma n-class com.tw ter.s mclusters_v2.scald ng.mult _type_graph.assemble_mult _type_graph.AssembleMult TypeGraphAdhocApp \
--target src/scala/com/tw ter/s mclusters_v2/scald ng/mult _type_graph/assemble_mult _type_graph:mult _type_graph-adhoc \
--hadoop-propert es "mapreduce.reduce. mory.mb=8192 mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce.java.opts='-Xmx7618M' mapreduce.task.t  out=3600000" \
-- --date 2021-07-10 --outputD r /gcs/user/cassowary/adhoc/y _ldap/mult _type/mult _type

To run us ng scald ng_job target:
scald ng remote run --target src/scala/com/tw ter/s mclusters_v2/scald ng/mult _type_graph/assemble_mult _type_graph:mult _type_graph-adhoc
 */

object AssembleMult TypeGraphAdhocApp extends AssembleMult TypeGraphBaseApp w h AdhocExecut onApp {
  overr de val  sAdhoc: Boolean = true
  overr de val truncatedMult TypeGraphMHOutputPath: Str ng = "truncated_graph_mh"
  overr de val topKR ghtNounsMHOutputPath: Str ng = "top_k_r ght_nouns_mh"
  overr de val fullMult TypeGraphThr ftOutputPath: Str ng = "full_graph_thr ft"
  overr de val truncatedMult TypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, R ghtNodeW hEdge  ghtL st]
  ] = TruncatedMult TypeGraphAdhocScalaDataset
  overr de val topKR ghtNounsKeyValDataset: KeyValDALDataset[
    KeyVal[R ghtNodeTypeStruct, NounW hFrequencyL st]
  ] = TopKR ghtNounsAdhocScalaDataset
  overr de val fullMult TypeGraphSnapshotDataset: SnapshotDALDataset[Mult TypeGraphEdge] =
    FullMult TypeGraphAdhocScalaDataset
}

/**
To deploy t  job:

capesospy-v2 update --bu ld_locally \
 --start_cron assemble_mult _type_graph \
 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object AssembleMult TypeGraphBatchApp
    extends AssembleMult TypeGraphBaseApp
    w h Sc duledExecut onApp {
  overr de val  sAdhoc: Boolean = false
  overr de val truncatedMult TypeGraphMHOutputPath: Str ng = "truncated_graph_mh"
  overr de val topKR ghtNounsMHOutputPath: Str ng = "top_k_r ght_nouns_mh"
  overr de val fullMult TypeGraphThr ftOutputPath: Str ng = "full_graph_thr ft"
  overr de val truncatedMult TypeGraphKeyValDataset: KeyValDALDataset[
    KeyVal[LeftNode, R ghtNodeW hEdge  ghtL st]
  ] = TruncatedMult TypeGraphScalaDataset
  overr de val topKR ghtNounsKeyValDataset: KeyValDALDataset[
    KeyVal[R ghtNodeTypeStruct, NounW hFrequencyL st]
  ] = TopKR ghtNounsScalaDataset
  overr de val fullMult TypeGraphSnapshotDataset: SnapshotDALDataset[Mult TypeGraphEdge] =
    FullMult TypeGraphScalaDataset
  overr de val f rstT  : R chDate = R chDate("2021-08-21")
  overr de val batch ncre nt: Durat on = Days(7)
}
