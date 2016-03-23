import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Jmdict {
	
	public static void doQuery(String data){
		Connection connection = null;
		try {
			// create a database connection

			//connection = DriverManager.getConnection("jdbc:sqlite:D:/dict/jap/dict.sqlite");
			connection = DriverManager.getConnection("jdbc:sqlite:lib/dict.sqlite");
			
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			//String sql = "select * from dict where kanji like '" + data + "%'  or kana like '" + data + "%'";
			String sql = "select * from dict where kanji = '" + data + "'  or kana = '" + data + "'";
			
			ResultSet rs = statement.executeQuery(sql);

//			CREATE TABLE dict (kanji TEXT, kana TEXT, entry TEXT);
//
//			CREATE INDEX "ix_kanji" ON "dict" ("kanji" ASC);
//
//			CREATE INDEX "ix_kana" ON "dict" ("kana" ASC);
			while (rs.next()) {
				//System.out.println("kana = " + rs.getString("kana"));
//				System.out.println(rs.getString("kanji") 
//						+ " | " + rs.getString("kana") 
//						+ " | " + rs.getString("entry") );
				String kanji = rs.getString("kanji");
				String kana = rs.getString("kana");
				String romaji = JapaneseCharacter.toRomaji(kana);
				String entry = rs.getString("entry");

				//System.out.println(kanji + " | " + kana + " | " + entry );

//				if(kanji!=null){
//					//data.indexOf(kana)
//					//System.out.println(kanji.length());
//					System.out.println(kanji + " | " + kanji.indexOf(data) + " | " + entry );
//				}
				
				
				if(( kanji!=null && kanji.indexOf(data)!=-1 && data.length()>=kanji.length()-1)
						|| (kana!=null &&  kana.indexOf(data)!=-1 && data.length()>=kana.length()-1)){
					System.out.println(kanji + " | " + kana + " | " + romaji + " | " + entry );
//					System.out.println(data.length());
//					if(kanji!=null){
//						System.out.println(kanji.length());
//					}
//					if(kana!=null){
//						System.out.println(kana.length());
//					}
				}
			}
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	public static void main(String[] args) {
		//String data = "‡ì³ž";
		//doQuery(args[0]);
		//System.in.readline();
	}

}
