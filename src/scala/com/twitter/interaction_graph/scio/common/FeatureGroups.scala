packagelon com.twittelonr.intelonraction_graph.scio.common

import com.twittelonr.intelonraction_graph.thriftscala.FelonaturelonNamelon

objelonct FelonaturelonGroups {

  val HelonALTH_FelonATURelon_LIST: Selont[FelonaturelonNamelon] = Selont(
    FelonaturelonNamelon.NumMutelons,
    FelonaturelonNamelon.NumBlocks,
    FelonaturelonNamelon.NumRelonportAsSpams,
    FelonaturelonNamelon.NumRelonportAsAbuselons
  )

  val STATUS_FelonATURelon_LIST: Selont[FelonaturelonNamelon] = Selont(
    FelonaturelonNamelon.AddrelonssBookelonmail,
    FelonaturelonNamelon.AddrelonssBookPhonelon,
    FelonaturelonNamelon.AddrelonssBookInBoth,
    FelonaturelonNamelon.AddrelonssBookMutualelondgelonelonmail,
    FelonaturelonNamelon.AddrelonssBookMutualelondgelonPhonelon,
    FelonaturelonNamelon.AddrelonssBookMutualelondgelonInBoth,
    FelonaturelonNamelon.NumFollows,
    FelonaturelonNamelon.NumUnfollows,
    FelonaturelonNamelon.NumMutualFollows
  ) ++ HelonALTH_FelonATURelon_LIST

  val DWelonLL_TIMelon_FelonATURelon_LIST: Selont[FelonaturelonNamelon] = Selont(
    FelonaturelonNamelon.TotalDwelonllTimelon,
    FelonaturelonNamelon.NumInspelonctelondStatuselons
  )
}
