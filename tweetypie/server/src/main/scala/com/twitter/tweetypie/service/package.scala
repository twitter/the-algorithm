package com.tw ter.t etyp e

 mport com.tw ter.servo.request
 mport com.tw ter.servo.request.Cl entRequestAuthor zer

package object serv ce {
  type Cl entRequestAuthor zer = request.Cl entRequestAuthor zer

  type Unauthor zedExcept on = request.Cl entRequestAuthor zer.Unauthor zedExcept on
  val Unauthor zedExcept on: Cl entRequestAuthor zer.Unauthor zedExcept on.type =
    request.Cl entRequestAuthor zer.Unauthor zedExcept on
}
