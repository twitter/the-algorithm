package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}

case class FollowProof(follo dBy: Seq[Long], num ds:  nt) {
  def toThr ft: t.FollowProof = {
    t.FollowProof(follo dBy, num ds)
  }

  def toOffl neThr ft: offl ne.FollowProof = offl ne.FollowProof(follo dBy, num ds)
}

object FollowProof {

  def fromThr ft(proof: t.FollowProof): FollowProof = {
    FollowProof(proof.user ds, proof.num ds)
  }
}

case class S m larToProof(s m larTo: Seq[Long]) {
  def toThr ft: t.S m larToProof = {
    t.S m larToProof(s m larTo)
  }

  def toOffl neThr ft: offl ne.S m larToProof = offl ne.S m larToProof(s m larTo)
}

object S m larToProof {
  def fromThr ft(proof: t.S m larToProof): S m larToProof = {
    S m larToProof(proof.user ds)
  }
}

case class Popular nGeoProof(locat on: Str ng) {
  def toThr ft: t.Popular nGeoProof = {
    t.Popular nGeoProof(locat on)
  }

  def toOffl neThr ft: offl ne.Popular nGeoProof = offl ne.Popular nGeoProof(locat on)
}

object Popular nGeoProof {

  def fromThr ft(proof: t.Popular nGeoProof): Popular nGeoProof = {
    Popular nGeoProof(proof.locat on)
  }
}

case class Ttt nterestProof( nterest d: Long,  nterestD splayNa : Str ng) {
  def toThr ft: t.Ttt nterestProof = {
    t.Ttt nterestProof( nterest d,  nterestD splayNa )
  }

  def toOffl neThr ft: offl ne.Ttt nterestProof =
    offl ne.Ttt nterestProof( nterest d,  nterestD splayNa )
}

object Ttt nterestProof {

  def fromThr ft(proof: t.Ttt nterestProof): Ttt nterestProof = {
    Ttt nterestProof(proof. nterest d, proof. nterestD splayNa )
  }
}

case class Top cProof(top c d: Long) {
  def toThr ft: t.Top cProof = {
    t.Top cProof(top c d)
  }

  def toOffl neThr ft: offl ne.Top cProof =
    offl ne.Top cProof(top c d)
}

object Top cProof {
  def fromThr ft(proof: t.Top cProof): Top cProof = {
    Top cProof(proof.top c d)
  }
}

case class Custom nterest(query: Str ng) {
  def toThr ft: t.Custom nterestProof = {
    t.Custom nterestProof(query)
  }

  def toOffl neThr ft: offl ne.Custom nterestProof =
    offl ne.Custom nterestProof(query)
}

object Custom nterest {
  def fromThr ft(proof: t.Custom nterestProof): Custom nterest = {
    Custom nterest(proof.query)
  }
}

case class T etsAuthorProof(t et ds: Seq[Long]) {
  def toThr ft: t.T etsAuthorProof = {
    t.T etsAuthorProof(t et ds)
  }

  def toOffl neThr ft: offl ne.T etsAuthorProof =
    offl ne.T etsAuthorProof(t et ds)
}

object T etsAuthorProof {
  def fromThr ft(proof: t.T etsAuthorProof): T etsAuthorProof = {
    T etsAuthorProof(proof.t et ds)
  }
}

case class Dev ceFollowProof( sDev ceFollow: Boolean) {
  def toThr ft: t.Dev ceFollowProof = {
    t.Dev ceFollowProof( sDev ceFollow)
  }
  def toOffl neThr ft: offl ne.Dev ceFollowProof =
    offl ne.Dev ceFollowProof( sDev ceFollow)
}

object Dev ceFollowProof {
  def fromThr ft(proof: t.Dev ceFollowProof): Dev ceFollowProof = {
    Dev ceFollowProof(proof. sDev ceFollow)
  }

}

case class AccountProof(
  followProof: Opt on[FollowProof] = None,
  s m larToProof: Opt on[S m larToProof] = None,
  popular nGeoProof: Opt on[Popular nGeoProof] = None,
  ttt nterestProof: Opt on[Ttt nterestProof] = None,
  top cProof: Opt on[Top cProof] = None,
  custom nterestProof: Opt on[Custom nterest] = None,
  t etsAuthorProof: Opt on[T etsAuthorProof] = None,
  dev ceFollowProof: Opt on[Dev ceFollowProof] = None) {
  def toThr ft: t.AccountProof = {
    t.AccountProof(
      followProof.map(_.toThr ft),
      s m larToProof.map(_.toThr ft),
      popular nGeoProof.map(_.toThr ft),
      ttt nterestProof.map(_.toThr ft),
      top cProof.map(_.toThr ft),
      custom nterestProof.map(_.toThr ft),
      t etsAuthorProof.map(_.toThr ft),
      dev ceFollowProof.map(_.toThr ft)
    )
  }

  def toOffl neThr ft: offl ne.AccountProof = {
    offl ne.AccountProof(
      followProof.map(_.toOffl neThr ft),
      s m larToProof.map(_.toOffl neThr ft),
      popular nGeoProof.map(_.toOffl neThr ft),
      ttt nterestProof.map(_.toOffl neThr ft),
      top cProof.map(_.toOffl neThr ft),
      custom nterestProof.map(_.toOffl neThr ft),
      t etsAuthorProof.map(_.toOffl neThr ft),
      dev ceFollowProof.map(_.toOffl neThr ft)
    )
  }
}

object AccountProof {
  def fromThr ft(proof: t.AccountProof): AccountProof = {
    AccountProof(
      proof.followProof.map(FollowProof.fromThr ft),
      proof.s m larToProof.map(S m larToProof.fromThr ft),
      proof.popular nGeoProof.map(Popular nGeoProof.fromThr ft),
      proof.ttt nterestProof.map(Ttt nterestProof.fromThr ft),
      proof.top cProof.map(Top cProof.fromThr ft),
      proof.custom nterestProof.map(Custom nterest.fromThr ft),
      proof.t etsAuthorProof.map(T etsAuthorProof.fromThr ft),
      proof.dev ceFollowProof.map(Dev ceFollowProof.fromThr ft)
    )
  }
}

case class Reason(accountProof: Opt on[AccountProof]) {
  def toThr ft: t.Reason = {
    t.Reason(accountProof.map(_.toThr ft))
  }

  def toOffl neThr ft: offl ne.Reason = {
    offl ne.Reason(accountProof.map(_.toOffl neThr ft))
  }
}

object Reason {

  def fromThr ft(reason: t.Reason): Reason = {
    Reason(reason.accountProof.map(AccountProof.fromThr ft))
  }
}

tra  HasReason {

  def reason: Opt on[Reason]
  //  lper  thods below

  def follo dBy: Opt on[Seq[Long]] = {
    for {
      reason <- reason
      accountProof <- reason.accountProof
      followProof <- accountProof.followProof
    } y eld { followProof.follo dBy }
  }
}
