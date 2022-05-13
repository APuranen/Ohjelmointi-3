/**
 * Ohjelmointi-3 Harjoitustyö: Sisu-projekti, CourseHandler.
 * @author Antti-Jussi Isoviita, H283435
 * @author Aleksi Puranen, H283752
 */
package fi.tuni.prog3.projekti;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.control.TreeItem;


/**
 * Helper class to get information from Sisu's API and return it in usable format
 */

public class CourseHandler{

    static String degreeProgrammesJson;

    /**
     * Constructor for new helper object that contains a JSON body 
     * containing all found degreeProgrammes from Sisu's API
     */
    public CourseHandler(){
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000")).build();
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join();

        // Crude solution to remove response part containing info parameters :-D works until amount of programmes change
        degreeProgrammesJson = response.replace("{\"start\":0,\"limit\":1000,\"total\":269,\"searchResults\":", "");
    }
    /**
     * Searches a JSON-formatted string for information contained
     * in specific fields.
     * @param jsonBody JSON-data in string format
     * @param field name of the field data is wanted from
     * @return arraylist containing information found in given field names
     */
    private ArrayList<String> getFieldContent(String jsonBody, String field){

        ArrayList<String> infoArr = new ArrayList<>();

        JSONArray array = new JSONArray(jsonBody);
        
        for (int i =0; i < array.length(); i ++){
            JSONObject arrItem = array.getJSONObject(i);
            String fieldInfo = arrItem.getString(field);
            infoArr.add(fieldInfo);
        }
        return infoArr;
    }
    /**
     * Public method to get names of degreeProgrammes from initialized
     * degreeProgrammesJson -variable
     * @return arraylist containing names of degreeProgrammes
     */
    public ArrayList<String> getDegreeProgrammes(){
        String field = "name";
        return getFieldContent(degreeProgrammesJson, field);
    }    
    /**
     * Populates a treeView of a degreeProgramme with nested submodules and
     * courses.
     * @param programmeName name of degreeProgramme
     * @param rootItem rootItem of treeView
     * @return treeItem filled with submodules and courses
     */
    public static TreeItem<String> getModuleTree(String programmeName, TreeItem<String> rootItem){

        String programmeID = null;

        // Iterate through degreeProgrammes to get ID corresponding to given name
        JSONArray array = new JSONArray(degreeProgrammesJson);
        for (int i = 0; i < array.length(); i++){
            JSONObject arrItem = array.getJSONObject(i);
            String name = arrItem.getString("name");

            if(name.equals(programmeName)){
                programmeID = arrItem.getString("id");
            }
        }

        if(programmeID != null){
            // Construct URL
            String address = "https://sis-tuni.funidata.fi/kori/api/modules/####";
            String URL = address.replace("####", programmeID);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
            String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
            String currentJSONbody = response; // root node

            /* Pass JSON formatted string, which acts as root node
             to recursive function to fill the treeView */
            recursiveFillTree(currentJSONbody, rootItem);
        }
        return rootItem;
    }
    /**
     * Private recursive helper method to populate treeView with submodules
     * and courses. 
     * Iterates through module-course hierarchy using depth-first search
     * @param JSONBody JSON- formatted string
     * @param treeItem node representing a module
     * @return treeView with all submodules and courses
     */
    private static TreeItem<String> recursiveFillTree(String JSONBody, TreeItem<String> treeItem){

            JSONObject currentNode;

            if(JSONBody.charAt(0) == '{'){
                currentNode = new JSONObject(JSONBody);
            }
            else{
                JSONArray currentNodeArr = new JSONArray(JSONBody);
                currentNode = currentNodeArr.getJSONObject(0);
            }
            JSONObject JSONRules = currentNode.getJSONObject("rule");

            /* Change JSON-object to prettified string for row-to-row iteration
             Sisu's JSON-hierarchy has some weird edge cases containing nested arrays
             so this seems to be a simple solution to parse JSON-data.
             Especially when we're only interested in single fields*/
            String prettifiedJSON = JSONRules.toString(1);
            Scanner scanner = new Scanner(prettifiedJSON);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("moduleGroupId")){
                    String lineTypeRemoved = line.replace("\"moduleGroupId\": \"", "");
                    String lineEndRemoved = lineTypeRemoved.replace("\",", "");
                    String groupID = lineEndRemoved.replaceAll("\\s+", "");

                    String NewJSONBody = getJsonBodyOfModuleGroupID(groupID);

                    String name = getInfoString(NewJSONBody);
                    TreeItem<String> child = new TreeItem<>(name);
                    treeItem.getChildren().add(child);
                    
                    // Call recursively to get next node
                    recursiveFillTree(NewJSONBody, child);
                }

                if(line.contains("courseUnitGroupId")){
                    String lineTypeRemoved = line.replace("\"courseUnitGroupId\": \"", "");
                    String lineEndRemoved = lineTypeRemoved.replace("\",", "");
                    String groupID = lineEndRemoved.replaceAll("\\s+", "");
                    String name = getInfoString(getJsonBodyOfCourseUnitGroupID(groupID));
                    TreeItem<String> child = new TreeItem<>(name);
                    treeItem.getChildren().add(child);

                    // No lower levels in courseUnits, so no need for a recursive call
                }
            }
            scanner.close();
        return treeItem;
    }
    /**
     * Private helper method to call Sisu's API in the case that we need to get information
     * about a study module. 
     * @param groupID groupID of module
     * @return string in JSON data format containing Sisu's API response
     */
    private static String getJsonBodyOfModuleGroupID(String groupID){
     
        String address = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=####&universityId=tuni-university-root-id";
        String URL = address.replace("####", groupID);

        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
            String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        return response;
    }
    /**
     * Private helper method to call Sisu's API in the case that we need to get information
     * about a course. 
     * @param groupID groupID of course
     * @return string in JSON data format containing Sisu's API response
     */
    private static String getJsonBodyOfCourseUnitGroupID(String groupID){
        String address = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=####&universityId=tuni-university-root-id";
        String URL = address.replace("####", groupID);

        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
            String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        return response;
    }
    /**
     * Private helper method to parse information from JSON-data.  
     * @param JSONbody JSON formatted string
     * @return string in JSON data format containing Sisu's API response
     */
    private static String getInfoString(String JSONbody){
        JSONObject object;
        if(JSONbody.charAt(0) == '{'){
            object = new JSONObject(JSONbody);
        }
        else{
            JSONArray currentNodeArr = new JSONArray(JSONbody);
            object = currentNodeArr.getJSONObject(0);
        }
        try{
            String name = new JSONObject(object.get("name").toString()).getString("fi");
            return name;
        } catch(JSONException e) {
            String name = new JSONObject(object.get("name").toString()).getString("en");
            return name;
        }
    }
}