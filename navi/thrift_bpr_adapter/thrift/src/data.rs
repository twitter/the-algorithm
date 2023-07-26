// autogenewated by thwift compiwew (0.17.0)
// do n-nyot edit unwess y-you awe suwe t-that you know nyani y-you awe doing

#![awwow(unused_impowts)]
#![awwow(unused_extewn_cwates)]
#![awwow(cwippy::too_many_awguments, ^^;; c-cwippy::type_compwexity, òωó c-cwippy::vec_box)]
#![cfg_attw(wustfmt, -.- w-wustfmt_skip)]

u-use std::ceww::wefceww;
use std::cowwections::{btweemap, ( ͡o ω ͡o ) btweeset};
use std::convewt::{fwom, o.O twyfwom};
use std::defauwt::defauwt;
u-use std::ewwow::ewwow;
use std::fmt;
use std::fmt::{dispway, rawr f-fowmattew};
use std::wc::wc;

use t-thwift::owdewedfwoat;
use thwift::{appwicationewwow, (✿oωo) appwicationewwowkind, σωσ pwotocowewwow, (U ᵕ U❁) p-pwotocowewwowkind, >_< tthwiftcwient};
u-use thwift::pwotocow::{tfiewdidentifiew, ^^ t-twistidentifiew, rawr tmapidentifiew, >_< tmessageidentifiew, (⑅˘꒳˘) tmessagetype, >w< tinputpwotocow, (///ˬ///✿) t-toutputpwotocow, ^•ﻌ•^ tsewiawizabwe, (✿oωo) tsetidentifiew, ʘwʘ tstwuctidentifiew, >w< ttype};
use thwift::pwotocow::fiewd_id;
u-use thwift::pwotocow::vewify_expected_message_type;
use thwift::pwotocow::vewify_expected_sequence_numbew;
u-use thwift::pwotocow::vewify_expected_sewvice_caww;
u-use thwift::pwotocow::vewify_wequiwed_fiewd_exists;
u-use thwift::sewvew::tpwocessow;

u-use cwate::tensow;

#[dewive(copy, :3 cwone, debug, (ˆ ﻌ ˆ)♡ eq, h-hash, owd, -.- pawtiaweq, pawtiawowd)]
pub stwuct featuwetype(pub i-i32);

impw featuwetype {
  pub const binawy: featuwetype = featuwetype(1);
  pub c-const continuous: featuwetype = f-featuwetype(2);
  p-pub const discwete: f-featuwetype = featuwetype(3);
  pub const stwing: featuwetype = f-featuwetype(4);
  p-pub const spawse_binawy: f-featuwetype = featuwetype(5);
  p-pub const spawse_continuous: featuwetype = f-featuwetype(6);
  pub c-const unknown: featuwetype = featuwetype(7);
  pub const bwob: f-featuwetype = featuwetype(8);
  pub const tensow: f-featuwetype = featuwetype(9);
  p-pub const spawse_tensow: f-featuwetype = featuwetype(10);
  pub const featuwe_type11: featuwetype = featuwetype(11);
  pub const f-featuwe_type12: f-featuwetype = featuwetype(12);
  p-pub const enum_vawues: &'static [sewf] = &[
    s-sewf::binawy, rawr
    s-sewf::continuous, rawr x3
    sewf::discwete, (U ﹏ U)
    sewf::stwing, (ˆ ﻌ ˆ)♡
    sewf::spawse_binawy, :3
    sewf::spawse_continuous, òωó
    s-sewf::unknown, /(^•ω•^)
    sewf::bwob, >w<
    sewf::tensow, nyaa~~
    sewf::spawse_tensow, mya
    sewf::featuwe_type11, mya
    sewf::featuwe_type12, ʘwʘ
  ];
}

i-impw tsewiawizabwe f-fow featuwetype {
  #[awwow(cwippy::twiviawwy_copy_pass_by_wef)]
  f-fn wwite_to_out_pwotocow(&sewf, rawr o-o_pwot: &mut dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    o-o_pwot.wwite_i32(sewf.0)
  }
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<featuwetype> {
    wet enum_vawue = i-i_pwot.wead_i32()?;
    o-ok(featuwetype::fwom(enum_vawue))
  }
}

i-impw fwom<i32> f-fow featuwetype {
  f-fn fwom(i: i32) -> sewf {
    match i {
      1 => featuwetype::binawy, (˘ω˘)
      2 => f-featuwetype::continuous,
      3 => featuwetype::discwete, /(^•ω•^)
      4 => featuwetype::stwing, (˘ω˘)
      5 => featuwetype::spawse_binawy, (///ˬ///✿)
      6 => featuwetype::spawse_continuous, (˘ω˘)
      7 => featuwetype::unknown, -.-
      8 => featuwetype::bwob, -.-
      9 => f-featuwetype::tensow, ^^
      10 => featuwetype::spawse_tensow, (ˆ ﻌ ˆ)♡
      11 => featuwetype::featuwe_type11, UwU
      12 => featuwetype::featuwe_type12, 🥺
      _ => featuwetype(i)
    }
  }
}

i-impw fwom<&i32> f-fow featuwetype {
  f-fn fwom(i: &i32) -> sewf {
    f-featuwetype::fwom(*i)
  }
}

impw fwom<featuwetype> f-fow i32 {
  f-fn fwom(e: featuwetype) -> i32 {
    e.0
  }
}

impw fwom<&featuwetype> fow i32 {
  fn fwom(e: &featuwetype) -> i32 {
    e-e.0
  }
}

//
// datawecowd
//

#[dewive(cwone, 🥺 d-debug, eq, hash, 🥺 owd, pawtiaweq, 🥺 p-pawtiawowd)]
pub s-stwuct datawecowd {
  pub binawy_featuwes: option<btweeset<i64>>, :3
  p-pub continuous_featuwes: o-option<btweemap<i64, (˘ω˘) owdewedfwoat<f64>>>, ^^;;
  p-pub d-discwete_featuwes: option<btweemap<i64, (ꈍᴗꈍ) i64>>,
  pub stwing_featuwes: option<btweemap<i64, ʘwʘ s-stwing>>, :3
  p-pub spawse_binawy_featuwes: o-option<btweemap<i64, XD btweeset<stwing>>>,
  p-pub s-spawse_continuous_featuwes: option<btweemap<i64, UwU b-btweemap<stwing, rawr x3 owdewedfwoat<f64>>>>, ( ͡o ω ͡o )
  pub bwob_featuwes: option<btweemap<i64, :3 vec<u8>>>, rawr
  p-pub tensows: option<btweemap<i64, ^•ﻌ•^ t-tensow::genewawtensow>>, 🥺
  pub spawse_tensows: o-option<btweemap<i64, (⑅˘꒳˘) t-tensow::spawsetensow>>, :3
}

impw datawecowd {
  pub fn nyew<f1, (///ˬ///✿) f2, f3, f4, 😳😳😳 f-f5, f6, f7, 😳😳😳 f8, f9>(binawy_featuwes: f1, 😳😳😳 continuous_featuwes: f2, nyaa~~ discwete_featuwes: f3, UwU stwing_featuwes: f-f4, òωó spawse_binawy_featuwes: f5, òωó spawse_continuous_featuwes: f-f6, UwU bwob_featuwes: f-f7, (///ˬ///✿) tensows: f8, ( ͡o ω ͡o ) spawse_tensows: f9) -> datawecowd whewe f-f1: into<option<btweeset<i64>>>, rawr f-f2: into<option<btweemap<i64, :3 owdewedfwoat<f64>>>>, >w< f3: into<option<btweemap<i64, σωσ i64>>>, f4: i-into<option<btweemap<i64, σωσ stwing>>>, >_< f-f5: into<option<btweemap<i64, -.- btweeset<stwing>>>>, 😳😳😳 f6: into<option<btweemap<i64, :3 btweemap<stwing, mya o-owdewedfwoat<f64>>>>>, (✿oωo) f7: into<option<btweemap<i64, 😳😳😳 vec<u8>>>>, f-f8: into<option<btweemap<i64, o.O t-tensow::genewawtensow>>>, (ꈍᴗꈍ) f9: into<option<btweemap<i64, (ˆ ﻌ ˆ)♡ t-tensow::spawsetensow>>> {
    datawecowd {
      binawy_featuwes: b-binawy_featuwes.into(), -.-
      c-continuous_featuwes: c-continuous_featuwes.into(), mya
      discwete_featuwes: d-discwete_featuwes.into(), :3
      s-stwing_featuwes: stwing_featuwes.into(), σωσ
      spawse_binawy_featuwes: s-spawse_binawy_featuwes.into(), 😳😳😳
      s-spawse_continuous_featuwes: s-spawse_continuous_featuwes.into(),
      bwob_featuwes: bwob_featuwes.into(), -.-
      t-tensows: tensows.into(), 😳😳😳
      s-spawse_tensows: s-spawse_tensows.into(), rawr x3
    }
  }
}

impw tsewiawizabwe fow datawecowd {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> t-thwift::wesuwt<datawecowd> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<btweeset<i64>> = n-nyone;
    wet mut f_2: option<btweemap<i64, (///ˬ///✿) owdewedfwoat<f64>>> = nyone;
    wet mut f_3: option<btweemap<i64, >w< i64>> = nyone;
    w-wet mut f_4: option<btweemap<i64, o.O s-stwing>> = nyone;
    wet m-mut f_5: option<btweemap<i64, (˘ω˘) btweeset<stwing>>> = n-none;
    wet mut f_6: option<btweemap<i64, rawr b-btweemap<stwing, mya o-owdewedfwoat<f64>>>> = n-nyone;
    w-wet mut f_7: o-option<btweemap<i64, òωó vec<u8>>> = nyone;
    wet mut f_8: option<btweemap<i64, nyaa~~ tensow::genewawtensow>> = nyone;
    wet mut f_9: o-option<btweemap<i64, òωó t-tensow::spawsetensow>> = n-nyone;
    woop {
      wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          wet s-set_ident = i_pwot.wead_set_begin()?;
          wet mut vaw: btweeset<i64> = btweeset::new();
          f-fow _ in 0..set_ident.size {
            w-wet set_ewem_0 = i_pwot.wead_i64()?;
            v-vaw.insewt(set_ewem_0);
          }
          i-i_pwot.wead_set_end()?;
          f_1 = some(vaw);
        },
        2 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          wet mut v-vaw: btweemap<i64, mya o-owdewedfwoat<f64>> = b-btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet m-map_key_1 = i_pwot.wead_i64()?;
            wet map_vaw_2 = owdewedfwoat::fwom(i_pwot.wead_doubwe()?);
            v-vaw.insewt(map_key_1, ^^ m-map_vaw_2);
          }
          i_pwot.wead_map_end()?;
          f-f_2 = some(vaw);
        }, ^•ﻌ•^
        3 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, -.- i64> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet map_key_3 = i_pwot.wead_i64()?;
            wet m-map_vaw_4 = i_pwot.wead_i64()?;
            vaw.insewt(map_key_3, UwU m-map_vaw_4);
          }
          i_pwot.wead_map_end()?;
          f-f_3 = some(vaw);
        }, (˘ω˘)
        4 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, UwU s-stwing> = b-btweemap::new();
          fow _ in 0..map_ident.size {
            wet map_key_5 = i_pwot.wead_i64()?;
            w-wet map_vaw_6 = i_pwot.wead_stwing()?;
            vaw.insewt(map_key_5, rawr m-map_vaw_6);
          }
          i-i_pwot.wead_map_end()?;
          f_4 = some(vaw);
        }, :3
        5 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, nyaa~~ b-btweeset<stwing>> = btweemap::new();
          fow _ i-in 0..map_ident.size {
            wet map_key_7 = i_pwot.wead_i64()?;
            w-wet set_ident = i-i_pwot.wead_set_begin()?;
            wet mut m-map_vaw_8: btweeset<stwing> = btweeset::new();
            f-fow _ i-in 0..set_ident.size {
              w-wet set_ewem_9 = i_pwot.wead_stwing()?;
              map_vaw_8.insewt(set_ewem_9);
            }
            i_pwot.wead_set_end()?;
            vaw.insewt(map_key_7, rawr map_vaw_8);
          }
          i_pwot.wead_map_end()?;
          f_5 = some(vaw);
        }, (ˆ ﻌ ˆ)♡
        6 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, (ꈍᴗꈍ) btweemap<stwing, (˘ω˘) o-owdewedfwoat<f64>>> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet map_key_10 = i-i_pwot.wead_i64()?;
            w-wet map_ident = i-i_pwot.wead_map_begin()?;
            wet mut m-map_vaw_11: btweemap<stwing, (U ﹏ U) owdewedfwoat<f64>> = btweemap::new();
            f-fow _ in 0..map_ident.size {
              w-wet map_key_12 = i_pwot.wead_stwing()?;
              w-wet map_vaw_13 = owdewedfwoat::fwom(i_pwot.wead_doubwe()?);
              m-map_vaw_11.insewt(map_key_12, >w< m-map_vaw_13);
            }
            i_pwot.wead_map_end()?;
            vaw.insewt(map_key_10, m-map_vaw_11);
          }
          i_pwot.wead_map_end()?;
          f-f_6 = some(vaw);
        }, UwU
        7 => {
          w-wet map_ident = i-i_pwot.wead_map_begin()?;
          w-wet mut v-vaw: btweemap<i64, (ˆ ﻌ ˆ)♡ v-vec<u8>> = b-btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_14 = i-i_pwot.wead_i64()?;
            w-wet map_vaw_15 = i_pwot.wead_bytes()?;
            v-vaw.insewt(map_key_14, nyaa~~ map_vaw_15);
          }
          i_pwot.wead_map_end()?;
          f_7 = some(vaw);
        }, 🥺
        8 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          w-wet m-mut vaw: btweemap<i64, >_< t-tensow::genewawtensow> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_16 = i_pwot.wead_i64()?;
            w-wet map_vaw_17 = tensow::genewawtensow::wead_fwom_in_pwotocow(i_pwot)?;
            v-vaw.insewt(map_key_16, òωó map_vaw_17);
          }
          i_pwot.wead_map_end()?;
          f_8 = some(vaw);
        },
        9 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, ʘwʘ t-tensow::spawsetensow> = b-btweemap::new();
          fow _ in 0..map_ident.size {
            wet map_key_18 = i-i_pwot.wead_i64()?;
            wet map_vaw_19 = t-tensow::spawsetensow::wead_fwom_in_pwotocow(i_pwot)?;
            v-vaw.insewt(map_key_18, mya m-map_vaw_19);
          }
          i_pwot.wead_map_end()?;
          f_9 = some(vaw);
        }, σωσ
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, OwO
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet w-wet = datawecowd {
      binawy_featuwes: f_1, (✿oωo)
      c-continuous_featuwes: f_2, ʘwʘ
      d-discwete_featuwes: f-f_3, mya
      s-stwing_featuwes: f_4, -.-
      s-spawse_binawy_featuwes: f-f_5, -.-
      s-spawse_continuous_featuwes: f-f_6, ^^;;
      bwob_featuwes: f_7, (ꈍᴗꈍ)
      t-tensows: f_8, rawr
      s-spawse_tensows: f-f_9, ^^
    };
    o-ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, nyaa~~ o-o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("datawecowd");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    i-if wet some(wef fwd_vaw) = sewf.binawy_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("binawyfeatuwes", (⑅˘꒳˘) t-ttype::set, (U ᵕ U❁) 1))?;
      o-o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::i64, (ꈍᴗꈍ) fwd_vaw.wen() as i32))?;
      fow e i-in fwd_vaw {
        o-o_pwot.wwite_i64(*e)?;
      }
      o-o_pwot.wwite_set_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = s-sewf.continuous_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("continuousfeatuwes", (✿oωo) ttype::map, UwU 2))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, ^^ ttype::doubwe, :3 f-fwd_vaw.wen() a-as i32))?;
      fow (k, ( ͡o ω ͡o ) v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_doubwe((*v).into())?;
      }
      o-o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.discwete_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("discwetefeatuwes", t-ttype::map, ( ͡o ω ͡o ) 3))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, (U ﹏ U) ttype::i64, fwd_vaw.wen() a-as i32))?;
      fow (k, -.- v) in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_i64(*v)?;
      }
      o-o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef f-fwd_vaw) = sewf.stwing_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("stwingfeatuwes", 😳😳😳 ttype::map, UwU 4))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >w< ttype::stwing, mya f-fwd_vaw.wen() a-as i32))?;
      f-fow (k, :3 v-v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_stwing(v)?;
      }
      o-o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef f-fwd_vaw) = sewf.spawse_binawy_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsebinawyfeatuwes", (ˆ ﻌ ˆ)♡ ttype::map, (U ﹏ U) 5))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, ʘwʘ t-ttype::set, rawr f-fwd_vaw.wen() as i32))?;
      fow (k, (ꈍᴗꈍ) v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::stwing, ( ͡o ω ͡o ) v.wen() a-as i32))?;
        f-fow e in v {
          o_pwot.wwite_stwing(e)?;
        }
        o_pwot.wwite_set_end()?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef f-fwd_vaw) = sewf.spawse_continuous_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsecontinuousfeatuwes", 😳😳😳 t-ttype::map, òωó 6))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, mya ttype::map, rawr x3 fwd_vaw.wen() as i32))?;
      fow (k, XD v) in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::stwing, (ˆ ﻌ ˆ)♡ t-ttype::doubwe, >w< v.wen() as i32))?;
        fow (k, (ꈍᴗꈍ) v) in v-v {
          o_pwot.wwite_stwing(k)?;
          o_pwot.wwite_doubwe((*v).into())?;
        }
        o_pwot.wwite_map_end()?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef f-fwd_vaw) = sewf.bwob_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("bwobfeatuwes", (U ﹏ U) t-ttype::map, >_< 7))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >_< ttype::stwing, -.- f-fwd_vaw.wen() a-as i32))?;
      fow (k, òωó v) in f-fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_bytes(v)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet s-some(wef fwd_vaw) = sewf.tensows {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("tensows", o.O t-ttype::map, σωσ 8))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, σωσ t-ttype::stwuct, mya f-fwd_vaw.wen() as i32))?;
      fow (k, o.O v) i-in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef f-fwd_vaw) = s-sewf.spawse_tensows {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsetensows", XD ttype::map, 9))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, XD ttype::stwuct, fwd_vaw.wen() a-as i32))?;
      f-fow (k, (✿oωo) v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        v-v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt fow datawecowd {
  fn defauwt() -> s-sewf {
    datawecowd{
      b-binawy_featuwes: some(btweeset::new()), -.-
      c-continuous_featuwes: s-some(btweemap::new()), (ꈍᴗꈍ)
      d-discwete_featuwes: some(btweemap::new()), ( ͡o ω ͡o )
      stwing_featuwes: s-some(btweemap::new()), (///ˬ///✿)
      spawse_binawy_featuwes: some(btweemap::new()),
      s-spawse_continuous_featuwes: some(btweemap::new()), 🥺
      bwob_featuwes: some(btweemap::new()), (ˆ ﻌ ˆ)♡
      t-tensows: some(btweemap::new()), ^•ﻌ•^
      s-spawse_tensows: s-some(btweemap::new()),
    }
  }
}

//
// c-compactdatawecowd
//

#[dewive(cwone, rawr x3 d-debug, (U ﹏ U) eq, hash, owd, OwO p-pawtiaweq, pawtiawowd)]
pub stwuct compactdatawecowd {
  p-pub binawy_featuwes: option<btweeset<i64>>, (✿oωo)
  pub continuous_featuwes: o-option<btweemap<i64, (⑅˘꒳˘) i32>>, UwU
  pub discwete_featuwes: o-option<btweemap<i64, (ˆ ﻌ ˆ)♡ i-i64>>,
  pub stwing_featuwes: o-option<btweemap<i64, /(^•ω•^) stwing>>, (˘ω˘)
  p-pub spawse_binawy_featuwes: o-option<btweemap<i64, XD btweeset<stwing>>>, òωó
  p-pub spawse_binawy_featuwes_with16b_spawse_key: o-option<btweemap<i64, UwU btweeset<i16>>>, -.-
  p-pub spawse_binawy_featuwes_with32b_spawse_key: option<btweemap<i64, (ꈍᴗꈍ) btweeset<i32>>>, (⑅˘꒳˘)
  pub spawse_binawy_featuwes_with64b_spawse_key: o-option<btweemap<i64, 🥺 btweeset<i64>>>, òωó
  p-pub spawse_continuous_featuwes: option<btweemap<i64, 😳 btweemap<stwing, òωó i-i32>>>, 🥺
  p-pub spawse_continuous_featuwes_with16b_spawse_key: o-option<btweemap<i64, btweemap<i16, ( ͡o ω ͡o ) i-i32>>>, UwU
  p-pub spawse_continuous_featuwes_with32b_spawse_key: option<btweemap<i64, 😳😳😳 b-btweemap<i32, ʘwʘ i32>>>,
  p-pub spawse_continuous_featuwes_with64b_spawse_key: option<btweemap<i64, ^^ b-btweemap<i64, >_< i-i32>>>, (ˆ ﻌ ˆ)♡
  pub bwob_featuwes: option<btweemap<i64, (ˆ ﻌ ˆ)♡ vec<u8>>>, 🥺
  pub tensows: o-option<btweemap<i64, ( ͡o ω ͡o ) t-tensow::genewawtensow>>, (ꈍᴗꈍ)
  pub spawse_tensows: option<btweemap<i64, :3 tensow::spawsetensow>>, (✿oωo)
}

i-impw compactdatawecowd {
  p-pub fn nyew<f1, (U ᵕ U❁) f-f2, UwU f3, f4, f5, f6, ^^ f7, f8, f9, f10, /(^•ω•^) f11, f12, f13, (˘ω˘) f14, f15>(binawy_featuwes: f-f1, OwO continuous_featuwes: f2, (U ᵕ U❁) discwete_featuwes: f3, (U ﹏ U) stwing_featuwes: f-f4, mya spawse_binawy_featuwes: f5, (⑅˘꒳˘) spawse_binawy_featuwes_with16b_spawse_key: f-f6, (U ᵕ U❁) spawse_binawy_featuwes_with32b_spawse_key: f-f7, /(^•ω•^) spawse_binawy_featuwes_with64b_spawse_key: f8, ^•ﻌ•^ spawse_continuous_featuwes: f-f9, (///ˬ///✿) spawse_continuous_featuwes_with16b_spawse_key: f-f10, spawse_continuous_featuwes_with32b_spawse_key: f-f11, o.O s-spawse_continuous_featuwes_with64b_spawse_key: f12, (ˆ ﻌ ˆ)♡ b-bwob_featuwes: f-f13, 😳 tensows: f14, òωó spawse_tensows: f15) -> compactdatawecowd whewe f1: into<option<btweeset<i64>>>, (⑅˘꒳˘) f2: into<option<btweemap<i64, rawr i32>>>, f3: i-into<option<btweemap<i64, (ꈍᴗꈍ) i-i64>>>, ^^ f-f4: into<option<btweemap<i64, (ˆ ﻌ ˆ)♡ s-stwing>>>, f5: i-into<option<btweemap<i64, b-btweeset<stwing>>>>, /(^•ω•^) f6: into<option<btweemap<i64, ^^ btweeset<i16>>>>, o.O f7: into<option<btweemap<i64, 😳😳😳 btweeset<i32>>>>, XD f8: i-into<option<btweemap<i64, nyaa~~ b-btweeset<i64>>>>, ^•ﻌ•^ f9: into<option<btweemap<i64, :3 btweemap<stwing, ^^ i32>>>>, o.O f-f10: into<option<btweemap<i64, ^^ b-btweemap<i16, (⑅˘꒳˘) i-i32>>>>, f11: into<option<btweemap<i64, ʘwʘ btweemap<i32, mya i-i32>>>>, >w< f12: into<option<btweemap<i64, o.O btweemap<i64, OwO i-i32>>>>, f13: into<option<btweemap<i64, -.- v-vec<u8>>>>, (U ﹏ U) f14: into<option<btweemap<i64, òωó tensow::genewawtensow>>>, >w< f-f15: into<option<btweemap<i64, ^•ﻌ•^ t-tensow::spawsetensow>>> {
    c-compactdatawecowd {
      binawy_featuwes: b-binawy_featuwes.into(), /(^•ω•^)
      c-continuous_featuwes: c-continuous_featuwes.into(), ʘwʘ
      d-discwete_featuwes: d-discwete_featuwes.into(), XD
      s-stwing_featuwes: stwing_featuwes.into(),
      s-spawse_binawy_featuwes: s-spawse_binawy_featuwes.into(), (U ᵕ U❁)
      spawse_binawy_featuwes_with16b_spawse_key: s-spawse_binawy_featuwes_with16b_spawse_key.into(), (ꈍᴗꈍ)
      spawse_binawy_featuwes_with32b_spawse_key: spawse_binawy_featuwes_with32b_spawse_key.into(), rawr x3
      spawse_binawy_featuwes_with64b_spawse_key: s-spawse_binawy_featuwes_with64b_spawse_key.into(), :3
      spawse_continuous_featuwes: s-spawse_continuous_featuwes.into(), (˘ω˘)
      spawse_continuous_featuwes_with16b_spawse_key: s-spawse_continuous_featuwes_with16b_spawse_key.into(), -.-
      s-spawse_continuous_featuwes_with32b_spawse_key: spawse_continuous_featuwes_with32b_spawse_key.into(), (ꈍᴗꈍ)
      spawse_continuous_featuwes_with64b_spawse_key: s-spawse_continuous_featuwes_with64b_spawse_key.into(), UwU
      bwob_featuwes: bwob_featuwes.into(), σωσ
      t-tensows: tensows.into(), ^^
      s-spawse_tensows: spawse_tensows.into(), :3
    }
  }
}

impw tsewiawizabwe f-fow compactdatawecowd {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<compactdatawecowd> {
    i_pwot.wead_stwuct_begin()?;
    wet mut f-f_1: option<btweeset<i64>> = n-nyone;
    wet mut f-f_2: option<btweemap<i64, ʘwʘ i-i32>> = nyone;
    wet mut f_3: option<btweemap<i64, 😳 i-i64>> = nyone;
    w-wet mut f_4: o-option<btweemap<i64, ^^ s-stwing>> = nyone;
    wet mut f_5: option<btweemap<i64, σωσ btweeset<stwing>>> = nyone;
    wet mut f_6: option<btweemap<i64, /(^•ω•^) btweeset<i16>>> = nyone;
    wet m-mut f_7: option<btweemap<i64, 😳😳😳 b-btweeset<i32>>> = n-nyone;
    wet mut f-f_8: option<btweemap<i64, 😳 b-btweeset<i64>>> = nyone;
    w-wet mut f_9: option<btweemap<i64, OwO b-btweemap<stwing, :3 i-i32>>> = nyone;
    w-wet mut f_10: option<btweemap<i64, nyaa~~ b-btweemap<i16, OwO i32>>> = nyone;
    wet mut f_11: o-option<btweemap<i64, o.O btweemap<i32, (U ﹏ U) i32>>> = n-nyone;
    wet mut f_12: option<btweemap<i64, (⑅˘꒳˘) b-btweemap<i64, OwO i-i32>>> = nyone;
    w-wet mut f_13: option<btweemap<i64, 😳 v-vec<u8>>> = nyone;
    w-wet mut f_14: option<btweemap<i64, :3 t-tensow::genewawtensow>> = n-nyone;
    wet mut f_15: o-option<btweemap<i64, ( ͡o ω ͡o ) tensow::spawsetensow>> = n-nyone;
    w-woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet set_ident = i_pwot.wead_set_begin()?;
          wet mut vaw: btweeset<i64> = b-btweeset::new();
          fow _ in 0..set_ident.size {
            wet set_ewem_20 = i_pwot.wead_i64()?;
            vaw.insewt(set_ewem_20);
          }
          i_pwot.wead_set_end()?;
          f_1 = some(vaw);
        }, 🥺
        2 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          wet mut v-vaw: btweemap<i64, /(^•ω•^) i32> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet map_key_21 = i_pwot.wead_i64()?;
            w-wet map_vaw_22 = i_pwot.wead_i32()?;
            v-vaw.insewt(map_key_21, nyaa~~ map_vaw_22);
          }
          i-i_pwot.wead_map_end()?;
          f_2 = s-some(vaw);
        }, (✿oωo)
        3 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, (✿oωo) i64> = btweemap::new();
          fow _ in 0..map_ident.size {
            w-wet map_key_23 = i_pwot.wead_i64()?;
            wet m-map_vaw_24 = i_pwot.wead_i64()?;
            v-vaw.insewt(map_key_23, (ꈍᴗꈍ) map_vaw_24);
          }
          i-i_pwot.wead_map_end()?;
          f-f_3 = some(vaw);
        }, OwO
        4 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, :3 s-stwing> = btweemap::new();
          fow _ in 0..map_ident.size {
            wet map_key_25 = i_pwot.wead_i64()?;
            wet map_vaw_26 = i-i_pwot.wead_stwing()?;
            v-vaw.insewt(map_key_25, mya map_vaw_26);
          }
          i-i_pwot.wead_map_end()?;
          f_4 = s-some(vaw);
        }, >_<
        5 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, (///ˬ///✿) btweeset<stwing>> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_27 = i_pwot.wead_i64()?;
            w-wet s-set_ident = i_pwot.wead_set_begin()?;
            wet mut map_vaw_28: b-btweeset<stwing> = btweeset::new();
            fow _ in 0..set_ident.size {
              w-wet set_ewem_29 = i_pwot.wead_stwing()?;
              map_vaw_28.insewt(set_ewem_29);
            }
            i-i_pwot.wead_set_end()?;
            v-vaw.insewt(map_key_27, (///ˬ///✿) map_vaw_28);
          }
          i_pwot.wead_map_end()?;
          f-f_5 = some(vaw);
        },
        6 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, 😳😳😳 btweeset<i16>> = btweemap::new();
          fow _ in 0..map_ident.size {
            w-wet map_key_30 = i-i_pwot.wead_i64()?;
            wet s-set_ident = i_pwot.wead_set_begin()?;
            w-wet mut map_vaw_31: btweeset<i16> = b-btweeset::new();
            fow _ in 0..set_ident.size {
              wet set_ewem_32 = i_pwot.wead_i16()?;
              map_vaw_31.insewt(set_ewem_32);
            }
            i-i_pwot.wead_set_end()?;
            vaw.insewt(map_key_30, (U ᵕ U❁) map_vaw_31);
          }
          i_pwot.wead_map_end()?;
          f_6 = s-some(vaw);
        }, (///ˬ///✿)
        7 => {
          w-wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, ( ͡o ω ͡o ) btweeset<i32>> = b-btweemap::new();
          f-fow _ i-in 0..map_ident.size {
            wet map_key_33 = i-i_pwot.wead_i64()?;
            wet set_ident = i-i_pwot.wead_set_begin()?;
            wet m-mut map_vaw_34: btweeset<i32> = b-btweeset::new();
            fow _ in 0..set_ident.size {
              w-wet set_ewem_35 = i_pwot.wead_i32()?;
              m-map_vaw_34.insewt(set_ewem_35);
            }
            i-i_pwot.wead_set_end()?;
            vaw.insewt(map_key_33, (✿oωo) m-map_vaw_34);
          }
          i-i_pwot.wead_map_end()?;
          f_7 = some(vaw);
        }, òωó
        8 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          wet m-mut vaw: btweemap<i64, (ˆ ﻌ ˆ)♡ btweeset<i64>> = b-btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet map_key_36 = i_pwot.wead_i64()?;
            w-wet set_ident = i_pwot.wead_set_begin()?;
            wet mut map_vaw_37: btweeset<i64> = btweeset::new();
            fow _ in 0..set_ident.size {
              wet set_ewem_38 = i_pwot.wead_i64()?;
              m-map_vaw_37.insewt(set_ewem_38);
            }
            i_pwot.wead_set_end()?;
            vaw.insewt(map_key_36, :3 m-map_vaw_37);
          }
          i_pwot.wead_map_end()?;
          f-f_8 = some(vaw);
        }, (ˆ ﻌ ˆ)♡
        9 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, (U ᵕ U❁) btweemap<stwing, (U ᵕ U❁) i-i32>> = btweemap::new();
          fow _ in 0..map_ident.size {
            wet map_key_39 = i-i_pwot.wead_i64()?;
            wet map_ident = i_pwot.wead_map_begin()?;
            w-wet mut map_vaw_40: btweemap<stwing, XD i32> = b-btweemap::new();
            f-fow _ in 0..map_ident.size {
              wet map_key_41 = i_pwot.wead_stwing()?;
              w-wet map_vaw_42 = i-i_pwot.wead_i32()?;
              map_vaw_40.insewt(map_key_41, nyaa~~ m-map_vaw_42);
            }
            i-i_pwot.wead_map_end()?;
            vaw.insewt(map_key_39, (ˆ ﻌ ˆ)♡ map_vaw_40);
          }
          i-i_pwot.wead_map_end()?;
          f_9 = some(vaw);
        }, ʘwʘ
        10 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, ^•ﻌ•^ btweemap<i16, mya i32>> = b-btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_43 = i_pwot.wead_i64()?;
            wet map_ident = i_pwot.wead_map_begin()?;
            w-wet mut map_vaw_44: btweemap<i16, (ꈍᴗꈍ) i-i32> = btweemap::new();
            fow _ in 0..map_ident.size {
              w-wet map_key_45 = i-i_pwot.wead_i16()?;
              wet map_vaw_46 = i_pwot.wead_i32()?;
              map_vaw_44.insewt(map_key_45, (ˆ ﻌ ˆ)♡ map_vaw_46);
            }
            i_pwot.wead_map_end()?;
            v-vaw.insewt(map_key_43, (ˆ ﻌ ˆ)♡ m-map_vaw_44);
          }
          i_pwot.wead_map_end()?;
          f_10 = s-some(vaw);
        }, ( ͡o ω ͡o )
        11 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: b-btweemap<i64, o.O b-btweemap<i32, 😳😳😳 i-i32>> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_47 = i_pwot.wead_i64()?;
            wet m-map_ident = i_pwot.wead_map_begin()?;
            w-wet mut map_vaw_48: b-btweemap<i32, ʘwʘ i-i32> = btweemap::new();
            f-fow _ in 0..map_ident.size {
              w-wet map_key_49 = i_pwot.wead_i32()?;
              w-wet map_vaw_50 = i-i_pwot.wead_i32()?;
              m-map_vaw_48.insewt(map_key_49, :3 map_vaw_50);
            }
            i_pwot.wead_map_end()?;
            vaw.insewt(map_key_47, m-map_vaw_48);
          }
          i_pwot.wead_map_end()?;
          f_11 = some(vaw);
        }, UwU
        12 => {
          w-wet map_ident = i_pwot.wead_map_begin()?;
          wet mut v-vaw: btweemap<i64, nyaa~~ b-btweemap<i64, :3 i32>> = btweemap::new();
          fow _ in 0..map_ident.size {
            wet map_key_51 = i-i_pwot.wead_i64()?;
            w-wet map_ident = i_pwot.wead_map_begin()?;
            w-wet mut map_vaw_52: b-btweemap<i64, nyaa~~ i32> = btweemap::new();
            fow _ in 0..map_ident.size {
              w-wet map_key_53 = i-i_pwot.wead_i64()?;
              wet map_vaw_54 = i_pwot.wead_i32()?;
              m-map_vaw_52.insewt(map_key_53, ^^ m-map_vaw_54);
            }
            i_pwot.wead_map_end()?;
            vaw.insewt(map_key_51, nyaa~~ m-map_vaw_52);
          }
          i_pwot.wead_map_end()?;
          f_12 = some(vaw);
        }, 😳😳😳
        13 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          wet m-mut vaw: btweemap<i64, ^•ﻌ•^ vec<u8>> = btweemap::new();
          f-fow _ i-in 0..map_ident.size {
            w-wet map_key_55 = i_pwot.wead_i64()?;
            w-wet map_vaw_56 = i-i_pwot.wead_bytes()?;
            v-vaw.insewt(map_key_55, m-map_vaw_56);
          }
          i-i_pwot.wead_map_end()?;
          f_13 = some(vaw);
        }, (⑅˘꒳˘)
        14 => {
          wet m-map_ident = i_pwot.wead_map_begin()?;
          w-wet mut vaw: btweemap<i64, (✿oωo) t-tensow::genewawtensow> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            w-wet map_key_57 = i-i_pwot.wead_i64()?;
            wet map_vaw_58 = t-tensow::genewawtensow::wead_fwom_in_pwotocow(i_pwot)?;
            vaw.insewt(map_key_57, mya m-map_vaw_58);
          }
          i-i_pwot.wead_map_end()?;
          f-f_14 = s-some(vaw);
        },
        15 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet mut vaw: b-btweemap<i64, (///ˬ///✿) t-tensow::spawsetensow> = btweemap::new();
          fow _ in 0..map_ident.size {
            wet m-map_key_59 = i_pwot.wead_i64()?;
            w-wet map_vaw_60 = tensow::spawsetensow::wead_fwom_in_pwotocow(i_pwot)?;
            v-vaw.insewt(map_key_59, ʘwʘ m-map_vaw_60);
          }
          i_pwot.wead_map_end()?;
          f_15 = s-some(vaw);
        }, >w<
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, o.O
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    w-wet wet = c-compactdatawecowd {
      binawy_featuwes: f_1, ^^;;
      c-continuous_featuwes: f_2, :3
      discwete_featuwes: f_3, (ꈍᴗꈍ)
      stwing_featuwes: f-f_4,
      s-spawse_binawy_featuwes: f_5, XD
      spawse_binawy_featuwes_with16b_spawse_key: f_6, ^^;;
      s-spawse_binawy_featuwes_with32b_spawse_key: f-f_7, (U ﹏ U)
      spawse_binawy_featuwes_with64b_spawse_key: f_8, (ꈍᴗꈍ)
      spawse_continuous_featuwes: f-f_9, 😳
      spawse_continuous_featuwes_with16b_spawse_key: f_10, rawr
      s-spawse_continuous_featuwes_with32b_spawse_key: f-f_11, ( ͡o ω ͡o )
      s-spawse_continuous_featuwes_with64b_spawse_key: f_12, (ˆ ﻌ ˆ)♡
      bwob_featuwes: f_13, OwO
      tensows: f-f_14, >_<
      spawse_tensows: f_15, XD
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, (ˆ ﻌ ˆ)♡ o-o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("compactdatawecowd");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet some(wef fwd_vaw) = s-sewf.binawy_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("binawyfeatuwes", (ꈍᴗꈍ) t-ttype::set, (✿oωo) 1))?;
      o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::i64, UwU fwd_vaw.wen() as i32))?;
      fow e in fwd_vaw {
        o_pwot.wwite_i64(*e)?;
      }
      o_pwot.wwite_set_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet s-some(wef fwd_vaw) = s-sewf.continuous_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("continuousfeatuwes", (ꈍᴗꈍ) ttype::map, (U ﹏ U) 2))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >w< t-ttype::i32, ^•ﻌ•^ fwd_vaw.wen() as i32))?;
      fow (k, 😳 v) i-in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_i32(*v)?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.discwete_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("discwetefeatuwes", XD t-ttype::map, 3))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, :3 ttype::i64, fwd_vaw.wen() a-as i32))?;
      fow (k, rawr x3 v) i-in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_i64(*v)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.stwing_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("stwingfeatuwes", (⑅˘꒳˘) ttype::map, ^^ 4))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >w< t-ttype::stwing, 😳 fwd_vaw.wen() as i32))?;
      fow (k, rawr v) i-in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_stwing(v)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if w-wet some(wef fwd_vaw) = s-sewf.spawse_binawy_featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsebinawyfeatuwes", rawr x3 t-ttype::map, (ꈍᴗꈍ) 5))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, -.- t-ttype::set, òωó f-fwd_vaw.wen() as i32))?;
      f-fow (k, v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::stwing, (U ﹏ U) v.wen() a-as i32))?;
        fow e in v {
          o-o_pwot.wwite_stwing(e)?;
        }
        o-o_pwot.wwite_set_end()?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.spawse_binawy_featuwes_with16b_spawse_key {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsebinawyfeatuweswith16bspawsekey", ( ͡o ω ͡o ) ttype::map, :3 6))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >w< t-ttype::set, ^^ f-fwd_vaw.wen() a-as i32))?;
      fow (k, 😳😳😳 v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::i16, OwO v.wen() as i32))?;
        f-fow e in v {
          o_pwot.wwite_i16(*e)?;
        }
        o_pwot.wwite_set_end()?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.spawse_binawy_featuwes_with32b_spawse_key {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsebinawyfeatuweswith32bspawsekey", XD t-ttype::map, (⑅˘꒳˘) 7))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, OwO t-ttype::set, (⑅˘꒳˘) f-fwd_vaw.wen() a-as i32))?;
      fow (k, (U ﹏ U) v) i-in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::i32, (ꈍᴗꈍ) v-v.wen() a-as i32))?;
        f-fow e in v-v {
          o_pwot.wwite_i32(*e)?;
        }
        o-o_pwot.wwite_set_end()?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.spawse_binawy_featuwes_with64b_spawse_key {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsebinawyfeatuweswith64bspawsekey", rawr ttype::map, XD 8))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, >w< ttype::set, UwU fwd_vaw.wen() as i32))?;
      f-fow (k, 😳 v-v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_set_begin(&tsetidentifiew::new(ttype::i64, (ˆ ﻌ ˆ)♡ v.wen() as i32))?;
        fow e i-in v {
          o-o_pwot.wwite_i64(*e)?;
        }
        o-o_pwot.wwite_set_end()?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.spawse_continuous_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsecontinuousfeatuwes", ^•ﻌ•^ t-ttype::map, ^^ 9))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, 😳 ttype::map, fwd_vaw.wen() as i32))?;
      fow (k, :3 v-v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::stwing, (⑅˘꒳˘) ttype::i32, ( ͡o ω ͡o ) v.wen() as i32))?;
        fow (k, :3 v-v) in v {
          o_pwot.wwite_stwing(k)?;
          o-o_pwot.wwite_i32(*v)?;
        }
        o_pwot.wwite_map_end()?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef f-fwd_vaw) = sewf.spawse_continuous_featuwes_with16b_spawse_key {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsecontinuousfeatuweswith16bspawsekey", (⑅˘꒳˘) t-ttype::map, >w< 10))?;
      o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, OwO ttype::map, fwd_vaw.wen() as i-i32))?;
      fow (k, 😳 v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i16, OwO t-ttype::i32, 🥺 v-v.wen() as i32))?;
        f-fow (k, (˘ω˘) v) in v {
          o_pwot.wwite_i16(*k)?;
          o_pwot.wwite_i32(*v)?;
        }
        o_pwot.wwite_map_end()?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = s-sewf.spawse_continuous_featuwes_with32b_spawse_key {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsecontinuousfeatuweswith32bspawsekey", 😳😳😳 ttype::map, mya 11))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, OwO ttype::map, >_< fwd_vaw.wen() a-as i32))?;
      f-fow (k, 😳 v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i32, (U ᵕ U❁) t-ttype::i32, 🥺 v.wen() as i32))?;
        f-fow (k, (U ﹏ U) v) in v {
          o_pwot.wwite_i32(*k)?;
          o-o_pwot.wwite_i32(*v)?;
        }
        o_pwot.wwite_map_end()?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet s-some(wef fwd_vaw) = sewf.spawse_continuous_featuwes_with64b_spawse_key {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsecontinuousfeatuweswith64bspawsekey", (U ﹏ U) t-ttype::map, rawr x3 12))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, :3 t-ttype::map, rawr f-fwd_vaw.wen() a-as i32))?;
      f-fow (k, XD v) i-in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        o-o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, ^^ t-ttype::i32, mya v.wen() as i32))?;
        f-fow (k, (U ﹏ U) v) in v {
          o-o_pwot.wwite_i64(*k)?;
          o_pwot.wwite_i32(*v)?;
        }
        o_pwot.wwite_map_end()?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = s-sewf.bwob_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("bwobfeatuwes", 😳 t-ttype::map, mya 13))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, 😳 t-ttype::stwing, ^^ f-fwd_vaw.wen() as i32))?;
      f-fow (k, :3 v) in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        o_pwot.wwite_bytes(v)?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.tensows {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("tensows", (U ﹏ U) ttype::map, UwU 14))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, (ˆ ﻌ ˆ)♡ ttype::stwuct, (ˆ ﻌ ˆ)♡ f-fwd_vaw.wen() as i32))?;
      fow (k, ^^;; v-v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        v-v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.spawse_tensows {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsetensows", rawr ttype::map, nyaa~~ 15))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, rawr x3 ttype::stwuct, (⑅˘꒳˘) f-fwd_vaw.wen() a-as i32))?;
      f-fow (k, OwO v) in fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        v-v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o-o_pwot.wwite_map_end()?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt fow compactdatawecowd {
  fn d-defauwt() -> sewf {
    c-compactdatawecowd{
      b-binawy_featuwes: s-some(btweeset::new()), OwO
      c-continuous_featuwes: some(btweemap::new()), ʘwʘ
      d-discwete_featuwes: s-some(btweemap::new()), :3
      s-stwing_featuwes: s-some(btweemap::new()), mya
      s-spawse_binawy_featuwes: s-some(btweemap::new()), OwO
      s-spawse_binawy_featuwes_with16b_spawse_key: s-some(btweemap::new()), :3
      s-spawse_binawy_featuwes_with32b_spawse_key: s-some(btweemap::new()), >_<
      spawse_binawy_featuwes_with64b_spawse_key: some(btweemap::new()), σωσ
      spawse_continuous_featuwes: s-some(btweemap::new()), /(^•ω•^)
      spawse_continuous_featuwes_with16b_spawse_key: s-some(btweemap::new()), mya
      spawse_continuous_featuwes_with32b_spawse_key: some(btweemap::new()), nyaa~~
      spawse_continuous_featuwes_with64b_spawse_key: s-some(btweemap::new()), 😳
      b-bwob_featuwes: s-some(btweemap::new()), ^^;;
      tensows: s-some(btweemap::new()), 😳😳😳
      s-spawse_tensows: some(btweemap::new()), nyaa~~
    }
  }
}

//
// tensowwecowd
//

#[dewive(cwone, 🥺 debug, XD eq, hash, owd, pawtiaweq, (ꈍᴗꈍ) pawtiawowd)]
p-pub stwuct tensowwecowd {
  pub tensows: option<btweemap<i64, 😳😳😳 tensow::genewawtensow>>, ( ͡o ω ͡o )
  pub s-spawse_tensows: o-option<btweemap<i64, tensow::spawsetensow>>, nyaa~~
}

i-impw tensowwecowd {
  p-pub fn n-nyew<f1, XD f2>(tensows: f-f1, (ˆ ﻌ ˆ)♡ spawse_tensows: f-f2) -> t-tensowwecowd whewe f-f1: into<option<btweemap<i64, rawr x3 tensow::genewawtensow>>>, OwO f2: i-into<option<btweemap<i64, UwU tensow::spawsetensow>>> {
    t-tensowwecowd {
      tensows: t-tensows.into(), ^^
      s-spawse_tensows: spawse_tensows.into(), (✿oωo)
    }
  }
}

i-impw tsewiawizabwe fow tensowwecowd {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn t-tinputpwotocow) -> t-thwift::wesuwt<tensowwecowd> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f-f_1: option<btweemap<i64, 😳😳😳 t-tensow::genewawtensow>> = n-nyone;
    wet mut f_2: option<btweemap<i64, t-tensow::spawsetensow>> = nyone;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match f-fiewd_id {
        1 => {
          wet map_ident = i_pwot.wead_map_begin()?;
          wet mut vaw: btweemap<i64, 🥺 t-tensow::genewawtensow> = btweemap::new();
          f-fow _ in 0..map_ident.size {
            wet map_key_61 = i_pwot.wead_i64()?;
            w-wet map_vaw_62 = t-tensow::genewawtensow::wead_fwom_in_pwotocow(i_pwot)?;
            vaw.insewt(map_key_61, ʘwʘ map_vaw_62);
          }
          i-i_pwot.wead_map_end()?;
          f-f_1 = some(vaw);
        },
        2 => {
          wet map_ident = i-i_pwot.wead_map_begin()?;
          wet m-mut vaw: btweemap<i64, 😳 t-tensow::spawsetensow> = btweemap::new();
          fow _ in 0..map_ident.size {
            w-wet map_key_63 = i-i_pwot.wead_i64()?;
            w-wet map_vaw_64 = t-tensow::spawsetensow::wead_fwom_in_pwotocow(i_pwot)?;
            vaw.insewt(map_key_63, ^^;; map_vaw_64);
          }
          i-i_pwot.wead_map_end()?;
          f-f_2 = some(vaw);
        }, (///ˬ///✿)
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, OwO
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet w-wet = tensowwecowd {
      t-tensows: f_1, -.-
      spawse_tensows: f_2, ^^
    };
    ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, (ꈍᴗꈍ) o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("tensowwecowd");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet some(wef fwd_vaw) = s-sewf.tensows {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("tensows", ^^;; t-ttype::map, (˘ω˘) 1))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, 🥺 t-ttype::stwuct, ʘwʘ f-fwd_vaw.wen() as i32))?;
      fow (k, (///ˬ///✿) v) in f-fwd_vaw {
        o-o_pwot.wwite_i64(*k)?;
        v-v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o-o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.spawse_tensows {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("spawsetensows", ^^;; ttype::map, 2))?;
      o_pwot.wwite_map_begin(&tmapidentifiew::new(ttype::i64, XD ttype::stwuct, (ˆ ﻌ ˆ)♡ f-fwd_vaw.wen() as i32))?;
      f-fow (k, (˘ω˘) v-v) in fwd_vaw {
        o_pwot.wwite_i64(*k)?;
        v.wwite_to_out_pwotocow(o_pwot)?;
      }
      o_pwot.wwite_map_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt fow tensowwecowd {
  fn d-defauwt() -> sewf {
    tensowwecowd{
      tensows: some(btweemap::new()), σωσ
      s-spawse_tensows: some(btweemap::new()), 😳😳😳
    }
  }
}

//
// featuwemetainfo
//

#[dewive(cwone, ^•ﻌ•^ debug, eq, σωσ hash, owd, pawtiaweq, (///ˬ///✿) p-pawtiawowd)]
p-pub stwuct featuwemetainfo {
  pub f-featuwe_id: option<i64>, XD
  p-pub fuww_featuwe_name: option<stwing>, >_<
  p-pub featuwe_type: option<featuwetype>, òωó
}

i-impw featuwemetainfo {
  pub fn nyew<f1, (U ᵕ U❁) f2, f3>(featuwe_id: f-f1, (˘ω˘) f-fuww_featuwe_name: f-f2, 🥺 featuwe_type: f3) -> featuwemetainfo whewe f-f1: into<option<i64>>, (✿oωo) f2: into<option<stwing>>, (˘ω˘) f3: into<option<featuwetype>> {
    featuwemetainfo {
      featuwe_id: featuwe_id.into(), (ꈍᴗꈍ)
      fuww_featuwe_name: fuww_featuwe_name.into(), ( ͡o ω ͡o )
      f-featuwe_type: f-featuwe_type.into(), (U ᵕ U❁)
    }
  }
}

impw tsewiawizabwe fow featuwemetainfo {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<featuwemetainfo> {
    i_pwot.wead_stwuct_begin()?;
    wet mut f-f_1: option<i64> = n-nyone;
    wet m-mut f_2: option<stwing> = n-nyone;
    wet mut f_3: option<featuwetype> = nyone;
    woop {
      wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      wet f-fiewd_id = fiewd_id(&fiewd_ident)?;
      match f-fiewd_id {
        1 => {
          wet vaw = i_pwot.wead_i64()?;
          f_1 = s-some(vaw);
        },
        2 => {
          w-wet vaw = i_pwot.wead_stwing()?;
          f-f_2 = s-some(vaw);
        }, ʘwʘ
        3 => {
          wet vaw = featuwetype::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_3 = some(vaw);
        }, (ˆ ﻌ ˆ)♡
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, /(^•ω•^)
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet w-wet = featuwemetainfo {
      f-featuwe_id: f_1, (ˆ ﻌ ˆ)♡
      fuww_featuwe_name: f_2, (✿oωo)
      f-featuwe_type: f_3, ^•ﻌ•^
    };
    o-ok(wet)
  }
  fn w-wwite_to_out_pwotocow(&sewf, (ˆ ﻌ ˆ)♡ o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = tstwuctidentifiew::new("featuwemetainfo");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    i-if wet some(fwd_vaw) = s-sewf.featuwe_id {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("featuweid", XD ttype::i64, :3 1))?;
      o-o_pwot.wwite_i64(fwd_vaw)?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef f-fwd_vaw) = s-sewf.fuww_featuwe_name {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("fuwwfeatuwename", -.- t-ttype::stwing, 2))?;
      o_pwot.wwite_stwing(fwd_vaw)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    if wet some(wef fwd_vaw) = sewf.featuwe_type {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("featuwetype", ^^;; t-ttype::i32, OwO 3))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt fow featuwemetainfo {
  fn defauwt() -> sewf {
    featuwemetainfo{
      featuwe_id: s-some(0), ^^;;
      f-fuww_featuwe_name: s-some("".to_owned()), 🥺
      f-featuwe_type: nyone, ^^
    }
  }
}

//
// featuwemetainfowist
//

#[dewive(cwone, o.O debug, eq, ( ͡o ω ͡o ) hash, owd, pawtiaweq, nyaa~~ pawtiawowd)]
p-pub stwuct featuwemetainfowist {
  pub contents: o-option<vec<featuwemetainfo>>, (///ˬ///✿)
}

i-impw featuwemetainfowist {
  p-pub fn nyew<f1>(contents: f1) -> featuwemetainfowist w-whewe f1: into<option<vec<featuwemetainfo>>> {
    featuwemetainfowist {
      contents: contents.into(), (ˆ ﻌ ˆ)♡
    }
  }
}

impw tsewiawizabwe fow featuwemetainfowist {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> thwift::wesuwt<featuwemetainfowist> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<vec<featuwemetainfo>> = n-nyone;
    woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        bweak;
      }
      wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          wet wist_ident = i_pwot.wead_wist_begin()?;
          w-wet mut vaw: v-vec<featuwemetainfo> = v-vec::with_capacity(wist_ident.size a-as usize);
          f-fow _ in 0..wist_ident.size {
            wet wist_ewem_65 = featuwemetainfo::wead_fwom_in_pwotocow(i_pwot)?;
            v-vaw.push(wist_ewem_65);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = s-some(vaw);
        }, XD
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, >_<
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet wet = featuwemetainfowist {
      contents: f-f_1, (U ﹏ U)
    };
    ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, òωó o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("featuwemetainfowist");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet some(wef fwd_vaw) = s-sewf.contents {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("contents", >w< t-ttype::wist, ^•ﻌ•^ 1))?;
      o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::stwuct, 🥺 f-fwd_vaw.wen() as i32))?;
      fow e in f-fwd_vaw {
        e-e.wwite_to_out_pwotocow(o_pwot)?;
      }
      o-o_pwot.wwite_wist_end()?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt f-fow featuwemetainfowist {
  f-fn defauwt() -> s-sewf {
    featuwemetainfowist{
      contents: some(vec::new()), (✿oωo)
    }
  }
}

