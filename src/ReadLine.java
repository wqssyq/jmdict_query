import java.io.*;

public class ReadLine {
	
	public static void doQuery(String strLine){
		if(strLine.length()>0){
			String hiragana = JapaneseCharacter.toHiragana(strLine);
			boolean isKanji = JapaneseCharacter.isKanji(strLine.charAt(0));
			String romaji = JapaneseCharacter.toRomaji(hiragana);
			System.out.println(hiragana + " | " + romaji);
			int count = Jmdict.doQuery(hiragana, isKanji);
			
			if(count==0){
				doQuery(strLine.substring(0, strLine.length()-1));
			}
		}
		
	}
	
	public static void main(String[] args) {
		
//		File f = new File("lib/dict.sqlite");
//		System.out.println(f.getAbsolutePath());
		
		System.out.println("press Q/q to quit!");
		String strLine = "";
		while (!strLine.equalsIgnoreCase("q")) {
			strLine = readString();
			//System.out.println(strLine);
			strLine = strLine.trim();
			
			if(strLine.length()>5 && strLine.startsWith("page=")){
				String page = strLine.substring(5);
				System.out.println(page);
				try {
					int iPage = Integer.parseInt(page);
					Jmdict.page = iPage;
					continue;
				} catch (Exception e) {
					System.out.println("can't convert " + page + " to int.");
				}
			}
			doQuery(strLine);
		}

	}

	public static String readString() {
		try {
			return new BufferedReader(new InputStreamReader(System.in))
					.readLine();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {

		}

	}

}