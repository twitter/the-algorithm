require 'faker'

FactoryBot.define do
  factory :admin do
    login { generate(:login) }
    password { "adminpassword" }
    password_confirmation { |u| u.password }
    email { generate(:email) }

    factory :superadmin do
      roles { ["superadmin"] }
    end

    factory :policy_and_abuse_admin do
      roles { ["policy_and_abuse"] }
    end

    factory :support_admin do
      roles { ["support"] }
    end

    factory :tag_wrangling_admin do
      roles { ["tag_wrangling"] }
    end

    factory :open_doors_admin do
      roles { ["open_doors"] }
    end
  end

  factory :admin_activity do
    admin
    action { "update_tags" }
    summary { "MyActivity" }
  end
end
