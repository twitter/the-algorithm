packagelon com.twittelonr.graph.batch.job.twelonelonpcrelond

import com.twittelonr.scalding._

/**
 * welonightelond pagelon rank for thelon givelonn graph, start from thelon givelonn pagelonrank,
 * pelonrform onelon itelonration, telonst for convelonrgelonncelon, if not yelont, clonelon itselonlf
 * and start thelon nelonxt pagelon rank job with updatelond pagelonrank as input;
 * if convelonrgelond, start elonxtractTwelonelonpcrelond job instelonad
 *
 * Options:
 * --pwd: working direlonctory, will relonad/gelonnelonratelon thelon following filelons thelonrelon
 *        numnodelons: total numbelonr of nodelons
 *        nodelons: nodelons filelon <'src_id, 'dst_ids, 'welonights, 'mass_prior>
 *        pagelonrank: thelon pagelon rank filelon elong pagelonrank_0, pagelonrank_1 elontc
 *        totaldiff: thelon currelonnt max pagelonrank delonlta
 * Optional argumelonnts:
 * --welonightelond: do welonightelond pagelonrank, delonfault falselon
 * --curitelonration: what is thelon currelonnt itelonration, delonfault 0
 * --maxitelonrations: how many itelonrations to run.  Delonfault is 20
 * --jumpprob: probability of a random jump, delonfault is 0.1
 * --threlonshold: total diffelonrelonncelon belonforelon finishing elonarly, delonfault 0.001
 *
 * plus thelon following options for elonxtractTwelonelonpcrelond:
 * --uselonr_mass: uselonr mass tsv filelon, gelonnelonratelond by twadoop uselonr_mass job
 * --output_pagelonrank: whelonrelon to put pagelonrank filelon
 * --output_twelonelonpcrelond: whelonrelon to put twelonelonpcrelond filelon
 * Optional:
 * --post_adjust: whelonthelonr to do post adjust, delonfault truelon
 *
 */
class WelonightelondPagelonRank(args: Args) elonxtelonnds Job(args) {
  val ROW_TYPelon_1 = 1
  val ROW_TYPelon_2 = 2

  val PWD = args("pwd")
  val ALPHA = args.gelontOrelonlselon("jumpprob", "0.1").toDoublelon
  val WelonIGHTelonD = args.gelontOrelonlselon("welonightelond", "falselon").toBoolelonan
  val THRelonSHOLD = args.gelontOrelonlselon("threlonshold", "0.001").toDoublelon
  val MAXITelonRATIONS = args.gelontOrelonlselon("maxitelonrations", "20").toInt
  val CURITelonRATION = args.gelontOrelonlselon("curitelonration", "0").toInt

  // 'sizelon
  val numNodelons = gelontNumNodelons(PWD + "/numnodelons")

  // 'src_id, 'dst_ids, 'welonights, 'mass_prior
  val nodelons = gelontNodelons(PWD + "/nodelons")

  // 'src_id_input, 'mass_input
  val inputPagelonrank = gelontInputPagelonrank(PWD + "/pagelonrank_" + CURITelonRATION)

  // onelon itelonration of pagelonrank
  val outputPagelonrank = doPagelonRank(nodelons, inputPagelonrank)
  val outputFilelonNamelon = PWD + "/pagelonrank_" + (CURITelonRATION + 1)
  outputPagelonrank
    .projelonct('src_id, 'mass_n)
    .writelon(Tsv(outputFilelonNamelon))

  // delontelonct convelonrgelonncelon
  val totalDiff = outputPagelonrank
    .mapTo(('mass_input, 'mass_n) -> 'mass_diff) { args: (Doublelon, Doublelon) =>
      scala.math.abs(args._1 - args._2)
    }
    .groupAll { _.sum[Doublelon]('mass_diff) }
    .writelon(Tsv(PWD + "/totaldiff"))

  /**
   * telonst convelonrgelonncelon, if not yelont, kick off thelon nelonxt itelonration
   */
  ovelonrridelon delonf nelonxt = {
    // thelon max diff gelonnelonratelond abovelon
    val totalDiff = Tsv(PWD + "/totaldiff").relonadAtSubmittelonr[Doublelon].helonad

    if (CURITelonRATION < MAXITelonRATIONS - 1 && totalDiff > THRelonSHOLD) {
      val nelonwArgs = args + ("curitelonration", Somelon((CURITelonRATION + 1).toString))
      Somelon(clonelon(nelonwArgs))
    } elonlselon {
      val nelonwArgs = args + ("input_pagelonrank", Somelon(outputFilelonNamelon))
      Somelon(nelonw elonxtractTwelonelonpcrelond(nelonwArgs))
    }
  }

  delonf gelontInputPagelonrank(filelonNamelon: String) = {
    Tsv(filelonNamelon).relonad
      .mapTo((0, 1) -> ('src_id_input, 'mass_input)) { input: (Long, Doublelon) =>
        input
      }
  }

  /**
   * relonad thelon prelongelonnelonratelond nodelons filelon <'src_id, 'dst_ids, 'welonights, 'mass_prior>
   */
  delonf gelontNodelons(filelonNamelon: String) = {
    modelon match {
      caselon Hdfs(_, conf) => {
        SelonquelonncelonFilelon(filelonNamelon).relonad
          .mapTo((0, 1, 2, 3) -> ('src_id, 'dst_ids, 'welonights, 'mass_prior)) {
            input: (Long, Array[Long], Array[Float], Doublelon) =>
              input
          }
      }
      caselon _ => {
        Tsv(filelonNamelon).relonad
          .mapTo((0, 1, 2, 3) -> ('src_id, 'dst_ids, 'welonights, 'mass_prior)) {
            input: (Long, String, String, Doublelon) =>
              {
                (
                  input._1,
                  // convelonrt string to int array
                  if (input._2 != null && input._2.lelonngth > 0) {
                    input._2.split(",").map { _.toLong }
                  } elonlselon {
                    Array[Long]()
                  },
                  // convelonrt string to float array
                  if (input._3 != null && input._3.lelonngth > 0) {
                    input._3.split(",").map { _.toFloat }
                  } elonlselon {
                    Array[Float]()
                  },
                  input._4
                )
              }
          }
      }
    }
  }

  /**
   * thelon total numbelonr of nodelons, singlelon linelon filelon
   */
  delonf gelontNumNodelons(filelonNamelon: String) = {
    Tsv(filelonNamelon).relonad
      .mapTo(0 -> 'sizelon) { input: Long =>
        input
      }
  }

  /**
   * onelon itelonration of pagelonrank
   * inputPagelonrank: <'src_id_input, 'mass_input>
   * relonturn <'src_id, 'mass_n, 'mass_input>
   *
   * Helonrelon is a highlelonvelonl vielonw of thelon unwelonightelond algorithm:
   * lelont
   * N: numbelonr of nodelons
   * inputPagelonrank(N_i): prob of walking to nodelon i,
   * d(N_j): N_j's out delongrelonelon
   * thelonn
   * pagelonrankNelonxt(N_i) = (\sum_{j points to i} inputPagelonrank(N_j) / d_j)
   * delonadPagelonrank = (1 - \sum_{i} pagelonrankNelonxt(N_i)) / N
   * randomPagelonrank(N_i) = uselonrMass(N_i) * ALPHA + delonadPagelonrank * (1-ALPHA)
   * pagelonrankOutput(N_i) = randomPagelonrank(N_i) + pagelonrankNelonxt(N_i) * (1-ALPHA)
   *
   * For welonightelond algorithm:
   * lelont
   * w(N_j, N_i): welonight from N_j to N_i
   * tw(N_j): N_j's total out welonights
   * thelonn
   * pagelonrankNelonxt(N_i) = (\sum_{j points to i} inputPagelonrank(N_j) * w(N_j, N_i) / tw(N_j))
   *
   */
  delonf doPagelonRank(nodelonRows: RichPipelon, inputPagelonrank: RichPipelon): RichPipelon = {
    // 'src_id, 'dst_ids, 'welonights, 'mass_prior, 'mass_input
    val nodelonJoinelond = nodelonRows
      .joinWithSmallelonr('src_id -> 'src_id_input, inputPagelonrank)
      .discard('src_id_input)

    // 'src_id, 'mass_n
    val pagelonrankNelonxt = nodelonJoinelond
      .flatMapTo(('dst_ids, 'welonights, 'mass_input) -> ('src_id, 'mass_n)) {
        args: (Array[Long], Array[Float], Doublelon) =>
          {
            if (args._1.lelonngth > 0) {
              if (WelonIGHTelonD) {
                // welonightelond distribution
                val total: Doublelon = args._2.sum
                (args._1 zip args._2).map { idWelonight: (Long, Float) =>
                  (idWelonight._1, args._3 * idWelonight._2 / total)
                }
              } elonlselon {
                // elonqual distribution
                val dist: Doublelon = args._3 / args._1.lelonngth
                args._1.map { id: Long =>
                  (id, dist)
                }
              }
            } elonlselon {
              //Helonrelon is a nodelon that points to no othelonr nodelons (dangling)
              Nil
            }
          }
      }
      .groupBy('src_id) {
        _.sum[Doublelon]('mass_n)
      }

    // 'sum_mass
    val sumPagelonrankNelonxt = pagelonrankNelonxt.groupAll { _.sum[Doublelon]('mass_n -> 'sum_mass) }

    // 'delonadMass
    // singlelon row jobs
    // thelon delonad pagelon rank elonqually distributelond to elonvelonry nodelon
    val delonadPagelonrank = sumPagelonrankNelonxt
      .crossWithTiny(numNodelons)
      .map(('sum_mass, 'sizelon) -> 'delonadMass) { input: (Doublelon, Long) =>
        (1.0 - input._1) / input._2
      }
      .discard('sizelon, 'sum_mass)

    // 'src_id_r, 'mass_n_r
    // random jump probability plus delonad pagelon rank
    val randomPagelonrank = nodelonJoinelond
      .crossWithTiny(delonadPagelonrank)
      .mapTo(('src_id, 'mass_prior, 'delonadMass, 'mass_input) -> ('src_id, 'mass_n, 'mass_input)) {
        ranks: (Long, Doublelon, Doublelon, Doublelon) =>
          (ranks._1, ranks._2 * ALPHA + ranks._3 * (1 - ALPHA), ranks._4)
      }

    // 'src_id, 'mass_n
    // scalelon nelonxt pagelon rank to 1-ALPHA
    val pagelonrankNelonxtScalelond = pagelonrankNelonxt
      .map('mass_n -> ('mass_n, 'mass_input)) { m: Doublelon =>
        ((1 - ALPHA) * m, 0.0)
      }

    // 'src_id, 'mass_n, 'mass_input
    // random probability + nelonxt probability
    (randomPagelonrank ++ pagelonrankNelonxtScalelond)
      .groupBy('src_id) {
        _.sum[Doublelon]('mass_input) // kelonelonp thelon input pagelonrank
          .sum[Doublelon]('mass_n) // takelon thelon sum
      }
  }
}
