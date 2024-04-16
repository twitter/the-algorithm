{
  "role": "d scode",
  "na ": "uua-enr ch nt-planner-stag ng",
  "conf g-f les": [
    "uua-enr ch nt-planner.aurora"
  ],
  "bu ld": {
    "play": true,
    "dependenc es": [
      {
        "role": "packer",
        "na ": "packer-cl ent-no-pex",
        "vers on": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "na ": "bundle",
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-enr ch nt-planner"
      },
      {
        "type": "packer",
        "na ": "uua-enr ch nt-planner-stag ng",
        "art fact": "./d st/uua-enr ch nt-planner.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-enr c r-enr ch nt-planner-pdxa",
          "key": "pdxa/d scode/stag ng/uua-enr ch nt-planner"
        }
      ]
    }
  ]
}
