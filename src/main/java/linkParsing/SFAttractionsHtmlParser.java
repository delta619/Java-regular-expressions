package linkParsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 In this lab, you will fill in the code in the SFAttractionsHtmlParser class, that has a method getValidLinks() that finds and returns a list of valid hyperlinks contained in a given HTML file.
A link in an HTML document is specified in the href attribute of an anchor tag (<a> tag).
Please refer to the comments inside the SFAttractionsHtmlParser for a detailed description of the lab.
<a href="http://www.cs.usfca.edu/">

Note that the format of the anchor tag is actually more complex than the example above shows -  the following would also be a valid a tag:
<a name="home" target="_top" href="index.html" id="home" accesskey="A">, so you can not assume that it is <a followed by the space and the href attribute.
Your goal is to extract only the link, you do not need to capture other attributes of the anchor tag.
Start by looking at the touristInfo.html file in the input folder that contains several valid html links and several invalid links at the bottom of the file.

You may not use any classes or packages except Pattern, Matcher, String, ArrayList, StringBuffer, BufferedReader, PrintWriter, IOException. Before using any other class, please ask the instructor.
Requirements:
1.	Fill in the regular expression in the string called REGEX for matching an a tag and capturing a link by using a group
2.	Fill in the code in the getValidLinks method that takes the name of the .html file and returns an ArrayList of hyperlinks in that html. The requirements:
•	The list should not contain "duplicates". For the purpose of this assignment, "duplicates" are the links that are the same, except for the fragment. Example: your code should consider these two links as equal: (because they are the same if you remove the fragment).
	"java/lang/StringBuffer.html#StringBuffer" 	"java/lang/StringBuffer.html#StringBuffer-java.lang.String"
•	Do not include links that take you to the same page (links that start with the fragment #).
•	You are required to use a regular expression and classes Pattern and Matcher, and use at least one group in this method.

3.	Fill in the code in the writeToHtmlFile method that takes a list of valid hyperlinks and writes them to the html file (so that they are clickable).
Look at the expectedResult.html file for the expected result.

You may use https://regex101.com/ website while working on your regular expression.
Hint: you may find the following "subpattern" useful for this lab: [^>]

Note: this assignment is based on and was modified from the assignment of Prof. Engle.
 */
public class SFAttractionsHtmlParser {
    public static final String REGEX = ""; // regular expression for extracting the links

    /** Return a list of valid html links without the fragment (that starts with #)
     * @param filename input file
     * @return a list of valid links represented as strings
     */
    public static List<String> getValidLinks(String filename) {
        String strData = readFromFile(filename);
        List<String> links = new ArrayList<>();

        String patternString1 = "<a\\s+href\\s*=\\s*\"(http[^\"]+)\"[^>]*>"; // regular expression for extracting the links

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(strData);


        while(matcher.find()){
            String link = matcher.group(1);
            System.out.println(link);
            links.add(link);
        }
        return links;

    }

    public static String readFromFile(String input) {
        String s = "";
        try (FileReader f = new FileReader(input)) { // using try with resources
            // BufferedReader br = new BufferedReader(f);

            // Alternatively, we could have done:
            BufferedReader br = Files.newBufferedReader(Paths.get(input));

            String line;
            while ((line = br.readLine()) != null) {
                s+=line;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    return s;
    }



    /** Write a list of links to the html file, make each link clickable using the <a></a> tags
     *
     * @param links a list of valid links
     * @param outputFilename html file
     */
    public static void writeToHtmlFile(List<String> links, String outputFilename) {
        try ( PrintWriter writer = new PrintWriter(outputFilename)) {

            for(String str: links) {
                writer.println(str.split("#")[0]);
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("IOException occured" + e);
        }


        // no need to close FileReader, BufferedReader or PrintWriter because
        // we used try with resources -> they will be closed automatically.
    }


    public static void main(String[] args) {
        List<String> links = getValidLinks("input/touristInfo.html");
//        System.out.println(links);
        writeToHtmlFile(links, "result.html"); // output all the valid links (without the fragment) to another html file
        // The expected output is in expectedResult.html
    }
}
