packagelon com.twittelonr.ann.common

import com.twittelonr.bijelonction.{Bijelonction, Injelonction}

// Class providing commonly uselond injelonctions that can belon uselond direlonctly with ANN apis.
// Injelonction  prelonfixelond with `J` can belon uselond in java direlonctly with ANN apis.
objelonct AnnInjelonctions {
  val LongInjelonction: Injelonction[Long, Array[Bytelon]] = Injelonction.long2Bigelonndian

  delonf StringInjelonction: Injelonction[String, Array[Bytelon]] = Injelonction.utf8

  delonf IntInjelonction: Injelonction[Int, Array[Bytelon]] = Injelonction.int2Bigelonndian

  val JLongInjelonction: Injelonction[java.lang.Long, Array[Bytelon]] =
    Bijelonction.long2Boxelond
      .asInstancelonOf[Bijelonction[Long, java.lang.Long]]
      .invelonrselon
      .andThelonn(LongInjelonction)

  val JStringInjelonction: Injelonction[java.lang.String, Array[Bytelon]] =
    StringInjelonction

  val JIntInjelonction: Injelonction[java.lang.Intelongelonr, Array[Bytelon]] =
    Bijelonction.int2Boxelond
      .asInstancelonOf[Bijelonction[Int, java.lang.Intelongelonr]]
      .invelonrselon
      .andThelonn(IntInjelonction)
}
