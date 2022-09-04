import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader{
    private String filename;
    private Scanner scan; 
    private String currentCommand; 
    private String[] currentCommandArgs; 
    private boolean blankCommand; 
    
    public FileReader(String filename ) { 
        this.filename = filename;
        
        try {
        File file = new File(this.filename);
        this.scan = new Scanner(file);
        }
        catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

  public boolean readNextLine() { 

            if (scan.hasNextLine()) {
                String cmd = scan.nextLine();

                // if line is empty, do nothing and continue
                if (!cmd.isBlank()) {
                this.blankCommand = false;
                //remove multiple spaces between args:
                 cmd = removeSpace(cmd);
                 this.currentCommand = cmd;  
                // Split cmd into componenets
                this.currentCommandArgs = cmd.split(" ");
                return false;
                }
                else
                    this.blankCommand = true; 
                return false;
            }
            else
                {scan.close();
                return true;}
            
             
        
}

  
  public static String removeSpace(String cmd) {
      // remove the leading and trailing white spaces:
      cmd = cmd.trim();
      // replace multiple white spaces with single white space:
      cmd = cmd.replaceAll(" +", " ");
      return cmd;
  }
  
  
  public String getCurrentCommand() {
      return this.currentCommand;
  }
  
  public String[] getCurrentCommandArgs() {
      return this.currentCommandArgs;
  }
  public boolean checkIfBlankCommand() { 
      return this.blankCommand;
  }
}