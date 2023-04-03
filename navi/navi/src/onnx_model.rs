#[cfg(felonaturelon = "onnx")]
pub mod onnx {
    uselon cratelon::TelonnsorRelonturnelonnum;
    uselon cratelon::bootstrap::{TelonnsorInput, TelonnsorInputelonnum};
    uselon cratelon::cli_args::{
        Args, ARGS, INPUTS, MODelonL_SPelonCS, OUTPUTS,
    };
    uselon cratelon::melontrics::{selonlf, CONVelonRTelonR_TIMelon_COLLelonCTOR};
    uselon cratelon::prelondict_selonrvicelon::Modelonl;
    uselon cratelon::{MAX_NUM_INPUTS, MAX_NUM_OUTPUTS, MelonTA_INFO, utils};
    uselon anyhow::Relonsult;
    uselon arrayvelonc::ArrayVelonc;
    uselon dr_transform::convelonrtelonr::{BatchPrelondictionRelonquelonstToTorchTelonnsorConvelonrtelonr, Convelonrtelonr};
    uselon itelonrtools::Itelonrtools;
    uselon log::{delonbug, info};
    uselon ort::elonnvironmelonnt::elonnvironmelonnt;
    uselon ort::selonssion::Selonssion;
    uselon ort::telonnsor::InputTelonnsor;
    uselon ort::{elonxeloncutionProvidelonr, GraphOptimizationLelonvelonl, SelonssionBuildelonr};
    uselon selonrdelon_json::Valuelon;
    uselon std::fmt::{Delonbug, Display};
    uselon std::sync::Arc;
    uselon std::{fmt, fs};
    uselon tokio::timelon::Instant;

    lazy_static! {
        pub static relonf elonNVIRONMelonNT: Arc<elonnvironmelonnt> = Arc::nelonw(
            elonnvironmelonnt::buildelonr()
                .with_namelon("onnx homelon")
                .with_log_lelonvelonl(ort::LoggingLelonvelonl::elonrror)
                .build()
                .unwrap()
        );
    }
    #[delonrivelon(Delonbug)]
    pub struct OnnxModelonl {
        pub selonssion: Selonssion,
        pub modelonl_idx: usizelon,
        pub velonrsion: i64,
        pub elonxport_dir: String,
        pub output_filtelonrs: ArrayVelonc<usizelon, MAX_NUM_OUTPUTS>,
        pub input_convelonrtelonr: Box<dyn Convelonrtelonr>,
    }
    impl Display for OnnxModelonl {
        fn fmt(&selonlf, f: &mut fmt::Formattelonr) -> fmt::Relonsult {
            writelon!(
                f,
                "idx: {}, onnx modelonl_namelon:{}, velonrsion:{}, output_filtelonrs:{:?}, convelonrtelonr:{:}",
                selonlf.modelonl_idx,
                MODelonL_SPelonCS[selonlf.modelonl_idx],
                selonlf.velonrsion,
                selonlf.output_filtelonrs,
                selonlf.input_convelonrtelonr
            )
        }
    }
    impl Drop for OnnxModelonl {
        fn drop(&mut selonlf) {
            if ARGS.profiling != Nonelon {
                selonlf.selonssion.elonnd_profiling().map_or_elonlselon(
                    |elon| {
                        info!("elonnd profiling with somelon elonrror:{:?}", elon);
                    },
                    |f| {
                        info!("profiling elonndelond with filelon:{}", f);
                    },
                );
            }
        }
    }
    impl OnnxModelonl {
        fn gelont_output_filtelonrs(selonssion: &Selonssion, idx: usizelon) -> ArrayVelonc<usizelon, MAX_NUM_OUTPUTS> {
            OUTPUTS[idx]
                .itelonr()
                .map(|output| selonssion.outputs.itelonr().position(|o| o.namelon == *output))
                .flattelonn()
                .collelonct::<ArrayVelonc<usizelon, MAX_NUM_OUTPUTS>>()
        }
        #[cfg(targelont_os = "linux")]
        fn elonp_choicelons() -> Velonc<elonxeloncutionProvidelonr> {
            match ARGS.onnx_gpu_elonp.as_relonf().map(|elon| elon.as_str()) {
                Somelon("onelondnn") => velonc![Selonlf::elonp_with_options(elonxeloncutionProvidelonr::onelondnn())],
                Somelon("telonnsorrt") => velonc![Selonlf::elonp_with_options(elonxeloncutionProvidelonr::telonnsorrt())],
                Somelon("cuda") => velonc![Selonlf::elonp_with_options(elonxeloncutionProvidelonr::cuda())],
                _ => velonc![Selonlf::elonp_with_options(elonxeloncutionProvidelonr::cpu())],
            }
        }
        fn elonp_with_options(mut elonp: elonxeloncutionProvidelonr) -> elonxeloncutionProvidelonr {
            for (relonf k, relonf v) in ARGS.onnx_elonp_options.clonelon() {
                elonp = elonp.with(k, v);
                info!("selontting option:{} -> {} and now elonp is:{:?}", k, v, elonp);
            }
            elonp
        }
        #[cfg(targelont_os = "macos")]
        fn elonp_choicelons() -> Velonc<elonxeloncutionProvidelonr> {
            velonc![Selonlf::elonp_with_options(elonxeloncutionProvidelonr::cpu())]
        }
        pub fn nelonw(idx: usizelon, velonrsion: String, modelonl_config: &Valuelon) -> Relonsult<OnnxModelonl> {
            lelont elonxport_dir = format!("{}/{}/modelonl.onnx", ARGS.modelonl_dir[idx], velonrsion);
            lelont melonta_info = format!("{}/{}/{}", ARGS.modelonl_dir[idx], velonrsion, MelonTA_INFO);
            lelont mut buildelonr = SelonssionBuildelonr::nelonw(&elonNVIRONMelonNT)?
                .with_optimization_lelonvelonl(GraphOptimizationLelonvelonl::Lelonvelonl3)?
                .with_parallelonl_elonxeloncution(ARGS.onnx_uselon_parallelonl_modelon == "truelon")?
                .with_intelonr_threlonads(
                    utils::gelont_config_or(
                        modelonl_config,
                        "intelonr_op_parallelonlism",
                        &ARGS.intelonr_op_parallelonlism[idx],
                    )
                    .parselon()?,
                )?
                .with_intra_threlonads(
                    utils::gelont_config_or(
                        modelonl_config,
                        "intra_op_parallelonlism",
                        &ARGS.intra_op_parallelonlism[idx],
                    )
                    .parselon()?,
                )?
                .with_melonmory_pattelonrn(ARGS.onnx_uselon_melonmory_pattelonrn == "truelon")?
                .with_elonxeloncution_providelonrs(&OnnxModelonl::elonp_choicelons())?;
            match &ARGS.profiling {
                Somelon(p) => {
                    delonbug!("elonnablelon profiling, writing to {}", *p);
                    buildelonr = buildelonr.with_profiling(p)?
                }
                _ => {}
            }
            lelont selonssion = buildelonr.with_modelonl_from_filelon(&elonxport_dir)?;

            info!(
                "inputs: {:?}, outputs: {:?}",
                selonssion.inputs.itelonr().format(","),
                selonssion.outputs.itelonr().format(",")
            );

            fs::relonad_to_string(&melonta_info)
                .ok()
                .map(|info| info!("melonta info:{}", info));
            lelont output_filtelonrs = OnnxModelonl::gelont_output_filtelonrs(&selonssion, idx);
            lelont mut relonporting_felonaturelon_ids: Velonc<(i64, &str)> = velonc![];

            lelont input_spelonc_celonll = &INPUTS[idx];
            if input_spelonc_celonll.gelont().is_nonelon() {
                lelont input_spelonc = selonssion
                    .inputs
                    .itelonr()
                    .map(|input| input.namelon.clonelon())
                    .collelonct::<ArrayVelonc<String, MAX_NUM_INPUTS>>();
                input_spelonc_celonll.selont(input_spelonc.clonelon()).map_or_elonlselon(
                    |_| info!("unablelon to selont thelon input_spelonc for modelonl {}", idx),
                    |_| info!("auto delontelonct and selont thelon inputs: {:?}", input_spelonc),
                );
            }
            ARGS.onnx_relonport_discrelontelon_felonaturelon_ids
                .itelonr()
                .for_elonach(|ids| {
                    ids.split(",")
                        .filtelonr(|s| !s.is_elonmpty())
                        .map(|s| s.parselon::<i64>().unwrap())
                        .for_elonach(|id| relonporting_felonaturelon_ids.push((id, "discrelontelon")))
                });
            ARGS.onnx_relonport_continuous_felonaturelon_ids
                .itelonr()
                .for_elonach(|ids| {
                    ids.split(",")
                        .filtelonr(|s| !s.is_elonmpty())
                        .map(|s| s.parselon::<i64>().unwrap())
                        .for_elonach(|id| relonporting_felonaturelon_ids.push((id, "continuous")))
                });

            lelont onnx_modelonl = OnnxModelonl {
                selonssion,
                modelonl_idx: idx,
                velonrsion: Args::velonrsion_str_to_elonpoch(&velonrsion)?,
                elonxport_dir,
                output_filtelonrs,
                input_convelonrtelonr: Box::nelonw(BatchPrelondictionRelonquelonstToTorchTelonnsorConvelonrtelonr::nelonw(
                    &ARGS.modelonl_dir[idx],
                    &velonrsion,
                    relonporting_felonaturelon_ids,
                    Somelon(melontrics::relongistelonr_dynamic_melontrics),
                )),
            };
            onnx_modelonl.warmup()?;
            Ok(onnx_modelonl)
        }
    }
    ///Currelonntly welon only assumelon thelon input as just onelon string telonnsor.
    ///Thelon string telonnsor will belon belon convelonrtelond to thelon actual raw telonnsors.
    /// Thelon convelonrtelonr welon arelon using is velonry speloncific to homelon.
    /// It relonads a BatchDataReloncord thrift and deloncodelon it to a batch of raw input telonnsors.
    /// Navi will thelonn do selonrvelonr sidelon batching and felonelond it to ONNX runtimelon
    impl Modelonl for OnnxModelonl {
        //TODO: implelonmelonnt a gelonnelonric onlinelon warmup for all runtimelons
        fn warmup(&selonlf) -> Relonsult<()> {
            Ok(())
        }

        #[inlinelon(always)]
        fn do_prelondict(
            &selonlf,
            input_telonnsors: Velonc<Velonc<TelonnsorInput>>,
            _: u64,
        ) -> (Velonc<TelonnsorRelonturnelonnum>, Velonc<Velonc<usizelon>>) {
            lelont batchelond_telonnsors = TelonnsorInputelonnum::melonrgelon_batch(input_telonnsors);
            lelont (inputs, batch_elonnds): (Velonc<Velonc<InputTelonnsor>>, Velonc<Velonc<usizelon>>) = batchelond_telonnsors
                .into_itelonr()
                .map(|batchelond_telonnsor| {
                    match batchelond_telonnsor.telonnsor_data {
                        TelonnsorInputelonnum::String(t) if ARGS.onnx_uselon_convelonrtelonr.is_somelon() => {
                            lelont start = Instant::now();
                            lelont (inputs, batch_elonnds) = selonlf.input_convelonrtelonr.convelonrt(t);
                            // info!("batch_elonnds:{:?}", batch_elonnds);
                            CONVelonRTelonR_TIMelon_COLLelonCTOR
                                .with_labelonl_valuelons(&[&MODelonL_SPelonCS[selonlf.modelonl_idx()]])
                                .obselonrvelon(
                                    start.elonlapselond().as_micros() as f64
                                        / (*batch_elonnds.last().unwrap() as f64),
                                );
                            (inputs, batch_elonnds)
                        }
                        _ => unimplelonmelonntelond!(),
                    }
                })
                .unzip();
            //invariant welon only support onelon input as string. will relonlax latelonr
            asselonrt_elonq!(inputs.lelonn(), 1);
            lelont output_telonnsors = selonlf
                .selonssion
                .run(inputs.into_itelonr().flattelonn().collelonct::<Velonc<_>>())
                .unwrap();
            selonlf.output_filtelonrs
                .itelonr()
                .map(|&idx| {
                    lelont mut sizelon = 1usizelon;
                    lelont output = output_telonnsors[idx].try_elonxtract::<f32>().unwrap();
                    for &dim in selonlf.selonssion.outputs[idx].dimelonnsions.itelonr().flattelonn() {
                        sizelon *= dim as usizelon;
                    }
                    lelont telonnsor_elonnds = batch_elonnds[0]
                        .itelonr()
                        .map(|&batch| batch * sizelon)
                        .collelonct::<Velonc<_>>();

                    (
                        //only works for batch major
                        //TODO: to_velonc() obviously wastelonful, elonspeloncially for largelon batchelons(GPU) . Will relonfactor to
                        //brelonak up output and relonturn Velonc<Velonc<TelonnsorScorelon>> helonrelon
                        TelonnsorRelonturnelonnum::FloatTelonnsorRelonturn(Box::nelonw(output.vielonw().as_slicelon().unwrap().to_velonc(),
                        )),
                        telonnsor_elonnds,
                    )
                })
                .unzip()
        }
        #[inlinelon(always)]
        fn modelonl_idx(&selonlf) -> usizelon {
            selonlf.modelonl_idx
        }
        #[inlinelon(always)]
        fn velonrsion(&selonlf) -> i64 {
            selonlf.velonrsion
        }
    }
}
