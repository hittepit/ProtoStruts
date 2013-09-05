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
	- dont le plugin struts2-json
* spring
* hibernate
* jquery
* angularJS

### Modèle robuste

La base de l'application c'est un modèle robuste. Voir les explications dans la JavaDoc de la classe
[Book](https://github.com/hittepit/ProtoStruts/blob/master/src/main/java/be/fabrice/proto/model/entity/Book.java).

Elle utilise notamment un objet ISBN dont la validation a été simplifiée pour permettre l'introduction
de n'importe quel chiffre à des fins de démo. Il suffit de rentrer 13 chiffres (caractères en fait) commençant par 978.

Aucune contrainte d'unicité sur ce numéro n'a été activée dans l'application où au niveau de la base de données
ce qui devrait normalement être le cas.

### Les échanges Ajax

L'application affiche au départ une page dont le contenu est chargé par un contrôleur AngularJS.

A partir de ce moment, les seules requêtes vers le serveur sont des requêtes Ajax, les données envoyées et reçues
sont au format Json.

C'est AngularJS et jQuery qui se chargent d'utiliser les résultats.

L'application montre comment il est possible de standardiser

* la validation côté serveur. Elle existe du côté client mais a été désactivée pour démontrer le fonctionnement
du côté serveur;
* les opérations non autorisées (utilisation d'un intercepteur "mock": il suffit de chercher à entrer le titre
"unauthorized" pour que l'accès soit refusé;
* les erreurs 500. Pour les tester entrer un titre "exception".

## La validation

La validation est effectuée de manière standard par Struts. Les validateurs sont soit des built-in, soit des
validateurs qui font appel à des validations métier (pour l'ISBN par exemple).

## TODO proto

* améliorer l'ajax/json pour les exceptions et les erreurs d'autorisation

* problème mise en page formulaire

	- centrage du formulaire lorsque erreurs de validations
	
* commentaires
 
* formater l'isbn (écriture, champ de saisie)

* supprimer des livres

* gestion des catégories

* tests sur CategoryMapper