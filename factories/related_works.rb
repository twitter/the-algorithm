require 'faker'

FactoryBot.define do
  factory :related_work do
    parent_type { "Work" }
    parent_id { FactoryBot.create(:work).id }
    work_id { FactoryBot.create(:work).id }
  end
end
