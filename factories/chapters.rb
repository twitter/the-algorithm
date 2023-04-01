require 'faker'

FactoryBot.define do
  factory :chapter do
    content { "Awesome content!" }
    work
    posted { true }

    transient do
      authors { work.pseuds }
    end

    after(:build) do |chapter, evaluator|
      evaluator.authors.each do |pseud|
        chapter.creatorships.build(pseud: pseud)
      end
    end

    trait :draft do
      content { "Draft content!" }
      posted { false }
    end
  end
end
