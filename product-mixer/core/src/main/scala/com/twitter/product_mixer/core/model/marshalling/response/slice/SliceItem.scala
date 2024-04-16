package com.tw ter.product_m xer.core.model.marshall ng.response.sl ce

 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng

/**
 * T se are t  Ad Types exposed on AdUn s
 *
 * T y are to be kept  n sync w h strato/conf g/src/thr ft/com/tw ter/strato/graphql/hubble.thr ft
 */
sealed tra  AdType
object AdType {
  case object T et extends AdType
  case object Account extends AdType
  case object  nStreamV deo extends AdType
  case object D splayCreat ve extends AdType
  case object Trend extends AdType
  case object Spotl ght extends AdType
  case object Takeover extends AdType
}

tra  Sl ce em
case class T et em( d: Long) extends Sl ce em
case class User em( d: Long) extends Sl ce em
case class Tw terL st em( d: Long) extends Sl ce em
case class DMConvoSearch em( d: Str ng, lastReadableEvent d: Opt on[Long]) extends Sl ce em
case class DMEvent em( d: Long) extends Sl ce em
case class DMConvo em( d: Str ng, lastReadableEvent d: Opt on[Long]) extends Sl ce em
case class DM ssageSearch em( d: Long) extends Sl ce em
case class Top c em( d: Long) extends Sl ce em
case class Typea adEvent em(event d: Long,  tadata: Opt on[Typea ad tadata]) extends Sl ce em
case class Typea adQuerySuggest on em(query: Str ng,  tadata: Opt on[Typea ad tadata])
    extends Sl ce em
case class Typea adUser em(
  user d: Long,
   tadata: Opt on[Typea ad tadata],
  badges: Seq[UserBadge])
    extends Sl ce em
case class Ad em(adUn  d: Long, adAccount d: Long) extends Sl ce em
case class AdCreat ve em(creat ve d: Long, adType: AdType, adAccount d: Long) extends Sl ce em
case class AdGroup em(adGroup d: Long, adAccount d: Long) extends Sl ce em
case class Campa gn em(campa gn d: Long, adAccount d: Long) extends Sl ce em
case class Fund ngS ce em(fund ngS ce d: Long, adAccount d: Long) extends Sl ce em

sealed tra  CursorType
case object Prev ousCursor extends CursorType
case object NextCursor extends CursorType
@deprecated(
  "GapCursors are not supported by Product M xer Sl ce marshallers,  f   need support for t se reach out to #product-m xer")
case object GapCursor extends CursorType

// Cursor em extends Sl ce em to enable support for GapCursors
case class Cursor em(value: Str ng, cursorType: CursorType) extends Sl ce em

case class Sl ce nfo(
  prev ousCursor: Opt on[Str ng],
  nextCursor: Opt on[Str ng])

case class Sl ce(
   ems: Seq[Sl ce em],
  sl ce nfo: Sl ce nfo)
    extends HasMarshall ng

sealed tra  Typea adResultContextType
case object   extends Typea adResultContextType
case object Locat on extends Typea adResultContextType
case object NumFollo rs extends Typea adResultContextType
case object FollowRelat onsh p extends Typea adResultContextType
case object B o extends Typea adResultContextType
case object NumT ets extends Typea adResultContextType
case object Trend ng extends Typea adResultContextType
case object H ghl ghtedLabel extends Typea adResultContextType

case class Typea adResultContext(
  contextType: Typea adResultContextType,
  d splayStr ng: Str ng,
   conUrl: Opt on[Str ng])

case class Typea ad tadata(
  score: Double,
  s ce: Opt on[Str ng],
  context: Opt on[Typea adResultContext])

// Used to render badges  n Typea ad, such as Bus ness-aff l ated badges
case class UserBadge(badgeType: Str ng, badgeUrl: Str ng, descr pt on: Str ng)
