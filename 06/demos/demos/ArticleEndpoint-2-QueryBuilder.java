// ###########################
// Step 1 - Imports
// ###########################


    import com.day.cq.search.PredicateGroup;
    import com.day.cq.search.Query;
    import com.day.cq.search.QueryBuilder;
    import com.day.cq.search.result.Hit;
    import com.day.cq.search.result.SearchResult;




// ###########################
// Step 2 - Reference queryBuilder OSGI service
// Add before DoGet Method 
// ###########################

//ResourceType of our article pages
private static String ARTICLE_RESOURCE_TYPE = "full-stack-training/components/structure/page-article";

@Reference
private QueryBuilder queryBuilder;






// ###########################
// Step 3 - QueryBuilder Configuration
// Add inside of DoGet after setting the response type
// ###########################

// Get the resource from where the request path is
final Resource resource = req.getResource();

// Get a resourceResolver from the request! - Awesome!
ResourceResolver resourceResolver = req.getResourceResolver();

//Map to build queryBuilder
Map<String, String> map = new HashMap<String, String>();
map.put("path", resource.getParent().getPath());
map.put("1_property","sling:resourceType");
map.put("1_property.value", ARTICLE_RESOURCE_TYPE);
map.put("p.guessTotal", "true"); //Suggested always use
map.put("p.limit","-1"); //Don't limit to 10 results.

//Query articles - Requires a resource resolver which we got from the request above
Query query = queryBuilder.createQuery(PredicateGroup.create(map), resourceResolver.adaptTo(Session.class));
SearchResult result = query.getResult();

// Create an List to hold all the found article page resources        
final List<Resource> resources = new ArrayList<Resource>();

// QueryBuilder has a leaking ResourceResolver, so the following work around is required.
ResourceResolver leakingResourceResolver = null;

try {
    // A common use case it to collect all the resources that represent hits and put them in a list for work outside of the search service
    for (final Hit hit : result.getHits()) {
        if (leakingResourceResolver == null) {
            // Get a reference to QB's leaking ResourceResolver
            leakingResourceResolver = hit.getResource().getResourceResolver();
        }

        // Add all of the pages we found in our query to a resources List.
        resources.add(resourceResolver.getResource(hit.getPath()));
    }

} catch (RepositoryException e) {
    // Should log the caught exception
} finally {
    if (leakingResourceResolver != null) {
        // Always Close the leaking QueryBuilder resourceResolver.
        leakingResourceResolver.close();
    }
}




// ###########################
// Step 4 - Update Response
// Update the response to show how many articles were found
// ###########################

// Finally send the JSON as the response of our servlet!
resp.getWriter().write("Found Articles: " + Integer.toString(resources.size()));