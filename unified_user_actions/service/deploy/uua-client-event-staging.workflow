{
  "role": "d scode",
  "na ": "uua-cl ent-event-stag ng",
  "conf g-f les": [
    "uua-cl ent-event.aurora"
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
        "target": "un f ed_user_act ons/serv ce/src/ma n/scala:uua-cl ent-event"
      },
      {
        "type": "packer",
        "na ": "uua-cl ent-event-stag ng",
        "art fact": "./d st/uua-cl ent-event.z p"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "na ": "stag ng",
      "targets": [
        {
          "na ": "uua-cl ent-event-stag ng-pdxa",
          "key": "pdxa/d scode/stag ng/uua-cl ent-event"
        }
      ]
    }
  ]
}
