import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;


import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class App{
    public static void main(String[] args) throws IOException{
        boolean debug = false;

        String filename = args[0];
        String searchterm = args[1];

        if(debug){System.out.println("reading file:" + filename);}

        SevenZFile sevenZFile = new SevenZFile(new File(filename));
        SevenZArchiveEntry entry;

        try{
            while((entry = sevenZFile.getNextEntry()) != null){

                String name = entry.getName();
    
                if(name.endsWith(".txt")){
                    System.out.println(name);
                    
                    byte[] buffer = new byte[2048];
                    int bytesRead;
    
                    ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();
                    
                    while((bytesRead = sevenZFile.read(buffer)) != -1){
                        contentBytes.write(buffer, 0, bytesRead);
                    }
    
                    String content = contentBytes.toString();
                    
                    String replaceword = searchterm.toUpperCase();
                    content = content.replace(searchterm.toLowerCase(), replaceword);

                    String searchterm2 = searchterm.substring(0,1).toUpperCase() + searchterm.substring(1);
                    content = content.replace(searchterm2, replaceword);

                    BufferedReader reader = new BufferedReader(new StringReader(content));

                    String line = null;
                    int i = 1;
                    while((line = reader.readLine()) != null){
                        if(line.toLowerCase().indexOf(searchterm.toLowerCase()) != -1){
                            System.out.printf("%d: %s\n", i, line);
                        }
                        i++;
                    }
                    System.out.println();
                }
            }
        } finally{
            sevenZFile.close();
        }
    }
}
// hello