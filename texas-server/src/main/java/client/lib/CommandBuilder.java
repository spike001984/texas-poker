package client.lib;

public class CommandBuilder {
	
	private static class Command{
		public static String FOLD = "fold";
		public static String CHECK = "check";
		public static String CALL = "call";
		public static String BET = "bet";
		public static String RAISE = "raise";
		public static String ALL_IN = "all-in";
	}
	
	public String getCallCommand(){
		return Command.CALL;
	}
	
	public String getFoldCommand(){
		return Command.FOLD;
	}
	
	public String getCheckCommand(){
		return Command.CHECK;
	}
	
	public String getRaiseCommand(int chip){
		return Command.RAISE + " " + chip;
	}
	
	public String getAllInCommand(){
		return Command.ALL_IN;
	}
	
	public String getBetCommand(int chip){
		return Command.BET + " " + chip;
	}
}
