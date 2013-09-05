Projet prototype Struts
=============================

L'objectif de ce projet est de tester certains aspects de Struts.

Parmi les essais en cours, le plus abouti est celui de l'utilisation d'Ajax.

Prototype d'échange Ajax/json
---------------------------------------

Il s'agit d'une application de gestion de livre, très simple. Elle peut être déployé sur un Tomcat (version 7). Celui-ci doit définir une datasource 
JNDI du nom de jdbc/booksDataSource. Sous reserve de configuration de cette datasource, l'application crée automatiquement les
tables au démarrage:

* BOOK
* CAT
* BOOK_CAT

Une fois démarrée, l'application est accessible à 
[http://localhost:8080/ProtoStruts/proto/books.do](http://localhost:8080/ProtoStruts/proto/books.do)

### Librairies

Pour les versions exactes, voir le [pom.xml](https://github.com/hittepit/ProtoStruts/blob/master/pom.xml).

* struts 2
	- dont plugin struts-json
* spring
* hibernate
* 

### Modèle robuste

La base de l'application c'est un modèle robuste. Voir les explications dans la JavaDoc de la classe
[Book](https://github.com/hittepit/ProtoStruts/blob/master/src/main/java/be/fabrice/proto/model/entity/Book.java).

### Les échanges Ajax

dsfdsf

## La validation

Eléments pris en comptes
 
Ce qui fonctionne correctement:

* l'utilisation de validation avec Json et angularJs

Attention, le contrôle sur l'isbn a été allégé. Maintenant, 13 chiffres (caractères en fait) qui doivent commencer par 978.

TODO proto

* améliorer l'ajax/json pour les exceptions et les erreurs d'autorisation

* problème mise en page formulaire

	- centrage du formulaire lorsque erreurs de validations
	
* commentaires
 
* formater l'isbn (écriture, champ de saisie)

* supprimer des livres

* gestion des catégories

* tests sur CategoryMapper