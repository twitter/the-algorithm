package com.tw ter.t  l neranker.ut l

 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.servo.repos ory.Repos ory
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport scala.ut l.Random

//  nject an art f c al delay  nto an underly ng repos ory's response to match t  prov ded p50
// and max latenc es.
class LatentRepos ory[Q, R](
  underly ng: Repos ory[Q, R],
  p50: Durat on,
  max: Durat on,
  random: Random = new Random,
  t  r: T  r = DefaultT  r)
    extends Repos ory[Q, R] {
   mport scala.math.ce l
   mport scala.math.pow

  val p50M ll s: Long = p50. nM ll seconds
  val maxM ll s: Long = max. nM ll seconds
  requ re(p50M ll s > 0 && maxM ll s > 0 && maxM ll s > p50M ll s)

  overr de def apply(query: Q): Future[R] = {
    val x = random.nextDouble()
    val sleepT   = ce l(pow(p50M ll s, 2 * (1 - x)) / pow(maxM ll s, 1 - 2 * x)).to nt
    Future.sleep(Durat on.fromM ll seconds(sleepT  ))(t  r).flatMap { _ => underly ng(query) }
  }
}
