require "spec_helper"

describe TagTypeHelper do
  it "finds the archive warning label" do
    result = tag_type_label_name("archive_warning")
    expect(result).to eq("Warning")
  end

  it "finds the right label pluralized" do
    result = tag_type_label_name("freeform")
    expect(result).to eq("Additional Tag")
  end

  it "finds the correct CSS class for the ArchiveWarning tag type" do
    result = tag_type_css_class("ArchiveWarning")
    expect(result).to eq("warning")
  end

  it "finds the correct CSS class for the AdditionalTag tag type" do
    result = tag_type_css_class("AdditionalTag")
    expect(result).to eq("freeform")
  end

  it "finds the correct CSS class for all other tag types" do
    result = tag_type_css_class("Category")
    expect(result).to eq("category")
  end
end
