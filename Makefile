# Ensimag 2A POO - TP 2016/17
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture testPremierePartie testScenario1Partie2 testScenario2Partie2

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

testPremierePartie:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestPremierePartie.java

testScenario1Partie2:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario1Partie2.java

testScenario2Partie2:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestScenario2Partie2.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:bin/gui.jar TestInvader

exeLecture: 
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map

exePremierePartie:
	java -classpath bin:bin/gui.jar TestPremierePartie cartes/carteSujet.map

exeScenario1Partie2:
	java -classpath bin:bin/gui.jar TestScenario1Partie2 cartes/carteSujet.map

exeScenario2Partie2:
	java -classpath bin:bin/gui.jar TestScenario2Partie2 cartes/carteSujet.map

clean:
	rm -rf bin/*.class
