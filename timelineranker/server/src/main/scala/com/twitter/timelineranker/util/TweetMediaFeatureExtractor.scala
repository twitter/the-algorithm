package com.tw ter.t  l neranker.ut l

 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala. d a nfo
 mport com.tw ter.t etyp e.thr ftscala. d aEnt y
 mport com.tw ter.t etyp e.thr ftscala. d aTag
 mport com.tw ter.t etyp e.{thr ftscala => t etyp e}
 mport scala.collect on.Map
 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.ColorPalette em
 mport com.tw ter. d aserv ces.commons. d a nformat on.thr ftscala.Face
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures

object T et d aFeaturesExtractor {

  //  thod to overload for content features.
  def add d aFeaturesFromT et(
     nputFeatures: ContentFeatures,
    t et: t etyp e.T et,
    enableT et d aHydrat on: Boolean
  ): ContentFeatures = {
    val featuresW h d aEnt y = t et. d a
      .map {  d aEnt  es =>
        val s zeFeatures = getS zeFeatures( d aEnt  es)
        val playbackFeatures = getPlaybackFeatures( d aEnt  es)
        val  d aW dths = s zeFeatures.map(_.w dth.toShort)
        val  d a  ghts = s zeFeatures.map(_.  ght.toShort)
        val res ze thods = s zeFeatures.map(_.res ze thod.toShort)
        val faceMapAreas = getFaceMapAreas( d aEnt  es)
        val sortedColorPalette = getSortedColorPalette( d aEnt  es)
        val st ckerFeatures = getSt ckerFeatures( d aEnt  es)
        val  d aOr g nProv ders = get d aOr g nProv ders( d aEnt  es)
        val  sManaged = get sManaged( d aEnt  es)
        val  s360 = get s360( d aEnt  es)
        val v ewCount = getV ewCount( d aEnt  es)
        val userDef nedProduct tadataFeatures =
          getUserDef nedProduct tadataFeatures( d aEnt  es)
        val  sMonet zable =
          getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_. sMonet zable))
        val  sEmbeddable =
          getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_. sEmbeddable))
        val hasSelectedPrev ew mage =
          getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_.hasSelectedPrev ew mage))
        val hasT le = getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_.hasT le))
        val hasDescr pt on =
          getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_.hasDescr pt on))
        val hasV s S eCallToAct on = getOptBooleanFromSeqOpt(
          userDef nedProduct tadataFeatures.map(_.hasV s S eCallToAct on))
        val hasApp nstallCallToAct on = getOptBooleanFromSeqOpt(
          userDef nedProduct tadataFeatures.map(_.hasApp nstallCallToAct on))
        val hasWatchNowCallToAct on =
          getOptBooleanFromSeqOpt(userDef nedProduct tadataFeatures.map(_.hasWatchNowCallToAct on))

         nputFeatures.copy(
          v deoDurat onMs = playbackFeatures.durat onMs,
          b Rate = playbackFeatures.b Rate,
          aspectRat oNum = playbackFeatures.aspectRat oNum,
          aspectRat oDen = playbackFeatures.aspectRat oDen,
          w dths = So ( d aW dths),
            ghts = So ( d a  ghts),
          res ze thods = So (res ze thods),
          faceAreas = So (faceMapAreas),
          dom nantColorRed = sortedColorPalette. adOpt on.map(_.rgb.red),
          dom nantColorBlue = sortedColorPalette. adOpt on.map(_.rgb.blue),
          dom nantColorGreen = sortedColorPalette. adOpt on.map(_.rgb.green),
          dom nantColorPercentage = sortedColorPalette. adOpt on.map(_.percentage),
          numColors = So (sortedColorPalette.s ze.toShort),
          st cker ds = So (st ckerFeatures),
           d aOr g nProv ders = So ( d aOr g nProv ders),
           sManaged = So ( sManaged),
           s360 = So ( s360),
          v ewCount = v ewCount,
           sMonet zable =  sMonet zable,
           sEmbeddable =  sEmbeddable,
          hasSelectedPrev ew mage = hasSelectedPrev ew mage,
          hasT le = hasT le,
          hasDescr pt on = hasDescr pt on,
          hasV s S eCallToAct on = hasV s S eCallToAct on,
          hasApp nstallCallToAct on = hasApp nstallCallToAct on,
          hasWatchNowCallToAct on = hasWatchNowCallToAct on
        )
      }
      .getOrElse( nputFeatures)

    val featuresW h d aTags = t et. d aTags
      .map {  d aTags =>
        val  d aTagScreenNa s = get d aTagScreenNa s( d aTags.tagMap)
        val num d aTags =  d aTagScreenNa s.s ze

        featuresW h d aEnt y.copy(
           d aTagScreenNa s = So ( d aTagScreenNa s),
          num d aTags = So (num d aTags.toShort)
        )
      }
      .getOrElse(featuresW h d aEnt y)

     f (enableT et d aHydrat on) {
      featuresW h d aTags
        .copy( d a = t et. d a)
    } else {
      featuresW h d aTags
    }
  }

  // Extracts   ght, w dth and res ze  thod of photo/v deo.
  pr vate def getS zeFeatures( d aEnt  es: Seq[ d aEnt y]): Seq[ d aS zeFeatures] = {
     d aEnt  es.map {  d aEnt y =>
       d aEnt y.s zes.foldLeft( d aS zeFeatures(0, 0, 0))((accD  ns ons, d  ns ons) =>
         d aS zeFeatures(
          w dth = math.max(d  ns ons.w dth, accD  ns ons.w dth),
            ght = math.max(d  ns ons.  ght, accD  ns ons.  ght),
          res ze thod = math.max(d  ns ons.res ze thod.getValue, accD  ns ons.res ze thod)
        ))
    }
  }

  // Extracts  d a playback features
  pr vate def getPlaybackFeatures( d aEnt  es: Seq[ d aEnt y]): PlaybackFeatures = {
    val allPlaybackFeatures =  d aEnt  es
      .flatMap {  d aEnt y =>
         d aEnt y. d a nfo map {
          case v deoEnt y:  d a nfo.V deo nfo =>
            PlaybackFeatures(
              durat onMs = So (v deoEnt y.v deo nfo.durat onM ll s),
              b Rate = v deoEnt y.v deo nfo.var ants.maxBy(_.b Rate).b Rate,
              aspectRat oNum = So (v deoEnt y.v deo nfo.aspectRat o.nu rator),
              aspectRat oDen = So (v deoEnt y.v deo nfo.aspectRat o.denom nator)
            )
          case g fEnt y:  d a nfo.An matedG f nfo =>
            PlaybackFeatures(
              durat onMs = None,
              b Rate = g fEnt y.an matedG f nfo.var ants.maxBy(_.b Rate).b Rate,
              aspectRat oNum = So (g fEnt y.an matedG f nfo.aspectRat o.nu rator),
              aspectRat oDen = So (g fEnt y.an matedG f nfo.aspectRat o.denom nator)
            )
          case _ => PlaybackFeatures(None, None, None, None)
        }
      }
      .collect {
        case playbackFeatures: PlaybackFeatures => playbackFeatures
      }

     f (allPlaybackFeatures.nonEmpty) allPlaybackFeatures.maxBy(_.durat onMs)
    else PlaybackFeatures(None, None, None, None)
  }

  pr vate def get d aTagScreenNa s(tagMap: Map[Long, Seq[ d aTag]]): Seq[Str ng] =
    tagMap.values
      .flatMap(seq d aTag => seq d aTag.flatMap(_.screenNa ))
      .toSeq

  // Areas of t  faces  dent f ed  n t   d a ent  es
  pr vate def getFaceMapAreas( d aEnt  es: Seq[ d aEnt y]): Seq[ nt] = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
      faceData <-  tadata.faceData
      faces <- faceData.faces
    } y eld {
      faces
        .getOrElse("or g", Seq.empty[Face])
        .flatMap(f => f.bound ngBox.map(bb => bb.w dth * bb.  ght))
    }
  }.flatten

  // All ColorPalettes  n t   d a sorted by t  percentage  n descend ng order
  pr vate def getSortedColorPalette( d aEnt  es: Seq[ d aEnt y]): Seq[ColorPalette em] = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
      color nfo <-  tadata.color nfo
    } y eld {
      color nfo.palette
    }
  }.flatten.sortBy(_.percentage).reverse

  //  d's of st ckers appl ed by t  user
  pr vate def getSt ckerFeatures( d aEnt  es: Seq[ d aEnt y]): Seq[Long] = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
      st cker nfo <-  tadata.st cker nfo
    } y eld {
      st cker nfo.st ckers.map(_. d)
    }
  }.flatten

  // 3rd party  d a prov ders. eg. g phy for g fs
  pr vate def get d aOr g nProv ders( d aEnt  es: Seq[ d aEnt y]): Seq[Str ng] =
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
       d aOr g n <-  tadata.found d aOr g n
    } y eld {
       d aOr g n.prov der
    }

  pr vate def get sManaged( d aEnt  es: Seq[ d aEnt y]): Boolean = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
      manage nt nfo <-  tadata.manage nt nfo
    } y eld {
      manage nt nfo.managed
    }
  }.conta ns(true)

  pr vate def get s360( d aEnt  es: Seq[ d aEnt y]): Boolean = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
       nfo360 <-  tadata. nfo360
    } y eld {
       nfo360. s360
    }
  }.conta ns(So (true))

  pr vate def getV ewCount( d aEnt  es: Seq[ d aEnt y]): Opt on[Long] = {
    for {
       d aEnt y <-  d aEnt  es
       tadata <-  d aEnt y.add  onal tadata.toSeq
      engage nt nfo <-  tadata.engage nt nfo
      v ewCounts <- engage nt nfo.v ewCount
    } y eld {
      v ewCounts
    }
  }.reduceOpt on(_ max _)

  //  tadata def ned by t  user w n upload ng t   mage
  pr vate def getUserDef nedProduct tadataFeatures(
     d aEnt  es: Seq[ d aEnt y]
  ): Seq[UserDef nedProduct tadataFeatures] =
    for {
       d aEnt y <-  d aEnt  es
      userDef ned tadata <-  d aEnt y. tadata
    } y eld {
      UserDef nedProduct tadataFeatures(
         sMonet zable = userDef ned tadata.monet zable,
         sEmbeddable = userDef ned tadata.embeddable,
        hasSelectedPrev ew mage = So (userDef ned tadata.prev ew mage.nonEmpty),
        hasT le = userDef ned tadata.t le.map(_.nonEmpty),
        hasDescr pt on = userDef ned tadata.descr pt on.map(_.nonEmpty),
        hasV s S eCallToAct on = userDef ned tadata.callToAct ons.map(_.v s S e.nonEmpty),
        hasApp nstallCallToAct on = userDef ned tadata.callToAct ons.map(_.app nstall.nonEmpty),
        hasWatchNowCallToAct on = userDef ned tadata.callToAct ons.map(_.watchNow.nonEmpty)
      )
    }

  pr vate def getOptBooleanFromSeqOpt(
    seqOpt: Seq[Opt on[Boolean]],
    default: Boolean = false
  ): Opt on[Boolean] = So (
    seqOpt.ex sts(boolOpt => boolOpt.conta ns(true))
  )

}

case class  d aS zeFeatures(w dth:  nt,   ght:  nt, res ze thod:  nt)

case class PlaybackFeatures(
  durat onMs: Opt on[ nt],
  b Rate: Opt on[ nt],
  aspectRat oNum: Opt on[Short],
  aspectRat oDen: Opt on[Short])

case class UserDef nedProduct tadataFeatures(
   sMonet zable: Opt on[Boolean],
   sEmbeddable: Opt on[Boolean],
  hasSelectedPrev ew mage: Opt on[Boolean],
  hasT le: Opt on[Boolean],
  hasDescr pt on: Opt on[Boolean],
  hasV s S eCallToAct on: Opt on[Boolean],
  hasApp nstallCallToAct on: Opt on[Boolean],
  hasWatchNowCallToAct on: Opt on[Boolean])
