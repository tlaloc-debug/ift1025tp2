Ce projet vous permet de consulter la liste des cours offerts par l'Université de Montréal pour les sessions d'automne, d'hiver et d'été.

Ce projet permet également l'inscription à l'un de ces cours.

Ce projet est composé de trois projets:

Server

ce programme a accès aux fichiers cours.txt et inscription.txt. Il accepte un client et reçoit des commandes. Si la commande est "charger", le serveur reçoit une session en paramètre et renvoie la liste des cours offerts dans cette session à partir du fichier "cours.txt". Si la commande est "inscrire", le serveur attend un format d'inscription et insere un enregistrement dans le fichier "inscription.txt".

Client_Simple

Ce programme se connecte au serveur et envoie une commande. Si la commande est "charger", il imprime la liste reçue du serveur sur la ligne de commande. Si la commande est "inscrire", le programme reçoit les données de l'étudiant en s'assurant qu'elles sont valides et envoie ces informations au serveur.

Client_GUI

Ce programme est une adaptation graphique du client_simple qui facilite le processus de consultation et d'inscription en éliminant la nécessité d'utiliser la ligne de commande

Liste de fichiers

- Server

	Lien vers javaDocs server:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/IFT1025-TP2-server/javadoc
	
	Lien vers code server:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/IFT1025-TP2-server/src/main/java/server
	
- Client_Simple
	
	Lien vers javaDocs Client_simple:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/Client/javaDoc
	
	Lien vers code Client_simple:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/Client/client
	
- Client_GUI
	
	Lien vers javaDocs Client_GUI:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/demo/javaDoc
	
	Lien vers code Client_GUI:
	- https://github.com/tlaloc-debug/ift1025tp2/tree/inscription2/demo/src/main/java/com/example/demo
