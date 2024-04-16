{
  "role": "d scode",
  "na ": "uua-enr c r-stag ng",
  "conf g-f les": [
    "uua-enr c r.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-enr c r"
      },
      {
        "type": "packer",
        "na ": "uua-enr c r-stag ng",
        "art fact": "./d st/uua-enr c r.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-enr c r-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-enr c r"
        }
      ]
    }
  ]
}
