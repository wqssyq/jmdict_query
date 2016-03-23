# jmdict_query

Query japanese . the dictionary is by jmdict.

1. can convert katakana to hiragana

2. out put romazi. 

How to use:

1. Click [jmdict_qry.cmd] to run the application.

2. then type in the japanese word, such as:プロジェクト

3. will output:
ぷろじぇくと | purojiekuto<br>
null | ぷろじぇくと | purojiekuto | プロジェクト /(n) project/(P)/

4. then type in the japanese word, such as:発表

5. will output:
発表 | 発表<br>
発表 | はっぴょう | hatsupiou | (n,vs) announcement/publication/presenting/statement/communique/making known/breaking (news story)/expressing (one's opinion)/releasing/unveiling/(P)


there is the run message :<br>
java -cp "bin;lib\sqlite-jdbc-3.8.11.2.jar" ReadLine<br>
按Q/q键退出!<br>
プロジェクト<br>
ぷろじぇくと | purojiekuto<br>
null | ぷろじぇくと | purojiekuto | プロジェクト /(n) project/(P)/<br>
発表<br>
発表 | 発表<br>
発表 | はっぴょう | hatsupiou | (n,vs) announcement/publication/presenting/statement/communique/making known/breaking (news story)/expressing (one's opinion)/releasing/unveiling/(P)

