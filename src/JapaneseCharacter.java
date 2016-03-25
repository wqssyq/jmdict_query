
/**
 * JapaneseCharacter contains static functions to do various tests
 * on characters to determine if it is one of the various types of
 * characters used in the japanese writing system.
 * <p/>
 * There are also a functions to translate between Katakana, Hiragana,
 * and Romaji.
 *
 * @author Duane J. May <djmay@mayhoo.com>
 * @version $Id: JapaneseCharacter.java,v 1.2 2002/04/20 18:10:24 djmay Exp $
 * @since 10:37 AM - 6/3/14
 *
 * @see <a href="http://sourceforge.net/projects/kanjixml/">http://sourceforge.net/projects/kanjixml/</a>
 */
public class JapaneseCharacter {

	/**
	 * Version information
	 */
	@SuppressWarnings("unused")
	private final static String VERSION =
			"$Id: JapaneseCharacter.java,v 1.2 2002/04/20 18:10:24 djmay Exp $";

	/**
	 * Determines if this character is a Japanese Kana.
	 */
	public static boolean isKana(char c) {
		return (isHiragana(c) || isKatakana(c));
	}

	/**
	 * Determines if this character is one of the Japanese Hiragana.
	 */
	public static boolean isHiragana(char c) {
		return (('\u3041' <= c) && (c <= '\u309e'));
	}
	
	public static boolean isLegalUnit(String unit) {
		/*
		 * Japanese syllabic units can comprise of at most two Hiragana characters,
		 * the first one must be a full-width character, and the second must be a
		 * half-width や, よ, or ゆ
		 */
		
		/*
		 * Note: there is no need to have a case for when hiragana is null because
		 * the compiler will complain about an ambiguous parameter since both
		 * Hiragana constructors take in one Object, and null will work for both of
		 * them
		 */
		
		// Ensure valid number of characters
		if (unit.length() < 1 || unit.length() > 2) {
			return false;
		}
		
		// First character must be full width Hiragana
		if (!JapaneseCharacter.isFullWidthHiragana(unit.charAt(0))) {
			return false;
		}
		
		// Second character (if it exists) must be half width Hiragana
		if (unit.length() == 2) {
			if (!JapaneseCharacter.isHalfWidthHiragana(unit.charAt(1))) {
				return false;
			}
			
			// Verify the legality of the 2 character combination
			if (!JapaneseCharacter.isLegalCombo(unit)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifies that the specified unit is indeed a legal syllabic unit in Japanese.
	 * 
	 * All characters in unit must be in Hiragana.
	 * 
	 * @return true iff the specified unit is a legal syllabic unit in Japanese.
	 */
	public static boolean isLegalCombo(String combo) {
		/*
		 * A legal Hiragana unit is one where the first char ends in the -i vowel sound
		 * and the second char must be one of small 'や', 'よ', or 'ゆ'
		 */
		boolean endsWithI = JapaneseCharacter.toRomaji(combo.charAt(0)).endsWith("i");
		
		char secondChar = combo.charAt(1);
		boolean legalSecondChar = secondChar == 'ゃ' ||
															secondChar == 'ょ' ||
															secondChar == 'ゅ';
				
		return endsWithI && legalSecondChar;
	}
	
	/**
	 * Determines if this character is one of the Japanese Katakana.
	 */
	public static boolean isKatakana(char c) {
		//return (isHalfWidthKatakana(c) || isFullWidthKatakana(c));
		boolean isHalfWidthKatakana = isHalfWidthKatakana(c);
		boolean isFullWidthKatakana = isFullWidthKatakana(c);
		//System.out.println(" (JapaneseCharacter.java:106)" + c + "" + Integer.toHexString((int)c)  + ":" + isHalfWidthKatakana + ":" + isFullWidthKatakana);
		return (isHalfWidthKatakana || isFullWidthKatakana);
	}
	
	/**
	 * Determines if this character is a half width Hiragana character,
	 * EXCLUDING 'っ' (small tsu).
	 */
	public static boolean isHalfWidthHiragana(char c) {
		return (c == '\u3083' ||  // small や
						c == '\u3087' ||  // small よ 
						c == '\u3085');   // small ゆ
	}
	
	/**
	 * Determines if this character is a full width Hiragana character.
	 */
	public static boolean isFullWidthHiragana(char c) {
		return isHiragana(c) && !isHalfWidthHiragana(c);
	}
	
	/**
	 * Determines if this character is a Half width Katakana.
	 */
	public static boolean isHalfWidthKatakana(char c) {
		return (('\uff66' <= c) && (c <= '\uff9d'));
	}

	/**
	 * Determines if this character is a Full width Katakana.
	 */
	public static boolean isFullWidthKatakana(char c) {
		return (('\u30a1' <= c) && (c <= '\u30fe'));
	}

	/**
	 * Determines if this character is a Kanji character.
	 */
	public static boolean isKanji(char c) {
		if (('\u4e00' <= c) && (c <= '\u9fa5')) {
			return true;
		}
		if (('\u3005' <= c) && (c <= '\u3007')) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if this character could be used as part of
	 * a romaji character.
	 */
	public static boolean isRomaji(char c) {
		if (('\u0041' <= c) && (c <= '\u0090'))
			return true;
		else if (('\u0061' <= c) && (c <= '\u007a'))
			return true;
		else if (('\u0021' <= c) && (c <= '\u003a'))
			return true;
		else if (('\u0041' <= c) && (c <= '\u005a'))
			return true;
		else
			return false;
	}

	/**
	 * Translates this character into the equivalent Katakana character.
	 * The function only operates on Hiragana and always returns the
	 * Full width version of the Katakana. If the character is outside the
	 * Hiragana then the origianal character is returned.
	 */
	public static char toKatakana(char c) {
		if (isHiragana(c)) {
			return (char) (c + 0x60);
		}
		return c;
	}

	/**
	 * Translates this character into the equivalent Hiragana character.
	 * The function only operates on Katakana characters
	 * If the character is outside the Full width or Half width
	 * Katakana then the origianal character is returned.
	 */
	public static char toHiragana(char c) {
		//System.out.println("191:" + c + ":" + ((int)c) + ":" + '\u30fc');
		if(c=='\u30fc'){
			//System.out.println("193:" + c);
			return c;
		}else 
		if (isFullWidthKatakana(c)) {
			return (char) (c - 0x60);
		} else if (isHalfWidthKatakana(c)) {
			return (char) (c - 0xcf25);
		}
		return c;
	}

	public static String toHiragana(String s) {
		StringBuffer sb = new StringBuffer();
    	for(int i=0; i<s.length(); i++){
    		char c = s.charAt(i);
    		sb.append(toHiragana(c));
    	}
		return sb.toString();
	}
	
	/**
	 * Translates this character into the equivalent Romaji character.
	 * The function only operates on Hiragana and Katakana characters
	 * If the character is outside the given range then
	 * the origianal character is returned.
	 * <p/>
	 * The resulting string is lowercase if the input was Hiragana and
	 * UPPERCASE if the input was Katakana.
	 */
	public static String toRomaji(char c) {
		if (isHiragana(c)) {
			return lookupRomaji(c);
		} else if (isKatakana(c)) {
			c = toHiragana(c);
			//System.out.println("(JapaneseCharacter.java:227)" + c );
			String str = lookupRomaji(c);
			//System.out.println("229:" + str);
			//Thread.dumpStack();
			return str.toUpperCase();
		}
		return String.valueOf(c);
	}

	public static String toRomaji(String s) {
		StringBuffer sb = new StringBuffer();
    	for(int i=0; i<s.length(); i++){
    		char c = s.charAt(i);
    		sb.append(toRomaji(c));
    	}
		return sb.toString();
	}
	
	public static void test01(){
    	//String s1 = "はディープラーニングのこと";
    	String s1 = "ー" + '\u30fc';
    	for(int i=0; i<s1.length(); i++){
    		char c = s1.charAt(i);
    		//System.out.println(c + toRomaji(c));
    		//System.out.print(c);
    		System.out.println(Integer.toHexString((int)c));
    		System.out.print(toRomaji(c));
    	}
    	System.out.println();
    	System.out.println(s1);
	}

	public static void test02(){
		//isFullWidthKatakana
    	//return (('\u30a1' <= c) && (c <= '\u30fe'));	
    	for(char c='\u30a1'; c<='\u30fe'; c++){
    		System.out.print(c);
    		System.out.println((char)(c-0x60));
    		//(char) (c - 0x60)
    	}
    	//ー30fc:false:true゜
	}
	
//	public static boolean isHalfWidthKatakana(char c) {
//		return (('\uff66' <= c) && (c <= '\uff9d'));
//	}
	
	public static void test03(){
		//isHalfWidthKatakana
    	//return (('\uff66' <= c) && (c <= '\uff9d'));	
    	for(char c='\uff66'; c<='\uff9d'; c++){
    		System.out.print(c);
    	}
	}
	
    public static void main(String[] args) {
    	test01();
    	//test02();
    	//test03();
    }
	

	/**
	 * The array used to map hirgana to romaji.
	 */
	protected static String romaji[] = {
			"a", "a",
			"i", "i",
			"u", "u",
			"e", "e",
			"o", "o",

			"ka", "ga",
			"ki", "gi",
			"ku", "gu",
			"ke", "ge",
			"ko", "go",

			"sa", "za",
//			"shi", "ji",
			"si", "ji",
			"su", "zu",
			"se", "ze",
			"so", "zo",

			"ta", "da",
//			"chi", "ji",
			"ti", "ji",			
//			"tsu", "tsu", "zu",
			"tu", "tu", "zu",			
			"te", "de",
			"to", "do",

			"na",
			"ni",
			"nu",
			"ne",
			"no",

			"ha", "ba", "pa",
			"hi", "bi", "pi",
			"fu", "bu", "pu",
			"he", "be", "pe",
			"ho", "bo", "po",

			"ma",
			"mi",
			"mu",
			"me",
			"mo",

			"a", "ya",
			"u", "yu",
			"o", "yo",

			"ra",
			"ri",
			"ru",
			"re",
			"ro",

			"wa", "wa",
			"wi", "we",
			"o",
			"n",

			"v",
			"ka",
			"ke"

	};
    protected static int romaji_len = romaji.length;

	/**
	 * Access the array to return the correct romaji string.
	 */
	private static String lookupRomaji(char c) {
		//return romaji[c - 0x3041];
		//System.out.println(romaji_len);
		//System.out.println(c - 0x3041);
		if(c - 0x3041<=romaji_len-1){
			return romaji[c - 0x3041];
		}else{
			return ""+c;
		}
	}
}
