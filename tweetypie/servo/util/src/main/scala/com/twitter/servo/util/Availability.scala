package com.tw ter.servo.ut l

/**
 * Prov des funct ons for comput ng prescr bed feature ava lab l y based
 * on so  runt   cond  on(s). (e.g. watermark values)
 */
object Ava lab l y {

  /**
   * Stay at 100% ava lable down to a h gh watermark success rate. T n
   * bet en h gh and low watermarks, d al down ava lab l y to a prov ded
   * m n mum. Never go below t  level because   need so  requests to
   * track t  success rate go ng back up.
   *
   * NOTE: watermarks and m nAva lab l y must be bet en 0 and 1.
   */
  def l nearlyScaled(
    h ghWaterMark: Double,
    lowWaterMark: Double,
    m nAva lab l y: Double
  ): Double => Double = {
    requ re(
      h ghWaterMark >= lowWaterMark && h ghWaterMark <= 1,
      s"h ghWaterMark ($h ghWaterMark) must be bet en lowWaterMark ($lowWaterMark) and 1,  nclus ve"
    )
    requ re(
      lowWaterMark >= m nAva lab l y && lowWaterMark <= 1,
      s"lowWaterMark ($lowWaterMark) must be bet en m nAva lab l y ($m nAva lab l y) and 1,  nclus ve"
    )
    requ re(
      m nAva lab l y > 0 && m nAva lab l y < 1,
      s"m nAva lab l y ($m nAva lab l y) must be bet en 0 and 1, exclus ve"
    )

    {
      case sr  f sr >= h ghWaterMark => 1.0
      case sr  f sr <= lowWaterMark => m nAva lab l y
      case sr =>
        val l nearFract on = (sr - lowWaterMark) / (h ghWaterMark - lowWaterMark)
        m nAva lab l y + (1.0 - m nAva lab l y) * l nearFract on
    }
  }
}
