require 'spec_helper'

describe Pseud do
  it "has a valid factory" do
    expect(build(:pseud)).to be_valid
  end

  it "is invalid without a name" do
    expect(build(:pseud, name: nil)).to be_invalid
  end

  it "is invalid if there are special characters" do
    expect(build(:pseud, name: "*pseud*")).to be_invalid
  end

  describe "save" do
    before do
      @user = User.new
      @user.login = "mynamepseud"
      @user.age_over_13 = "1"
      @user.terms_of_service = "1"
      @user.email = "foo1@archiveofourown.org"
      @user.password = "password"
      @user.save
    end

    before(:each) do
      @pseud = Pseud.new
      @pseud.user_id = @user.id
      @pseud.name = "MyName"
    end

    it "should save a minimalistic pseud" do
      @pseud.should be_valid_verbose
      expect(@pseud.save).to be_truthy
      @pseud.errors.should be_empty
    end

    it "should not save pseud with too-long alt text for icon" do
      @pseud.icon_alt_text = "Something that is too long blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah this needs 250 characters lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum"
      expect(@pseud.save).to be_falsey
      @pseud.errors[:icon_alt_text].should_not be_empty
    end

    it "should not save pseud with too-long comment text for icon" do
      @pseud.icon_comment_text = "Something that is too long blah blah blah blah blah blah this needs a mere 50 characters"
      expect(@pseud.save).to be_falsey
      @pseud.errors[:icon_comment_text].should_not be_empty
    end
  end

  describe "touch_comments" do
    let(:pseud) { create(:pseud) }
    let!(:comment) { create(:comment, pseud: pseud) }

    it "modifies the updated_at of associated comments" do
      # Without this, the in-memory pseud has 0 comments and the test fails.
      pseud.reload
      travel(1.day)
      expect do
        pseud.update(name: "New Name")
      end.to change { comment.reload.updated_at }
    end
  end
end
