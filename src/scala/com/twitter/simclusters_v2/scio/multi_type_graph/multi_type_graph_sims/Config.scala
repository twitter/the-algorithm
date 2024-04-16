package com.tw ter.s mclusters_v2.sc o
package mult _type_graph.mult _type_graph_s ms

object Conf g {
  // Conf g sett ngs for R ghtNodeS mHashSc oBaseApp job
  // Number of has s to generate  n t  sketch
  val numHas s:  nt = 8192 // each  s a b , so t  results  n 1KB uncompressed sketch/user
  // Reduce skew by lett ng each reducers process a l m ed number of follo rs/user
  val maxNumNe ghborsPerReducers:  nt = 300000
  val s msHashJobOutputD rectory: Str ng = "r ght_node/s ms/s m_hash"

  // Conf g sett ngs for R ghtNodeCos neS m lar ySc oBaseApp job
  val numS ms:  nt = 500
  val m nCos neS m lar yThreshold: Double = 0.01
  val maxOutDegree:  nt = 10000
  val cos neS mJobOutputD rectory = "r ght_node/s ms/cos ne_s m lar y"

}
