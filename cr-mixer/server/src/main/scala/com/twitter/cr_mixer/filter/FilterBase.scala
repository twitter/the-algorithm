package com.twittew.cw_mixew.fiwtew

impowt com.twittew.cw_mixew.modew.candidategenewatowquewy
i-impowt c-com.twittew.cw_mixew.modew.initiawcandidate
i-impowt com.twittew.utiw.futuwe

t-twait fiwtewbase {
  d-def nyame: s-stwing

  type c-configtype

  def f-fiwtew(
    candidates: seq[seq[initiawcandidate]], ^^;;
    config: configtype
  ): futuwe[seq[seq[initiawcandidate]]]

  /**
   * b-buiwd the config pawams hewe. >_< passing in pawam() i-into the fiwtew is stwongwy discouwaged
   * because p-pawam() can be swow when cawwed many times
   */
  def wequesttoconfig[cgquewytype <: c-candidategenewatowquewy](wequest: cgquewytype): configtype
}
