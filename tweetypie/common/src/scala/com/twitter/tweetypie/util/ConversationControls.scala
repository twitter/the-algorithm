package com.tw ter.t etyp e
package ut l

 mport com.tw ter.t etyp e.thr ftscala._

object Conversat onControls {
  object Create {
    def by nv at on(
       nv eV a nt on: Opt on[Boolean] = None
    ): T etCreateConversat onControl.By nv at on = T etCreateConversat onControl.By nv at on(
      T etCreateConversat onControlBy nv at on( nv eV a nt on =  nv eV a nt on)
    )

    def commun y(
       nv eV a nt on: Opt on[Boolean] = None
    ): T etCreateConversat onControl.Commun y = T etCreateConversat onControl.Commun y(
      T etCreateConversat onControlCommun y( nv eV a nt on =  nv eV a nt on)
    )

    def follo rs(
       nv eV a nt on: Opt on[Boolean] = None
    ): T etCreateConversat onControl.Follo rs = T etCreateConversat onControl.Follo rs(
      T etCreateConversat onControlFollo rs( nv eV a nt on =  nv eV a nt on)
    )
  }

  object Scenar o {
    case class CommonScenar o(
      createConversat onControl: T etCreateConversat onControl,
      descr pt onSuff x: Str ng,
      expectedConversat onControl: (User d, Seq[User d]) => Conversat onControl,
       nv eV a nt on: Opt on[Boolean])

    def mkCommun yScenar o( nv eV a nt on: Opt on[Boolean]): CommonScenar o =
      CommonScenar o(
        Create.commun y( nv eV a nt on =  nv eV a nt on),
        "commun y",
        expectedConversat onControl = (author d, user ds) => {
          commun y(user ds, author d,  nv eV a nt on)
        },
         nv eV a nt on
      )

    def mkBy nv at onScenar o( nv eV a nt on: Opt on[Boolean]): CommonScenar o =
      CommonScenar o(
        Create.by nv at on( nv eV a nt on =  nv eV a nt on),
        " nv ed users",
        expectedConversat onControl = (author d, user ds) => {
          by nv at on(user ds, author d,  nv eV a nt on)
        },
         nv eV a nt on
      )

    def mkFollo rsScenar o( nv eV a nt on: Opt on[Boolean]): CommonScenar o =
      CommonScenar o(
        Create.follo rs( nv eV a nt on =  nv eV a nt on),
        "follo rs",
        expectedConversat onControl = (author d, user ds) => {
          follo rs(user ds, author d,  nv eV a nt on)
        },
         nv eV a nt on
      )

    val commun yScenar o = mkCommun yScenar o(None)
    val commun y nv eV a nt onScenar o = mkCommun yScenar o(So (true))

    val by nv at onScenar o = mkBy nv at onScenar o(None)
    val by nv at on nv eV a nt onScenar o = mkBy nv at onScenar o(So (true))

    val follo rsScenar o = mkFollo rsScenar o(None)
    val follo rs nv eV a nt onScenar o = mkFollo rsScenar o(So (true))
  }

  def by nv at on(
     nv edUser ds: Seq[User d],
    conversat onT etAuthor d: User d,
     nv eV a nt on: Opt on[Boolean] = None
  ): Conversat onControl =
    Conversat onControl.By nv at on(
      Conversat onControlBy nv at on(
        conversat onT etAuthor d = conversat onT etAuthor d,
         nv edUser ds =  nv edUser ds,
         nv eV a nt on =  nv eV a nt on
      )
    )

  def commun y(
     nv edUser ds: Seq[User d],
    conversat onT etAuthor d: User d,
     nv eV a nt on: Opt on[Boolean] = None
  ): Conversat onControl =
    Conversat onControl.Commun y(
      Conversat onControlCommun y(
        conversat onT etAuthor d = conversat onT etAuthor d,
         nv edUser ds =  nv edUser ds,
         nv eV a nt on =  nv eV a nt on
      )
    )

  def follo rs(
     nv edUser ds: Seq[User d],
    conversat onT etAuthor d: User d,
     nv eV a nt on: Opt on[Boolean] = None
  ): Conversat onControl =
    Conversat onControl.Follo rs(
      Conversat onControlFollo rs(
        conversat onT etAuthor d = conversat onT etAuthor d,
         nv edUser ds =  nv edUser ds,
         nv eV a nt on =  nv eV a nt on
      )
    )
}
