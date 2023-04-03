packagelon com.twittelonr.homelon_mixelonr.util.twelonelontypielon

import com.twittelonr.twelonelontypielon.{thriftscala => tp}

objelonct RelonquelonstFielonlds {

  val CorelonTwelonelontFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.IdFielonld.id),
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.CorelonDataFielonld.id)
  )
  val MelondiaFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.MelondiaFielonld.id),
  )
  val SelonlfThrelonadFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.SelonlfThrelonadMelontadataFielonld.id)
  )
  val MelonntionsTwelonelontFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.MelonntionsFielonld.id)
  )
  val SelonmanticAnnotationTwelonelontFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.elonschelonrbirdelonntityAnnotationsFielonld.id)
  )
  val NsfwLabelonlFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    // Twelonelont fielonlds containing NSFW relonlatelond attributelons.
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.NsfwHighReloncallLabelonlFielonld.id),
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.NsfwHighPreloncisionLabelonlFielonld.id),
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.NsfaHighReloncallLabelonlFielonld.id)
  )
  val SafelontyLabelonlFielonlds: Selont[tp.TwelonelontIncludelon] = Selont[tp.TwelonelontIncludelon](
    // Twelonelont fielonlds containing RTF labelonls for abuselon and spam.
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.SpamLabelonlFielonld.id),
    tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.AbusivelonLabelonlFielonld.id)
  )
  val ConvelonrsationControlFielonld: Selont[tp.TwelonelontIncludelon] =
    Selont[tp.TwelonelontIncludelon](tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.ConvelonrsationControlFielonld.id))

  val TwelonelontTPHydrationFielonlds: Selont[tp.TwelonelontIncludelon] = CorelonTwelonelontFielonlds ++
    NsfwLabelonlFielonlds ++
    SafelontyLabelonlFielonlds ++
    SelonmanticAnnotationTwelonelontFielonlds ++
    Selont(
      tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.TakelondownCountryCodelonsFielonld.id),
      // QTs imply a TwelonelontyPielon -> SGS relonquelonst delonpelonndelonncy
      tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.QuotelondTwelonelontFielonld.id),
      tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.CommunitielonsFielonld.id),
      // Fielonld relonquirelond for delontelonrmining if a Twelonelont was crelonatelond via Nelonws Camelonra.
      tp.TwelonelontIncludelon.TwelonelontFielonldId(tp.Twelonelont.ComposelonrSourcelonFielonld.id)
    )

  val TwelonelontStaticelonntitielonsFielonlds: Selont[tp.TwelonelontIncludelon] =
    MelonntionsTwelonelontFielonlds ++ CorelonTwelonelontFielonlds ++ SelonmanticAnnotationTwelonelontFielonlds ++ MelondiaFielonlds

  val ContelonntFielonlds: Selont[tp.TwelonelontIncludelon] = CorelonTwelonelontFielonlds ++ MelondiaFielonlds ++ SelonlfThrelonadFielonlds ++
    ConvelonrsationControlFielonld ++ SelonmanticAnnotationTwelonelontFielonlds ++
    Selont[tp.TwelonelontIncludelon](
      tp.TwelonelontIncludelon.MelondiaelonntityFielonldId(tp.Melondiaelonntity.AdditionalMelontadataFielonld.id))
}
