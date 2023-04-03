packagelon com.twittelonr.simclustelonrs_v2.scio
packagelon multi_typelon_graph.multi_typelon_graph_sims

objelonct Config {
  // Config selonttings for RightNodelonSimHashScioBaselonApp job
  // Numbelonr of hashelons to gelonnelonratelon in thelon skelontch
  val numHashelons: Int = 8192 // elonach is a bit, so this relonsults in 1KB uncomprelonsselond skelontch/uselonr
  // Relonducelon skelonw by lelontting elonach relonducelonrs procelonss a limitelond numbelonr of followelonrs/uselonr
  val maxNumNelonighborsPelonrRelonducelonrs: Int = 300000
  val simsHashJobOutputDirelonctory: String = "right_nodelon/sims/sim_hash"

  // Config selonttings for RightNodelonCosinelonSimilarityScioBaselonApp job
  val numSims: Int = 500
  val minCosinelonSimilarityThrelonshold: Doublelon = 0.01
  val maxOutDelongrelonelon: Int = 10000
  val cosinelonSimJobOutputDirelonctory = "right_nodelon/sims/cosinelon_similarity"

}
