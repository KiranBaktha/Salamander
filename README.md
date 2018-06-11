# Salamander
An android app that lets you read and store your favorite news articles


This is the Starting Activity where the user can enter a keyword to read related articles

<img src="https://github.com/KiranBaktha/Salamander/blob/master/Screenshots/main_activity.PNG" width="200" height="400">

The articles are fetched from [News API](https://newsapi.org/) and displayed in a list view. Picasso is used to lazy load the images in list view and cache them.

<img src="https://github.com/KiranBaktha/Salamander/blob/master/Screenshots/article_activity.PNG" width="200" height="400">

When a particular article is selected, the article content is displayed in a dialog box and the user is provided with an option to favourite the article. Certain special text characters still need to be handled.

<img src="https://github.com/KiranBaktha/Salamander/blob/master/Screenshots/news_dialog.PNG" width="200" height="400">

Favourite articles are displayed in a seperate activity with their content loaded on selection.

<img src="https://github.com/KiranBaktha/Salamander/blob/master/Screenshots/favourites_activity.PNG" width="200" height="400">



