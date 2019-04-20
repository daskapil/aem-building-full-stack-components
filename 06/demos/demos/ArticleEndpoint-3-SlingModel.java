// ###########################
// Step 1 - Imports
// ###########################

import pluralsight.core.models.ArticleModel;



// ###########################
// Step 2 - Loop through resource and adaptTo ArticleModel
// Add just before sending the response
// ###########################

//Create an ArrayList to store objects in, we will turn this to JSON with GSON.
ArrayList<ArticleModel> allArticles = new ArrayList<>();

//Iterate over the resources in our list from the query.
Iterator<Resource> resourceIterator= resources.iterator();
while (resourceIterator.hasNext()) {

    //Grab the current Resource/Article
    Resource currentResource = resourceIterator.next();

    //Adapt it to our Sling Model (aka make it an article!)
    ArticleModel currenArticle = currentResource.adaptTo(ArticleModel.class);

    //Add it to our array.
    allArticles.add(currenArticle);
}


// ###########################
// Step 4 - Update Response
// Update the response to show how many articles were found
// ###########################

resp.getWriter().write("First Article Title: " + allArticles.get(1).getTitle());


