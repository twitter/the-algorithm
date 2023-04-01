require 'spec_helper'

describe AdminBlacklistedEmail, :ready do

  it "can be created" do
    expect(create(:admin_blacklisted_email)).to be_valid
  end

  context "invalid" do
    let(:blacklisted_without_email) {build(:admin_blacklisted_email, email: nil)}
    it 'is invalid without an email' do
      expect(blacklisted_without_email.save).to be_falsey
      expect(blacklisted_without_email.errors[:email]).not_to be_empty
    end
  end

  context "uniqueness" do
    let(:existing_email) {create(:admin_blacklisted_email, email: "foobar@gmail.com")}

    it "is invalid if email is not unique" do
      expect(build(:admin_blacklisted_email, email: existing_email.email)).to be_invalid
    end
  end
    
  context "blacklisted emails" do
    before(:each) do
      @existing_email = FactoryBot.create(:admin_blacklisted_email, email: "foobar@gmail.com")
      @existing_email2 = FactoryBot.create(:admin_blacklisted_email, email: "foo@bar.com")
    end
    
    it "match themselves" do
      expect(AdminBlacklistedEmail.is_blacklisted?("foobar@gmail.com"))
      expect(AdminBlacklistedEmail.is_blacklisted?("foo@bar.com"))
    end
    
    it "match variants" do
      expect(AdminBlacklistedEmail.is_blacklisted?("FOO@bar.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foo+baz@bar.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("FOO@BAR.COM")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("FOOBAR@gmail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foobar+baz+bat@gmail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foo.bar@gmail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("f.o.o.bar@gmail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foobar@googlemail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foobar@GOOGLEMAIL.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("fo.ob.ar@googlemail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("foobar+baz@googlemail.com")).to be_truthy
      expect(AdminBlacklistedEmail.is_blacklisted?("FOOBAR@googlemail.com")).to be_truthy      
    end
    
    it "do not match other emails" do
      expect(AdminBlacklistedEmail.is_blacklisted?("something_else@bar.com")).to be_falsey
      expect(AdminBlacklistedEmail.is_blacklisted?("foo@gmail.com")).to be_falsey
      expect(AdminBlacklistedEmail.is_blacklisted?("something_else@gmail.com")).to be_falsey
      expect(AdminBlacklistedEmail.is_blacklisted?("foo.f.o.o@gmail.com")).to be_falsey
    end
  end
end
