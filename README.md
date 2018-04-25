# Teaching-HEIGVD-RES-2018-Labo-SMTP


## Objectifs

Les objectifs de ce projet sont de développer une application client (TCP) en Java. Cette application client utilisera l'API Socket pour communiquer avec un server SMTP. Le code écrit inclura une **implémentation partiel du protocol SMTP**.


## Example

Considérons que notre programme génère un groupe G1. Le sender du groupe est Bob. Les destinataires sont Alice, Claire et Peter. Lorsque le prank est utilisé sur le groupe G1, alors notre programme va choisir un des faux messages. Il va communiquer avec un server SMTP, pour que Alice Claire et Peter reçoivent un e-mail qui, en apparence, paraît être envoyé par Bob.


## Configurations

Il est possible d'effectuer un certain nombre de configurations dans le fichier "config.propreties". Nous pouvons:

	- Configurer un autre port que le 2525 pour la communication avec le server smtp. Vous trouverez dans la partie "Utilisation de Docker avec MockMock" une explication de comment y parvenir.
	- Configurer une autre adresse ip en modifiant la variable smtpServerAddress. Cette adresse doit correspondre à l'adresse ip du container Docker (se référer à la partie "Utilisation de Docker avec MockMock").
	- Configurer le nombre de groupe en changeant la valeur de la variable numberOfGroup.
	- Configurer l'adresse du temoin en changeant la valeur de la variable witnessesToCc.


## Utilisation de Docker avec MockMock

Afin de construire l'image Docker, il faut:

	- Lancer Docker
	- Se placer dans le dossier contenant le fichier Dockerfile
	- Exécuter la commande "docker build -t mockmock . "
	
Une fois l'image construite:

	- Exécuter la commande "docker run -p 2525:2525 -p 8282:8282 mockmock"
	
Il suffit alors de lancer le programme depuis Intellij pour pouvoir envoyer des mails et les recevoir depuis le container.

Les mails sont disponible sur http://192.168.99.100:8282 ou l'adresse ip correspondante obtenue avec:

	- Exécuter la commande "docker-machine ip default"
	
Il faudra alors également instancier la variable smtpServerAddress du fichier config.properties à la même valeur.

Pour changer le port de communication smtp, il faut modifier la variable smtpServerPort à la valeur désirée. Il est également nécessaire de modifier les 2525:2525 en la valeur xx:xx souhaitée
dans la commande docker run.

Ne pas oublier après chaque utilisation d'effectuer les commandes:

	- Exécuter la commande "docker ps"	//pour récupérer l'identifiant idContainer du contener 
	- Exécuter la commande "docker kill idContainer" // pour tuer le contener 

	
BIM.
	
	
