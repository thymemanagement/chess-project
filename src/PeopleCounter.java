import java.util.Scanner;

public class PeopleCounter {

	public static void main(String[] args) {
		Scanner inp = new Scanner(System.in);
		String data = inp.nextLine();
		if (data.equals("Q")) System.out.println("quited");
		else {
			System.out.print("\n" + data.length() + " ");
			int psn = 0;
			int wordCount = 1;
			while (data.indexOf(" ", psn) >= 0) {
				psn = data.indexOf(" ", psn) + 1;
				wordCount++;
			}
		System.out.println(wordCount);
		main(args);
		}
	}
}
