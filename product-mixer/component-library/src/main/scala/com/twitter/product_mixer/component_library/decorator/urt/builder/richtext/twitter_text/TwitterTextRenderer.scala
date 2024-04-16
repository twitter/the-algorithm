package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext.tw ter_text

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.ReferenceObject
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chText
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextAl gn nt
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextEnt y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.r chtext.R chTextFormat
 mport scala.annotat on.ta lrec
 mport scala.collect on.mutable

object Tw terTextRenderer {

  /**
   * Creates a new [[Tw terTextRenderer]]  nstance.
   * @param text      T   n  al text representat on
   * @param rtl       Def nes w t r t  text  s  n an RTL language
   * @param al gn nt Ass gns t  [[R chTextAl gn nt]] of t  g ven text for d splay
   * @return          A new [[Tw terTextRenderer]]  nstance
   */
  def apply(
    text: Str ng,
    rtl: Opt on[Boolean] = None,
    al gn nt: Opt on[R chTextAl gn nt] = None
  ): Tw terTextRenderer = {
    Tw terTextRenderer(rtl, al gn nt).append(text)
  }

  /**
   * Creates a new [[Tw terTextRenderer]]  nstance from a product-m xer [[R chText]] object.
   * Converts Un code ent y  ndexes  nto JVM Str ng  ndexes.
   * @param r chText  T  product-m xer [[R chText]] representat on
   * @return          A new [[Tw terTextRenderer]]  nstance
   */
  def fromR chText(r chText: R chText): Tw terTextRenderer = {
    val bu lder = Tw terTextRenderer(r chText.text, r chText.rtl, r chText.al gn nt)
    r chText.ent  es.foreach { e =>
      val start ndex = r chText.text.offsetByCodePo nts(0, e.from ndex)
      val end ndex = r chText.text.offsetByCodePo nts(0, e.to ndex)
      e.format.foreach { f =>
        bu lder.setFormat(start ndex, end ndex, f)
      }
      e.ref.foreach { r =>
        bu lder.setRefObject(start ndex, end ndex, r)
      }
    }
    bu lder
  }

  pr vate def bu ldR chTextEnt y(
    text: Str ng,
    ent y: Tw terTextRendererEnt y[_]
  ): R chTextEnt y = {
    val from ndex = text.codePo ntCount(0, ent y.start ndex)
    val to ndex = text.codePo ntCount(0, ent y.end ndex)

    ent y.value match {
      case format: R chTextFormat =>
        R chTextEnt y(from ndex, to ndex, ref = None, format = So (format))
      case ref: ReferenceObject =>
        R chTextEnt y(from ndex, to ndex, ref = So (ref), format = None)
    }
  }
}

case class Tw terTextRenderer(
  rtl: Opt on[Boolean],
  al gn nt: Opt on[R chTextAl gn nt],
) {
  pr vate[t ] val textBu lder = new mutable.Str ngBu lder()

  pr vate[r chtext] val formatBuffer =
    mutable.ArrayBuffer[Tw terTextRendererEnt y[R chTextFormat]]()
  pr vate[r chtext] val refObjectBuffer =
    mutable.ArrayBuffer[Tw terTextRendererEnt y[ReferenceObject]]()

  /**
   * Appends a str ng w h attac d [[R chTextFormat]] and [[ReferenceObject]]  nformat on.
   * @param str ng    T  text to append to t  end of t  ex st ng text
   * @param format    T  [[R chTextFormat]] ass gned to t  new text
   * @param refObject T  [[ReferenceObject]] ass gned to t  new text
   * @return          t 
   */
  def append(
    str ng: Str ng,
    format: Opt on[R chTextFormat] = None,
    refObject: Opt on[ReferenceObject] = None
  ): Tw terTextRenderer = {
     f (str ng.nonEmpty) {
      val start = textBu lder.length
      val end = start + str ng.length
      format.foreach { f =>
        formatBuffer.append(Tw terTextRendererEnt y(start, end, f))
      }
      refObject.foreach { r =>
        refObjectBuffer.append(Tw terTextRendererEnt y(start, end, r))
      }
      textBu lder.append(str ng)
    }
    t 
  }

  /**
   * Bu lds a new [[R chText]] thr ft  nstance w h Un code ent y ranges.
   */
  def bu ld: R chText = {
    val r chTextStr ng = t .text
    val r chTextEnt  es = t .ent  es
      .map { e =>
        Tw terTextRenderer.bu ldR chTextEnt y(r chTextStr ng, e)
      }

    R chText(
      text = r chTextStr ng,
      rtl = rtl,
      al gn nt = al gn nt,
      ent  es = r chTextEnt  es.toL st
    )
  }

  /**
   * Mod f es t  Tw terTextRenderer w h t  prov ded [[Tw terTextRendererProcessor]]
   */
  def transform(tw terTextProcessor: Tw terTextRendererProcessor): Tw terTextRenderer = {
    tw terTextProcessor.process(t )
  }

  /**
   * Bu lds and returns a sorted l st of [[Tw terTextRendererEnt y]] w h JVM Str ng  ndex ent y ranges.
   */
  def ent  es: Seq[Tw terTextRendererEnt y[_]] = {
    bu ldEnt  es(formatBuffer.toL st, refObjectBuffer.toL st)
  }

  /**
   * Ass gns a [[R chTextFormat]] to t  g ven range wh le keep ng ex st ng formatt ng  nformat on.
   * New formatt ng w ll only be ass gned to unformatted text ranges.
   * @param start  Start  ndex to apply formatt ng ( nclus ve)
   * @param end    End  ndex to apply formatt ng (exclus ve)
   * @param format T  format to ass gn
   * @return       t 
   */
  def  rgeFormat(start:  nt, end:  nt, format: R chTextFormat): Tw terTextRenderer = {
    val dateRange(start, end)
    var  nject on ndex: Opt on[ nt] = None
    var ent y = Tw terTextRendererEnt y(start, end, format)

    val buffer = mutable.ArrayBuffer[Tw terTextRendererEnt y[R chTextFormat]]()
    val  erator = formatBuffer.z pW h ndex.reverse erator

    wh le ( erator.hasNext &&  nject on ndex. sEmpty) {
       erator.next match {
        case (e,  )  f e.start ndex >= end =>
          buffer.append(e)

        case (e,  )  f e.enclosed n(ent y.start ndex, ent y.end ndex) =>
          val endEnt y = ent y.copy(start ndex = e.end ndex)
           f (endEnt y.nonEmpty) { buffer.append(endEnt y) }
          buffer.append(e)
          ent y = ent y.copy(end ndex = e.start ndex)

        case (e,  )  f e.encloses(ent y.start ndex, ent y.end ndex) =>
          buffer.append(e.copy(start ndex = ent y.end ndex))
          buffer.append(e.copy(end ndex = ent y.start ndex))
           nject on ndex = So (  + 1)

        case (e,  )  f e.startsBet en(ent y.start ndex, ent y.end ndex) =>
          buffer.append(e)
          ent y = ent y.copy(end ndex = e.start ndex)

        case (e,  )  f e.endsBet en(ent y.start ndex, ent y.end ndex) =>
          buffer.append(e)
          ent y = ent y.copy(start ndex = e.end ndex)
           nject on ndex = So (  + 1)

        case (e,  )  f e.end ndex <= ent y.start ndex =>
          buffer.append(e)
           nject on ndex = So (  + 1)

        case _ => // do noth ng
      }
    }

    val  ndex =  nject on ndex.map(_ - 1).getOrElse(0)
    formatBuffer.remove( ndex, formatBuffer.length -  ndex)
    formatBuffer.appendAll(buffer.reverse)

     f (ent y.nonEmpty) {
      formatBuffer. nsert( nject on ndex.getOrElse(0), ent y)
    }

    t 
  }

  /**
   * Removes text, formatt ng, and refObject  nformat on from t  g ven range.
   * @param start  Start  ndex to apply formatt ng ( nclus ve)
   * @param end    End  ndex to apply formatt ng (exclus ve)
   * @return       t 
   */
  def remove(start:  nt, end:  nt): Tw terTextRenderer = replace(start, end, "")

  /**
   * Replaces text, formatt ng, and refObject  nformat on  n t  g ven range.
   * @param start     Start  ndex to apply formatt ng ( nclus ve)
   * @param end       End  ndex to apply formatt ng (exclus ve)
   * @param str ng    T  new text to  nsert
   * @param format    T  [[R chTextFormat]] ass gned to t  new text
   * @param refObject T  [[ReferenceObject]] ass gned to t  new text
   * @return          t 
   */
  def replace(
    start:  nt,
    end:  nt,
    str ng: Str ng,
    format: Opt on[R chTextFormat] = None,
    refObject: Opt on[ReferenceObject] = None
  ): Tw terTextRenderer = {
    val dateRange(start, end)

    val newEnd = start + str ng.length
    val format nject ndex = removeAndOffsetFormats(start, end, str ng.length)
    val refObject nject ndex = removeAndOffsetRefObjects(start, end, str ng.length)
    format.foreach { f =>
      formatBuffer. nsert(format nject ndex, Tw terTextRendererEnt y(start, newEnd, f))
    }
    refObject.foreach { r =>
      refObjectBuffer. nsert(refObject nject ndex, Tw terTextRendererEnt y(start, newEnd, r))
    }
    textBu lder.replace(start, end, str ng)

    t 
  }

  /**
   * Ass gns a [[R chTextFormat]] to t  g ven range. Tr ms ex st ng format ranges that overlap t 
   * new format range. Removes format ranges that fall w h n t  new range.
   * @param start  Start  ndex to apply formatt ng ( nclus ve)
   * @param end    End  ndex to apply formatt ng (exclus ve)
   * @param format T  format to ass gn
   * @return       t 
   */
  def setFormat(start:  nt, end:  nt, format: R chTextFormat): Tw terTextRenderer = {
    val dateRange(start, end)
    val buffer ndex = removeAndOffsetFormats(start, end, end - start)
    formatBuffer. nsert(buffer ndex, Tw terTextRendererEnt y(start, end, format))

    t 
  }

  pr vate[t ] def removeAndOffsetFormats(start:  nt, end:  nt, newS ze:  nt):  nt = {
    val newEnd = start + newS ze
    val offset = newEnd - end
    var  nject on ndex: Opt on[ nt] = None

    val buffer = mutable.ArrayBuffer[Tw terTextRendererEnt y[R chTextFormat]]()
    val  erator = formatBuffer.z pW h ndex.reverse erator

    wh le ( erator.hasNext &&  nject on ndex. sEmpty) {
       erator.next match {
        case (e,  )  f e.start ndex >= end =>
          buffer.append(e.offset(offset))
        case (e,  )  f e.encloses(start, end) =>
          buffer.append(e.copy(start ndex = newEnd, end ndex = e.end ndex + offset))
          buffer.append(e.copy(end ndex = e.end ndex + offset))
           nject on ndex = So (  + 1)
        case (e,  )  f e.endsBet en(start, end) =>
          buffer.append(e.copy(end ndex = start))
           nject on ndex = So (  + 1)
        case (e,  )  f e.startsBet en(start, end) =>
          buffer.append(e.copy(start ndex = newEnd, end ndex = e.end ndex + offset))
        case (e,  )  f e.end ndex <= start =>
          buffer.append(e)
           nject on ndex = So (  + 1)
        case _ => // do noth ng
      }
    }
    val  ndex =  nject on ndex.map(_ - 1).getOrElse(0)
    formatBuffer.remove( ndex, formatBuffer.length -  ndex)
    formatBuffer.appendAll(buffer.reverse)

     nject on ndex.getOrElse(0)
  }

  pr vate[t ] def val dateRange(start:  nt, end:  nt): Un  = {
    requ re(
      start >= 0 && start < textBu lder.length && end > start && end <= textBu lder.length,
      s"T  start ($start) and end ($end)  ndexes must be w h n t  text range (0..${textBu lder.length})"
    )
  }

  /**
   * Ass gns a [[ReferenceObject]] to t  g ven range. S nce   makes l tle sense to tr m object
   * ranges, ex st ng  ntersect ng or overlapp ng ranges are removed ent rely.
   * @param start  Start  ndex to apply formatt ng ( nclus ve)
   * @param end       End  ndex to apply formatt ng (exclus ve)
   * @param refObject T  [[ReferenceObject]] to ass gn
   * @return          t 
   */
  def setRefObject(start:  nt, end:  nt, refObject: ReferenceObject): Tw terTextRenderer = {
    val dateRange(start, end)
    val buffer ndex = removeAndOffsetRefObjects(start, end, end - start)
    refObjectBuffer. nsert(buffer ndex, Tw terTextRendererEnt y(start, end, refObject))

    t 
  }

  pr vate[t ] def removeAndOffsetRefObjects(start:  nt, end:  nt, newS ze:  nt):  nt = {
    val newEnd = start + newS ze
    val offset = newEnd - end
    var  nject on ndex: Opt on[ nt] = None

    val buffer = mutable.ArrayBuffer[Tw terTextRendererEnt y[ReferenceObject]]()
    val  erator = refObjectBuffer.z pW h ndex.reverse erator

    wh le ( erator.hasNext &&  nject on ndex. sEmpty) {
       erator.next match {
        case (e,  )  f e.start ndex >= end => buffer.append(e.offset(offset))
        case (e,  )  f e.end ndex <= start =>  nject on ndex = So (  + 1)
        case _ => // do noth ng
      }
    }
    val  ndex =  nject on ndex.getOrElse(0)
    refObjectBuffer.remove( ndex, refObjectBuffer.length -  ndex)
    refObjectBuffer.appendAll(buffer.reverse)

     ndex
  }

  /**
   * Bu lds and returns t  full Tw terTextRenderer text w h any changes appl ed to t  bu lder  nstance.
   */
  def text: Str ng = {
    textBu lder.mkStr ng
  }

  @ta lrec
  pr vate def bu ldEnt  es(
    formats: L st[Tw terTextRendererEnt y[R chTextFormat]],
    refs: L st[Tw terTextRendererEnt y[ReferenceObject]],
    acc: L st[Tw terTextRendererEnt y[_]] = L st()
  ): Seq[Tw terTextRendererEnt y[_]] = {
    (formats, refs) match {
      case (N l, N l) => acc
      case (rema n ngFormats, N l) => acc ++ rema n ngFormats
      case (N l, rema n ngRefs) => acc ++ rema n ngRefs

      case (format +: rema n ngFormats, ref +: rema n ngRefs)
           f format.start ndex < ref.start ndex || (format.start ndex == ref.start ndex && format.end ndex < ref.end ndex) =>
        bu ldEnt  es(rema n ngFormats, refs, acc :+ format)

      case (format +: rema n ngFormats, ref +: rema n ngRefs)
           f format.start ndex == ref.start ndex && format.end ndex == ref.end ndex =>
        bu ldEnt  es(rema n ngFormats, rema n ngRefs, acc :+ format :+ ref)

      case (_, ref +: rema n ngRefs) =>
        bu ldEnt  es(formats, rema n ngRefs, acc :+ ref)
    }
  }
}

case class Tw terTextRendererEnt y[+T] pr vate[r chtext] (
  start ndex:  nt,
  end ndex:  nt,
  value: T) {
  requ re(start ndex <= end ndex, "start ndex must be <= than end ndex")

  def nonEmpty: Boolean = ! sEmpty

  def  sEmpty: Boolean = start ndex == end ndex

  pr vate[r chtext] def enclosed n(start:  nt, end:  nt): Boolean = {
    start <= start ndex && end ndex <= end
  }

  pr vate[r chtext] def encloses(start:  nt, end:  nt): Boolean = {
    start ndex < start && end < end ndex
  }

  pr vate[r chtext] def endsBet en(start:  nt, end:  nt): Boolean = {
    start < end ndex && end ndex <= end && start ndex < start
  }

  pr vate[r chtext] def offset(num:  nt): Tw terTextRendererEnt y[T] = {
    copy(start ndex = start ndex + num, end ndex = end ndex + num)
  }

  pr vate[r chtext] def startsBet en(start:  nt, end:  nt): Boolean = {
    start ndex >= start && start ndex < end && end ndex > end
  }
}
