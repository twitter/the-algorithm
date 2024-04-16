package com.tw ter.s mclusters_v2.scald ng.mbcg

 mport com.google.common.collect. mmutableSet
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._
 mport com.tw ter.ml.ap .DataType
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .Feature.SparseCont nuous
 mport com.tw ter.ml.ap .Feature.Tensor
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport java.ut l.{Map => JMap}

/*
Features used for model-based cand date generat on
 */
object T etAllFeatures {
  val t et d = SharedFeatures.TWEET_ D
  val t etS mclusters =
    new SparseCont nuous(
      "t et.s mcluster.log_fav_based_embedd ng.20m_145k_2020",
       mmutableSet.of( nferred nterests))
      .as nstanceOf[Feature[JMap[Str ng, Double]]]
  val authorF2vProducerEmbedd ng =
    new Tensor(
      "t et.author_follow2vec.producer_embedd ng_200",
      DataType.FLOAT
    )

  pr vate val allFeatures: Seq[Feature[_]] = Seq(
    t et d,
    t etS mclusters,
    authorF2vProducerEmbedd ng
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}

object UserAllFeatures {
  val user d = SharedFeatures.USER_ D
  val userS mclusters =
    new SparseCont nuous(
      "user.  ape.log_fav_based_embedd ng.20m_145k_2020",
       mmutableSet.of( nferred nterests))
      .as nstanceOf[Feature[JMap[Str ng, Double]]]
  val userF2vConsu rEmbedd ng =
    new Tensor(
      "user.follow2vec.consu r_avg_fol_emb_200",
      DataType.FLOAT
    )

  pr vate val allFeatures: Seq[Feature[_]] = Seq(
    user d,
    userS mclusters,
    userF2vConsu rEmbedd ng
  )

  val featureContext = new FeatureContext(allFeatures: _*)
}
