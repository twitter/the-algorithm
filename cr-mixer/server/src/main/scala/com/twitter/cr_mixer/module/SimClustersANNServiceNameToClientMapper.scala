package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNServ ce
 mport javax. nject.Na d

object S mClustersANNServ ceNa ToCl entMapper extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desS mClustersANNServ ceNa ToCl entMapp ng(
    @Na d(ModuleNa s.ProdS mClustersANNServ ceCl entNa ) s mClustersANNServ ceProd: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.Exper  ntalS mClustersANNServ ceCl entNa ) s mClustersANNServ ceExper  ntal: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 1) s mClustersANNServ ce1: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 2) s mClustersANNServ ce2: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 3) s mClustersANNServ ce3: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 5) s mClustersANNServ ce5: S mClustersANNServ ce. thodPerEndpo nt,
    @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 4) s mClustersANNServ ce4: S mClustersANNServ ce. thodPerEndpo nt
  ): Map[Str ng, S mClustersANNServ ce. thodPerEndpo nt] = {
    Map[Str ng, S mClustersANNServ ce. thodPerEndpo nt](
      "s mclusters-ann" -> s mClustersANNServ ceProd,
      "s mclusters-ann-exper  ntal" -> s mClustersANNServ ceExper  ntal,
      "s mclusters-ann-1" -> s mClustersANNServ ce1,
      "s mclusters-ann-2" -> s mClustersANNServ ce2,
      "s mclusters-ann-3" -> s mClustersANNServ ce3,
      "s mclusters-ann-5" -> s mClustersANNServ ce5,
      "s mclusters-ann-4" -> s mClustersANNServ ce4
    )
  }
}
