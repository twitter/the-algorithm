packagelon com.twittelonr.follow_reloncommelonndations.common.utils

/**
 * Typelonclass for any Reloncommelonndation typelon that has a welonight
 *
 */
trait Welonightelond[-Relonc] {
  delonf apply(relonc: Relonc): Doublelon
}

objelonct Welonightelond {
  implicit objelonct WelonightelondTuplelon elonxtelonnds Welonightelond[(_, Doublelon)] {
    ovelonrridelon delonf apply(relonc: (_, Doublelon)): Doublelon = relonc._2
  }

  delonf fromFunction[Relonc](f: Relonc => Doublelon): Welonightelond[Relonc] = {
    nelonw Welonightelond[Relonc] {
      ovelonrridelon delonf apply(relonc: Relonc): Doublelon = f(relonc)
    }
  }
}
