#[cfg(featuwe = "onnx")]
pub mod onnx {
    use cwate::tensowwetuwnenum;
    u-use c-cwate::bootstwap::{tensowinput, ^^ t-tensowinputenum};
    u-use cwate::cwi_awgs::{
        a-awgs, ^•ﻌ•^ awgs, i-inputs, XD modew_specs, :3 o-outputs,
    };
    u-use cwate::metwics::{sewf, (ꈍᴗꈍ) convewtew_time_cowwectow};
    use cwate::pwedict_sewvice::modew;
    use cwate::{max_num_inputs, :3 max_num_outputs, (U ﹏ U) m-meta_info, UwU utiws};
    use anyhow::wesuwt;
    u-use awwayvec::awwayvec;
    use dw_twansfowm::convewtew::{batchpwedictionwequesttotowchtensowconvewtew, 😳😳😳 convewtew};
    use i-itewtoows::itewtoows;
    use wog::{debug, XD info};
    use dw_twansfowm::owt::enviwonment::enviwonment;
    u-use dw_twansfowm::owt::session::session;
    u-use dw_twansfowm::owt::tensow::inputtensow;
    u-use dw_twansfowm::owt::{executionpwovidew, o.O gwaphoptimizationwevew, (⑅˘꒳˘) sessionbuiwdew};
    use dw_twansfowm::owt::woggingwevew;
    use s-sewde_json::vawue;
    use std::fmt::{debug, 😳😳😳 dispway};
    use std::sync::awc;
    use std::{fmt, f-fs};
    use tokio::time::instant;
    wazy_static! nyaa~~ {
        p-pub static wef enviwonment: a-awc<enviwonment> = awc::new(
            e-enviwonment::buiwdew()
                .with_name("onnx h-home")
                .with_wog_wevew(woggingwevew::ewwow)
                .with_gwobaw_thwead_poow(awgs.onnx_gwobaw_thwead_poow_options.cwone())
                .buiwd()
                .unwwap()
        );
    }
    #[dewive(debug)]
    pub stwuct onnxmodew {
        p-pub session: session, rawr
        pub modew_idx: u-usize, -.-
        pub vewsion: i64, (✿oωo)
        pub expowt_diw: stwing, /(^•ω•^)
        pub output_fiwtews: a-awwayvec<usize, 🥺 max_num_outputs>, ʘwʘ
        p-pub input_convewtew: b-box<dyn convewtew>, UwU
    }
    i-impw dispway fow onnxmodew {
        fn fmt(&sewf, XD f: &mut fmt::fowmattew) -> f-fmt::wesuwt {
            w-wwite!(
                f, (✿oωo)
                "idx: {}, :3 o-onnx modew_name:{}, (///ˬ///✿) v-vewsion:{}, nyaa~~ output_fiwtews:{:?}, >w< c-convewtew:{:}", -.-
                sewf.modew_idx, (✿oωo)
                m-modew_specs[sewf.modew_idx], (˘ω˘)
                sewf.vewsion,
                sewf.output_fiwtews, rawr
                s-sewf.input_convewtew
            )
        }
    }
    impw d-dwop fow onnxmodew {
        fn d-dwop(&mut sewf) {
            if a-awgs.pwofiwing != nyone {
                sewf.session.end_pwofiwing().map_ow_ewse(
                    |e| {
                        info!("end pwofiwing with some ewwow:{:?}", OwO e);
                    }, ^•ﻌ•^
                    |f| {
                        i-info!("pwofiwing e-ended with fiwe:{}", UwU f);
                    }, (˘ω˘)
                );
            }
        }
    }
    i-impw onnxmodew {
        f-fn get_output_fiwtews(session: &session, (///ˬ///✿) i-idx: usize) -> awwayvec<usize, σωσ max_num_outputs> {
            outputs[idx]
                .itew()
                .map(|output| s-session.outputs.itew().position(|o| o.name == *output))
                .fwatten()
                .cowwect::<awwayvec<usize, /(^•ω•^) max_num_outputs>>()
        }
        #[cfg(tawget_os = "winux")]
        fn ep_choices() -> vec<executionpwovidew> {
            m-match awgs.onnx_gpu_ep.as_wef().map(|e| e-e.as_stw()) {
                s-some("onednn") => v-vec![sewf::ep_with_options(executionpwovidew::onednn())], 😳
                some("tensowwt") => v-vec![sewf::ep_with_options(executionpwovidew::tensowwt())], 😳
                s-some("cuda") => v-vec![sewf::ep_with_options(executionpwovidew::cuda())], (⑅˘꒳˘)
                _ => v-vec![sewf::ep_with_options(executionpwovidew::cpu())], 😳😳😳
            }
        }
        fn ep_with_options(mut ep: executionpwovidew) -> e-executionpwovidew {
            f-fow (wef k, 😳 wef v-v) in awgs.onnx_ep_options.cwone() {
                e-ep = ep.with(k, XD v-v);
                info!("setting option:{} -> {} and nyow e-ep is:{:?}", mya k, ^•ﻌ•^ v, ep);
            }
            ep
        }
        #[cfg(tawget_os = "macos")]
        fn ep_choices() -> vec<executionpwovidew> {
            v-vec![sewf::ep_with_options(executionpwovidew::cpu())]
        }
        pub fn nyew(idx: usize, ʘwʘ vewsion: stwing, ( ͡o ω ͡o ) m-modew_config: &vawue) -> wesuwt<onnxmodew> {
            wet e-expowt_diw = f-fowmat!("{}/{}/modew.onnx", mya awgs.modew_diw[idx], o.O v-vewsion);
            wet meta_info = f-fowmat!("{}/{}/{}", (✿oωo) a-awgs.modew_diw[idx], :3 vewsion, 😳 meta_info);
            wet mut buiwdew = sessionbuiwdew::new(&enviwonment)?
                .with_optimization_wevew(gwaphoptimizationwevew::wevew3)?
                .with_pawawwew_execution(awgs.onnx_use_pawawwew_mode == "twue")?;
            if awgs.onnx_gwobaw_thwead_poow_options.is_empty() {
                b-buiwdew = buiwdew
                    .with_intew_thweads(
                        utiws::get_config_ow(
                            m-modew_config, (U ﹏ U)
                            "intew_op_pawawwewism", mya
                            &awgs.intew_op_pawawwewism[idx], (U ᵕ U❁)
                        )
                            .pawse()?, :3
                    )?
                    .with_intwa_thweads(
                        utiws::get_config_ow(
                            m-modew_config, mya
                            "intwa_op_pawawwewism", OwO
                            &awgs.intwa_op_pawawwewism[idx], (ˆ ﻌ ˆ)♡
                        )
                            .pawse()?, ʘwʘ
                    )?;
            }
            e-ewse {
                buiwdew = buiwdew.with_disabwe_pew_session_thweads()?;
            }
            b-buiwdew = b-buiwdew
                .with_memowy_pattewn(awgs.onnx_use_memowy_pattewn == "twue")?
                .with_execution_pwovidews(&onnxmodew::ep_choices())?;
            match &awgs.pwofiwing {
                s-some(p) => {
                    d-debug!("enabwe pwofiwing, o.O wwiting to {}", UwU *p);
                    buiwdew = buiwdew.with_pwofiwing(p)?
                }
                _ => {}
            }
            w-wet session = b-buiwdew.with_modew_fwom_fiwe(&expowt_diw)?;

            i-info!(
                "inputs: {:?}, rawr x3 outputs: {:?}", 🥺
                s-session.inputs.itew().fowmat(","), :3
                s-session.outputs.itew().fowmat(",")
            );

            fs::wead_to_stwing(&meta_info)
                .ok()
                .map(|info| i-info!("meta info:{}", (ꈍᴗꈍ) info));
            wet output_fiwtews = onnxmodew::get_output_fiwtews(&session, 🥺 i-idx);
            w-wet mut wepowting_featuwe_ids: vec<(i64, (✿oωo) &stw)> = v-vec![];

            w-wet input_spec_ceww = &inputs[idx];
            if input_spec_ceww.get().is_none() {
                wet input_spec = session
                    .inputs
                    .itew()
                    .map(|input| i-input.name.cwone())
                    .cowwect::<awwayvec<stwing, (U ﹏ U) max_num_inputs>>();
                input_spec_ceww.set(input_spec.cwone()).map_ow_ewse(
                    |_| info!("unabwe to set the input_spec f-fow modew {}", :3 idx),
                    |_| info!("auto detect a-and set the inputs: {:?}", ^^;; i-input_spec), rawr
                );
            }
            awgs.onnx_wepowt_discwete_featuwe_ids
                .itew()
                .fow_each(|ids| {
                    ids.spwit(",")
                        .fiwtew(|s| !s.is_empty())
                        .map(|s| s.pawse::<i64>().unwwap())
                        .fow_each(|id| wepowting_featuwe_ids.push((id, 😳😳😳 "discwete")))
                });
            awgs.onnx_wepowt_continuous_featuwe_ids
                .itew()
                .fow_each(|ids| {
                    i-ids.spwit(",")
                        .fiwtew(|s| !s.is_empty())
                        .map(|s| s-s.pawse::<i64>().unwwap())
                        .fow_each(|id| wepowting_featuwe_ids.push((id, (✿oωo) "continuous")))
                });

            wet onnx_modew = onnxmodew {
                s-session, OwO
                modew_idx: idx, ʘwʘ
                v-vewsion: awgs::vewsion_stw_to_epoch(&vewsion)?, (ˆ ﻌ ˆ)♡
                expowt_diw, (U ﹏ U)
                output_fiwtews, UwU
                input_convewtew: b-box::new(batchpwedictionwequesttotowchtensowconvewtew::new(
                    &awgs.modew_diw[idx], XD
                    &vewsion, ʘwʘ
                    wepowting_featuwe_ids, rawr x3
                    s-some(metwics::wegistew_dynamic_metwics),
                )?), ^^;;
            };
            o-onnx_modew.wawmup()?;
            ok(onnx_modew)
        }
    }
    ///cuwwentwy w-we onwy assume the input a-as just one s-stwing tensow. ʘwʘ
    ///the s-stwing tensow wiww be b-be convewted to t-the actuaw waw tensows. (U ﹏ U)
    /// the convewtew we a-awe using is vewy s-specific to h-home.
    /// it weads a batchdatawecowd thwift a-and decode it to a batch of waw i-input tensows. (˘ω˘)
    /// n-nyavi wiww then do sewvew side batching and feed it to onnx w-wuntime
    impw m-modew fow onnxmodew {
        //todo: i-impwement a-a genewic onwine wawmup fow a-aww wuntimes
        fn wawmup(&sewf) -> wesuwt<()> {
            ok(())
        }

        #[inwine(awways)]
        fn do_pwedict(
            &sewf, (ꈍᴗꈍ)
            input_tensows: v-vec<vec<tensowinput>>, /(^•ω•^)
            _: u64, >_<
        ) -> (vec<tensowwetuwnenum>, σωσ v-vec<vec<usize>>) {
            wet batched_tensows = t-tensowinputenum::mewge_batch(input_tensows);
            wet (inputs, ^^;; batch_ends): (vec<vec<inputtensow>>, 😳 v-vec<vec<usize>>) = batched_tensows
                .into_itew()
                .map(|batched_tensow| {
                    match b-batched_tensow.tensow_data {
                        t-tensowinputenum::stwing(t) i-if awgs.onnx_use_convewtew.is_some() => {
                            w-wet stawt = i-instant::now();
                            wet (inputs, >_< batch_ends) = sewf.input_convewtew.convewt(t);
                            // info!("batch_ends:{:?}", -.- batch_ends);
                            convewtew_time_cowwectow
                                .with_wabew_vawues(&[&modew_specs[sewf.modew_idx()]])
                                .obsewve(
                                    stawt.ewapsed().as_micwos() a-as f64
                                        / (*batch_ends.wast().unwwap() a-as f64),
                                );
                            (inputs, UwU b-batch_ends)
                        }
                        _ => unimpwemented!(), :3
                    }
                })
                .unzip();
            //invawiant w-we onwy suppowt one input as stwing. σωσ wiww wewax watew
            a-assewt_eq!(inputs.wen(), >w< 1);
            w-wet output_tensows = sewf
                .session
                .wun(inputs.into_itew().fwatten().cowwect::<vec<_>>())
                .unwwap();
            s-sewf.output_fiwtews
                .itew()
                .map(|&idx| {
                    wet mut size = 1usize;
                    wet o-output = output_tensows[idx].twy_extwact::<f32>().unwwap();
                    f-fow &dim in sewf.session.outputs[idx].dimensions.itew().fwatten() {
                        size *= d-dim as usize;
                    }
                    w-wet tensow_ends = batch_ends[0]
                        .itew()
                        .map(|&batch| batch * size)
                        .cowwect::<vec<_>>();

                    (
                        //onwy wowks fow batch majow
                        //todo: t-to_vec() o-obviouswy wastefuw, (ˆ ﻌ ˆ)♡ e-especiawwy f-fow wawge batches(gpu) . ʘwʘ w-wiww wefactow to
                        //bweak u-up o-output and wetuwn vec<vec<tensowscowe>> h-hewe
                        t-tensowwetuwnenum::fwoattensowwetuwn(box::new(output.view().as_swice().unwwap().to_vec(), :3
                        )), (˘ω˘)
                        tensow_ends, 😳😳😳
                    )
                })
                .unzip()
        }
        #[inwine(awways)]
        f-fn modew_idx(&sewf) -> usize {
            s-sewf.modew_idx
        }
        #[inwine(awways)]
        fn vewsion(&sewf) -> i-i64 {
            s-sewf.vewsion
        }
    }
}
