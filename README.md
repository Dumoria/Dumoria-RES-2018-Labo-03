# Teaching-HEIGVD-RES-2018-Labo-SMTP


## Objectifs

The objectives of this project is to develop a client application (TCP) in Java. This client application use the Socket API to communicate with a SMTP server. The code written include a **partial implementation of the SMTP protocol**. 


## Example

Consider that your program generates a group G1. The group sender is Bob. The group recipients are Alice, Claire and Peter. When the prank is played on group G1, then your program should pick one of the fake messages. It should communicate with a SMTP server, so that Alice, Claire and Peter receive an e-mail, which appears to be sent by Bob.


## Configuration

Il est possible d'effectuer un certain nombre de configurations dans le fichier "config.propreties". Nous pouvons:

	- Configurer un autre port que le 2525 pour la communication avec le server smtp. Vous trouverez dans la partie "Utilisation de Docker avec MockMock" une explication de comment y parvenir.
	- Configurer une autre adresse ip en modifiant la variable smtpServerAddress. Cette adresse doit correpondre à l'adresse ip du container Docker (se référer à la partie "Utilisation de Docker avec MockMock").
	- Configurer le nombre de groupe en changeant la valeur de la variable numberOfGroup.
	- Configurer l'adresse du temoin en changeant la valeur de la variable witnessesToCc.


## Utilisation de Docker avec MockMock

Afin de construire l'image Docker, il faut:

	- Lancer Docker
	- Se placer dans le dossier contenant le fichier Dockerfile
	- Executer la commande:
		docker build -t mockmock . 
	
Une fois l'image construite, executer la commande:
	- docker run -p 2525:2525 -p 8282:8282 mockmock
	
Il suffit alors de lancer le programme depuis Intellij pour pouvoir envoyer des mails et les recevoir depuis le container.

Les mails sont disponible sur http://192.168.99.100:8282 ou l'adresse ip correspondante obtenue avec la commande:
	docker-machine ip default
Il faudra alors également instancier la variable smtpServerAddress du fichier config.properties à la même valeur.

Pour changer le port de communication smtp, il faut modifier la variable smtpServerPort à la valeur désirée. Il est également nécessaire de modifier les 2525:2525 en la valeur xx:xx souhaitée
dans la commande docker run.

Ne pas oublier après chaque utilisation d'effectuer les commandes:
	- docker ps 				//pour récupérer l'identifiant du contener 
	- docker kill idContainer // pour tuer le contener 

	
BIM.
	
	
