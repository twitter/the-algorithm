require "spec_helper"

describe "rake defaults:create_roles" do
  it "creates roles" do
    subject.invoke

    role_names = %w[archivist no_resets official opendoors protected_user tag_wrangler translator]
    expect(Role.all.pluck(:name)).to eq(role_names)
  end
end
