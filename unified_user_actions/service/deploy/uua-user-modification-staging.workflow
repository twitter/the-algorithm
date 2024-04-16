{
  "role": "d scode",
  "na ": "uua-user-mod f cat on-stag ng",
  "conf g-f les": [
    "uua-user-mod f cat on.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-user-mod f cat on"
      },
      {
        "type": "packer",
        "na ": "uua-user-mod f cat on-stag ng",
        "art fact": "./d st/uua-user-mod f cat on.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-user-mod f cat on-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-user-mod f cat on"
        }
      ]
    }
  ]
}
