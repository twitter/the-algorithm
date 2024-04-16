package com.tw ter.t  l nes.pred ct on.features.soc alproof

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .Feature.B nary
 mport com.tw ter.ml.ap .Feature.Cont nuous
 mport com.tw ter.ml.ap .Feature.SparseB nary
 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.t  l nes.pred ct on.features.soc alproof.Soc alProofDataRecordFeatures._
 mport com.tw ter.t  l nes.soc alproof.thr ftscala.Soc alProof
 mport com.tw ter.t  l nes.soc alproof.v1.thr ftscala.Soc alProofType
 mport com.tw ter.t  l nes.ut l.CommonTypes.User d
 mport scala.collect on.JavaConverters._
 mport com.tw ter.dal.personal_data.thr ftjava.PersonalDataType._

abstract class Soc alProofUserGroundTruth(user ds: Seq[User d], count:  nt) {
  requ re(
    count >= user ds.s ze,
    "count must be equal to or greater than t  number of entr es  n user ds"
  )
  // Us ng Double as t  return type to make   more conven ent for t se values to be used as
  // ML feature values.
  val d splayedUserCount: Double = user ds.s ze.toDouble
  val und splayedUserCount: Double = count - user ds.s ze.toDouble
  val totalCount: Double = count.toDouble

  def featureD splayedUsers: SparseB nary
  def featureD splayedUserCount: Cont nuous
  def featureUnd splayedUserCount: Cont nuous
  def featureTotalUserCount: Cont nuous

  def setFeatures(rec: DataRecord): Un  = {
    rec.setFeatureValue(featureD splayedUsers, toStr ngSet(user ds))
    rec.setFeatureValue(featureD splayedUserCount, d splayedUserCount)
    rec.setFeatureValue(featureUnd splayedUserCount, und splayedUserCount)
    rec.setFeatureValue(featureTotalUserCount, totalCount)
  }
  protected def toStr ngSet(value: Seq[Long]): Set[Str ng] = {
    value.map(_.toStr ng).toSet
  }
}

case class Favor edBySoc alProofUserGroundTruth(user ds: Seq[User d] = Seq.empty, count:  nt = 0)
    extends Soc alProofUserGroundTruth(user ds, count) {

  overr de val featureD splayedUsers = Soc alProofD splayedFavor edByUsers
  overr de val featureD splayedUserCount = Soc alProofD splayedFavor edByUserCount
  overr de val featureUnd splayedUserCount = Soc alProofUnd splayedFavor edByUserCount
  overr de val featureTotalUserCount = Soc alProofTotalFavor edByUserCount
}

case class Ret etedBySoc alProofUserGroundTruth(user ds: Seq[User d] = Seq.empty, count:  nt = 0)
    extends Soc alProofUserGroundTruth(user ds, count) {

  overr de val featureD splayedUsers = Soc alProofD splayedRet etedByUsers
  overr de val featureD splayedUserCount = Soc alProofD splayedRet etedByUserCount
  overr de val featureUnd splayedUserCount = Soc alProofUnd splayedRet etedByUserCount
  overr de val featureTotalUserCount = Soc alProofTotalRet etedByUserCount
}

case class Repl edBySoc alProofUserGroundTruth(user ds: Seq[User d] = Seq.empty, count:  nt = 0)
    extends Soc alProofUserGroundTruth(user ds, count) {

  overr de val featureD splayedUsers = Soc alProofD splayedRepl edByUsers
  overr de val featureD splayedUserCount = Soc alProofD splayedRepl edByUserCount
  overr de val featureUnd splayedUserCount = Soc alProofUnd splayedRepl edByUserCount
  overr de val featureTotalUserCount = Soc alProofTotalRepl edByUserCount
}

case class Soc alProofFeatures(
  hasSoc alProof: Boolean,
  favor edBy: Favor edBySoc alProofUserGroundTruth = Favor edBySoc alProofUserGroundTruth(),
  ret etedBy: Ret etedBySoc alProofUserGroundTruth = Ret etedBySoc alProofUserGroundTruth(),
  repl edBy: Repl edBySoc alProofUserGroundTruth = Repl edBySoc alProofUserGroundTruth()) {

  def setFeatures(dataRecord: DataRecord): Un  =
     f (hasSoc alProof) {
      dataRecord.setFeatureValue(HasSoc alProof, hasSoc alProof)
      favor edBy.setFeatures(dataRecord)
      ret etedBy.setFeatures(dataRecord)
      repl edBy.setFeatures(dataRecord)
    }
}

object Soc alProofFeatures {
  def apply(soc alProofs: Seq[Soc alProof]): Soc alProofFeatures =
    soc alProofs.foldLeft(Soc alProofFeatures(hasSoc alProof = soc alProofs.nonEmpty))(
      (prevFeatures, soc alProof) => {
        val user ds = soc alProof.v1.user ds
        val count = soc alProof.v1.count
        soc alProof.v1.soc alProofType match {
          case Soc alProofType.Favor edBy =>
            prevFeatures.copy(favor edBy = Favor edBySoc alProofUserGroundTruth(user ds, count))
          case Soc alProofType.Ret etedBy =>
            prevFeatures.copy(ret etedBy = Ret etedBySoc alProofUserGroundTruth(user ds, count))
          case Soc alProofType.Repl edBy =>
            prevFeatures.copy(repl edBy = Repl edBySoc alProofUserGroundTruth(user ds, count))
          case _ =>
            prevFeatures // sk p s lently  nstead of break ng jobs, s nce t   sn't used yet
        }
      })
}

object Soc alProofDataRecordFeatures {
  val HasSoc alProof = new B nary("recap.soc al_proof.has_soc al_proof")

  val Soc alProofD splayedFavor edByUsers = new SparseB nary(
    "recap.soc al_proof.l st.d splayed.favor ed_by",
    Set(User d, Publ cL kes, Pr vateL kes).asJava
  )
  val Soc alProofD splayedFavor edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.d splayed.favor ed_by",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava
  )
  val Soc alProofUnd splayedFavor edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.und splayed.favor ed_by",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava
  )
  val Soc alProofTotalFavor edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.total.favor ed_by",
    Set(CountOfPr vateL kes, CountOfPubl cL kes).asJava
  )

  val Soc alProofD splayedRet etedByUsers = new SparseB nary(
    "recap.soc al_proof.l st.d splayed.ret eted_by",
    Set(User d, Publ cRet ets, Pr vateRet ets).asJava
  )
  val Soc alProofD splayedRet etedByUserCount = new Cont nuous(
    "recap.soc al_proof.count.d splayed.ret eted_by",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava
  )
  val Soc alProofUnd splayedRet etedByUserCount = new Cont nuous(
    "recap.soc al_proof.count.und splayed.ret eted_by",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava
  )
  val Soc alProofTotalRet etedByUserCount = new Cont nuous(
    "recap.soc al_proof.count.total.ret eted_by",
    Set(CountOfPr vateRet ets, CountOfPubl cRet ets).asJava
  )

  val Soc alProofD splayedRepl edByUsers = new SparseB nary(
    "recap.soc al_proof.l st.d splayed.repl ed_by",
    Set(User d, Publ cRepl es, Pr vateRepl es).asJava
  )
  val Soc alProofD splayedRepl edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.d splayed.repl ed_by",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava
  )
  val Soc alProofUnd splayedRepl edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.und splayed.repl ed_by",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava
  )
  val Soc alProofTotalRepl edByUserCount = new Cont nuous(
    "recap.soc al_proof.count.total.repl ed_by",
    Set(CountOfPr vateRepl es, CountOfPubl cRepl es).asJava
  )

  val AllFeatures = Seq(
    HasSoc alProof,
    Soc alProofD splayedFavor edByUsers,
    Soc alProofD splayedFavor edByUserCount,
    Soc alProofUnd splayedFavor edByUserCount,
    Soc alProofTotalFavor edByUserCount,
    Soc alProofD splayedRet etedByUsers,
    Soc alProofD splayedRet etedByUserCount,
    Soc alProofUnd splayedRet etedByUserCount,
    Soc alProofTotalRet etedByUserCount,
    Soc alProofD splayedRepl edByUsers,
    Soc alProofD splayedRepl edByUserCount,
    Soc alProofUnd splayedRepl edByUserCount,
    Soc alProofTotalRepl edByUserCount
  )
}
