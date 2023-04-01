require "spec_helper"

describe ArchiveFaq do
  let(:faq) { I18n.with_locale("en") { create(:archive_faq, title: "hello") } }

  it "is valid with the default locale" do
    I18n.locale = I18n.default_locale
    expect(faq).to be_valid
    expect(faq.title).to eq("hello")
  end

  it "is invalid with a non-existent locale" do
    I18n.locale = "sjn"
    faq.title = "suilad"
    expect(Locale.exists?(iso: I18n.locale)).to be_falsey
    expect(faq.save).to be_falsey
    expect(faq.errors.full_messages).to include("The locale sjn does not exist.")
  end

  it "uses the title from the default locale for non-translated locales" do
    I18n.locale = "sjn"
    expect(faq.title).to eq("hello")
  end

  it "cannot have questions with a non-existent locale" do
    I18n.locale = "sjn"
    question = faq.questions.build(attributes: { question: "it's a question?", content: "it's an answer", anchor: "identity" })
    expect(question.save).to be_falsey
    expect(question.errors.full_messages).to include("The locale sjn does not exist.")
  end
end
