package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.tco_ut l.D splayUrl
 mport com.tw ter.t etut l.T etPermal nk
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * T  populates expanded URL and d splay text  n ShortenedUrl struct,
 * wh ch  s part of QuotedT et  tadata.   are us ng User  dent y repo
 * to retr eve user's current screen-na  to construct expanded url,  nstead
 * of rely ng on URL hydrat on.
 *
 * Expanded urls conta n a mutable screen na  and an  mmutable t et d.
 * w n v s  ng t  l nk,   always red rected to t  l nk w h
 * correct screen na  - t refore,  's okay to have permal nks conta n ng
 * old screen na s that have s nce been changed by t  r user  n t  cac .
 * Keys w ll be auto-refres d based on t  14 days TTL,   can also have
 * a daemon flush t  keys w h screen-na  change.
 *
 */
object QuotedT etRefUrlsHydrator {
  type Type = ValueHydrator[Opt on[QuotedT et], T etCtx]

  /**
   * Return true  f longUrl  s not set or  f a pr or hydrat on set   to shortUrl due to
   * a part al (to re-attempt hydrat on).
   */
  def needsHydrat on(s: ShortenedUrl): Boolean =
    s.longUrl. sEmpty || s.d splayText. sEmpty || s.longUrl == s.shortUrl

  def apply(repo: User dent yRepos ory.Type): Type = {
    ValueHydrator[QuotedT et, T etCtx] { (curr, _) =>
      repo(UserKey(curr.user d)).l ftToTry.map { r =>
        //   ver fy curr.permal nk.ex sts pre-hydrat on
        val shortUrl = curr.permal nk.get.shortUrl
        val expandedUrl = r match {
          case Return(user) => T etPermal nk(user.screenNa , curr.t et d).httpsUrl
          case Throw(_) => shortUrl // fall-back to shortUrl as expandedUrl
        }
        ValueState.delta(
          curr,
          curr.copy(
            permal nk = So (
              ShortenedUrl(
                shortUrl,
                expandedUrl,
                D splayUrl.truncateUrl(expandedUrl, true)
              )
            )
          )
        )
      }
    }
  }.only f { (curr, ctx) =>
    curr.permal nk.ex sts(needsHydrat on) &&
    ctx.t etF eldRequested(T et.QuotedT etF eld) && !ctx. sRet et
  }.l ftOpt on
}
