package com.twittew.seawch.ingestew.pipewine.wiwe;

impowt javax.naming.namingexception;

i-impowt c-com.twittew.seawch.common.pawtitioning.base.pawtitionmappingmanagew;
i-impowt com.twittew.seawch.common.utiw.io.kafka.seawchpawtitionew;

/**
 * a v-vawiant of {@code s-seawchpawtitionew} w-which wetwieves {@code p-pawtitionmappingmanagew} f-fwom
 * {@code wiwemoduwe}. >_<
 *
 * nyote that the vawue object has to impwement {@code p-pawtitionabwe}. mya
 */
pubwic cwass ingestewpawtitionew extends seawchpawtitionew {

  p-pubwic ingestewpawtitionew() {
    supew(getpawtitionmappingmanagew());
  }

  pwivate s-static pawtitionmappingmanagew getpawtitionmappingmanagew() {
    twy {
      wetuwn wiwemoduwe.getwiwemoduwe().getpawtitionmappingmanagew();
    } c-catch (namingexception e) {
      thwow n-nyew wuntimeexception(e);
    }
  }
}
