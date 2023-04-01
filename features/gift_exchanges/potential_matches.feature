Feature:
  Testing potential match generation.

  Scenario: Small multi-fandom exchange, only fandom tags.

    Given I create the gift exchange "multifan3" with the following options
        | value   | minimum | maximum | match |
        | prompts | 2       | 2       | 1     |
        | fandoms | 1       | 1       | 1     |
      And the user "test1" signs up for "multifan3" with the following prompts
        | type    | fandoms        |
        | request | Popular Fandom |
        | request | Fandom of One  |
        | offer   | Rare Fandom 1  |
        | offer   | Fandom of One  |
      And the user "test2" signs up for "multifan3" with the following prompts
        | type    | fandoms        |
        | request | Rare Fandom 1  |
        | request | Rare Fandom 2  |
        | offer   | Popular Fandom |
        | offer   | Rare Fandom 2  |
      And the user "test3" signs up for "multifan3" with the following prompts
        | type    | fandoms        |
        | request | Rare Fandom 2  |
        | request | Rare Fandom 3  |
        | offer   | Popular Fandom |
        | offer   | Rare Fandom 2  |

    When potential matches are generated for "multifan3"
    Then the potential matches for "multifan3" should be
        | offer | request |
        | test1 | test2   |
        | test2 | test1   |
        | test2 | test3   |
        | test3 | test1   |
        | test3 | test2   |

  Scenario: Unconstrained exchange.

    Given I create the gift exchange "unconstrained3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 1       | 0     |
      And the user "test1" signs up for "unconstrained3" with the following prompts
        | type    | characters         |
        | request | Evil Villain       |
        | offer   | Evil Villain       |
      And the user "test2" signs up for "unconstrained3" with the following prompts
        | type    | characters         |
        | request | Sweet Protagonist  |
        | offer   | Sweet Protagonist  |
      And the user "test3" signs up for "unconstrained3" with the following prompts
        | type    | characters         |
        | request | Morally Grey       |
        | offer   | Morally Grey       |

    When potential matches are generated for "unconstrained3"
    Then the potential matches for "unconstrained3" should be
        | offer | request |
        | test1 | test2   |
        | test1 | test3   |
        | test2 | test1   |
        | test2 | test3   |
        | test3 | test1   |
        | test3 | test2   |

  Scenario: Unconstrained exchange with no tags.

    Given I create the gift exchange "no_tags3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
      And the user "test1" signs up for "no_tags3" with the following prompts
        | type    |
        | request |
        | offer   |
      And the user "test2" signs up for "no_tags3" with the following prompts
        | type    |
        | request |
        | offer   |
      And the user "test3" signs up for "no_tags3" with the following prompts
        | type    |
        | request |
        | offer   |

    When potential matches are generated for "no_tags3"
    Then the potential matches for "no_tags3" should be
        | offer | request |
        | test1 | test2   |
        | test1 | test3   |
        | test2 | test1   |
        | test2 | test3   |
        | test3 | test1   |
        | test3 | test2   |

  Scenario: Constrained exchange with no matches.

    Given I create the gift exchange "constrained3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 1       | 1     |
      And the user "test1" signs up for "constrained3" with the following prompts
        | type    | characters         |
        | request | Evil Villain       |
        | offer   | Evil Villain       |
      And the user "test2" signs up for "constrained3" with the following prompts
        | type    | characters         |
        | request | Sweet Protagonist  |
        | offer   | Sweet Protagonist  |
      And the user "test3" signs up for "constrained3" with the following prompts
        | type    | characters         |
        | request | Morally Grey       |
        | offer   | Morally Grey       |

    When potential matches are generated for "constrained3"
    Then there should be no potential matches for "constrained3"

  Scenario: Exchange with someone offering Any.

    Given I create the gift exchange "any_offer3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 1       | 1     |
      And the user "test1" signs up for "any_offer3" with the following prompts
        | type    | characters         |
        | request | Evil Villain       |
        | offer   | any                |
      And the user "test2" signs up for "any_offer3" with the following prompts
        | type    | characters         |
        | request | Sweet Protagonist  |
        | offer   | Sweet Protagonist  |
      And the user "test3" signs up for "any_offer3" with the following prompts
        | type    | characters         |
        | request | Morally Grey       |
        | offer   | Morally Grey       |

    When potential matches are generated for "any_offer3"
    Then the potential matches for "any_offer3" should be
        | offer | request |
        | test1 | test2   |
        | test1 | test3   |

  Scenario: Exchange with someone requesting Any.

    Given I create the gift exchange "any_request3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 1       | 1     |
      And the user "test1" signs up for "any_request3" with the following prompts
        | type    | characters         |
        | request | any                |
        | offer   | Evil Villain       |
      And the user "test2" signs up for "any_request3" with the following prompts
        | type    | characters         |
        | request | Sweet Protagonist  |
        | offer   | Sweet Protagonist  |
      And the user "test3" signs up for "any_request3" with the following prompts
        | type    | characters         |
        | request | Morally Grey       |
        | offer   | Morally Grey       |

    When potential matches are generated for "any_request3"
    Then the potential matches for "any_request3" should be
        | offer | request |
        | test2 | test1   |
        | test3 | test1   |

  Scenario: Exchange with offers and requests for Any.
 
    Given I create the gift exchange "any_both3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 1       | 1     |
      And the user "test1" signs up for "any_both3" with the following prompts
        | type    | characters         |
        | request | any                |
        | offer   | Evil Villain       |
      And the user "test2" signs up for "any_both3" with the following prompts
        | type    | characters         |
        | request | Sweet Protagonist  |
        | offer   | any                |
      And the user "test3" signs up for "any_both3" with the following prompts
        | type    | characters         |
        | request | Morally Grey       |
        | offer   | Morally Grey       |

    When potential matches are generated for "any_both3"
    Then the potential matches for "any_both3" should be
        | offer | request |
        | test2 | test1   |
        | test2 | test3   |
        | test3 | test1   |

  Scenario: Exchange with ALL matching.
 
    Given I create the gift exchange "all_matching3" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 4       | -1    |
      And the user "test1" signs up for "all_matching3" with the following prompts
        | type    | characters                                                |
        | request | Evil Villain, Shy Friend                                  |
        | offer   | Evil Villain, Shy Friend, Anti-Villain, Sweet Protagonist |
      And the user "test2" signs up for "all_matching3" with the following prompts
        | type    | characters                                                |
        | request | Evil Villain, Comic Relief, Sweet Protagonist             |
        | offer   | Evil Villain, Comic Relief, Sweet Protagonist             |
      And the user "test3" signs up for "all_matching3" with the following prompts
        | type    | characters                                                |
        | request | Comic Relief                                              |
        | offer   | Shy Friend, Sweet Protagonist, Comic Relief               |

    When potential matches are generated for "all_matching3"
    Then the potential matches for "all_matching3" should be
        | offer | request |
        | test2 | test3   |

  Scenario: Exchange with ALL and Any.
 
    Given I create the gift exchange "all_and_any4" with the following options
        | value      | minimum | maximum | match |
        | prompts    | 1       | 1       | 1     |
        | characters | 1       | 4       | -1    |
      And the user "test1" signs up for "all_and_any4" with the following prompts
        | type    | characters                                    |
        | request | Evil Villain, Shy Friend                      |
        | offer   | any                                           |
      And the user "test2" signs up for "all_and_any4" with the following prompts
        | type    | characters                                    |
        | request | Evil Villain, Comic Relief, Sweet Protagonist |
        | offer   | Evil Villain, Comic Relief, Sweet Protagonist |
      And the user "test3" signs up for "all_and_any4" with the following prompts
        | type    | characters                                    |
        | request | Comic Relief                                  |
        | offer   | Shy Friend, Sweet Protagonist, Comic Relief   |
      And the user "test4" signs up for "all_and_any4" with the following prompts
        | type    | characters                                    |
        | request | any                                           |
        | offer   | Shy Friend, Sweet Protagonist, Comic Relief   |

    When potential matches are generated for "all_and_any4"
    Then the potential matches for "all_and_any4" should be
        | offer | request |
        | test1 | test2   |
        | test1 | test3   |
        | test2 | test3   |
        | test1 | test4   |
        | test2 | test4   |
        | test3 | test4   |
        | test4 | test3   |

  Scenario: Exchange with freeform restrictions.
 
    Given I create the gift exchange "freeform4" with the following options
        | value      | minimum | maximum | match | unique |
        | prompts    | 1       | 2       | 1     | n/a    |
        | characters | 1       | 2       | -1    | no     |
        | freeforms  | 1       | 2       | 1     | no     |
      And the user "test1" signs up for "freeform4" with the following prompts
        | type    | characters                       | freeforms   |
        | request | Evil Villain                     | Fic         |
        | request | Evil Villain, Sweet Protagonist  | Fic, Art    |
        | offer   | Evil Villain, Sweet Protagonist  | Fic         |
      And the user "test2" signs up for "freeform4" with the following prompts
        | type    | characters                       | freeforms   |
        | request | Comic Relief                     | Fic         |
        | request | Woobie of Choice                 | Art         |
        | offer   | any                              | Art         |
      And the user "test3" signs up for "freeform4" with the following prompts
        | type    | characters                       | freeforms   |
        | request | Sweet Protagonist                | Art, Fic    |
        | request | Evil Villain, Anti-Villain       | Art         |
        | offer   | Evil Villain, Anti-Villain       | Fic         |
      And the user "test4" signs up for "freeform4" with the following prompts
        | type    | characters                       | freeforms   |
        | request | Sweet Protagonist                | Fic         |
        | offer   | Comic Relief, Sweet Protagonist  | Fic         |

    When potential matches are generated for "freeform4"
    Then the potential matches for "freeform4" should be
        | offer | request |
        | test1 | test3   |
        | test1 | test4   |
        | test2 | test1   |
        | test2 | test3   |
        | test3 | test1   |
        | test4 | test2   |
        | test4 | test3   |

  Scenario: Multi-fandom exchange with optional tags.

    Given I create the gift exchange "optional3" with the following options
        | value      | minimum | maximum | match | optional | any |
        | prompts    | 2       | 2       | 1     | n/a      | n/a |
        | fandoms    | 1       | 1       | 1     | yes      | no  |
        | characters | 1       | 1       | 1     | yes      | yes |
      And the user "bookfan" signs up for "optional3" with the following prompts
        | type    | fandoms           | characters        | optional fandoms |
        | request | Out-of-Print Book | Rare Woobie       |                  |
        | request | Rare Book         | Obscure Character |                  |
        | offer   | Out-of-Print Book | any               | Rare Book        |
        | offer   | Popular Book      | any               |                  |
      And the user "showfan" signs up for "optional3" with the following prompts
        | type    | fandoms           | characters        | optional fandoms |
        | request | 80s Cop Show      | Badass Partner    |                  |
        | request | 90s Fantasy Show  | Witty Protagonist |                  |
        | offer   | 80s Cop Show      | Badass Partner    |                  |
        | offer   | 90s Fantasy Show  | any               | 00s SciFi Show   |
      And the user "mixed" signs up for "optional3" with the following prompts
        | type    | fandoms           | characters        | optional fandoms |
        | request | Rare Book         | Fandom Darling    |                  |
        | request | 00s SciFi Show    | Bounty Hunter     |                  |
        | offer   | Popular Book      | any               | Rare Book        |
        | offer   | 10s Drama Show    | any               | 90s Fantasy Show |

    When potential matches are generated for "optional3"
    Then the potential matches for "optional3" should be
        | offer   | request |
        | mixed   | bookfan |
        | mixed   | showfan |
        | bookfan | mixed   |
        | showfan | mixed   |

  Scenario: Exchange with optional tags and restrictions on a different type.

    Given I create the gift exchange "optional4" with the following options
        | value      | minimum | maximum | match | optional | any | unique |
        | prompts    | 1       | 3       | 1     | n/a      | n/a | n/a    |
        | characters | 1       | 1       | 1     | yes      | no  | no     |
        | freeforms  | 1       | 1       | 1     | no       | no  | yes    |
      And the user "test1" signs up for "optional4" with the following prompts
        | type    | character         | optional characters  | freeform |
        | request | Warlock           |                      | Fic      |
        | request | Warlock           |                      | Art      |
        | offer   | Warlock           | Witch, Magician      | Fic      |
      And the user "test2" signs up for "optional4" with the following prompts
        | type    | character         | optional characters  | freeform |
        | request | Magician          | Headologist          | Art      |
        | offer   | Magician          | Headologist, Warlock | Art      |
      And the user "test3" signs up for "optional4" with the following prompts
        | type    | character         | optional characters  | freeform |
        | request | Magician          |                      | Fic      |
        | request | Magician          |                      | Art      |
        | request | Magician          |                      | Vid      |
        | offer   | Magician          |                      | Fic      |
        | offer   | Magician          |                      | Art      |
        | offer   | Magician          |                      | Vid      |
      And the user "test4" signs up for "optional4" with the following prompts
        | type    | character         | optional characters  | freeform |
        | request | Headologist       | Summoner, Witch      | Vid      |
        | offer   | Headologist       | Summoner, Witch      | Art      |
        | offer   | Headologist       | Summoner, Witch      | Vid      |

    When potential matches are generated for "optional4"
    Then the potential matches for "optional4" should be
        | offer   | request |
        | test2   | test1   |
        | test3   | test2   |
        | test1   | test3   |
        | test2   | test3   |
        | test4   | test2   |
