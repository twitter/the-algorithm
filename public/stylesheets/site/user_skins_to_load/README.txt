This folder holds skins to load into the database. It can include either: 

- one css file per skin 
- one css file containing multiple skins marked out by /* END SKIN */ comments

Put the title at the top:
/* SKIN: My Awesome Skin */

If this is an existing skin to replace, add a comment with the skin id:
/* REPLACE: 3 */

Each skin can have a comment to list parents: either skins by title or just numbers for any of the current (2.0) site skin components that are part of this skin.
/* PARENTS: 5, 7, 10 */ 
or
/* PARENTS: Archive 1.0 */

/* SKIN: Paster for complex ones*/
/* PARENTS: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 */
/* END SKIN */



Load them up with the rake task 

rake skins:load_user_skins

