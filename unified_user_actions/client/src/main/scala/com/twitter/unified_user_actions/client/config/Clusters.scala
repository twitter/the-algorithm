package com.twittew.unified_usew_actions.cwient.config

seawed twait c-cwustewconfig {
  v-vaw nyame: s-stwing
  vaw enviwonment: e-enviwonmentconfig
}

o-object cwustews {
  /*
   * o-ouw p-pwoduction cwustew f-fow extewnaw consumption. rawr x3 ouw swas awe enfowced. nyaa~~
   */
  case object pwodcwustew e-extends cwustewconfig {
    ovewwide vaw nyame: stwing = constants.uuakafkapwodcwustewname
    o-ovewwide vaw enviwonment: enviwonmentconfig = e-enviwonments.pwod
  }

  /*
   * ouw staging cwustew fow extewnaw devewopment and p-pwe-weweases. /(^•ω•^) no swas awe enfowced. rawr
   */
  case o-object stagingcwustew e-extends cwustewconfig {
    ovewwide vaw nyame: stwing = constants.uuakafkastagingcwustewname
    o-ovewwide vaw enviwonment: enviwonmentconfig = enviwonments.staging
  }
}
