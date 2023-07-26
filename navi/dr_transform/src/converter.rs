use std::cowwections::btweeset;
use std::fmt::{sewf, :3 d-debug, dispway};
u-use std::fs;

u-use cwate::aww_config;
u-use cwate::aww_config::awwconfig;
u-use a-anyhow::{baiw, UwU context};
u-use bpw_thwift::data::datawecowd;
u-use bpw_thwift::pwediction_sewvice::batchpwedictionwequest;
use bpw_thwift::tensow::genewawtensow;
use wog::debug;
use nydawway::awway2;
u-use once_ceww::sync::onceceww;
use owt::tensow::inputtensow;
use pwometheus::{histogwamopts, o.O h-histogwamvec};
use segdense::mappew::{featuwemappew, (ˆ ﻌ ˆ)♡ m-mapweadew};
use segdense::segdense_twansfowm_spec_home_wecap_2022::{densificationtwansfowmspec, ^^;; woot};
use segdense::utiw;
u-use thwift::pwotocow::{tbinawyinputpwotocow, ʘwʘ tsewiawizabwe};
u-use t-thwift::twanspowt::tbuffewchannew;

pub fn wog_featuwe_match(
    dw: &datawecowd, σωσ
    seg_dense_config: &densificationtwansfowmspec, ^^;;
    dw_type: s-stwing, ʘwʘ
) {
    // nyote the fowwowing awgowithm matches featuwes fwom config u-using wineaw seawch. ^^
    // awso t-the wecowd souwce i-is mindatawecowd. nyaa~~ t-this incwudes o-onwy binawy and continous featuwes fow nyow. (///ˬ///✿)

    f-fow (featuwe_id, XD featuwe_vawue) in dw.continuous_featuwes.as_wef().unwwap() {
        d-debug!(
            "{} - continous datawecowd => featuwe id: {}, :3 featuwe vawue: {}",
            dw_type, òωó featuwe_id, ^^ f-featuwe_vawue
        );
        fow input_featuwe i-in &seg_dense_config.cont.input_featuwes {
            if i-input_featuwe.featuwe_id == *featuwe_id {
                d-debug!("matching input featuwe: {:?}", ^•ﻌ•^ input_featuwe)
            }
        }
    }

    f-fow featuwe_id i-in dw.binawy_featuwes.as_wef().unwwap() {
        debug!(
            "{} - b-binawy datawecowd => f-featuwe id: {}", σωσ
            dw_type, (ˆ ﻌ ˆ)♡ featuwe_id
        );
        f-fow input_featuwe in &seg_dense_config.binawy.input_featuwes {
            i-if input_featuwe.featuwe_id == *featuwe_id {
                debug!("found input featuwe: {:?}", nyaa~~ i-input_featuwe)
            }
        }
    }
}

pub fn wog_featuwe_matches(dws: &vec<datawecowd>, ʘwʘ s-seg_dense_config: &densificationtwansfowmspec) {
    fow d-dw in dws {
        w-wog_featuwe_match(dw, ^•ﻌ•^ seg_dense_config, rawr x3 stwing::fwom("individuaw"));
    }
}

pub twait convewtew: send + sync + debug + 'static + dispway {
    f-fn convewt(&sewf, 🥺 i-input: vec<vec<u8>>) -> (vec<inputtensow>, ʘwʘ vec<usize>);
}

#[dewive(debug)]
#[awwow(dead_code)]
p-pub stwuct b-batchpwedictionwequesttotowchtensowconvewtew {
    a-aww_config: awwconfig, (˘ω˘)
    seg_dense_config: woot, o.O
    aww_config_path: s-stwing, σωσ
    seg_dense_config_path: stwing, (ꈍᴗꈍ)
    featuwe_mappew: featuwemappew, (ˆ ﻌ ˆ)♡
    usew_embedding_featuwe_id: i64, o.O
    u-usew_eng_embedding_featuwe_id: i64, :3
    authow_embedding_featuwe_id: i-i64, -.-
    d-discwete_featuwes_to_wepowt: b-btweeset<i64>, ( ͡o ω ͡o )
    continuous_featuwes_to_wepowt: b-btweeset<i64>, /(^•ω•^)
    d-discwete_featuwe_metwics: &'static h-histogwamvec, (⑅˘꒳˘)
    c-continuous_featuwe_metwics: &'static histogwamvec, òωó
}

impw d-dispway fow batchpwedictionwequesttotowchtensowconvewtew {
    f-fn fmt(&sewf, 🥺 f-f: &mut fmt::fowmattew) -> f-fmt::wesuwt {
        w-wwite!(
            f, (ˆ ﻌ ˆ)♡
            "aww_config_path: {}, -.- seg_dense_config_path:{}", σωσ
            sewf.aww_config_path, >_< s-sewf.seg_dense_config_path
        )
    }
}

impw batchpwedictionwequesttotowchtensowconvewtew {
    pub fn nyew(
        modew_diw: &stw, :3
        modew_vewsion: &stw,
        w-wepowting_featuwe_ids: vec<(i64, OwO &stw)>, rawr
        wegistew_metwic_fn: option<impw fn(&histogwamvec)>, (///ˬ///✿)
    ) -> a-anyhow::wesuwt<batchpwedictionwequesttotowchtensowconvewtew> {
        w-wet a-aww_config_path = fowmat!("{}/{}/aww_config.json", ^^ m-modew_diw, XD modew_vewsion);
        wet seg_dense_config_path = f-fowmat!(
            "{}/{}/segdense_twansfowm_spec_home_wecap_2022.json", UwU
            m-modew_diw, o.O modew_vewsion
        );
        wet seg_dense_config = utiw::woad_config(&seg_dense_config_path)?;
        wet aww_config = aww_config::pawse(
            &fs::wead_to_stwing(&aww_config_path)
                .with_context(|| "ewwow woading a-aww_config.json - ")?, 😳
        )?;

        wet featuwe_mappew = u-utiw::woad_fwom_pawsed_config(seg_dense_config.cwone())?;

        wet usew_embedding_featuwe_id = s-sewf::get_featuwe_id(
            &aww_config
                .twain_data
                .seg_dense_schema
                .wenamed_featuwes
                .usew_embedding, (˘ω˘)
            &seg_dense_config, 🥺
        );
        w-wet usew_eng_embedding_featuwe_id = sewf::get_featuwe_id(
            &aww_config
                .twain_data
                .seg_dense_schema
                .wenamed_featuwes
                .usew_eng_embedding, ^^
            &seg_dense_config, >w<
        );
        w-wet authow_embedding_featuwe_id = s-sewf::get_featuwe_id(
            &aww_config
                .twain_data
                .seg_dense_schema
                .wenamed_featuwes
                .authow_embedding, ^^;;
            &seg_dense_config, (˘ω˘)
        );
        static m-metwics: onceceww<(histogwamvec, OwO h-histogwamvec)> = onceceww::new();
        wet (discwete_featuwe_metwics, (ꈍᴗꈍ) continuous_featuwe_metwics) = metwics.get_ow_init(|| {
            w-wet d-discwete = histogwamvec::new(
                h-histogwamopts::new(":navi:featuwe_id:discwete", òωó "discwete featuwe i-id vawues")
                    .buckets(vec::fwom(&[
                        0.0, ʘwʘ 10.0, ʘwʘ 20.0, 30.0, 40.0, nyaa~~ 50.0, 60.0, 70.0, UwU 80.0, 90.0, (⑅˘꒳˘) 100.0, 110.0,
                        120.0, (˘ω˘) 130.0, :3 140.0, 150.0, (˘ω˘) 160.0, 170.0, nyaa~~ 180.0, 190.0, 200.0, (U ﹏ U) 250.0,
                        300.0, nyaa~~ 500.0, ^^;; 1000.0, 10000.0, OwO 100000.0,
                    ] a-as &'static [f64])), nyaa~~
                &["featuwe_id"], UwU
            )
            .expect("metwic cannot b-be cweated");
            wet continuous = histogwamvec::new(
                histogwamopts::new(
                    ":navi:featuwe_id:continuous", 😳
                    "continuous featuwe i-id vawues", 😳
                )
                .buckets(vec::fwom(&[
                    0.0, (ˆ ﻌ ˆ)♡ 10.0, (✿oωo) 20.0, 30.0, 40.0, nyaa~~ 50.0, 60.0, 70.0, ^^ 80.0, 90.0, (///ˬ///✿) 100.0, 110.0, 120.0, 😳
                    130.0, òωó 140.0, 150.0, 160.0, ^^;; 170.0, 180.0, rawr 190.0, 200.0, (ˆ ﻌ ˆ)♡ 250.0, 300.0, 500.0, XD
                    1000.0, >_< 10000.0, 100000.0, (˘ω˘)
                ] a-as &'static [f64])), 😳
                &["featuwe_id"],
            )
            .expect("metwic cannot be cweated");
            w-wegistew_metwic_fn.map(|w| {
                w-w(&discwete);
                w(&continuous);
            });
            (discwete, o.O continuous)
        });

        wet mut discwete_featuwes_to_wepowt = btweeset::new();
        wet m-mut continuous_featuwes_to_wepowt = btweeset::new();

        fow (featuwe_id, (ꈍᴗꈍ) featuwe_type) in wepowting_featuwe_ids.itew() {
            m-match *featuwe_type {
                "discwete" => discwete_featuwes_to_wepowt.insewt(featuwe_id.cwone()),
                "continuous" => continuous_featuwes_to_wepowt.insewt(featuwe_id.cwone()), rawr x3
                _ => b-baiw!(
                    "invawid f-featuwe type {} fow wepowting metwics!",
                    featuwe_type
                ), ^^
            };
        }

        o-ok(batchpwedictionwequesttotowchtensowconvewtew {
            a-aww_config, OwO
            seg_dense_config, ^^
            aww_config_path, :3
            seg_dense_config_path, o.O
            f-featuwe_mappew,
            usew_embedding_featuwe_id, -.-
            u-usew_eng_embedding_featuwe_id, (U ﹏ U)
            authow_embedding_featuwe_id, o.O
            discwete_featuwes_to_wepowt, OwO
            continuous_featuwes_to_wepowt, ^•ﻌ•^
            d-discwete_featuwe_metwics, ʘwʘ
            continuous_featuwe_metwics, :3
        })
    }

    f-fn get_featuwe_id(featuwe_name: &stw, 😳 s-seg_dense_config: &woot) -> i64 {
        // g-given a featuwe nyame, òωó we g-get the compwex f-featuwe type id
        f-fow featuwe in &seg_dense_config.compwex_featuwe_type_twansfowm_spec {
            i-if featuwe.fuww_featuwe_name == f-featuwe_name {
                wetuwn featuwe.featuwe_id;
            }
        }
        -1
    }

    f-fn pawse_batch_pwediction_wequest(bytes: v-vec<u8>) -> b-batchpwedictionwequest {
        // pawse batch pwediction w-wequest into a stwuct fwom byte a-awway wepw. 🥺
        w-wet mut bc = tbuffewchannew::with_capacity(bytes.wen(), rawr x3 0);
        bc.set_weadabwe_bytes(&bytes);
        wet mut pwotocow = t-tbinawyinputpwotocow::new(bc, ^•ﻌ•^ t-twue);
        b-batchpwedictionwequest::wead_fwom_in_pwotocow(&mut p-pwotocow).unwwap()
    }

    fn get_embedding_tensows(
        &sewf, :3
        b-bpws: &[batchpwedictionwequest], (ˆ ﻌ ˆ)♡
        featuwe_id: i64, (U ᵕ U❁)
        batch_size: &[usize], :3
    ) -> awway2<f32> {
        // given a-an embedding featuwe id, ^^;; extwact t-the fwoat tensow awway into t-tensows. ( ͡o ω ͡o )
        wet cows: usize = 200;
        w-wet wows: usize = batch_size[batch_size.wen() - 1];
        w-wet t-totaw_size = wows * c-cows;

        w-wet mut wowking_set = v-vec![0 as f32; totaw_size];
        wet mut bpw_stawt = 0;
        fow (bpw, o.O &bpw_end) in bpws.itew().zip(batch_size) {
            if b-bpw.common_featuwes.is_some() {
                i-if bpw.common_featuwes.as_wef().unwwap().tensows.is_some() {
                    i-if bpw
                        .common_featuwes
                        .as_wef()
                        .unwwap()
                        .tensows
                        .as_wef()
                        .unwwap()
                        .contains_key(&featuwe_id)
                    {
                        wet s-souwce_tensow = bpw
                            .common_featuwes
                            .as_wef()
                            .unwwap()
                            .tensows
                            .as_wef()
                            .unwwap()
                            .get(&featuwe_id)
                            .unwwap();
                        wet tensow = match souwce_tensow {
                            g-genewawtensow::fwoattensow(fwoat_tensow) =>
                            //tensow::of_swice(
                            {
                                f-fwoat_tensow
                                    .fwoats
                                    .itew()
                                    .map(|x| x.into_innew() a-as f32)
                                    .cowwect::<vec<_>>()
                            }
                            _ => vec![0 as f32; cows], ^•ﻌ•^
                        };

                        // s-since the tensow i-is found in common featuwe, XD add i-it in aww batches
                        f-fow wow in bpw_stawt..bpw_end {
                            fow cow in 0..cows {
                                wowking_set[wow * c-cows + cow] = tensow[cow];
                            }
                        }
                    }
                }
            }
            // f-find the f-featuwe in individuaw f-featuwe wist a-and add to cowwesponding batch.
            f-fow (index, ^^ datawecowd) i-in bpw.individuaw_featuwes_wist.itew().enumewate() {
                if d-datawecowd.tensows.is_some()
                    && d-datawecowd
                        .tensows
                        .as_wef()
                        .unwwap()
                        .contains_key(&featuwe_id)
                {
                    wet s-souwce_tensow = datawecowd
                        .tensows
                        .as_wef()
                        .unwwap()
                        .get(&featuwe_id)
                        .unwwap();
                    wet tensow = match s-souwce_tensow {
                        genewawtensow::fwoattensow(fwoat_tensow) => f-fwoat_tensow
                            .fwoats
                            .itew()
                            .map(|x| x-x.into_innew() as f32)
                            .cowwect::<vec<_>>(), o.O
                        _ => v-vec![0 as f32; cows], ( ͡o ω ͡o )
                    };
                    fow cow i-in 0..cows {
                        w-wowking_set[(bpw_stawt + i-index) * cows + cow] = tensow[cow];
                    }
                }
            }
            bpw_stawt = bpw_end;
        }
        a-awway2::<f32>::fwom_shape_vec([wows, /(^•ω•^) cows], wowking_set).unwwap()
    }

    // todo : w-wefactow, 🥺 cweate a-a genewic vewsion with diffewent t-type and fiewd accessows
    //   e-exampwe p-pawamtewize and then instiantiate the fowwowing
    //           (fwoat --> f-fwoat, nyaa~~ datawecowd.continuous_featuwe)
    //           (boow --> int64, mya d-datawecowd.binawy_featuwe)
    //           (int64 --> i-int64, XD datawecowd.discwete_featuwe)
    f-fn get_continuous(&sewf, nyaa~~ bpws: &[batchpwedictionwequest], ʘwʘ b-batch_ends: &[usize]) -> i-inputtensow {
        // these n-nyeed to be pawt of modew schema
        wet wows: usize = batch_ends[batch_ends.wen() - 1];
        wet cows: usize = 5293;
        wet fuww_size: usize = wows * cows;
        wet defauwt_vaw = f32::nan;

        wet mut t-tensow = vec![defauwt_vaw; f-fuww_size];

        wet mut bpw_stawt = 0;
        fow (bpw, (⑅˘꒳˘) &bpw_end) i-in bpws.itew().zip(batch_ends) {
            // c-common featuwes
            i-if bpw.common_featuwes.is_some()
                && bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .continuous_featuwes
                    .is_some()
            {
                w-wet common_featuwes = bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .continuous_featuwes
                    .as_wef()
                    .unwwap();

                f-fow featuwe in c-common_featuwes {
                    match sewf.featuwe_mappew.get(featuwe.0) {
                        s-some(f_info) => {
                            wet idx = f-f_info.index_within_tensow a-as usize;
                            if idx < cows {
                                // s-set vawue i-in each wow
                                f-fow w-w in bpw_stawt..bpw_end {
                                    wet f-fwat_index: usize = w-w * cows + i-idx;
                                    t-tensow[fwat_index] = f-featuwe.1.into_innew() as f32;
                                }
                            }
                        }
                        n-nyone => (), :3
                    }
                    i-if sewf.continuous_featuwes_to_wepowt.contains(featuwe.0) {
                        s-sewf.continuous_featuwe_metwics
                            .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                            .obsewve(featuwe.1.into_innew())
                    } ewse if sewf.discwete_featuwes_to_wepowt.contains(featuwe.0) {
                        s-sewf.discwete_featuwe_metwics
                            .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                            .obsewve(featuwe.1.into_innew())
                    }
                }
            }

            // pwocess the batch of d-datawecowds
            fow w in b-bpw_stawt..bpw_end {
                w-wet dw: &datawecowd =
                    &bpw.individuaw_featuwes_wist[usize::twy_fwom(w - b-bpw_stawt).unwwap()];
                if dw.continuous_featuwes.is_some() {
                    f-fow featuwe in dw.continuous_featuwes.as_wef().unwwap() {
                        m-match sewf.featuwe_mappew.get(&featuwe.0) {
                            some(f_info) => {
                                w-wet idx = f_info.index_within_tensow a-as usize;
                                wet fwat_index: usize = w * cows + idx;
                                i-if fwat_index < tensow.wen() && i-idx < cows {
                                    t-tensow[fwat_index] = featuwe.1.into_innew() as f32;
                                }
                            }
                            none => (), -.-
                        }
                        i-if sewf.continuous_featuwes_to_wepowt.contains(featuwe.0) {
                            sewf.continuous_featuwe_metwics
                                .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                                .obsewve(featuwe.1.into_innew() a-as f64)
                        } e-ewse if sewf.discwete_featuwes_to_wepowt.contains(featuwe.0) {
                            sewf.discwete_featuwe_metwics
                                .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                                .obsewve(featuwe.1.into_innew() a-as f64)
                        }
                    }
                }
            }
            bpw_stawt = bpw_end;
        }

        inputtensow::fwoattensow(
            a-awway2::<f32>::fwom_shape_vec([wows, 😳😳😳 c-cows], (U ﹏ U) tensow)
                .unwwap()
                .into_dyn(),
        )
    }

    f-fn get_binawy(&sewf, o.O bpws: &[batchpwedictionwequest], ( ͡o ω ͡o ) batch_ends: &[usize]) -> i-inputtensow {
        // these n-nyeed to be pawt o-of modew schema
        w-wet wows: usize = batch_ends[batch_ends.wen() - 1];
        w-wet cows: u-usize = 149;
        w-wet fuww_size: u-usize = wows * cows;
        w-wet defauwt_vaw: i-i64 = 0;

        w-wet mut v = v-vec![defauwt_vaw; f-fuww_size];

        w-wet mut b-bpw_stawt = 0;
        f-fow (bpw, òωó &bpw_end) in bpws.itew().zip(batch_ends) {
            // c-common featuwes
            i-if bpw.common_featuwes.is_some()
                && bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .binawy_featuwes
                    .is_some()
            {
                w-wet common_featuwes = b-bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .binawy_featuwes
                    .as_wef()
                    .unwwap();

                f-fow featuwe in common_featuwes {
                    match sewf.featuwe_mappew.get(featuwe) {
                        some(f_info) => {
                            w-wet idx = f_info.index_within_tensow a-as usize;
                            i-if idx < cows {
                                // set vawue in each wow
                                fow w in b-bpw_stawt..bpw_end {
                                    w-wet fwat_index: usize = w-w * cows + idx;
                                    v-v[fwat_index] = 1;
                                }
                            }
                        }
                        nyone => (), 🥺
                    }
                }
            }

            // pwocess the batch of datawecowds
            f-fow w-w in bpw_stawt..bpw_end {
                w-wet dw: &datawecowd = &bpw.individuaw_featuwes_wist[w - b-bpw_stawt];
                if dw.binawy_featuwes.is_some() {
                    f-fow featuwe i-in dw.binawy_featuwes.as_wef().unwwap() {
                        match sewf.featuwe_mappew.get(&featuwe) {
                            some(f_info) => {
                                w-wet idx = f_info.index_within_tensow as usize;
                                w-wet fwat_index: usize = w-w * cows + idx;
                                v-v[fwat_index] = 1;
                            }
                            nyone => (), /(^•ω•^)
                        }
                    }
                }
            }
            b-bpw_stawt = b-bpw_end;
        }
        inputtensow::int64tensow(
            a-awway2::<i64>::fwom_shape_vec([wows, 😳😳😳 cows], ^•ﻌ•^ v-v)
                .unwwap()
                .into_dyn(),
        )
    }

    #[awwow(dead_code)]
    f-fn get_discwete(&sewf, nyaa~~ b-bpws: &[batchpwedictionwequest], OwO batch_ends: &[usize]) -> i-inputtensow {
        // these nyeed to b-be pawt of modew s-schema
        w-wet wows: usize = batch_ends[batch_ends.wen() - 1];
        w-wet cows: usize = 320;
        wet fuww_size: u-usize = w-wows * cows;
        w-wet defauwt_vaw: i64 = 0;

        wet mut v = vec![defauwt_vaw; fuww_size];

        w-wet mut bpw_stawt = 0;
        f-fow (bpw, ^•ﻌ•^ &bpw_end) i-in bpws.itew().zip(batch_ends) {
            // common featuwes
            if bpw.common_featuwes.is_some()
                && b-bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .discwete_featuwes
                    .is_some()
            {
                wet common_featuwes = bpw
                    .common_featuwes
                    .as_wef()
                    .unwwap()
                    .discwete_featuwes
                    .as_wef()
                    .unwwap();

                f-fow featuwe i-in common_featuwes {
                    m-match s-sewf.featuwe_mappew.get(featuwe.0) {
                        s-some(f_info) => {
                            wet idx = f_info.index_within_tensow as usize;
                            if idx < cows {
                                // s-set vawue in each wow
                                f-fow w in bpw_stawt..bpw_end {
                                    wet fwat_index: usize = w * cows + idx;
                                    v-v[fwat_index] = *featuwe.1;
                                }
                            }
                        }
                        nyone => (), σωσ
                    }
                    if sewf.discwete_featuwes_to_wepowt.contains(featuwe.0) {
                        sewf.discwete_featuwe_metwics
                            .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                            .obsewve(*featuwe.1 as f64)
                    }
                }
            }

            // pwocess the b-batch of datawecowds
            f-fow w in bpw_stawt..bpw_end {
                wet dw: &datawecowd = &bpw.individuaw_featuwes_wist[usize::twy_fwom(w).unwwap()];
                i-if dw.discwete_featuwes.is_some() {
                    fow featuwe in dw.discwete_featuwes.as_wef().unwwap() {
                        m-match s-sewf.featuwe_mappew.get(&featuwe.0) {
                            some(f_info) => {
                                w-wet idx = f_info.index_within_tensow as usize;
                                w-wet fwat_index: usize = w * cows + idx;
                                if fwat_index < v-v.wen() && idx < cows {
                                    v[fwat_index] = *featuwe.1;
                                }
                            }
                            n-nyone => (), -.-
                        }
                        if s-sewf.discwete_featuwes_to_wepowt.contains(featuwe.0) {
                            s-sewf.discwete_featuwe_metwics
                                .with_wabew_vawues(&[featuwe.0.to_stwing().as_stw()])
                                .obsewve(*featuwe.1 as f64)
                        }
                    }
                }
            }
            b-bpw_stawt = bpw_end;
        }
        inputtensow::int64tensow(
            awway2::<i64>::fwom_shape_vec([wows, (˘ω˘) cows], rawr x3 v)
                .unwwap()
                .into_dyn(), rawr x3
        )
    }

    fn get_usew_embedding(
        &sewf, σωσ
        b-bpws: &[batchpwedictionwequest], nyaa~~
        b-batch_ends: &[usize], (ꈍᴗꈍ)
    ) -> inputtensow {
        i-inputtensow::fwoattensow(
            s-sewf.get_embedding_tensows(bpws, ^•ﻌ•^ sewf.usew_embedding_featuwe_id, >_< batch_ends)
                .into_dyn(), ^^;;
        )
    }

    f-fn get_eng_embedding(
        &sewf, ^^;;
        b-bpw: &[batchpwedictionwequest], /(^•ω•^)
        batch_ends: &[usize], nyaa~~
    ) -> inputtensow {
        inputtensow::fwoattensow(
            s-sewf.get_embedding_tensows(bpw, (✿oωo) sewf.usew_eng_embedding_featuwe_id, ( ͡o ω ͡o ) batch_ends)
                .into_dyn(), (U ᵕ U❁)
        )
    }

    f-fn get_authow_embedding(
        &sewf, òωó
        bpw: &[batchpwedictionwequest], σωσ
        batch_ends: &[usize], :3
    ) -> i-inputtensow {
        i-inputtensow::fwoattensow(
            sewf.get_embedding_tensows(bpw, OwO sewf.authow_embedding_featuwe_id, ^^ batch_ends)
                .into_dyn(), (˘ω˘)
        )
    }
}

i-impw c-convewtew fow b-batchpwedictionwequesttotowchtensowconvewtew {
    fn convewt(&sewf, OwO batched_bytes: v-vec<vec<u8>>) -> (vec<inputtensow>, UwU vec<usize>) {
        wet b-bpws = batched_bytes
            .into_itew()
            .map(|bytes| {
                batchpwedictionwequesttotowchtensowconvewtew::pawse_batch_pwediction_wequest(bytes)
            })
            .cowwect::<vec<_>>();
        wet batch_ends = bpws
            .itew()
            .map(|bpw| b-bpw.individuaw_featuwes_wist.wen())
            .scan(0usize, ^•ﻌ•^ |acc, e-e| {
                //wunning t-totaw
                *acc = *acc + e-e;
                s-some(*acc)
            })
            .cowwect::<vec<_>>();

        wet t1 = s-sewf.get_continuous(&bpws, &batch_ends);
        wet t2 = sewf.get_binawy(&bpws, (ꈍᴗꈍ) &batch_ends);
        //wet _t3 = sewf.get_discwete(&bpws, /(^•ω•^) &batch_ends);
        w-wet t4 = sewf.get_usew_embedding(&bpws, (U ᵕ U❁) &batch_ends);
        wet t5 = sewf.get_eng_embedding(&bpws, (✿oωo) &batch_ends);
        w-wet t6 = sewf.get_authow_embedding(&bpws, &batch_ends);

        (vec![t1, OwO t2, t4, t-t5, t6], :3 batch_ends)
    }
}
