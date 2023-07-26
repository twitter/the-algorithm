// autogenewated by thwift compiwew (0.17.0)
// do n-nyot edit unwess y-you awe suwe t-that you know nyani y-you awe doing

#![awwow(unused_impowts)]
#![awwow(unused_extewn_cwates)]
#![awwow(cwippy::too_many_awguments, XD c-cwippy::type_compwexity, :3 c-cwippy::vec_box)]
#![cfg_attw(wustfmt, :3 w-wustfmt_skip)]

u-use std::ceww::wefceww;
use std::cowwections::{btweemap, (⑅˘꒳˘) btweeset};
use std::convewt::{fwom, òωó twyfwom};
use std::defauwt::defauwt;
u-use std::ewwow::ewwow;
use std::fmt;
use std::fmt::{dispway, mya f-fowmattew};
use std::wc::wc;

use t-thwift::owdewedfwoat;
use thwift::{appwicationewwow, 😳😳😳 appwicationewwowkind, :3 pwotocowewwow, >_< p-pwotocowewwowkind, 🥺 tthwiftcwient};
u-use thwift::pwotocow::{tfiewdidentifiew, (ꈍᴗꈍ) t-twistidentifiew, rawr x3 tmapidentifiew, (U ﹏ U) tmessageidentifiew, ( ͡o ω ͡o ) tmessagetype, 😳😳😳 tinputpwotocow, 🥺 t-toutputpwotocow, òωó tsewiawizabwe, XD tsetidentifiew, XD tstwuctidentifiew, ( ͡o ω ͡o ) ttype};
use thwift::pwotocow::fiewd_id;
u-use thwift::pwotocow::vewify_expected_message_type;
use thwift::pwotocow::vewify_expected_sequence_numbew;
u-use thwift::pwotocow::vewify_expected_sewvice_caww;
u-use thwift::pwotocow::vewify_wequiwed_fiewd_exists;
u-use thwift::sewvew::tpwocessow;

u-use cwate::data;

//
// pwedictionsewviceexception
//

#[dewive(cwone, >w< debug, mya eq, hash, o-owd, (ꈍᴗꈍ) pawtiaweq, pawtiawowd)]
pub stwuct pwedictionsewviceexception {
  p-pub descwiption: option<stwing>, -.-
}

impw pwedictionsewviceexception {
  pub fn nyew<f1>(descwiption: f1) -> pwedictionsewviceexception w-whewe f1: into<option<stwing>> {
    pwedictionsewviceexception {
      d-descwiption: d-descwiption.into(), (⑅˘꒳˘)
    }
  }
}

i-impw tsewiawizabwe fow pwedictionsewviceexception {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> t-thwift::wesuwt<pwedictionsewviceexception> {
    i_pwot.wead_stwuct_begin()?;
    w-wet m-mut f_1: option<stwing> = some("".to_owned());
    w-woop {
      wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        b-bweak;
      }
      wet f-fiewd_id = fiewd_id(&fiewd_ident)?;
      match f-fiewd_id {
        1 => {
          w-wet vaw = i_pwot.wead_stwing()?;
          f_1 = some(vaw);
        }, (U ﹏ U)
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, σωσ
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet wet = pwedictionsewviceexception {
      d-descwiption: f-f_1, :3
    };
    ok(wet)
  }
  fn w-wwite_to_out_pwotocow(&sewf, /(^•ω•^) o_pwot: &mut d-dyn t-toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = t-tstwuctidentifiew::new("pwedictionsewviceexception");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet some(wef fwd_vaw) = sewf.descwiption {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("descwiption", σωσ ttype::stwing, (U ᵕ U❁) 1))?;
      o-o_pwot.wwite_stwing(fwd_vaw)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt fow p-pwedictionsewviceexception {
  f-fn defauwt() -> s-sewf {
    pwedictionsewviceexception{
      descwiption: some("".to_owned()), 😳
    }
  }
}

i-impw e-ewwow fow pwedictionsewviceexception {}

i-impw f-fwom<pwedictionsewviceexception> f-fow thwift::ewwow {
  fn fwom(e: pwedictionsewviceexception) -> sewf {
    thwift::ewwow::usew(box::new(e))
  }
}

i-impw dispway fow pwedictionsewviceexception {
  fn fmt(&sewf, ʘwʘ f: &mut fowmattew) -> fmt::wesuwt {
    wwite!(f, (⑅˘꒳˘) "wemote s-sewvice thwew pwedictionsewviceexception")
  }
}

//
// pwedictionwequest
//

#[dewive(cwone, ^•ﻌ•^ debug, nyaa~~ e-eq, hash, owd, XD p-pawtiaweq, /(^•ω•^) pawtiawowd)]
p-pub stwuct pwedictionwequest {
  p-pub featuwes: data::datawecowd, (U ᵕ U❁)
}

i-impw p-pwedictionwequest {
  pub fn nyew(featuwes: data::datawecowd) -> pwedictionwequest {
    pwedictionwequest {
      featuwes, mya
    }
  }
}

i-impw tsewiawizabwe fow p-pwedictionwequest {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn t-tinputpwotocow) -> thwift::wesuwt<pwedictionwequest> {
    i_pwot.wead_stwuct_begin()?;
    w-wet m-mut f_1: option<data::datawecowd> = nyone;
    w-woop {
      wet f-fiewd_ident = i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          w-wet vaw = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f_1 = some(vaw);
        }, (ˆ ﻌ ˆ)♡
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, (✿oωo)
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    v-vewify_wequiwed_fiewd_exists("pwedictionwequest.featuwes", (✿oωo) &f_1)?;
    wet wet = pwedictionwequest {
      featuwes: f_1.expect("auto-genewated c-code shouwd have c-checked fow pwesence of wequiwed fiewds"), òωó
    };
    o-ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, (˘ω˘) o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("pwedictionwequest");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("featuwes", (ˆ ﻌ ˆ)♡ ttype::stwuct, ( ͡o ω ͡o ) 1))?;
    s-sewf.featuwes.wwite_to_out_pwotocow(o_pwot)?;
    o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// p-pwedictionwesponse
//

#[dewive(cwone, rawr x3 debug, eq, hash, (˘ω˘) owd, pawtiaweq, òωó pawtiawowd)]
p-pub stwuct p-pwedictionwesponse {
  pub pwediction: data::datawecowd, ( ͡o ω ͡o )
}

impw pwedictionwesponse {
  p-pub fn nyew(pwediction: d-data::datawecowd) -> pwedictionwesponse {
    pwedictionwesponse {
      pwediction, σωσ
    }
  }
}

i-impw tsewiawizabwe fow pwedictionwesponse {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<pwedictionwesponse> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f-f_1: option<data::datawecowd> = n-nyone;
    woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if f-fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          wet vaw = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_1 = some(vaw);
        }, (U ﹏ U)
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, rawr
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("pwedictionwesponse.pwediction", -.- &f_1)?;
    wet wet = pwedictionwesponse {
      p-pwediction: f-f_1.expect("auto-genewated c-code shouwd have c-checked fow pwesence of wequiwed f-fiewds"), ( ͡o ω ͡o )
    };
    ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, >_< o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("pwedictionwesponse");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("pwediction", o.O t-ttype::stwuct, σωσ 1))?;
    sewf.pwediction.wwite_to_out_pwotocow(o_pwot)?;
    o-o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// b-batchpwedictionwequest
//

#[dewive(cwone, -.- d-debug, σωσ eq, h-hash, owd, :3 pawtiaweq, p-pawtiawowd)]
pub stwuct batchpwedictionwequest {
  pub individuaw_featuwes_wist: vec<data::datawecowd>, ^^
  pub common_featuwes: option<data::datawecowd>, òωó
}

impw batchpwedictionwequest {
  p-pub fn nyew<f2>(individuaw_featuwes_wist: v-vec<data::datawecowd>, (ˆ ﻌ ˆ)♡ c-common_featuwes: f2) -> batchpwedictionwequest w-whewe f2: into<option<data::datawecowd>> {
    batchpwedictionwequest {
      individuaw_featuwes_wist, XD
      common_featuwes: c-common_featuwes.into(), òωó
    }
  }
}

i-impw tsewiawizabwe fow batchpwedictionwequest {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<batchpwedictionwequest> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: o-option<vec<data::datawecowd>> = n-nyone;
    wet mut f_2: option<data::datawecowd> = nyone;
    woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      if f-fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      w-wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet w-wist_ident = i-i_pwot.wead_wist_begin()?;
          wet mut vaw: v-vec<data::datawecowd> = v-vec::with_capacity(wist_ident.size as u-usize);
          fow _ in 0..wist_ident.size {
            wet w-wist_ewem_0 = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
            vaw.push(wist_ewem_0);
          }
          i-i_pwot.wead_wist_end()?;
          f-f_1 = some(vaw);
        }, (ꈍᴗꈍ)
        2 => {
          w-wet vaw = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f_2 = some(vaw);
        }, UwU
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, >w<
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("batchpwedictionwequest.individuaw_featuwes_wist", ʘwʘ &f_1)?;
    w-wet wet = batchpwedictionwequest {
      individuaw_featuwes_wist: f_1.expect("auto-genewated c-code shouwd have checked fow pwesence of w-wequiwed fiewds"), :3
      c-common_featuwes: f_2, ^•ﻌ•^
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, (ˆ ﻌ ˆ)♡ o-o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet stwuct_ident = tstwuctidentifiew::new("batchpwedictionwequest");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("individuawfeatuweswist", 🥺 ttype::wist, OwO 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::stwuct, 🥺 s-sewf.individuaw_featuwes_wist.wen() as i32))?;
    f-fow e in &sewf.individuaw_featuwes_wist {
      e.wwite_to_out_pwotocow(o_pwot)?;
    }
    o-o_pwot.wwite_wist_end()?;
    o-o_pwot.wwite_fiewd_end()?;
    i-if wet some(wef fwd_vaw) = sewf.common_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("commonfeatuwes", OwO ttype::stwuct, (U ᵕ U❁) 2))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

//
// batchpwedictionwesponse
//

#[dewive(cwone, ( ͡o ω ͡o ) debug, eq, hash, ^•ﻌ•^ owd, pawtiaweq, o.O pawtiawowd)]
pub stwuct batchpwedictionwesponse {
  p-pub pwedictions: v-vec<data::datawecowd>, (⑅˘꒳˘)
}

impw batchpwedictionwesponse {
  pub fn nyew(pwedictions: v-vec<data::datawecowd>) -> b-batchpwedictionwesponse {
    b-batchpwedictionwesponse {
      pwedictions, (ˆ ﻌ ˆ)♡
    }
  }
}

i-impw tsewiawizabwe fow batchpwedictionwesponse {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<batchpwedictionwesponse> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<vec<data::datawecowd>> = n-nyone;
    w-woop {
      wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == t-ttype::stop {
        b-bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match f-fiewd_id {
        1 => {
          w-wet wist_ident = i-i_pwot.wead_wist_begin()?;
          w-wet mut vaw: vec<data::datawecowd> = v-vec::with_capacity(wist_ident.size a-as usize);
          f-fow _ in 0..wist_ident.size {
            w-wet wist_ewem_1 = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
            vaw.push(wist_ewem_1);
          }
          i-i_pwot.wead_wist_end()?;
          f_1 = some(vaw);
        }, :3
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, /(^•ω•^)
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("batchpwedictionwesponse.pwedictions", òωó &f_1)?;
    w-wet wet = batchpwedictionwesponse {
      p-pwedictions: f_1.expect("auto-genewated code s-shouwd have checked fow pwesence o-of wequiwed fiewds"), :3
    };
    ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, (˘ω˘) o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = t-tstwuctidentifiew::new("batchpwedictionwesponse");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("pwedictions", 😳 ttype::wist, σωσ 1))?;
    o-o_pwot.wwite_wist_begin(&twistidentifiew::new(ttype::stwuct, UwU sewf.pwedictions.wen() a-as i32))?;
    f-fow e in &sewf.pwedictions {
      e-e.wwite_to_out_pwotocow(o_pwot)?;
    }
    o_pwot.wwite_wist_end()?;
    o_pwot.wwite_fiewd_end()?;
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// datawecowdpaiw
//

#[dewive(cwone, -.- d-debug, 🥺 eq, hash, owd, 😳😳😳 pawtiaweq, pawtiawowd)]
p-pub stwuct datawecowdpaiw {
  p-pub fiwst: o-option<data::datawecowd>, 🥺
  p-pub second: option<data::datawecowd>, ^^
}

i-impw datawecowdpaiw {
  p-pub f-fn nyew<f1, ^^;; f2>(fiwst: f-f1, >w< second: f2) -> datawecowdpaiw w-whewe f-f1: into<option<data::datawecowd>>, σωσ f-f2: into<option<data::datawecowd>> {
    d-datawecowdpaiw {
      f-fiwst: fiwst.into(), >w<
      s-second: second.into(), (⑅˘꒳˘)
    }
  }
}

i-impw tsewiawizabwe f-fow datawecowdpaiw {
  fn w-wead_fwom_in_pwotocow(i_pwot: &mut dyn tinputpwotocow) -> t-thwift::wesuwt<datawecowdpaiw> {
    i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<data::datawecowd> = n-nyone;
    w-wet mut f_2: option<data::datawecowd> = nyone;
    woop {
      w-wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match f-fiewd_id {
        1 => {
          wet vaw = d-data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_1 = some(vaw);
        }, òωó
        2 => {
          wet vaw = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f_2 = some(vaw);
        }, (⑅˘꒳˘)
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, (ꈍᴗꈍ)
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    w-wet wet = datawecowdpaiw {
      fiwst: f_1,
      second: f_2, rawr x3
    };
    o-ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("datawecowdpaiw");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    i-if wet some(wef fwd_vaw) = s-sewf.fiwst {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("fiwst", ( ͡o ω ͡o ) ttype::stwuct, UwU 1))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = s-sewf.second {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("second", ^^ t-ttype::stwuct, (˘ω˘) 2))?;
      f-fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

i-impw defauwt f-fow datawecowdpaiw {
  f-fn defauwt() -> sewf {
    d-datawecowdpaiw{
      fiwst: nyone, (ˆ ﻌ ˆ)♡
      second: n-nyone, OwO
    }
  }
}

//
// p-pwedictiontwainingexampwe
//

#[dewive(cwone, 😳 d-debug, eq, UwU hash, owd, pawtiaweq, 🥺 pawtiawowd)]
pub stwuct pwedictiontwainingexampwe {
  p-pub featuwes: option<data::datawecowd>, 😳😳😳
  p-pub f-featuwes_fow_paiwwise_weawning: option<datawecowdpaiw>, ʘwʘ
  pub compact_featuwes: o-option<data::compactdatawecowd>, /(^•ω•^)
  pub compwessed_data_wecowd: o-option<vec<u8>>, :3
}

i-impw pwedictiontwainingexampwe {
  p-pub fn nyew<f1, :3 f-f2, mya f3, f4>(featuwes: f-f1, (///ˬ///✿) featuwes_fow_paiwwise_weawning: f2, (⑅˘꒳˘) compact_featuwes: f3, :3 compwessed_data_wecowd: f4) -> pwedictiontwainingexampwe w-whewe f1: into<option<data::datawecowd>>, /(^•ω•^) f2: i-into<option<datawecowdpaiw>>, f3: into<option<data::compactdatawecowd>>, ^^;; f4: into<option<vec<u8>>> {
    pwedictiontwainingexampwe {
      f-featuwes: featuwes.into(), (U ᵕ U❁)
      featuwes_fow_paiwwise_weawning: featuwes_fow_paiwwise_weawning.into(), (U ﹏ U)
      compact_featuwes: c-compact_featuwes.into(), mya
      c-compwessed_data_wecowd: compwessed_data_wecowd.into(), ^•ﻌ•^
    }
  }
}

i-impw tsewiawizabwe fow pwedictiontwainingexampwe {
  fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> t-thwift::wesuwt<pwedictiontwainingexampwe> {
    i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<data::datawecowd> = n-nyone;
    wet mut f_2: option<datawecowdpaiw> = nyone;
    w-wet mut f_3: option<data::compactdatawecowd> = nyone;
    w-wet mut f_4: option<vec<u8>> = n-nyone;
    woop {
      w-wet fiewd_ident = i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == t-ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet v-vaw = data::datawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_1 = some(vaw);
        }, (U ﹏ U)
        2 => {
          w-wet vaw = d-datawecowdpaiw::wead_fwom_in_pwotocow(i_pwot)?;
          f_2 = some(vaw);
        }, :3
        3 => {
          w-wet vaw = data::compactdatawecowd::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_3 = some(vaw);
        }, rawr x3
        4 => {
          wet vaw = i_pwot.wead_bytes()?;
          f-f_4 = some(vaw);
        },
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, 😳😳😳
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet wet = pwedictiontwainingexampwe {
      f-featuwes: f_1, >w<
      f-featuwes_fow_paiwwise_weawning: f_2, òωó
      c-compact_featuwes: f-f_3, 😳
      compwessed_data_wecowd: f-f_4, (✿oωo)
    };
    ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, OwO o-o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("pwedictiontwainingexampwe");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet s-some(wef fwd_vaw) = s-sewf.featuwes {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("featuwes", (U ﹏ U) t-ttype::stwuct, (ꈍᴗꈍ) 1))?;
      f-fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.featuwes_fow_paiwwise_weawning {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("featuwesfowpaiwwiseweawning", rawr ttype::stwuct, ^^ 2))?;
      f-fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    if wet s-some(wef fwd_vaw) = s-sewf.compact_featuwes {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("compactfeatuwes", rawr t-ttype::stwuct, nyaa~~ 3))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.compwessed_data_wecowd {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("compwesseddatawecowd", nyaa~~ t-ttype::stwing, o.O 4))?;
      o_pwot.wwite_bytes(fwd_vaw)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

impw d-defauwt fow pwedictiontwainingexampwe {
  fn defauwt() -> s-sewf {
    pwedictiontwainingexampwe{
      featuwes: n-nyone, òωó
      featuwes_fow_paiwwise_weawning: nyone, ^^;;
      c-compact_featuwes: n-nyone, rawr
      compwessed_data_wecowd: s-some(vec::new()), ^•ﻌ•^
    }
  }
}

//
// p-pwedictionsewvice sewvice c-cwient
//

pub twait tpwedictionsewvicesynccwient {
  f-fn get_pwediction(&mut sewf, nyaa~~ wequest: pwedictionwequest) -> t-thwift::wesuwt<pwedictionwesponse>;
  f-fn get_batch_pwediction(&mut sewf, nyaa~~ batch_wequest: batchpwedictionwequest) -> thwift::wesuwt<batchpwedictionwesponse>;
}

pub twait tpwedictionsewvicesynccwientmawkew {}

p-pub stwuct pwedictionsewvicesynccwient<ip, 😳😳😳 op> w-whewe ip: tinputpwotocow, 😳😳😳 op: toutputpwotocow {
  _i_pwot: ip, σωσ
  _o_pwot: o-op, o.O
  _sequence_numbew: i32, σωσ
}

impw <ip, nyaa~~ o-op> pwedictionsewvicesynccwient<ip, rawr x3 o-op> whewe ip: tinputpwotocow, (///ˬ///✿) op: toutputpwotocow {
  pub fn new(input_pwotocow: ip, o.O o-output_pwotocow: op) -> pwedictionsewvicesynccwient<ip, òωó op> {
    p-pwedictionsewvicesynccwient { _i_pwot: input_pwotocow, OwO _o_pwot: o-output_pwotocow, σωσ _sequence_numbew: 0 }
  }
}

i-impw <ip, nyaa~~ op> tthwiftcwient fow p-pwedictionsewvicesynccwient<ip, OwO o-op> whewe ip: tinputpwotocow, ^^ o-op: t-toutputpwotocow {
  f-fn i_pwot_mut(&mut s-sewf) -> &mut dyn tinputpwotocow { &mut sewf._i_pwot }
  fn o_pwot_mut(&mut sewf) -> &mut dyn toutputpwotocow { &mut s-sewf._o_pwot }
  fn s-sequence_numbew(&sewf) -> i-i32 { s-sewf._sequence_numbew }
  f-fn incwement_sequence_numbew(&mut s-sewf) -> i32 { sewf._sequence_numbew += 1; sewf._sequence_numbew }
}

impw <ip, (///ˬ///✿) op> tpwedictionsewvicesynccwientmawkew f-fow pwedictionsewvicesynccwient<ip, σωσ o-op> whewe ip: tinputpwotocow, rawr x3 op: toutputpwotocow {}

impw <c: tthwiftcwient + t-tpwedictionsewvicesynccwientmawkew> t-tpwedictionsewvicesynccwient f-fow c {
  fn get_pwediction(&mut sewf, (ˆ ﻌ ˆ)♡ w-wequest: pwedictionwequest) -> thwift::wesuwt<pwedictionwesponse> {
    (
      {
        sewf.incwement_sequence_numbew();
        wet message_ident = t-tmessageidentifiew::new("getpwediction", 🥺 t-tmessagetype::caww, (⑅˘꒳˘) sewf.sequence_numbew());
        wet caww_awgs = p-pwedictionsewvicegetpwedictionawgs { wequest };
        s-sewf.o_pwot_mut().wwite_message_begin(&message_ident)?;
        c-caww_awgs.wwite_to_out_pwotocow(sewf.o_pwot_mut())?;
        sewf.o_pwot_mut().wwite_message_end()?;
        s-sewf.o_pwot_mut().fwush()
      }
    )?;
    {
      w-wet message_ident = s-sewf.i_pwot_mut().wead_message_begin()?;
      v-vewify_expected_sequence_numbew(sewf.sequence_numbew(), 😳😳😳 m-message_ident.sequence_numbew)?;
      v-vewify_expected_sewvice_caww("getpwediction", /(^•ω•^) &message_ident.name)?;
      if m-message_ident.message_type == tmessagetype::exception {
        w-wet wemote_ewwow = thwift::ewwow::wead_appwication_ewwow_fwom_in_pwotocow(sewf.i_pwot_mut())?;
        s-sewf.i_pwot_mut().wead_message_end()?;
        wetuwn eww(thwift::ewwow::appwication(wemote_ewwow))
      }
      vewify_expected_message_type(tmessagetype::wepwy, >w< m-message_ident.message_type)?;
      wet wesuwt = pwedictionsewvicegetpwedictionwesuwt::wead_fwom_in_pwotocow(sewf.i_pwot_mut())?;
      s-sewf.i_pwot_mut().wead_message_end()?;
      wesuwt.ok_ow()
    }
  }
  f-fn get_batch_pwediction(&mut s-sewf, ^•ﻌ•^ batch_wequest: batchpwedictionwequest) -> thwift::wesuwt<batchpwedictionwesponse> {
    (
      {
        s-sewf.incwement_sequence_numbew();
        wet message_ident = tmessageidentifiew::new("getbatchpwediction", 😳😳😳 t-tmessagetype::caww, :3 s-sewf.sequence_numbew());
        wet caww_awgs = pwedictionsewvicegetbatchpwedictionawgs { b-batch_wequest };
        s-sewf.o_pwot_mut().wwite_message_begin(&message_ident)?;
        caww_awgs.wwite_to_out_pwotocow(sewf.o_pwot_mut())?;
        s-sewf.o_pwot_mut().wwite_message_end()?;
        sewf.o_pwot_mut().fwush()
      }
    )?;
    {
      wet message_ident = s-sewf.i_pwot_mut().wead_message_begin()?;
      v-vewify_expected_sequence_numbew(sewf.sequence_numbew(), (ꈍᴗꈍ) message_ident.sequence_numbew)?;
      v-vewify_expected_sewvice_caww("getbatchpwediction", ^•ﻌ•^ &message_ident.name)?;
      i-if message_ident.message_type == tmessagetype::exception {
        wet wemote_ewwow = t-thwift::ewwow::wead_appwication_ewwow_fwom_in_pwotocow(sewf.i_pwot_mut())?;
        s-sewf.i_pwot_mut().wead_message_end()?;
        w-wetuwn e-eww(thwift::ewwow::appwication(wemote_ewwow))
      }
      vewify_expected_message_type(tmessagetype::wepwy, >w< message_ident.message_type)?;
      wet wesuwt = pwedictionsewvicegetbatchpwedictionwesuwt::wead_fwom_in_pwotocow(sewf.i_pwot_mut())?;
      sewf.i_pwot_mut().wead_message_end()?;
      wesuwt.ok_ow()
    }
  }
}

//
// p-pwedictionsewvice s-sewvice p-pwocessow
//

p-pub twait pwedictionsewvicesynchandwew {
  fn h-handwe_get_pwediction(&sewf, ^^;; wequest: p-pwedictionwequest) -> thwift::wesuwt<pwedictionwesponse>;
  f-fn handwe_get_batch_pwediction(&sewf, (✿oωo) b-batch_wequest: batchpwedictionwequest) -> t-thwift::wesuwt<batchpwedictionwesponse>;
}

p-pub stwuct pwedictionsewvicesyncpwocessow<h: pwedictionsewvicesynchandwew> {
  handwew: h, òωó
}

impw <h: p-pwedictionsewvicesynchandwew> pwedictionsewvicesyncpwocessow<h> {
  pub fn n-nyew(handwew: h) -> pwedictionsewvicesyncpwocessow<h> {
    p-pwedictionsewvicesyncpwocessow {
      h-handwew, ^^
    }
  }
  fn pwocess_get_pwediction(&sewf, ^^ i-incoming_sequence_numbew: i-i32, rawr i_pwot: &mut d-dyn tinputpwotocow, XD o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    tpwedictionsewvicepwocessfunctions::pwocess_get_pwediction(&sewf.handwew, rawr i-incoming_sequence_numbew, 😳 i_pwot, 🥺 o-o_pwot)
  }
  f-fn pwocess_get_batch_pwediction(&sewf, (U ᵕ U❁) i-incoming_sequence_numbew: i32, 😳 i_pwot: &mut d-dyn tinputpwotocow, 🥺 o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    t-tpwedictionsewvicepwocessfunctions::pwocess_get_batch_pwediction(&sewf.handwew, (///ˬ///✿) incoming_sequence_numbew, mya i_pwot, o_pwot)
  }
}

pub stwuct tpwedictionsewvicepwocessfunctions;

impw tpwedictionsewvicepwocessfunctions {
  pub fn pwocess_get_pwediction<h: p-pwedictionsewvicesynchandwew>(handwew: &h, (✿oωo) incoming_sequence_numbew: i32, ^•ﻌ•^ i_pwot: &mut dyn tinputpwotocow, o.O o_pwot: &mut dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet awgs = pwedictionsewvicegetpwedictionawgs::wead_fwom_in_pwotocow(i_pwot)?;
    match handwew.handwe_get_pwediction(awgs.wequest) {
      o-ok(handwew_wetuwn) => {
        wet m-message_ident = tmessageidentifiew::new("getpwediction", o.O tmessagetype::wepwy, XD i-incoming_sequence_numbew);
        o_pwot.wwite_message_begin(&message_ident)?;
        w-wet wet = pwedictionsewvicegetpwedictionwesuwt { w-wesuwt_vawue: s-some(handwew_wetuwn), ^•ﻌ•^ pwediction_sewvice_exception: none };
        w-wet.wwite_to_out_pwotocow(o_pwot)?;
        o_pwot.wwite_message_end()?;
        o_pwot.fwush()
      }, ʘwʘ
      eww(e) => {
        m-match e {
          t-thwift::ewwow::usew(usw_eww) => {
            if u-usw_eww.downcast_wef::<pwedictionsewviceexception>().is_some() {
              wet eww = usw_eww.downcast::<pwedictionsewviceexception>().expect("downcast a-awweady c-checked");
              wet wet_eww = pwedictionsewvicegetpwedictionwesuwt{ w-wesuwt_vawue: nyone, (U ﹏ U) pwediction_sewvice_exception: some(*eww) };
              w-wet message_ident = tmessageidentifiew::new("getpwediction", 😳😳😳 tmessagetype::wepwy, 🥺 incoming_sequence_numbew);
              o_pwot.wwite_message_begin(&message_ident)?;
              w-wet_eww.wwite_to_out_pwotocow(o_pwot)?;
              o-o_pwot.wwite_message_end()?;
              o_pwot.fwush()
            } e-ewse {
              w-wet wet_eww = {
                appwicationewwow::new(
                  a-appwicationewwowkind::unknown, (///ˬ///✿)
                  usw_eww.to_stwing()
                )
              };
              wet message_ident = tmessageidentifiew::new("getpwediction", (˘ω˘) tmessagetype::exception, :3 i-incoming_sequence_numbew);
              o-o_pwot.wwite_message_begin(&message_ident)?;
              thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&wet_eww, /(^•ω•^) o-o_pwot)?;
              o-o_pwot.wwite_message_end()?;
              o_pwot.fwush()
            }
          }, :3
          t-thwift::ewwow::appwication(app_eww) => {
            wet message_ident = tmessageidentifiew::new("getpwediction", mya t-tmessagetype::exception, XD incoming_sequence_numbew);
            o_pwot.wwite_message_begin(&message_ident)?;
            t-thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&app_eww, (///ˬ///✿) o-o_pwot)?;
            o_pwot.wwite_message_end()?;
            o_pwot.fwush()
          }, 🥺
          _ => {
            w-wet wet_eww = {
              appwicationewwow::new(
                appwicationewwowkind::unknown, o.O
                e.to_stwing()
              )
            };
            wet message_ident = tmessageidentifiew::new("getpwediction", mya tmessagetype::exception, rawr x3 incoming_sequence_numbew);
            o-o_pwot.wwite_message_begin(&message_ident)?;
            t-thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&wet_eww, 😳 o_pwot)?;
            o-o_pwot.wwite_message_end()?;
            o-o_pwot.fwush()
          }, 😳😳😳
        }
      }, >_<
    }
  }
  pub f-fn pwocess_get_batch_pwediction<h: pwedictionsewvicesynchandwew>(handwew: &h, >w< incoming_sequence_numbew: i32, rawr x3 i_pwot: &mut dyn tinputpwotocow, XD o_pwot: &mut d-dyn toutputpwotocow) -> thwift::wesuwt<()> {
    wet awgs = pwedictionsewvicegetbatchpwedictionawgs::wead_fwom_in_pwotocow(i_pwot)?;
    match handwew.handwe_get_batch_pwediction(awgs.batch_wequest) {
      o-ok(handwew_wetuwn) => {
        w-wet message_ident = t-tmessageidentifiew::new("getbatchpwediction", tmessagetype::wepwy, ^^ incoming_sequence_numbew);
        o_pwot.wwite_message_begin(&message_ident)?;
        w-wet wet = p-pwedictionsewvicegetbatchpwedictionwesuwt { wesuwt_vawue: s-some(handwew_wetuwn), (✿oωo) pwediction_sewvice_exception: n-nyone };
        wet.wwite_to_out_pwotocow(o_pwot)?;
        o-o_pwot.wwite_message_end()?;
        o_pwot.fwush()
      }, >w<
      e-eww(e) => {
        match e {
          t-thwift::ewwow::usew(usw_eww) => {
            if usw_eww.downcast_wef::<pwedictionsewviceexception>().is_some() {
              wet eww = u-usw_eww.downcast::<pwedictionsewviceexception>().expect("downcast awweady checked");
              w-wet wet_eww = p-pwedictionsewvicegetbatchpwedictionwesuwt{ wesuwt_vawue: nyone, 😳😳😳 p-pwediction_sewvice_exception: s-some(*eww) };
              wet m-message_ident = tmessageidentifiew::new("getbatchpwediction", (ꈍᴗꈍ) t-tmessagetype::wepwy, (✿oωo) incoming_sequence_numbew);
              o-o_pwot.wwite_message_begin(&message_ident)?;
              w-wet_eww.wwite_to_out_pwotocow(o_pwot)?;
              o_pwot.wwite_message_end()?;
              o_pwot.fwush()
            } ewse {
              w-wet wet_eww = {
                appwicationewwow::new(
                  appwicationewwowkind::unknown, (˘ω˘)
                  usw_eww.to_stwing()
                )
              };
              wet message_ident = tmessageidentifiew::new("getbatchpwediction", nyaa~~ tmessagetype::exception, ( ͡o ω ͡o ) incoming_sequence_numbew);
              o_pwot.wwite_message_begin(&message_ident)?;
              t-thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&wet_eww, 🥺 o_pwot)?;
              o_pwot.wwite_message_end()?;
              o-o_pwot.fwush()
            }
          }, (U ﹏ U)
          thwift::ewwow::appwication(app_eww) => {
            w-wet message_ident = tmessageidentifiew::new("getbatchpwediction", ( ͡o ω ͡o ) tmessagetype::exception, (///ˬ///✿) i-incoming_sequence_numbew);
            o_pwot.wwite_message_begin(&message_ident)?;
            thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&app_eww, (///ˬ///✿) o-o_pwot)?;
            o_pwot.wwite_message_end()?;
            o_pwot.fwush()
          }, (✿oωo)
          _ => {
            w-wet wet_eww = {
              appwicationewwow::new(
                appwicationewwowkind::unknown, (U ᵕ U❁)
                e.to_stwing()
              )
            };
            w-wet message_ident = tmessageidentifiew::new("getbatchpwediction", ʘwʘ tmessagetype::exception, ʘwʘ i-incoming_sequence_numbew);
            o-o_pwot.wwite_message_begin(&message_ident)?;
            thwift::ewwow::wwite_appwication_ewwow_to_out_pwotocow(&wet_eww, XD o_pwot)?;
            o-o_pwot.wwite_message_end()?;
            o-o_pwot.fwush()
          }, (✿oωo)
        }
      }, ^•ﻌ•^
    }
  }
}

impw <h: pwedictionsewvicesynchandwew> t-tpwocessow f-fow pwedictionsewvicesyncpwocessow<h> {
  fn pwocess(&sewf, ^•ﻌ•^ i_pwot: &mut d-dyn tinputpwotocow, o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet message_ident = i_pwot.wead_message_begin()?;
    wet wes = match &*message_ident.name {
      "getpwediction" => {
        s-sewf.pwocess_get_pwediction(message_ident.sequence_numbew, >_< i-i_pwot, mya o_pwot)
      }, σωσ
      "getbatchpwediction" => {
        s-sewf.pwocess_get_batch_pwediction(message_ident.sequence_numbew, rawr i_pwot, o_pwot)
      }, (✿oωo)
      method => {
        e-eww(
          thwift::ewwow::appwication(
            a-appwicationewwow::new(
              appwicationewwowkind::unknownmethod, :3
              f-fowmat!("unknown m-method {}", rawr x3 method)
            )
          )
        )
      }, ^^
    };
    thwift::sewvew::handwe_pwocess_wesuwt(&message_ident, wes, ^^ o_pwot)
  }
}

//
// pwedictionsewvicegetpwedictionawgs
//

#[dewive(cwone, OwO debug, e-eq, hash, ʘwʘ o-owd, pawtiaweq, /(^•ω•^) pawtiawowd)]
stwuct pwedictionsewvicegetpwedictionawgs {
  w-wequest: pwedictionwequest, ʘwʘ
}

impw pwedictionsewvicegetpwedictionawgs {
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn t-tinputpwotocow) -> t-thwift::wesuwt<pwedictionsewvicegetpwedictionawgs> {
    i-i_pwot.wead_stwuct_begin()?;
    w-wet mut f_1: option<pwedictionwequest> = nyone;
    w-woop {
      wet f-fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      i-if fiewd_ident.fiewd_type == ttype::stop {
        b-bweak;
      }
      w-wet fiewd_id = fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        1 => {
          w-wet vaw = pwedictionwequest::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_1 = some(vaw);
        }, (⑅˘꒳˘)
        _ => {
          i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, UwU
      };
      i_pwot.wead_fiewd_end()?;
    }
    i-i_pwot.wead_stwuct_end()?;
    vewify_wequiwed_fiewd_exists("pwedictionsewvicegetpwedictionawgs.wequest", -.- &f_1)?;
    wet wet = p-pwedictionsewvicegetpwedictionawgs {
      wequest: f_1.expect("auto-genewated c-code shouwd have c-checked fow pwesence of wequiwed fiewds"), :3
    };
    ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, >_< o-o_pwot: &mut dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("getpwediction_awgs");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("wequest", nyaa~~ t-ttype::stwuct, ( ͡o ω ͡o ) 1))?;
    s-sewf.wequest.wwite_to_out_pwotocow(o_pwot)?;
    o_pwot.wwite_fiewd_end()?;
    o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// p-pwedictionsewvicegetpwedictionwesuwt
//

#[dewive(cwone, o.O debug, :3 eq, hash, owd, p-pawtiaweq, (˘ω˘) pawtiawowd)]
stwuct pwedictionsewvicegetpwedictionwesuwt {
  wesuwt_vawue: option<pwedictionwesponse>, rawr x3
  pwediction_sewvice_exception: o-option<pwedictionsewviceexception>, (U ᵕ U❁)
}

impw pwedictionsewvicegetpwedictionwesuwt {
  f-fn ok_ow(sewf) -> t-thwift::wesuwt<pwedictionwesponse> {
    i-if sewf.pwediction_sewvice_exception.is_some() {
      eww(thwift::ewwow::usew(box::new(sewf.pwediction_sewvice_exception.unwwap())))
    } e-ewse if sewf.wesuwt_vawue.is_some() {
      o-ok(sewf.wesuwt_vawue.unwwap())
    } e-ewse {
      eww(
        t-thwift::ewwow::appwication(
          a-appwicationewwow::new(
            appwicationewwowkind::missingwesuwt, 🥺
            "no wesuwt w-weceived fow pwedictionsewvicegetpwediction"
          )
        )
      )
    }
  }
  f-fn wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> t-thwift::wesuwt<pwedictionsewvicegetpwedictionwesuwt> {
    i_pwot.wead_stwuct_begin()?;
    w-wet mut f_0: option<pwedictionwesponse> = n-nyone;
    wet mut f_1: o-option<pwedictionsewviceexception> = n-nyone;
    w-woop {
      wet f-fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == t-ttype::stop {
        bweak;
      }
      w-wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        0 => {
          wet vaw = pwedictionwesponse::wead_fwom_in_pwotocow(i_pwot)?;
          f_0 = s-some(vaw);
        }, >_<
        1 => {
          w-wet vaw = pwedictionsewviceexception::wead_fwom_in_pwotocow(i_pwot)?;
          f_1 = some(vaw);
        }, :3
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, :3
      };
      i-i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    wet wet = p-pwedictionsewvicegetpwedictionwesuwt {
      wesuwt_vawue: f-f_0, (ꈍᴗꈍ)
      p-pwediction_sewvice_exception: f-f_1, σωσ
    };
    o-ok(wet)
  }
  f-fn wwite_to_out_pwotocow(&sewf, 😳 o_pwot: &mut dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    wet stwuct_ident = tstwuctidentifiew::new("pwedictionsewvicegetpwedictionwesuwt");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    if wet some(wef f-fwd_vaw) = s-sewf.wesuwt_vawue {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("wesuwt_vawue", mya ttype::stwuct, (///ˬ///✿) 0))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    i-if wet some(wef fwd_vaw) = sewf.pwediction_sewvice_exception {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("pwedictionsewviceexception", ^^ t-ttype::stwuct, (✿oωo) 1))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o-o_pwot.wwite_fiewd_end()?
    }
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// pwedictionsewvicegetbatchpwedictionawgs
//

#[dewive(cwone, ( ͡o ω ͡o ) debug, eq, hash, ^^;; owd, p-pawtiaweq, :3 pawtiawowd)]
stwuct p-pwedictionsewvicegetbatchpwedictionawgs {
  batch_wequest: b-batchpwedictionwequest, 😳
}

impw pwedictionsewvicegetbatchpwedictionawgs {
  fn wead_fwom_in_pwotocow(i_pwot: &mut dyn t-tinputpwotocow) -> thwift::wesuwt<pwedictionsewvicegetbatchpwedictionawgs> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_1: option<batchpwedictionwequest> = n-nyone;
    woop {
      wet fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      wet fiewd_id = fiewd_id(&fiewd_ident)?;
      match fiewd_id {
        1 => {
          wet v-vaw = batchpwedictionwequest::wead_fwom_in_pwotocow(i_pwot)?;
          f-f_1 = s-some(vaw);
        }, XD
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, (///ˬ///✿)
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    v-vewify_wequiwed_fiewd_exists("pwedictionsewvicegetbatchpwedictionawgs.batch_wequest", o.O &f_1)?;
    wet wet = pwedictionsewvicegetbatchpwedictionawgs {
      batch_wequest: f_1.expect("auto-genewated code shouwd h-have checked f-fow pwesence of w-wequiwed fiewds"), o.O
    };
    o-ok(wet)
  }
  fn wwite_to_out_pwotocow(&sewf, XD o_pwot: &mut dyn toutputpwotocow) -> thwift::wesuwt<()> {
    w-wet s-stwuct_ident = tstwuctidentifiew::new("getbatchpwediction_awgs");
    o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("batchwequest", ^^;; ttype::stwuct, 😳😳😳 1))?;
    s-sewf.batch_wequest.wwite_to_out_pwotocow(o_pwot)?;
    o_pwot.wwite_fiewd_end()?;
    o-o_pwot.wwite_fiewd_stop()?;
    o-o_pwot.wwite_stwuct_end()
  }
}

//
// p-pwedictionsewvicegetbatchpwedictionwesuwt
//

#[dewive(cwone, (U ᵕ U❁) debug, eq, /(^•ω•^) hash, owd, pawtiaweq, 😳😳😳 pawtiawowd)]
stwuct pwedictionsewvicegetbatchpwedictionwesuwt {
  wesuwt_vawue: o-option<batchpwedictionwesponse>, rawr x3
  pwediction_sewvice_exception: o-option<pwedictionsewviceexception>, ʘwʘ
}

impw pwedictionsewvicegetbatchpwedictionwesuwt {
  fn ok_ow(sewf) -> thwift::wesuwt<batchpwedictionwesponse> {
    i-if sewf.pwediction_sewvice_exception.is_some() {
      eww(thwift::ewwow::usew(box::new(sewf.pwediction_sewvice_exception.unwwap())))
    } e-ewse if sewf.wesuwt_vawue.is_some() {
      ok(sewf.wesuwt_vawue.unwwap())
    } ewse {
      eww(
        t-thwift::ewwow::appwication(
          a-appwicationewwow::new(
            a-appwicationewwowkind::missingwesuwt, UwU
            "no w-wesuwt w-weceived fow pwedictionsewvicegetbatchpwediction"
          )
        )
      )
    }
  }
  fn w-wead_fwom_in_pwotocow(i_pwot: &mut d-dyn tinputpwotocow) -> thwift::wesuwt<pwedictionsewvicegetbatchpwedictionwesuwt> {
    i-i_pwot.wead_stwuct_begin()?;
    wet mut f_0: option<batchpwedictionwesponse> = n-nyone;
    wet mut f_1: o-option<pwedictionsewviceexception> = n-nyone;
    woop {
      wet f-fiewd_ident = i-i_pwot.wead_fiewd_begin()?;
      if fiewd_ident.fiewd_type == ttype::stop {
        bweak;
      }
      w-wet fiewd_id = f-fiewd_id(&fiewd_ident)?;
      m-match fiewd_id {
        0 => {
          w-wet vaw = batchpwedictionwesponse::wead_fwom_in_pwotocow(i_pwot)?;
          f_0 = some(vaw);
        }, (⑅˘꒳˘)
        1 => {
          wet vaw = pwedictionsewviceexception::wead_fwom_in_pwotocow(i_pwot)?;
          f_1 = some(vaw);
        }, ^^
        _ => {
          i-i_pwot.skip(fiewd_ident.fiewd_type)?;
        }, 😳😳😳
      };
      i_pwot.wead_fiewd_end()?;
    }
    i_pwot.wead_stwuct_end()?;
    w-wet wet = pwedictionsewvicegetbatchpwedictionwesuwt {
      wesuwt_vawue: f-f_0, òωó
      pwediction_sewvice_exception: f_1, ^^;;
    };
    ok(wet)
  }
  fn w-wwite_to_out_pwotocow(&sewf, o_pwot: &mut d-dyn toutputpwotocow) -> t-thwift::wesuwt<()> {
    w-wet stwuct_ident = tstwuctidentifiew::new("pwedictionsewvicegetbatchpwedictionwesuwt");
    o-o_pwot.wwite_stwuct_begin(&stwuct_ident)?;
    i-if wet some(wef fwd_vaw) = s-sewf.wesuwt_vawue {
      o-o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("wesuwt_vawue", (✿oωo) t-ttype::stwuct, rawr 0))?;
      f-fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    i-if wet s-some(wef fwd_vaw) = s-sewf.pwediction_sewvice_exception {
      o_pwot.wwite_fiewd_begin(&tfiewdidentifiew::new("pwedictionsewviceexception", XD t-ttype::stwuct, 😳 1))?;
      fwd_vaw.wwite_to_out_pwotocow(o_pwot)?;
      o_pwot.wwite_fiewd_end()?
    }
    o_pwot.wwite_fiewd_stop()?;
    o_pwot.wwite_stwuct_end()
  }
}

