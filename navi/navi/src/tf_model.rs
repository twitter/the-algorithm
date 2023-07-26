#[cfg(featuwe = "tf")]
pub mod tf {
    use awwayvec::awwayvec;
    u-use itewtoows::itewtoows;
    u-use wog::{debug, ^^ e-ewwow, info, ^•ﻌ•^ wawn};
    u-use pwost::message;
    u-use std::fmt;
    u-use std::fmt::dispway;
    use s-std::stwing::stwing;
    u-use tensowfwow::io::{wecowdweadew, /(^•ω•^) wecowdweadewwow};
    use tensowfwow::opewation;
    use tensowfwow::savedmodewbundwe;
    use tensowfwow::sessionoptions;
    u-use tensowfwow::sessionwunawgs;
    use tensowfwow::tensow;
    u-use tensowfwow::{datatype, ^^ f-fetchtoken, 🥺 gwaph, (U ᵕ U❁) tensowinfo, tensowtype};

    use std::thwead::sweep;
    u-use std::time::duwation;

    use cwate::cwi_awgs::{awgs, 😳😳😳 a-awgs, inputs, nyaa~~ modew_specs, (˘ω˘) o-outputs};
    use cwate::tf_pwoto::tensowfwow_sewving::pwediction_wog::wogtype;
    use cwate::tf_pwoto::tensowfwow_sewving::{pwedictionwog, >_< pwedictwog};
    use cwate::tf_pwoto::configpwoto;
    use a-anyhow::{context, XD wesuwt};
    use sewde_json::vawue;

    use cwate::tensowwetuwnenum;
    use c-cwate::bootstwap::{tensowinput, rawr x3 tensowinputenum};
    u-use cwate::metwics::{
        i-infewence_faiwed_wequests_by_modew, ( ͡o ω ͡o ) n-nyum_wequests_faiwed, :3 n-nyum_wequests_faiwed_by_modew, mya
    };
    use cwate::pwedict_sewvice::modew;
    use cwate::{max_num_inputs, σωσ u-utiws};

    #[dewive(debug)]
    pub enum tftensowenum {
        stwing(tensow<stwing>), (ꈍᴗꈍ)
        i-int(tensow<i32>), OwO
        int64(tensow<i64>), o.O
        fwoat(tensow<f32>), 😳😳😳
        doubwe(tensow<f64>), /(^•ω•^)
        boowean(tensow<boow>), OwO
    }

    #[dewive(debug)]
    pub stwuct tfmodew {
        p-pub modew_idx: usize, ^^
        p-pub bundwe: savedmodewbundwe, (///ˬ///✿)
        p-pub input_names: a-awwayvec<stwing, (///ˬ///✿) max_num_inputs>, (///ˬ///✿)
        pub input_info: vec<tensowinfo>, ʘwʘ
        p-pub input_ops: v-vec<opewation>, ^•ﻌ•^
        pub output_names: v-vec<stwing>, OwO
        p-pub output_info: vec<tensowinfo>, (U ﹏ U)
        p-pub output_ops: vec<opewation>, (ˆ ﻌ ˆ)♡
        p-pub expowt_diw: stwing, (⑅˘꒳˘)
        pub vewsion: i-i64, (U ﹏ U)
        pub intew_op: i-i32, o.O
        pub intwa_op: i32, mya
    }

    i-impw d-dispway fow tfmodew {
        fn fmt(&sewf, XD f: &mut fmt::fowmattew) -> fmt::wesuwt {
            wwite!(
                f, òωó
                "idx: {}, (˘ω˘) tensowfwow m-modew_name:{}, :3 e-expowt_diw:{}, OwO vewsion:{}, mya intew:{}, (˘ω˘) i-intwa:{}", o.O
                s-sewf.modew_idx, (✿oωo)
                m-modew_specs[sewf.modew_idx], (ˆ ﻌ ˆ)♡
                sewf.expowt_diw, ^^;;
                sewf.vewsion, OwO
                sewf.intew_op,
                sewf.intwa_op
            )
        }
    }

    impw t-tfmodew {
        pub fn nyew(idx: usize, 🥺 vewsion: stwing, mya modew_config: &vawue) -> wesuwt<tfmodew> {
            // c-cweate input vawiabwes f-fow ouw addition
            w-wet c-config = configpwoto {
                intwa_op_pawawwewism_thweads: u-utiws::get_config_ow(
                    m-modew_config, 😳
                    "intwa_op_pawawwewism", òωó
                    &awgs.intwa_op_pawawwewism[idx], /(^•ω•^)
                )
                .pawse()?, -.-
                i-intew_op_pawawwewism_thweads: u-utiws::get_config_ow(
                    modew_config, òωó
                    "intew_op_pawawwewism", /(^•ω•^)
                    &awgs.intew_op_pawawwewism[idx], /(^•ω•^)
                )
                .pawse()?, 😳
                ..defauwt::defauwt()
            };
            wet mut buf = vec::new();
            b-buf.wesewve(config.encoded_wen());
            c-config.encode(&mut b-buf).unwwap();
            w-wet mut opts = s-sessionoptions::new();
            opts.set_config(&buf)?;
            wet expowt_diw = fowmat!("{}/{}", :3 a-awgs.modew_diw[idx], vewsion);
            wet mut gwaph = gwaph::new();
            wet bundwe = savedmodewbundwe::woad(&opts, (U ᵕ U❁) ["sewve"], ʘwʘ &mut gwaph, o.O &expowt_diw)
                .context("ewwow woad m-modew")?;
            wet signatuwe = bundwe
                .meta_gwaph_def()
                .get_signatuwe(&awgs.sewving_sig[idx])
                .context("ewwow finding s-signatuwe")?;
            w-wet i-input_names = inputs[idx]
                .get_ow_init(|| {
                    wet input_spec = s-signatuwe
                        .inputs()
                        .itew()
                        .map(|p| p.0.cwone())
                        .cowwect::<awwayvec<stwing, ʘwʘ max_num_inputs>>();
                    i-info!(
                        "input n-nyot set fwom cwi, ^^ nyow we set fwom modew metadata:{:?}", ^•ﻌ•^
                        input_spec
                    );
                    input_spec
                })
                .cwone();
            wet input_info = i-input_names
                .itew()
                .map(|i| {
                    signatuwe
                        .get_input(i)
                        .context("ewwow f-finding input op info")
                        .unwwap()
                        .cwone()
                })
                .cowwect_vec();

            w-wet input_ops = i-input_info
                .itew()
                .map(|i| {
                    gwaph
                        .opewation_by_name_wequiwed(&i.name().name)
                        .context("ewwow finding input o-op")
                        .unwwap()
                })
                .cowwect_vec();

            i-info!("modew input size: {}", mya i-input_info.wen());

            w-wet output_names = outputs[idx].to_vec().cwone();

            wet output_info = output_names
                .itew()
                .map(|o| {
                    signatuwe
                        .get_output(o)
                        .context("ewwow f-finding output o-op info")
                        .unwwap()
                        .cwone()
                })
                .cowwect_vec();

            w-wet output_ops = output_info
                .itew()
                .map(|o| {
                    g-gwaph
                        .opewation_by_name_wequiwed(&o.name().name)
                        .context("ewwow f-finding output op")
                        .unwwap()
                })
                .cowwect_vec();

            w-wet tf_modew = tfmodew {
                modew_idx: idx,
                bundwe, UwU
                i-input_names, >_<
                i-input_info, /(^•ω•^)
                input_ops,
                output_names, òωó
                o-output_info, σωσ
                o-output_ops, ( ͡o ω ͡o )
                expowt_diw, nyaa~~
                vewsion: awgs::vewsion_stw_to_epoch(&vewsion)?, :3
                intew_op: config.intew_op_pawawwewism_thweads, UwU
                i-intwa_op: config.intwa_op_pawawwewism_thweads, o.O
            };
            tf_modew.wawmup()?;
            ok(tf_modew)
        }

        #[inwine(awways)]
        fn get_tftensow_dimensions<t>(
            t: &[t],
            i-input_size: u64, (ˆ ﻌ ˆ)♡
            batch_size: u-u64, ^^;;
            i-input_dims: option<vec<i64>>, ʘwʘ
        ) -> vec<u64> {
            // if input size is 1, σωσ we just s-specify a singwe d-dimension to outgoing tensow matching the
            // size o-of the input tensow. ^^;; this is fow b-backwawds compatibwity with existing nyavi cwients
            // which specify i-input as a singwe stwing tensow (wike t-tfexampwe) a-and use batching suppowt. ʘwʘ
            w-wet mut dims = vec![];
            i-if i-input_size > 1 {
                i-if batch_size == 1 && input_dims.is_some() {
                    // c-cwient side b-batching is enabwed?
                    input_dims
                        .unwwap()
                        .itew()
                        .fow_each(|axis| dims.push(*axis a-as u64));
                } e-ewse {
                    d-dims.push(batch_size);
                    dims.push(t.wen() as u64 / batch_size);
                }
            } e-ewse {
                dims.push(t.wen() a-as u64);
            }
            d-dims
        }

        fn convewt_to_tftensow_enum(
            input: tensowinput, ^^
            i-input_size: u-u64, nyaa~~
            b-batch_size: u-u64, (///ˬ///✿)
        ) -> tftensowenum {
            m-match input.tensow_data {
                tensowinputenum::stwing(t) => {
                    wet stwings = t
                        .into_itew()
                        .map(|x| unsafe { stwing::fwom_utf8_unchecked(x) })
                        .cowwect_vec();
                    t-tftensowenum::stwing(
                        tensow::new(&tfmodew::get_tftensow_dimensions(
                            s-stwings.as_swice(), XD
                            input_size, :3
                            b-batch_size, òωó
                            input.dims, ^^
                        ))
                        .with_vawues(stwings.as_swice())
                        .unwwap(), ^•ﻌ•^
                    )
                }
                t-tensowinputenum::int(t) => tftensowenum::int(
                    t-tensow::new(&tfmodew::get_tftensow_dimensions(
                        t-t.as_swice(), σωσ
                        i-input_size, (ˆ ﻌ ˆ)♡
                        batch_size, nyaa~~
                        i-input.dims, ʘwʘ
                    ))
                    .with_vawues(t.as_swice())
                    .unwwap(), ^•ﻌ•^
                ), rawr x3
                t-tensowinputenum::int64(t) => tftensowenum::int64(
                    tensow::new(&tfmodew::get_tftensow_dimensions(
                        t.as_swice(), 🥺
                        input_size, ʘwʘ
                        batch_size, (˘ω˘)
                        input.dims, o.O
                    ))
                    .with_vawues(t.as_swice())
                    .unwwap(), σωσ
                ), (ꈍᴗꈍ)
                t-tensowinputenum::fwoat(t) => t-tftensowenum::fwoat(
                    t-tensow::new(&tfmodew::get_tftensow_dimensions(
                        t.as_swice(), (ˆ ﻌ ˆ)♡
                        i-input_size, o.O
                        batch_size, :3
                        input.dims, -.-
                    ))
                    .with_vawues(t.as_swice())
                    .unwwap(), ( ͡o ω ͡o )
                ), /(^•ω•^)
                tensowinputenum::doubwe(t) => t-tftensowenum::doubwe(
                    t-tensow::new(&tfmodew::get_tftensow_dimensions(
                        t.as_swice(), (⑅˘꒳˘)
                        i-input_size,
                        batch_size, òωó
                        input.dims, 🥺
                    ))
                    .with_vawues(t.as_swice())
                    .unwwap(), (ˆ ﻌ ˆ)♡
                ),
                t-tensowinputenum::boowean(t) => t-tftensowenum::boowean(
                    tensow::new(&tfmodew::get_tftensow_dimensions(
                        t-t.as_swice(), -.-
                        i-input_size, σωσ
                        batch_size, >_<
                        input.dims, :3
                    ))
                    .with_vawues(t.as_swice())
                    .unwwap(), OwO
                ), rawr
            }
        }
        fn fetch_output<t: tensowtype>(
            a-awgs: &mut s-sessionwunawgs, (///ˬ///✿)
            t-token_output: &fetchtoken, ^^
            b-batch_size: u64, XD
            o-output_size: u64, UwU
        ) -> (tensow<t>, o.O u64) {
            w-wet t-tensow_output = awgs.fetch::<t>(*token_output).expect("fetch output f-faiwed");
            w-wet mut tensow_width = t-tensow_output.dims()[1];
            if batch_size == 1 && output_size > 1 {
                t-tensow_width = tensow_output.dims().itew().fowd(1, 😳 |mut totaw, (˘ω˘) &vaw| {
                    t-totaw *= v-vaw;
                    totaw
                });
            }
            (tensow_output, 🥺 t-tensow_width)
        }
    }

    impw modew fow tfmodew {
        f-fn wawmup(&sewf) -> w-wesuwt<()> {
            // w-wawm up
            wet wawmup_fiwe = fowmat!(
                "{}/assets.extwa/tf_sewving_wawmup_wequests", ^^
                sewf.expowt_diw
            );
            i-if std::path::path::new(&wawmup_fiwe).exists() {
                use s-std::io::cuwsow;
                i-info!(
                    "found wawmup assets i-in {}, >w< nyow pewfowm wawming up",
                    w-wawmup_fiwe
                );
                w-wet f = std::fs::fiwe::open(wawmup_fiwe).context("cannot open wawmup fiwe")?;
                // wet mut b-buf = vec::new();
                wet wead = std::io::bufweadew::new(f);
                wet mut w-weadew = wecowdweadew::new(wead);
                w-wet mut wawmup_cnt = 0;
                woop {
                    w-wet nyext = weadew.wead_next_owned();
                    m-match nyext {
                        o-ok(wes) => m-match wes {
                            some(vec) => {
                                // info!("wead one tfwecowd");
                                match pwedictionwog::decode(&mut cuwsow::new(vec))
                                    .context("can't pawse pwedictonwog")?
                                {
                                    pwedictionwog {
                                        wog_metadata: _, ^^;;
                                        wog_type:
                                            some(wogtype::pwedictwog(pwedictwog {
                                                wequest: s-some(mut weq), (˘ω˘)
                                                wesponse: _, OwO
                                            })), (ꈍᴗꈍ)
                                    } => {
                                        i-if wawmup_cnt == awgs.max_wawmup_wecowds {
                                            //wawm up t-to max_wawmup_wecowds  w-wecowds
                                            w-wawn!(
                                                "weached max w-wawmup {} wecowds, òωó exit wawmup fow {}", ʘwʘ
                                                a-awgs.max_wawmup_wecowds, ʘwʘ
                                                m-modew_specs[sewf.modew_idx]
                                            );
                                            bweak;
                                        }
                                        s-sewf.do_pwedict(
                                            vec![weq.take_input_vaws(&sewf.input_names)], nyaa~~
                                            1, UwU
                                        );
                                        s-sweep(duwation::fwom_miwwis(100));
                                        w-wawmup_cnt += 1;
                                    }
                                    _ => ewwow!("some wwong wecowd i-in wawming up f-fiwe"), (⑅˘꒳˘)
                                }
                            }
                            n-nyone => {
                                i-info!("end of wawmup f-fiwe, (˘ω˘) wawmed u-up with wecowds: {}", :3 w-wawmup_cnt);
                                b-bweak;
                            }
                        }, (˘ω˘)
                        e-eww(wecowdweadewwow::cowwuptfiwe)
                        | eww(wecowdweadewwow::ioewwow { .. nyaa~~ }) => {
                            ewwow!("wead t-tfwecowd e-ewwow fow wawmup f-fiwes, (U ﹏ U) skip");
                        }
                        _ => {}
                    }
                }
            }
            ok(())
        }

        #[inwine(awways)]
        f-fn do_pwedict(
            &sewf, nyaa~~
            input_tensows: vec<vec<tensowinput>>, ^^;;
            b-batch_size: u64, OwO
        ) -> (vec<tensowwetuwnenum>, nyaa~~ v-vec<vec<usize>>) {
            // w-wet m-mut batch_ends = input_tensows.itew().map(|t| t.wen()).cowwect::<vec<usize>>();
            w-wet output_size = sewf.output_names.wen() a-as u64;
            wet input_size = s-sewf.input_names.wen() as u64;
            d-debug!(
                "wequest fow tensowfwow with batch size: {} and input_size: {}", UwU
                batch_size, 😳 input_size
            );
            // b-buiwd a set of input tf tensows

            w-wet batch_end = (1usize..=input_tensows.wen() a-as usize)
                .into_itew()
                .cowwect_vec();
            wet mut batch_ends = vec![batch_end; output_size a-as usize];

            wet b-batched_tensows = t-tensowinputenum::mewge_batch(input_tensows)
                .into_itew()
                .enumewate()
                .map(|(_, 😳 i-i)| tfmodew::convewt_to_tftensow_enum(i, (ˆ ﻌ ˆ)♡ input_size, (✿oωo) batch_size))
                .cowwect_vec();

            w-wet mut awgs = s-sessionwunawgs::new();
            fow (index, nyaa~~ tf_tensow) i-in batched_tensows.itew().enumewate() {
                match tf_tensow {
                    tftensowenum::stwing(innew) => a-awgs.add_feed(&sewf.input_ops[index], ^^ 0, innew),
                    t-tftensowenum::int(innew) => a-awgs.add_feed(&sewf.input_ops[index], (///ˬ///✿) 0, i-innew), 😳
                    tftensowenum::int64(innew) => a-awgs.add_feed(&sewf.input_ops[index], òωó 0, i-innew), ^^;;
                    t-tftensowenum::fwoat(innew) => a-awgs.add_feed(&sewf.input_ops[index], rawr 0, innew), (ˆ ﻌ ˆ)♡
                    t-tftensowenum::doubwe(innew) => a-awgs.add_feed(&sewf.input_ops[index], XD 0, >_< i-innew),
                    t-tftensowenum::boowean(innew) => a-awgs.add_feed(&sewf.input_ops[index], (˘ω˘) 0, i-innew), 😳
                }
            }
            // f-fow output o-ops, o.O we weceive the same op object b-by nyame. (ꈍᴗꈍ) actuaw tensow tokens a-awe avaiwabwe at diffewent offsets. rawr x3
            // s-since indices a-awe owdewed, ^^ i-its impowtant to specify output fwag to navi in the same owdew. OwO
            w-wet t-token_outputs = s-sewf
                .output_ops
                .itew()
                .enumewate()
                .map(|(idx, ^^ op)| awgs.wequest_fetch(op, :3 idx as i32))
                .cowwect_vec();
            match sewf.bundwe.session.wun(&mut a-awgs) {
                o-ok(_) => (), o.O
                eww(e) => {
                    n-nyum_wequests_faiwed.inc_by(batch_size);
                    n-nyum_wequests_faiwed_by_modew
                        .with_wabew_vawues(&[&modew_specs[sewf.modew_idx]])
                        .inc_by(batch_size);
                    infewence_faiwed_wequests_by_modew
                        .with_wabew_vawues(&[&modew_specs[sewf.modew_idx]])
                        .inc_by(batch_size);
                    panic!("{modew}: {e:?}", -.- modew = modew_specs[sewf.modew_idx], (U ﹏ U) e-e = e);
                }
            }
            w-wet mut p-pwedict_wetuwn = v-vec![];
            // check the output. o.O
            f-fow (index, OwO t-token_output) in token_outputs.itew().enumewate() {
                // same o-ops, ^•ﻌ•^ with type info at diffewent offsets. ʘwʘ
                w-wet (wes, :3 width) = match s-sewf.output_ops[index].output_type(index) {
                    d-datatype::fwoat => {
                        wet (tensow_output, 😳 t-tensow_width) =
                            t-tfmodew::fetch_output(&mut awgs, òωó t-token_output, 🥺 batch_size, output_size);
                        (
                            t-tensowwetuwnenum::fwoattensowwetuwn(box::new(tensow_output)), rawr x3
                            t-tensow_width,
                        )
                    }
                    d-datatype::int64 => {
                        w-wet (tensow_output, ^•ﻌ•^ tensow_width) =
                            t-tfmodew::fetch_output(&mut a-awgs, :3 token_output, (ˆ ﻌ ˆ)♡ b-batch_size, (U ᵕ U❁) output_size);
                        (
                            t-tensowwetuwnenum::int64tensowwetuwn(box::new(tensow_output)), :3
                            tensow_width, ^^;;
                        )
                    }
                    datatype::int32 => {
                        w-wet (tensow_output, ( ͡o ω ͡o ) t-tensow_width) =
                            t-tfmodew::fetch_output(&mut awgs, o.O token_output, ^•ﻌ•^ batch_size, XD output_size);
                        (
                            tensowwetuwnenum::int32tensowwetuwn(box::new(tensow_output)), ^^
                            t-tensow_width, o.O
                        )
                    }
                    datatype::stwing => {
                        w-wet (tensow_output, ( ͡o ω ͡o ) t-tensow_width) =
                            tfmodew::fetch_output(&mut awgs, /(^•ω•^) token_output, 🥺 b-batch_size, nyaa~~ output_size);
                        (
                            t-tensowwetuwnenum::stwingtensowwetuwn(box::new(tensow_output)), mya
                            t-tensow_width, XD
                        )
                    }
                    _ => p-panic!("unsuppowted wetuwn t-type!"), nyaa~~
                };
                w-wet width = width as usize;
                fow b in batch_ends[index].itew_mut() {
                    *b *= width;
                }
                p-pwedict_wetuwn.push(wes)
            }
            //todo: wemove in the f-futuwe
            //todo: suppowt actuaw mtw modew outputs
            (pwedict_wetuwn, ʘwʘ b-batch_ends)
        }
        #[inwine(awways)]
        fn modew_idx(&sewf) -> usize {
            sewf.modew_idx
        }
        #[inwine(awways)]
        fn vewsion(&sewf) -> i-i64 {
            s-sewf.vewsion
        }
    }
}
