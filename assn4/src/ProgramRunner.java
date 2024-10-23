import controller.ScriptRunner;

public class ProgramRunner {

  public static void main(String[] args) {
    if(args.length > 0) {
      ScriptRunner.go(args[0]);
    } else {
      System.out.println("No script file provided!");
    }
  }

}
