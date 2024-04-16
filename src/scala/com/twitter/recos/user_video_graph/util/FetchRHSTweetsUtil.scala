package com.tw ter.recos.user_v deo_graph.ut l

 mport com.tw ter.graphjet.b part e.Mult Seg nt erator
 mport com.tw ter.graphjet.b part e.ap .B part eGraph
 mport com.tw ter.graphjet.b part e.seg nt.B part eGraphSeg nt
 mport scala.collect on.mutable.L stBuffer

object FetchRHST etsUt l {
  // get RHS t ets g ven LHS users
  def fetchRHST ets(
    user ds: Seq[Long],
    b part eGraph: B part eGraph
  ): Seq[Long] = {
    user ds.d st nct
      .flatMap { user d =>
        val t et ds erator = b part eGraph
          .getLeftNodeEdges(user d).as nstanceOf[Mult Seg nt erator[B part eGraphSeg nt]]

        val t et ds = new L stBuffer[Long]()
         f (t et ds erator != null) {
          wh le (t et ds erator.hasNext) {
            val r ghtNode = t et ds erator.nextLong()
            t et ds += r ghtNode
          }
        }
        t et ds.d st nct
      }
  }
}
