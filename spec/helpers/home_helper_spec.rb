require 'spec_helper'

describe HomeHelper do

  describe "html_to_text" do
    context "for illegal code" do
      it "should strip out offending characters" do
        string = "<br>I see what you\n have done there.</br><p> Do you see what I see?</p>"
        expect(html_to_text(string)).to eq "\nI see what you\nhave done there.\n\nDo you see what I see?\n\n"
      end
    end
  end
end
