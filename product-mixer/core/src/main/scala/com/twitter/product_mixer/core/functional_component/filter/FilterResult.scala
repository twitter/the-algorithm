package com.twitter.product_mixer.core.functional_component.filter

/** `Candidate`s were `kept` and `removed` by a [[Filter]] */
case class FilterResult[+Candidate](kept: Seq[Candidate], removed: Seq[Candidate])
