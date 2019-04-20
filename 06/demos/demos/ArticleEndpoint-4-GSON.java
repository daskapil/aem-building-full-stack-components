// ###########################
// Step 0 - Update POMs
//  -- /full-stack-training/pom.xml
//  -- /full-stack-training/core/pom.xml
// ###########################
<dependency>
    <artifactId>gson</artifactId>
    <version>2.8.2</version>
    <groupId>com.google.code.gson</groupId>
    <scope>provided</scope>
</dependency>

// ###########################
// Step 1 - Imports
// ###########################

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



// ###########################
// Step 2 - Use GSON and Update Response!
// Replace current response with block below
// ###########################

 //Create the JSON string
String responseJson = new Gson().toJson(allArticles);

// Finally send the JSON as the response of our servlet!
resp.getWriter().write(responseJson);

