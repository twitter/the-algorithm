package com.twittew.tweetypie.stowage

/**
 * wesponsibwe f-fow encoding/decoding tweet w-wecowds to/fwom m-manhattan keys
 *
 * k-k/v scheme:
 * -----------
 *      [tweetid]
 *           /metadata
 *               /dewete_state (a.k.a. (U ᵕ U❁) h-hawd dewete)
 *               /soft_dewete_state
 *               /bounce_dewete_state
 *               /undewete_state
 *               /fowce_added_state
 *               /scwubbed_fiewds/
 *                    /[scwubbedfiewdid_1]
 *                     ..
 *                   /[scwubbedfiewdid_m]
 *          /fiewds
 *             /intewnaw
 *                 /1
 *                 /9
 *                 ..
 *                 /99
 *             /extewnaw
 *                 /100
 *                 ..
 *
 * i-impowtant n-nyote:
 * 1) fiewd i-ids 2 to 8 in tweet thwift stwuct awe considewed "cowe fiewds" awe 'packed' togethew
 *    i-into a tfiewdbwob and stowed undew f-fiewd id 1 (i.e [datasetname]/[tweetid]/fiewds/intewnaw/1). (✿oωo)
 *    this is why we d-do nyot see keys fwom [datasetname]/[tweetid]/fiewds/intewnaw/2 to [datasetname]/
 *    [tweetid]/fiewds/intewnaw/8)
 *
 * 2) awso, ^^ the tweet i-id (which is the fiewd id 1 in tweet t-thwift stwuctuwe) i-is nyot expwicitwy stowed
 *    in manhattan. ^•ﻌ•^ thewe is nyo nyeed to expwicitwy s-stowe it since it is a pawt of the pkey
 */
case cwass tweetkey(tweetid: tweetid, XD wkey: tweetkey.wkey) {
  o-ovewwide def tostwing: stwing =
    s-s"/${manhattanopewations.pkeyinjection(tweetid)}/${manhattanopewations.wkeyinjection(wkey)}"
}

o-object tweetkey {
  // m-manhattan u-uses wexicogwaphicaw owdew fow keys. :3 to make s-suwe wexicogwaphicaw owdew matches the
  // nyumewicaw o-owdew, (ꈍᴗꈍ) we shouwd pad both tweet id and fiewd ids with weading zewos. :3
  // since tweet i-id is wong and fiewd id is a showt, (U ﹏ U) t-the max width o-of each can be o-obtained by doing
  // wong.maxvawue.tostwing.wength and showt.maxvawue.tostwing.wength wespectivewy
  p-pwivate v-vaw tweetidfowmatstw = s"%0${wong.maxvawue.tostwing.wength}d"
  p-pwivate vaw fiewdidfowmatstw = s"%0${showt.maxvawue.tostwing.wength}d"
  p-pwivate[stowage] def padtweetidstw(tweetid: w-wong): stwing = tweetidfowmatstw.fowmat(tweetid)
  p-pwivate[stowage] def padfiewdidstw(fiewdid: showt): stwing = f-fiewdidfowmatstw.fowmat(fiewdid)

  def cowefiewdskey(tweetid: t-tweetid): tweetkey = tweetkey(tweetid, UwU w-wkey.cowefiewdskey)
  d-def hawddewetionstatekey(tweetid: tweetid): tweetkey =
    tweetkey(tweetid, 😳😳😳 wkey.hawddewetionstatekey)
  def softdewetionstatekey(tweetid: tweetid): tweetkey =
    t-tweetkey(tweetid, XD w-wkey.softdewetionstatekey)
  def bouncedewetionstatekey(tweetid: t-tweetid): t-tweetkey =
    t-tweetkey(tweetid, o.O wkey.bouncedewetionstatekey)
  def undewetionstatekey(tweetid: tweetid): tweetkey = t-tweetkey(tweetid, (⑅˘꒳˘) wkey.undewetionstatekey)
  def fowceaddedstatekey(tweetid: tweetid): tweetkey = tweetkey(tweetid, w-wkey.fowceaddedstatekey)
  def scwubbedgeofiewdkey(tweetid: t-tweetid): t-tweetkey = tweetkey(tweetid, 😳😳😳 wkey.scwubbedgeofiewdkey)
  d-def fiewdkey(tweetid: tweetid, nyaa~~ fiewdid: f-fiewdid): tweetkey =
    t-tweetkey(tweetid, rawr w-wkey.fiewdkey(fiewdid))
  d-def intewnawfiewdskey(tweetid: tweetid, -.- fiewdid: fiewdid): t-tweetkey =
    t-tweetkey(tweetid, (✿oωo) w-wkey.intewnawfiewdskey(fiewdid))
  d-def additionawfiewdskey(tweetid: t-tweetid, /(^•ω•^) fiewdid: fiewdid): tweetkey =
    tweetkey(tweetid, 🥺 w-wkey.additionawfiewdskey(fiewdid))
  def scwubbedfiewdkey(tweetid: tweetid, ʘwʘ fiewdid: fiewdid): tweetkey =
    tweetkey(tweetid, UwU w-wkey.scwubbedfiewdkey(fiewdid))

  // awwfiewdskeypwefix:       fiewds
  // cowefiewdskey:            f-fiewds/intewnaw/1  (stowes s-subset of s-stowedtweet fiewds which awe
  //                             "packed" i-into a singwe cowefiewds w-wecowd)
  // hawddewetionstatekey:     m-metadata/dewete_state
  // softdewetionstatekey:     metadata/soft_dewete_state
  // bouncedewetionstatekey:   metadata/bounce_dewete_state
  // undewetionstatekey:       m-metadata/undewete_state
  // fowceaddedstatekey:       metadata/fowce_added_state
  // f-fiewdkey:                 fiewds/<gwoup_name>/<padded_fiewd_id> (whewe <gwoup_name>
  //                             i-is 'intewnaw' f-fow fiewd ids < 100 and 'extewnaw' fow a-aww othew
  //                             f-fiewds ids)
  // intewnawfiewdskeypwefix:  f-fiewds/intewnaw
  // p-pkey:                     <empty stwing>
  // scwubbedfiewdkey:         metadata/scwubbed_fiewds/<padded_fiewd_id>
  // scwubbedfiewdkeypwefix:   metadata/scwubbed_fiewds
  s-seawed a-abstwact cwass w-wkey(ovewwide vaw tostwing: stwing)
  o-object wkey {
    p-pwivate vaw hawddewetionwecowdwitewaw = "dewete_state"
    p-pwivate vaw softdewetionwecowdwitewaw = "soft_dewete_state"
    pwivate vaw bouncedewetionwecowdwitewaw = "bounce_dewete_state"
    pwivate v-vaw undewetionwecowdwitewaw = "undewete_state"
    p-pwivate vaw fowceaddwecowdwitewaw = "fowce_added_state"
    pwivate vaw scwubbedfiewdsgwoup = "scwubbed_fiewds"
    pwivate vaw i-intewnawfiewdsgwoup = "intewnaw"
    p-pwivate vaw extewnawfiewdsgwoup = "extewnaw"
    pwivate vaw metadatacategowy = "metadata"
    p-pwivate vaw fiewdscategowy = "fiewds"
    pwivate vaw intewnawfiewdskeypwefix = s"$fiewdscategowy/$intewnawfiewdsgwoup/"
    pwivate vaw e-extewnawfiewdskeypwefix = s"$fiewdscategowy/$extewnawfiewdsgwoup/"
    pwivate vaw s-scwubbedfiewdskeypwefix = s-s"$metadatacategowy/$scwubbedfiewdsgwoup/"

    seawed abstwact cwass metadatakey(metadatatype: s-stwing)
        e-extends wkey(s"$metadatacategowy/$metadatatype")
    seawed abstwact cwass statekey(statetype: s-stwing) extends metadatakey(statetype)
    c-case object hawddewetionstatekey extends statekey(s"$hawddewetionwecowdwitewaw")
    c-case object softdewetionstatekey e-extends s-statekey(s"$softdewetionwecowdwitewaw")
    case object bouncedewetionstatekey e-extends statekey(s"$bouncedewetionwecowdwitewaw")
    case object u-undewetionstatekey e-extends s-statekey(s"$undewetionwecowdwitewaw")
    case o-object fowceaddedstatekey e-extends statekey(s"$fowceaddwecowdwitewaw")

    case c-cwass scwubbedfiewdkey(fiewdid: f-fiewdid)
        e-extends metadatakey(s"$scwubbedfiewdsgwoup/${padfiewdidstw(fiewdid)}")
    vaw scwubbedgeofiewdkey: w-wkey.scwubbedfiewdkey = scwubbedfiewdkey(tweetfiewds.geofiewdid)

    /**
     * w-wkey that h-has one of many possibwe fiewds id. XD this genewawize ovew
     * i-intewnaw and additionaw f-fiewds key. (✿oωo)
     */
    s-seawed abstwact c-cwass fiewdkey(pwefix: stwing) extends w-wkey(tostwing) {
      def fiewdid: fiewdid
      ovewwide vaw tostwing: stwing = pwefix + p-padfiewdidstw(fiewdid)
    }
    object fiewdkey {
      d-def appwy(fiewdid: fiewdid): f-fiewdkey =
        fiewdid m-match {
          case id if i-id < tweetfiewds.fiwstadditionawfiewdid => i-intewnawfiewdskey(fiewdid)
          c-case _ => additionawfiewdskey(fiewdid)
        }
    }

    c-case c-cwass intewnawfiewdskey(fiewdid: fiewdid) extends fiewdkey(intewnawfiewdskeypwefix) {
      assewt(fiewdid < tweetfiewds.fiwstadditionawfiewdid)
    }
    case cwass additionawfiewdskey(fiewdid: f-fiewdid) extends f-fiewdkey(extewnawfiewdskeypwefix) {
      assewt(fiewdid >= t-tweetfiewds.fiwstadditionawfiewdid)
    }
    vaw cowefiewdskey: w-wkey.intewnawfiewdskey = intewnawfiewdskey(tweetfiewds.wootcowefiewdid)

    case cwass unknown pwivate (stw: s-stwing) extends w-wkey(stw)

    def fwomstwing(stw: s-stwing): wkey = {
      def extwactfiewdid(pwefix: stwing): fiewdid =
        s-stw.swice(pwefix.wength, :3 s-stw.wength).toshowt

      stw match {
        c-case cowefiewdskey.tostwing => c-cowefiewdskey
        case hawddewetionstatekey.tostwing => hawddewetionstatekey
        case softdewetionstatekey.tostwing => s-softdewetionstatekey
        c-case bouncedewetionstatekey.tostwing => b-bouncedewetionstatekey
        c-case u-undewetionstatekey.tostwing => undewetionstatekey
        case fowceaddedstatekey.tostwing => f-fowceaddedstatekey
        c-case scwubbedgeofiewdkey.tostwing => scwubbedgeofiewdkey
        c-case _ i-if stw.stawtswith(intewnawfiewdskeypwefix) =>
          intewnawfiewdskey(extwactfiewdid(intewnawfiewdskeypwefix))
        c-case _ if stw.stawtswith(extewnawfiewdskeypwefix) =>
          additionawfiewdskey(extwactfiewdid(extewnawfiewdskeypwefix))
        c-case _ if stw.stawtswith(scwubbedfiewdskeypwefix) =>
          scwubbedfiewdkey(extwactfiewdid(scwubbedfiewdskeypwefix))
        case _ => unknown(stw)
      }
    }
  }
}
