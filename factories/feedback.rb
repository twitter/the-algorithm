require 'faker'

FactoryBot.define do
  factory :feedback do
    comment { Faker::Lorem.paragraph(sentence_count: 1) }
    email { Faker::Internet.email }
    summary { Faker::Lorem.sentence(word_count: 1) }
    language { "English" }
  end
end
