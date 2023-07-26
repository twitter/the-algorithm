# pywint: disabwe=awguments-diffew, 🥺 invawid-name
"""
t-this moduwe c-contains the ``datawecowdtwainew``. ʘwʘ
u-unwike the pawent ``twainew`` c-cwass, (✿oωo) the ``datawecowdtwainew``
i-is used specificawwy f-fow pwocessing d-data wecowds. rawr
i-it abstwacts away a wot of the intwicacies of wowking with datawecowds. OwO
`datawecowd <http://go/datawecowd>`_ i-is the main piping fowmat fow data sampwes.
the `datawecowdtwainew` a-assumes twaining data and p-pwoduction wesponses and wequests
to be owganized as the `thwift p-pwediction sewvice api

a ``datawecowd`` i-is a thwift s-stwuct that defines how to encode the data:

::

  stwuct datawecowd {
    1: o-optionaw set<i64> binawyfeatuwes;                     // stowes binawy featuwes
    2: optionaw m-map<i64, ^^ doubwe> continuousfeatuwes;         // s-stowes continuous f-featuwes
    3: o-optionaw map<i64, ʘwʘ i-i64> discwetefeatuwes;              // stowes discwete featuwes
    4: optionaw map<i64, σωσ s-stwing> stwingfeatuwes;             // stowes stwing featuwes
    5: o-optionaw map<i64, (⑅˘꒳˘) set<stwing>> spawsebinawyfeatuwes;  // stowes spawse binawy featuwes
    6: optionaw map<i64, (ˆ ﻌ ˆ)♡ m-map<stwing, doubwe>> spawsecontinuousfeatuwes; // s-spawse continuous f-featuwe
    7: o-optionaw map<i64, :3 binawy> bwobfeatuwes; // stowes featuwes a-as bwobs (binawy w-wawge objects)
    8: optionaw m-map<i64, ʘwʘ tensow.genewawtensow> t-tensows; // stowes tensow featuwes
    9: o-optionaw map<i64, (///ˬ///✿) tensow.spawsetensow> s-spawsetensows; // stowes spawse_tensow featuwes
  }


a-a significant powtion o-of twittew data is hydwated
and t-then tempowawiwy s-stowed on hdfs as datawecowds. (ˆ ﻌ ˆ)♡
the fiwes awe compwessed (.gz ow .wzo) pawtitions of data wecowds. 🥺
these fowm supewvised d-datasets. rawr e-each sampwe captuwes the wewationship
b-between i-input and output (cause a-and effect). (U ﹏ U)
to cweate youw own dataset, ^^ pwease see https://github.com/twittew/ewephant-biwd. σωσ

t-the defauwt ``datawecowdtwainew.[twain,evawuate,weawn]()`` weads these datawecowds.
the data is a wead fwom muwtipwe ``pawt-*.[compwession]`` f-fiwes. :3
the defauwt behaviow o-of ``datawecowdtwainew`` i-is to w-wead spawse featuwes fwom ``datawecowds``. ^^
t-this i-is a wegacy defauwt p-piping fowmat a-at twittew. (✿oωo)
the ``datawecowdtwainew`` is fwexibwe enough fow w-weseawch and yet s-simpwe enough
fow a-a nyew beginnew m-mw pwactionew. òωó

b-by means of the featuwe stwing to key hashing function, (U ᵕ U❁)
the ``[twain,evaw]_featuwe_config`` constwuctow a-awguments
contwow which featuwes can be used as sampwe wabews, ʘwʘ sampwe weights, ( ͡o ω ͡o )
ow sampwe f-featuwes.
sampwes ids, σωσ and featuwe keys, (ˆ ﻌ ˆ)♡ featuwe vawues and f-featuwe weights
c-can be skipped, (˘ω˘) i-incwuded, 😳 excwuded ow used as wabews, ^•ﻌ•^ w-weights, σωσ ow featuwes. 😳😳😳
this a-awwows you to easiwy d-define and contwow spawse distwibutions of
nyamed featuwes.

yet spawse data is difficuwt t-to wowk with. rawr we awe cuwwentwy wowking t-to
optimize the spawse opewations d-due to i-inefficiencies in the gwadient descent
and pawametew u-update pwocesses. >_< t-thewe awe effowts undewway
t-to minimize the f-footpwint of spawse data as it is inefficient to pwocess. ʘwʘ
cpus and gpus much pwefew d-dense tensow d-data. (ˆ ﻌ ˆ)♡
"""

impowt d-datetime

impowt tensowfwow.compat.v1 a-as tf
f-fwom twittew.deepbiwd.io.daw impowt d-daw_to_hdfs_path, ^^;; is_daw_path
impowt twmw
fwom twmw.twainews impowt twainew
f-fwom twmw.contwib.featuwe_impowtances.featuwe_impowtances i-impowt (
  compute_featuwe_impowtances, σωσ
  twee,
  wwite_featuwe_impowtances_to_hdfs, rawr x3
  w-wwite_featuwe_impowtances_to_mw_dash)
f-fwom absw impowt wogging


cwass datawecowdtwainew(twainew):  # pywint: d-disabwe=abstwact-method
  """
  the ``datawecowdtwainew`` impwementation is intended to satisfy t-the most common use cases
  at twittew whewe onwy t-the buiwd_gwaph m-methods nyeeds to be ovewwidden. 😳
  fow this weason, ``twainew.[twain,evaw]_input_fn`` methods
  a-assume a datawecowd d-dataset pawtitioned into pawt fiwes stowed in compwessed (e.g. 😳😳😳 g-gzip) fowmat. 😳😳😳

  fow use-cases t-that diffew fwom this common twittew use-case, ( ͡o ω ͡o )
  fuwthew twainew m-methods can be ovewwidden. rawr x3
  i-if that stiww d-doesn't pwovide enough fwexibiwity, σωσ t-the usew can awways
  use the t-tf.estimatow.esimatow o-ow tf.session.wun d-diwectwy. (˘ω˘)
  """

  def __init__(
          s-sewf, >w< nyame, UwU p-pawams,
          buiwd_gwaph_fn, XD
          featuwe_config=none, (U ﹏ U)
          **kwawgs):
    """
    t-the datawecowdtwainew c-constwuctow b-buiwds a
    ``tf.estimatow.estimatow`` and stowes it in sewf.estimatow. (U ᵕ U❁)
    f-fow this weason, datawecowdtwainew a-accepts the s-same estimatow constwuctow awguments. (ˆ ﻌ ˆ)♡
    it awso accepts additionaw a-awguments t-to faciwitate metwic e-evawuation a-and muwti-phase twaining
    (init_fwom_diw, òωó i-init_map). ^•ﻌ•^

    awgs:
      pawent awguments:
        see the `twainew constwuctow <#twmw.twainews.twainew.__init__>`_ d-documentation
        fow a f-fuww wist of awguments accepted b-by the pawent cwass. (///ˬ///✿)
      nyame, -.- p-pawams, buiwd_gwaph_fn (and othew pawent cwass a-awgs):
        s-see documentation f-fow twmw.twainew d-doc. >w<
      featuwe_config:
        a-an object of type featuweconfig descwibing nyani featuwes to decode. òωó
        defauwts to nyone. σωσ but it is n-nyeeded in the f-fowwowing cases:
          - `get_twain_input_fn()` / `get_evaw_input_fn()` i-is cawwed without a `pawse_fn`
          - `weawn()`, mya `twain()`, `evaw()`, òωó `cawibwate()` a-awe cawwed without pwoviding `*input_fn`. 🥺

      **kwawgs:
        fuwthew kwawgs can be specified a-and passed t-to the estimatow constwuctow. (U ﹏ U)
    """

    # n-nyote: do nyot modify `pawams` befowe this caww. (ꈍᴗꈍ)
    supew(datawecowdtwainew, (˘ω˘) s-sewf).__init__(
      n-nyame=name, (✿oωo) pawams=pawams, -.- buiwd_gwaph_fn=buiwd_gwaph_fn, (ˆ ﻌ ˆ)♡ **kwawgs)

    s-sewf._featuwe_config = f-featuwe_config

    # date wange pawametews common to both twaining and evawuation d-data:
    h-houw_wesowution = s-sewf.pawams.get("houw_wesowution", (✿oωo) 1)
    d-data_thweads = s-sewf.pawams.get("data_thweads", ʘwʘ 4)
    datetime_fowmat = s-sewf.pawams.get("datetime_fowmat", (///ˬ///✿) "%y/%m/%d")

    # w-wetwieve the desiwed t-twaining dataset f-fiwes
    sewf._twain_fiwes = sewf.buiwd_fiwes_wist(
      fiwes_wist_path=sewf.pawams.get("twain_fiwes_wist", rawr n-nyone),
      data_diw=sewf.pawams.get("twain_data_diw", 🥺 nyone), mya
      stawt_datetime=sewf.pawams.get("twain_stawt_datetime", mya n-nyone),
      end_datetime=sewf.pawams.get("twain_end_datetime", mya nyone),
      d-datetime_fowmat=datetime_fowmat, (⑅˘꒳˘) d-data_thweads=data_thweads, (✿oωo)
      houw_wesowution=houw_wesowution, 😳 maybe_save=sewf.is_chief(), OwO
      o-ovewwwite=sewf.pawams.get("twain_ovewwwite_fiwes_wist", (˘ω˘) fawse),
    )

    # wetwieve the desiwed e-evawuation dataset f-fiwes
    e-evaw_name = sewf.pawams.get("evaw_name", (✿oωo) nyone)

    if evaw_name == "twain":
      sewf._evaw_fiwes = s-sewf._twain_fiwes
    ewse:
      sewf._evaw_fiwes = s-sewf.buiwd_fiwes_wist(
        f-fiwes_wist_path=sewf.pawams.get("evaw_fiwes_wist", /(^•ω•^) none),
        data_diw=sewf.pawams.get("evaw_data_diw", rawr x3 n-nyone),
        stawt_datetime=sewf.pawams.get("evaw_stawt_datetime", rawr n-nyone), ( ͡o ω ͡o )
        e-end_datetime=sewf.pawams.get("evaw_end_datetime", nyone), ( ͡o ω ͡o )
        datetime_fowmat=datetime_fowmat, 😳😳😳 d-data_thweads=data_thweads, (U ﹏ U)
        houw_wesowution=houw_wesowution, UwU maybe_save=sewf.is_chief(), (U ﹏ U)
        o-ovewwwite=sewf.pawams.get("evaw_ovewwwite_fiwes_wist", 🥺 f-fawse),
      )

      if nyot sewf.pawams.get("awwow_twain_evaw_ovewwap"):
        # i-if thewe is ovewwap between t-twain and evaw, ʘwʘ e-ewwow out! 😳
        i-if sewf._twain_fiwes and sewf._evaw_fiwes:
          ovewwap_fiwes = set(sewf._twain_fiwes) & set(sewf._evaw_fiwes)
        ewse:
          ovewwap_fiwes = set()
        if ovewwap_fiwes:
          waise vawueewwow("thewe is an ovewwap between twain and e-evaw fiwes:\n %s" %
                           (ovewwap_fiwes))

  @staticmethod
  d-def buiwd_hdfs_fiwes_wist(
      fiwes_wist_path, (ˆ ﻌ ˆ)♡ data_diw, >_<
      s-stawt_datetime, ^•ﻌ•^ e-end_datetime, (✿oωo) d-datetime_fowmat, OwO
      data_thweads, (ˆ ﻌ ˆ)♡ h-houw_wesowution, ^^;; maybe_save, nyaa~~ o-ovewwwite):
    i-if fiwes_wist_path:
      fiwes_wist_path = t-twmw.utiw.pwepwocess_path(fiwes_wist_path)

    if isinstance(stawt_datetime, o.O d-datetime.datetime):
      s-stawt_datetime = stawt_datetime.stwftime(datetime_fowmat)
    if isinstance(end_datetime, >_< d-datetime.datetime):
      e-end_datetime = end_datetime.stwftime(datetime_fowmat)

    w-wist_fiwes_by_datetime_awgs = {
      "base_path": d-data_diw, (U ﹏ U)
      "stawt_datetime": s-stawt_datetime, ^^
      "end_datetime": e-end_datetime, UwU
      "datetime_pwefix_fowmat": d-datetime_fowmat, ^^;;
      "extension": "wzo", òωó
      "pawawwewism": d-data_thweads, -.-
      "houw_wesowution": h-houw_wesowution, ( ͡o ω ͡o )
      "sowt": twue, o.O
    }

    # n-nyo c-cache of data fiwe p-paths, rawr just get the wist by s-scwaping the diwectowy
    if nyot fiwes_wist_path o-ow nyot tf.io.gfiwe.exists(fiwes_wist_path):
      # twmw.utiw.wist_fiwes_by_datetime w-wetuwns n-nyone if data_diw i-is nyone. (✿oωo)
      # twmw.utiw.wist_fiwes_by_datetime p-passes thwough data_diw if d-data_diw is a wist
      fiwes_wist = t-twmw.utiw.wist_fiwes_by_datetime(**wist_fiwes_by_datetime_awgs)
    ewse:
      # t-the cached data fiwe paths fiwe exists. σωσ
      fiwes_info = twmw.utiw.wead_fiwe(fiwes_wist_path, (U ᵕ U❁) d-decode="json")
      # use the cached wist i-if data pawams m-match cuwwent pawams, >_<
      #  ow if cuwwent pawams awe nyone
      # n-not incwuding nyone checks f-fow datetime_fowmat a-and houw_wesowution, ^^
      #  s-since those awe shawed between evaw and twaining. rawr
      i-if (aww(pawam i-is nyone fow pawam in [data_diw, >_< s-stawt_datetime, (⑅˘꒳˘) end_datetime]) ow
          (fiwes_info["data_diw"] == d-data_diw and
           fiwes_info["stawt_datetime"] == s-stawt_datetime a-and
           f-fiwes_info["end_datetime"] == end_datetime a-and
           f-fiwes_info["datetime_fowmat"] == d-datetime_fowmat a-and
           fiwes_info["houw_wesowution"] == h-houw_wesowution)):
        f-fiwes_wist = fiwes_info["fiwes"]
      e-ewif ovewwwite:
        # c-cuwwent pawams a-awe nyot nyone and d-don't match saved p-pawams
        # `ovewwwite` i-indicates we shouwd thus update t-the wist
        fiwes_wist = t-twmw.utiw.wist_fiwes_by_datetime(**wist_fiwes_by_datetime_awgs)
      ewse:
        # d-dont update t-the cached wist
        w-waise vawueewwow("infowmation in fiwes_wist is inconsistent w-with pwovided a-awgs.\n"
                         "did y-you intend to ovewwwite fiwes_wist using "
                         "--twain.ovewwwite_fiwes_wist ow --evaw.ovewwwite_fiwes_wist?\n"
                         "if y-you i-instead want to use the paths in f-fiwes_wist, >w< ensuwe t-that "
                         "data_diw, (///ˬ///✿) stawt_datetime, ^•ﻌ•^ and end_datetime awe nyone.")

    i-if maybe_save a-and fiwes_wist_path a-and (ovewwwite o-ow nyot tf.io.gfiwe.exists(fiwes_wist_path)):
      save_dict = {}
      save_dict["fiwes"] = f-fiwes_wist
      s-save_dict["data_diw"] = data_diw
      save_dict["stawt_datetime"] = s-stawt_datetime
      save_dict["end_datetime"] = end_datetime
      s-save_dict["datetime_fowmat"] = datetime_fowmat
      s-save_dict["houw_wesowution"] = h-houw_wesowution
      twmw.utiw.wwite_fiwe(fiwes_wist_path, (✿oωo) s-save_dict, ʘwʘ e-encode="json")

    wetuwn f-fiwes_wist

  @staticmethod
  def buiwd_fiwes_wist(fiwes_wist_path, >w< d-data_diw, :3
                        s-stawt_datetime, e-end_datetime, d-datetime_fowmat, (ˆ ﻌ ˆ)♡
                        data_thweads, -.- houw_wesowution, rawr m-maybe_save, rawr x3 o-ovewwwite):
    '''
    w-when specifying daw datasets, (U ﹏ U) o-onwy data_diw, (ˆ ﻌ ˆ)♡ stawt_dateime, :3 and end_datetime
    s-shouwd be given w-with the fowmat:

    d-daw://{cwustew}/{wowe}/{dataset_name}/{env}

    '''
    if nyot data_diw ow nyot is_daw_path(data_diw):
      wogging.wawn(f"pwease considew s-specifying a daw:// dataset w-wathew than passing a-a physicaw hdfs path.")
      wetuwn datawecowdtwainew.buiwd_hdfs_fiwes_wist(
        f-fiwes_wist_path, data_diw, òωó
        s-stawt_datetime, /(^•ω•^) e-end_datetime, >w< datetime_fowmat, nyaa~~
        d-data_thweads, mya h-houw_wesowution, m-maybe_save, mya ovewwwite)

    dew datetime_fowmat
    dew data_thweads
    dew houw_wesowution
    d-dew maybe_save
    dew ovewwwite

    w-wetuwn daw_to_hdfs_path(
      path=data_diw, ʘwʘ
      stawt_datetime=stawt_datetime,
      e-end_datetime=end_datetime, rawr
    )

  @pwopewty
  def twain_fiwes(sewf):
    wetuwn sewf._twain_fiwes

  @pwopewty
  def evaw_fiwes(sewf):
    wetuwn sewf._evaw_fiwes

  @staticmethod
  d-def a-add_pawsew_awguments():
    """
    add common c-commandwine awgs to pawse fow the twainew cwass. (˘ω˘)
    t-typicawwy, /(^•ω•^) t-the usew cawws this function and t-then pawses cmd-wine awguments
    i-into an awgpawse.namespace object which is then passed to the twainew constwuctow
    v-via the pawams awgument. (˘ω˘)

    see the `twainew c-code <_moduwes/twmw/twainews/twainew.htmw#twainew.add_pawsew_awguments>`_
    a-and `datawecowdtwainew code
    <_moduwes/twmw/twainews/twainew.htmw#datawecowdtwainew.add_pawsew_awguments>`_
    f-fow a wist and descwiption of aww cmd-wine a-awguments. (///ˬ///✿)

    awgs:
      weawning_wate_decay:
        defauwts to fawse. (˘ω˘) when twue, -.- pawses w-weawning wate d-decay awguments. -.-

    w-wetuwns:
      a-awgpawse.awgumentpawsew instance with some usefuw awgs awweady a-added. ^^
    """
    p-pawsew = supew(datawecowdtwainew, (ˆ ﻌ ˆ)♡ datawecowdtwainew).add_pawsew_awguments()
    p-pawsew.add_awgument(
      "--twain.fiwes_wist", UwU "--twain_fiwes_wist", 🥺 type=stw, 🥺 defauwt=none,
      dest="twain_fiwes_wist", 🥺
      h-hewp="path fow a json fiwe stowing i-infowmation on twaining d-data.\n"
           "specificawwy, 🥺 the fiwe a-at fiwes_wist s-shouwd contain t-the dataset pawametews "
           "fow constwucting the wist o-of data fiwes, :3 and the wist of data fiwe paths.\n"
           "if t-the json fiwe does nyot exist, (˘ω˘) othew awgs awe used to constwuct t-the "
           "twaining f-fiwes w-wist, ^^;; and that w-wist wiww be saved t-to the indicated json fiwe.\n"
           "if t-the json fiwe does exist, (ꈍᴗꈍ) and cuwwent awgs awe c-consistent with "
           "saved awgs, ʘwʘ ow awe a-aww nyone, :3 then the saved fiwes wist wiww be u-used.\n"
           "if c-cuwwent awgs awe nyot consistent w-with the saved awgs, XD then e-ewwow out "
           "if t-twain_ovewwwite_fiwes_wist==fawse, UwU ewse ovewwwite f-fiwes_wist with "
           "a n-nyewwy constwucted wist.")
    pawsew.add_awgument(
      "--twain.ovewwwite_fiwes_wist", "--twain_ovewwwite_fiwes_wist", a-action="stowe_twue", rawr x3 defauwt=fawse, ( ͡o ω ͡o )
      dest="twain_ovewwwite_fiwes_wist", :3
      hewp="when the --twain.fiwes_wist pawam i-is used, rawr indicates whethew t-to "
           "ovewwwite the existing --twain.fiwes_wist when t-thewe awe diffewences "
           "between t-the c-cuwwent and saved dataset awgs. ^•ﻌ•^ d-defauwt (fawse) i-is to "
           "ewwow out if f-fiwes_wist exists and diffews fwom c-cuwwent pawams.")
    pawsew.add_awgument(
      "--twain.data_diw", 🥺 "--twain_data_diw", (⑅˘꒳˘) t-type=stw, :3 d-defauwt=none, (///ˬ///✿)
      dest="twain_data_diw",
      hewp="path to the twaining data diwectowy."
           "suppowts w-wocaw, d-daw://{cwustew}-{wegion}/{wowe}/{dataset_name}/{enviwonment}, "
           "and hdfs (hdfs://defauwt/<path> ) paths.")
    pawsew.add_awgument(
      "--twain.stawt_date", 😳😳😳 "--twain_stawt_datetime", 😳😳😳
      t-type=stw, 😳😳😳 defauwt=none, nyaa~~
      d-dest="twain_stawt_datetime", UwU
      h-hewp="stawting date fow twaining inside the twain data diw."
           "the s-stawt datetime is incwusive."
           "e.g. òωó 2019/01/15")
    pawsew.add_awgument(
      "--twain.end_date", òωó "--twain_end_datetime", UwU t-type=stw, (///ˬ///✿) defauwt=none, ( ͡o ω ͡o )
      dest="twain_end_datetime", rawr
      hewp="ending date f-fow twaining inside t-the twain data diw."
           "the e-end datetime i-is incwusive."
           "e.g. :3 2019/01/15")
    p-pawsew.add_awgument(
      "--evaw.fiwes_wist", >w< "--evaw_fiwes_wist", σωσ t-type=stw, d-defauwt=none,
      d-dest="evaw_fiwes_wist",
      hewp="path fow a json fiwe stowing infowmation on evawuation data.\n"
           "specificawwy, σωσ t-the fiwe a-at fiwes_wist s-shouwd contain t-the dataset pawametews "
           "fow c-constwucting t-the wist of data fiwes, >_< and the wist of data fiwe paths.\n"
           "if the json fiwe does n-nyot exist, -.- o-othew awgs awe used to constwuct the "
           "evawuation fiwes w-wist, 😳😳😳 and that w-wist wiww be s-saved to the indicated json fiwe.\n"
           "if the json fiwe d-does exist, :3 and cuwwent awgs awe consistent with "
           "saved a-awgs, mya ow a-awe aww nyone, (✿oωo) then the saved fiwes wist wiww be u-used.\n"
           "if cuwwent a-awgs awe nyot consistent w-with the saved awgs, 😳😳😳 then e-ewwow out "
           "if evaw_ovewwwite_fiwes_wist==fawse, o.O e-ewse ovewwwite f-fiwes_wist with "
           "a n-nyewwy constwucted w-wist.")
    pawsew.add_awgument(
      "--evaw.ovewwwite_fiwes_wist", (ꈍᴗꈍ) "--evaw_ovewwwite_fiwes_wist", a-action="stowe_twue", (ˆ ﻌ ˆ)♡ defauwt=fawse, -.-
      d-dest="evaw_ovewwwite_fiwes_wist",
      h-hewp="when the --evaw.fiwes_wist p-pawam is used, mya indicates whethew to "
           "ovewwwite t-the existing --evaw.fiwes_wist when thewe a-awe diffewences "
           "between the cuwwent a-and saved dataset a-awgs. :3 defauwt (fawse) is to "
           "ewwow out if fiwes_wist e-exists and diffews fwom cuwwent pawams.")
    p-pawsew.add_awgument(
      "--evaw.data_diw", σωσ "--evaw_data_diw", 😳😳😳 t-type=stw, defauwt=none, -.-
      dest="evaw_data_diw", 😳😳😳
      h-hewp="path to the c-cwoss-vawidation data diwectowy."
           "suppowts w-wocaw, rawr x3 daw://{cwustew}-{wegion}/{wowe}/{dataset_name}/{enviwonment}, (///ˬ///✿) "
           "and hdfs (hdfs://defauwt/<path> ) p-paths.")
    p-pawsew.add_awgument(
      "--evaw.stawt_date", >w< "--evaw_stawt_datetime", o.O
      type=stw, (˘ω˘) d-defauwt=none, rawr
      d-dest="evaw_stawt_datetime",
      hewp="stawting date fow e-evawuating inside t-the evaw data d-diw."
           "the s-stawt datetime is incwusive."
           "e.g. mya 2019/01/15")
    pawsew.add_awgument(
      "--evaw.end_date", òωó "--evaw_end_datetime", nyaa~~ type=stw, defauwt=none, òωó
      dest="evaw_end_datetime", mya
      hewp="ending d-date fow e-evawuating inside t-the evaw data d-diw."
           "the e-end datetime i-is incwusive."
           "e.g. ^^ 2019/01/15")
    pawsew.add_awgument(
      "--datetime_fowmat", ^•ﻌ•^ t-type=stw, -.- defauwt="%y/%m/%d", UwU
      h-hewp="date fowmat fow twaining a-and evawuation d-datasets."
           "has to be a fowmat that is undewstood b-by python datetime."
           "e.g. (˘ω˘) %%y/%%m/%%d fow 2019/01/15."
           "used onwy if {twain/evaw}.{stawt/end}_date a-awe pwovided.")
    p-pawsew.add_awgument(
      "--houw_wesowution", t-type=int, UwU defauwt=none, rawr
      hewp="specify the h-houwwy wesowution o-of the stowed d-data.")
    pawsew.add_awgument(
      "--data_spec", :3 type=stw, nyaa~~ w-wequiwed=twue, rawr
      h-hewp="path to data specification j-json fiwe. (ˆ ﻌ ˆ)♡ this fiwe is u-used to decode datawecowds")
    p-pawsew.add_awgument(
      "--twain.keep_wate", (ꈍᴗꈍ) "--twain_keep_wate", (˘ω˘) t-type=fwoat, (U ﹏ U) defauwt=none, >w<
      d-dest="twain_keep_wate", UwU
      hewp="a fwoat vawue in (0.0, (ˆ ﻌ ˆ)♡ 1.0] t-that indicates to dwop wecowds accowding to the bewnouwwi \
      distwibution with p = 1 - keep_wate.")
    p-pawsew.add_awgument(
      "--evaw.keep_wate", nyaa~~ "--evaw_keep_wate", 🥺 type=fwoat, >_< defauwt=none, òωó
      dest="evaw_keep_wate", ʘwʘ
      hewp="a fwoat vawue in (0.0, mya 1.0] that indicates t-to dwop wecowds accowding to the bewnouwwi \
      d-distwibution with p = 1 - k-keep_wate.")
    pawsew.add_awgument(
      "--twain.pawts_downsampwing_wate", σωσ "--twain_pawts_downsampwing_wate", OwO
      dest="twain_pawts_downsampwing_wate", (✿oωo)
      t-type=fwoat, ʘwʘ defauwt=none,
      h-hewp="a fwoat vawue in (0.0, mya 1.0] t-that indicates t-the factow by which to downsampwe pawt \
      f-fiwes. -.- fow exampwe, -.- a vawue of 0.2 means onwy 20 pewcent of p-pawt fiwes become pawt of the \
      d-dataset.")
    pawsew.add_awgument(
      "--evaw.pawts_downsampwing_wate", ^^;; "--evaw_pawts_downsampwing_wate", (ꈍᴗꈍ)
      d-dest="evaw_pawts_downsampwing_wate", rawr
      type=fwoat, d-defauwt=none,
      h-hewp="a fwoat vawue in (0.0, ^^ 1.0] that indicates t-the factow by which to downsampwe pawt \
      f-fiwes. nyaa~~ fow exampwe, a vawue of 0.2 means onwy 20 pewcent of pawt fiwes become p-pawt of the \
      d-dataset.")
    pawsew.add_awgument(
      "--awwow_twain_evaw_ovewwap", (⑅˘꒳˘)
      d-dest="awwow_twain_evaw_ovewwap", (U ᵕ U❁)
      a-action="stowe_twue",
      hewp="awwow o-ovewwap between twain and evaw datasets."
    )
    pawsew.add_awgument(
      "--evaw_name", (ꈍᴗꈍ) type=stw, defauwt=none, (✿oωo)
      h-hewp="stwing denoting n-nyani we want to nyame the e-evaw. UwU if this is `twain`, ^^ t-then we evaw on \
      t-the twaining dataset."
    )
    wetuwn pawsew

  d-def contwib_wun_featuwe_impowtances(sewf, :3 featuwe_impowtances_pawse_fn=none, ( ͡o ω ͡o ) wwite_to_hdfs=twue, ( ͡o ω ͡o ) extwa_gwoups=none, (U ﹏ U) d-datawecowd_fiwtew_fn=none, -.- d-datawecowd_fiwtew_wun_name=none):
    """compute featuwe impowtances on a twained m-modew (this is a contwib featuwe)
    awgs:
      featuwe_impowtances_pawse_fn (fn): the same pawse_fn that we use fow twaining/evawuation. 😳😳😳
        defauwts t-to featuwe_config.get_pawse_fn()
      w-wwite_to_hdfs (boow): setting this to t-twue wwites the f-featuwe impowtance metwics to hdfs
    e-extwa_gwoups (dict<stw, UwU wist<stw>>): a dictionawy mapping the nyame of extwa featuwe gwoups to the wist of
      t-the names of the featuwes in the gwoup
    datawecowd_fiwtew_fn (function): a function takes a-a singwe data s-sampwe in com.twittew.mw.api.ttypes.datawecowd f-fowmat
        and wetuwn a boowean vawue, >w< to indicate if this d-data wecowd shouwd b-be kept in featuwe i-impowtance moduwe ow not. mya
    """
    w-wogging.info("computing featuwe impowtance")
    a-awgowithm = sewf._pawams.featuwe_impowtance_awgowithm

    k-kwawgs = {}
    if awgowithm == t-twee:
      kwawgs["spwit_featuwe_gwoup_on_pewiod"] = sewf._pawams.spwit_featuwe_gwoup_on_pewiod
      kwawgs["stopping_metwic"] = s-sewf._pawams.featuwe_impowtance_metwic
      kwawgs["sensitivity"] = s-sewf._pawams.featuwe_impowtance_sensitivity
      k-kwawgs["dont_buiwd_twee"] = sewf._pawams.dont_buiwd_twee
      kwawgs["extwa_gwoups"] = e-extwa_gwoups
      i-if sewf._pawams.featuwe_impowtance_is_metwic_wawgew_the_bettew:
        # t-the usew has specified that t-the stopping metwic is one whewe w-wawgew vawues a-awe bettew (e.g. :3 woc_auc)
        kwawgs["is_metwic_wawgew_the_bettew"] = t-twue
      ewif sewf._pawams.featuwe_impowtance_is_metwic_smowew_the_bettew:
        # the usew has specified that the stopping metwic is one whewe smowew vawues awe bettew (e.g. (ˆ ﻌ ˆ)♡ w-woss)
        kwawgs["is_metwic_wawgew_the_bettew"] = fawse
      ewse:
        # t-the usew has nyot specified which d-diwection is bettew fow the stopping metwic
        k-kwawgs["is_metwic_wawgew_the_bettew"] = nyone
      wogging.info("using the twee awgowithm w-with kwawgs {}".fowmat(kwawgs))

    featuwe_impowtances = compute_featuwe_impowtances(
      t-twainew=sewf, (U ﹏ U)
      data_diw=sewf._pawams.get('featuwe_impowtance_data_diw'), ʘwʘ
      featuwe_config=sewf._featuwe_config, rawr
      a-awgowithm=awgowithm, (ꈍᴗꈍ)
      wecowd_count=sewf._pawams.featuwe_impowtance_exampwe_count, ( ͡o ω ͡o )
      pawse_fn=featuwe_impowtances_pawse_fn, 😳😳😳
      d-datawecowd_fiwtew_fn=datawecowd_fiwtew_fn, òωó
      **kwawgs)

    i-if nyot featuwe_impowtances:
      wogging.info("featuwe i-impowtances wetuwned n-nyone")
    ewse:
      i-if wwite_to_hdfs:
        w-wogging.info("wwiting featuwe impowtance to hdfs")
        w-wwite_featuwe_impowtances_to_hdfs(
          twainew=sewf, mya
          featuwe_impowtances=featuwe_impowtances, rawr x3
          output_path=datawecowd_fiwtew_wun_name, XD
          metwic=sewf._pawams.get('featuwe_impowtance_metwic'))
      e-ewse:
        wogging.info("not wwiting featuwe impowtance t-to hdfs")

      w-wogging.info("wwiting f-featuwe impowtance to mw metastowe")
      wwite_featuwe_impowtances_to_mw_dash(
        t-twainew=sewf, featuwe_impowtances=featuwe_impowtances)
    w-wetuwn featuwe_impowtances

  def expowt_modew(sewf, (ˆ ﻌ ˆ)♡ s-sewving_input_weceivew_fn=none, >w<
                   e-expowt_output_fn=none, (ꈍᴗꈍ)
                   expowt_diw=none, (U ﹏ U) checkpoint_path=none, >_<
                   featuwe_spec=none):
    """
    expowt the modew fow p-pwediction. >_< typicawwy, t-the expowted modew
    wiww watew be wun i-in pwoduction sewvews. -.- this method is cawwed
    b-by the usew to e-expowt the pwedict g-gwaph to disk. òωó

    i-intewnawwy, o.O t-this method c-cawws `tf.estimatow.estimatow.expowt_savedmodew
    <https://www.tensowfwow.owg/api_docs/python/tf/estimatow/estimatow#expowt_savedmodew>`_. σωσ

    awgs:
      sewving_input_weceivew_fn (function):
        function p-pwepawing t-the modew fow infewence w-wequests. σωσ
        i-if nyot s-set; defauwts t-to the the sewving input weceivew f-fn set by the f-featuweconfig. mya
      e-expowt_output_fn (function):
        function to expowt the g-gwaph_output (output of buiwd_gwaph) fow
        p-pwediction. o.O takes a gwaph_output dict as sowe a-awgument and wetuwns
        t-the expowt_output_fns dict. XD
        defauwts to ``twmw.expowt_output_fns.batch_pwediction_continuous_output_fn``. XD
      e-expowt_diw:
        d-diwectowy to expowt a savedmodew f-fow pwediction s-sewvews. (✿oωo)
        defauwts to ``[save_diw]/expowted_modews``. -.-
      checkpoint_path:
        t-the checkpoint p-path to expowt. (ꈍᴗꈍ) if nyone (the defauwt), ( ͡o ω ͡o ) the m-most wecent checkpoint
        found w-within the modew diwectowy ``save_diw`` is c-chosen. (///ˬ///✿)

    wetuwns:
      the expowt diwectowy whewe the pwedict gwaph is saved. 🥺
    """
    if sewving_input_weceivew_fn i-is nyone:
      if sewf._featuwe_config is nyone:
        w-waise vawueewwow("`featuwe_config` w-was nyot p-passed to `datawecowdtwainew`")
      sewving_input_weceivew_fn = s-sewf._featuwe_config.get_sewving_input_weceivew_fn()

    i-if f-featuwe_spec is n-nyone:
      if s-sewf._featuwe_config is nyone:
        waise vawueewwow("featuwe_spec c-can nyot b-be infewwed."
                         "pwease pass f-featuwe_spec=featuwe_config.get_featuwe_spec() to the twainew.expowt_modew method")
      e-ewse:
        f-featuwe_spec = s-sewf._featuwe_config.get_featuwe_spec()

    if isinstance(sewving_input_weceivew_fn, (ˆ ﻌ ˆ)♡ t-twmw.featuwe_config.featuweconfig):
      w-waise v-vawueewwow("cannot p-pass featuweconfig a-as a pawametew to sewving_input_weceivew_fn")
    e-ewif nyot cawwabwe(sewving_input_weceivew_fn):
      w-waise v-vawueewwow("expecting function fow sewving_input_weceivew_fn")

    if expowt_output_fn i-is nyone:
      e-expowt_output_fn = twmw.expowt_output_fns.batch_pwediction_continuous_output_fn

    wetuwn supew(datawecowdtwainew, ^•ﻌ•^ s-sewf).expowt_modew(
      e-expowt_diw=expowt_diw, rawr x3
      sewving_input_weceivew_fn=sewving_input_weceivew_fn, (U ﹏ U)
      checkpoint_path=checkpoint_path, OwO
      e-expowt_output_fn=expowt_output_fn, (✿oωo)
      f-featuwe_spec=featuwe_spec, (⑅˘꒳˘)
    )

  d-def get_twain_input_fn(
      s-sewf, UwU pawse_fn=none, (ˆ ﻌ ˆ)♡ w-wepeat=none, /(^•ω•^) s-shuffwe=twue, (˘ω˘) intewweave=twue, XD shuffwe_fiwes=none, òωó
      i-initiawizabwe=fawse, UwU wog_tf_data_summawies=fawse, -.- **kwawgs):
    """
    this method is used to cweate input function u-used by estimatow.twain(). (ꈍᴗꈍ)

    a-awgs:
      pawse_fn:
        function to pawse a data wecowd i-into a set of f-featuwes. (⑅˘꒳˘)
        defauwts to the pawsew wetuwned b-by the featuweconfig sewected
      w-wepeat (optionaw):
        s-specifies if t-the dataset is to be wepeated. 🥺 defauwts to `pawams.twain_steps > 0`. òωó
        this e-ensuwes the twaining is wun fow a-atweast `pawams.twain_steps`. 😳
        toggwing t-this to `fawse` wesuwts in twaining finishing when o-one of the fowwowing happens:
          - t-the entiwe dataset has been twained u-upon once. òωó
          - `pawams.twain_steps` has b-been weached. 🥺
      shuffwe (optionaw):
        specifies if the fiwes and wecowds in the fiwes nyeed to be shuffwed. ( ͡o ω ͡o )
        when `twue`, UwU  fiwes a-awe shuffwed, 😳😳😳 a-and wecowds of e-each fiwes awe s-shuffwed. ʘwʘ
        when `fawse`, ^^ fiwes awe wead in a-awpha-numewicaw owdew. >_< awso when `fawse`
        the dataset is shawded among w-wowkews fow hogwiwd a-and distwibuted t-twaining
        i-if nyo shawding configuwation is pwovided in `pawams.twain_dataset_shawds`. (ˆ ﻌ ˆ)♡
        defauwts to `twue`. (ˆ ﻌ ˆ)♡
      i-intewweave (optionaw):
        s-specifies if wecowds fwom muwtipwe fiwes nyeed to be intewweaved i-in pawawwew. 🥺
        defauwts t-to `twue`. ( ͡o ω ͡o )
      s-shuffwe_fiwes (optionaw):
        s-shuffwe the wist of fiwes. (ꈍᴗꈍ) defauwts to 'shuffwe' if nyot pwovided. :3
      initiawizabwe (optionaw):
        a boowean indicatow. (✿oωo) w-when the pawsing function depends o-on some wesouwce, (U ᵕ U❁) e.g. UwU a hashtabwe ow
        a tensow, ^^ i.e. i-it's an initiawizabwe itewatow, /(^•ω•^) s-set it to twue. (˘ω˘) othewwise, OwO defauwt vawue
        (fawse) i-is used f-fow most pwain i-itewatows. (U ᵕ U❁)
      w-wog_tf_data_summawies (optionaw):
        a-a boowean indicatow d-denoting whethew t-to add a `tf.data.expewimentaw.statsaggwegatow` to the
        t-tf.data pipewine. (U ﹏ U) this adds summawies of pipewine u-utiwization and buffew sizes t-to the output
        e-events fiwes. mya this wequiwes t-that `initiawizabwe` i-is `twue` above. (⑅˘꒳˘)

    wetuwns:
      an input_fn that can b-be consumed by `estimatow.twain()`. (U ᵕ U❁)
    """
    i-if pawse_fn is n-nyone:
      if s-sewf._featuwe_config is nyone:
        waise vawueewwow("`featuwe_config` was nyot p-passed to `datawecowdtwainew`")
      pawse_fn = sewf._featuwe_config.get_pawse_fn()

    i-if not cawwabwe(pawse_fn):
      waise vawueewwow("expecting p-pawse_fn to be a function.")

    if wog_tf_data_summawies a-and nyot initiawizabwe:
      waise vawueewwow("wequiwe `initiawizabwe` i-if `wog_tf_data_summawies`.")

    i-if wepeat is nyone:
      w-wepeat = sewf.pawams.twain_steps > 0 o-ow sewf.pawams.get('distwibuted', /(^•ω•^) f-fawse)

    if nyot shuffwe and s-sewf.num_wowkews > 1 a-and sewf.pawams.twain_dataset_shawds i-is nyone:
      n-nyum_shawds = sewf.num_wowkews
      s-shawd_index = sewf.wowkew_index
    e-ewse:
      n-nyum_shawds = sewf.pawams.twain_dataset_shawds
      shawd_index = s-sewf.pawams.twain_dataset_shawd_index

    wetuwn wambda: twmw.input_fns.defauwt_input_fn(
      fiwes=sewf._twain_fiwes, ^•ﻌ•^
      batch_size=sewf.pawams.twain_batch_size, (///ˬ///✿)
      pawse_fn=pawse_fn, o.O
      nyum_thweads=sewf.pawams.num_thweads, (ˆ ﻌ ˆ)♡
      w-wepeat=wepeat, 😳
      k-keep_wate=sewf.pawams.twain_keep_wate, òωó
      pawts_downsampwing_wate=sewf.pawams.twain_pawts_downsampwing_wate, (⑅˘꒳˘)
      s-shawds=num_shawds, rawr
      shawd_index=shawd_index, (ꈍᴗꈍ)
      shuffwe=shuffwe, ^^
      s-shuffwe_fiwes=(shuffwe i-if shuffwe_fiwes i-is nyone e-ewse shuffwe_fiwes), (ˆ ﻌ ˆ)♡
      intewweave=intewweave, /(^•ω•^)
      i-initiawizabwe=initiawizabwe, ^^
      wog_tf_data_summawies=wog_tf_data_summawies, o.O
      **kwawgs)

  def g-get_evaw_input_fn(
      s-sewf, 😳😳😳 pawse_fn=none, wepeat=none, XD
      shuffwe=twue, nyaa~~ i-intewweave=twue, ^•ﻌ•^
      shuffwe_fiwes=none, :3 i-initiawizabwe=fawse, ^^ wog_tf_data_summawies=fawse, o.O **kwawgs):
    """
    this method i-is used to cweate input function u-used by estimatow.evaw(). ^^

    awgs:
      pawse_fn:
        function to pawse a-a data wecowd into a set of featuwes. (⑅˘꒳˘)
        defauwts t-to twmw.pawsews.get_spawse_pawse_fn(featuwe_config). ʘwʘ
      wepeat (optionaw):
        s-specifies i-if the dataset is to be wepeated. mya defauwts t-to `pawams.evaw_steps > 0`. >w<
        this ensuwes the evawuation i-is wun fow atweast `pawams.evaw_steps`. o.O
        t-toggwing this t-to `fawse` wesuwts in evawuation finishing when one of the fowwowing happens:
          - the entiwe d-dataset has been evawed upon once. OwO
          - `pawams.evaw_steps` h-has been w-weached. -.-
      shuffwe (optionaw):
        specifies i-if the fiwes a-and wecowds in the fiwes nyeed to be shuffwed. (U ﹏ U)
        when `fawse`, òωó f-fiwes awe wead in awpha-numewicaw o-owdew. >w<
        when `twue`, ^•ﻌ•^  fiwes awe s-shuffwed, /(^•ω•^) and w-wecowds of each fiwes awe shuffwed. ʘwʘ
        d-defauwts t-to `twue`. XD
      intewweave (optionaw):
        s-specifies if wecowds fwom muwtipwe f-fiwes nyeed t-to be intewweaved i-in pawawwew. (U ᵕ U❁)
        d-defauwts t-to `twue`. (ꈍᴗꈍ)
      shuffwe_fiwes (optionaw):
        s-shuffwes t-the wist of fiwes. rawr x3 defauwts to 'shuffwe' if nyot p-pwovided. :3
      initiawizabwe (optionaw):
        a-a boowean indicatow. (˘ω˘) when the pawsing function depends on some wesouwce, -.- e.g. (ꈍᴗꈍ) a hashtabwe ow
        a tensow, UwU i-i.e. it's an initiawizabwe itewatow, σωσ s-set it to twue. ^^ othewwise, :3 d-defauwt vawue
        (fawse) i-is used fow most pwain itewatows. ʘwʘ
      w-wog_tf_data_summawies (optionaw):
        a boowean indicatow d-denoting whethew to add a `tf.data.expewimentaw.statsaggwegatow` t-to the
        tf.data pipewine. 😳 this adds summawies of pipewine utiwization and buffew sizes to the output
        e-events fiwes. ^^ this wequiwes that `initiawizabwe` i-is `twue` above. σωσ

    w-wetuwns:
      an input_fn that can be consumed by `estimatow.evaw()`. /(^•ω•^)
    """
    if pawse_fn is nyone:
      if sewf._featuwe_config is nyone:
        waise v-vawueewwow("`featuwe_config` w-was n-nyot passed to `datawecowdtwainew`")
      pawse_fn = s-sewf._featuwe_config.get_pawse_fn()

    i-if nyot sewf._evaw_fiwes:
      w-waise vawueewwow("`evaw_fiwes` was nyot pwesent in `pawams` passed t-to `datawecowdtwainew`")

    i-if nyot cawwabwe(pawse_fn):
      waise vawueewwow("expecting p-pawse_fn to be a f-function.")

    i-if wog_tf_data_summawies a-and nyot i-initiawizabwe:
      waise vawueewwow("wequiwe `initiawizabwe` i-if `wog_tf_data_summawies`.")

    i-if wepeat i-is none:
      wepeat = s-sewf.pawams.evaw_steps > 0

    w-wetuwn wambda: t-twmw.input_fns.defauwt_input_fn(
      f-fiwes=sewf._evaw_fiwes, 😳😳😳
      b-batch_size=sewf.pawams.evaw_batch_size, 😳
      p-pawse_fn=pawse_fn, OwO
      n-num_thweads=sewf.pawams.num_thweads, :3
      wepeat=wepeat, nyaa~~
      keep_wate=sewf.pawams.evaw_keep_wate, OwO
      pawts_downsampwing_wate=sewf.pawams.evaw_pawts_downsampwing_wate, o.O
      shuffwe=shuffwe, (U ﹏ U)
      s-shuffwe_fiwes=(shuffwe if shuffwe_fiwes i-is nyone ewse shuffwe_fiwes), (⑅˘꒳˘)
      intewweave=intewweave, OwO
      i-initiawizabwe=initiawizabwe, 😳
      w-wog_tf_data_summawies=wog_tf_data_summawies, :3
      **kwawgs
    )

  def _assewt_twain_fiwes(sewf):
    i-if nyot sewf._twain_fiwes:
      waise vawueewwow("twain.data_diw w-was nyot set i-in pawams passed to datawecowdtwainew.")

  def _assewt_evaw_fiwes(sewf):
    if nyot sewf._evaw_fiwes:
      waise vawueewwow("evaw.data_diw was nyot set in pawams p-passed to datawecowdtwainew.")

  def twain(sewf, ( ͡o ω ͡o ) input_fn=none, 🥺 s-steps=none, /(^•ω•^) h-hooks=none):
    """
    makes i-input functions o-optionaw. nyaa~~ input_fn d-defauwts to s-sewf.get_twain_input_fn(). (✿oωo)
    s-see twainew fow m-mowe detaiwed documentation d-documentation. (✿oωo)
    """
    if input_fn is nyone:
      s-sewf._assewt_twain_fiwes()
    input_fn = input_fn i-if input_fn ewse sewf.get_twain_input_fn()
    s-supew(datawecowdtwainew, (ꈍᴗꈍ) s-sewf).twain(input_fn=input_fn, OwO steps=steps, :3 h-hooks=hooks)

  def evawuate(sewf, mya input_fn=none, >_< s-steps=none, (///ˬ///✿) h-hooks=none, (///ˬ///✿) n-nyame=none):
    """
    m-makes input functions o-optionaw. 😳😳😳 input_fn d-defauwts to s-sewf.get_evaw_input_fn(). (U ᵕ U❁)
    see twainew fow m-mowe detaiwed documentation. (///ˬ///✿)
    """
    if input_fn is nyone:
      sewf._assewt_evaw_fiwes()
    input_fn = input_fn if input_fn ewse sewf.get_evaw_input_fn(wepeat=fawse)
    wetuwn supew(datawecowdtwainew, ( ͡o ω ͡o ) sewf).evawuate(
      i-input_fn=input_fn, (✿oωo)
      s-steps=steps, òωó
      hooks=hooks, (ˆ ﻌ ˆ)♡
      nyame=name
    )

  def weawn(sewf, :3 twain_input_fn=none, (ˆ ﻌ ˆ)♡ evaw_input_fn=none, (U ᵕ U❁) **kwawgs):
    """
    o-ovewwides ``twainew.weawn`` t-to make ``input_fn`` functions optionaw. (U ᵕ U❁)
    wespectivewy, XD ``twain_input_fn`` a-and ``evaw_input_fn`` d-defauwt to
    ``sewf.twain_input_fn`` a-and ``sewf.evaw_input_fn``. nyaa~~
    s-see ``twainew.weawn`` fow mowe d-detaiwed documentation. (ˆ ﻌ ˆ)♡
    """
    if twain_input_fn i-is nyone:
      s-sewf._assewt_twain_fiwes()
    if evaw_input_fn is nyone:
      sewf._assewt_evaw_fiwes()
    t-twain_input_fn = t-twain_input_fn i-if twain_input_fn e-ewse sewf.get_twain_input_fn()
    evaw_input_fn = e-evaw_input_fn i-if evaw_input_fn e-ewse sewf.get_evaw_input_fn()

    s-supew(datawecowdtwainew, ʘwʘ sewf).weawn(
      twain_input_fn=twain_input_fn, ^•ﻌ•^
      e-evaw_input_fn=evaw_input_fn, mya
      **kwawgs
    )

  d-def twain_and_evawuate(sewf, (ꈍᴗꈍ)
                         twain_input_fn=none, (ˆ ﻌ ˆ)♡ evaw_input_fn=none, (ˆ ﻌ ˆ)♡
                          **kwawgs):
    """
    ovewwides ``twainew.twain_and_evawuate`` to make ``input_fn`` functions o-optionaw. ( ͡o ω ͡o )
    w-wespectivewy, o.O ``twain_input_fn`` and ``evaw_input_fn`` d-defauwt to
    ``sewf.twain_input_fn`` and ``sewf.evaw_input_fn``. 😳😳😳
    see ``twainew.twain_and_evawuate`` f-fow detaiwed d-documentation. ʘwʘ
    """
    i-if twain_input_fn is nyone:
      s-sewf._assewt_twain_fiwes()
    i-if evaw_input_fn is nyone:
      sewf._assewt_evaw_fiwes()
    t-twain_input_fn = t-twain_input_fn i-if twain_input_fn e-ewse sewf.get_twain_input_fn()
    e-evaw_input_fn = e-evaw_input_fn if evaw_input_fn ewse sewf.get_evaw_input_fn()

    supew(datawecowdtwainew, :3 sewf).twain_and_evawuate(
      twain_input_fn=twain_input_fn, UwU
      e-evaw_input_fn=evaw_input_fn, nyaa~~
      **kwawgs
    )

  def _modew_fn(sewf, :3 f-featuwes, nyaa~~ w-wabews, mode, ^^ pawams, config=none):
    """
    ovewwides the _modew_fn t-to cowwect fow the f-featuwes shape of the spawse f-featuwes
    extwacted with the c-contwib.featuweconfig
    """
    if isinstance(sewf._featuwe_config, nyaa~~ twmw.contwib.featuwe_config.featuweconfig):
      # fix the s-shape of the featuwes. 😳😳😳 the featuwes dictionawy wiww be modified to
      # contain t-the shape changes.
      t-twmw.utiw.fix_shape_spawse(featuwes, ^•ﻌ•^ s-sewf._featuwe_config)
    w-wetuwn supew(datawecowdtwainew, (⑅˘꒳˘) sewf)._modew_fn(
      f-featuwes=featuwes, (✿oωo)
      wabews=wabews, mya
      m-mode=mode, (///ˬ///✿)
      pawams=pawams, ʘwʘ
      config=config
    )

  def c-cawibwate(sewf, >w<
                c-cawibwatow, o.O
                input_fn=none, ^^;;
                s-steps=none, :3
                save_cawibwatow=twue, (ꈍᴗꈍ)
                hooks=none):
    """
    m-makes input functions optionaw. XD input_fn defauwts to sewf.twain_input_fn. ^^;;
    see twainew fow mowe detaiwed documentation. (U ﹏ U)
    """
    i-if input_fn is nyone:
      s-sewf._assewt_twain_fiwes()
    input_fn = input_fn if input_fn ewse sewf.get_twain_input_fn()
    supew(datawecowdtwainew, (ꈍᴗꈍ) s-sewf).cawibwate(cawibwatow=cawibwatow, 😳
                                             input_fn=input_fn, rawr
                                             steps=steps, ( ͡o ω ͡o )
                                             s-save_cawibwatow=save_cawibwatow, (ˆ ﻌ ˆ)♡
                                             h-hooks=hooks)

  d-def save_checkpoints_and_expowt_modew(sewf, OwO
                                        s-sewving_input_weceivew_fn, >_<
                                        expowt_output_fn=none, XD
                                        expowt_diw=none, (ˆ ﻌ ˆ)♡
                                        checkpoint_path=none,
                                        input_fn=none):
    """
    expowts s-saved moduwe a-aftew saving checkpoint t-to save_diw. (ꈍᴗꈍ)
    p-pwease note that to use t-this method, (✿oωo) you nyeed to assign a-a woss to the output
    of the buiwd_gwaph (fow the twain mode). UwU
    s-see expowt_modew f-fow mowe d-detaiwed infowmation. (ꈍᴗꈍ)
    """
    s-sewf.twain(input_fn=input_fn, (U ﹏ U) steps=1)
    s-sewf.expowt_modew(sewving_input_weceivew_fn, >w< e-expowt_output_fn, ^•ﻌ•^ expowt_diw, 😳 checkpoint_path)

  def save_checkpoints_and_evawuate(sewf, XD
                                    input_fn=none, :3
                                    s-steps=none, rawr x3
                                    h-hooks=none, (⑅˘꒳˘)
                                    nyame=none):
    """
    evawuates modew aftew saving c-checkpoint to save_diw. ^^
    p-pwease nyote that t-to use this method, y-you nyeed to assign a woss to the output
    of the buiwd_gwaph (fow the twain mode). >w<
    s-see evawuate fow mowe detaiwed infowmation. 😳
    """
    s-sewf.twain(input_fn=input_fn, rawr steps=1)
    sewf.evawuate(input_fn, rawr x3 s-steps, (ꈍᴗꈍ) hooks, nyame)
