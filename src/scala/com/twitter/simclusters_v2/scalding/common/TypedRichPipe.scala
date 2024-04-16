package com.tw ter.s mclusters_v2.scald ng.common

 mport com.tw ter.algeb rd._
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.{Execut on, Stat, Un que D}

/**
 * A r c r vers on of TypedP pe.
 */
class TypedR chP pe[V](p pe: TypedP pe[V]) {

  def count(counterNa : Str ng)( mpl c  un que D: Un que D): TypedP pe[V] = {
    val stat = Stat(counterNa )
    p pe.map { v =>
      stat. nc()
      v
    }
  }

  /**
   * Pr nt a summary of t  TypedP pe w h total s ze and so  randomly selected records
   */
  def getSummary(numRecords:  nt = 100): Execut on[Opt on[(Long, Str ng)]] = {
    val randomSample = Aggregator.reservo rSample[V](numRecords)

    // more aggregator can be added  re
    p pe
      .aggregate(randomSample.jo n(Aggregator.s ze))
      .map {
        case (randomSamples, s ze) =>
          val samplesStr = randomSamples
            .map { sample =>
              Ut l.prettyJsonMapper
                .wr eValueAsStr ng(sample)
                .replaceAll("\n", " ")
            }
            .mkStr ng("\n\t")

          (s ze, samplesStr)
      }
      .toOpt onExecut on
  }

  def getSummaryStr ng(na : Str ng, numRecords:  nt = 100): Execut on[Str ng] = {
    getSummary(numRecords)
      .map {
        case So ((s ze, str ng)) =>
          s"TypedP peNa : $na  \nTotal s ze: $s ze. \nSample records: \n$str ng"
        case None => s"TypedP peNa : $na   s empty"
      }

  }

  /**
   * Pr nt a summary of t  TypedP pe w h total s ze and so  randomly selected records
   */
  def pr ntSummary(na : Str ng, numRecords:  nt = 100): Execut on[Un ] = {
    getSummaryStr ng(na , numRecords).map { s => pr ntln(s) }
  }
}

object TypedR chP pe extends java. o.Ser al zable {
   mport scala.language. mpl c Convers ons

   mpl c  def typedP peToR chP pe[V](
    p pe: TypedP pe[V]
  )(
     mpl c  un que D: Un que D
  ): TypedR chP pe[V] = {
    new TypedR chP pe(p pe)
  }
}
