class MenuController < ApplicationController

  # about menu
  def about
    render action: "about", layout: "application"
  end
  
  # browse menu
  def browse
    render action: "browse", layout: "application"
  end

  # fandoms menu
  def fandoms
    render action: "fandoms", layout: "application"
  end

  # search menu
  def search
    render action: "search", layout: "application"
  end
  
end