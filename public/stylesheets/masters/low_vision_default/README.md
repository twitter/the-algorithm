# Low Vision Default Overview

* Takes out all "columns" and makes everything max-width, so dashboard is max,
filters are max and so it puts the filters at the bottom of the page 
* Puts strong borders around some groups and buttons 
* Makes header and footer buttons max width blocks
* Shows the header text instead of image
* Removes the blurb icons and replaces with text
* Enlarges the `pre` and `textarea` text by 200% so it scales in Firefox
* Swaps a lot of margins and padding into percentages so they don't scale up as
much and crowd out the text
* Takes off the header and footer texture and makes it flat

There's not a lot else done with colors/images on the grounds you're probably
overriding in your browser.

# Low Vision Default Structure

Low Vision Default is unique because it uses only some of the default
stylesheets as parents.

* Use the CSS from low_vision_default_site_screen_.css.
* Expand the "Advanced" section.
* For "What it does," select "replace archive skin entirely."
* For "Media," check the "all" option.
* Check "Load Archive Skin Components."
* Save to load the default skin components as parents.
* Use the button-style "x" to remove the following parents:
  * Archive 2.0: (3) region header
  * Archive 2.0: (6) region footer
  * Archive 2.0: (12) group meta
  * Archive 2.0: (13) group blurb
  * Archive 2.0: (25) media midsize

# Credits

Much owed to Amanda Bankier.
