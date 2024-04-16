package com.tw ter.s mclusters_v2.sc o
package bq_generat on.common

 mport com.tw ter.algeb rd_ nternal.thr ftscala.DecayedValue
 mport com.tw ter.s mclusters_v2.thr ftscala.FullCluster d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.Scores
 mport com.tw ter.s mclusters_v2.thr ftscala.TopKT etsW hScores
 mport com.tw ter.snowflake. d.Snowflake d
 mport org.apac .avro.gener c.Gener cRecord
 mport org.apac .beam.sdk. o.gcp.b gquery.Sc maAndRecord
 mport org.apac .beam.sdk.transforms.Ser al zableFunct on
 mport scala.collect on.JavaConverters._

object  ndexGenerat onUt l {
  // Funct on that parses [Gener cRecord] results   read from BQ  nto [TopKT etsForClusterKey]
  def parseClusterTopKT etsFn(t etEmbedd ngsHalfL fe:  nt) =
    new Ser al zableFunct on[Sc maAndRecord, TopKT etsForClusterKey] {
      overr de def apply(record: Sc maAndRecord): TopKT etsForClusterKey = {
        val gener cRecord: Gener cRecord = record.getRecord()
        TopKT etsForClusterKey(
          cluster d = FullCluster d(
            modelVers on = ModelVers on.Model20m145k2020,
            cluster d = gener cRecord.get("cluster d").toStr ng.to nt
          ),
          topKT etsW hScores = parseTopKT etsForClusterKeyColumn(
            gener cRecord,
            "topKT etsForClusterKey",
            t etEmbedd ngsHalfL fe),
        )
      }
    }

  // Funct on that parses t  topKT etsForClusterKey column  nto [TopKT etsW hScores]
  def parseTopKT etsForClusterKeyColumn(
    gener cRecord: Gener cRecord,
    columnNa : Str ng,
    t etEmbedd ngsHalfL fe:  nt
  ): TopKT etsW hScores = {
    val t etScorePa rs: java.ut l.L st[Gener cRecord] =
      gener cRecord.get(columnNa ).as nstanceOf[java.ut l.L st[Gener cRecord]]
    val t et dToScoresMap = t etScorePa rs.asScala
      .map((gr: Gener cRecord) => {
        // Retr eve t  t et d and t etScore
        val t et d = gr.get("t et d").toStr ng.toLong
        val t etScore = gr.get("t etScore").toStr ng.toDouble

        // Transform t etScore  nto DecayedValue
        // Ref: https://g hub.com/tw ter/algeb rd/blob/develop/algeb rd-core/src/ma n/scala/com/tw ter/algeb rd/DecayedValue.scala
        val scaledT   =
          Snowflake d.un xT  M ll sFrom d(t et d) * math.log(2.0) / t etEmbedd ngsHalfL fe
        val decayedValue = DecayedValue(t etScore, scaledT  )

        // Update t  TopT ets Map
        t et d -> Scores(favClusterNormal zed8HrHalfL feScore = So (decayedValue))
      }).toMap
    TopKT etsW hScores(topT etsByFavClusterNormal zedScore = So (t et dToScoresMap))
  }
  case class TopKT etsForClusterKey(
    cluster d: FullCluster d,
    topKT etsW hScores: TopKT etsW hScores)

}
