require "spec_helper"

describe "rake skins:cache_chooser_skins", default_skin: true do
  let(:css) { ".selector { color: yellow; }" }
  let!(:default_skin) { Skin.find(AdminSetting.default_skin_id) }
  let!(:chooser_skin) { create(:skin, in_chooser: true, css: css) }
  let!(:user_skin) { create(:skin, css: css) }

  it "calls cache! on in_chooser skins" do
    expect do
      subject.invoke
    end.to change { chooser_skin.reload.public }.from(false).to(true)
      .and change { chooser_skin.official }.from(false).to(true)
      .and change { chooser_skin.cached }.from(false).to(true)
      .and avoid_changing { default_skin.reload.public }
      .and avoid_changing { default_skin.official }
      .and change { default_skin.cached }.from(false).to(true)
      .and avoid_changing { user_skin.reload.public }
      .and avoid_changing { user_skin.official }
      .and avoid_changing { user_skin.cached }
  end

  it "outputs names of skins that were cached" do
    expect do
      subject.invoke
    end.to output("\nCached #{default_skin.title},#{chooser_skin.title}\n").to_stdout
  end

  it "outputs names of skins that could not be cached" do
    allow_any_instance_of(Skin).to receive(:cache!).and_return(false)
    expect do
      subject.invoke
    end.to output("\nCouldn't cache #{default_skin.title},#{chooser_skin.title}\n").to_stdout
  end
end
