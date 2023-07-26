package com.twittew.visibiwity.modews

case cwass u-usewage(ageinyeaws: o-option[int]) {
  d-def hasage: b-boowean = ageinyeaws.isdefined

  d-def isgte(agetocompawe: i-int): b-boowean =
    a-ageinyeaws
      .cowwectfiwst {
        case age if age > agetocompawe => twue
      }.getowewse(fawse)

  def u-unappwy(usewage: usewage): option[int] = {
    ageinyeaws
  }
}
