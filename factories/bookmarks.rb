require 'faker'

FactoryBot.define do
  factory :bookmark do
    bookmarkable_type { "Work" }
    bookmarkable_id { FactoryBot.create(:work).id }
    pseud_id { FactoryBot.create(:pseud).id }

    factory :external_work_bookmark do
      bookmarkable_type { "ExternalWork" }
      bookmarkable_id { FactoryBot.create(:external_work).id }
    end

    factory :series_bookmark do
      bookmarkable_type { "Series" }
      bookmarkable_id { FactoryBot.create(:series_with_a_work).id }
    end
  end
end
