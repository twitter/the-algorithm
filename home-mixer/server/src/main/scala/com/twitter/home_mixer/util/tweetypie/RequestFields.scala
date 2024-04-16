package com.tw ter.ho _m xer.ut l.t etyp e

 mport com.tw ter.t etyp e.{thr ftscala => tp}

object RequestF elds {

  val CoreT etF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    tp.T et nclude.T etF eld d(tp.T et. dF eld. d),
    tp.T et nclude.T etF eld d(tp.T et.CoreDataF eld. d)
  )
  val  d aF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    tp.T et nclude.T etF eld d(tp.T et. d aF eld. d),
  )
  val SelfThreadF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    tp.T et nclude.T etF eld d(tp.T et.SelfThread tadataF eld. d)
  )
  val  nt onsT etF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    tp.T et nclude.T etF eld d(tp.T et. nt onsF eld. d)
  )
  val Semant cAnnotat onT etF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    tp.T et nclude.T etF eld d(tp.T et.Esc rb rdEnt yAnnotat onsF eld. d)
  )
  val NsfwLabelF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    // T et f elds conta n ng NSFW related attr butes.
    tp.T et nclude.T etF eld d(tp.T et.NsfwH ghRecallLabelF eld. d),
    tp.T et nclude.T etF eld d(tp.T et.NsfwH ghPrec s onLabelF eld. d),
    tp.T et nclude.T etF eld d(tp.T et.NsfaH ghRecallLabelF eld. d)
  )
  val SafetyLabelF elds: Set[tp.T et nclude] = Set[tp.T et nclude](
    // T et f elds conta n ng RTF labels for abuse and spam.
    tp.T et nclude.T etF eld d(tp.T et.SpamLabelF eld. d),
    tp.T et nclude.T etF eld d(tp.T et.Abus veLabelF eld. d)
  )
  val Conversat onControlF eld: Set[tp.T et nclude] =
    Set[tp.T et nclude](tp.T et nclude.T etF eld d(tp.T et.Conversat onControlF eld. d))

  val T etTPHydrat onF elds: Set[tp.T et nclude] = CoreT etF elds ++
    NsfwLabelF elds ++
    SafetyLabelF elds ++
    Semant cAnnotat onT etF elds ++
    Set(
      tp.T et nclude.T etF eld d(tp.T et.TakedownCountryCodesF eld. d),
      // QTs  mply a T etyP e -> SGS request dependency
      tp.T et nclude.T etF eld d(tp.T et.QuotedT etF eld. d),
      tp.T et nclude.T etF eld d(tp.T et.Commun  esF eld. d),
      // F eld requ red for determ n ng  f a T et was created v a News Ca ra.
      tp.T et nclude.T etF eld d(tp.T et.ComposerS ceF eld. d),
      tp.T et nclude.T etF eld d(tp.T et.LanguageF eld. d)
    )

  val T etStat cEnt  esF elds: Set[tp.T et nclude] =
     nt onsT etF elds ++ CoreT etF elds ++ Semant cAnnotat onT etF elds ++  d aF elds

  val ContentF elds: Set[tp.T et nclude] = CoreT etF elds ++  d aF elds ++ SelfThreadF elds ++
    Conversat onControlF eld ++ Semant cAnnotat onT etF elds ++
    Set[tp.T et nclude](
      tp.T et nclude. d aEnt yF eld d(tp. d aEnt y.Add  onal tadataF eld. d))
}
