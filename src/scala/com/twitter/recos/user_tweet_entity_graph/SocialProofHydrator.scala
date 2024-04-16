package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graphjet.algor hms.count ng.t et.{
  T et tadataRecom ndat on nfo,
  T etRecom ndat on nfo
}
 mport com.tw ter.recos.recos_common.thr ftscala.{Soc alProof, Soc alProofType}

 mport scala.collect on.JavaConverters._

class Soc alProofHydrator(statsRece ver: StatsRece ver) {
  pr vate val stats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val soc alProofsDup = stats.counter("soc alProofsDup")
  pr vate val soc alProofsUn  = stats.counter("soc alProofsUn ")
  pr vate val soc alProofByTypeDup = stats.counter("soc alProofByTypeDup")
  pr vate val soc alProofByTypeUn  = stats.counter("soc alProofByTypeUn ")

  //  f t  soc al proof type  s favor e, t re are cases that one user favs, unfavs and t n favs t  sa  t et aga n.
  //  n t  case, UTEG only returns one val d soc al proof. Note that GraphJet l brary compares t  number of un que users
  // w h t  m nSoc alProofThreshold, so t  threshold c ck ng log c  s correct.
  //  f t  soc al proof type  s reply or quote, t re are val d cases that one user repl es t  sa  t et mult ple t  s.
  // GraphJet does not handle t  dedup ng because t   s Tw ter spec f c log c.
  def getSoc alProofs(
    soc alProofType: Soc alProofType,
    users: Seq[Long],
     tadata: Seq[Long]
  ): Seq[Soc alProof] = {
     f (soc alProofType == Soc alProofType.Favor e && users.s ze > 1 && users.s ze != users.d st nct.s ze) {
      soc alProofsDup. ncr()
      val un que = users
        .z p( tadata)
        .foldLeft[Seq[(Long, Long)]](N l) { (l st, next) =>
          {
            val test = l st f nd { _._1 == next._1 }
             f (test. sEmpty) next +: l st else l st
          }
        }
        .reverse
      un que.map { case (user, data) => Soc alProof(user, So (data)) }
    } else {
      soc alProofsUn . ncr()
      users.z p( tadata).map { case (user, data) => Soc alProof(user, So (data)) }
    }

  }

  // Extract and dedup soc al proofs from GraphJet. Only Favor e based soc al proof needs to dedup.
  // Return t  soc al proofs (user d,  tadata) pa r  n Soc alProof thr ft objects.
  def addT etSoc alProofs(
    t et: T etRecom ndat on nfo
  ): Opt on[Map[Soc alProofType, Seq[Soc alProof]]] = {
    So (
      t et.getSoc alProof.asScala.map {
        case (soc alProofType, soc alProof) =>
          val soc alProofThr ftType = Soc alProofType(soc alProofType.toByte)
          (
            soc alProofThr ftType,
            getSoc alProofs(
              soc alProofThr ftType,
              soc alProof.getConnect ngUsers.asScala.map(_.toLong),
              soc alProof.get tadata.asScala.map(_.toLong)
            )
          )
      }.toMap
    )
  }

  def getSoc alProofs(users: Seq[Long]): Seq[Long] = {
     f (users.s ze > 1) {
      val d st nctUsers = users.d st nct
       f (users.s ze != d st nctUsers.s ze) {
        soc alProofByTypeDup. ncr()
      } else {
        soc alProofByTypeUn . ncr()
      }
      d st nctUsers
    } else {
      soc alProofByTypeUn . ncr()
      users
    }
  }

  // Extract and dedup soc al proofs from GraphJet. All soc al proof types need to dedup.
  // Return t  user d soc al proofs w hout  tadata.
  def addT etSoc alProofByType(t et: T etRecom ndat on nfo): Map[Soc alProofType, Seq[Long]] = {
    t et.getSoc alProof.asScala.map {
      case (soc alProofType, soc alProof) =>
        (
          Soc alProofType(soc alProofType.toByte),
          getSoc alProofs(soc alProof.getConnect ngUsers.asScala.map(_.toLong))
        )
    }.toMap
  }

  // T  Hashtag and URL Soc al Proof. Dedup  s not necessary.
  def add tadataSoc alProofByType(
    t et tadataRec: T et tadataRecom ndat on nfo
  ): Map[Soc alProofType, Map[Long, Seq[Long]]] = {
    t et tadataRec.getSoc alProof.asScala.map {
      case (soc alProofType, soc alProof) =>
        (
          Soc alProofType(soc alProofType.toByte),
          soc alProof.asScala.map {
            case (author d, t et ds) =>
              (author d.toLong, t et ds.asScala.map(_.toLong))
          }.toMap)
    }.toMap
  }

}
