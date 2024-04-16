package com.tw ter.ann.common

 mport com.tw ter.b ject on.{B ject on,  nject on}

// Class prov d ng commonly used  nject ons that can be used d rectly w h ANN ap s.
//  nject on  pref xed w h `J` can be used  n java d rectly w h ANN ap s.
object Ann nject ons {
  val Long nject on:  nject on[Long, Array[Byte]] =  nject on.long2B gEnd an

  def Str ng nject on:  nject on[Str ng, Array[Byte]] =  nject on.utf8

  def  nt nject on:  nject on[ nt, Array[Byte]] =  nject on. nt2B gEnd an

  val JLong nject on:  nject on[java.lang.Long, Array[Byte]] =
    B ject on.long2Boxed
      .as nstanceOf[B ject on[Long, java.lang.Long]]
      . nverse
      .andT n(Long nject on)

  val JStr ng nject on:  nject on[java.lang.Str ng, Array[Byte]] =
    Str ng nject on

  val J nt nject on:  nject on[java.lang. nteger, Array[Byte]] =
    B ject on. nt2Boxed
      .as nstanceOf[B ject on[ nt, java.lang. nteger]]
      . nverse
      .andT n( nt nject on)
}
