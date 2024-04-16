package com.tw ter.t etyp e.thr ftscala.ent  es

 mport com.tw ter.servo.data.Mutat on
 mport com.tw ter.tco_ut l.TcoUrl
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala.ent  es. mpl c s._
 mport com.tw ter.t etyp e.t ettext.Part alHtmlEncod ng
 mport com.tw ter.t etyp e.t ettext.TextEnt y
 mport com.tw ter.t etyp e.t ettext.TextMod f cat on
 mport com.tw ter.t etyp e.ut l.T etLenses
 mport com.tw ter.tw tertext.Extractor
 mport scala.collect on.JavaConverters._

/**
 * Conta ns funct ons to collect urls,  nt ons, hashtags, and cashtags from t  text of t ets and  ssages
 */
object Ent yExtractor {
  //   only use one conf gurat on of com.tw ter.tw tertext.Extractor, so  's
  // OK to share one global reference. T  only ava lable
  // conf gurat on opt on  s w t r to extract URLs w hout protocols
  // (defaults to true)
  pr vate[t ] val extractor = new Extractor

  // T  tw ter-text l brary operates on unencoded text, but   store
  // and process HTML-encoded text. T  TextMod f cat on returned
  // from t  funct on conta ns t  decoded text wh ch   w ll operate on,
  // but also prov des us w h t  ab l y to map t   nd ces on
  // t  tw ter-text ent  es back to t  ent  es on t  encoded text.
  pr vate val htmlEncodedTextToEncodeMod f cat on: Str ng => TextMod f cat on =
    text =>
      Part alHtmlEncod ng
        .decodeW hMod f cat on(text)
        .getOrElse(TextMod f cat on. dent y(text))
        . nverse

  pr vate[t ] val extractAllUrlsFromTextMod: TextMod f cat on => Seq[UrlEnt y] =
    extractUrls(false)

  val extractAllUrls: Str ng => Seq[UrlEnt y] =
    htmlEncodedTextToEncodeMod f cat on.andT n(extractAllUrlsFromTextMod)

  pr vate[t ] val extractTcoUrls: TextMod f cat on => Seq[UrlEnt y] =
    extractUrls(true)

  pr vate[t ] def extractUrls(tcoOnly: Boolean): TextMod f cat on => Seq[UrlEnt y] =
    mkEnt yExtractor[UrlEnt y](
      extractor.extractURLsW h nd ces(_).asScala.f lter { e =>
         f (tcoOnly) TcoUrl. sTcoUrl(e.getValue) else true
      },
      UrlEnt y(_, _, _)
    )

  pr vate[t ] val extract nt onsFromTextMod: TextMod f cat on => Seq[ nt onEnt y] =
    mkEnt yExtractor[ nt onEnt y](
      extractor.extract nt onedScreenna sW h nd ces(_).asScala,
       nt onEnt y(_, _, _)
    )

  val extract nt ons: Str ng => Seq[ nt onEnt y] =
    htmlEncodedTextToEncodeMod f cat on.andT n(extract nt onsFromTextMod)

  pr vate[t ] val extractHashtagsFromTextMod: TextMod f cat on => Seq[HashtagEnt y] =
    mkEnt yExtractor[HashtagEnt y](
      extractor.extractHashtagsW h nd ces(_).asScala,
      HashtagEnt y(_, _, _)
    )

  val extractHashtags: Str ng => Seq[HashtagEnt y] =
    htmlEncodedTextToEncodeMod f cat on.andT n(extractHashtagsFromTextMod)

  pr vate[t ] val extractCashtagsFromTextMod: TextMod f cat on => Seq[CashtagEnt y] =
    mkEnt yExtractor[CashtagEnt y](
      extractor.extractCashtagsW h nd ces(_).asScala,
      CashtagEnt y(_, _, _)
    )

  val extractCashtags: Str ng => Seq[CashtagEnt y] =
    htmlEncodedTextToEncodeMod f cat on.andT n(extractCashtagsFromTextMod)

  pr vate[t ] def mkEnt yExtractor[E: TextEnt y](
    extract: Str ng => Seq[Extractor.Ent y],
    construct: (Short, Short, Str ng) => E
  ): TextMod f cat on => Seq[E] =
    htmlEncodedMod => {
      val convert: Extractor.Ent y => Opt on[E] =
        e =>
          for {
            start <- asShort(e.getStart. ntValue)
            end <- asShort(e.getEnd. ntValue)
             f e.getValue != null
            res <- htmlEncodedMod.re ndexEnt y(construct(start, end, e.getValue))
          } y eld res

      val ent  es = extract(htmlEncodedMod.or g nal)
      extractor.mod fy nd cesFromUTF16ToUn code(htmlEncodedMod.or g nal, ent  es.asJava)
      ent  es.map(convert).flatten
    }

  pr vate[t ] def asShort( :  nt): Opt on[Short] =
     f ( . sVal dShort) So ( .toShort) else None

  pr vate[t ] def mutat on(extractUrls: Boolean): Mutat on[T et] =
    Mutat on { t et =>
      val htmlEncodedMod = htmlEncodedTextToEncodeMod f cat on(T etLenses.text.get(t et))

      So (
        t et.copy(
          urls =  f (extractUrls) So (extractTcoUrls(htmlEncodedMod)) else t et.urls,
           nt ons = So (extract nt onsFromTextMod(htmlEncodedMod)),
          hashtags = So (extractHashtagsFromTextMod(htmlEncodedMod)),
          cashtags = So (extractCashtagsFromTextMod(htmlEncodedMod))
        )
      )
    }

  val mutat onW houtUrls: Mutat on[T et] = mutat on(false)
  val mutat onAll: Mutat on[T et] = mutat on(true)
}
