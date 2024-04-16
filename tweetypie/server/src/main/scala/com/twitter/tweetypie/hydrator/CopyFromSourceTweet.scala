package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.t ettext.T etText
 mport com.tw ter.t etyp e.thr ftscala._

object CopyFromS ceT et {

  /**
   * A `ValueHydrator` that cop es and/or  rges certa n f elds from a ret et's s ce
   * t et  nto t  ret et.
   */
  def hydrator: ValueHydrator[T etData, T etQuery.Opt ons] =
    ValueHydrator.map { (td, _) =>
      td.s ceT etResult.map(_.value.t et) match {
        case None => ValueState.unmod f ed(td)
        case So (src) => ValueState.mod f ed(td.copy(t et = copy(src, td.t et)))
      }
    }

  /**
   * Updates `dst` w h f elds from `src`. T   s more compl cated than   would th nk, because:
   *
   *   - t  t et has an extra  nt on ent y due to t  "RT @user" pref x;
   *   - t  ret et text may be truncated at t  end, and doesn't necessar ly conta n all of t 
   *     t  text from t  s ce t et.  truncat on may happen  n t  m ddle of ent y.
   *   - t  text  n t  ret et may have a d fferent un code normal zat on, wh ch affects
   *     code po nt  nd ces. t   ans ent  es aren't sh fted by a f xed amount equal to
   *     t  RT pref x.
   *   - url ent  es, w n hydrated, may be converted to  d a ent  es; url ent  es may not
   *     be hydrated  n t  ret et, so t  s ce t et may have a  d a ent y that corresponds
   *     to an unhydrated url ent y  n t  ret et.
   *   - t re may be mult ple  d a ent  es that map to a s ngle url ent y, because t  t et
   *     may have mult ple photos.
   */
  def copy(src: T et, dst: T et): T et = {
    val srcCoreData = src.coreData.get
    val dstCoreData = dst.coreData.get

    // get t  code po nt  ndex of t  end of t  text
    val max = getText(dst).codePo ntCount(0, getText(dst).length).toShort

    // get all ent  es from t  s ce t et,  rged  nto a s ngle l st sorted by from ndex.
    val srcEnt  es = getWrappedEnt  es(src)

    // sa  for t  ret et, but drop f rst @ nt on, add back later
    val dstEnt  es = getWrappedEnt  es(dst).drop(1)

    //  rge  nd ces from dst  nto srcEnt  es. at t  end, resort ent  es back
    // to t  r or g nal order ng.  for  d a ent  es, order matters to cl ents.
    val  rgedEnt  es =  rge(srcEnt  es, dstEnt  es, max).sortBy(_.pos  on)

    // extract ent  es back out by type
    val  nt ons =  rgedEnt  es.collect { case Wrapped nt onEnt y(e, _) => e }
    val hashtags =  rgedEnt  es.collect { case WrappedHashtagEnt y(e, _) => e }
    val cashtags =  rgedEnt  es.collect { case WrappedCashtagEnt y(e, _) => e }
    val urls =  rgedEnt  es.collect { case WrappedUrlEnt y(e, _) => e }
    val  d a =  rgedEnt  es.collect { case Wrapped d aEnt y(e, _) => e }

    //  rge t  updated ent  es back  nto t  ret et, add ng t  RT @ nt on back  n
    dst.copy(
      coreData = So (
        dstCoreData.copy(
          has d a = srcCoreData.has d a,
          hasTakedown = dstCoreData.hasTakedown || srcCoreData.hasTakedown
        )
      ),
       nt ons = So (get nt ons(dst).take(1) ++  nt ons),
      hashtags = So (hashtags),
      cashtags = So (cashtags),
      urls = So (urls),
       d a = So ( d a.map(updateS ceStatus d(src. d, getUser d(src)))),
      quotedT et = src.quotedT et,
      card2 = src.card2,
      cards = src.cards,
      language = src.language,
       d aTags = src. d aTags,
      spamLabel = src.spamLabel,
      takedownCountryCodes =
         rgeTakedowns(Seq(src, dst).map(T etLenses.takedownCountryCodes.get): _*),
      conversat onControl = src.conversat onControl,
      exclus veT etControl = src.exclus veT etControl
    )
  }

  /**
   *  rges one or more opt onal l sts of takedowns.   f no l sts are def ned, returns None.
   */
  pr vate def  rgeTakedowns(takedowns: Opt on[Seq[CountryCode]]*): Opt on[Seq[CountryCode]] =
     f (takedowns.ex sts(_. sDef ned)) {
      So (takedowns.flatten.flatten.d st nct.sorted)
    } else {
      None
    }

  /**
   * A ret et should never have  d a w hout a s ce_status_ d or s ce_user_ d
   */
  pr vate def updateS ceStatus d(
    srcT et d: T et d,
    srcUser d: User d
  ):  d aEnt y =>  d aEnt y =
     d aEnt y =>
       f ( d aEnt y.s ceStatus d.nonEmpty) {
        // w n s ceStatus d  s set t   nd cates t   d a  s "pasted  d a" so t  values
        // should already be correct (ret et ng won't change s ceStatus d / s ceUser d)
         d aEnt y
      } else {
         d aEnt y.copy(
          s ceStatus d = So (srcT et d),
          s ceUser d = So ( d aEnt y.s ceUser d.getOrElse(srcUser d))
        )
      }

  /**
   * Attempts to match up ent  es from t  s ce t et w h ent  es from t  ret et,
   * and to use t  s ce t et ent  es but sh fted to t  ret et ent y  nd ces.   f an ent y
   * got truncated at t  end of t  ret et text,   drop   and any follow ng ent  es.
   */
  pr vate def  rge(
    srcEnt  es: L st[WrappedEnt y],
    rtEnt  es: L st[WrappedEnt y],
    max ndex: Short
  ): L st[WrappedEnt y] = {
    (srcEnt  es, rtEnt  es) match {
      case (N l, N l) =>
        // successfully matc d all ent  es!
        N l

      case (N l, _) =>
        // no more s ce t et ent  es, but   st ll have rema n ng ret et ent  es.
        // t  can happen  f a a text truncat on turns so th ng  nval d l ke #tag1#tag2 or
        // @ nt on1@ nt on2  nto a val d ent y. just drop all t  rema n ng ret et ent  es.
        N l

      case (_, N l) =>
        // no more ret et ent  es, wh ch  ans t  rema n ng ent  es have been truncated.
        N l

      case (src ad :: srcTa l, rt ad :: rtTa l) =>
        //   have more ent  es from t  s ce t et and t  ret et.  typ cally,   can
        // match t se ent  es because t y have t  sa  normal zed text, but t  ret et
        // ent y m ght be truncated, so   allow for a pref x match  f t  ret et ent y
        // ends at t  end of t  t et.
        val poss blyTruncated = rt ad.to ndex == max ndex - 1
        val exactMatch = src ad.normal zedText == rt ad.normal zedText

         f (exactMatch) {
          // t re could be mult ple  d a ent  es for t  sa  t.co url, so   need to f nd
          // cont guous group ngs of ent  es that share t  sa  from ndex.
          val rtTa l = rtEnt  es.dropWh le(_.from ndex == rt ad.from ndex)
          val srcGroup =
            srcEnt  es
              .takeWh le(_.from ndex == src ad.from ndex)
              .map(_.sh ft(rt ad.from ndex, rt ad.to ndex))
          val srcTa l = srcEnt  es.drop(srcGroup.s ze)

          srcGroup ++  rge(srcTa l, rtTa l, max ndex)
        } else {
          //  f   encounter a m smatch,    s most l kely because of truncat on,
          // so   stop  re.
          N l
        }
    }
  }

  /**
   * Wraps all t  ent  es w h t  appropr ate WrappedEnt y subclasses,  rges t m  nto
   * a s ngle l st, and sorts by from ndex.
   */
  pr vate def getWrappedEnt  es(t et: T et): L st[WrappedEnt y] =
    (getUrls(t et).z pW h ndex.map { case (e, p) => WrappedUrlEnt y(e, p) } ++
      get d a(t et).z pW h ndex.map { case (e, p) => Wrapped d aEnt y(e, p) } ++
      get nt ons(t et).z pW h ndex.map { case (e, p) => Wrapped nt onEnt y(e, p) } ++
      getHashtags(t et).z pW h ndex.map { case (e, p) => WrappedHashtagEnt y(e, p) } ++
      getCashtags(t et).z pW h ndex.map { case (e, p) => WrappedCashtagEnt y(e, p) })
      .sortBy(_.from ndex)
      .toL st

  /**
   * T  thr ft-ent y classes don't share a common ent y parent class, so   wrap
   * t m w h a class that allows us to m x ent  es toget r  nto a s ngle l st, and
   * to prov de a gener c  nterface for sh ft ng  nd c es.
   */
  pr vate sealed abstract class WrappedEnt y(
    val from ndex: Short,
    val to ndex: Short,
    val rawText: Str ng) {

    /** t  or g nal pos  on of t  ent y w h n t  ent y group */
    val pos  on:  nt

    val normal zedText: Str ng = T etText.nfcNormal ze(rawText).toLo rCase

    def sh ft(from ndex: Short, to ndex: Short): WrappedEnt y
  }

  pr vate case class WrappedUrlEnt y(ent y: UrlEnt y, pos  on:  nt)
      extends WrappedEnt y(ent y.from ndex, ent y.to ndex, ent y.url) {
    overr de def sh ft(from ndex: Short, to ndex: Short): WrappedUrlEnt y =
      copy(ent y.copy(from ndex = from ndex, to ndex = to ndex))
  }

  pr vate case class Wrapped d aEnt y(ent y:  d aEnt y, pos  on:  nt)
      extends WrappedEnt y(ent y.from ndex, ent y.to ndex, ent y.url) {
    overr de def sh ft(from ndex: Short, to ndex: Short): Wrapped d aEnt y =
      copy(ent y.copy(from ndex = from ndex, to ndex = to ndex))
  }

  pr vate case class Wrapped nt onEnt y(ent y:  nt onEnt y, pos  on:  nt)
      extends WrappedEnt y(ent y.from ndex, ent y.to ndex, ent y.screenNa ) {
    overr de def sh ft(from ndex: Short, to ndex: Short): Wrapped nt onEnt y =
      copy(ent y.copy(from ndex = from ndex, to ndex = to ndex))
  }

  pr vate case class WrappedHashtagEnt y(ent y: HashtagEnt y, pos  on:  nt)
      extends WrappedEnt y(ent y.from ndex, ent y.to ndex, ent y.text) {
    overr de def sh ft(from ndex: Short, to ndex: Short): WrappedHashtagEnt y =
      copy(ent y.copy(from ndex = from ndex, to ndex = to ndex))
  }

  pr vate case class WrappedCashtagEnt y(ent y: CashtagEnt y, pos  on:  nt)
      extends WrappedEnt y(ent y.from ndex, ent y.to ndex, ent y.text) {
    overr de def sh ft(from ndex: Short, to ndex: Short): WrappedCashtagEnt y =
      copy(ent y.copy(from ndex = from ndex, to ndex = to ndex))
  }
}
