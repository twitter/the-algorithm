require 'faker'
FactoryBot.define do
  factory :abuse_report do
    email { Faker::Internet.email }
    url { "http://archiveofourown.org/tags/2000%20AD%20(Comics)/works" }
    comment { Faker::Lorem.paragraph(sentence_count: 1) }
    summary { Faker::Lorem.sentence(word_count: 1) }
    language { "Francais" }
  end
end
