import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {
	
	private String fileName;
	//max deep of the decision tree
	private final int maxDeep = 3;
	
	public JSONHandler() throws FileNotFoundException {
		fileName = "decisionTree" + Settings.lengthOfSeries + "_" + Settings.possibleElements + ".json";
        File f = new File(fileName);
        if (!f.exists())
        	create();
	}
	
	//crate new decision tree file
	private void create() throws FileNotFoundException {
		JSONObject jo = new JSONObject();
        jo.put("Series", Settings.generateFirstGuessString());
        JSONArray next = new JSONArray();
        jo.put("next", next);

        PrintWriter pw = new PrintWriter(fileName);
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
	}
	
	//Read the next decision according to the last grades
	public Series read(List<Grade> lastGrades) throws FileNotFoundException, IOException, ParseException {
		if (lastGrades.size() >= maxDeep)
			return null;
		
        Object root = new JSONParser().parse(new FileReader(fileName));
        JSONObject current = (JSONObject) root;
        current = getDescendantByGrades(lastGrades, current);
        //No data in the tree
        if (current == null)
        	return null;
        String ser = (String) current.get("Series");
		return new Series(ser);
	}
	
	public void push(List<Grade> lastGrades, Grade nextGrade, Series nextSer) throws FileNotFoundException, IOException, ParseException {
		if (lastGrades.size() + 1 >= maxDeep)
			return;
		
		Object oRoot = new JSONParser().parse(new FileReader(fileName));
		JSONObject root = (JSONObject) oRoot;
        JSONObject current = root;
        current = getDescendantByGrades(lastGrades, current);
        
        JSONArray nextGradesArray = (JSONArray) current.get("next");
               
        Map nextGradeMap = new LinkedHashMap(2);
        nextGradeMap.put("black", nextGrade.getBlacks());
        nextGradeMap.put("white", nextGrade.getWhites());
        JSONArray arrayNextsOfNext = new JSONArray();
        
        JSONObject nextObject = new JSONObject();
        nextObject.put("Grade", nextGradeMap);
        nextObject.put("Series", nextSer.get());
        nextObject.put("next", arrayNextsOfNext);
        
        nextGradesArray.add(nextObject);
        
        PrintWriter pw = new PrintWriter(fileName);
        pw.write(root.toJSONString());
        pw.flush();
        pw.close();
	}
	
	//Travels the tree from the current object according to the list of grades
	private JSONObject getDescendantByGrades(List<Grade> grades, JSONObject current) {
        //iterating series of grades
        for (int i=0; i<grades.size(); ++i) {
            JSONArray nextGradesArray = (JSONArray) current.get("next");
            JSONObject child = getChildByGrade(grades.get(i), nextGradesArray);
            if (child == null)
            	return null;
            else
            	current = child;
        }	
        return current;
	}
	
    //find child with appropriate grade 
	private JSONObject getChildByGrade(Grade grade, JSONArray arr) {
		for (Object oChild : arr) {
			JSONObject child = (JSONObject) oChild;
            if (grade.equals(getGradeFromJSONObject(child)))
            	return child;
		}
        return null;
	}
	
	private Grade getGradeFromJSONObject(JSONObject jo) {
        Map grade = ((Map)jo.get("Grade"));
		long black = (long) grade.get("black");
		long white = (long) grade.get("white");
		return new Grade((int)black, (int)white);
	}

}