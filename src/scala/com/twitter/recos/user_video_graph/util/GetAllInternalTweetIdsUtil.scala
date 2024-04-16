package com.tw ter.recos.user_v deo_graph.ut l

 mport com.tw ter.graphjet.algor hms.T et DMask
 mport com.tw ter.graphjet.b part e.ap .B part eGraph

object GetAll nternalT et dsUt l {

  def getAll nternalT et ds(t et d: Long, b part eGraph: B part eGraph): Seq[Long] = {
    val  nternalT et ds = getAllMasks(t et d)
    sortByDegrees( nternalT et ds, b part eGraph)
  }

  pr vate def getAllMasks(t et d: Long): Seq[Long] = {
    Seq(
      t et d,
      T et DMask.summary(t et d),
      T et DMask.photo(t et d),
      T et DMask.player(t et d),
      T et DMask.promot on(t et d)
    )
  }

  pr vate def sortByDegrees(
    encodedT et ds: Seq[Long],
    b part eGraph: B part eGraph
  ): Seq[Long] = {
    encodedT et ds
      .map { encodedT et d => (encodedT et d, b part eGraph.getR ghtNodeDegree(encodedT et d)) }
      .f lter { case (_, degree) => degree > 0 } // keep only t etds w h pos  ve degree
      .sortBy { case (_, degree) => -degree } // sort by degree  n descend ng order
      .map { case (encodedT et d, _) => encodedT et d }
  }
}
