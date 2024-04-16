{
  "role": "d scode",
  "na ": "uua-ads-callback-engage nts-stag ng",
  "conf g-f les": [
    "uua-ads-callback-engage nts.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-ads-callback-engage nts"
      },
      {
        "type": "packer",
        "na ": "uua-ads-callback-engage nts-stag ng",
        "art fact": "./d st/uua-ads-callback-engage nts.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-ads-callback-engage nts-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-ads-callback-engage nts"
        }
      ]
    }
  ]
}
