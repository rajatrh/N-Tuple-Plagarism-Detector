public class MainClass {
	public static void main(String args[]) {
		String synonymsArray = "run go wow|run sprint jog|a the|a the";
		String stringA = "go for a";
		String stringB = "wow for the sprint";
		
		System.out.print(solution(new String[]{synonymsArray, stringA, stringB}, 3));
	}
	
	public static int solution(String[] inputStrings, int tupleSize) {
		if (inputStrings.length != 3) {
            throw new IllegalArgumentException();
        }
        // Write your code here
		PlagarismService plagarismService = new PlagarismService(inputStrings[0], tupleSize, true);
		
		int measure =  plagarismService.measureRoundedPlagarism(inputStrings[1], inputStrings[2]);
		return measure;
    }
}
